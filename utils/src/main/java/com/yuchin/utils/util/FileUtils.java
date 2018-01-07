package com.yuchin.utils.util;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/05/03
 *     desc  : 文件相關工具類
 * </pre>
 */
public final class FileUtils {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 根據文件路徑獲取文件
     *
     * @param filePath 文件路徑
     * @return 文件
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判斷文件是否存在
     *
     * @param filePath 文件路徑
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判斷文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路徑
     * @param newName  新名稱
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失敗
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 重命名文件
     *
     * @param file    文件
     * @param newName 新名稱
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失敗
     */
    public static boolean rename(final File file, final String newName) {
        // 文件為空返回 false
        if (file == null) return false;
        // 文件不存在返回 false
        if (!file.exists()) return false;
        // 新的文件名為空返回 false
        if (isSpace(newName)) return false;
        // 如果文件名沒有改變返回 true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回 false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * 判斷是否是目錄
     *
     * @param dirPath 目錄路徑
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判斷是否是目錄
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判斷是否是文件
     *
     * @param filePath 文件路徑
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判斷是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * 判斷目錄是否存在，不存在則判斷是否創建成功
     *
     * @param dirPath 目錄路徑
     * @return {@code true}: 存在或創建成功<br>{@code false}: 不存在或創建失敗
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判斷目錄是否存在，不存在則判斷是否創建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或創建成功<br>{@code false}: 不存在或創建失敗
     */
    public static boolean createOrExistsDir(final File file) {
        // 如果存在，是目錄則返回 true，是文件則返回 false，不存在則返回是否創建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判斷文件是否存在，不存在則判斷是否創建成功
     *
     * @param filePath 文件路徑
     * @return {@code true}: 存在或創建成功<br>{@code false}: 不存在或創建失敗
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判斷文件是否存在，不存在則判斷是否創建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或創建成功<br>{@code false}: 不存在或創建失敗
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        // 如果存在，是文件則返回 true，是目錄則返回 false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判斷文件是否存在，存在則在創建之前刪除
     *
     * @param filePath 文件路徑
     * @return {@code true}: 創建成功<br>{@code false}: 創建失敗
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判斷文件是否存在，存在則在創建之前刪除
     *
     * @param file 文件
     * @return {@code true}: 創建成功<br>{@code false}: 創建失敗
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        // 文件存在並且刪除失敗返回 false
        if (file.exists() && !file.delete()) return false;
        // 創建目錄失敗返回 false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 覆制或移動目錄
     *
     * @param srcDirPath  源目錄路徑
     * @param destDirPath 目標目錄路徑
     * @param listener    是否覆蓋監聽器
     * @param isMove      是否移動
     * @return {@code true}: 覆制或移動成功<br>{@code false}: 覆制或移動失敗
     */
    private static boolean copyOrMoveDir(final String srcDirPath,
                                         final String destDirPath,
                                         final OnReplaceListener listener,
                                         final boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath),
                getFileByPath(destDirPath),
                listener,
                isMove
        );
    }

