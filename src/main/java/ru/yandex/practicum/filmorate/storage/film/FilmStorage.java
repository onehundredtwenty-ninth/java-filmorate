package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

  Film addFilm(Film film);

  Film updateFilm(Film film);

  Collection<Film> getFilms();

  Film getFilmById(long filmId);

  Collection<Film> getMostPopularFilms(int count);

  void addLike(long filmId, long userId);

  void removeLike(long filmId, long userId);
}
