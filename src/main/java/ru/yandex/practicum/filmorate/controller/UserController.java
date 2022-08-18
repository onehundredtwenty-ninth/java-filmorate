package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Slf4j
@RestController
public class UserController {

  private final UserStorage userStorage;

  @Autowired
  public UserController(UserStorage userStorage) {
    this.userStorage = userStorage;
  }

  @PostMapping(value = "/users")
  public User addUser(@Valid @RequestBody User user) {
    return userStorage.addUser(user);
  }

  @PutMapping(value = "/users")
  public User updateUser(@Valid @RequestBody User user) {
    return userStorage.updateUser(user);
  }

  @GetMapping(value = "/users")
  public Collection<User> getUsers() {
    return userStorage.getUsers();
  }
}
