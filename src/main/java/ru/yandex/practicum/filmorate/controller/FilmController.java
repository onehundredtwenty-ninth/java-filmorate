package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
@RestController
public class FilmController {

  private static int idTracker;
  private final Map<Integer, Film> films = new HashMap<>();

  @PostMapping(value = "/films")
  public Film addFilm(@Valid @RequestBody Film film) {
    if (film.getId() == null) {
      film.setId(++idTracker);
      log.info("Added film with id: " + film.getId());
      films.put(film.getId(), film);
      return film;
    } else {
      throw new IllegalArgumentException("Film must not contain id");
    }
  }

  @PutMapping(value = "/films")
  public Film updateFilm(@Valid @RequestBody Film film) {
    if (films.containsKey(film.getId())) {
      log.info("Updated film with id: " + film.getId());
      films.put(film.getId(), film);
      return film;
    } else {
      throw new IllegalArgumentException("Film with id: " + film.getId() + " not found");
    }
  }

  @GetMapping(value = "/films")
  public Collection<Film> getFilms() {
    return films.values();
  }
}
