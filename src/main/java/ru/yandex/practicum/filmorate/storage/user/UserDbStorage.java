package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import java.util.List;
import ru.yandex.practicum.filmorate.model.User;

public class UserDbStorage implements UserStorage {

  @Override
  public User addUser(User user) {
    return null;
  }

  @Override
  public User updateUser(User user) {
    return null;
  }

  @Override
  public Collection<User> getUsers() {
    return null;
  }

  @Override
  public User getUserById(long id) {
    return null;
  }

  @Override
  public void addFriend(long requestingFriendshipUserId, long receivingFriendshipUserId) {

  }

  @Override
  public void removeFriend(long requestingFriendshipEndUserId, long receivingFriendshipEndUserId) {

  }

  @Override
  public List<User> getCommonFriends(long firstUserId, long secondUserId) {
    return null;
  }

  @Override
  public List<User> getFriends(int id) {
    return null;
  }
}
