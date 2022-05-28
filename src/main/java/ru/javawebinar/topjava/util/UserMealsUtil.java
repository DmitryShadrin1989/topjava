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

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<LocalDate, Integer> sumCaloriesPerDayMap = new HashMap<>();

        // Сначала проходим по переданной коллекции чтобы накопить в Map каллории за день
        for (UserMeal userMeal: meals) {
            LocalDate dateMeal = userMeal.getDateTime().toLocalDate();
            Integer sumCalories = sumCaloriesPerDayMap.getOrDefault(dateMeal, 0);
            sumCalories += userMeal.getCalories();
            sumCaloriesPerDayMap.put(dateMeal, sumCalories);
        }

        // Проходим по переданной коллекции и заполняем (с учетом фильра по времени) результирующую коллекцию
        for (UserMeal userMeal: meals) {
            LocalTime timeMeal = userMeal.getDateTime().toLocalTime();
            if (timeMeal.compareTo(startTime) == 0
                    || TimeUtil.isBetweenHalfOpen(timeMeal, startTime, endTime)) {

                LocalDate dateMeal = userMeal.getDateTime().toLocalDate();
                Integer sumCalories = sumCaloriesPerDayMap.get(dateMeal);

                userMealWithExcessList.add(new UserMealWithExcess(
                        userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        sumCalories > caloriesPerDay));
            }

        }

        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // Сначала проходим по переданной коллекции чтобы накопить в Map каллории за день
        Map<LocalDate, Integer> sumCaloriesPerDayMap = meals.stream()
                .collect(Collectors.groupingBy(element -> element.getDateTime().toLocalDate(),
                        Collectors.summingInt(element -> element.getCalories())));

        // Проходим по переданной коллекции и заполняем (с учетом фильра по времени) результирующую коллекцию
        List<UserMealWithExcess> userMealWithExcessList = meals.stream().filter(element
                -> element.getDateTime().toLocalTime().compareTo(startTime) == 0
        || TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime))
                .map(element -> new UserMealWithExcess(
                        element.getDateTime(),
                        element.getDescription(),
                        element.getCalories(),
                        sumCaloriesPerDayMap.get(element.getDateTime().toLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());

        return userMealWithExcessList;
    }
}
