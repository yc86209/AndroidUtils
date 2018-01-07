package com.yuchin.utils.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : 欄相關工具類
 * </pre>
 */
public final class BarUtils {

    ///////////////////////////////////////////////////////////////////////////
    // status bar
    ///////////////////////////////////////////////////////////////////////////

    private static final int DEFAULT_ALPHA = 112;
    private static final String TAG_COLOR = "TAG_COLOR";
    private static final String TAG_ALPHA = "TAG_ALPHA";
    private static final int TAG_OFFSET = -123;

    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 獲取狀態欄高度（單位：px）
     *
     * @return 狀態欄高度（單位：px）
     */
    public static int getStatusBarHeight() {
        Resources resources = Utils.getApp().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 設置狀態欄是否可見
     *
     * @param activity  activity
     * @param isVisible {@code true}: 可見<br>{@code false}: 不可見
     */
    public static void setStatusBarVisibility(final Activity activity, final boolean isVisible) {
        if (isVisible) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    /**
     * 判斷狀態欄是否可見
     *
     * @param activity activity
     * @return {@code true}: 可見<br>{@code false}: 不可見
     */
    public static boolean isStatusBarVisible(final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * 為 view 增加 MarginTop 為狀態欄高度
     *
     * @param view view
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) return;
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin + getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(TAG_OFFSET, true);
    }

    /**
     * 為 view 減少 MarginTop 為狀態欄高度
     *
     * @param view view
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) return;
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin - getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(TAG_OFFSET, false);
    }

    /**
     * 設置狀態欄顏色
     *
     * @param activity activity
     * @param color    狀態欄顏色值
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, false);
    }

    /**
     * 設置狀態欄顏色
     *
     * @param activity activity
     * @param color    狀態欄顏色值
     * @param alpha    狀態欄透明度，此透明度並非顏色中的透明度
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarColor(activity, color, alpha, false);
    }

    /**
     * 設置狀態欄顏色
     *
     * @param activity activity
     * @param color    狀態欄顏色值
     * @param alpha    狀態欄透明度，此透明度並非顏色中的透明度
     * @param isDecor  {@code true}: 設置在 DecorView 中<br>
     *                 {@code false}: 設置在 ContentView 中
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideAlphaView(activity);
        transparentStatusBar(activity);
        addStatusBarColor(activity, color, alpha, isDecor);
    }

    /**
     * 設置狀態欄透明度
     *
     * @param activity activity
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity) {
        setStatusBarAlpha(activity, DEFAULT_ALPHA, false);
    }

    /**
     * 設置狀態欄透明度
     *
     * @param activity activity
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarAlpha(activity, alpha, false);
    }

    /**
     * 設置狀態欄透明度
     *
     * @param activity activity
     * @param alpha    狀態欄透明度
     * @param isDecor  {@code true}: 設置在 DecorView 中<br>
     *                 {@code false}: 設置在 ContentView 中
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideColorView(activity);
        transparentStatusBar(activity);
        addStatusBarAlpha(activity, alpha, isDecor);
    }

    /**
     * 設置狀態欄顏色
     *
     * @param fakeStatusBar 偽造狀態欄
     * @param color         狀態欄顏色值
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color) {
        setStatusBarColor(fakeStatusBar, color, DEFAULT_ALPHA);
    }

    /**
     * 設置狀態欄顏色
     *
     * @param fakeStatusBar 偽造狀態欄
     * @param color         狀態欄顏色值
     * @param alpha         狀態欄透明度，此透明度並非顏色中的透明度
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha));
    }

    /**
     * 設置狀態欄透明度
     *
     * @param fakeStatusBar 偽造狀態欄
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar) {
        setStatusBarAlpha(fakeStatusBar, DEFAULT_ALPHA);
    }

    /**
     * 設置狀態欄透明度
     *
     * @param fakeStatusBar 偽造狀態欄
     * @param alpha         狀態欄透明度
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }

    /**
     * 為 DrawerLayout 設置狀態欄顏色
     * <p>DrawLayout 需設置 {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      activity
     * @param drawer        drawerLayout
     * @param fakeStatusBar 偽造狀態欄
     * @param color         狀態欄顏色值
     * @param isTop         drawerLayout 是否在頂層
     */
    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                final boolean isTop) {
        setStatusBarColor4Drawer(activity, drawer, fakeStatusBar, color, DEFAULT_ALPHA, isTop);
    }

