package com.yuchin.utils.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/27
 *     desc  : 壓縮相關工具類
 * </pre>
 */
public final class ZipUtils {

    private static final int BUFFER_LEN = 8192;

    private ZipUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 壓縮文件
     *
     * @param resFilePath 待壓縮文件路徑
     * @param zipFilePath 壓縮文件路徑
     * @return {@code true}: 壓縮成功<br>{@code false}: 壓縮失敗
     * @throws IOException IO 錯誤時拋出
     */
    public static boolean zipFile(final String resFilePath,
                                  final String zipFilePath)
            throws IOException {
        return zipFile(resFilePath, zipFilePath, null);
    }

    /**
     * 壓縮文件
     *
     * @param resFilePath 待壓縮文件路徑
     * @param zipFilePath 壓縮文件路徑
     * @param comment     壓縮文件的註釋
     * @return {@code true}: 壓縮成功<br>{@code false}: 壓縮失敗
     * @throws IOException IO 錯誤時拋出
     */
    public static boolean zipFile(final String resFilePath,
                                  final String zipFilePath,
                                  final String comment)
            throws IOException {
        return zipFile(getFileByPath(resFilePath), getFileByPath(zipFilePath), comment);
    }

    /**
     * 壓縮文件
     *
     * @param resFile 待壓縮文件
     * @param zipFile 壓縮文件
     * @return {@code true}: 壓縮成功<br>{@code false}: 壓縮失敗
     * @throws IOException IO 錯誤時拋出
     */
    public static boolean zipFile(final File resFile,
                                  final File zipFile)
            throws IOException {
        return zipFile(resFile, zipFile, null);
    }

    /**
     * 壓縮文件
     *
     * @param resFile 待壓縮文件
     * @param zipFile 壓縮文件
     * @param comment 壓縮文件的註釋
     * @return {@code true}: 壓縮成功<br>{@code false}: 壓縮失敗
     * @throws IOException IO 錯誤時拋出
     */
    public static boolean zipFile(final File resFile,
                                  final File zipFile,
                                  final String comment)
            throws IOException {
        if (resFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(resFile, "", zos, comment);
        } finally {
            if (zos != null) {
                CloseUtils.closeIO(zos);
            }
        }
    }

    /**
     * 壓縮文件
     *
     * @param resFile  待壓縮文件
     * @param rootPath 相對於壓縮文件的路徑
     * @param zos      壓縮文件輸出流
     * @param comment  壓縮文件的註釋
     * @return {@code true}: 壓縮成功<br>{@code false}: 壓縮失敗
     * @throws IOException IO 錯誤時拋出
     */
    private static boolean zipFile(final File resFile,
                                   String rootPath,
                                   final ZipOutputStream zos,
                                   final String comment)
            throws IOException {
        rootPath = rootPath + (isSpace(rootPath) ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            // 如果是空文件夾那麽創建它，我把'/'換為File.separator測試就不成功，eggPain
            if (fileList == null || fileList.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                if (!isSpace(comment)) entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    // 如果遞歸返回 false 則返回 false
                    if (!zipFile(file, rootPath, zos, comment)) return false;
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                ZipEntry entry = new ZipEntry(rootPath);
                if (!isSpace(comment)) entry.setComment(comment);
                zos.putNextEntry(entry);
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                CloseUtils.closeIO(is);
            }
        }
        return true;
    }

    /**
     * 解壓文件
     *
     * @param zipFilePath 待解壓文件路徑
     * @param destDirPath 目標目錄路徑
     * @return 文件鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<File> unzipFile(final String zipFilePath,
                                       final String destDirPath)
            throws IOException {
        return unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    /**
     * 解壓文件
     *
     * @param zipFile 待解壓文件
     * @param destDir 目標目錄
     * @return 文件鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<File> unzipFile(final File zipFile,
                                       final File destDir)
            throws IOException {
        return unzipFileByKeyword(zipFile, destDir, null);
    }

    /**
     * 解壓帶有關鍵字的文件
     *
     * @param zipFilePath 待解壓文件路徑
     * @param destDirPath 目標目錄路徑
     * @param keyword     關鍵字
     * @return 返回帶有關鍵字的文件鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<File> unzipFileByKeyword(final String zipFilePath,
                                                final String destDirPath,
                                                final String keyword)
            throws IOException {
        return unzipFileByKeyword(getFileByPath(zipFilePath), getFileByPath(destDirPath), keyword);
    }

    /**
     * 解壓帶有關鍵字的文件
     *
     * @param zipFile 待解壓文件
     * @param destDir 目標目錄
     * @param keyword 關鍵字
     * @return 返回帶有關鍵字的文件鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<File> unzipFileByKeyword(final File zipFile,
                                                final File destDir,
                                                final String keyword)
            throws IOException {
        if (zipFile == null || destDir == null) return null;
        List<File> files = new ArrayList<>();
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        if (isSpace(keyword)) {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (!unzipChildFile(destDir, files, zf, entry, entryName)) return files;
            }
        } else {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(keyword)) {
                    if (!unzipChildFile(destDir, files, zf, entry, entryName)) return files;
                }
            }
        }
        return files;
    }

    private static boolean unzipChildFile(final File destDir,
                                          final List<File> files,
                                          final ZipFile zf,
                                          final ZipEntry entry,
                                          final String entryName) throws IOException {
        String filePath = destDir + File.separator + entryName;
        File file = new File(filePath);
        files.add(file);
        if (entry.isDirectory()) {
            if (!createOrExistsDir(file)) return false;
        } else {
            if (!createOrExistsFile(file)) return false;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(zf.getInputStream(entry));
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                CloseUtils.closeIO(in, out);
            }
        }
        return true;
    }

    /**
     * 獲取壓縮文件中的文件路徑鏈表
     *
     * @param zipFilePath 壓縮文件路徑
     * @return 壓縮文件中的文件路徑鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<String> getFilesPath(final String zipFilePath)
            throws IOException {
        return getFilesPath(getFileByPath(zipFilePath));
    }

    /**
     * 獲取壓縮文件中的文件路徑鏈表
     *
     * @param zipFile 壓縮文件
     * @return 壓縮文件中的文件路徑鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<String> getFilesPath(final File zipFile)
            throws IOException {
        if (zipFile == null) return null;
        List<String> paths = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            paths.add(((ZipEntry) entries.nextElement()).getName());
        }
        return paths;
    }

    /**
     * 獲取壓縮文件中的註釋鏈表
     *
     * @param zipFilePath 壓縮文件路徑
     * @return 壓縮文件中的註釋鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<String> getComments(final String zipFilePath)
            throws IOException {
        return getComments(getFileByPath(zipFilePath));
    }

    /**
     * 獲取壓縮文件中的註釋鏈表
     *
     * @param zipFile 壓縮文件
     * @return 壓縮文件中的註釋鏈表
     * @throws IOException IO 錯誤時拋出
     */
    public static List<String> getComments(final File zipFile)
            throws IOException {
        if (zipFile == null) return null;
        List<String> comments = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            comments.add(entry.getComment());
        }
        return comments;
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
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

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
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
