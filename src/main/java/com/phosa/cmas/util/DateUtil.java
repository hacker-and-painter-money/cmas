package com.phosa.cmas.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    @Contract(" -> new")
    public static @NotNull Date getCurrentDate() {
//        return new Date();
        return Calendar.getInstance().getTime();
    }
}
