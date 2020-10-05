package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.RuntimeMealDao;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final String ACTION_NAME = "action";
    private static final String MEALS_LIST = "/topjava/meals";
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private RuntimeMealDao dao;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        dao = new RuntimeMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION_NAME);
        if (action == null)
            action = "list";
        switch (action) {
            case "update":
                Integer id = getIntegerParam(request, "id");
                log.debug("Edit meal {}", id);
                Meal entity;
                if (id != null) {
                    entity = dao.getById(id);
                } else
                    entity = new Meal();
                request.setAttribute("meal", entity);
                request.getRequestDispatcher("/editForm.jsp").forward(request, response);
                break;
            case "delete":
                Integer deletedId = getIntegerParam(request, "id");
                if (deletedId != null) {
                    log.debug("Delete meal {}", deletedId);
                    dao.delete(deletedId);
                }
                getMealsTo(request);
                response.sendRedirect(MEALS_LIST);
                break;
            case "list":
            default:
                log.debug("Get meals list");
                getMealsTo(request);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Post");
        request.setCharacterEncoding("UTF-8");
        Integer calories = getIntegerParam(request, "calories");
        Integer id = getIntegerParam(request, "id");
        LocalDateTime dateTime = getDateTimeParam(request, "datetime");
        log.debug("Update meal {}", id);
        Meal newMeal = new Meal(id, dateTime, request.getParameter("description"), calories != null ? calories.intValue() : 0);
        if (id == null)
            dao.add(newMeal);
        else
            dao.update(newMeal);
        log.debug("Post meals list");
        getMealsTo(request);
        response.sendRedirect(MEALS_LIST);
    }

    private Integer getIntegerParam(HttpServletRequest request, String paramName) {
        String paramStr = request.getParameter(paramName);
        if (paramStr != null && !paramStr.isEmpty())
            try {
                return Integer.valueOf(paramStr);
            } catch (NumberFormatException e) {
                log.warn("'{}' invalid parameter format", paramName);
            }
        return null;
    }

    private LocalDateTime getDateTimeParam(HttpServletRequest request, String paramName) {
        String paramStr = request.getParameter(paramName);
        if (paramStr != null && !paramStr.isEmpty()) {
            try {
                return LocalDateTime.parse(paramStr, formatter);
            } catch (DateTimeParseException e) {
                log.warn("'{}' invalid datetime parameter format", paramName);
            }
        }
        return null;
    }

    private void getMealsTo(HttpServletRequest request) {
        List<MealTo> meals = MealsUtil.filteredByStreams(dao.getAll(),  //MealsUtil.getMeals(),
                LocalTime.MIN, LocalTime.MAX,
                2000);
        request.setAttribute("list", meals);
    }
}
