package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.resolver.ActiveDbJpaProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(resolver = ActiveDbJpaProfileResolver.class)
public class JpaUserServiceTest extends UserServiceTest {
}