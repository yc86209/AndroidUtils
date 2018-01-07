package com.yuchin.utils.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by youchin-Li on 2017/12/15.
 */

public class ActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private static int foregroundActivityCount = 0;
    private boolean isAppLive = false;

    private static void startLauncherActivity(Activity activity) {
        try {
            Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            if (launchIntent == null || launchIntent.getComponent() == null) return;

            String launcherClassName = launchIntent.getComponent().getClassName();
            String className = activity.getComponentName().getClassName();
            if (launcherClassName.equals(className)) return;

            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(launchIntent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判斷 UtilsApp 是否處於後台 (螢幕關閉也算處於後台)
     *
     * @return true: 處於後台
     * <br/>false: 處於前台
     */
    public static boolean isAppInBackground() {
        return foregroundActivityCount <= 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityStackManager.getInstance().addActivity(activity);

        if (!isAppLive) {
            isAppLive = true;
            startLauncherActivity(activity);
            if (savedInstanceState != null && savedInstanceState.getBoolean("saveStateKey", false)) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
//                Log.v("[localTime]", format.format(new Date(savedInstanceState.getLong("localTime"))));
            }
        }


    }

    @Override
    public void onActivityStarted(Activity activity) {
        foregroundActivityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // 弱引用持有当前 Activity 实例
        ActivityStackManager.getInstance().setCurrentActivity(activity);
        // Activity 页面栈方式
        ActivityStackManager.getInstance().setTopActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        foregroundActivityCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        outState.putBoolean("saveStateKey", true);
        outState.putLong("localTime", System.currentTimeMillis());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStackManager.getInstance().removeActivity(activity);
    }
}
