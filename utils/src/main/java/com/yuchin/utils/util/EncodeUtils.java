package com.yuchin.utils.util;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/07
 *     desc  : 編碼解碼相關工具類
 * </pre>
 */
public final class EncodeUtils {

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * URL 編碼
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要編碼的字符
     * @return 編碼為 UTF-8 的字符串
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * URL 編碼
     * <p>若系統不支持指定的編碼字符集,則直接將 input 原樣返回</p>
     *
     * @param input   要編碼的字符
     * @param charset 字符集
     * @return 編碼為字符集的字符串
     */
    public static String urlEncode(final String input, final String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL 解碼
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解碼的字符串
     * @return URL 解碼後的字符串
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL 解碼
     * <p>若系統不支持指定的解碼字符集,則直接將 input 原樣返回</p>
     *
     * @param input   要解碼的字符串
     * @param charset 字符集
     * @return URL 解碼為指定字符集的字符串
     */
    public static String urlDecode(final String input, final String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * Base64 編碼
     *
     * @param input 要編碼的字符串
     * @return Base64 編碼後的字符串
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * Base64 編碼
     *
     * @param input 要編碼的字節數組
     * @return Base64 編碼後的字符串
     */
    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 編碼
     *
     * @param input 要編碼的字節數組
     * @return Base64 編碼後的字符串
     */
    public static String base64Encode2String(final byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解碼
     *
     * @param input 要解碼的字符串
     * @return Base64 解碼後的字符串
     */
    public static byte[] base64Decode(final String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解碼
     *
     * @param input 要解碼的字符串
     * @return Base64 解碼後的字符串
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64URL 安全編碼
     * <p>將 Base64 中的 URL 非法字符�?,/=轉為其他字符, 見 RFC3548</p>
     *
     * @param input 要 Base64URL 安全編碼的字符串
     * @return Base64URL 安全編碼後的字符串
     */
    public static byte[] base64UrlSafeEncode(final String input) {
        return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }

    /**
     * Html 編碼
     *
     * @param input 要 Html 編碼的字符串
     * @return Html 編碼後的字符串
     */
    public static String htmlEncode(final CharSequence input) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Html 解碼
     *
     * @param input 待解碼的字符串
     * @return Html 解碼後的字符串
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }
}
