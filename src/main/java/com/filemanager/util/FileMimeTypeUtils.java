package com.filemanager.util;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件MIME类型工具类
 * 
 * @author filemanager
 */
@Component
public class FileMimeTypeUtils {

    /**
     * 文件扩展名与MIME类型映射
     */
    private static final Map<String, String> MIME_TYPE_MAP = new HashMap<>();

    static {
        // 图片文件
        MIME_TYPE_MAP.put("jpg", "image/jpeg");
        MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        MIME_TYPE_MAP.put("png", "image/png");
        MIME_TYPE_MAP.put("gif", "image/gif");
        MIME_TYPE_MAP.put("bmp", "image/bmp");
        MIME_TYPE_MAP.put("webp", "image/webp");
        MIME_TYPE_MAP.put("svg", "image/svg+xml");
        
        // 文档文件
        MIME_TYPE_MAP.put("txt", "text/plain");
        MIME_TYPE_MAP.put("html", "text/html");
        MIME_TYPE_MAP.put("htm", "text/html");
        MIME_TYPE_MAP.put("pdf", "application/pdf");
        MIME_TYPE_MAP.put("doc", "application/msword");
        MIME_TYPE_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
        MIME_TYPE_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        MIME_TYPE_MAP.put("md", "text/markdown");
        
        // 音频文件
        MIME_TYPE_MAP.put("mp3", "audio/mpeg");
        MIME_TYPE_MAP.put("wav", "audio/wav");
        MIME_TYPE_MAP.put("ogg", "audio/ogg");
        MIME_TYPE_MAP.put("flac", "audio/flac");
        MIME_TYPE_MAP.put("aac", "audio/aac");
        
        // 视频文件
        MIME_TYPE_MAP.put("mp4", "video/mp4");
        MIME_TYPE_MAP.put("avi", "video/x-msvideo");
        MIME_TYPE_MAP.put("wmv", "video/x-ms-wmv");
        MIME_TYPE_MAP.put("flv", "video/x-flv");
        MIME_TYPE_MAP.put("mov", "video/quicktime");
        MIME_TYPE_MAP.put("mkv", "video/x-matroska");
        MIME_TYPE_MAP.put("webm", "video/webm");
        
        // 压缩文件
        MIME_TYPE_MAP.put("zip", "application/zip");
        MIME_TYPE_MAP.put("rar", "application/x-rar-compressed");
        MIME_TYPE_MAP.put("7z", "application/x-7z-compressed");
        MIME_TYPE_MAP.put("tar", "application/x-tar");
        MIME_TYPE_MAP.put("gz", "application/gzip");
        
        // 代码文件
        MIME_TYPE_MAP.put("java", "text/x-java-source");
        MIME_TYPE_MAP.put("js", "application/javascript");
        MIME_TYPE_MAP.put("css", "text/css");
        MIME_TYPE_MAP.put("json", "application/json");
        MIME_TYPE_MAP.put("xml", "application/xml");
        MIME_TYPE_MAP.put("py", "text/x-python");
        MIME_TYPE_MAP.put("c", "text/x-c");
        MIME_TYPE_MAP.put("cpp", "text/x-c++");
        MIME_TYPE_MAP.put("cs", "text/x-csharp");
        MIME_TYPE_MAP.put("go", "text/x-go");
        MIME_TYPE_MAP.put("php", "application/x-php");
        MIME_TYPE_MAP.put("rb", "application/x-ruby");
        MIME_TYPE_MAP.put("sql", "application/sql");
        
        // 其他文件类型
        MIME_TYPE_MAP.put("bin", "application/octet-stream");
    }

