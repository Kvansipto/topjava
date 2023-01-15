package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

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
        List<UserMealWithExcess> x = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        x.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<LocalDateTime, UserMeal> sortedMap = new TreeMap<>();
        for (UserMeal meal : meals) {
            sortedMap.put(meal.getDateTime(), meal);
        }
        boolean excess = false;

        for (Map.Entry<LocalDateTime, UserMeal> entry : sortedMap.entrySet()) {
            UserMeal userMeal = entry.getValue();
            caloriesPerDay -= userMeal.getCalories();
            if (caloriesPerDay < 0) {
                excess = true;
            }
            LocalDateTime localDateTime = entry.getKey();
            if (TimeUtil.isBetweenHalfOpen(localDateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExcessList.add(new UserMealWithExcess(localDateTime, userMeal.getDescription(), userMeal.getCalories(), excess));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        int[] countCalories = {caloriesPerDay};
        return meals
                .stream()
                .collect(Collectors.toMap(UserMeal::getDateTime, i -> {
                    countCalories[0] -= i.getCalories();
                    return new UserMealWithExcess(i.getDateTime(), i.getDescription(), i.getCalories(), countCalories[0] < 0);
                }, (v1, v2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                }, TreeMap::new))
                .entrySet()
                .stream()
                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getKey().toLocalTime(), startTime, endTime))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
