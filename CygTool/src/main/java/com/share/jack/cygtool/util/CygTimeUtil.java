package com.share.jack.cygtool.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class CygTimeUtil {

    /**
     * 获取现在的时间，返回想要的时间格式（Date类型）
     *
     * @param format 想要的时间格式
     * @return 返回想要的时间格式
     */
    public static Date getNowDate(String format) {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(currentTime);
        //解析字符串的文本，用来声称Date
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = sdf.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在的时间，返回想要的时间格式（String类型）
     *
     * @param format
     * @return
     */
    public static String getNowDateToString(String format) {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(currentTime);
    }

    /**
     * 二个'小时'时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHourTimeSpace(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDayTimeSpace(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    public static String longToString(Long t, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(t));
    }

    private static int[] getDateByLongTime(long t) {
        int[] time = new int[5];
        time[0] = Integer.valueOf(longToString(t, "yyyy"));
        time[1] = Integer.valueOf(longToString(t, "MM"));
        time[2] = Integer.valueOf(longToString(t, "dd"));
        return time;
    }

    public static String getTimeByLong(long t) {
        int year = getDateByLongTime(t)[0];
        int monthOfYear = getDateByLongTime(t)[1];
        int dayOfMonth = getDateByLongTime(t)[2];

        int yearEnd = getDateByLongTime(System.currentTimeMillis())[0];
        int monthOfYearEnd = getDateByLongTime(System.currentTimeMillis())[1];
        int dayOfMonthEnd = getDateByLongTime(System.currentTimeMillis())[2];

        if (year == yearEnd && monthOfYear == monthOfYearEnd && dayOfMonth == dayOfMonthEnd) {
            return longToString(t, "HH:mm");
        } else {
            return longToString(t, "yyyy-MM-dd");
        }
    }

    public static String dateToStr(String style, Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }

    public static String format_ymd(Date date) {
        return dateToStr("yyyy/MM/dd", date);
    }

    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        String time = dateToStr("yyyy-MM-dd HH:mm", date);
        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 ";
        } else {
            return format_ymd(date);
        }
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param t 时间戳
     * @return
     */
    public static String getStandardDate(Long t) {
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - t;
        long mill = (long) Math.ceil(time / 1000); //秒前   (除以一秒)
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前   (除以一分钟)
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时  (除以一小时)
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前   (除以一天)
        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
}
