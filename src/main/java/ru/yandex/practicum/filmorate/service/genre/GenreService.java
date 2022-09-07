package ru.yandex.practicum.filmorate.service.genre;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

@Service
public class GenreService {

  private final GenreStorage genreStorage;

  @Autowired
  public GenreService(@Qualifier("genreDbStorage") GenreStorage genreStorage) {
    this.genreStorage = genreStorage;
  }

  public Collection<Genre> getGenres() {
    return genreStorage.getGenres();
  }

  public Genre getGenresById(long genreId) {
    return genreStorage.getGenresById(genreId);
  }
}
