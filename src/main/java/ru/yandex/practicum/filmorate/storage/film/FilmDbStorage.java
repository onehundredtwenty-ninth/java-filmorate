package ru.yandex.practicum.filmorate.storage.film;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
public class FilmDbStorage implements FilmStorage {

  private final JdbcTemplate jdbcTemplate;

  public FilmDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Film addFilm(Film film) {
    var sqlQuery = "INSERT INTO \"film\" (name, description, release_date, duration, mpa_id) "
        + "VALUES (?, ?, ?, ?, ?);";

    var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
      stmt.setString(1, film.getName());
      stmt.setString(2, film.getDescription());
      stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
      stmt.setFloat(4, film.getDuration());
      stmt.setLong(5, film.getMpa().getId());
      return stmt;
    }, keyHolder);

    film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

    if (film.getGenres() != null) {
      setFilmGenres(film);
    }

    return film;
  }

  @Override
  public Film updateFilm(Film film) {
    var sqlQuery = "UPDATE \"film\" SET name = ?, "
        + "                             description = ?, "
        + "                             release_date = ?, "
        + "                             duration = ?, "
        + "                             mpa_id = ? "
        + "WHERE id = ?;";

    int updatedRowsQuantity = jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
        film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

    if (updatedRowsQuantity == 0) {
      throw new EntityNotFoundException("Film with id: " + film.getId() + " not found");
    }

    if (film.getGenres() != null) {
      film.setGenres(film.getGenres().stream().sorted(Comparator.comparingLong(Genre::getId)).collect(
          Collectors.toCollection(LinkedHashSet::new)));
      deleteOldGenres(film.getId());
      setFilmGenres(film);
    }

    return film;
  }

  @Override
  public Collection<Film> getFilms() {
    var sqlQuery = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.name AS mpa_name "
        + "FROM \"film\" f "
        + "JOIN \"mpa\" m ON f.mpa_id = m.id";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
  }

  @Override
  public Film getFilmById(long filmId) {
    var sqlQuery = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.name AS mpa_name "
        + "FROM \"film\" f "
        + "JOIN \"mpa\" m ON f.mpa_id = m.id "
        + "WHERE f.id = ?";

    return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeFilm(rs), filmId);
  }

  public Collection<Film> getMostPopularFilms(int count) {
    var sqlQuery = "SELECT f.id, "
        + "                f.name, "
        + "                f.description, "
        + "                f.release_date, "
        + "                f.duration, "
        + "                f.mpa_id, "
        + "                m.name AS mpa_name, "
        + "                COUNT(l.id) AS likes_quantity "
        + "FROM \"film\" f "
        + "JOIN \"mpa\" m ON f.mpa_id = m.id "
        + "LEFT JOIN \"likes\" l ON l.film_id = f.id "
        + "GROUP BY f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, mpa_name "
        + "ORDER BY likes_quantity DESC "
        + "LIMIT ?";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs), count);
  }

  public void addLike(long filmId, long userId) {
    var sqlQuery = "INSERT INTO \"likes\" (user_id, film_id) "
        + "VALUES (?, ?);";

    jdbcTemplate.update(sqlQuery, userId, filmId);
  }

  public void removeLike(long filmId, long userId) {
    var sqlQuery = "DELETE FROM \"likes\" "
        + "WHERE user_id = ? AND film_id = ?";

    int updatedRowsQuantity = jdbcTemplate.update(sqlQuery, userId, filmId);

    if (updatedRowsQuantity == 0) {
      throw new EntityNotFoundException(
          "Film with id: " + filmId + " didn't contain like from user with id: " + userId);
    }
  }

  @Override
  public Collection<Mpa> getMpaList() {
    var sqlQuery = "SELECT id, name "
        + "FROM \"mpa\" "
        + "ORDER BY id";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeMpa(rs));
  }

  @Override
  public Mpa getMpaById(long mpaId) {
    var sqlQuery = "SELECT id, name "
        + "FROM \"mpa\" "
        + "WHERE id = ?";

    return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeMpa(rs), mpaId);
  }

  @Override
  public Collection<Genre> getGenres() {
    var sqlQuery = "SELECT id, name "
        + "FROM \"genre\" "
        + "ORDER BY id";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeGenre(rs));
  }

  @Override
  public Genre getGenresById(long genreId) {
    var sqlQuery = "SELECT id, name "
        + "FROM \"genre\" "
        + "WHERE id = ?";

    return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeGenre(rs), genreId);
  }

  private Film makeFilm(ResultSet rs) throws SQLException {
    return Film.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("name"))
        .description(rs.getString("description"))
        .releaseDate(rs.getDate("release_date").toLocalDate())
        .duration(rs.getLong("duration"))
        .mpa(new Mpa(rs.getLong("mpa_id"), rs.getString("mpa_name")))
        .genres(getFilmGenres(rs.getLong("id")))
        .likes(getFilmLikes(rs.getLong("id")))
        .build();
  }

  private Set<Genre> getFilmGenres(Long filmId) {
    Set<Genre> genres = new HashSet<>();

    var sqlQuery = "SELECT g.id, g.name "
        + "FROM \"film_genres\" fg "
        + "JOIN \"genre\" g ON fg.genre_id = g.id "
        + "WHERE fg.film_id = ? "
        + "ORDER BY g.id";

    var rs = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
    while (rs.next()) {
      genres.add(new Genre(rs.getLong("id"), rs.getString("name")));
    }
    return genres;
  }

  private Set<Long> getFilmLikes(Long filmId) {
    Set<Long> likes = new HashSet<>();

    var sqlQuery = "SELECT user_id "
        + "FROM \"likes\" "
        + "WHERE film_id = ?";

    var rs = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
    while (rs.next()) {
      likes.add(rs.getLong("user_id"));
    }
    return likes;
  }

  private void setFilmGenres(Film film) {
    var sqlQuery = "INSERT INTO \"film_genres\" (film_id, genre_id) "
        + "VALUES (?, ?);";

    for (Genre genre : film.getGenres()) {
      jdbcTemplate.update(sqlQuery, film.getId(), genre.getId());
    }
  }

  private void deleteOldGenres(long filmId) {
    var sqlQuery = "DELETE FROM \"film_genres\" "
        + "WHERE film_id = ?";

    jdbcTemplate.update(sqlQuery, filmId);
  }

  private Mpa makeMpa(ResultSet rs) throws SQLException {
    return new Mpa(rs.getLong("id"), rs.getString("name"));
  }

  private Genre makeGenre(ResultSet rs) throws SQLException {
    return new Genre(rs.getLong("id"), rs.getString("name"));
  }
}
