package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    //private MealRepository repository;
    private MealRestController mealRestController;
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //repository = new InMemoryMealRepository();
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = applicationContext.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.update(meal);
        //repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.info("User id {}", SecurityUtil.authUserId());
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                //repository.delete(id, SecurityUtil.authUserId());
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId()) :
                        //repository.get(getId(request), SecurityUtil.authUserId());
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                final LocalDate fromDate = getDateParam(request, "fromDate");
                final LocalDate toDate = getDateParam(request, "toDate");

                final LocalTime fromTime = getTimeParam(request, "fromTime");
                final LocalTime toTime = getTimeParam(request, "toTime");

                final LocalDateTime from = LocalDateTime.of(fromDate != null ? fromDate : LocalDate.MIN, fromTime != null ? fromTime : LocalTime.MIN);
                final LocalDateTime to = LocalDateTime.of(toDate != null ? toDate : LocalDate.MAX, toTime != null ? toTime : LocalTime.MAX);

                request.setAttribute("meals",
                        MealsUtil.getFilteredTos(mealRestController.getAll(from, to), //repository.getAll(SecurityUtil.authUserId()),
                                SecurityUtil.authUserCaloriesPerDay(),
                                from.toLocalTime(),
                                to.toLocalTime()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private LocalDate getDateParam(HttpServletRequest request, String paramName) {
        String paramStr = request.getParameter(paramName);
        if (paramStr != null && !paramStr.isEmpty()) {
            try {
                final LocalDate date = LocalDate.parse(paramStr, dateFormatter);
                request.setAttribute(paramName, date);
                return date;
            } catch (DateTimeParseException e) {
                log.warn("'{}' invalid date parameter format", paramName);
            }
        }
        return null;
    }

    private LocalTime getTimeParam(HttpServletRequest request, String paramName) {
        String paramStr = request.getParameter(paramName);
        if (paramStr != null && !paramStr.isEmpty()) {
            try {
                final LocalTime time = LocalTime.parse(paramStr, timeFormatter);
                request.setAttribute(paramName, time);
                return time;
            } catch (DateTimeParseException e) {
                log.warn("'{}' invalid time parameter format", paramName);
            }
        }
        return null;
    }

    @Override
    public void destroy() {
        applicationContext.close();
        super.destroy();
    }
}
