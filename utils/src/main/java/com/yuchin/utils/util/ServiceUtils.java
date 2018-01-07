package com.yuchin.utils.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 服務相關工具類
 * </pre>
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 獲取所有運行的服務
     *
     * @return 服務名集合
     */
    public static Set getAllRunningService() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 啟動服務
     *
     * @param className 完整包名的服務類名
     */
    public static void startService(final String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 啟動服務
     *
     * @param cls 服務類
     */
    public static void startService(final Class<?> cls) {
        Intent intent = new Intent(Utils.getApp(), cls);
        Utils.getApp().startService(intent);
    }

    /**
     * 停止服務
     *
     * @param className 完整包名的服務類名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失敗
     */
    public static boolean stopService(final String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服務
     *
     * @param cls 服務類
     * @return {@code true}: 停止成功<br>{@code false}: 停止失敗
     */
    public static boolean stopService(final Class<?> cls) {
        Intent intent = new Intent(Utils.getApp(), cls);
        return Utils.getApp().stopService(intent);
    }

    /**
     * 綁定服務
     *
     * @param className 完整包名的服務類名
     * @param conn      服務連接對象
     * @param flags     綁定選項
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(final String className,
                                   final ServiceConnection conn,
                                   final int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 綁定服務
     *
     * @param cls   服務類
     * @param conn  服務連接對象
     * @param flags 綁定選項
     *              <ul>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(final Class<?> cls,
                                   final ServiceConnection conn,
                                   final int flags) {
        Intent intent = new Intent(Utils.getApp(), cls);
        Utils.getApp().bindService(intent, conn, flags);
    }

    /**
     * 解綁服務
     *
     * @param conn 服務連接對象
     */
    public static void unbindService(final ServiceConnection conn) {
        Utils.getApp().unbindService(conn);
    }

    /**
     * 判斷服務是否運行
     *
     * @param className 完整包名的服務類名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(final String className) {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }
}
