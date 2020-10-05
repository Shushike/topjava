package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.dao.EntityDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RuntimeMealDao implements EntityDao<Meal> {
    private Map<Integer, Meal> meals;
    private static AtomicInteger idCounter = new AtomicInteger(0);

    private Map<Integer, Meal> getMeals() {
        if (meals == null) {
            synchronized (this) {
                if (meals == null) {
                    meals = new ConcurrentHashMap<>();
                    for (Meal meal : MealsUtil.getMeals()) {
                        add(meal);
                        //meal.setId(idCounter.incrementAndGet());
                        //meals.put(meal.getId(), meal);
                    }
                }
            }
        }
        return meals;
    }

    @Override
    public Meal add(Meal entity) {
        if (!entity.isSetId())
            entity.setId(idCounter.incrementAndGet());
        meals.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Meal update(Meal entity) {
        getMeals().replace(entity.getId(), entity);
        return entity;
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
