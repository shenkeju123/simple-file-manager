package com.filemanager.util;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件MIME类型工具类
 *
 * @author filemanager
 */
@Component
public class FileMimeTypeUtils {

    /**
     * 常见文件扩展名到MIME类型的映射
     */
    private static final Map<String, String> MIME_TYPE_MAP = new HashMap<>();
    
    /**
     * 可预览的文件类型集合
     */
    private static final Set<String> PREVIEWABLE_TYPES = new HashSet<>();
    
    /**
     * 可编辑的文件类型集合
     */
    private static final Set<String> EDITABLE_TYPES = new HashSet<>();
    
    static {
        // 初始化MIME类型映射
        // 图像文件
        MIME_TYPE_MAP.put("jpg", "image/jpeg");
        MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        MIME_TYPE_MAP.put("png", "image/png");
        MIME_TYPE_MAP.put("gif", "image/gif");
        MIME_TYPE_MAP.put("bmp", "image/bmp");
        MIME_TYPE_MAP.put("svg", "image/svg+xml");
        MIME_TYPE_MAP.put("webp", "image/webp");
        MIME_TYPE_MAP.put("ico", "image/x-icon");
        
        // 文档文件
        MIME_TYPE_MAP.put("txt", "text/plain");
        MIME_TYPE_MAP.put("htm", "text/html");
        MIME_TYPE_MAP.put("html", "text/html");
        MIME_TYPE_MAP.put("css", "text/css");
        MIME_TYPE_MAP.put("js", "application/javascript");
        MIME_TYPE_MAP.put("json", "application/json");
        MIME_TYPE_MAP.put("xml", "application/xml");
        MIME_TYPE_MAP.put("csv", "text/csv");
        MIME_TYPE_MAP.put("md", "text/markdown");
        MIME_TYPE_MAP.put("pdf", "application/pdf");
        MIME_TYPE_MAP.put("doc", "application/msword");
        MIME_TYPE_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
        MIME_TYPE_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        
        // 视频文件
        MIME_TYPE_MAP.put("mp4", "video/mp4");
        MIME_TYPE_MAP.put("webm", "video/webm");
        MIME_TYPE_MAP.put("avi", "video/x-msvideo");
        MIME_TYPE_MAP.put("mov", "video/quicktime");
        MIME_TYPE_MAP.put("wmv", "video/x-ms-wmv");
        MIME_TYPE_MAP.put("flv", "video/x-flv");
        MIME_TYPE_MAP.put("mkv", "video/x-matroska");
        
        // 音频文件
        MIME_TYPE_MAP.put("mp3", "audio/mpeg");
        MIME_TYPE_MAP.put("wav", "audio/wav");
        MIME_TYPE_MAP.put("ogg", "audio/ogg");
        MIME_TYPE_MAP.put("m4a", "audio/mp4");
        MIME_TYPE_MAP.put("flac", "audio/flac");
        MIME_TYPE_MAP.put("aac", "audio/aac");
        
        // 压缩文件
        MIME_TYPE_MAP.put("zip", "application/zip");
        MIME_TYPE_MAP.put("rar", "application/x-rar-compressed");
        MIME_TYPE_MAP.put("7z", "application/x-7z-compressed");
        MIME_TYPE_MAP.put("tar", "application/x-tar");
        MIME_TYPE_MAP.put("gz", "application/gzip");
        
        // 可执行文件
        MIME_TYPE_MAP.put("exe", "application/x-msdownload");
        MIME_TYPE_MAP.put("msi", "application/x-msi");
        MIME_TYPE_MAP.put("apk", "application/vnd.android.package-archive");
        MIME_TYPE_MAP.put("dmg", "application/x-apple-diskimage");
        
        // 代码文件
        MIME_TYPE_MAP.put("java", "text/x-java-source");
        MIME_TYPE_MAP.put("py", "text/x-python");
        MIME_TYPE_MAP.put("c", "text/x-c");
        MIME_TYPE_MAP.put("cpp", "text/x-c++");
        MIME_TYPE_MAP.put("h", "text/x-c");
        MIME_TYPE_MAP.put("php", "application/x-httpd-php");
        MIME_TYPE_MAP.put("sh", "application/x-sh");
        MIME_TYPE_MAP.put("sql", "application/x-sql");
        
        // 初始化可预览的文件类型
        PREVIEWABLE_TYPES.addAll(Arrays.asList(
            // 图像
            "jpg", "jpeg", "png", "gif", "bmp", "svg", "webp",
            // 文档
            "txt", "htm", "html", "css", "js", "json", "xml", "csv", "md", "pdf",
            // 视频
            "mp4", "webm",
            // 音频
            "mp3", "wav", "ogg", "m4a"
        ));
        
        // 初始化可编辑的文件类型
        EDITABLE_TYPES.addAll(Arrays.asList(
            "txt", "htm", "html", "css", "js", "json", "xml", "csv", "md",
            "java", "py", "c", "cpp", "h", "php", "sh", "sql"
        ));
    }
    
