package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDbStorageTest {

  private final UserDbStorage userDbStorage;

  @Test
  @Order(0)
  void addUserTest() {
    var user = userDbStorage.addUser(buildRandomUserObject());

    assertThat(user.getId())
        .isEqualTo(1);
  }

  @Test
  @Order(1)
  void updateUserTest() {
    var user = buildRandomUserObject();
    userDbStorage.addUser(user);
    user.setName("testUserLoginUpdate");
    var updatedUser = userDbStorage.updateUser(user);

    assertThat(updatedUser.getName())
        .isEqualTo("testUserLoginUpdate");
  }

  @Test
  @Order(2)
  void getUsersTest() {
    userDbStorage.addUser(buildRandomUserObject());
    userDbStorage.addUser(buildRandomUserObject());

    var users = userDbStorage.getUsers();

    assertThat(users.size())
        .isEqualTo(4);
  }

  @Test
  @Order(3)
  void getUserByIdTest() {
    var user = userDbStorage.addUser(buildRandomUserObject());
    userDbStorage.addUser(buildRandomUserObject());

    var userFromDb = userDbStorage.getUserById(user.getId());

    assertThat(user.getId())
        .isEqualTo(userFromDb.getId());
  }

  @Test
  @Order(4)
  void addFriendTest() {
    var user = userDbStorage.addUser(buildRandomUserObject());
    var friend = userDbStorage.addUser(buildRandomUserObject());

    userDbStorage.addFriend(user.getId(), friend.getId());

    var userFromDb = userDbStorage.getFriends(user.getId());

    assertThat(userFromDb.size())
        .isEqualTo(1);
  }

  @Test
  @Order(5)
  void removeFriendTest() {
    var user = userDbStorage.addUser(buildRandomUserObject());
    var friend = userDbStorage.addUser(buildRandomUserObject());

    userDbStorage.addFriend(user.getId(), friend.getId());
    userDbStorage.removeFriend(user.getId(), friend.getId());

    var userFromDb = userDbStorage.getFriends(user.getId());

    assertThat(userFromDb.size())
        .isEqualTo(0);
  }

  @Test
  @Order(6)
  void getFriendsTest() {
    var user = userDbStorage.addUser(buildRandomUserObject());
    var friend = userDbStorage.addUser(buildRandomUserObject());

    userDbStorage.addFriend(user.getId(), friend.getId());

    var userFromDb = userDbStorage.getFriends(user.getId());

    assertThat(userFromDb.size())
        .isEqualTo(1);
  }

  @Test
  @Order(7)
  void getCommonFriendsTest() {
    var user = userDbStorage.addUser(buildRandomUserObject());
    var friend = userDbStorage.addUser(buildRandomUserObject());
    var anotherFriend = userDbStorage.addUser(buildRandomUserObject());

    userDbStorage.addFriend(user.getId(), friend.getId());
    userDbStorage.addFriend(anotherFriend.getId(), friend.getId());

    var userFromDb = userDbStorage.getCommonFriends(user.getId(), anotherFriend.getId());

    assertThat(userFromDb.size())
        .isEqualTo(1);
  }

  private User buildRandomUserObject() {
    return User.builder()
        .email("testUserEmail@mail.com")
        .login("testUserLogin")
        .name("testUserName")
        .birthday(LocalDate.now().minusYears(25))
        .build();
  }
}
