package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

  private Integer id;
  @Email
  private String email;
  @Pattern(regexp = "^\\S*$")
  @NotEmpty
  private String login;
  private String name;
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Past
  private LocalDate birthday;
  private Set<Integer> friends;
}
