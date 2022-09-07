package ru.yandex.practicum.filmorate.storage.genre;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

public interface GenreStorage {

  Collection<Genre> getGenres();

  Genre getGenresById(long genreId);
}
