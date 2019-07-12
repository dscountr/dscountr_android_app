package dscountr.app.co.ke.dscountr_android_app.view.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A class with static util methods.
 */

public class DateUtils {

    // This class should not be initialized
    private DateUtils() {

    }


    /**
     * Gets string timestamp and converts it to HH:mm (e.g. 16:44).
     */
    public static String formatTime(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        if (value == null){
            return null;
        }
        return dateFormat.format(value);
    }

    /**
     * Gets string timestamp and converts it to HH:mm:aa (e.g. 16:44:12).
     */
    public static String formatTimeWithMarker(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        if (value == null){
            return null;
        }
        return dateFormat.format(value);
    }

    /**
     * Gets string timestamp and converts it to HH: (e.g. 16).
     */
    public static int getHourOfDay(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("H", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        if (value == null){
            return 0;
        }
        return Integer.valueOf(dateFormat.format(value));
    }

    /**
     * Gets string timestamp and converts it to :mm (e.g. :44).
     */
    public static int getMinute(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("m", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        if (value == null){
            return 0;
        }
        return Integer.valueOf(dateFormat.format(value));
    }

    /**
     * If the given time is of a different date, display the date.
     * If it is of the same date, display the time.
     * @param dateTime  The time to convert, in milliseconds.
     * @return  The time or date.
     */
    public static String formatDateTime(String dateTime) {
        if(isToday(dateTime)) {
            return formatTime(dateTime);
        } else {
            return formatMonthDayYear(dateTime);
        }
    }

    /**
     * Formats timestamp to 'date month' format (e.g. 'February 3').
     */
    public static String formatMonthDay(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        if (value == null){
            return null;
        }
        return dateFormat.format(value);
    }

    /**
     * Formats timestamp to 'date month' format (e.g. 'Feb 3, 2019').
     */
    public static String formatMonthDayYear(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        if (value == null){
            return null;
        }
        return dateFormat.format(value);
    }

    /**
     * Returns the parsed date.
     */
    private static Date parseDateFromUTC(String dateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            return formatter.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the parsed date.
     */
    private static Date parseDateToMM_DD_YYYY(String dateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns whether the given date is today, based on the user's current locale.
     */
    public static boolean isToday(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date value = parseDateFromUTC(dateTime);
        String date = dateFormat.format(value);
        return date.equals(dateFormat.format(System.currentTimeMillis()));
    }

    /**
     * Checks if two dates are of the same day.
     * @param dateTimeFirst   The time in milliseconds of the first date.
     * @param dateTimeSecond  The time in milliseconds of the second date.
     * @return  Whether {@param dateTimeFirst} and {@param dateTimeSecond} are off the same day.
     */
    public static boolean hasSameDate(String dateTimeFirst, String dateTimeSecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date valueFirst = parseDateFromUTC(dateTimeFirst);
        if (valueFirst == null){
            return false;
        }
        Date valueSecond = parseDateFromUTC(dateTimeSecond);
        if (valueSecond == null){
            return false;
        }
        return dateFormat.format(valueFirst).equals(dateFormat.format(valueSecond));
    }

    /**
     * Returns the parsed date.
     */
    public static String parseDateForDOB(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date value = parseDateToMM_DD_YYYY(dateTime);
        if (value == null){
            return null;
        }
        return dateFormat.format(value);
    }
}
