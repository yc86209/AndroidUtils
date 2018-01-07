package com.yuchin.utils.util;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 尺寸相關工具類
 * </pre>
 */
public final class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * dp 轉 px
     *
     * @param dpValue dp 值
     * @return px 值
     */
    public static int dp2px(final float dpValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 轉 dp
     *
     * @param pxValue px 值
     * @return dp 值
     */
    public static int px2dp(final float pxValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 轉 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 轉 sp
     *
     * @param pxValue px 值
     * @return sp 值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 各種單位轉換
     * <p>該方法存在於 TypedValue</p>
     *
     * @param unit    單位
     * @param value   值
     * @param metrics DisplayMetrics
     * @return 轉換結果
     */
    public static float applyDimension(final int unit,
                                       final float value,
                                       final DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 在 onCreate 中獲取視圖的尺寸
     * <p>需回調 onGetSizeListener 接口，在 onGetSize 中獲取 view 寬高</p>
     * <p>用法示例如下所示</p>
     * <pre>
     * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *     Override
     *     public void onGetSize(final View view) {
     *         view.getWidth();
     *     }
     * });
     * </pre>
     *
     * @param view     視圖
     * @param listener 監聽器
     */
    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }
            }
        });
    }

    /**
     * 測量視圖尺寸
     *
     * @param view 視圖
     * @return arr[0]: 視圖寬度, arr[1]: 視圖高度
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * 獲取測量視圖寬度
     *
     * @param view 視圖
     * @return 視圖寬度
     */
    public static int getMeasuredWidth(final View view) {
        return measureView(view)[0];
    }

    /**
     * 獲取測量視圖高度
     *
     * @param view 視圖
     * @return 視圖高度
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * 獲取到 View 尺寸的監聽
     */
    public interface onGetSizeListener {
        void onGetSize(View view);
    }
}
