package com.yuchin.utils.util;

import android.annotation.SuppressLint;

import com.yuchin.utils.constant.TimeConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 時間相關工具類
 * </pre>
 */
public final class TimeUtils {

    /**
     * <p>在工具類中經常使用到工具類的格式化描述，這個主要是一個日期的操作類，
     * 所以日志格式主要使用 SimpleDateFormat 的定義格式.</p>
     * 格式的意義如下： 日期和時間模式 <br>
     * <p>日期和時間格式由日期和時間模式字符串指定。
     * 在日期和時間模式字符串中，未加引號的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解釋為模式字母，用來表示日期或時間字符串元素。文本可以使用單引號 (') 引起來，以免進行解釋。"''"
     * 表示單引號。所有其他字符均不解釋；只是在格式化時將它們簡單覆制到輸出字符串，或者在分析時與輸入字符串進行匹配。
     * </p>
     * 定義了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1"
     * summary="Chart shows format letters, date/time component, presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或時間元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 標志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周數 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周數 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天數 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天數 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天數 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 標記 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小時數（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小時數（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小時數（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小時數（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小時中的分鐘數 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分鐘中的秒數 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒數 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>時區 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>時區 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                                        HH:mm    15:44
     *                                       h:mm a    3:44 下午
     *                                      HH:mm z    15:44 CST
     *                                      HH:mm Z    15:44 +0800
     *                                   HH:mm zzzz    15:44 中國標準時間
     *                                     HH:mm:ss    15:44:40
     *                                   yyyy-MM-dd    2016-08-12
     *                             yyyy-MM-dd HH:mm    2016-08-12 15:44
     *                          yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *                     yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中國標準時間
     *                EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中國標準時間
     *                     yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *                   yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *                 yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                                       K:mm a    3:44 下午
     *                             EEE, MMM d, ''yy    星期五, 八月 12, '16
     *                        hh 'o''clock' a, zzzz    03 o'clock 下午, 中國標準時間
     *                 yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *                   EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                                yyMMddHHmmssZ    160812154440+0800
     *                   yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    DATE(2016-08-12) TIME(15:44:40) 中國標準時間
     * </pre>
     * 註意：SimpleDateFormat 不是線程安全的，線程安全需用{@code ThreadLocal<SimpleDateFormat>}
     */
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] CHINESE_ZODIAC =
            {"猴", "雞", "狗", "豬", "鼠", "牛", "虎", "兔", "龍", "蛇", "馬", "羊"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC = {
            "水瓶座", "雙魚座", "白羊座", "金牛座", "雙子座", "巨蟹座",
            "獅子座", "處女座", "天秤座", "天蠍座", "射手座", "魔羯座"
    };

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 將時間戳轉為時間字符串
     * <p>格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒時間戳
     * @return 時間字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 將時間戳轉為時間字符串
     * <p>格式為 format</p>
     *
     * @param millis 毫秒時間戳
     * @param format 時間格式
     * @return 時間字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 將時間字符串轉為時間戳
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 毫秒時間戳
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * 將時間字符串轉為時間戳
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 毫秒時間戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 將時間字符串轉為 Date 類型
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return Date 類型
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    /**
     * 將時間字符串轉為 Date 類型
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return Date 類型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 將 Date 類型轉為時間字符串
     * <p>格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date 類型時間
     * @return 時間字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    /**
     * 將 Date 類型轉為時間字符串
     * <p>格式為 format</p>
     *
     * @param date   Date 類型時間
     * @param format 時間格式
     * @return 時間字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * 將 Date 類型轉為時間戳
     *
     * @param date Date 類型時間
     * @return 毫秒時間戳
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 將時間戳轉為 Date 類型
     *
     * @param millis 毫秒時間戳
     * @return Date 類型時間
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 獲取兩個時間差（單位：unit）
     * <p>time0 和 time1 格式都為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 時間字符串 0
     * @param time1 時間字符串 1
     * @param unit  單位類型
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *              <li>{@link TimeConstants#SEC }: 秒</li>
     *              <li>{@link TimeConstants#MIN }: 分</li>
     *              <li>{@link TimeConstants#HOUR}: 小時</li>
     *              <li>{@link TimeConstants#DAY }: 天</li>
     *              </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpan(final String time0,
                                   final String time1,
                                   @TimeConstants.Unit final int unit) {
        return getTimeSpan(time0, time1, DEFAULT_FORMAT, unit);
    }

    /**
     * 獲取兩個時間差（單位：unit）
     * <p>time0 和 time1 格式都為 format</p>
     *
     * @param time0  時間字符串 0
     * @param time1  時間字符串 1
     * @param format 時間格式
     * @param unit   單位類型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小時</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpan(final String time0,
                                   final String time1,
                                   final DateFormat format,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(
                Math.abs(string2Millis(time0, format) - string2Millis(time1, format)), unit
        );
    }

    /**
     * 獲取兩個時間差（單位：unit）
     *
     * @param date0 Date 類型時間 0
     * @param date1 Date 類型時間 1
     * @param unit  單位類型
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *              <li>{@link TimeConstants#SEC }: 秒</li>
     *              <li>{@link TimeConstants#MIN }: 分</li>
     *              <li>{@link TimeConstants#HOUR}: 小時</li>
     *              <li>{@link TimeConstants#DAY }: 天</li>
     *              </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpan(final Date date0,
                                   final Date date1,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), unit);
    }

    /**
     * 獲取兩個時間差（單位：unit）
     *
     * @param millis0 毫秒時間戳 0
     * @param millis1 毫秒時間戳 1
     * @param unit    單位類型
     *                <ul>
     *                <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                <li>{@link TimeConstants#SEC }: 秒</li>
     *                <li>{@link TimeConstants#MIN }: 分</li>
     *                <li>{@link TimeConstants#HOUR}: 小時</li>
     *                <li>{@link TimeConstants#DAY }: 天</li>
     *                </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpan(final long millis0,
                                   final long millis1,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(millis0 - millis1), unit);
    }

    /**
     * 獲取合適型兩個時間差
     * <p>time0 和 time1 格式都為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0     時間字符串 0
     * @param time1     時間字符串 1
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小時</p>
     *                  <p>precision = 3，返回天、小時和分鐘</p>
     *                  <p>precision = 4，返回天、小時、分鐘和秒</p>
     *                  <p>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</p>
     * @return 合適型兩個時間差
     */
    public static String getFitTimeSpan(final String time0,
                                        final String time1,
                                        final int precision) {
        long delta = string2Millis(time0, DEFAULT_FORMAT) - string2Millis(time1, DEFAULT_FORMAT);
        return millis2FitTimeSpan(Math.abs(delta), precision);
    }

    /**
     * 獲取合適型兩個時間差
     * <p>time0 和 time1 格式都為 format</p>
     *
     * @param time0     時間字符串 0
     * @param time1     時間字符串 1
     * @param format    時間格式
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小時</p>
     *                  <p>precision = 3，返回天、小時和分鐘</p>
     *                  <p>precision = 4，返回天、小時、分鐘和秒</p>
     *                  <p>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</p>
     * @return 合適型兩個時間差
     */
    public static String getFitTimeSpan(final String time0,
                                        final String time1,
                                        final DateFormat format,
                                        final int precision) {
        long delta = string2Millis(time0, format) - string2Millis(time1, format);
        return millis2FitTimeSpan(Math.abs(delta), precision);
    }

    /**
     * 獲取合適型兩個時間差
     *
     * @param date0     Date 類型時間 0
     * @param date1     Date 類型時間 1
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小時</p>
     *                  <p>precision = 3，返回天、小時和分鐘</p>
     *                  <p>precision = 4，返回天、小時、分鐘和秒</p>
     *                  <p>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</p>
     * @return 合適型兩個時間差
     */
    public static String getFitTimeSpan(final Date date0, final Date date1, final int precision) {
        return millis2FitTimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), precision);
    }

    /**
     * 獲取合適型兩個時間差
     *
     * @param millis0   毫秒時間戳 1
     * @param millis1   毫秒時間戳 2
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小時</p>
     *                  <p>precision = 3，返回天、小時和分鐘</p>
     *                  <p>precision = 4，返回天、小時、分鐘和秒</p>
     *                  <p>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</p>
     * @return 合適型兩個時間差
     */
    public static String getFitTimeSpan(final long millis0,
                                        final long millis1,
                                        final int precision) {
        return millis2FitTimeSpan(Math.abs(millis0 - millis1), precision);
    }

    /**
     * 獲取當前毫秒時間戳
     *
     * @return 毫秒時間戳
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 獲取當前時間字符串
     * <p>格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 時間字符串
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    /**
     * 獲取當前時間字符串
     * <p>格式為 format</p>
     *
     * @param format 時間格式
     * @return 時間字符串
     */
    public static String getNowString(final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 獲取當前 Date
     *
     * @return Date 類型時間
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 獲取與當前時間的差（單位：unit）
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @param unit 單位類型
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *             <li>{@link TimeConstants#SEC }: 秒</li>
     *             <li>{@link TimeConstants#MIN }: 分</li>
     *             <li>{@link TimeConstants#HOUR}: 小時</li>
     *             <li>{@link TimeConstants#DAY }: 天</li>
     *             </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpanByNow(final String time, @TimeConstants.Unit final int unit) {
        return getTimeSpan(getNowString(), time, DEFAULT_FORMAT, unit);
    }

    /**
     * 獲取與當前時間的差（單位：unit）
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @param unit   單位類型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小時</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpanByNow(final String time,
                                        final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        return getTimeSpan(getNowString(format), time, format, unit);
    }

    /**
     * 獲取與當前時間的差（單位：unit）
     *
     * @param date Date 類型時間
     * @param unit 單位類型
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *             <li>{@link TimeConstants#SEC }: 秒</li>
     *             <li>{@link TimeConstants#MIN }: 分</li>
     *             <li>{@link TimeConstants#HOUR}: 小時</li>
     *             <li>{@link TimeConstants#DAY }: 天</li>
     *             </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpanByNow(final Date date, @TimeConstants.Unit final int unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     * 獲取與當前時間的差（單位：unit）
     *
     * @param millis 毫秒時間戳
     * @param unit   單位類型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小時</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 時間戳
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants.Unit final int unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * 獲取合適型與當前時間的差
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time      時間字符串
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小時</li>
     *                  <li>precision = 3，返回天、小時和分鐘</li>
     *                  <li>precision = 4，返回天、小時、分鐘和秒</li>
     *                  <li>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</li>
     *                  </ul>
     * @return 合適型與當前時間的差
     */
    public static String getFitTimeSpanByNow(final String time, final int precision) {
        return getFitTimeSpan(getNowString(), time, DEFAULT_FORMAT, precision);
    }

    /**
     * 獲取合適型與當前時間的差
     * <p>time 格式為 format</p>
     *
     * @param time      時間字符串
     * @param format    時間格式
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小時</li>
     *                  <li>precision = 3，返回天、小時和分鐘</li>
     *                  <li>precision = 4，返回天、小時、分鐘和秒</li>
     *                  <li>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</li>
     *                  </ul>
     * @return 合適型與當前時間的差
     */
    public static String getFitTimeSpanByNow(final String time,
                                             final DateFormat format,
                                             final int precision) {
        return getFitTimeSpan(getNowString(format), time, format, precision);
    }

    /**
     * 獲取合適型與當前時間的差
     *
     * @param date      Date 類型時間
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小時</li>
     *                  <li>precision = 3，返回天、小時和分鐘</li>
     *                  <li>precision = 4，返回天、小時、分鐘和秒</li>
     *                  <li>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</li>
     *                  </ul>
     * @return 合適型與當前時間的差
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(getNowDate(), date, precision);
    }

    /**
     * 獲取合適型與當前時間的差
     *
     * @param millis    毫秒時間戳
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小時</li>
     *                  <li>precision = 3，返回天、小時和分鐘</li>
     *                  <li>precision = 4，返回天、小時、分鐘和秒</li>
     *                  <li>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</li>
     *                  </ul>
     * @return 合適型與當前時間的差
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * 獲取友好型與當前時間的差
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 友好型與當前時間的差
     * <ul>
     * <li>如果小於 1 秒鐘內，顯示剛剛</li>
     * <li>如果在 1 分鐘內，顯示 XXX秒前</li>
     * <li>如果在 1 小時內，顯示 XXX分鐘前</li>
     * <li>如果在 1 小時外的今天內，顯示今天15:32</li>
     * <li>如果是昨天的，顯示昨天15:32</li>
     * <li>其余顯示，2016-10-15</li>
     * <li>時間不合法的情況全部日期和時間信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_FORMAT);
    }

    /**
     * 獲取友好型與當前時間的差
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 友好型與當前時間的差
     * <ul>
     * <li>如果小於 1 秒鐘內，顯示剛剛</li>
     * <li>如果在 1 分鐘內，顯示 XXX秒前</li>
     * <li>如果在 1 小時內，顯示 XXX分鐘前</li>
     * <li>如果在 1 小時外的今天內，顯示今天15:32</li>
     * <li>如果是昨天的，顯示昨天15:32</li>
     * <li>其余顯示，2016-10-15</li>
     * <li>時間不合法的情況全部日期和時間信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time, final DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * 獲取友好型與當前時間的差
     *
     * @param date Date 類型時間
     * @return 友好型與當前時間的差
     * <ul>
     * <li>如果小於 1 秒鐘內，顯示剛剛</li>
     * <li>如果在 1 分鐘內，顯示 XXX秒前</li>
     * <li>如果在 1 小時內，顯示 XXX分鐘前</li>
     * <li>如果在 1 小時外的今天內，顯示今天15:32</li>
     * <li>如果是昨天的，顯示昨天15:32</li>
     * <li>其余顯示，2016-10-15</li>
     * <li>時間不合法的情況全部日期和時間信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 獲取友好型與當前時間的差
     *
     * @param millis 毫秒時間戳
     * @return 友好型與當前時間的差
     * <ul>
     * <li>如果小於 1 秒鐘內，顯示剛剛</li>
     * <li>如果在 1 分鐘內，顯示 XXX秒前</li>
     * <li>如果在 1 小時內，顯示 XXX分鐘前</li>
     * <li>如果在 1 小時外的今天內，顯示今天15:32</li>
     * <li>如果是昨天的，顯示昨天15:32</li>
     * <li>其余顯示，2016-10-15</li>
     * <li>時間不合法的情況全部日期和時間信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis);
        if (span < 1000) {
            return "剛剛";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分鐘前", span / TimeConstants.MIN);
        }
        // 獲取當天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 獲取與給定時間等於時間差的時間戳
     *
     * @param millis   給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間戳
     */
    public static long getMillis(final long millis,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間戳
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time     給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間戳
     */
    public static long getMillis(final String time,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return getMillis(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間戳
     * <p>time 格式為 format</p>
     *
     * @param time     給定時間
     * @param format   時間格式
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間戳
     */
    public static long getMillis(final String time,
                                 final DateFormat format,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間戳
     *
     * @param date     給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間戳
     */
    public static long getMillis(final Date date,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間字符串
     * <p>格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis   給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間字符串
     */
    public static String getString(final long millis,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(millis, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間字符串
     * <p>格式為 format</p>
     *
     * @param millis   給定時間
     * @param format   時間格式
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間字符串
     */
    public static String getString(final long millis,
                                   final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 獲取與給定時間等於時間差的時間字符串
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time     給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間字符串
     */
    public static String getString(final String time,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間字符串
     * <p>格式為 format</p>
     *
     * @param time     給定時間
     * @param format   時間格式
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間字符串
     */
    public static String getString(final String time,
                                   final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 獲取與給定時間等於時間差的時間字符串
     * <p>格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date     給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間字符串
     */
    public static String getString(final Date date,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(date, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的時間字符串
     * <p>格式為 format</p>
     *
     * @param date     給定時間
     * @param format   時間格式
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的時間字符串
     */
    public static String getString(final Date date,
                                   final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 獲取與給定時間等於時間差的 Date
     *
     * @param millis   給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的 Date
     */
    public static Date getDate(final long millis,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 獲取與給定時間等於時間差的 Date
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time     給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的 Date
     */
    public static Date getDate(final String time,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return getDate(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 獲取與給定時間等於時間差的 Date
     * <p>格式為 format</p>
     *
     * @param time     給定時間
     * @param format   時間格式
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的 Date
     */
    public static Date getDate(final String time,
                               final DateFormat format,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 獲取與給定時間等於時間差的 Date
     *
     * @param date     給定時間
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與給定時間等於時間差的 Date
     */
    public static Date getDate(final Date date,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 獲取與當前時間等於時間差的時間戳
     *
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與當前時間等於時間差的時間戳
     */
    public static long getMillisByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    /**
     * 獲取與當前時間等於時間差的時間字符串
     * <p>格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與當前時間等於時間差的時間字符串
     */
    public static String getStringByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getStringByNow(timeSpan, DEFAULT_FORMAT, unit);
    }

    /**
     * 獲取與當前時間等於時間差的時間字符串
     * <p>格式為 format</p>
     *
     * @param timeSpan 時間差的毫秒時間戳
     * @param format   時間格式
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與當前時間等於時間差的時間字符串
     */
    public static String getStringByNow(final long timeSpan,
                                        final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    /**
     * 獲取與當前時間等於時間差的 Date
     *
     * @param timeSpan 時間差的毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 與當前時間等於時間差的 Date
     */
    public static Date getDateByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    /**
     * 判斷是否今天
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, DEFAULT_FORMAT));
    }

    /**
     * 判斷是否今天
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final String time, final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * 判斷是否今天
     *
     * @param date Date 類型時間
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * 判斷是否今天
     *
     * @param millis 毫秒時間戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    /**
     * 判斷是否閏年
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return {@code true}: 閏年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(final String time) {
        return isLeapYear(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 判斷是否閏年
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return {@code true}: 閏年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(final String time, final DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    /**
     * 判斷是否閏年
     *
     * @param date Date 類型時間
     * @return {@code true}: 閏年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 判斷是否閏年
     *
     * @param millis 毫秒時間戳
     * @return {@code true}: 閏年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * 判斷是否閏年
     *
     * @param year 年份
     * @return {@code true}: 閏年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 獲取中式星期
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 中式星期
     */
    public static String getChineseWeek(final String time) {
        return getChineseWeek(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取中式星期
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 中式星期
     */
    public static String getChineseWeek(final String time, final DateFormat format) {
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * 獲取中式星期
     *
     * @param date Date 類型時間
     * @return 中式星期
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 獲取中式星期
     *
     * @param millis 毫秒時間戳
     * @return 中式星期
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 獲取美式星期
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 美式星期
     */
    public static String getUSWeek(final String time) {
        return getUSWeek(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取美式星期
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 美式星期
     */
    public static String getUSWeek(final String time, final DateFormat format) {
        return getUSWeek(string2Date(time, format));
    }

    /**
     * 獲取美式星期
     *
     * @param date Date 類型時間
     * @return 美式星期
     */
    public static String getUSWeek(final Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    /**
     * 獲取美式星期
     *
     * @param millis 毫秒時間戳
     * @return 美式星期
     */
    public static String getUSWeek(final long millis) {
        return getUSWeek(new Date(millis));
    }

    /**
     * 獲取星期索引
     * <p>註意：周日的 Index 才是 1，周六為 7</p>
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final String time) {
        return getWeekIndex(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取星期索引
     * <p>註意：周日的 Index 才是 1，周六為 7</p>
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final String time, final DateFormat format) {
        return getWeekIndex(string2Date(time, format));
    }

    /**
     * 獲取星期索引
     * <p>註意：周日的 Index 才是 1，周六為 7</p>
     *
     * @param date Date 類型時間
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 獲取星期索引
     * <p>註意：周日的 Index 才是 1，周六為 7</p>
     *
     * @param millis 毫秒時間戳
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * 獲取月份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(final String time) {
        return getWeekOfMonth(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取月份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 1...5
     */
    public static int getWeekOfMonth(final String time, final DateFormat format) {
        return getWeekOfMonth(string2Date(time, format));
    }

    /**
     * 獲取月份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     *
     * @param date Date 類型時間
     * @return 1...5
     */
    public static int getWeekOfMonth(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 獲取月份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     *
     * @param millis 毫秒時間戳
     * @return 1...5
     */
    public static int getWeekOfMonth(final long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * 獲取年份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 1...54
     */
    public static int getWeekOfYear(final String time) {
        return getWeekOfYear(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取年份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 1...54
     */
    public static int getWeekOfYear(final String time, final DateFormat format) {
        return getWeekOfYear(string2Date(time, format));
    }

    /**
     * 獲取年份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     *
     * @param date Date 類型時間
     * @return 1...54
     */
    public static int getWeekOfYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 獲取年份中的第幾周
     * <p>註意：國外周日才是新的一周的開始</p>
     *
     * @param millis 毫秒時間戳
     * @return 1...54
     */
    public static int getWeekOfYear(final long millis) {
        return getWeekOfYear(millis2Date(millis));
    }

    /**
     * 獲取生肖
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 生肖
     */
    public static String getChineseZodiac(final String time) {
        return getChineseZodiac(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取生肖
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 生肖
     */
    public static String getChineseZodiac(final String time, final DateFormat format) {
        return getChineseZodiac(string2Date(time, format));
    }

    /**
     * 獲取生肖
     *
     * @param date Date 類型時間
     * @return 生肖
     */
    public static String getChineseZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 獲取生肖
     *
     * @param millis 毫秒時間戳
     * @return 生肖
     */
    public static String getChineseZodiac(final long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * 獲取生肖
     *
     * @param year 年
     * @return 生肖
     */
    public static String getChineseZodiac(final int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    /**
     * 獲取星座
     * <p>time 格式為 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 時間字符串
     * @return 生肖
     */
    public static String getZodiac(final String time) {
        return getZodiac(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 獲取星座
     * <p>time 格式為 format</p>
     *
     * @param time   時間字符串
     * @param format 時間格式
     * @return 生肖
     */
    public static String getZodiac(final String time, final DateFormat format) {
        return getZodiac(string2Date(time, format));
    }

    /**
     * 獲取星座
     *
     * @param date Date 類型時間
     * @return 星座
     */
    public static String getZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 獲取星座
     *
     * @param millis 毫秒時間戳
     * @return 星座
     */
    public static String getZodiac(final long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * 獲取星座
     *
     * @param month 月
     * @param day   日
     * @return 星座
     */
    public static String getZodiac(final int month, final int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    private static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (millis < 0 || precision <= 0) return null;
        precision = Math.min(precision, 5);
        String[] units = {"天", "小時", "分鐘", "秒", "毫秒"};
        if (millis == 0) return 0 + units[precision - 1];
        StringBuilder sb = new StringBuilder();
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }
}
