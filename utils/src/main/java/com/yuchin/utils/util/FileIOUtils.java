package com.yuchin.utils.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/22
 *     desc  : 文件讀寫相關工具類
 * </pre>
 */
public final class FileIOUtils {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static int sBufferSize = 8192;

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 將輸入流寫入文件
     *
     * @param filePath 路徑
     * @param is       輸入流
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return writeFileFromIS(getFileByPath(filePath), is, false);
    }

    /**
     * 將輸入流寫入文件
     *
     * @param filePath 路徑
     * @param is       輸入流
     * @param append   是否追加在文件末
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * 將輸入流寫入文件
     *
     * @param file 文件
     * @param is   輸入流
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromIS(final File file, final InputStream is) {
        return writeFileFromIS(file, is, false);
    }

    /**
     * 將輸入流寫入文件
     *
     * @param file   文件
     * @param is     輸入流
     * @param append 是否追加在文件末
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append) {
        if (!createOrExistsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(is, os);
        }
    }

    /**
     * 將字節數組寫入文件
     *
     * @param filePath 文件路徑
     * @param bytes    字節數組
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param filePath 文件路徑
     * @param bytes    字節數組
     * @param append   是否追加在文件末
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByStream(final String filePath,
                                                     final byte[] bytes,
                                                     final boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param file  文件
     * @param bytes 字節數組
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param file   文件
     * @param bytes  字節數組
     * @param append 是否追加在文件末
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByStream(final File file,
                                                     final byte[] bytes,
                                                     final boolean append) {
        if (bytes == null || !createOrExistsFile(file)) return false;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bos);
        }
    }

    /**
     * 將字節數組寫入文件
     *
     * @param filePath 文件路徑
     * @param bytes    字節數組
     * @param isForce  是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param filePath 文件路徑
     * @param bytes    字節數組
     * @param append   是否追加在文件末
     * @param isForce  是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param file    文件
     * @param bytes   字節數組
     * @param isForce 是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param file    文件
     * @param bytes   字節數組
     * @param append  是否追加在文件末
     * @param isForce 是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        if (bytes == null) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 將字節數組寫入文件
     *
     * @param filePath 文件路徑
     * @param bytes    字節數組
     * @param isForce  是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param filePath 文件路徑
     * @param bytes    字節數組
     * @param append   是否追加在文件末
     * @param isForce  是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param file    文件
     * @param bytes   字節數組
     * @param isForce 是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * 將字節數組寫入文件
     *
     * @param file    文件
     * @param bytes   字節數組
     * @param append  是否追加在文件末
     * @param isForce 是否寫入文件
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        if (bytes == null || !createOrExistsFile(file)) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) mbb.force();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 將字符串寫入文件
     *
     * @param filePath 文件路徑
     * @param content  寫入內容
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    /**
     * 將字符串寫入文件
     *
     * @param filePath 文件路徑
     * @param content  寫入內容
     * @param append   是否追加在文件末
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromString(final String filePath,
                                              final String content,
                                              final boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 將字符串寫入文件
     *
     * @param file    文件
     * @param content 寫入內容
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    /**
     * 將字符串寫入文件
     *
     * @param file    文件
     * @param content 寫入內容
     * @param append  是否追加在文件末
     * @return {@code true}: 寫入成功<br>{@code false}: 寫入失敗
     */
    public static boolean writeFileFromString(final File file,
                                              final String content,
                                              final boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // the divide line of write and read
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param filePath 文件路徑
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param filePath    文件路徑
     * @param charsetName 編碼格式
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param file 文件
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param file        文件
     * @param charsetName 編碼格式
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final File file, final String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param filePath 文件路徑
     * @param st       需要讀取的開始行數
     * @param end      需要讀取的結束行數
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final String filePath, final int st, final int end) {
        return readFile2List(getFileByPath(filePath), st, end, null);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param filePath    文件路徑
     * @param st          需要讀取的開始行數
     * @param end         需要讀取的結束行數
     * @param charsetName 編碼格式
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final String filePath,
                                             final int st,
                                             final int end,
                                             final String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param file 文件
     * @param st   需要讀取的開始行數
     * @param end  需要讀取的結束行數
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final File file, final int st, final int end) {
        return readFile2List(file, st, end, null);
    }

    /**
     * 讀取文件到字符串鏈表中
     *
     * @param file        文件
     * @param st          需要讀取的開始行數
     * @param end         需要讀取的結束行數
     * @param charsetName 編碼格式
     * @return 字符串鏈表中
     */
    public static List<String> readFile2List(final File file,
                                             final int st,
                                             final int end,
                                             final String charsetName) {
        if (!isFileExists(file)) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file), charsetName)
                );
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * 讀取文件到字符串中
     *
     * @param filePath 文件路徑
     * @return 字符串
     */
    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }

    /**
     * 讀取文件到字符串中
     *
     * @param filePath    文件路徑
     * @param charsetName 編碼格式
     * @return 字符串
     */
    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * 讀取文件到字符串中
     *
     * @param file 文件
     * @return 字符串
     */
    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

    /**
     * 讀取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 編碼格式
     * @return 字符串
     */
    public static String readFile2String(final File file, final String charsetName) {
        if (!isFileExists(file)) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file), charsetName)
                );
            }
            String line;
            if ((line = reader.readLine()) != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(LINE_SEP).append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * 讀取文件到字節數組中
     *
     * @param filePath 文件路徑
     * @return 字符數組
     */
    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    /**
     * 讀取文件到字節數組中
     *
     * @param file 文件
     * @return 字符數組
     */
    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file)) return null;
        FileInputStream fis = null;
        ByteArrayOutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            byte[] b = new byte[sBufferSize];
            int len;
            while ((len = fis.read(b, 0, sBufferSize)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fis, os);
        }
    }

    /**
     * 讀取文件到字節數組中
     *
     * @param filePath 文件路徑
     * @return 字符數組
     */
    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    /**
     * 讀取文件到字節數組中
     *
     * @param file 文件
     * @return 字符數組
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 讀取文件到字節數組中
     *
     * @param filePath 文件路徑
     * @return 字符數組
     */
    public static byte[] readFile2BytesByMap(final String filePath) {
        return readFile2BytesByMap(getFileByPath(filePath));
    }

    /**
     * 讀取文件到字節數組中
     *
     * @param file 文件
     * @return 字符數組
     */
    public static byte[] readFile2BytesByMap(final File file) {
        if (!isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int size = (int) fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] result = new byte[size];
            mbb.get(result, 0, size);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 設置緩沖區尺寸
     *
     * @param bufferSize 緩沖區大小
     */
    public static void setBufferSize(final int bufferSize) {
        sBufferSize = bufferSize;
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
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
}