    /**
     * 根据文件扩展名获取MIME类型
     *
     * @param extension 文件扩展名
     * @return MIME类型
     */
    public static String getMimeTypeByExtension(String extension) {
        if (extension == null || extension.trim().isEmpty()) {
            return "application/octet-stream";
        }
        
        extension = extension.toLowerCase().trim();
        if (extension.startsWith(".")) {
            extension = extension.substring(1);
        }
        
        String mimeType = MIME_TYPE_MAP.get(extension);
        return mimeType != null ? mimeType : "application/octet-stream";
    }
    
    /**
     * 根据文件名获取MIME类型
     *
     * @param filename 文件名
     * @return MIME类型
     */
    public static String getMimeTypeByFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return "application/octet-stream";
        }
        
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex > 0) {
            String extension = filename.substring(lastDotIndex + 1).toLowerCase();
            return getMimeTypeByExtension(extension);
        }
        
        return "application/octet-stream";
    }
    
    /**
     * 判断文件是否为图片
     *
     * @param filename 文件名
     * @return 是否为图片
     */
    public static boolean isImage(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType.startsWith("image/");
    }
    
    /**
     * 判断文件是否为文档
     *
     * @param filename 文件名
     * @return 是否为文档
     */
    public static boolean isDocument(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType.startsWith("text/") || 
               mimeType.equals("application/pdf") ||
               mimeType.equals("application/msword") ||
               mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
               mimeType.equals("application/vnd.ms-excel") ||
               mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
               mimeType.equals("application/vnd.ms-powerpoint") ||
               mimeType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation");
    }
    
    /**
     * 判断文件是否为视频
     *
     * @param filename 文件名
     * @return 是否为视频
     */
    public static boolean isVideo(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType.startsWith("video/");
    }
    
    /**
     * 判断文件是否为音频
     *
     * @param filename 文件名
     * @return 是否为音频
     */
    public static boolean isAudio(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType.startsWith("audio/");
    }
    
    /**
     * 判断文件是否为压缩文件
     *
     * @param filename 文件名
     * @return 是否为压缩文件
     */
    public static boolean isArchive(String filename) {
        String extension = FileNameUtils.getExtension(filename);
        return Arrays.asList("zip", "rar", "7z", "tar", "gz").contains(extension);
    }
    
    /**
     * 判断文件是否可预览
     *
     * @param filename 文件名
     * @return 是否可预览
     */
    public static boolean isPreviewable(String filename) {
        String extension = FileNameUtils.getExtension(filename);
        return PREVIEWABLE_TYPES.contains(extension);
    }
    
    /**
     * 判断文件是否可编辑
     *
     * @param filename 文件名
     * @return 是否可编辑
     */
    public static boolean isEditable(String filename) {
        String extension = FileNameUtils.getExtension(filename);
        return EDITABLE_TYPES.contains(extension);
    }
    
    /**
     * 获取文件的图标类型名称（用于前端展示）
     *
     * @param filename 文件名
     * @return 图标类型名称
     */
    public static String getFileIconType(String filename) {
        String extension = FileNameUtils.getExtension(filename);
        
        if (isImage(filename)) {
            return "image";
        } else if (isVideo(filename)) {
            return "video";
        } else if (isAudio(filename)) {
            return "audio";
        } else if (isArchive(filename)) {
            return "archive";
        } else if (isDocument(filename)) {
            // 针对特定文档类型返回特定图标
            switch (extension) {
                case "pdf":
                    return "pdf";
                case "doc":
                case "docx":
                    return "word";
                case "xls":
                case "xlsx":
                    return "excel";
                case "ppt":
                case "pptx":
                    return "powerpoint";
                case "txt":
                case "md":
                    return "text";
                case "html":
                case "htm":
                    return "html";
                default:
                    return "document";
            }
        } else if (isEditable(filename)) {
            return "code";
        } else {
            return "file";
        }
    }
    
    /**
     * 添加自定义MIME类型
     *
     * @param extension 文件扩展名
     * @param mimeType MIME类型
     */
    public static void addCustomMimeType(String extension, String mimeType) {
        if (extension != null && !extension.trim().isEmpty() && 
            mimeType != null && !mimeType.trim().isEmpty()) {
            MIME_TYPE_MAP.put(extension.toLowerCase().trim(), mimeType);
        }
    }
    
    /**
     * 添加可预览的文件类型
     *
     * @param extension 文件扩展名
     */
    public static void addPreviewableType(String extension) {
        if (extension != null && !extension.trim().isEmpty()) {
            PREVIEWABLE_TYPES.add(extension.toLowerCase().trim());
        }
    }
    
    /**
     * 添加可编辑的文件类型
     *
     * @param extension 文件扩展名
     */
    public static void addEditableType(String extension) {
        if (extension != null && !extension.trim().isEmpty()) {
            EDITABLE_TYPES.add(extension.toLowerCase().trim());
        }
    }
}