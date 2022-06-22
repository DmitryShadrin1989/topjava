package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actualMeal = service.get(MealTestData.MEAL_ID_1, UserTestData.USER_ID);
        MealTestData.assertMatch(actualMeal, MealTestData.meal1);
    }

    @Test
    public void delete() {
        service.delete(MealTestData.MEAL_ID_2, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL_ID_2, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        MealTestData.assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.JANUARY, 31),
                        LocalDate.of(2020, Month.JANUARY, 31), UserTestData.USER_ID),
                MealTestData.meal4, MealTestData.meal5, MealTestData.meal6, MealTestData.meal7);
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(UserTestData.USER_ID);
        MealTestData.assertMatch(actualMeals, MealTestData.meals);
    }

    @Test
    public void update() {
        Meal updateMeal = MealTestData.getUpdated();
        service.update(updateMeal, UserTestData.USER_ID);
        MealTestData.assertMatch(service.get(MealTestData.MEAL_ID_3, UserTestData.USER_ID), updateMeal);
    }

    @Test
    public void create() {
        Meal expectedMeal = MealTestData.getNew();
        Meal actualMeal = service.create(expectedMeal, UserTestData.USER_ID);
        int newActualMealId = actualMeal.getId();
        expectedMeal.setId(newActualMealId);
        MealTestData.assertMatch(actualMeal, expectedMeal);
    }
}