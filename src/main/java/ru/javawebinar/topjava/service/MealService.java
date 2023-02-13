package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.ValidationUtil.*;


@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer authId) {
        return checkNotFoundWithId(repository.save(meal, authId), meal.getId());
    }

    public void delete(int id, Integer authId) {
        checkNotFoundWithId(repository.delete(id, authId), id);
    }

    public Meal get(int id, Integer authId) {
        return checkNotFoundWithId(repository.get(id, authId), id);
    }

    public List<MealTo> getAll(Integer authId) {
        return MealsUtil.getTos(repository.getAll(authId), DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAll(Integer authId, LocalDate startDate, LocalTime startTime,
                               LocalDate endDate, LocalTime endTime) {
        if (startDate == null && endDate == null && startTime == null && endTime == null) {
            return getAll(authId);
        } else {
            startDate = startDate == null ? LocalDate.MIN : startDate;
            endDate = endDate == null ? LocalDate.MAX : endDate;
            startTime = startTime == null ? LocalTime.MIN : startTime;
            endTime = endTime == null ? LocalTime.MAX : endTime;
        }
        return MealsUtil.getFilteredTos(repository.getAll(authId), DEFAULT_CALORIES_PER_DAY,
                startDate.atTime(startTime), endDate.atTime(endTime));
    }

    public List<MealTo> getAll(Integer authId, LocalTime startTime, LocalTime endTime) {
        if (startTime == null && endTime == null) {
            return getAll(authId);
        } else {
            startTime = startTime == null ? LocalTime.MIN : startTime;
            endTime = endTime == null ? LocalTime.MAX : endTime;
        }
        return MealsUtil.getFilteredTos(repository.getAll(authId), DEFAULT_CALORIES_PER_DAY,
                startTime, endTime);
    }

    public void update(Meal meal, Integer authId) {
        checkNotFoundWithId(repository.save(meal, authId), meal.getId());
    }
}