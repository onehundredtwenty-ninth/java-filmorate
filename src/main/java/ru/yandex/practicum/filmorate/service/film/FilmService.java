package ru.yandex.practicum.filmorate.service.film;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Service
public class FilmService {

  private final FilmStorage filmStorage;

  @Autowired
  public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
    this.filmStorage = filmStorage;
  }

  public void addLike(long filmId, long userId) {
    filmStorage.addLike(filmId, userId);
  }

  public void removeLike(long filmId, long userId) {
    filmStorage.removeLike(filmId, userId);
  }

  public Collection<Film> getMostPopularFilms(int count) {
    return filmStorage.getMostPopularFilms(count);
  }

  public Film addFilm(Film film) {
    return filmStorage.addFilm(film);
  }

  public Film updateFilm(Film film) {
    return filmStorage.updateFilm(film);
  }

  public Collection<Film> getFilms() {
    return filmStorage.getFilms();
  }

  public Film getFilmById(long filmId) {
    return filmStorage.getFilmById(filmId);
  }
}
