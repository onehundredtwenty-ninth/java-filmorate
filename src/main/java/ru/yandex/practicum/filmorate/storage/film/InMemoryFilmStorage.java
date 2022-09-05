package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

  private long idTracker;
  private final Map<Long, Film> films = new HashMap<>();

  @Override
  public Film addFilm(Film film) {
    if (!films.containsKey(film.getId())) {
      if (film.getId() == null) {
        film.setId(++idTracker);
      }
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

  @Override
  public Collection<Film> getMostPopularFilms(int count) {
    return films.values().stream()
        .sorted(Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed())
        .limit(count)
        .collect(Collectors.toList());
  }

  public void addLike(long filmId, long userId) {
    getFilmById(filmId).getLikes().add(userId);
  }

  public void removeLike(long filmId, long userId) {
    var fimLikes = getFilmById(filmId).getLikes();
    if (fimLikes.contains(userId)) {
      fimLikes.remove(userId);
    } else {
      throw new EntityNotFoundException("Like with id: " + userId + " not found");
    }
  }

  @Override
  public Collection<Mpa> getMpaList() {
    return null;
  }

  @Override
  public Mpa getMpaById(long mpaId) {
    return null;
  }

  @Override
  public Collection<Genre> getGenres() {
    return null;
  }

  @Override
  public Genre getGenresById(long genreId) {
    return null;
  }
}
