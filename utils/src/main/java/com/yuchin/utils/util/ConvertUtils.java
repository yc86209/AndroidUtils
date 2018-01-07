package com.yuchin.utils.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.yuchin.utils.constant.MemoryConstants;
import com.yuchin.utils.constant.TimeConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/13
 *     desc  : 轉換相關工具類
 * </pre>
 */
public final class ConvertUtils {

    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * byteArr 轉 hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字節數組
     * @return 16 進制大寫字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * hexString 轉 byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六進制字符串
     * @return 字節數組
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar 轉 int
     *
     * @param hexChar hex 單個字節
     * @return 0..15
     */
    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArr 轉 byteArr
     *
     * @param chars 字符數組
     * @return 字節數組
     */
    public static byte[] chars2Bytes(final char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * byteArr 轉 charArr
     *
     * @param bytes 字節數組
     * @return 字符數組
     */
    public static char[] bytes2Chars(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * 以 unit 為單位的內存大小轉字節數
     *
     * @param memorySize 大小
     * @param unit       單位類型
     *                   <ul>
     *                   <li>{@link MemoryConstants#BYTE}: 字節</li>
     *                   <li>{@link MemoryConstants#KB}  : 千字節</li>
     *                   <li>{@link MemoryConstants#MB}  : 兆</li>
     *                   <li>{@link MemoryConstants#GB}  : GB</li>
     *                   </ul>
     * @return 字節數
     */
    public static long memorySize2Byte(final long memorySize, @MemoryConstants.Unit final int unit) {
        if (memorySize < 0) return -1;
        return memorySize * unit;
    }

    /**
     * 字節數轉以 unit 為單位的內存大小
     *
     * @param byteNum 字節數
     * @param unit    單位類型
     *                <ul>
     *                <li>{@link MemoryConstants#BYTE}: 字節</li>
     *                <li>{@link MemoryConstants#KB}  : 千字節</li>
     *                <li>{@link MemoryConstants#MB}  : 兆</li>
     *                <li>{@link MemoryConstants#GB}  : GB</li>
     *                </ul>
     * @return 以 unit 為單位的 size
     */
    public static double byte2MemorySize(final long byteNum, @MemoryConstants.Unit final int unit) {
        if (byteNum < 0) return -1;
        return (double) byteNum / unit;
    }

    /**
     * 字節數轉合適內存大小
     * <p>保留 3 位小數</p>
     *
     * @param byteNum 字節數
     * @return 合適內存大小
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < MemoryConstants.KB) {
            return String.format("%.3fB", (double) byteNum);
        } else if (byteNum < MemoryConstants.MB) {
            return String.format("%.3fKB", (double) byteNum / MemoryConstants.KB);
        } else if (byteNum < MemoryConstants.GB) {
            return String.format("%.3fMB", (double) byteNum / MemoryConstants.MB);
        } else {
            return String.format("%.3fGB", (double) byteNum / MemoryConstants.GB);
        }
    }

    /**
     * 以 unit 為單位的時間長度轉毫秒時間戳
     *
     * @param timeSpan 毫秒時間戳
     * @param unit     單位類型
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                 <li>{@link TimeConstants#SEC }: 秒</li>
     *                 <li>{@link TimeConstants#MIN }: 分</li>
     *                 <li>{@link TimeConstants#HOUR}: 小時</li>
     *                 <li>{@link TimeConstants#DAY }: 天</li>
     *                 </ul>
     * @return 毫秒時間戳
     */
    public static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    /**
     * 毫秒時間戳轉以 unit 為單位的時間長度
     *
     * @param millis 毫秒時間戳
     * @param unit   單位類型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小時</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return 以 unit 為單位的時間長度
     */
    public static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * 毫秒時間戳轉合適時間長度
     *
     * @param millis    毫秒時間戳
     *                  <p>小於等於 0，返回 null</p>
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小時</li>
     *                  <li>precision = 3，返回天、小時和分鐘</li>
     *                  <li>precision = 4，返回天、小時、分鐘和秒</li>
     *                  <li>precision &gt;= 5，返回天、小時、分鐘、秒和毫秒</li>
     *                  </ul>
     * @return 合適時間長度
     */
    @SuppressLint("DefaultLocale")
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小時", "分鐘", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * bytes 轉 bits
     *
     * @param bytes 字節數組
     * @return bits
     */
    public static String bytes2Bits(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * bits 轉 bytes
     *
     * @param bits 二進制
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // 不是 8 的倍數前面補 0
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * inputStream 轉 outputStream
     *
     * @param is 輸入流
     * @return outputStream 子類
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[MemoryConstants.KB];
            int len;
            while ((len = is.read(b, 0, MemoryConstants.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(is);
        }
    }

    /**
     * inputStream 轉 byteArr
     *
     * @param is 輸入流
     * @return 字節數組
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * byteArr 轉 inputStream
     *
     * @param bytes 字節數組
     * @return 輸入流
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream 轉 byteArr
     *
     * @param out 輸出流
     * @return 字節數組
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * outputStream 轉 byteArr
     *
     * @param bytes 字節數組
     * @return 字節數組
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(os);
        }
    }

    /**
     * inputStream 轉 string 按編碼
     *
     * @param is          輸入流
     * @param charsetName 編碼格式
     * @return 字符串
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null || isSpace(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string 轉 inputStream 按編碼
     *
     * @param string      字符串
     * @param charsetName 編碼格式
     * @return 輸入流
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream 轉 string 按編碼
     *
     * @param out         輸出流
     * @param charsetName 編碼格式
     * @return 字符串
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null || isSpace(charsetName)) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string 轉 outputStream 按編碼
     *
     * @param string      字符串
     * @param charsetName 編碼格式
     * @return 輸入流
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * bitmap 轉 byteArr
     *
     * @param bitmap bitmap 對象
     * @param format 格式
     * @return 字節數組
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byteArr 轉 bitmap
     *
     * @param bytes 字節數組
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * drawable 轉 bitmap
     *
     * @param drawable drawable 對象
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap 轉 drawable
     *
     * @param bitmap bitmap 對象
     * @return drawable
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(Utils.getApp().getResources(), bitmap);
    }

    /**
     * drawable 轉 byteArr
     *
     * @param drawable drawable 對象
     * @param format   格式
     * @return 字節數組
     */
    public static byte[] drawable2Bytes(final Drawable drawable,
                                        final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * byteArr 轉 drawable
     *
     * @param bytes 字節數組
     * @return drawable
     */
    public static Drawable bytes2Drawable(final byte[] bytes) {
        return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes));
    }

    /**
     * view 轉 Bitmap
     *
     * @param view 視圖
     * @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret =
                Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * dp 轉 px
     *
     * @param dpValue dp 值
     * @return px 值
     */
    public static int dp2px(final float dpValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 轉 dp
     *
     * @param pxValue px 值
     * @return dp 值
     */
    public static int px2dp(final float pxValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 轉 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 轉 sp
     *
     * @param pxValue px 值
     * @return sp 值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 判斷字符串是否為 null 或全為空白字符
     *
     * @param s 待校驗字符串
     * @return {@code true}: null 或全空白字符<br> {@code false}: 不為 null 且不全空白字符
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * outputStream 轉 inputStream
     *
     * @param out 輸出流
     * @return inputStream 子類
     */
    public ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }
}
