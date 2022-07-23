package ru.yandex.practicum.filmorate.constraint;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class FilmReleaseDateValidator implements ConstraintValidator<ReleaseDateBefore, LocalDate> {

  @Override
  public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
    return localDate.isAfter(LocalDate.of(1895, 12, 28));
  }
}
