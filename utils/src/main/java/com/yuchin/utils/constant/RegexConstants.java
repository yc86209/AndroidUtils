package com.yuchin.utils.constant;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : 正則相關常量
 * </pre>
 */
public final class RegexConstants {

    /**
     * 正則：手機號（簡單）
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 正則：手機號（精確）
     * <p>移動：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</p>
     * <p>聯通：130、131、132、145、155、156、166、171、175、176、185、186</p>
     * <p>電信：133、153、173、177、180、181、189、199</p>
     * <p>全球星：1349</p>
     * <p>虛擬運營商：170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$";
    /**
     * 正則：電話號碼
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * 正則：身份證號碼 15 位
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正則：身份證號碼 18 位
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 正則：郵箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正則：URL
     */
    public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    /**
     * 正則：漢字
     */
    public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 正則：用戶名，取值範圍為 a-z,A-Z,0-9,"_",漢字，不能以"_"結尾,用戶名必須是 6-20 位
     */
    public static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * 正則：yyyy-MM-dd 格式的日期校驗，已考慮平閏年
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 正則：IP 地址
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    ///////////////////////////////////////////////////////////////////////////
    // 以下摘自 http://tool.oschina.net/regex
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 正則：雙字節字符(包括漢字在內)
     */
    public static final String REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
    /**
     * 正則：空白行
     */
    public static final String REGEX_BLANK_LINE = "\\n\\s*\\r";
    /**
     * 正則：QQ 號
     */
    public static final String REGEX_TENCENT_NUM = "[1-9][0-9]{4,}";
    /**
     * 正則：中國郵政編碼
     */
    public static final String REGEX_ZIP_CODE = "[1-9]\\d{5}(?!\\d)";
    /**
     * 正則：正整數
     */
    public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    /**
     * 正則：負整數
     */
    public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    /**
     * 正則：整數
     */
    public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
    /**
     * 正則：非負整數(正整數 + 0)
     */
    public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    /**
     * 正則：非正整數（負整數 + 0）
     */
    public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
    /**
     * 正則：正浮點數
     */
    public static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    /**
     * 正則：負浮點數
     */
    public static final String REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";

    ///////////////////////////////////////////////////////////////////////////
    // If u want more please visit http://toutiao.com/i6231678548520731137
    ///////////////////////////////////////////////////////////////////////////
}