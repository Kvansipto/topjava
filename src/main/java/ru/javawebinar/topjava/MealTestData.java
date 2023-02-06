package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealTestData {
    private static final List<MealTo> mealWithExcessList = new ArrayList(){{
        add(new MealTo(LocalDateTime.of(2022, 2,10,10,0),"Завтрак", 500, false));
        add(new MealTo(LocalDateTime.of(2022, 2,10,14,0),"Обед", 1000, false));
        add(new MealTo(LocalDateTime.of(2022, 2,10,18,0),"Ужин", 1000, true));
        add(new MealTo(LocalDateTime.of(2022, 2,10,22,0),"Второй Ужин", 100, false));
        add(new MealTo(LocalDateTime.of(2022, 2,10,23,50),"Ночной перекус", 400, false));
    }};

    public static List<MealTo> getMealWithExcessList(){
        return mealWithExcessList;
    }
}
