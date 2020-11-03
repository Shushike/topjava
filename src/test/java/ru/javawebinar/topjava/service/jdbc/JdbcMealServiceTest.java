package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.resolver.ActiveDbJdbcProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(resolver = ActiveDbJdbcProfileResolver.class)
public class JdbcMealServiceTest extends MealServiceTest {
}