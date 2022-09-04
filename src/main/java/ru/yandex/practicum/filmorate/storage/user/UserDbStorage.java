package ru.yandex.practicum.filmorate.storage.user;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class UserDbStorage implements UserStorage {

  private final JdbcTemplate jdbcTemplate;

  public UserDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public User addUser(User user) {
    var sqlQuery = "INSERT INTO \"user\" (email, login, name, birthday) "
        + "VALUES (?, ?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
      stmt.setString(1, user.getEmail());
      stmt.setString(2, user.getLogin());
      stmt.setString(3, user.getName());
      stmt.setDate(4, Date.valueOf(user.getBirthday()));
      return stmt;
    }, keyHolder);

    user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

    return user;
  }

  @Override
  public User updateUser(User user) {
    var sqlQuery = "UPDATE \"user\" SET email = ?, "
        + "                             login = ?, "
        + "                             name = ?, "
        + "                             birthday = ? "
        + "WHERE id = ?;";

    jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
    return user;
  }

  @Override
  public Collection<User> getUsers() {
    var sqlQuery = "SELECT id, email, login, name, birthday "
        + "FROM \"user\"";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
  }

  @Override
  public User getUserById(long id) {
    var sqlQuery = "SELECT id, email, login, name, birthday "
        + "FROM \"user\" "
        + "WHERE id = ?";

    return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
  }

  @Override
  public void addFriend(long requestingFriendshipUserId, long receivingFriendshipUserId) {
    var sqlQuery = "INSERT INTO \"friendship\" (user_id, friend_id, is_confirmed) "
        + "VALUES (?, ?, false);";

    jdbcTemplate.update(sqlQuery, requestingFriendshipUserId, receivingFriendshipUserId);
  }

  @Override
  public void removeFriend(long requestingFriendshipEndUserId, long receivingFriendshipEndUserId) {
    var sqlQuery = "DELETE FROM \"friendship\" "
        + "WHERE (user_id = ? AND friend_id = ?) "
        + "OR (friend_id = ? AND user_id = ?)";

    jdbcTemplate.update(sqlQuery, requestingFriendshipEndUserId, receivingFriendshipEndUserId,
        requestingFriendshipEndUserId, receivingFriendshipEndUserId);
  }

  @Override
  public List<User> getCommonFriends(long firstUserId, long secondUserId) {
    var sqlQuery = "SELECT u.id, u.email, u.login, u.name, u.birthday "
        + "FROM \"friendship\" f "
        + "JOIN \"user\" u ON u.id = f.friend_id "
        + "WHERE user_id = ? "
        + "AND friend_id IN ( "
        + "   SELECT friend_id "
        + "   FROM \"friendship\" f1 "
        + "   WHERE f1.user_id = ? "
        + ")";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), firstUserId, secondUserId);
  }

  @Override
  public List<User> getFriends(long id) {
    var sqlQuery = "SELECT u.id, email, login, name, birthday "
        + "FROM \"friendship\" f "
        + "JOIN \"user\" u ON u.id = f.friend_id "
        + "WHERE f.user_id = ?";

    return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
  }

  private User makeUser(ResultSet rs) throws SQLException {
    return User.builder()
        .id(rs.getLong("id"))
        .email(rs.getString("email"))
        .login(rs.getString("login"))
        .name(rs.getString("name"))
        .birthday(rs.getDate("birthday").toLocalDate())
        .build();
  }
}
