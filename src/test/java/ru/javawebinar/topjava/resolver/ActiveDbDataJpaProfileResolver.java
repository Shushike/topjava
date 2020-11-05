package ru.javawebinar.topjava.resolver;

import ru.javawebinar.topjava.Profiles;

public class ActiveDbDataJpaProfileResolver extends ActiveDbProfileResolver {

    @Override
    protected String implementationProfile(){
        return Profiles.DATAJPA;
    }
}