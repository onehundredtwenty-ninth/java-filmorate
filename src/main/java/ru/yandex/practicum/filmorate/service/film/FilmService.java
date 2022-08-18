package ru.yandex.practicum.filmorate.service.film;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Service
public class FilmService {

  private final FilmStorage filmStorage;

  @Autowired
  public FilmService(FilmStorage filmStorage) {
    this.filmStorage = filmStorage;
  }

  public void addLike(int filmId, int userId) {
    filmStorage.getFilmById(filmId).getLikes().add(userId);
  }

  public void removeLike(int filmId, int userId) {
    filmStorage.getFilmById(filmId).getLikes().remove(userId);
  }

  public Collection<Film> getTenMostPopularFilms() {
    return filmStorage.getFilms().stream()
        .sorted(Comparator.comparingInt(s -> s.getLikes().size()))
        .limit(10)
        .collect(Collectors.toList());
  }
}
