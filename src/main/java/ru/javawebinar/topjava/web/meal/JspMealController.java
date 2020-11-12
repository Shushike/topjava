package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends MealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    public String doDelete(@RequestParam int id) {
        delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String doUpdate(@RequestParam int id, Model model) {//HttpServletRequest request) {
        final Meal meal = get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String doCreate(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam Map<String, String> allParams, Model model) {
        LocalDate startDate = parseLocalDate(allParams.get("startDate"));
        LocalDate endDate = parseLocalDate(allParams.get("endDate"));
        LocalTime startTime = parseLocalTime(allParams.get("startTime"));
        LocalTime endTime = parseLocalTime(allParams.get("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping
    public String setMeal(@RequestParam Map<String, String> allParams) throws IOException {
        Meal meal = new Meal(
                LocalDateTime.parse(allParams.get("dateTime")),
                allParams.get("description"),
                Integer.parseInt(allParams.get("calories")));

        if (!StringUtils.hasText(allParams.get("id"))) {
            create(meal);
        } else {
            update(meal, getId(allParams.get("id")));
        }
        return "redirect:meals";
    }

}
