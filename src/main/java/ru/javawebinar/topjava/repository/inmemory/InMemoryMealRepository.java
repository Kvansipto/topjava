package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        MealsUtil.meals.stream()
                .map(meal -> new Meal(meal.getDateTime(), "2-" + meal.getDescription(), meal.getCalories()))
                .forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (!isAllowed(repository.get(meal.getId()), userId)) {
            return null;
        }
        Integer mealId = meal.getId();
        meal = new Meal(mealId, meal.getCalories(), meal.getDateTime(), meal.getDescription(), userId);
        return repository.put(mealId, meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return isAllowed(repository.get(id), userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return isAllowed(meal, userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return allowedSortedMeals(repository.values().stream(), userId);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return allowedSortedMeals(repository.values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDate, startTime, endDate, endTime)), userId);
    }

    private List<Meal> allowedSortedMeals(Stream<Meal> mealStream, int userId) {
        return mealStream
                .filter(meal -> isAllowed(meal, userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean isAllowed(Meal meal, Integer userId) {
        return meal != null && Objects.equals(meal.getUserId(), userId);
    }
}

