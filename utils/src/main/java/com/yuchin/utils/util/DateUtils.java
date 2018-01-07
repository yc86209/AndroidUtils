package com.yuchin.utils.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作工具類.
 *
 * @author shimiso
 */

public class DateUtils {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文簡寫如：2010
     */
    public static String FORMAT_Y = "yyyy";
    /**
     * 英文簡寫如：12:01
     */
    public static String FORMAT_HM = "HH:mm";
    /**
     * 英文簡寫如：1-12 12:01
     */
    public static String FORMAT_MDHM = "MM-dd HH:mm";
    /**
     * 英文簡寫（默認）如：2010-12-01
     */
    public static String FORMAT_YMD = "yyyy-MM-dd";
    /**
     * 英文全稱  如：2010-12-01 23:15
     */
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    /**
     * 英文全稱  如：2010-12-01 23:15:06
     */
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精確到毫秒的完整時間    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 精確到毫秒的完整時間    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL_SN = "yyyyMMddHHmmssS";
    /**
     * 中文簡寫  如：2010年12月01日
     */
    public static String FORMAT_YMD_CN = "yyyy年MM月dd日";
    /**
     * 中文簡寫  如：2010年12月01日  12時
     */
    public static String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH時";
    /**
     * 中文簡寫  如：2010年12月01日  12時12分
     */
    public static String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH時mm分";
    /**
     * 中文全稱  如：2010年12月01日  23時15分06秒
     */
    public static String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH時mm分ss秒";
    /**
     * 精確到毫秒的完整中文時間
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH時mm分ss秒SSS毫秒";
    public static Calendar calendar = null;

    public static Date str2Date(String str) {
        return str2Date(str, null);
    }


    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.TAIWAN);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);
    }


    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }


    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }


    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }


    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }


    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }


    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" +
                c.get(Calendar.DAY_OF_MONTH) + "-" +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) +
                ":" + c.get(Calendar.SECOND);
    }


    /**
     * 獲得當前日期的字符串格式
     *
     * @param format 格式化的類型
     * @return 返回格式化之後的事件
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);

    }


    /**
     * @param time 當前的時間
     * @return 格式到秒
     */
    //
    public static String getMillon(long time) {

        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);

    }


    /**
     * @param time 當前的時間
     * @return 當前的天
     */
    public static String getDay(long time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }


    /**
     * @param time 時間
     * @return 返回一個毫秒
     */
    // 格式到毫秒
    public static String getSMillon(long time) {

        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

    }


    /**
     * 在日期上增加數個整月
     *
     * @param date 日期
     * @param n    要增加的月數
     * @return 增加數個整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();

    }


    /**
     * 在日期上增加天數
     *
     * @param date 日期
     * @param n    要增加的天數
     * @return 增加之後的天數
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();

    }


    /**
     * 獲取距現在某一小時的時刻
     *
     * @param format 格式化時間的格式
     * @param h      距現在的小時 例如：h=-1為上一個小時，h=1為下一個小時
     * @return 獲取距現在某一小時的時刻
     */
    public static String getNextHour(String format, int h) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime() + h * 60 * 60 * 1000);
        return sdf.format(date);

    }


    /**
     * 獲取時間戳
     *
     * @return 獲取時間戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());

    }


    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小時
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分鐘
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }


    /**
     * 獲得默認的 date pattern
     *
     * @return 默認的格式
     */
    public static String getDatePattern() {

        return FORMAT_YMDHMS;
    }


    /**
     * 返回秒鐘
     *
     * @param date Date 日期
     * @return 返回秒鐘
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();

        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }


    /**
     * 使用預設格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return 提取字符串的日期
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());

    }


    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }


    /**
     * 按默認格式的字符串距離今天的天數
     *
     * @param date 日期字符串
     * @return 按默認格式的字符串距離今天的天數
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

    }


    /**
     * 使用用戶格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 提取字符串日期
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 按用戶格式字符串距離今天的天數
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return 按用戶格式字符串距離今天的天數
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

    }
}
