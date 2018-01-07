package com.yuchin.utils.base;

import android.app.Application;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/30
 *     desc  : 基类 UtilsApp
 * </pre>
 */
public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallback());
    }

}
