package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

  private final FilmStorage filmStorage;
  private final FilmService filmService;

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

  @GetMapping(value = "/films/{id}")
  public Film getFilmById(@PathVariable int id) {
    return filmStorage.getFilmById(id);
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
}
