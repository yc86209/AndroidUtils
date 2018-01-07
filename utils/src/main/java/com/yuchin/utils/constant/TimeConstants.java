package com.yuchin.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : 時間相關常量
 * </pre>
 */
public final class TimeConstants {

    /**
     * 秒與毫秒的倍數
     */
    public static final int MSEC = 1;
    /**
     * 秒與毫秒的倍數
     */
    public static final int SEC = 1000;
    /**
     * 分與毫秒的倍數
     */
    public static final int MIN = 60000;
    /**
     * 時與毫秒的倍數
     */
    public static final int HOUR = 3600000;
    /**
     * 天與毫秒的倍數
     */
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
