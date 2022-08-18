package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

  Film addFilm(Film film);

  Film updateFilm(Film film);

  Collection<Film> getFilms();

  Film getFilmById(int filmId);
}
