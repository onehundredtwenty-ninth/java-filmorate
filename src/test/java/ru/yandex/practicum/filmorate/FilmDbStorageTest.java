package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {

  private final FilmDbStorage filmDbStorage;
  private final UserDbStorage userDbStorage;

  @Test
  void addFilmTest() {
    var film = filmDbStorage.addFilm(buildRandomFilmObject());

    assertThat(film.getId())
        .isEqualTo(1);
  }

  @Test
  void updateFilmTest() {
    var film = buildRandomFilmObject();
    filmDbStorage.addFilm(film);
    film.setDescription("testFilmDescriptionUpdate");
    var updatedFilm = filmDbStorage.updateFilm(film);

    assertThat(updatedFilm.getDescription())
        .isEqualTo("testFilmDescriptionUpdate");
  }

  @Test
  void getFilmsTest() {
    filmDbStorage.addFilm(buildRandomFilmObject());
    filmDbStorage.addFilm(buildRandomFilmObject());

    var films = filmDbStorage.getFilms();

    assertThat(films.size())
        .isEqualTo(3);
  }

  @Test
  void getFilmByIdTest() {
    var film = filmDbStorage.addFilm(buildRandomFilmObject());
    filmDbStorage.addFilm(buildRandomFilmObject());

    var filmFromDb = filmDbStorage.getFilmById(film.getId());

    assertThat(film.getId())
        .isEqualTo(filmFromDb.getId());
  }

  @Test
  void getMostPopularFilmsTest() {
    var mostPopularFilm = filmDbStorage.addFilm(buildRandomFilmObject());
    filmDbStorage.addFilm(buildRandomFilmObject());
    filmDbStorage.addFilm(buildRandomFilmObject());
    filmDbStorage.addFilm(buildRandomFilmObject());
    filmDbStorage.addFilm(buildRandomFilmObject());

    var user = userDbStorage.addUser(buildRandomUserObject());
    filmDbStorage.addLike(mostPopularFilm.getId(), user.getId());

    var mostPopularFilms = filmDbStorage.getMostPopularFilms(3);

    assertThat(mostPopularFilms.size())
        .isEqualTo(3);

    assertThat(new ArrayList<>(mostPopularFilms).get(0).getId())
        .isEqualTo(mostPopularFilm.getId());
  }

  @Test
  void addLikeTest() {
    var film = filmDbStorage.addFilm(buildRandomFilmObject());

    var user = userDbStorage.addUser(buildRandomUserObject());
    filmDbStorage.addLike(film.getId(), user.getId());

    film = filmDbStorage.getFilmById(film.getId());

    assertThat(film.getLikes().size())
        .isEqualTo(1);
  }

  @Test
  void removeLikeTest() {
    var film = filmDbStorage.addFilm(buildRandomFilmObject());

    var user = userDbStorage.addUser(buildRandomUserObject());
    filmDbStorage.addLike(film.getId(), user.getId());
    filmDbStorage.removeLike(film.getId(), user.getId());

    film = filmDbStorage.getFilmById(film.getId());

    assertThat(film.getLikes().size())
        .isEqualTo(0);
  }

  private Film buildRandomFilmObject() {
    return Film.builder()
        .name("testFilmName")
        .description("testFilmDescription")
        .releaseDate(LocalDate.now().minusYears(5))
        .duration(190)
        .mpa(new Mpa(1L, "PG"))
        .genres(Set.of(new Genre(1L, "Комедия")))
        .build();
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
