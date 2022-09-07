package ru.yandex.practicum.filmorate.service.mpa;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

@Service
public class MpaService {

  private final MpaStorage mpaStorage;

  @Autowired
  public MpaService(@Qualifier("mpaDbStorage") MpaStorage mpaStorage) {
    this.mpaStorage = mpaStorage;
  }

  public Collection<Mpa> getMpaList() {
    return mpaStorage.getMpaList();
  }

  public Mpa getMpaById(long mpaId) {
    return mpaStorage.getMpaById(mpaId);
  }
}
