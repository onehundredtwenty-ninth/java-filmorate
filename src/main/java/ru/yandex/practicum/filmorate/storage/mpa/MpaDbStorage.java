package ru.yandex.practicum.filmorate.storage.mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
public class MpaDbStorage implements MpaStorage {

  private final JdbcTemplate jdbcTemplate;

  public MpaDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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

  private Mpa makeMpa(ResultSet rs) throws SQLException {
    return new Mpa(rs.getLong("id"), rs.getString("name"));
  }
}
