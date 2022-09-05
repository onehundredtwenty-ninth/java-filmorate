package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.film.FilmService;

@Slf4j
@RestController
public class FilmController {

  private final FilmService filmService;

  @Autowired
  public FilmController(FilmService filmService) {
    this.filmService = filmService;
  }

  @PostMapping(value = "/films")
  public Film addFilm(@Valid @RequestBody Film film) {
    return filmService.addFilm(film);
  }

  @PutMapping(value = "/films")
  public Film updateFilm(@Valid @RequestBody Film film) {
    return filmService.updateFilm(film);
  }

  @GetMapping(value = "/films")
  public Collection<Film> getFilms() {
    return filmService.getFilms();
  }

  @GetMapping(value = "/films/{id}")
  public Film getFilmById(@PathVariable int id) {
    return filmService.getFilmById(id);
  }

  @PutMapping(value = "/films/{id}/like/{userId}")
  public void addLike(@PathVariable int id, @PathVariable int userId) {
    filmService.addLike(id, userId);
  }

  @DeleteMapping(value = "/films/{id}/like/{userId}")
  public void deleteLike(@PathVariable int id, @PathVariable int userId) {
    filmService.removeLike(id, userId);
  }

  @GetMapping(value = "/films/popular")
  public Collection<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
    return filmService.getMostPopularFilms(count);
  }

  @GetMapping(value = "/mpa")
  public Collection<Mpa> getMpaList() {
    return filmService.getMpaList();
  }

  @GetMapping(value = "/mpa/{id}")
  public Mpa getMpaById(@PathVariable int id) {
    return filmService.getMpaById(id);
  }

  @GetMapping(value = "/genres")
  public Collection<Genre> getGenres() {
    return filmService.getGenres();
  }

  @GetMapping(value = "/genres/{id}")
  public Genre getGenreById(@PathVariable int id) {
    return filmService.getGenresById(id);
  }
}
