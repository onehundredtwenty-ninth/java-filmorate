package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@RestController
public class UserController {

  private int idTracker;
  private final Map<Integer, User> users = new HashMap<>();

  @PostMapping(value = "/users")
  public User addUser(@Valid @RequestBody User user) {
    if (user.getId() == null) {
      user.setId(++idTracker);
      log.info("Added user with id: " + user.getId());
      user.setName("".equals(user.getName()) ? user.getLogin() : user.getName());
      users.put(user.getId(), user);
      return user;
    } else {
      throw new IllegalArgumentException("User with id: " + user.getId() + " already exists");
    }
  }

  @PutMapping(value = "/users")
  public User updateUser(@Valid @RequestBody User user) {
    if (users.containsKey(user.getId())) {
      log.info("Updated user with id: " + user.getId());
      users.put(user.getId(), user);
      return user;
    } else {
      throw new IllegalArgumentException("User with id: " + user.getId() + " not found");
    }
  }

  @GetMapping(value = "/users")
  public Collection<User> getUsers() {
    return users.values();
  }
}
