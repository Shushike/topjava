package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(Integer id) {
        log.info("get meal {}", id);
        return service.get(id, authUserId());
    }

    public void delete(Integer id) {
        log.info("delete meal {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, Integer id) {
        log.info("update meal {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("get all meal");
        return MealsUtil.getTos(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltredByUserAndDate(LocalDate from, LocalDate to) {
        log.info("get all meal from {} to {}", from, to);
        return MealsUtil.getTos(service.getFiltredByUserAndDate(authUserId(), from, to), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltredByUserAndDateAndTime(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        log.info("get all meal from {} to {} and since {} till {}", fromDate, toDate, fromTime, toTime);
        return MealsUtil.getFilteredTos(service.getFiltredByUserAndDate(SecurityUtil.authUserId(), fromDate, toDate),
                SecurityUtil.authUserCaloriesPerDay(),
                fromTime,
                toTime);
    }
}