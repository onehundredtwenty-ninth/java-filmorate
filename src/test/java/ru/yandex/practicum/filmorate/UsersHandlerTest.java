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
        .id(1)
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
        .id(-1)
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

    Assertions.assertEquals(500, response.getStatusCode());
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
}
