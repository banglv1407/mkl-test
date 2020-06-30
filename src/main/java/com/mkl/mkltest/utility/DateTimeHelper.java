package com.mkl.mkltest.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

public class DateTimeHelper {
    static Logger log = Logger.getLogger(DateTimeHelper.class.getName());

    public static final long ONE_DAY_IN_MILLISECOND = 86400000;

    public static final int ONE_HOUR_IN_MILLISECOND = 3600000;

    public static final int ONE_MINUTE_IN_MILLISECOND = 60000;

    public static final int DEFAULT_TIME_ZONE = 7;

    public static final String DEFAULT_TIME_ZONE_ID = "Asia/Bangkok";

    public static Date addDays(Date inputDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(inputDate);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Long getTheLastTimeOfDate(Long date, Integer timeZone) {
        // Get the first time of date
        Long theFirstTime = getFirstTimeOfDay(date, timeZone);

        return theFirstTime + ONE_DAY_IN_MILLISECOND - 1;
    }

    public static Long getFirstTimeOfDay(Long date, Integer timeZone) {
        Long temp = date + (timeZone * ONE_HOUR_IN_MILLISECOND);

        return temp - (temp % ONE_DAY_IN_MILLISECOND);
    }

    public static Long getFirstTimeOfDayInTimeZone(Long date, Integer timeZone) {
        return getFirstTimeOfDay(date, timeZone) - (timeZone * ONE_HOUR_IN_MILLISECOND); // return date at that timezone
    }

    public static List<Long> getListDateBetween(Long startDate, Long endDate, Integer timeZone) {
        List<Long> listDate = new LinkedList<>();
        startDate = getFirstTimeOfDayInTimeZone(startDate, timeZone);
        endDate = getFirstTimeOfDayInTimeZone(endDate, timeZone);

        String listDateStr = "";
        for (Long date = startDate; date <= endDate; date += ONE_DAY_IN_MILLISECOND) {
            listDateStr += date;
            listDate.add(date);
        }

        log.info("listDate: " + listDateStr);

        return listDate;
    }

    public static Long getFirstTimeFromDayInteger(int day, int timeZone) throws ParseException {
        String tzString = String.format("GMT%+03d", timeZone);

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        format.setTimeZone(TimeZone.getTimeZone(tzString));

        return format.parse(String.valueOf(day)).getTime();
    }

	public static List<Integer> getListDateBetween(int startDate, int endDate) {
	    List<Integer> listDate = new ArrayList<>();
	    LocalDate start = LocalDate.parse(startDate + "", DateTimeFormatter.ofPattern("yyyyMMdd"));
	    LocalDate end = LocalDate.parse(endDate + "", DateTimeFormatter.ofPattern("yyyyMMdd"));
	    for (LocalDate date = start; date.compareTo(end) <= 0; date = date.plusDays(1)) {
	        int dateInt = Integer.parseInt(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
	        listDate.add(dateInt);
	    }
	    return listDate;
    }
}
