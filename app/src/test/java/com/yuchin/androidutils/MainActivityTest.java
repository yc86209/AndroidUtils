package com.yuchin.androidutils;

import android.content.Intent;

import com.yuchin.androidutils.activity.MainActivity;
import com.yuchin.androidutils.activity.SecondActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;

/**
 * Creator：yuchin_li on 2018/1/6 20:44
 * Mail：yc86209@hotmail.com.tw
 **/


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void testMainActivity() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        mainActivity.findViewById(R.id.textView1).performClick();

        Intent expectedIntent = new Intent(mainActivity, SecondActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(mainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}
