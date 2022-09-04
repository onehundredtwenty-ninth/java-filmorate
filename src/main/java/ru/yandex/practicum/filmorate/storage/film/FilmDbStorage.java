package ru.yandex.practicum.filmorate.storage.film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
public class FilmDbStorage implements FilmStorage {

  private final JdbcTemplate jdbcTemplate;

  public FilmDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Film addFilm(Film film) {
    var sqlQuery = "INSERT INTO \"film\" (name, description, release_date, duration, rating_id) "
        + "VALUES (?, ?, ?, ?, ?);";

    jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
        film.getMpa().getId());
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

    jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
        film.getMpa().getId(), film.getId());
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
        + "JOIN \"likes\" l ON l.film_id = f.id "
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

    jdbcTemplate.update(sqlQuery, userId, filmId);
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

  private List<String> getFilmGenres(Long filmId) {
    List<String> genres = new ArrayList<>();

    var sqlQuery = "SELECT g.name "
        + "FROM \"film_genres\" fg "
        + "JOIN \"genre\" g ON fg.genre_id = g.id "
        + "WHERE fg.film_id = ?";

    var rs = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
    while (rs.next()) {
      genres.add(rs.getString("name"));
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
}
