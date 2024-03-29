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

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping(value = "/users")
  public User addUser(@Valid @RequestBody User user) {
    return userService.addUser(user);
  }

  @PutMapping(value = "/users")
  public User updateUser(@Valid @RequestBody User user) {
    return userService.updateUser(user);
  }

  @GetMapping(value = "/users")
  public Collection<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping(value = "/users/{id}")
  public User getUserById(@PathVariable long id) {
    return userService.getUserById(id);
  }

  @PutMapping(value = "/users/{id}/friends/{friendId}")
  public void addFriend(@PathVariable long id, @PathVariable long friendId) {
    userService.addFriend(id, friendId);
  }

  @DeleteMapping(value = "/users/{id}/friends/{friendId}")
  public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
    userService.removeFriend(id, friendId);
  }

  @GetMapping(value = "/users/{id}/friends")
  public List<User> getUserFriends(@PathVariable long id) {
    return userService.getFriends(id);
  }

  @GetMapping(value = "/users/{id}/friends/common/{otherId}")
  public List<User> getUserCommonFriends(@PathVariable long id, @PathVariable long otherId) {
    return userService.getCommonFriends(id, otherId);
  }
}
