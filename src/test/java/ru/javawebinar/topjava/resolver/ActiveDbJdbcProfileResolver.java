package ru.javawebinar.topjava.resolver;

import ru.javawebinar.topjava.Profiles;

public class ActiveDbJdbcProfileResolver extends ActiveDbProfileResolver {

    @Override
    protected String implementationProfile(){
        return Profiles.JDBC;
    }
}