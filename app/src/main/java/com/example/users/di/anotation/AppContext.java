package com.example.users.di.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Named;

@Named
@Retention( RetentionPolicy.RUNTIME)
public @interface AppContext {
}
