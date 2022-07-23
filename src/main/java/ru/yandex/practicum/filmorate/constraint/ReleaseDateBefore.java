package ru.yandex.practicum.filmorate.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FilmReleaseDateValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface ReleaseDateBefore {

  String message() default "Release date must be after 28.12.1895";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
