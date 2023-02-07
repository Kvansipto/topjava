package ru.javawebinar.topjava.storage;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Storage<T> {

    AtomicInteger getCount();

    void add(T t);

    void delete(T t);

    void update(T t);

    List<T> getAll();

    T getById(Integer id);

    void clear();
}
