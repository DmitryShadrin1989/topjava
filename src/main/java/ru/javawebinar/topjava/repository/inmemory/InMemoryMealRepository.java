package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository{
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);

        synchronized (repository) {
            meal.setUserId(userId);
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            Meal currentMeal = repository.get(meal.getId());
            return isNotUsersMeal(currentMeal, userId) ? null :
                    repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);

        synchronized (repository) {
            Meal meal = repository.get(id);
            return !isNotUsersMeal(meal, userId) && repository.remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);

        synchronized (repository) {
            Meal meal = repository.get(id);
            return isNotUsersMeal(meal, userId) ? null : meal;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");

        synchronized (repository) {
            return repository.values().stream()
                    .filter(meal -> meal.getUserId() == userId)
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
            }
    }

    @Override
    public List<Meal> getAllBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.values().stream()
                .filter(meal -> (meal.getUserId() == userId
                        && (startDate.compareTo(meal.getDate())<=0 && endDate.compareTo(meal.getDate())>=0)))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    private boolean isNotUsersMeal(Meal meal, int userId) {
        return meal != null && meal.getUserId() != userId;
    }
}

