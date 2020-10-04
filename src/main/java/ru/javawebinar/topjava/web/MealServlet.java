package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.RuntimeMealDao;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final String ACTION_NAME="action";
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    RuntimeMealDao dao = new RuntimeMealDao();

    private Integer getIntegerParam(HttpServletRequest request, String paramName) {
        String paramStr = request.getParameter(paramName);
        Integer result = null;
        if (paramStr != null && !paramStr.isEmpty())
            try {
                result = Integer.valueOf(paramStr);
            } catch (NumberFormatException e) {
                log.warn("'{}' invalid parameter format", paramName);
            }
        return result;
    }

    private LocalDateTime getDateTimeParam(HttpServletRequest request, String paramName) {
        String paramStr = request.getParameter(paramName);
        LocalDateTime result = null;
        if (paramStr != null && !paramStr.isEmpty()) {
            try {
                result = LocalDateTime.parse(paramStr, formatter);
            } catch (DateTimeParseException e) {
                log.warn("'{}' invalid datetime parameter format", paramName);
            }
        }
        return result;
    }

    private void getMealsTo(HttpServletRequest request) {
        Integer caloriesLimit = getIntegerParam(request, "limit");
        List<MealTo> meals = MealsUtil.filter(dao.getAll(),  //MealsUtil.getMeals(),
                null, null,
                caloriesLimit != null ? caloriesLimit.intValue() : 2000);
        request.setAttribute("list", meals);
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
                log.debug("Delete meal {}", deletedId);
                dao.delete(deletedId);
                getMealsTo(request);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
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
        String action = request.getParameter(ACTION_NAME);
        if ("submit".equals(action)) {
            Integer calories = getIntegerParam(request, "calories");
            Integer id = getIntegerParam(request, "id");
            LocalDateTime dateTime = getDateTimeParam(request, "datetime");
            log.debug("Update meal {}", id);
            Meal newMeal = new Meal(id, dateTime, request.getParameter("description"), calories != null ? calories.intValue() : 0);
            if (id == null)
                dao.add(newMeal);
            else
                dao.update(newMeal);
        }
        log.debug("Post meals list");
        getMealsTo(request);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
