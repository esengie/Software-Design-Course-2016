package ru.spbau.mit.Protocol;

import java.util.Calendar;

/**
 * Class returning current time in ms
 */
class GetNow {
    static long getNow() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
