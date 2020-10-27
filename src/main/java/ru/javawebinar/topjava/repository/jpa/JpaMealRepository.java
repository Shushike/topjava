package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
            final User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            final Meal oldValue = get(meal.getId(), userId);
            if (oldValue != null) {
                final User ref = em.getReference(User.class, userId);
                meal.setUser(ref);
                return em.merge(meal);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter(Meal.idName, id)
                .setParameter(Meal.userIdName, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> resultList =  em.createNamedQuery(Meal.BY_ID, Meal.class)
                    .setParameter(Meal.idName, id)
                    .setParameter(Meal.userIdName, userId)
                    .getResultList();
        return DataAccessUtils.singleResult(resultList);

        /*
        Meal result = em.find(Meal.class, id);
        if (result!=null && result.getUser()!=null && result.getUser().getId()==userId)
            return result;
        return null;*/
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
                .setParameter("startTime", startDateTime)
                .setParameter("endTime", endDateTime)
                .getResultList();
    }
}