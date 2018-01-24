package com.yuchin.androidutils.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yuchin.androidutils.R;

public class SecondActivity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        Intent starter = new Intent(activity, SecondActivity.class);
        activity.startActivity(starter);
    }

    public static void startActivity(Activity activity, int requestCode) {
        Intent starter = new Intent(activity, SecondActivity.class);
        activity.startActivityForResult(starter, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
