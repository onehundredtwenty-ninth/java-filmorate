package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

  User addUser(User user);

  User updateUser(User user);

  Collection<User> getUsers();
}
