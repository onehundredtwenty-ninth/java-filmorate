package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Slf4j
@RestController
public class FilmController {

  private final FilmStorage filmStorage;

  @Autowired
  public FilmController(FilmStorage filmStorage) {
    this.filmStorage = filmStorage;
  }

  @PostMapping(value = "/films")
  public Film addFilm(@Valid @RequestBody Film film) {
    return filmStorage.addFilm(film);
  }

  @PutMapping(value = "/films")
  public Film updateFilm(@Valid @RequestBody Film film) {
    return filmStorage.updateFilm(film);
  }

  @GetMapping(value = "/films")
  public Collection<Film> getFilms() {
    return filmStorage.getFilms();
  }
}