    /**
     * 覆制或移動目錄
     *
     * @param srcDir   源目錄
     * @param destDir  目標目錄
     * @param listener 是否覆蓋監聽器
     * @param isMove   是否移動
     * @return {@code true}: 覆制或移動成功<br>{@code false}: 覆制或移動失敗
     */
    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final OnReplaceListener listener,
                                         final boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        // 如果目標目錄在源目錄中則返回 false，看不懂的話好好想想遞歸怎麽結束
        // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
        // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
        // 為防止以上這種情況出現出現誤判，須分別在後面加個路徑分隔符
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        // 源文件不存在或者不是目錄則返回 false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (destDir.exists()) {
            if (listener.onReplace()) {// 需要覆蓋則刪除舊目錄
                if (!deleteAllInDir(destDir)) {// 刪除文件失敗的話返回 false
                    return false;
                }
            } else {// 不需要覆蓋直接返回即可 true
                return true;
            }
        }
        // 目標目錄不存在返回 false
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                // 如果操作失敗返回 false
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false;
            } else if (file.isDirectory()) {
                // 如果操作失敗返回 false
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 覆制或移動文件
     *
     * @param srcFilePath  源文件路徑
     * @param destFilePath 目標文件路徑
     * @param listener     是否覆蓋監聽器
     * @param isMove       是否移動
     * @return {@code true}: 覆制或移動成功<br>{@code false}: 覆制或移動失敗
     */
    private static boolean copyOrMoveFile(final String srcFilePath,
                                          final String destFilePath,
                                          final OnReplaceListener listener,
                                          final boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath),
                getFileByPath(destFilePath),
                listener,
                isMove
        );
    }

    /**
     * 覆制或移動文件
     *
     * @param srcFile  源文件
     * @param destFile 目標文件
     * @param listener 是否覆蓋監聽器
     * @param isMove   是否移動
     * @return {@code true}: 覆制或移動成功<br>{@code false}: 覆制或移動失敗
     */
    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final OnReplaceListener listener,
                                          final boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        // 如果源文件和目標文件相同則返回 false
        if (srcFile.equals(destFile)) return false;
        // 源文件不存在或者不是文件則返回 false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {// 目標文件存在
            if (listener.onReplace()) {// 需要覆蓋則刪除舊文件
                if (!destFile.delete()) {// 刪除文件失敗的話返回 false
                    return false;
                }
            } else {// 不需要覆蓋直接返回即可 true
                return true;
            }
        }
        // 目標目錄不存在返回 false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return FileIOUtils.writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 覆制目錄
     *
     * @param srcDirPath  源目錄路徑
     * @param destDirPath 目標目錄路徑
     * @param listener    是否覆蓋監聽器
     * @return {@code true}: 覆制成功<br>{@code false}: 覆制失敗
     */
    public static boolean copyDir(final String srcDirPath,
                                  final String destDirPath,
                                  final OnReplaceListener listener) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * 覆制目錄
     *
     * @param srcDir   源目錄
     * @param destDir  目標目錄
     * @param listener 是否覆蓋監聽器
     * @return {@code true}: 覆制成功<br>{@code false}: 覆制失敗
     */
    public static boolean copyDir(final File srcDir,
                                  final File destDir,
                                  final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    /**
     * 覆制文件
     *
     * @param srcFilePath  源文件路徑
     * @param destFilePath 目標文件路徑
     * @param listener     是否覆蓋監聽器
     * @return {@code true}: 覆制成功<br>{@code false}: 覆制失敗
     */
    public static boolean copyFile(final String srcFilePath,
                                   final String destFilePath,
                                   final OnReplaceListener listener) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * 覆制文件
     *
     * @param srcFile  源文件
     * @param destFile 目標文件
     * @param listener 是否覆蓋監聽器
     * @return {@code true}: 覆制成功<br>{@code false}: 覆制失敗
     */
    public static boolean copyFile(final File srcFile,
                                   final File destFile,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    /**
     * 移動目錄
     *
     * @param srcDirPath  源目錄路徑
     * @param destDirPath 目標目錄路徑
     * @param listener    是否覆蓋監聽器
     * @return {@code true}: 移動成功<br>{@code false}: 移動失敗
     */
    public static boolean moveDir(final String srcDirPath,
                                  final String destDirPath,
                                  final OnReplaceListener listener) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * 移動目錄
     *
     * @param srcDir   源目錄
     * @param destDir  目標目錄
     * @param listener 是否覆蓋監聽器
     * @return {@code true}: 移動成功<br>{@code false}: 移動失敗
     */
    public static boolean moveDir(final File srcDir,
                                  final File destDir,
                                  final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    /**
     * 移動文件
     *
     * @param srcFilePath  源文件路徑
     * @param destFilePath 目標文件路徑
     * @param listener     是否覆蓋監聽器
     * @return {@code true}: 移動成功<br>{@code false}: 移動失敗
     */
    public static boolean moveFile(final String srcFilePath,
                                   final String destFilePath,
                                   final OnReplaceListener listener) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * 移動文件
     *
     * @param srcFile  源文件
     * @param destFile 目標文件
     * @param listener 是否覆蓋監聽器
     * @return {@code true}: 移動成功<br>{@code false}: 移動失敗
     */
    public static boolean moveFile(final File srcFile,
                                   final File destFile,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    /**
     * 刪除目錄
     *
     * @param dirPath 目錄路徑
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 刪除目錄
     *
     * @param dir 目錄
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // 目錄不存在返回 true
        if (!dir.exists()) return true;
        // 不是目錄返回 false
        if (!dir.isDirectory()) return false;
        // 現在文件存在且是文件夾
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 刪除文件
     *
     * @param srcFilePath 文件路徑
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 刪除文件
     *
     * @param file 文件
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 刪除目錄下所有東西
     *
     * @param dirPath 目錄路徑
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * 刪除目錄下所有東西
     *
     * @param dir 目錄
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * 刪除目錄下所有文件
     *
     * @param dirPath 目錄路徑
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 刪除目錄下所有文件
     *
     * @param dir 目錄
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    /**
     * 刪除目錄下所有過濾的文件
     *
     * @param dirPath 目錄路徑
     * @param filter  過濾器
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteFilesInDirWithFilter(final String dirPath,
                                                     final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 刪除目錄下所有過濾的文件
     *
     * @param dir    目錄
     * @param filter 過濾器
     * @return {@code true}: 刪除成功<br>{@code false}: 刪除失敗
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        // 目錄不存在返回 true
        if (!dir.exists()) return true;
        // 不是目錄返回 false
        if (!dir.isDirectory()) return false;
        // 現在文件存在且是文件夾
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 獲取目錄下所有文件
     * <p>不遞歸進子目錄</p>
     *
     * @param dirPath 目錄路徑
     * @return 文件鏈表
     */
    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * 獲取目錄下所有文件
     * <p>不遞歸進子目錄</p>
     *
     * @param dir 目錄
     * @return 文件鏈表
     */
    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * 獲取目錄下所有文件
     *
     * @param dirPath     目錄路徑
     * @param isRecursive 是否遞歸進子目錄
     * @return 文件鏈表
     */
    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 獲取目錄下所有文件
     *
     * @param dir         目錄
     * @param isRecursive 是否遞歸進子目錄
     * @return 文件鏈表
     */
    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    /**
     * 獲取目錄下所有過濾的文件
     * <p>不遞歸進子目錄</p>
     *
     * @param dirPath 目錄路徑
     * @param filter  過濾器
     * @return 文件鏈表
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    /**
     * 獲取目錄下所有過濾的文件
     * <p>不遞歸進子目錄</p>
     *
     * @param dir    目錄
     * @param filter 過濾器
     * @return 文件鏈表
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    /**
     * 獲取目錄下所有過濾的文件
     *
     * @param dirPath     目錄路徑
     * @param filter      過濾器
     * @param isRecursive 是否遞歸進子目錄
     * @return 文件鏈表
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 獲取目錄下所有過濾的文件
     *
     * @param dir         目錄
     * @param filter      過濾器
     * @param isRecursive 是否遞歸進子目錄
     * @return 文件鏈表
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        if (!isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    //noinspection ConstantConditions
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**
     * 獲取文件最後修改的毫秒時間戳
     *
     * @param filePath 文件路徑
     * @return 文件最後修改的毫秒時間戳
     */

    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * 獲取文件最後修改的毫秒時間戳
     *
     * @param file 文件
     * @return 文件最後修改的毫秒時間戳
     */
    public static long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    /**
     * 簡單獲取文件編碼格式
     *
     * @param filePath 文件路徑
     * @return 文件編碼
     */
    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 簡單獲取文件編碼格式
     *
     * @param file 文件
     * @return 文件編碼
     */
    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 獲取文件行數
     *
     * @param filePath 文件路徑
     * @return 文件行數
     */
    public static int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * 獲取文件行數
     * <p>比 readLine 要快很多</p>
     *
     * @param file 文件
     * @return 文件行數
     */
    public static int getFileLines(final File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int readChars;
            if (LINE_SEP.endsWith("\n")) {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\n') ++count;
                    }
                }
            } else {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\r') ++count;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(is);
        }
        return count;
    }

    /**
     * 獲取目錄大小
     *
     * @param dirPath 目錄路徑
     * @return 文件大小
     */
    public static String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * 獲取目錄大小
     *
     * @param dir 目錄
     * @return 文件大小
     */
    public static String getDirSize(final File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 獲取文件大小
     *
     * @param filePath 文件路徑
     * @return 文件大小
     */
    public static String getFileSize(final String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 獲取文件大小
     *
     * @param file 文件
     * @return 文件大小
     */
    public static String getFileSize(final File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 獲取目錄長度
     *
     * @param dirPath 目錄路徑
     * @return 目錄長度
     */
    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * 獲取目錄長度
     *
     * @param dir 目錄
     * @return 目錄長度
     */
    public static long getDirLength(final File dir) {
        if (!isDir(dir)) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 獲取文件長度
     *
     * @param filePath 文件路徑
     * @return 文件長度
     */
    public static long getFileLength(final String filePath) {
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * 獲取文件長度
     *
     * @param file 文件
     * @return 文件長度
     */
    public static long getFileLength(final File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    /**
     * 獲取文件的 MD5 校驗碼
     *
     * @param filePath 文件路徑
     * @return 文件的 MD5 校驗碼
     */
    public static String getFileMD5ToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * 獲取文件的 MD5 校驗碼
     *
     * @param file 文件
     * @return 文件的 MD5 校驗碼
     */
    public static String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }

    /**
     * 獲取文件的 MD5 校驗碼
     *
     * @param filePath 文件路徑
     * @return 文件的 MD5 校驗碼
     */
    public static byte[] getFileMD5(final String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    /**
     * 獲取文件的 MD5 校驗碼
     *
     * @param file 文件
     * @return 文件的 MD5 校驗碼
     */
    public static byte[] getFileMD5(final File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(dis);
        }
        return null;
    }

    /**
     * 獲取全路徑中的最長目錄
     *
     * @param file 文件
     * @return filePath 最長目錄
     */
    public static String getDirName(final File file) {
        if (file == null) return null;
        return getDirName(file.getPath());
    }

    /**
     * 獲取全路徑中的最長目錄
     *
     * @param filePath 文件路徑
     * @return filePath 最長目錄
     */
    public static String getDirName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * 獲取全路徑中的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(final File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    /**
     * 獲取全路徑中的文件名
     *
     * @param filePath 文件路徑
     * @return 文件名
     */
    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 獲取全路徑中的不帶拓展名的文件名
     *
     * @param file 文件
     * @return 不帶拓展名的文件名
     */
    public static String getFileNameNoExtension(final File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * 獲取全路徑中的不帶拓展名的文件名
     *
     * @param filePath 文件路徑
     * @return 不帶拓展名的文件名
     */
    public static String getFileNameNoExtension(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 獲取全路徑中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    public static String getFileExtension(final File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    ///////////////////////////////////////////////////////////////////////////
    // copy from ConvertUtils
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 獲取全路徑中的文件拓展名
     *
     * @param filePath 文件路徑
     * @return 文件拓展名
     */
    public static String getFileExtension(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    /**
     * byteArr 轉 hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字節數組
     * @return 16 進制大寫字符串
     */
    private static String bytes2HexString(final byte[] bytes) {
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
     * 字節數轉合適內存大小
     * <p>保留 3 位小數</p>
     *
     * @param byteNum 字節數
     * @return 合適內存大小
     */
    @SuppressLint("DefaultLocale")
    private static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format("%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format("%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format("%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format("%.3fGB", (double) byteNum / 1073741824);
        }
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

    public interface OnReplaceListener {
        boolean onReplace();
    }
}
