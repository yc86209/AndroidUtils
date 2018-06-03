package com.yuchin.androidutils.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.yuchin.androidutils.R;

public class MainActivity extends AppCompatActivity {
    static final String FILE_SEP = System.getProperty("file.separator");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity.startActivity(MainActivity.this, 0);
            }
        });
    }
}
