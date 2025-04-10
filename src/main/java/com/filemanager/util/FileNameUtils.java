package com.filemanager.util;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件名工具类
 *
 * @author filemanager
 */
public class FileNameUtils {

    /**
     * 不安全的文件名字符正则表达式
     */
    private static final Pattern UNSAFE_FILENAME_PATTERN = Pattern.compile("[\\\\/:*?\"<>|]");
    
    /**
     * 文件扩展名分隔符
     */
    private static final String EXTENSION_SEPARATOR = ".";

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名（不包含点）
     */
    public static String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        
        int dotIndex = fileName.lastIndexOf(EXTENSION_SEPARATOR);
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";
        }
        
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param fileName 文件名
     * @return 不带扩展名的文件名
     */
    public static String getNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        
        int dotIndex = fileName.lastIndexOf(EXTENSION_SEPARATOR);
        if (dotIndex == -1) {
            return fileName;
        }
        
        return fileName.substring(0, dotIndex);
    }

    /**
     * 清理文件名中的不安全字符
     *
     * @param fileName 原始文件名
     * @return 安全的文件名
     */
    public static String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        
        Matcher matcher = UNSAFE_FILENAME_PATTERN.matcher(fileName);
        return matcher.replaceAll("_");
    }

    /**
     * 生成带有唯一标识的文件名
     *
     * @param originalFileName 原始文件名
     * @return 带有唯一标识的文件名
     */
    public static String generateUniqueFileName(String originalFileName) {
        String nameWithoutExt = getNameWithoutExtension(originalFileName);
        String extension = getExtension(originalFileName);
        
        String uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        
        if (extension.isEmpty()) {
            return nameWithoutExt + "_" + uniqueId;
        } else {
            return nameWithoutExt + "_" + uniqueId + EXTENSION_SEPARATOR + extension;
        }
    }

    /**
     * 根据文件路径获取文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        
        int lastSeparatorIndex = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        return lastSeparatorIndex == -1 ? filePath : filePath.substring(lastSeparatorIndex + 1);
    }

    /**
     * 检查文件名是否包含扩展名
     *
     * @param fileName 文件名
     * @return 是否包含扩展名
     */
    public static boolean hasExtension(String fileName) {
        return !getExtension(fileName).isEmpty();
    }

    /**
     * 添加或替换文件扩展名
     *
     * @param fileName  文件名
     * @param extension 新的扩展名（不包含点）
     * @return 更新后的文件名
     */
    public static String replaceExtension(String fileName, String extension) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        
        String nameWithoutExt = getNameWithoutExtension(fileName);
        
        if (extension == null || extension.isEmpty()) {
            return nameWithoutExt;
        }
        
        return nameWithoutExt + EXTENSION_SEPARATOR + extension;
    }

    /**
     * 合并文件路径
     *
     * @param basePath 基础路径
     * @param fileName 文件名
     * @return 合并后的路径
     */
    public static String combinePath(String basePath, String fileName) {
        if (basePath == null || basePath.isEmpty()) {
            return fileName;
        }
        
        if (fileName == null || fileName.isEmpty()) {
            return basePath;
        }
        
        char lastChar = basePath.charAt(basePath.length() - 1);
        
        if (lastChar == '/' || lastChar == '\\') {
            return basePath + fileName;
        } else {
            return basePath + File.separator + fileName;
        }
    }

    /**
     * 生成缩略图文件名
     *
     * @param originalFileName 原始文件名
     * @return 缩略图文件名
     */
    public static String generateThumbnailFileName(String originalFileName) {
        String nameWithoutExt = getNameWithoutExtension(originalFileName);
        String extension = getExtension(originalFileName);
        
        if (extension.isEmpty()) {
            return nameWithoutExt + "_thumb";
        } else {
            return nameWithoutExt + "_thumb" + EXTENSION_SEPARATOR + extension;
        }
    }
}