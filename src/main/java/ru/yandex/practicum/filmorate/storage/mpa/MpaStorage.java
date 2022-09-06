package ru.yandex.practicum.filmorate.storage.mpa;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Mpa;

public interface MpaStorage {

  Collection<Mpa> getMpaList();

  Mpa getMpaById(long mpaId);
}
