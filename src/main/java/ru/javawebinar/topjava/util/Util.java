package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static Calendar convertToCalendar(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return calendar;
    }

    public static Date convertToDate(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}