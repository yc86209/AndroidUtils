package com.yuchin.androidutils;

import android.annotation.SuppressLint;
import android.app.Application;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.socks.library.KLog;
import com.yuchin.androidutils.db.DBManager;


/**
 * Creator：YOOOOO on 2017/12/25 01:01
 * Mail：youchin_li@newsoft.com.tw
 **/


public class UtilsApp extends Application {
    // TODO: 2017/12/25 DEBUG
    public static final boolean DEBUG = BuildConfig.DEBUG;

    private static UtilsApp sInstance;

    public static UtilsApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
        initLeakCanary();
        initLog();
        initCrash();
        initAssets();
        initORM();

    }

    private void initORM() {
        DBManager.init(this);
    }

    private void initLeakCanary() {
//        // 內存泄露檢查工具
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }

    // init it in ur application
    public void initLog() {
//        LogUtils.Config config = LogUtils.getConfig()
//                .setLogSwitch(BuildConfig.DEBUG)// 設置 log 總開關，包括輸出到控制台和文件，默認開
//                .setConsoleSwitch(BuildConfig.DEBUG)// 設置是否輸出到控制台開關，默認開
//                .setGlobalTag(null)// 設置 log 全局標簽，默認為空
//                // 當全局標簽不為空時，我們輸出的 log 全部為該 tag，
//                // 為空時，如果傳入的 tag 為空那就顯示類名，否則顯示 tag
//                .setLogHeadSwitch(true)// 設置 log 頭信息開關，默認為開
//                .setLog2FileSwitch(false)// 打印 log 時是否存到文件的開關，默認關
//                .setDir("")// 當自定義路徑為空時，寫入應用的/cache/log/目錄中
//                .setFilePrefix("")// 當文件前綴為空時，默認為"util"，即寫入文件為"util-MM-dd.txt"
//                .setBorderSwitch(true)// 輸出日志是否帶邊框開關，默認開
//                .setConsoleFilter(LogUtils.V)// log 的控制台過濾器，和 logcat 過濾器同理，默認 Verbose
//                .setFileFilter(LogUtils.V)// log 文件過濾器，和 logcat 過濾器同理，默認 Verbose
//                .setStackDeep(1);// log 棧深度，默認為 1
//        LogUtils.d(config.toString());
        KLog.init(DEBUG);
    }

    @SuppressLint("MissingPermission")
    private void initCrash() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                LogUtils.e(crashInfo);
                AppUtils.relaunchApp();
            }
        });
    }
    private void initAssets() {
//        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
//            ThreadPoolUtils poolUtils = new ThreadPoolUtils(ThreadPoolUtils.SingleThread, 1);
//            poolUtils.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        FileIOUtils.writeFileFromIS(Config.TEST_APK_PATH, getAssets().open("test_install"), false);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
//            LogUtils.d("test apk existed.");
//        }
    }
}
