package com.yuchin.utils.util;

import com.yuchin.utils.constant.RegexConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 正則相關工具類
 * </pre>
 */
public final class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // If u want more please visit http://toutiao.com/i6231678548520731137
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 驗證手機號（簡單）
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 驗證手機號（精確）
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 驗證電話號碼
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_TEL, input);
    }

    /**
     * 驗證身份證號碼 15 位
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input);
    }

    /**
     * 驗證身份證號碼 18 位
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input);
    }

    /**
     * 驗證郵箱
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    /**
     * 驗證 URL
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_URL, input);
    }

    /**
     * 驗證漢字
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ZH, input);
    }

    /**
     * 驗證用戶名
     * <p>取值範圍為 a-z,A-Z,0-9,"_",漢字，不能以"_"結尾,用戶名必須是 6-20 位</p>
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_USERNAME, input);
    }

    /**
     * 驗證 yyyy-MM-dd 格式的日期校驗，已考慮平閏年
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_DATE, input);
    }

    /**
     * 驗證 IP 地址
     *
     * @param input 待驗證文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_IP, input);
    }

    /**
     * 判斷是否匹配正則
     *
     * @param regex 正則表達式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 獲取正則匹配的部分
     *
     * @param regex 正則表達式
     * @param input 要匹配的字符串
     * @return 正則匹配的部分
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 獲取正則匹配分組
     *
     * @param input 要分組的字符串
     * @param regex 正則表達式
     * @return 正則匹配分組
     */
    public static String[] getSplits(final String input, final String regex) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * 替換正則匹配的第一部分
     *
     * @param input       要替換的字符串
     * @param regex       正則表達式
     * @param replacement 代替者
     * @return 替換正則匹配的第一部分
     */
    public static String getReplaceFirst(final String input,
                                         final String regex,
                                         final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替換所有正則匹配的部分
     *
     * @param input       要替換的字符串
     * @param regex       正則表達式
     * @param replacement 代替者
     * @return 替換所有正則匹配的部分
     */
    public static String getReplaceAll(final String input,
                                       final String regex,
                                       final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
