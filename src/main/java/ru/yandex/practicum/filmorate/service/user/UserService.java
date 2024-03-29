package ru.yandex.practicum.filmorate.service.user;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class UserService {

  private final UserStorage userStorage;

  @Autowired
  public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
    this.userStorage = userStorage;
  }

  public void addFriend(long requestingFriendshipUserId, long receivingFriendshipUserId) {
    userStorage.getUserById(requestingFriendshipUserId);
    userStorage.getUserById(receivingFriendshipUserId);

    userStorage.addFriend(requestingFriendshipUserId, receivingFriendshipUserId);
  }

  public void removeFriend(long requestingFriendshipEndUserId, long receivingFriendshipEndUserId) {
    userStorage.removeFriend(requestingFriendshipEndUserId, receivingFriendshipEndUserId);
  }

  public List<User> getCommonFriends(long firstUserId, long secondUserId) {
    return userStorage.getCommonFriends(firstUserId, secondUserId);
  }

  public User addUser(User user) {
    if ("".equals(user.getName())) {
      user.setName(user.getLogin());
    }
    return userStorage.addUser(user);
  }

  public User updateUser(User user) {
    return userStorage.updateUser(user);
  }

  public Collection<User> getUsers() {
    return userStorage.getUsers();
  }

  public User getUserById(long id) {
    return userStorage.getUserById(id);
  }

  public List<User> getFriends(long id) {
    return userStorage.getFriends(id);
  }
}