    /**
     * 根据文件扩展名获取MIME类型
     *
     * @param extension 文件扩展名
     * @return MIME类型
     */
    public static String getMimeTypeByExtension(String extension) {
        if (extension == null || extension.trim().isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        
        // 去除可能存在的点号和空格，并转换为小写
        extension = extension.trim().toLowerCase();
        if (extension.startsWith(".")) {
            extension = extension.substring(1);
        }
        
        return MIME_TYPE_MAP.getOrDefault(extension, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 根据文件名获取MIME类型
     *
     * @param filename 文件名
     * @return MIME类型
     */
    public static String getMimeTypeByFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        
        String extension = FileNameUtils.getExtension(filename);
        return getMimeTypeByExtension(extension);
    }

    /**
     * 判断文件是否为图片类型
     *
     * @param filename 文件名
     * @return 是否为图片
     */
    public static boolean isImage(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType != null && mimeType.startsWith("image/");
    }

    /**
     * 判断文件是否为文本类型
     *
     * @param filename 文件名
     * @return 是否为文本
     */
    public static boolean isText(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType != null && (mimeType.startsWith("text/") || 
                mimeType.equals("application/json") || 
                mimeType.equals("application/xml"));
    }

    /**
     * 判断文件是否为音频类型
     *
     * @param filename 文件名
     * @return 是否为音频
     */
    public static boolean isAudio(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType != null && mimeType.startsWith("audio/");
    }

    /**
     * 判断文件是否为视频类型
     *
     * @param filename 文件名
     * @return 是否为视频
     */
    public static boolean isVideo(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return mimeType != null && mimeType.startsWith("video/");
    }

    /**
     * 判断文件是否为PDF文档
     *
     * @param filename 文件名
     * @return 是否为PDF
     */
    public static boolean isPdf(String filename) {
        String mimeType = getMimeTypeByFilename(filename);
        return "application/pdf".equals(mimeType);
    }

    /**
     * 判断文件是否可在线预览
     * 
     * @param filename 文件名
     * @return 是否可预览
     */
    public static boolean isPreviewable(String filename) {
        return isImage(filename) || isText(filename) || isPdf(filename) || 
               isAudio(filename) || isVideo(filename);
    }

    /**
     * 判断文件是否可在线编辑
     * 
     * @param filename 文件名
     * @return 是否可编辑
     */
    public static boolean isEditable(String filename) {
        return isText(filename);
    }
    
    /**
     * 获取文件预览类型
     * 
     * @param filename 文件名
     * @return 预览类型（image, text, pdf, audio, video, none）
     */
    public static String getPreviewType(String filename) {
        if (isImage(filename)) {
            return "image";
        } else if (isText(filename)) {
            return "text";
        } else if (isPdf(filename)) {
            return "pdf";
        } else if (isAudio(filename)) {
            return "audio";
        } else if (isVideo(filename)) {
            return "video";
        } else {
            return "none";
        }
    }
    
    /**
     * 根据MIME类型获取图标名称
     * 
     * @param mimeType MIME类型
     * @return 图标名称
     */
    public static String getMimeTypeIcon(String mimeType) {
        if (mimeType == null) {
            return "file-unknown";
        }
        
        if (mimeType.startsWith("image/")) {
            return "file-image";
        } else if (mimeType.startsWith("text/")) {
            return "file-text";
        } else if (mimeType.startsWith("audio/")) {
            return "file-audio";
        } else if (mimeType.startsWith("video/")) {
            return "file-video";
        } else if (mimeType.equals("application/pdf")) {
            return "file-pdf";
        } else if (mimeType.startsWith("application/vnd.ms-excel") || 
                  mimeType.startsWith("application/vnd.openxmlformats-officedocument.spreadsheet")) {
            return "file-excel";
        } else if (mimeType.startsWith("application/vnd.ms-powerpoint") || 
                  mimeType.startsWith("application/vnd.openxmlformats-officedocument.presentation")) {
            return "file-ppt";
        } else if (mimeType.startsWith("application/msword") || 
                  mimeType.startsWith("application/vnd.openxmlformats-officedocument.wordprocessing")) {
            return "file-word";
        } else if (mimeType.contains("zip") || mimeType.contains("compressed") || 
                  mimeType.contains("tar") || mimeType.contains("gzip")) {
            return "file-zip";
        } else if (mimeType.contains("javascript") || mimeType.contains("json") || 
                  mimeType.contains("xml") || mimeType.contains("html")) {
            return "file-code";
        } else {
            return "file";
        }
    }
}