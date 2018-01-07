package com.yuchin.utils.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : Activity 相關工具類
 * </pre>
 */
public final class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判斷 Activity 是否存在
     *
     * @param packageName 包名
     * @param className   activity 全路徑類名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(@NonNull final String packageName,
                                           @NonNull final String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getApp().getPackageManager()) == null ||
                Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 啟動 Activity
     *
     * @param clz Activity 類
     */
    public static void startActivity(@NonNull final Class<?> clz) {
        Context context = getActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(), null);
    }

    /**
     * 啟動 Activity
     *
     * @param clz     Activity 類
     * @param options 跳轉動畫
     */
    public static void startActivity(@NonNull final Class<?> clz,
                                     @NonNull final Bundle options) {
        Context context = getActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(), options);
    }

    /**
     * 啟動 Activity
     *
     * @param clz       Activity 類
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Class<?> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param clz      Activity 類
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param clz      Activity 類
     * @param options  跳轉動畫
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @NonNull final Bundle options) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), options);
    }

    /**
     * 啟動 Activity
     *
     * @param activity       activity
     * @param clz            Activity 類
     * @param sharedElements 共享元素
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @NonNull final View... sharedElements) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * 啟動 Activity
     *
     * @param activity  activity
     * @param clz       Activity 類
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {

        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param extras extras
     * @param clz    Activity 類
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> clz) {
        Context context = getActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clz.getName(), null);
    }

    /**
     * 啟動 Activity
     *
     * @param extras  extras
     * @param clz     Activity 類
     * @param options 跳轉動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> clz,
                                     @NonNull final Bundle options) {
        Context context = getActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clz.getName(), options);
    }

    /**
     * 啟動 Activity
     *
     * @param extras    extras
     * @param clz       Activity 類
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param clz      Activity 類
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> clz) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * 啟動 Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param clz      Activity 類
     * @param options  跳轉動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @NonNull final Bundle options) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(), options);
    }

    /**
     * 啟動 Activity
     *
     * @param extras         extras
     * @param activity       activity
     * @param clz            Activity 類
     * @param sharedElements 共享元素
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @NonNull final View... sharedElements) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * 啟動 Activity
     *
     * @param extras    extras
     * @param activity  activity
     * @param clz       Activity 類
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param pkg 包名
     * @param cls 全類名
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(getActivityOrApp(), null, pkg, cls, null);
    }

    /**
     * 啟動 Activity
     *
     * @param pkg     包名
     * @param cls     全類名
     * @param options 動畫
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(getActivityOrApp(), null, pkg, cls, options);
    }

    /**
     * 啟動 Activity
     *
     * @param pkg       包名
     * @param cls       全類名
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getActivityOrApp();
        startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param pkg      包名
     * @param cls      全類名
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, null, pkg, cls, null);
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param pkg      包名
     * @param cls      全類名
     * @param options  動畫
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, null, pkg, cls, options);
    }

    /**
     * 啟動 Activity
     *
     * @param activity       activity
     * @param pkg            包名
     * @param cls            全類名
     * @param sharedElements 共享元素
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final View... sharedElements) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 啟動 Activity
     *
     * @param activity  activity
     * @param pkg       包名
     * @param cls       全類名
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param extras extras
     * @param pkg    包名
     * @param cls    全類名
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(getActivityOrApp(), extras, pkg, cls, null);
    }

    /**
     * 啟動 Activity
     *
     * @param extras  extras
     * @param pkg     包名
     * @param cls     全類名
     * @param options 動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(getActivityOrApp(), extras, pkg, cls, options);
    }

    /**
     * 啟動 Activity
     *
     * @param extras    extras
     * @param pkg       包名
     * @param cls       全類名
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getActivityOrApp();
        startActivity(context, extras, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param extras   extras
     * @param pkg      包名
     * @param cls      全類名
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, extras, pkg, cls, null);
    }

    /**
     * 啟動 Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param pkg      包名
     * @param cls      全類名
     * @param options  動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, extras, pkg, cls, options);
    }

    /**
     * 啟動 Activity
     *
     * @param extras         extras
     * @param activity       activity
     * @param pkg            包名
     * @param cls            全類名
     * @param sharedElements 共享元素
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final View... sharedElements) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 啟動 Activity
     *
     * @param extras    extras
     * @param pkg       包名
     * @param cls       全類名
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param intent 意圖
     */
    public static void startActivity(@NonNull final Intent intent) {
        startActivity(intent, getActivityOrApp(), null);
    }

    /**
     * 啟動 Activity
     *
     * @param intent  意圖
     * @param options 跳轉動畫
     */
    public static void startActivity(@NonNull final Intent intent,
                                     @NonNull final Bundle options) {
        startActivity(intent, getActivityOrApp(), options);
    }

    /**
     * 啟動 Activity
     *
     * @param intent    意圖
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Intent intent,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getActivityOrApp();
        startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param intent   意圖
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent) {
        startActivity(intent, activity, null);
    }

    /**
     * 啟動 Activity
     *
     * @param activity activity
     * @param intent   意圖
     * @param options  跳轉動畫
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @NonNull final Bundle options) {
        startActivity(intent, activity, options);
    }

    /**
     * 啟動 Activity
     *
     * @param activity       activity
     * @param intent         意圖
     * @param sharedElements 共享元素
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @NonNull final View... sharedElements) {
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 啟動 Activity
     *
     * @param activity  activity
     * @param intent    意圖
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動多個 Activity
     *
     * @param intents 意圖
     */
    public static void startActivities(@NonNull final Intent[] intents) {
        startActivities(intents, getActivityOrApp(), null);
    }

    /**
     * 啟動多個 Activity
     *
     * @param intents 意圖
     * @param options 跳轉動畫
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @NonNull final Bundle options) {
        startActivities(intents, getActivityOrApp(), options);
    }

    /**
     * 啟動多個 Activity
     *
     * @param intents   意圖
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        Context context = getActivityOrApp();
        startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 啟動多個 Activity
     *
     * @param activity activity
     * @param intents  意圖
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents) {
        startActivities(intents, activity, null);
    }

    /**
     * 啟動多個 Activity
     *
     * @param activity activity
     * @param intents  意圖
     * @param options  跳轉動畫
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @NonNull final Bundle options) {
        startActivities(intents, activity, options);
    }

    /**
     * 啟動多個 Activity
     *
     * @param activity  activity
     * @param intents   意圖
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 回到桌面
     */
    public static void startHomeActivity() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(homeIntent);
    }

    /**
     * 獲取 Activity 棧鏈表
     *
     * @return Activity 棧鏈表
     */
    public static List<Activity> getActivityList() {
        return Utils.sActivityList;
    }

    /**
     * 獲取啟動項 Activity
     *
     * @return 啟動項 Activity
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    /**
     * 獲取啟動項 Activity
     *
     * @param packageName 包名
     * @return 啟動項 Activity
     */
    public static String getLauncherActivity(@NonNull final String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

    /**
     * 獲取棧頂 Activity
     *
     * @return 棧頂 Activity
     */
    public static Activity getTopActivity() {
        if (Utils.sTopActivityWeakRef != null) {
            Activity activity = Utils.sTopActivityWeakRef.get();
            if (activity != null) {
                return activity;
            }
        }
        List<Activity> activities = Utils.sActivityList;
        int size = activities.size();
        return size > 0 ? activities.get(size - 1) : null;
    }

    /**
     * 判斷 Activity 是否存在棧中
     *
     * @param activity activity
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Activity activity) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity aActivity : activities) {
            if (aActivity.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判斷 Activity 是否存在棧中
     *
     * @param clz Activity 類
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Class<?> clz) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity aActivity : activities) {
            if (aActivity.getClass().equals(clz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 結束 Activity
     *
     * @param activity activity
     */
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);
    }

    /**
     * 結束 Activity
     *
     * @param activity   activity
     * @param isLoadAnim 是否啟動動畫
     */
    public static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * 結束 Activity
     *
     * @param activity  activity
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void finishActivity(@NonNull final Activity activity,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 結束 Activity
     *
     * @param clz Activity 類
     */
    public static void finishActivity(@NonNull final Class<?> clz) {
        finishActivity(clz, false);
    }

    /**
     * 結束 Activity
     *
     * @param clz        Activity 類
     * @param isLoadAnim 是否啟動動畫
     */
    public static void finishActivity(@NonNull final Class<?> clz, final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    /**
     * 結束 Activity
     *
     * @param clz       Activity 類
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void finishActivity(@NonNull final Class<?> clz,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        }
    }

    /**
     * 結束到指定 Activity
     *
     * @param activity      activity
     * @param isIncludeSelf 是否結束該 activity 自己
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf) {
        return finishToActivity(activity, isIncludeSelf, false);
    }

    /**
     * 結束到指定 Activity
     *
     * @param activity      activity
     * @param isIncludeSelf 是否結束該 activity 自己
     * @param isLoadAnim    是否啟動動畫
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * 結束到指定 Activity
     *
     * @param activity      activity
     * @param isIncludeSelf 是否結束該 activity 自己
     * @param enterAnim     入場動畫
     * @param exitAnim      出場動畫
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(aActivity, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * 結束到指定 Activity
     *
     * @param clz           Activity 類
     * @param isIncludeSelf 是否結束該 activity 自己
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf) {
        return finishToActivity(clz, isIncludeSelf, false);
    }

    /**
     * 結束到指定 Activity
     *
     * @param clz           Activity 類
     * @param isIncludeSelf 是否結束該 activity 自己
     * @param isLoadAnim    是否啟動動畫
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * 結束到指定 Activity
     *
     * @param clz           Activity 類
     * @param isIncludeSelf 是否結束該 activity 自己
     * @param enterAnim     入場動畫
     * @param exitAnim      出場動畫
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(aActivity, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * 結束所有其他類型的 Activity
     *
     * @param clz Activity 類
     */
    public static void finishOtherActivities(@NonNull final Class<?> clz) {
        finishOtherActivities(clz, false);
    }


    /**
     * 結束所有其他類型的 Activity
     *
     * @param clz        Activity 類
     * @param isLoadAnim 是否啟動動畫
     */
    public static void finishOtherActivities(@NonNull final Class<?> clz,
                                             final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        boolean flag = false;
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity.getClass().equals(clz)) {
                if (flag) {
                    finishActivity(activity, isLoadAnim);
                } else {
                    flag = true;
                }
            } else {
                finishActivity(activity, isLoadAnim);
            }
        }
    }

    /**
     * 結束所有其他類型的 Activity
     *
     * @param clz       Activity 類
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void finishOtherActivities(@NonNull final Class<?> clz,
                                             @AnimRes final int enterAnim,
                                             @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        boolean flag = false;
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity.getClass().equals(clz)) {
                if (flag) {
                    finishActivity(activity, enterAnim, exitAnim);
                } else {
                    flag = true;
                }
            } else {
                finishActivity(activity, enterAnim, exitAnim);
            }
        }
    }

    /**
     * 結束所有 Activity
     */
    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    /**
     * 結束所有 Activity
     *
     * @param isLoadAnim 是否啟動動畫
     */
    public static void finishAllActivities(final boolean isLoadAnim) {
        List<Activity> activityList = Utils.sActivityList;
        for (int i = activityList.size() - 1; i >= 0; --i) {// 從棧頂開始移除
            Activity activity = activityList.get(i);
            activity.finish();// 在 onActivityDestroyed 發生 remove
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * 結束所有 Activity
     *
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void finishAllActivities(@AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activityList = Utils.sActivityList;
        for (int i = activityList.size() - 1; i >= 0; --i) {// 從棧頂開始移除
            Activity activity = activityList.get(i);
            activity.finish();// 在 onActivityDestroyed 發生 remove
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 結束除最新之外的所有 Activity
     */
    public static void finishAllActivitiesExceptNewest() {
        finishAllActivitiesExceptNewest(false);
    }

    /**
     * 結束除最新之外的所有 Activity
     *
     * @param isLoadAnim 是否啟動動畫
     */
    public static void finishAllActivitiesExceptNewest(final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        boolean flag = false;
        for (int i = activities.size() - 2; i >= 0; i--) {
            finishActivity(activities.get(i), isLoadAnim);
        }
    }

    /**
     * 結束除最新之外的所有 Activity
     *
     * @param enterAnim 入場動畫
     * @param exitAnim  出場動畫
     */
    public static void finishAllActivitiesExceptNewest(@AnimRes final int enterAnim,
                                                       @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        boolean flag = false;
        for (int i = activities.size() - 2; i >= 0; i--) {
            finishActivity(activities.get(i), enterAnim, exitAnim);
        }
    }

    private static Context getActivityOrApp() {
        Activity topActivity = getTopActivity();
        return topActivity == null ? Utils.getApp() : topActivity;
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static void startActivity(final Intent intent,
                                      final Context context,
                                      final Bundle options) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    private static void startActivities(final Intent[] intents,
                                        final Context context,
                                        final Bundle options) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intents) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivities(intents, options);
        } else {
            context.startActivities(intents);
        }
    }

    private static Bundle getOptionsBundle(final Context context,
                                           final int enterAnim,
                                           final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int len = sharedElements.length;
            @SuppressWarnings("unchecked")
            Pair<View, String>[] pairs = new Pair[len];
            for (int i = 0; i < len; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null, null).toBundle();
    }
}
