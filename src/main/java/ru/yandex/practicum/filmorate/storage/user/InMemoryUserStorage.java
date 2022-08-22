package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

  private long idTracker;
  private final Map<Long, User> users = new HashMap<>();

  @Override
  public User addUser(User user) {
    if (!users.containsKey(user.getId())) {
      if (user.getId() == null) {
        user.setId(++idTracker);
      }
      log.info("Added user with id: " + user.getId());
      user.setName("".equals(user.getName()) ? user.getLogin() : user.getName());
      user.setFriends(new HashSet<>());
      users.put(user.getId(), user);
      return user;
    } else {
      throw new IllegalArgumentException("User with id: " + user.getId() + " already exists");
    }
  }

  @Override
  public User updateUser(User user) {
    if (users.containsKey(user.getId())) {
      log.info("Updated user with id: " + user.getId());
      if (user.getFriends() == null) {
        user.setFriends(new HashSet<>());
      }
      users.put(user.getId(), user);
      return user;
    } else {
      throw new EntityNotFoundException("User with id: " + user.getId() + " not found");
    }
  }

  @Override
  public Collection<User> getUsers() {
    return users.values();
  }

  @Override
  public User getUserById(long id) {
    if (users.containsKey(id)) {
      return users.get(id);
    } else {
      throw new EntityNotFoundException("User with id: " + id + " not found");
    }
  }
}
