package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User("Name", "e@mail.com", "pwd", Role.USER,  Role.USER),
            new User("User", "user@yandex.ru", "000", Role.USER,  Role.USER),
            new User("Other", "other@gmail.com", "user", Role.USER,  Role.USER),
            new User("Admin", "admin@mail.com", "admin", Role.ADMIN, Role.USER)
    );
}
