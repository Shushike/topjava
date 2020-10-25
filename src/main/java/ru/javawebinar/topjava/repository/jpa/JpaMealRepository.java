package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            if (meal.getUser()!=null && meal.getUser().getId()==userId)
                return em.merge(meal);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter(Meal.userIdName, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<String,Object> props = new HashMap<>();
        props.put(Meal.userIdName, userId);
        return em.find(Meal.class, id, props);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(Meal.userIdName, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.BY_TIME, Meal.class)
                .setParameter(Meal.userIdName, userId)
                .setParameter(Meal.startTimeName, Util.convertToDate(startDateTime), TemporalType.TIMESTAMP)
                .setParameter(Meal.endTimeName, Util.convertToDate(endDateTime), TemporalType.TIMESTAMP)
                .getResultList();
    }
}