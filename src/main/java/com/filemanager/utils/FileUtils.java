package com.filemanager.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件工具类
 */
@Slf4j
public class FileUtils {

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名（不包含点）
     */
    public static String getExtension(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件名（不包含扩展名）
     *
     * @param fileName 文件名
     * @return 文件名（不包含扩展名）
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
    }

    /**
     * 生成唯一文件名
     *
     * @param originalFilename 原始文件名
     * @return 唯一文件名
     */
    public static String generateUniqueFileName(String originalFilename) {
        String extension = getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + (StringUtils.isNotBlank(extension) ? "." + extension : "");
    }

    /**
     * 生成基于日期的存储路径
     *
     * @return 存储路径，格式：yyyy/MM/dd/
     */
    public static String generateDatePath() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
    }

    /**
     * 计算文件MD5值
     *
     * @param file 文件
     * @return MD5值（32位小写）
     */
    public static String calculateMD5(MultipartFile file) {
        try {
            return calculateMD5(file.getInputStream());
        } catch (IOException e) {
            log.error("计算文件MD5值失败", e);
            return null;
        }
    }

    /**
     * 计算文件MD5值
     *
     * @param inputStream 输入流
     * @return MD5值（32位小写）
     */
    public static String calculateMD5(InputStream inputStream) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] digest = md.digest();
            return bytesToHex(digest);
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("计算文件MD5值失败", e);
            return null;
        }
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 根据文件扩展名获取MIME类型
     *
     * @param extension 文件扩展名
     * @return MIME类型
     */
    public static String getMimeType(String extension) {
        if (StringUtils.isBlank(extension)) {
            return "application/octet-stream";
        }
        
        extension = extension.toLowerCase();
        Map<String, String> mimeTypeMap = getMimeTypeMap();
        return mimeTypeMap.getOrDefault(extension, "application/octet-stream");
    }

    /**
     * 获取MIME类型映射
     *
     * @return MIME类型映射
     */
    private static Map<String, String> getMimeTypeMap() {
        Map<String, String> mimeTypeMap = new HashMap<>();
        // 图片类型
        mimeTypeMap.put("jpg", "image/jpeg");
        mimeTypeMap.put("jpeg", "image/jpeg");
        mimeTypeMap.put("png", "image/png");
        mimeTypeMap.put("gif", "image/gif");
        mimeTypeMap.put("bmp", "image/bmp");
        mimeTypeMap.put("svg", "image/svg+xml");
        mimeTypeMap.put("webp", "image/webp");
        
        // 文档类型
        mimeTypeMap.put("doc", "application/msword");
        mimeTypeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeTypeMap.put("xls", "application/vnd.ms-excel");
        mimeTypeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mimeTypeMap.put("ppt", "application/vnd.ms-powerpoint");
        mimeTypeMap.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mimeTypeMap.put("pdf", "application/pdf");
        mimeTypeMap.put("txt", "text/plain");
        mimeTypeMap.put("rtf", "application/rtf");
        
        // 音频类型
        mimeTypeMap.put("mp3", "audio/mpeg");
        mimeTypeMap.put("wav", "audio/wav");
        mimeTypeMap.put("ogg", "audio/ogg");
        mimeTypeMap.put("m4a", "audio/mp4");
        
        // 视频类型
        mimeTypeMap.put("mp4", "video/mp4");
        mimeTypeMap.put("avi", "video/x-msvideo");
        mimeTypeMap.put("mov", "video/quicktime");
        mimeTypeMap.put("wmv", "video/x-ms-wmv");
        mimeTypeMap.put("flv", "video/x-flv");
        mimeTypeMap.put("mkv", "video/x-matroska");
        
        // 压缩文件类型
        mimeTypeMap.put("zip", "application/zip");
        mimeTypeMap.put("rar", "application/x-rar-compressed");
        mimeTypeMap.put("7z", "application/x-7z-compressed");
        mimeTypeMap.put("tar", "application/x-tar");
        mimeTypeMap.put("gz", "application/gzip");
        
        // 代码文件类型
        mimeTypeMap.put("html", "text/html");
        mimeTypeMap.put("css", "text/css");
        mimeTypeMap.put("js", "application/javascript");
        mimeTypeMap.put("json", "application/json");
        mimeTypeMap.put("xml", "application/xml");
        mimeTypeMap.put("java", "text/x-java-source");
        mimeTypeMap.put("py", "text/x-python");
        mimeTypeMap.put("php", "application/x-httpd-php");
        
        return mimeTypeMap;
    }

    /**
     * 根据MIME类型获取文件类型
     *
     * @param mimeType MIME类型
     * @return 文件类型（0-普通文件，1-图片，2-文档，3-视频，4-音频，5-压缩包）
     */
    public static Integer getFileTypeFromMimeType(String mimeType) {
        if (StringUtils.isBlank(mimeType)) {
            return 0;
        }
        
        mimeType = mimeType.toLowerCase();
        
        if (mimeType.startsWith("image/")) {
            return 1;
        } else if (mimeType.startsWith("video/")) {
            return 3;
        } else if (mimeType.startsWith("audio/")) {
            return 4;
        } else if (mimeType.contains("zip") || mimeType.contains("rar") || mimeType.contains("tar") || mimeType.contains("7z") || mimeType.contains("gzip")) {
            return 5;
        } else if (mimeType.contains("pdf") || mimeType.contains("word") || mimeType.contains("excel") || mimeType.contains("powerpoint") || mimeType.contains("text/plain")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 根据文件扩展名判断文件是否可预览
     *
     * @param extension 文件扩展名
     * @return 是否可预览
     */
    public static boolean isPreviewable(String extension) {
        if (StringUtils.isBlank(extension)) {
            return false;
        }
        
        extension = extension.toLowerCase();
        
        // 图片类型
        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif") || extension.equals("bmp") || extension.equals("svg") || extension.equals("webp")) {
            return true;
        }
        
        // 文档类型
        if (extension.equals("pdf") || extension.equals("txt") || extension.equals("doc") || extension.equals("docx") || extension.equals("xls") || extension.equals("xlsx") || extension.equals("ppt") || extension.equals("pptx")) {
            return true;
        }
        
        // 视频类型
        if (extension.equals("mp4") || extension.equals("webm")) {
            return true;
        }
        
        // 音频类型
        if (extension.equals("mp3") || extension.equals("wav") || extension.equals("ogg")) {
            return true;
        }
        
        // 代码类型
        if (extension.equals("html") || extension.equals("css") || extension.equals("js") || extension.equals("json") || extension.equals("xml") || extension.equals("md")) {
            return true;
        }
        
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return 是否创建成功
     */
    public static boolean createDirectory(String path) {
        try {
            Path dirPath = Paths.get(path);
            Files.createDirectories(dirPath);
            return true;
        } catch (IOException e) {
            log.error("创建文件夹失败: {}", path, e);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return 是否删除成功
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        return file.delete();
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        return deleteFile(new File(path));
    }
}