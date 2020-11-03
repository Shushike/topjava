package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.resolver.ActiveDbJdbcProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(resolver = ActiveDbJdbcProfileResolver.class)
public class JdbcUserServiceTest extends UserServiceTest {
}