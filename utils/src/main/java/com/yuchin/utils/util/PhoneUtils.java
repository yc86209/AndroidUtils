package com.yuchin.utils.util;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 手機相關工具類
 * </pre>
 */
public final class PhoneUtils {

    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判斷設備是否是手機
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 獲取 IMEI 碼
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return IMEI 碼
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 獲取 IMSI 碼
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return IMSI 碼
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMSI() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 獲取移動終端類型
     *
     * @return 手機制式
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手機制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手機制式為 GSM，移動和聯通</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手機制式為 CDMA，電信</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     */
    public static int getPhoneType() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 判斷 sim 卡是否準備好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 獲取 Sim 卡運營商名稱
     * <p>中國移動、如中國聯通、中國電信</p>
     *
     * @return sim 卡運營商名稱
     */
    public static String getSimOperatorName() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : null;
    }

    /**
     * 獲取 Sim 卡運營商名稱
     * <p>中國移動、如中國聯通、中國電信</p>
     *
     * @return 移動網絡運營商名稱
     */
    public static String getSimOperatorByMnc() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return null;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中國移動";
            case "46001":
                return "中國聯通";
            case "46003":
                return "中國電信";
            default:
                return operator;
        }
    }

    /**
     * 獲取手機狀態信息
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return DeviceId(IMEI) = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中國電信<br>
     * NetworkType = 6<br>
     * PhoneType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中國電信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getPhoneStatus() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return "";
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "PhoneType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 跳至撥號界面
     *
     * @param phoneNumber 電話號碼
     */
    public static void dial(final String phoneNumber) {
        Utils.getApp().startActivity(IntentUtils.getDialIntent(phoneNumber, true));
    }

    /**
     * 撥打電話
     * <p>需添加權限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 電話號碼
     */
    public static void call(final String phoneNumber) {
        Utils.getApp().startActivity(IntentUtils.getCallIntent(phoneNumber, true));
    }

    /**
     * 跳至發送短信界面
     *
     * @param phoneNumber 接收號碼
     * @param content     短信內容
     */
    public static void sendSms(final String phoneNumber, final String content) {
        Utils.getApp().startActivity(IntentUtils.getSendSmsIntent(phoneNumber, content, true));
    }

    /**
     * 發送短信
     * <p>需添加權限 {@code <uses-permission android:name="android.permission.SEND_SMS" />}</p>
     *
     * @param phoneNumber 接收號碼
     * @param content     短信內容
     */
    public static void sendSmsSilent(final String phoneNumber, final String content) {
        if (StringUtils.isEmpty(content)) return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(Utils.getApp(), 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }

    /**
     * 獲取手機聯系人
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />}</p>
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.READ_CONTACTS" />}</p>
     */
    public static void getAllContactInfo() {
        Log.i("PhoneUtils", "Please refer to the following code.");
        /*
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        // 1.獲取內容解析者
        ContentResolver resolver = Utils.getApp().getContentResolver();
        // 2.獲取內容提供者的地址:com.android.contacts
        // raw_contacts 表的地址 :raw_contacts
        // view_data 表的地址 : data
        // 3.生成查詢地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查詢操作,先查詢 raw_contacts,查詢 contact_id
        // projection : 查詢的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
        try {
            // 5.解析 cursor
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 6.獲取查詢的數據
                    String contact_id = cursor.getString(0);
                    // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                    // : 查詢字段在 cursor 中索引值,一般都是用在查詢字段比較多的時候
                    // 判斷 contact_id 是否為空
                    if (!StringUtils.isEmpty(contact_id)) {//null   ""
                        // 7.根據 contact_id 查詢 view_data 表中的數據
                        // selection : 查詢條件
                        // selectionArgs :查詢條件的參數
                        // sortOrder : 排序
                        // 空指針: 1.null.方法 2.參數為 null
                        Cursor c = resolver.query(date_uri, new String[]{"data1",
                                        "mimetype"}, "raw_contact_id=?",
                                new String[]{contact_id}, null);
                        HashMap<String, String> map = new HashMap<String, String>();
                        // 8.解析 c
                        if (c != null) {
                            while (c.moveToNext()) {
                                // 9.獲取數據
                                String data1 = c.getString(0);
                                String mimetype = c.getString(1);
                                // 10.根據類型去判斷獲取的 data1 數據並保存
                                if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                                    // 電話
                                    map.put("phone", data1);
                                } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                                    // 姓名
                                    map.put("name", data1);
                                }
                            }
                        }
                        // 11.添加到集合中數據
                        list.add(map);
                        // 12.關閉 cursor
                        if (c != null) {
                            c.close();
                        }
                    }
                }
            }
        } finally {
            // 12.關閉 cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
        */
    }

    /**
     * 打開手機聯系人界面點擊聯系人後便獲取該號碼
     * <p>參照以下註釋代碼</p>
     */
    public static void getContactNum() {
        Log.i("PhoneUtils", "Please refer to the following code.");
        /*
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0);

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri uri = data.getData();
                String num = null;
                // 創建內容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri,
                        null, null, null, null);
                while (cursor.moveToNext()) {
                    num = cursor.getString(cursor.getColumnIndex("data1"));
                }
                cursor.close();
                num = num.replaceAll("-", "");//替換的操作,555-6 -> 5556
            }
        }
        */
    }

    /**
     * 獲取手機短信並保存到 xml 中
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     * <p>需添加權限
     * {@code <uses-permission android:name="android.permission.READ_SMS" />}</p>
     */
    public static void getAllSMS() {
        Log.i("PhoneUtils", "Please refer to the following code.");
        /*
        // 1.獲取短信
        // 1.1獲取內容解析者
        ContentResolver resolver = Utils.getApp().getContentResolver();
        // 1.2獲取內容提供者地址   sms,sms表的地址:null  不寫
        // 1.3獲取查詢路徑
        Uri uri = Uri.parse("content://sms");
        // 1.4.查詢操作
        // projection : 查詢的字段
        // selection : 查詢的條件
        // selectionArgs : 查詢條件的參數
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri,
                new String[]{"address", "date", "type", "body"},
                null,
                null,
                null
        );
        // 設置最大進度
        int count = cursor.getCount();//獲取短信的個數
        // 2.備份短信
        // 2.1獲取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2設置xml文件保存的路徑
            // os : 保存的位置
            // encoding : 編碼格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")),
                    "utf-8"
            );
            // 2.3設置頭信息
            // standalone : 是否獨立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4設置根標簽
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5設置短信的標簽
                xmlSerializer.startTag(null, "sms");
                // 2.6設置文本內容的標簽
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7設置文本內容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                Log.i("PhoneUtils", "address: " + address
                        + ", date: " + date
                        + ", type: " + type
                        + ", body: " + body
                );
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8將數據刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}
