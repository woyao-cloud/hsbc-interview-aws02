package com.myke.util;

import java.util.Calendar;

public class CalendarUtils {

    /**
     * 获取当前年份
     *
     * @return
     */
    public int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}