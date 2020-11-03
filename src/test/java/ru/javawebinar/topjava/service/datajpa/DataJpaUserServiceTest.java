package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.resolver.ActiveDbDataJpaProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(resolver = ActiveDbDataJpaProfileResolver.class)
public class DataJpaUserServiceTest extends UserServiceTest {
}