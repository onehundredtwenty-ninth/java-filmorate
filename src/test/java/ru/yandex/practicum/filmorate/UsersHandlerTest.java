package ru.yandex.practicum.filmorate;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

@SpringBootTest
class UsersHandlerTest {

  @BeforeAll
  static void contextLoads() {
    SpringApplication.run(FilmorateApplication.class, "");
  }

  @Test
  void createUserTest() {
    User user = User.builder()
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void createUserWithIncorrectLoginTest() {
    User user = User.builder()
        .login("dolore ullamco")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void createUserWithIncorrectEmailTest() {
    User user = User.builder()
        .login("dolore")
        .name("Nick Name")
        .email("mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void createUserWithIncorrectBirthdayTest() {
    User user = User.builder()
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("2446-08-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void updateUserTest() {
    User userToCreate = User.builder()
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userToCreate)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User user = User.builder()
        .id(1L)
        .login("doloreUpdate")
        .name("est adipisicing")
        .email("mail@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .put()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void updateUnknownUserTest() {
    User userToCreate = User.builder()
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userToCreate)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User user = User.builder()
        .id(-1L)
        .login("doloreUpdate")
        .name("est adipisicing")
        .email("mail@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .put()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(404, response.getStatusCode());
  }

  @Test
  void getAllUsersTest() {
    User userToCreate = User.builder()
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userToCreate)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .contentType(ContentType.JSON)
        .get()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void createUserWithEmptyNameTest() {
    User user = User.builder()
        .login("dolore")
        .name("")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void addFriendTest() {
    User user = User.builder()
        .id(1L)
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User userFriend = User.builder()
        .id(2L)
        .login("doloreFriend")
        .name("est adipisicing")
        .email("mail-friend@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userFriend)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/{friendId}")
        .contentType(ContentType.JSON)
        .put("", 1L, 2L)
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void deleteFriendTest() {
    User user = User.builder()
        .id(1L)
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User userFriend = User.builder()
        .id(2L)
        .login("doloreFriend")
        .name("est adipisicing")
        .email("mail-friend@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userFriend)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/{friendId}")
        .contentType(ContentType.JSON)
        .put("", user.getId(), userFriend.getId())
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/{friendId}")
        .contentType(ContentType.JSON)
        .delete("", user.getId(), userFriend.getId())
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void getFriendsTest() {
    User user = User.builder()
        .id(1L)
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User userFriend = User.builder()
        .id(2L)
        .login("doloreFriend")
        .name("est adipisicing")
        .email("mail-friend@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userFriend)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/{friendId}")
        .contentType(ContentType.JSON)
        .put("", user.getId(), userFriend.getId())
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends")
        .contentType(ContentType.JSON)
        .get("", user.getId())
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void getCommonFriendsTest() {
    User user = User.builder()
        .id(1L)
        .login("dolore")
        .name("Nick Name")
        .email("mail@mail.ru")
        .birthday(LocalDate.parse("1946-08-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(user)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User userFriend = User.builder()
        .id(2L)
        .login("doloreFriend")
        .name("est adipisicing")
        .email("mail-friend@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userFriend)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    User userCommonFriend = User.builder()
        .id(3L)
        .login("doloreCommonFriend")
        .name("est adipisicing")
        .email("mail-common-friend@yandex.ru")
        .birthday(LocalDate.parse("1976-09-20"))
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users")
        .body(userCommonFriend)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/{friendId}")
        .contentType(ContentType.JSON)
        .put("", user.getId(), userCommonFriend.getId())
        .then()
        .log().all()
        .extract();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/{friendId}")
        .contentType(ContentType.JSON)
        .put("", userFriend.getId(), userCommonFriend.getId())
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/users/{id}/friends/common/{otherId}")
        .contentType(ContentType.JSON)
        .get("", user.getId(), userCommonFriend.getId())
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }
}
