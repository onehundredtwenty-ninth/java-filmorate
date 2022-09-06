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
import ru.yandex.practicum.filmorate.service.genre.GenreService;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

@Slf4j
@RestController
public class FilmController {

  private final FilmService filmService;
  private final MpaService mpaService;
  private final GenreService genreService;

  @Autowired
  public FilmController(FilmService filmService, MpaService mpaService, GenreService genreService) {
    this.filmService = filmService;
    this.mpaService = mpaService;
    this.genreService = genreService;
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
  public Film getFilmById(@PathVariable long id) {
    return filmService.getFilmById(id);
  }

  @PutMapping(value = "/films/{id}/like/{userId}")
  public void addLike(@PathVariable long id, @PathVariable long userId) {
    filmService.addLike(id, userId);
  }

  @DeleteMapping(value = "/films/{id}/like/{userId}")
  public void deleteLike(@PathVariable long id, @PathVariable long userId) {
    filmService.removeLike(id, userId);
  }

  @GetMapping(value = "/films/popular")
  public Collection<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
    return filmService.getMostPopularFilms(count);
  }

  @GetMapping(value = "/mpa")
  public Collection<Mpa> getMpaList() {
    return mpaService.getMpaList();
  }

  @GetMapping(value = "/mpa/{id}")
  public Mpa getMpaById(@PathVariable long id) {
    return mpaService.getMpaById(id);
  }

  @GetMapping(value = "/genres")
  public Collection<Genre> getGenres() {
    return genreService.getGenres();
  }

  @GetMapping(value = "/genres/{id}")
  public Genre getGenreById(@PathVariable long id) {
    return genreService.getGenresById(id);
  }
}
