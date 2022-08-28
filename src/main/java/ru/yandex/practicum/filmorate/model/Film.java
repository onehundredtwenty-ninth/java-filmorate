package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.constraint.ReleaseDateBefore;

@Getter
@Setter
@Builder
public class Film {

  private Long id;
  @NotBlank
  private String name;
  @Size(max = 200)
  private String description;
  @Past
  @ReleaseDateBefore
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate releaseDate;
  @Positive
  private float duration;
  private Set<Long> likes;
}
