package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(int userId, int userCaloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(userId), userCaloriesPerDay);
    }

    public List<MealTo> getAllFiltered(int userId, LocalDate startDate, LocalTime startTime,
                                       LocalDate endDate, LocalTime endTime, int userCaloriesPerDay) {
        if (startDate == null && endDate == null && startTime == null && endTime == null) {
            return getAll(userId, userCaloriesPerDay);
        }
        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;
        startTime = startTime == null ? LocalTime.MIN : startTime;
        endTime = endTime == null ? LocalTime.MAX : endTime;
        return MealsUtil.getTos(repository.getAllFiltered(userId, startDate, startTime, endDate, endTime), userCaloriesPerDay);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}