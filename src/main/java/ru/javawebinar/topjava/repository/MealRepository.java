package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, Integer authId);

    // false if meal does not belong to userId
    boolean delete(int id, Integer authId);

    // null if meal does not belong to userId
    Meal get(int id, Integer authId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(Integer authId);
}
