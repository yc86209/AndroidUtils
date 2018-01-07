package com.yuchin.utils.reflect;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/26
 *     desc  : CacheUtils 單元測試
 * </pre>
 */
public class PrivateConstructors {

    public final String string;

    private PrivateConstructors() {
        this(null);
    }

    private PrivateConstructors(String string) {
        this.string = string;
    }
}
