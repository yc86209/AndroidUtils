package com.yuchin.utils.base;

/**
 * Created by youchin-Li on 2017/12/15.
 */

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class ActivityStackManager {

    private Stack<Activity> activities;

    private WeakReference<Activity> sCurrentActivityWeakRef;

    private ActivityStackManager() {

    }

    public static ActivityStackManager getInstance() {
        return InstanceHolder.sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>(activity);
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new Stack<>();
        }

        if (activities.search(activity) == -1) {
            activities.push(activity);
        }
    }

    public void removeActivity(Activity activity) {
        for (int i = 0; i < activities.size(); ) {
            Activity savedActivity = activities.get(i);
            if (savedActivity == null || savedActivity == activity) {
                activities.remove(i);
            } else {
                i++;
            }
        }
    }

    public Activity getTopActivity() {
        if (activities != null && activities.size() > 0) {
            return activities.peek();
        }
        return null;
    }

    public void setTopActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            if (activities.search(activity) == -1) {
                activities.push(activity);
                return;
            }

            int location = activities.search(activity);
            if (location != 1) {
                activities.remove(activity);
                activities.push(activity);
            }
        }
    }

    public boolean isTopActivity(Activity activity) {
        return activity.equals(activities.peek());
    }

    public void finishTopActivity() {
        if (activities != null && activities.size() > 0) {
            Activity activity = activities.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void finishAllActivity() {
        if (activities != null && activities.size() > 0) {
            while (!activities.empty()) {
                Activity activity = activities.pop();
                if (activity != null) {
                    activity.finish();
                }
            }

            activities.clear();
            activities = null;
        }
    }

    public Stack<Activity> getActivityStack() {
        return activities;
    }

    private static class InstanceHolder {
        private static final ActivityStackManager sInstance = new ActivityStackManager();
    }
}