    /**
     * 為 DrawerLayout 設置狀態欄顏色
     * <p>DrawLayout 需設置 {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      activity
     * @param drawer        drawerLayout
     * @param fakeStatusBar 偽造狀態欄
     * @param color         狀態欄顏色值
     * @param alpha         狀態欄透明度，此透明度並非顏色中的透明度
     * @param isTop         drawerLayout 是否在頂層
     */
    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                @IntRange(from = 0, to = 255) final int alpha,
                                                final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarColor(fakeStatusBar, color, isTop ? alpha : 0);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, alpha, false);
        }
    }

    /**
     * 為 DrawerLayout 設置狀態欄透明度
     * <p>DrawLayout 需設置 {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      activity
     * @param drawer        drawerLayout
     * @param fakeStatusBar 偽造狀態欄
     * @param isTop         drawerLayout 是否在頂層
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                final boolean isTop) {
        setStatusBarAlpha4Drawer(activity, drawer, fakeStatusBar, DEFAULT_ALPHA, isTop);
    }

    /**
     * 為 DrawerLayout 設置狀態欄透明度
     * <p>DrawLayout 需設置 {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      activity
     * @param drawer        drawerLayout
     * @param fakeStatusBar 偽造狀態欄
     * @param alpha         狀態欄透明度
     * @param isTop         drawerLayout 是否在頂層
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @IntRange(from = 0, to = 255) final int alpha,
                                                final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarAlpha(fakeStatusBar, isTop ? alpha : 0);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, alpha, false);
        }
    }

    private static void addStatusBarColor(final Activity activity,
                                          final int color,
                                          final int alpha,
                                          boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        } else {
            parent.addView(createColorStatusBarView(parent.getContext(), color, alpha));
        }
    }

    private static void addStatusBarAlpha(final Activity activity,
                                          final int alpha,
                                          boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        } else {
            parent.addView(createAlphaStatusBarView(parent.getContext(), alpha));
        }
    }

    private static void hideColorView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static void hideAlphaView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static int getStatusBarColor(final int color, final int alpha) {
        if (alpha == 0) return color;
        float a = 1 - alpha / 255f;
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return Color.argb(255, red, green, blue);
    }

    /**
     * 繪制一個和狀態欄一樣高的顏色矩形
     */
    private static View createColorStatusBarView(final Context context,
                                                 final int color,
                                                 final int alpha) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        statusBarView.setTag(TAG_COLOR);
        return statusBarView;
    }

    /**
     * 繪制一個和狀態欄一樣高的黑色透明度矩形
     */
    private static View createAlphaStatusBarView(final Context context, final int alpha) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setTag(TAG_ALPHA);
        return statusBarView;
    }

    private static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // action bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 獲取 ActionBar 高度
     *
     * @param activity activity
     * @return ActionBar 高度
     */
    public static int getActionBarHeight(@NonNull final Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, activity.getResources().getDisplayMetrics()
            );
        }
        return 0;
    }

    ///////////////////////////////////////////////////////////////////////////
    // notification bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 設置通知欄是否可見
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     *
     * @param isVisible {@code true}: 可見<br>{@code false}: 關閉
     */
    public static void setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }
        invokePanels(methodName);
    }

    private static void invokePanels(final String methodName) {
        try {
            @SuppressLint("WrongConstant")
            Object service = Utils.getApp().getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // navigation bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 獲取導航欄高度
     *
     * @return 導航欄高度
     */
    public static int getNavBarHeight() {
        Resources res = Utils.getApp().getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 設置導航欄是否可見
     *
     * @param activity  activity
     * @param isVisible {@code true}: 可見<br>{@code false}: 不可見
     */
    public static void setNavBarVisibility(final Activity activity, boolean isVisible) {
        if (isVisible) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = activity.getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int visibility = decorView.getSystemUiVisibility();
                decorView.setSystemUiVisibility(visibility & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    /**
     * 設置導航欄沈浸式
     *
     * @param activity activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(final Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 判斷導航欄是否可見
     *
     * @param activity activity
     * @return {@code true}: 可見<br>{@code false}: 不可見
     */
    public static boolean isNavBarVisible(final Activity activity) {
        boolean isNoLimits = (activity.getWindow().getAttributes().flags
                & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0;
        if (isNoLimits) return false;
        View decorView = activity.getWindow().getDecorView();
        int visibility = decorView.getSystemUiVisibility();
        return (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }
}
