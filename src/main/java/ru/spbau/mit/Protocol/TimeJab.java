package ru.spbau.mit.Protocol;

import java.util.Calendar;

public class TimeJab {
    public static long getNow() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
