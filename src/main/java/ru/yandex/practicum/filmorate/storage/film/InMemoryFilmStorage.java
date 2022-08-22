package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

  private long idTracker;
  private final Map<Long, Film> films = new HashMap<>();

  @Override
  public Film addFilm(Film film) {
    if (film.getId() == null) {
      film.setId(++idTracker);
      log.info("Added film with id: " + film.getId());
      film.setLikes(new HashSet<>());
      films.put(film.getId(), film);
      return film;
    } else {
      throw new IllegalArgumentException("Film must not contain id");
    }
  }

  @Override
  public Film updateFilm(Film film) {
    if (films.containsKey(film.getId())) {
      log.info("Updated film with id: " + film.getId());
      if (film.getLikes() == null) {
        film.setLikes(new HashSet<>());
      } else {
        film.setLikes(films.get(film.getId()).getLikes());
      }
      films.put(film.getId(), film);
      return film;
    } else {
      throw new EntityNotFoundException("Film with id: " + film.getId() + " not found");
    }
  }

  @Override
  public Collection<Film> getFilms() {
    return films.values();
  }

  @Override
  public Film getFilmById(long filmId) {
    if (films.containsKey(filmId)) {
      return films.get(filmId);
    } else {
      throw new EntityNotFoundException("Film with id: " + filmId + " not found");
    }
  }
}
