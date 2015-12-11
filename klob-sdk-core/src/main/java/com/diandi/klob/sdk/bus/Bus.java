package com.diandi.klob.sdk.bus;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-01  .
 * *********    Time : 10:51 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)

public @interface Bus {
    int value();
}
