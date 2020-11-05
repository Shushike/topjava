package ru.javawebinar.topjava.resolver;

import org.springframework.test.context.ActiveProfilesResolver;
import ru.javawebinar.topjava.Profiles;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {

    protected String implementationProfile(){
        return Profiles.REPOSITORY_IMPLEMENTATION;
    }

    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.getActiveDbProfile(), implementationProfile()};
    }
}