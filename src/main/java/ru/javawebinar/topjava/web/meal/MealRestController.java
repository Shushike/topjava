package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal get(Integer id) {
        return service.get(id, authUserId());
    }

    public void delete(Integer id) {
        service.delete(id, authUserId());
    }

    public void update(Meal meal) {
        service.update(meal);
    }

    public Collection<Meal> getAll(){
        return service.getAll(authUserId());
    }
}