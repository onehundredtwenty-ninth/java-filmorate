package ru.yandex.practicum.filmorate.storage.genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

@Component
public class GenreDbStorage implements GenreStorage {

  private final JdbcTemplate jdbcTemplate;

  public GenreDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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

  private Genre makeGenre(ResultSet rs) throws SQLException {
    return new Genre(rs.getLong("id"), rs.getString("name"));
  }
}
