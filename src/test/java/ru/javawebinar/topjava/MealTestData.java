package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_1 = START_SEQ + 2;
    public static final int MEAL_ID_2 = MEAL_ID_1+2;
    public static final int MEAL_ID_3 = MEAL_ID_1+3;
    public static final int MEAL_ID_4 = MEAL_ID_1+4;
    public static final int MEAL_ID_5 = MEAL_ID_1+5;
    public static final int MEAL_ID_6 = MEAL_ID_1+6;
    public static final int MEAL_ID_7 = MEAL_ID_1+7;
    public static final Meal meal1 = new Meal(MEAL_ID_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID_2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID_3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_ID_4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL_ID_5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(MEAL_ID_6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(MEAL_ID_7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510);

    public static final List<Meal> meals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 9, 30), "Новый завтрак", 300);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID_3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 30), "Новый сытный ужин ", 600);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
