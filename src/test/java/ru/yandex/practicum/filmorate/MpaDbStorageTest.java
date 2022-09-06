package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorageTest {

  private final MpaStorage mpaStorage;

  @Test
  void getMpaList() {
    var mpaList = mpaStorage.getMpaList();

    mpaList = new ArrayList<>(mpaList);

    assertThat(mpaList.size())
        .isEqualTo(5);
  }

  @Test
  void getMpaById() {
    var mpa = mpaStorage.getMpaById(1);

    assertThat(mpa.getId())
        .isEqualTo(1);

    assertThat(mpa.getName())
        .isEqualTo("G");
  }
}
