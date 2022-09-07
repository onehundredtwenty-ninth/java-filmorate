package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {

  private final GenreStorage genreStorage;

  @Test
  void getGenresList() {
    var genres = genreStorage.getGenres();

    genres = new ArrayList<>(genres);

    assertThat(genres.size())
        .isEqualTo(6);
  }

  @Test
  void getGenreById() {
    var mpa = genreStorage.getGenresById(1);

    assertThat(mpa.getId())
        .isEqualTo(1);

    assertThat(mpa.getName())
        .isEqualTo("Комедия");
  }
}
