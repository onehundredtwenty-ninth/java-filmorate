package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

public interface FilmStorage {

  Film addFilm(Film film);

  Film updateFilm(Film film);

  Collection<Film> getFilms();

  Film getFilmById(long filmId);

  Collection<Film> getMostPopularFilms(int count);

  void addLike(long filmId, long userId);

  void removeLike(long filmId, long userId);

  Collection<Mpa> getMpaList();

  Mpa getMpaById(long mpaId);

  Collection<Genre> getGenres();

  Genre getGenresById(long genreId);
}
