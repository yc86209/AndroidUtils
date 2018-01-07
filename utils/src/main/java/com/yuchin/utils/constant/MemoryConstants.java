package com.yuchin.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : 存儲相關常量
 * </pre>
 */
public final class MemoryConstants {

    /**
     * Byte 與 Byte 的倍數
     */
    public static final int BYTE = 1;
    /**
     * KB 與 Byte 的倍數
     */
    public static final int KB = 1024;
    /**
     * MB 與 Byte 的倍數
     */
    public static final int MB = 1048576;
    /**
     * GB 與 Byte 的倍數
     */
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
