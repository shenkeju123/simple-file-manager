package com.filemanager.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件路径工具类
 *
 * @author filemanager
 */
@Component
public class FilePathUtils {

    /**
     * 系统路径分隔符
     */
    public static final String SEPARATOR = File.separator;

    /**
     * 组合路径
     *
     * @param basePath 基础路径
     * @param paths    子路径
     * @return 组合后的路径
     */
    public static String combinePath(String basePath, String... paths) {
        if (basePath == null || basePath.trim().isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(normalizePath(basePath));

        for (String path : paths) {
            if (path != null && !path.trim().isEmpty()) {
                String normalizedPath = normalizePath(path);
                
                // 确保中间有分隔符，但避免重复的分隔符
                if (!builder.toString().endsWith(SEPARATOR) && !normalizedPath.startsWith(SEPARATOR)) {
                    builder.append(SEPARATOR);
                } else if (builder.toString().endsWith(SEPARATOR) && normalizedPath.startsWith(SEPARATOR)) {
                    normalizedPath = normalizedPath.substring(1);
                }
                
                builder.append(normalizedPath);
            }
        }

        return builder.toString();
    }

    /**
     * 规范化路径，处理路径分隔符
     *
     * @param path 原始路径
     * @return 规范化后的路径
     */
    public static String normalizePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return "";
        }

        // 将所有反斜杠转为正斜杠（Windows兼容）
        String normalizedPath = path.replace('\\', '/');
        
        // 使用Java NIO Path进行规范化处理
        try {
            Path pathObj = Paths.get(normalizedPath);
            normalizedPath = pathObj.normalize().toString();
        } catch (Exception e) {
            // 如果路径不合法，保留原始路径
        }
        
        // 转换为系统分隔符
        return normalizedPath.replace('/', File.separatorChar);
    }

    /**
     * 获取文件的父路径
     *
     * @param filePath 文件路径
     * @return 父路径
     */
    public static String getParentPath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return "";
        }

        File file = new File(filePath);
        String parentPath = file.getParent();
        return parentPath != null ? parentPath : "";
    }

    /**
     * 获取文件名（不含路径）
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return "";
        }

        File file = new File(filePath);
        return file.getName();
    }

    /**
     * 判断路径是否为绝对路径
     *
     * @param path 路径
     * @return 是否为绝对路径
     */
    public static boolean isAbsolutePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }

        return new File(path).isAbsolute();
    }

    /**
     * 将相对路径转换为绝对路径
     *
     * @param basePath 基础路径
     * @param relativePath 相对路径
     * @return 绝对路径
     */
    public static String toAbsolutePath(String basePath, String relativePath) {
        if (isAbsolutePath(relativePath)) {
            return normalizePath(relativePath);
        }

        return combinePath(basePath, relativePath);
    }

    /**
     * 检查给定的路径是否在指定的基础路径下（安全检查）
     *
     * @param basePath 基础路径
     * @param checkPath 待检查的路径
     * @return 是否在基础路径下
     */
    public static boolean isSubPath(String basePath, String checkPath) {
        if (basePath == null || checkPath == null) {
            return false;
        }

        String normalizedBasePath = normalizePath(basePath);
        String normalizedCheckPath = normalizePath(checkPath);

        try {
            Path base = Paths.get(normalizedBasePath).toAbsolutePath().normalize();
            Path check = Paths.get(normalizedCheckPath).toAbsolutePath().normalize();

            return check.startsWith(base);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建文件的父目录
     *
     * @param filePath 文件路径
     * @return 是否创建成功
     */
    public static boolean createParentDirectories(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        File file = new File(filePath);
        File parentDir = file.getParentFile();
        
        if (parentDir != null && !parentDir.exists()) {
            return parentDir.mkdirs();
        }
        
        return true;
    }

    /**
     * 获取相对路径
     *
     * @param basePath 基础路径
     * @param fullPath 完整路径
     * @return 相对路径
     */
    public static String getRelativePath(String basePath, String fullPath) {
        if (basePath == null || fullPath == null) {
            return fullPath;
        }

        try {
            Path base = Paths.get(normalizePath(basePath));
            Path full = Paths.get(normalizePath(fullPath));
            
            return base.relativize(full).toString().replace('\\', '/');
        } catch (Exception e) {
            // 如果无法计算相对路径，返回原路径
            return fullPath;
        }
    }
    
    /**
     * 判断文件扩展名是否合法（不包含路径分隔符等特殊字符）
     *
     * @param extension 扩展名
     * @return 是否合法
     */
    public static boolean isValidExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return true;
        }
        
        // 扩展名不应包含路径分隔符和其他特殊字符
        return !extension.contains("/") && 
               !extension.contains("\\") && 
               !extension.contains("..") &&
               !extension.contains(":");
    }
}