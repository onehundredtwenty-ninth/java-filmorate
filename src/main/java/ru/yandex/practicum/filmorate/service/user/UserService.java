package ru.yandex.practicum.filmorate.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class UserService {

  private final UserStorage userStorage;

  @Autowired
  public UserService(UserStorage userStorage) {
    this.userStorage = userStorage;
  }

  public void addFriend(int requestingFriendshipUserId, int receivingFriendshipUserId) {
    User requestingFriendshipUser = userStorage.getUserById(requestingFriendshipUserId);
    User receivingFriendshipUser = userStorage.getUserById(receivingFriendshipUserId);

    requestingFriendshipUser.getFriends().add(receivingFriendshipUserId);
    receivingFriendshipUser.getFriends().add(requestingFriendshipUserId);
  }

  public void removeFriend(int requestingFriendshipEndUserId, int receivingFriendshipEndUserId) {
    User requestingFriendshipEndUser = userStorage.getUserById(requestingFriendshipEndUserId);
    User receivingFriendshipEndUser = userStorage.getUserById(receivingFriendshipEndUserId);

    requestingFriendshipEndUser.getFriends().remove(receivingFriendshipEndUserId);
    receivingFriendshipEndUser.getFriends().remove(requestingFriendshipEndUserId);
  }

  public List<User> getFriendsInfo(Set<Integer> usersIds) {
    List<User> friendsInfo = new ArrayList<>();
    for (Integer userId : usersIds) {
      friendsInfo.add(userStorage.getUserById(userId));
    }
    return friendsInfo;
  }

  public List<User> getCommonFriends(int firstUserId, int secondUserId) {
    return getFriendsInfo(findCommonElements(
        userStorage.getUserById(firstUserId).getFriends(),
        userStorage.getUserById(secondUserId).getFriends()));
  }

  private <T> Set<T> findCommonElements(Set<T> first, Set<T> second) {
    return first.stream().filter(second::contains).collect(Collectors.toSet());
  }
}
