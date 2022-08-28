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
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

@SpringBootTest
class FilmsHandlerTest {

  @BeforeAll
  static void contextLoads() {
    SpringApplication.run(FilmorateApplication.class, "");
  }

  @Test
  void createFilmTest() {
    Film film = Film.builder()
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void createFilmWithIncorrectNameTest() {
    Film film = Film.builder()
        .name("")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void createFilmWithIncorrectDescriptionTest() {
    Film film = Film.builder()
        .name("nisi eiusmod")
        .description("\"Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать "
            + "господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время "
            + "«своего отсутствия», стал кандидатом Коломбани.\"")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void createFilmWithIncorrectReleaseDateTest() {
    Film film = Film.builder()
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1890-03-25"))
        .duration(100)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void createFilmWithIncorrectDurationTest() {
    Film film = Film.builder()
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(-100)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(400, response.getStatusCode());
  }

  @Test
  void updateFilmTest() {
    Film filmToCreate = Film.builder()
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(filmToCreate)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    Film film = Film.builder()
        .id(1L)
        .name("Film Updated")
        .description("New film update decription")
        .releaseDate(LocalDate.parse("1989-04-17"))
        .duration(190)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .put()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void updateUnknownFilmTest() {
    Film filmToCreate = Film.builder()
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(filmToCreate)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    Film film = Film.builder()
        .id(-1L)
        .name("Film Updated")
        .description("New film update decription")
        .releaseDate(LocalDate.parse("1989-04-17"))
        .duration(190)
        .build();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .put()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(500, response.getStatusCode());
  }

  @Test
  void getAllFilmsTest() {
    Film filmToCreate = Film.builder()
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(filmToCreate)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .contentType(ContentType.JSON)
        .get()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void addLikeToFilmTest() {
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

    Film film = Film.builder()
        .id(1L)
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films/{id}/like/{userId}")
        .contentType(ContentType.JSON)
        .put("", film.getId(), user.getId())
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void deleteLikeFromFilmTest() {
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

    Film film = Film.builder()
        .id(1L)
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films/{id}/like/{userId}")
        .contentType(ContentType.JSON)
        .put("", film.getId(), user.getId())
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films/{id}/like/{userId}")
        .contentType(ContentType.JSON)
        .delete("", film.getId(), user.getId())
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void getMostPopularFilmTest() {
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

    Film film = Film.builder()
        .id(1L)
        .name("nisi eiusmod")
        .description("adipisicing")
        .releaseDate(LocalDate.parse("1967-03-25"))
        .duration(100)
        .build();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films")
        .body(film)
        .contentType(ContentType.JSON)
        .post()
        .then()
        .log().all()
        .extract();

    given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films/{id}/like/{userId}")
        .contentType(ContentType.JSON)
        .put("", film.getId(), user.getId())
        .then()
        .log().all()
        .extract();

    Response response = given()
        .log().all()
        .baseUri("http://localhost:8080")
        .basePath("/films/popular")
        .contentType(ContentType.JSON)
        .queryParam("count", 1)
        .get()
        .then()
        .log().all()
        .extract()
        .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }
}
