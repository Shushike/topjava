package ru.javawebinar.topjava.web;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static AtomicInteger userId = new AtomicInteger(1);

    public static int authUserId() {
        return userId.get();
    }

    public static void setAuthUserId(int newUserId) {
         userId.set(newUserId);
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}