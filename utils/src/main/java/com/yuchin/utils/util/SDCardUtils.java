package com.yuchin.utils.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.yuchin.utils.unit.FileSize;
import com.yuchin.utils.unit.Unit;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具類: 外部儲存相關
 *             API  : 判斷是否存在/可用, 獲取狀態, 獲取根目錄, 獲取容量 等
 * </pre>
 */
public class SDCardUtils {

    private SDCardUtils() {
    }

    /**
     * 獲取 SD卡的狀態
     *
     * @return SD卡狀態, 為以下其中一種: <br/>
     * {@link Environment#MEDIA_UNKNOWN}, {@link Environment#MEDIA_REMOVED},
     * {@link Environment#MEDIA_UNMOUNTED}, {@link Environment#MEDIA_CHECKING},
     * {@link Environment#MEDIA_NOFS}, {@link Environment#MEDIA_MOUNTED},
     * {@link Environment#MEDIA_MOUNTED_READ_ONLY}, {@link Environment#MEDIA_SHARED},
     * {@link Environment#MEDIA_BAD_REMOVAL}, {@link Environment#MEDIA_UNMOUNTABLE}.
     */
    public static String getState() {
        return Environment.getExternalStorageState();
    }

    /**
     * 判斷 SD卡是否可用
     *
     * @return 是否可用
     */
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 檢查 SD卡是否存在
     *
     * @return 是否存在
     */
    private static boolean checkSdCard() {
        return isAvailable();
    }

    /**
     * 獲取 SD卡的根目錄
     *
     * @return 根目錄文件對象, 不存在 SD卡返回 null
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    /**
     * 獲取 Download 文件夾
     *
     * @return 系統級 Download 文件夾對象
     */
    public static File getDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 獲取 SD卡容量
     *
     * @return SD卡容量, 單位: B
     */
    public static long getSdCardSize() {
        if (!isAvailable()) return 0;

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        } else {
            return statFs.getBlockCount() * statFs.getBlockSize();
        }
    }

    /**
     * 指定單位下, 獲取 SD卡容量
     *
     * @param unit 單位
     * @return 指定單位下的 SD卡容量
     */
    public static float getSdCardSize(@Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getSdCardSize(), unit);
    }

    /**
     * 獲取 SD卡可用容量
     *
     * @return SD卡可用容量, 單位: B
     */
    public static long getSdCardAvailableSize() {
        if (!isAvailable()) return 0;

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } else {
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        }
    }

    /**
     * 指定單位下, 獲取 SD卡可用容量
     *
     * @param unit 單位
     * @return 指定單位下的 SD卡可用容量
     */
    public static float getSdCardAvailableSize(@Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getSdCardAvailableSize(), unit);
    }

}
