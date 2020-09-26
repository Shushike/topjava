package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isInPeriod(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return startTime == null && endTime == null
                || startTime == null && lt.compareTo(endTime) < 0
                || endTime == null && lt.compareTo(startTime) >= 0
                || startTime != null && lt.compareTo(startTime) >= 0 && endTime != null && lt.compareTo(endTime) < 0;
    }
}
