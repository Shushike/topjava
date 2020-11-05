package ru.javawebinar.topjava.resolver;

import ru.javawebinar.topjava.Profiles;

public class ActiveDbJpaProfileResolver extends ActiveDbProfileResolver {

    @Override
    protected String implementationProfile(){
        return Profiles.JPA;
    }
}