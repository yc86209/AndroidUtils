package com.yuchin.androidutils.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yuchin.androidutils.R;
import com.yuchin.androidutils.helper.DialogHelper;
import com.yuchin.utils.constant.PermissionConstants;
import com.yuchin.utils.util.FileUtils;
import com.yuchin.utils.util.LogUtils;
import com.yuchin.utils.util.PermissionUtils;
import com.yuchin.utils.util.ScreenUtils;

import java.io.File;
import java.util.List;

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

        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        KLog.e("rationale");
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        KLog.e(getFilesDir());

                        KLog.e(FileUtils.createOrExistsDir(getFilesDir()));
                        KLog.e(getExternalFilesDir(null));
                        FileUtils.createOrExistsDir(getExternalFilesDir(null) + FILE_SEP + "fafa");
                        for (File file : FileUtils.listFilesInDir(getExternalFilesDir(null))) {
                            KLog.e(file.toString());
                        }
                        for (File file : FileUtils.listFilesInDir(getFilesDir())) {
                            KLog.e(file.toString());
                        }

                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .theme(new PermissionUtils.ThemeCallback() {
                    @Override
                    public void onActivityCreate(Activity activity) {
                        ScreenUtils.setFullScreen(activity);
                    }
                })
                .request();


    }


}
