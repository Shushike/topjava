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

    public RuntimeMealDao() {
        meals = new ConcurrentHashMap<>();
        for (Meal meal : MealsUtil.getMeals()) {
            add(meal);
        }
    }

    @Override
    public Meal add(Meal entity) {
        entity.setId(idCounter.incrementAndGet());
        meals.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Meal update(Meal entity) {
        try {
            meals.replace(entity.getId(), entity);
        } catch (Throwable e) {
            return null;
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList(meals.values());
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }
}
