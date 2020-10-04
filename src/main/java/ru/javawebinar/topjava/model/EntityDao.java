package ru.javawebinar.topjava.model;

import java.util.List;

public interface EntityDao<T extends Object> {
    public void delete(int id);

    public boolean add(T entity);

    public boolean update(T entity);

    public T getById(int id);

    public List<T> getAll();
}
