package com.yuchin.utils.util;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : 意圖相關工具類
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 獲取安裝 App（支持 8.0）的意圖
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  文件路徑
     * @param authority 7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                  <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安裝 App（支持 8.0）的意圖
     */
    public static Intent getInstallAppIntent(final String filePath, final String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 獲取安裝 App(支持 8.0)的意圖
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                  <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安裝 App(支持 8.0)的意圖
     */
    public static Intent getInstallAppIntent(final File file, final String authority) {
        return getInstallAppIntent(file, authority, false);
    }

    /**
     * 獲取安裝 App(支持 8.0)的意圖
     * <p>8.0 需添加權限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安裝需要傳入清單文件中的{@code <provider>}的 authorities 屬性
     *                  <br>參看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param isNewTask 是否開啟新的任務棧
     * @return 安裝 App(支持 8.0)的意圖
     */
    public static Intent getInstallAppIntent(final File file,
                                             final String authority,
                                             final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
        }
        intent.setDataAndType(data, type);
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取卸載 App 的意圖
     *
     * @param packageName 包名
     * @return 卸載 App 的意圖
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    /**
     * 獲取卸載 App 的意圖
     *
     * @param packageName 包名
     * @param isNewTask   是否開啟新的任務棧
     * @return 卸載 App 的意圖
     */
    public static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取打開 App 的意圖
     *
     * @param packageName 包名
     * @return 打開 App 的意圖
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    /**
     * 獲取打開 App 的意圖
     *
     * @param packageName 包名
     * @param isNewTask   是否開啟新的任務棧
     * @return 打開 App 的意圖
     */
    public static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = Utils.getApp().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取 App 具體設置的意圖
     *
     * @param packageName 包名
     * @return App 具體設置的意圖
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName) {
        return getAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * 獲取 App 具體設置的意圖
     *
     * @param packageName 包名
     * @param isNewTask   是否開啟新的任務棧
     * @return App 具體設置的意圖
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName,
                                                     final boolean isNewTask) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取分享文本的意圖
     *
     * @param content 分享文本
     * @return 分享文本的意圖
     */
    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /**
     * 獲取分享文本的意圖
     *
     * @param content   分享文本
     * @param isNewTask 是否開啟新的任務棧
     * @return 分享文本的意圖
     */

    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取分享圖片的意圖
     *
     * @param content   文本
     * @param imagePath 圖片文件路徑
     * @return 分享圖片的意圖
     */
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * 獲取分享圖片的意圖
     *
     * @param content   文本
     * @param imagePath 圖片文件路徑
     * @param isNewTask 是否開啟新的任務棧
     * @return 分享圖片的意圖
     */
    public static Intent getShareImageIntent(final String content,
                                             final String imagePath,
                                             final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        return getShareImageIntent(content, new File(imagePath), isNewTask);
    }

    /**
     * 獲取分享圖片的意圖
     *
     * @param content 文本
     * @param image   圖片文件
     * @return 分享圖片的意圖
     */
    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * 獲取分享圖片的意圖
     *
     * @param content   文本
     * @param image     圖片文件
     * @param isNewTask 是否開啟新的任務棧
     * @return 分享圖片的意圖
     */
    public static Intent getShareImageIntent(final String content,
                                             final File image,
                                             final boolean isNewTask) {
        if (image != null && image.isFile()) return null;
        return getShareImageIntent(content, Uri.fromFile(image), isNewTask);
    }

    /**
     * 獲取分享圖片的意圖
     *
     * @param content 分享文本
     * @param uri     圖片 uri
     * @return 分享圖片的意圖
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * 獲取分享圖片的意圖
     *
     * @param content   分享文本
     * @param uri       圖片 uri
     * @param isNewTask 是否開啟新的任務棧
     * @return 分享圖片的意圖
     */
    public static Intent getShareImageIntent(final String content,
                                             final Uri uri,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取其他應用組件的意圖
     *
     * @param packageName 包名
     * @param className   全類名
     * @return 其他應用組件的意圖
     */
    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null, false);
    }

    /**
     * 獲取其他應用組件的意圖
     *
     * @param packageName 包名
     * @param className   全類名
     * @param isNewTask   是否開啟新的任務棧
     * @return 其他應用組件的意圖
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final boolean isNewTask) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    /**
     * 獲取其他應用組件的意圖
     *
     * @param packageName 包名
     * @param className   全類名
     * @param bundle      bundle
     * @return 其他應用組件的意圖
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    /**
     * 獲取其他應用組件的意圖
     *
     * @param packageName 包名
     * @param className   全類名
     * @param bundle      bundle
     * @param isNewTask   是否開啟新的任務棧
     * @return 其他應用組件的意圖
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle,
                                            final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取關機的意圖
     * <p>需添加權限 {@code <uses-permission android:name="android.permission.SHUTDOWN" />}</p>
     *
     * @return 關機的意圖
     */
    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    /**
     * 獲取關機的意圖
     * <p>需添加權限 {@code <uses-permission android:name="android.permission.SHUTDOWN" />}</p>
     *
     * @param isNewTask 是否開啟新的任務棧
     * @return 關機的意圖
     */
    public static Intent getShutdownIntent(final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取跳至撥號界面意圖
     *
     * @param phoneNumber 電話號碼
     * @return 跳至撥號界面意圖
     */
    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    /**
     * 獲取跳至撥號界面意圖
     *
     * @param phoneNumber 電話號碼
     * @param isNewTask   是否開啟新的任務棧
     * @return 跳至撥號界面意圖
     */
    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取撥打電話意圖
     * <p>需添加權限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 電話號碼
     * @return 撥打電話意圖
     */
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * 獲取撥打電話意圖
     * <p>需添加權限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 電話號碼
     * @param isNewTask   是否開啟新的任務棧
     * @return 撥打電話意圖
     */
    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取發送短信界面的意圖
     *
     * @param phoneNumber 接收號碼
     * @param content     短信內容
     * @return 發送短信界面的意圖
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 獲取跳至發送短信界面的意圖
     *
     * @param phoneNumber 接收號碼
     * @param content     短信內容
     * @param isNewTask   是否開啟新的任務棧
     * @return 發送短信界面的意圖
     */
    public static Intent getSendSmsIntent(final String phoneNumber,
                                          final String content,
                                          final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 獲取拍照的意圖
     *
     * @param outUri 輸出的 uri
     * @return 拍照的意圖
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    /**
     * 獲取拍照的意圖
     *
     * @param outUri    輸出的 uri
     * @param isNewTask 是否開啟新的任務棧
     * @return 拍照的意圖
     */
    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return getIntent(intent, isNewTask);
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

//    /**
//     * 獲取選擇照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        return intent.setType("image*//*");
//    }
//
//    /**
//     * 獲取從文件中選擇照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithDocuments() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        return intent.setType("image*//*");
//    }
//
//
//    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
//                                             int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//        }
//        intent.setType("image*//*");
//        intent.putExtra("output", saveTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
//                                              int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uriFrom, "image*//*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("output", uriTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCaptureIntent(final Uri uri) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        return intent;
//    }
}
