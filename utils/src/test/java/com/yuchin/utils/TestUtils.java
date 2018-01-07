package com.yuchin.utils;

import com.yuchin.utils.util.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/21
 *     desc  : 單元測試工具類
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TestUtils {

    public static void init() {
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {
        TagMessage m0 = new TagMessage("", "0", -1);
        TagMessage m1 = new TagMessage("", "1", 0);
        TagMessage m2 = new TagMessage("", "2", 0);
        TagMessage m3 = new TagMessage("", "3", 0);
        TagMessage m4 = new TagMessage("", "4", 1);
        List<TagMessage> ms = new ArrayList<>();
        ms.add(m0);
        ms.add(m1);
        ms.add(m2);
        ms.add(m3);
        ms.add(m4);

        Collections.sort(ms);
        System.out.println(ms);
    }
}