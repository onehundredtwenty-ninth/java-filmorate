package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserStorage userStorage;
  private final UserService userService;

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

  @GetMapping(value = "/users/{id}")
  public User getUserById(@PathVariable int id) {
    return userStorage.getUserById(id);
  }

  @PutMapping(value = "/users/{id}/friends/{friendId}")
  public void addFriend(@PathVariable int id, @PathVariable int friendId) {
    userService.addFriend(id, friendId);
  }

  @DeleteMapping(value = "/users/{id}/friends/{friendId}")
  public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
    userService.removeFriend(id, friendId);
  }

  @GetMapping(value = "/users/{id}/friends")
  public List<User> getUserFriends(@PathVariable int id) {
    return userService.getFriendsInfo(userStorage.getUserById(id).getFriends());
  }

  @GetMapping(value = "/users/{id}/friends/common/{otherId}")
  public List<User> getUserCommonFriends(@PathVariable int id, @PathVariable int otherId) {
    return userService.getCommonFriends(id, otherId);
  }
}
