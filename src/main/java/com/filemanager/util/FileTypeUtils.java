package com.filemanager.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件类型工具类
 *
 * @author filemanager
 */
public class FileTypeUtils {

    /**
     * 文件类型枚举
     */
    public enum FileType {
        /**
         * 图片文件
         */
        IMAGE,
        
        /**
         * 文档文件
         */
        DOCUMENT,
        
        /**
         * 视频文件
         */
        VIDEO,
        
        /**
         * 音频文件
         */
        AUDIO,
        
        /**
         * 压缩文件
         */
        ARCHIVE,
        
        /**
         * 可执行文件
         */
        EXECUTABLE,
        
        /**
         * 代码文件
         */
        CODE,
        
        /**
         * 其他类型
         */
        OTHER
    }

    private static final Set<String> IMAGE_EXTENSIONS = new HashSet<>(
            Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "tiff", "svg", "webp", "ico", "heic", "heif"));
    
    private static final Set<String> DOCUMENT_EXTENSIONS = new HashSet<>(
            Arrays.asList("doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "txt", "csv", "rtf", "odt", "ods", "odp", "md", "json", "xml"));
    
    private static final Set<String> VIDEO_EXTENSIONS = new HashSet<>(
            Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm", "m4v", "mpeg", "mpg", "3gp", "ts"));
    
    private static final Set<String> AUDIO_EXTENSIONS = new HashSet<>(
            Arrays.asList("mp3", "wav", "ogg", "flac", "aac", "wma", "m4a", "mid", "midi"));
    
    private static final Set<String> ARCHIVE_EXTENSIONS = new HashSet<>(
            Arrays.asList("zip", "rar", "7z", "tar", "gz", "bz2", "xz", "iso", "cab"));
    
    private static final Set<String> EXECUTABLE_EXTENSIONS = new HashSet<>(
            Arrays.asList("exe", "dll", "bat", "sh", "com", "msi", "app", "dmg", "apk", "deb", "rpm"));
    
    private static final Set<String> CODE_EXTENSIONS = new HashSet<>(
            Arrays.asList("java", "c", "cpp", "h", "py", "js", "html", "css", "php", "rb", "go", "ts", "swift", "kt", "cs", 
                          "sql", "sh", "bash", "ps1", "pl", "groovy", "scala", "yml", "yaml", "ini", "properties"));

    /**
     * 根据文件扩展名获取文件类型
     *
     * @param fileName 文件名或文件扩展名
     * @return 文件类型
     */
    public static FileType getFileType(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return FileType.OTHER;
        }
        
        String extension = fileName;
        if (fileName.contains(".")) {
            extension = FileNameUtils.getExtension(fileName);
        }
        
        extension = extension.toLowerCase();
        
        if (IMAGE_EXTENSIONS.contains(extension)) {
            return FileType.IMAGE;
        } else if (DOCUMENT_EXTENSIONS.contains(extension)) {
            return FileType.DOCUMENT;
        } else if (VIDEO_EXTENSIONS.contains(extension)) {
            return FileType.VIDEO;
        } else if (AUDIO_EXTENSIONS.contains(extension)) {
            return FileType.AUDIO;
        } else if (ARCHIVE_EXTENSIONS.contains(extension)) {
            return FileType.ARCHIVE;
        } else if (EXECUTABLE_EXTENSIONS.contains(extension)) {
            return FileType.EXECUTABLE;
        } else if (CODE_EXTENSIONS.contains(extension)) {
            return FileType.CODE;
        } else {
            return FileType.OTHER;
        }
    }

    /**
     * 判断文件是否为图片
     *
     * @param fileName 文件名
     * @return 是否为图片
     */
    public static boolean isImage(String fileName) {
        return getFileType(fileName) == FileType.IMAGE;
    }

    /**
     * 判断文件是否为文档
     *
     * @param fileName 文件名
     * @return 是否为文档
     */
    public static boolean isDocument(String fileName) {
        return getFileType(fileName) == FileType.DOCUMENT;
    }

    /**
     * 判断文件是否为视频
     *
     * @param fileName 文件名
     * @return 是否为视频
     */
    public static boolean isVideo(String fileName) {
        return getFileType(fileName) == FileType.VIDEO;
    }

    /**
     * 判断文件是否为音频
     *
     * @param fileName 文件名
     * @return 是否为音频
     */
    public static boolean isAudio(String fileName) {
        return getFileType(fileName) == FileType.AUDIO;
    }

    /**
     * 判断文件是否为压缩文件
     *
     * @param fileName 文件名
     * @return 是否为压缩文件
     */
    public static boolean isArchive(String fileName) {
        return getFileType(fileName) == FileType.ARCHIVE;
    }

    /**
     * 判断文件是否为可执行文件
     *
     * @param fileName 文件名
     * @return 是否为可执行文件
     */
    public static boolean isExecutable(String fileName) {
        return getFileType(fileName) == FileType.EXECUTABLE;
    }

    /**
     * 判断文件是否为代码文件
     *
     * @param fileName 文件名
     * @return 是否为代码文件
     */
    public static boolean isCode(String fileName) {
        return getFileType(fileName) == FileType.CODE;
    }

    /**
     * 判断文件是否可以预览（图片、文档、视频、音频）
     *
     * @param fileName 文件名
     * @return 是否可预览
     */
    public static boolean isPreviewable(String fileName) {
        FileType type = getFileType(fileName);
        return type == FileType.IMAGE || type == FileType.DOCUMENT || 
               type == FileType.VIDEO || type == FileType.AUDIO || 
               type == FileType.CODE;
    }

    /**
     * 获取文件类型的中文描述
     *
     * @param fileName 文件名
     * @return 文件类型描述
     */
    public static String getFileTypeDescription(String fileName) {
        FileType type = getFileType(fileName);
        switch (type) {
            case IMAGE:
                return "图片文件";
            case DOCUMENT:
                return "文档文件";
            case VIDEO:
                return "视频文件";
            case AUDIO:
                return "音频文件";
            case ARCHIVE:
                return "压缩文件";
            case EXECUTABLE:
                return "可执行文件";
            case CODE:
                return "代码文件";
            default:
                return "其他文件";
        }
    }

    /**
     * 根据文件类型获取对应的图标名称
     *
     * @param fileName 文件名
     * @return 图标名称
     */
    public static String getFileIconName(String fileName) {
        FileType type = getFileType(fileName);
        switch (type) {
            case IMAGE:
                return "icon-image";
            case DOCUMENT:
                return "icon-document";
            case VIDEO:
                return "icon-video";
            case AUDIO:
                return "icon-audio";
            case ARCHIVE:
                return "icon-archive";
            case EXECUTABLE:
                return "icon-executable";
            case CODE:
                return "icon-code";
            default:
                return "icon-file";
        }
    }
}