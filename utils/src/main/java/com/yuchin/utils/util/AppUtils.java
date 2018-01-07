package com.yuchin.utils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : App 相關工具類
 * </pre>
 */
public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判斷 App 是否安裝
     *
     * @param action   action
     * @param category category
     * @return {@code true}: 已安裝<br>{@code false}: 未安裝
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = Utils.getApp().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判斷 App 是否安裝
     *
     * @param packageName 包名
     * @return {@code true}: 已安裝<br>{@code false}: 未安裝
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 安裝 App(支持 8.0)
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  文件路徑
     * @param authority 7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                  <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    public static void installApp(final String filePath, final String authority) {
        installApp(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 安裝 App（支持 8.0）
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                  <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    public static void installApp(final File file, final String authority) {
        if (!FileUtils.isFileExists(file)) return;
        Utils.getApp().startActivity(IntentUtils.getInstallAppIntent(file, authority, true));
    }

    /**
     * 安裝 App（支持 8.0）
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param filePath    文件路徑
     * @param authority   7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                    <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode 請求值
     */
    public static void installApp(final Activity activity,
                                  final String filePath,
                                  final String authority,
                                  final int requestCode) {
        installApp(activity, FileUtils.getFileByPath(filePath), authority, requestCode);
    }

    /**
     * 安裝 App（支持 8.0）
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param file        文件
     * @param authority   7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                    <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode 請求值
     */
    public static void installApp(final Activity activity,
                                  final File file,
                                  final String authority,
                                  final int requestCode) {
        if (!FileUtils.isFileExists(file)) return;
        activity.startActivityForResult(IntentUtils.getInstallAppIntent(file, authority),
                requestCode);
    }

    /**
     * 靜默安裝 App
     * <p>非 root 需添加權限
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路徑
     * @return {@code true}: 安裝成功<br>{@code false}: 安裝失敗
     */
    public static boolean installAppSilent(final String filePath) {
        File file = FileUtils.getFileByPath(filePath);
        if (!FileUtils.isFileExists(file)) return false;
        boolean isRoot = isDeviceRooted();
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRoot);
        if (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib64 pm install " + filePath;
            commandResult = ShellUtils.execCmd(command, isRoot, true);
            return commandResult.successMsg != null
                    && commandResult.successMsg.toLowerCase().contains("success");
        }
    }

    /**
     * 卸載 App
     *
     * @param packageName 包名
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) return;
        Utils.getApp().startActivity(IntentUtils.getUninstallAppIntent(packageName, true));
    }

    /**
     * 卸載 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 請求值
     */
    public static void uninstallApp(final Activity activity,
                                    final String packageName,
                                    final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 靜默卸載 App
     * <p>非 root 需添加權限
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName 包名
     * @param isKeepData  是否保留數據
     * @return {@code true}: 卸載成功<br>{@code false}: 卸載失敗
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        if (isSpace(packageName)) return false;
        boolean isRoot = isDeviceRooted();
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall "
                + (isKeepData ? "-k " : "")
                + packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRoot, true);
        if (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib64 pm uninstall "
                    + (isKeepData ? "-k " : "")
                    + packageName;
            commandResult = ShellUtils.execCmd(command, isRoot, true);
            return commandResult.successMsg != null
                    && commandResult.successMsg.toLowerCase().contains("success");
        }
    }

    /**
     * 判斷 App 是否有 root 權限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("echo root", true);
        if (result.result == 0) return true;
        if (result.errorMsg != null) {
            Log.d("AppUtils", "isAppRoot() called" + result.errorMsg);
        }
        return false;
    }

    /**
     * 打開App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) return;
        Utils.getApp().startActivity(IntentUtils.getLaunchAppIntent(packageName, true));
    }

    /**
     * 打開 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 請求值
     */
    public static void launchApp(final Activity activity,
                                 final String packageName,
                                 final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 關閉 App
     */
    public static void exitApp() {
        List<Activity> activityList = Utils.sActivityList;
        for (int i = activityList.size() - 1; i >= 0; --i) {
            activityList.get(i).finish();
            activityList.remove(i);
        }
        System.exit(0);
    }

    /**
     * 獲取 App 包名
     *
     * @return App 包名
     */
    public static String getAppPackageName() {
        return Utils.getApp().getPackageName();
    }

    /**
     * 獲取 App 具體設置
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 具體設置
     *
     * @param packageName 包名
     */
    public static void getAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) return;
        Utils.getApp().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName, true));
    }

    /**
     * 獲取 App 名稱
     *
     * @return App 名稱
     */
    public static String getAppName() {
        return getAppName(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 名稱
     *
     * @param packageName 包名
     * @return App 名稱
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 獲取 App 圖標
     *
     * @return App 圖標
     */
    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 圖標
     *
     * @param packageName 包名
     * @return App 圖標
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 獲取 App 路徑
     *
     * @return App 路徑
     */
    public static String getAppPath() {
        return getAppPath(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 路徑
     *
     * @param packageName 包名
     * @return App 路徑
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 獲取 App 版本號
     *
     * @return App 版本號
     */
    public static String getAppVersionName() {
        return getAppVersionName(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 版本號
     *
     * @param packageName 包名
     * @return App 版本號
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 獲取 App 版本碼
     *
     * @return App 版本碼
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 版本碼
     *
     * @param packageName 包名
     * @return App 版本碼
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判斷 App 是否是系統應用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(Utils.getApp().getPackageName());
    }

    /**
     * 判斷 App 是否是系統應用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判斷 App 是否是 Debug 版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return isAppDebug(Utils.getApp().getPackageName());
    }

    /**
     * 判斷 App 是否是 Debug 版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 獲取 App 簽名
     *
     * @return App 簽名
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 簽名
     *
     * @param packageName 包名
     * @return App 簽名
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 獲取應用簽名的的 SHA1 值
     * <p>可據此判斷高德，百度地圖 key 是否正確</p>
     *
     * @return 應用簽名的 SHA1 字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(Utils.getApp().getPackageName());
    }

    /**
     * 獲取應用簽名的的 SHA1 值
     * <p>可據此判斷高德，百度地圖 key 是否正確</p>
     *
     * @param packageName 包名
     * @return 應用簽名的 SHA1 字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1(final String packageName) {
        Signature[] signature = getAppSignature(packageName);
        if (signature == null) return null;
        return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 判斷 App 是否處於前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info;
        info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }

    /**
     * 判斷 App 是否處於前台
     * <p>當不是查看當前 App，且 SDK 大於 21 時，
     * 需添加權限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
    }

    /**
     * 獲取 App 信息
     * <p>AppInfo（名稱，圖標，包名，版本號，版本 Code，是否系統應用）</p>
     *
     * @return 當前應用的 AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(Utils.getApp().getPackageName());
    }

    /**
     * 獲取 App 信息
     * <p>AppInfo（名稱，圖標，包名，版本號，版本 Code，是否系統應用）</p>
     *
     * @param packageName 包名
     * @return 當前應用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到 AppInfo 的 Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo 類
     */
    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 獲取所有已安裝 App 信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}
     * （名稱，圖標，包名，包路徑，版本號，版本 Code，是否系統應用）</p>
     * <p>依賴上面的 getBean 方法</p>
     *
     * @return 所有已安裝的 AppInfo 列表
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = Utils.getApp().getPackageManager();
        // 獲取系統中安裝的所有軟件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    /**
     * 清除 App 所有數據
     *
     * @param dirPaths 目錄路徑
     * @return {@code true}: 成功<br>{@code false}: 失敗
     */
    public static boolean cleanAppData(final String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除 App 所有數據
     *
     * @param dirs 目錄
     * @return {@code true}: 成功<br>{@code false}: 失敗
     */
    public static boolean cleanAppData(final File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDbs();
        isSuccess &= CleanUtils.cleanInternalSP();
        isSuccess &= CleanUtils.cleanInternalFiles();
        isSuccess &= CleanUtils.cleanExternalCache();
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
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

    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打開 App
     *
     * @param packageName 應用包名
     * @return
     */
    public boolean isAppInstall(String packageName) {
        // 獲取packagemanager
        final PackageManager packageManager = Utils.getApp().getPackageManager();
        // 獲取所有已安裝程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用於存儲所有已安裝程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 從pinfo中將包名字逐一取出，壓入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判斷packageNames中是否有目標程序的包名，有TRUE，沒有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 封裝 App 信息的 Bean 類
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        /**
         * @param name        名稱
         * @param icon        圖標
         * @param packageName 包名
         * @param packagePath 包路徑
         * @param versionName 版本號
         * @param versionCode 版本碼
         * @param isSystem    是否系統應用
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }
}
