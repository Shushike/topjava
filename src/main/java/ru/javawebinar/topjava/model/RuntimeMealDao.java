package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class RuntimeMealDao implements EntityDao<Meal> {
    //для реализации при хранении в памяти
    private Map<Integer, Meal> meals = null;
    private static AtomicInteger idCounter = new AtomicInteger(0);

    private Map<Integer, Meal> getMeals() {
        if (meals == null) {
            synchronized (this) {
                if (meals == null) {
                    meals = new ConcurrentHashMap<>();
                    for (Meal meal : MealsUtil.getMeals()) {
                        meal.setId(idCounter.incrementAndGet());
                        meals.put(meal.getId(), meal);
                    }
                }
            }
        }
        return meals;
    }

    @Override
    public boolean add(Meal entity) {
        if (!entity.isSetId())
            entity.setId(idCounter.incrementAndGet());
        meals.put(entity.getId(), entity);
        return true;
    }

    @Override
    public boolean update(Meal entity) {
        getMeals().replace(entity.getId(), entity);
        return true;
    }

    @Override
    public void delete(int id) {
        getMeals().remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList(getMeals().values());
    }

    @Override
    public Meal getById(int id) {
        return getMeals().get(id);
    }
}
