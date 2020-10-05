package ru.javawebinar.topjava.dao;

import java.util.List;

public interface EntityDao<T> {
    void delete(int id);

    T add(T entity);

    T update(T entity);

    T getById(int id);

    List<T> getAll();
}
