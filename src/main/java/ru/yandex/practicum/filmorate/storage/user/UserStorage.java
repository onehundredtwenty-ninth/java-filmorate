package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import java.util.List;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

  User addUser(User user);

  User updateUser(User user);

  Collection<User> getUsers();

  User getUserById(long id);

  void addFriend(long requestingFriendshipUserId, long receivingFriendshipUserId);

  void removeFriend(long requestingFriendshipEndUserId, long receivingFriendshipEndUserId);

  List<User> getCommonFriends(long firstUserId, long secondUserId);

  List<User> getFriends(int id);
}
