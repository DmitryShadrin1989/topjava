package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> users = Arrays.asList(
            new User(null, "Dmitry", "dmitry@mail.com", "111", Role.USER),
            new User(null, "Vasily", "vasily@mail.com", "222", Role.USER),
            new User(null, "Ekaterina", "ekaterina@mail.com", "333", Role.USER),
            new User(null, "Mariya", "mariya@mail.com", "111", Role.USER),
            new User(null, "Alexandr", "alexandr@mail.com", "111", Role.USER)
    );
}
