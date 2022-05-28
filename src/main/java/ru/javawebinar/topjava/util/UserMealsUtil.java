package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDayMap = new HashMap<>();
        for (UserMeal userMeal: meals) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            Integer sumCalories = sumCaloriesPerDayMap.getOrDefault(mealDate, 0);
            sumCalories += userMeal.getCalories();
            sumCaloriesPerDayMap.put(mealDate, sumCalories);
        }

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal userMeal: meals) {
            LocalTime timeMeal = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(timeMeal, startTime, endTime)) {
                Integer sumCalories = sumCaloriesPerDayMap.get(userMeal.getDateTime().toLocalDate());
                userMealWithExcessList.add(new UserMealWithExcess(
                        userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        sumCalories > caloriesPerDay));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDayMap = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                         Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal ->
                 TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(element ->
                        new UserMealWithExcess(
                        element.getDateTime(),
                        element.getDescription(),
                        element.getCalories(),
                        sumCaloriesPerDayMap.get(element.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
