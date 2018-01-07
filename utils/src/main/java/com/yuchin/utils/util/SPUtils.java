package com.yuchin.utils.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : SP 相關工具類
 * </pre>
 */
@SuppressLint("ApplySharedPref")
public final class SPUtils {

    private static SimpleArrayMap<String, SPUtils> SP_UTILS_MAP = new SimpleArrayMap<>();
    private SharedPreferences sp;

    private SPUtils(final String spName) {
        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 獲取 SP 實例
     *
     * @return {@link SPUtils}
     */
    public static SPUtils getInstance() {
        return getInstance("");
    }

    /**
     * 獲取 SP 實例
     *
     * @param spName sp 名
     * @return {@link SPUtils}
     */
    public static SPUtils getInstance(String spName) {
        if (isSpace(spName)) spName = "spUtils";
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(spName);
            SP_UTILS_MAP.put(spName, spUtils);
        }
        return spUtils;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * SP 中寫入 String
     *
     * @param key   鍵
     * @param value 值
     */
    public void put(@NonNull final String key, @NonNull final String value) {
        put(key, value, false);
    }

    /**
     * SP 中寫入 String
     *
     * @param key      鍵
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key,
                    @NonNull final String value,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * SP 中讀取 String
     *
     * @param key 鍵
     * @return 存在返回對應值，不存在返回默認值{@code ""}
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    /**
     * SP 中讀取 String
     *
     * @param key          鍵
     * @param defaultValue 默認值
     * @return 存在返回對應值，不存在返回默認值{@code defaultValue}
     */
    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * SP 中寫入 int
     *
     * @param key   鍵
     * @param value 值
     */
    public void put(@NonNull final String key, final int value) {
        put(key, value, false);
    }

    /**
     * SP 中寫入 int
     *
     * @param key      鍵
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * SP 中讀取 int
     *
     * @param key 鍵
     * @return 存在返回對應值，不存在返回默認值-1
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    /**
     * SP 中讀取 int
     *
     * @param key          鍵
     * @param defaultValue 默認值
     * @return 存在返回對應值，不存在返回默認值{@code defaultValue}
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP 中寫入 long
     *
     * @param key   鍵
     * @param value 值
     */
    public void put(@NonNull final String key, final long value) {
        put(key, value, false);
    }

    /**
     * SP 中寫入 long
     *
     * @param key      鍵
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    /**
     * SP 中讀取 long
     *
     * @param key 鍵
     * @return 存在返回對應值，不存在返回默認值-1
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * SP 中讀取 long
     *
     * @param key          鍵
     * @param defaultValue 默認值
     * @return 存在返回對應值，不存在返回默認值{@code defaultValue}
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP 中寫入 float
     *
     * @param key   鍵
     * @param value 值
     */
    public void put(@NonNull final String key, final float value) {
        put(key, value, false);
    }

    /**
     * SP 中寫入 float
     *
     * @param key      鍵
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * SP 中讀取 float
     *
     * @param key 鍵
     * @return 存在返回對應值，不存在返回默認值-1
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP 中讀取 float
     *
     * @param key          鍵
     * @param defaultValue 默認值
     * @return 存在返回對應值，不存在返回默認值{@code defaultValue}
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP 中寫入 boolean
     *
     * @param key   鍵
     * @param value 值
     */
    public void put(@NonNull final String key, final boolean value) {
        put(key, value, false);
    }

    /**
     * SP 中寫入 boolean
     *
     * @param key      鍵
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * SP 中讀取 boolean
     *
     * @param key 鍵
     * @return 存在返回對應值，不存在返回默認值{@code false}
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * SP 中讀取 boolean
     *
     * @param key          鍵
     * @param defaultValue 默認值
     * @return 存在返回對應值，不存在返回默認值{@code defaultValue}
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP 中寫入 String 集合
     *
     * @param key    鍵
     * @param values 值
     */
    public void put(@NonNull final String key, @NonNull final Set<String> values) {
        put(key, values, false);
    }

    /**
     * SP 中寫入 String 集合
     *
     * @param key      鍵
     * @param values   值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key,
                    @NonNull final Set<String> values,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, values).commit();
        } else {
            sp.edit().putStringSet(key, values).apply();
        }
    }

    /**
     * SP 中讀取 StringSet
     *
     * @param key 鍵
     * @return 存在返回對應值，不存在返回默認值{@code Collections.<String>emptySet()}
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * SP 中讀取 StringSet
     *
     * @param key          鍵
     * @param defaultValue 默認值
     * @return 存在返回對應值，不存在返回默認值{@code defaultValue}
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    @NonNull final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * SP 中獲取所有鍵值對
     *
     * @return Map 對象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP 中是否存在該 key
     *
     * @param key 鍵
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    /**
     * SP 中移除該 key
     *
     * @param key 鍵
     */
    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    /**
     * SP 中移除該 key
     *
     * @param key      鍵
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * SP 中清除所有數據
     */
    public void clear() {
        clear(false);
    }

    /**
     * SP 中清除所有數據
     *
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }
}
