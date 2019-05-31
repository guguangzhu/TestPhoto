package com.photo.testphoto.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/23]
 */
public class DateUtils {


    private DateUtils() {
        throw new AssertionError("Utils class");
    }

    public static Date formatTimeToDate(String time) {

        if (TextUtils.isEmpty(time)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatTimeToStringNoYear(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringYear(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToMonth(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringMonth(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringDay(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("d天", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringMonthDay(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringMonthDayEnglish(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        return dateFormat.format(date);

    }
    public static String formatTimeToStringYearMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringHourMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearHourMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonth(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonthDay(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonthTwo(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringHour(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date formatTimeToStringHour(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        date = date.replace("Z"," UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            Date d = format.parse(date);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatTimeToStringHourTwo(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
//        date = date.replace("Z"," UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+08:00");
        try {
            Date d = format.parse(date);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatTimeToStringMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    //字符串转时间戳
    public static String getTime(String timeString){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算相差的小时
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getTimeDifferenceHour(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

            timeString = Float.toString(hour1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }


    public static Date getDateByString(String time) {
        Date date = null;
        if(time == null) {
            return date;
        }

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getShortTime(long time) {
        String shortString = null;
        long now = Calendar.getInstance().getTimeInMillis();

        long delTime = now/1000-time;
        if (delTime > 30 * 24 * 60 * 60) {  //日期
            shortString = formatTimeToStringYearMonthDay(new Date(time*1000));
        } else if (delTime > 24 * 60 * 60) {
            shortString = (int) (delTime / (24 * 60 * 60)) + "天前";
        } else if (delTime > 60 * 60) {
            shortString = (int) (delTime / (60 * 60)) + "小时前";
        } else if (delTime > 60) {
            shortString = (int) (delTime / (60)) + "分钟前";
        } else if (delTime > 1) {
            shortString = delTime + "秒前";
        } else {
            shortString = "1秒前";
        }
        return shortString;
    }
}
