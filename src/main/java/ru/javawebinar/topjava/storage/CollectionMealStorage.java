package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionMealStorage implements Storage<Meal> {
    ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public AtomicInteger getCount() {
        return counter;
    }

    @Override
    public void add(Meal meal) {
        storage.putIfAbsent(meal.getId(), meal);
        counter.incrementAndGet();
    }

    @Override
    public void delete(Meal meal) {
        storage.remove(meal.getId());
        counter.decrementAndGet();
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal getById(Integer id) {
        return storage.get(id);
    }

    @Override
    public void clear() {
        storage.clear();
        counter.set(0);
    }
}
