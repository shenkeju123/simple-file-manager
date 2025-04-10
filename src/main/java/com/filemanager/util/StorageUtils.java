package com.filemanager.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 存储工具类
 *
 * @author filemanager
 */
public class StorageUtils {

    private static final long KB = 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;
    private static final long TB = GB * 1024;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * 格式化文件大小，自动选择合适的单位（B、KB、MB、GB、TB）
     *
     * @param size 文件大小（字节数）
     * @return 格式化后的文件大小字符串
     */
    public static String formatFileSize(long size) {
        if (size < 0) {
            return "0 B";
        }
        if (size < KB) {
            return size + " B";
        } else if (size < MB) {
            return DECIMAL_FORMAT.format((double) size / KB) + " KB";
        } else if (size < GB) {
            return DECIMAL_FORMAT.format((double) size / MB) + " MB";
        } else if (size < TB) {
            return DECIMAL_FORMAT.format((double) size / GB) + " GB";
        } else {
            return DECIMAL_FORMAT.format((double) size / TB) + " TB";
        }
    }

    /**
     * 解析文件大小字符串，支持 B、KB、MB、GB、TB 单位
     *
     * @param sizeStr 文件大小字符串，如 "1.5 GB"
     * @return 文件大小（字节数）
     */
    public static long parseFileSize(String sizeStr) {
        if (sizeStr == null || sizeStr.trim().isEmpty()) {
            return 0;
        }

        Pattern pattern = Pattern.compile("([\\d.]+)\\s*(B|KB|MB|GB|TB)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sizeStr.trim());

        if (matcher.matches()) {
            double size = Double.parseDouble(matcher.group(1));
            String unit = matcher.group(2);

            if (unit == null || unit.equalsIgnoreCase("B")) {
                return (long) size;
            } else if (unit.equalsIgnoreCase("KB")) {
                return (long) (size * KB);
            } else if (unit.equalsIgnoreCase("MB")) {
                return (long) (size * MB);
            } else if (unit.equalsIgnoreCase("GB")) {
                return (long) (size * GB);
            } else if (unit.equalsIgnoreCase("TB")) {
                return (long) (size * TB);
            }
        }

        throw new IllegalArgumentException("无法解析的文件大小格式: " + sizeStr);
    }

    /**
     * 检查是否有足够的存储空间
     *
     * @param path 存储路径
     * @param requiredSize 需要的存储空间大小（字节数）
     * @return 是否有足够空间
     */
    public static boolean hasEnoughSpace(String path, long requiredSize) {
        File file = new File(path);
        return file.getUsableSpace() >= requiredSize;
    }

    /**
     * 计算存储使用百分比
     *
     * @param usedSpace 已使用空间（字节数）
     * @param totalSpace 总空间（字节数）
     * @return 使用百分比
     */
    public static double calculateUsagePercentage(long usedSpace, long totalSpace) {
        if (totalSpace <= 0) {
            return 0.0;
        }
        return ((double) usedSpace / totalSpace) * 100;
    }

    /**
     * 格式化使用百分比
     *
     * @param percentage 百分比值
     * @return 格式化后的百分比字符串
     */
    public static String formatUsagePercentage(double percentage) {
        return DECIMAL_FORMAT.format(percentage) + "%";
    }

    /**
     * 将不同单位的大小转换为字节数
     *
     * @param size 大小值
     * @param unit 单位（B、KB、MB、GB、TB）
     * @return 字节数
     */
    public static long convertToBytes(double size, String unit) {
        if (unit.equalsIgnoreCase("B")) {
            return (long) size;
        } else if (unit.equalsIgnoreCase("KB")) {
            return (long) (size * KB);
        } else if (unit.equalsIgnoreCase("MB")) {
            return (long) (size * MB);
        } else if (unit.equalsIgnoreCase("GB")) {
            return (long) (size * GB);
        } else if (unit.equalsIgnoreCase("TB")) {
            return (long) (size * TB);
        } else {
            throw new IllegalArgumentException("不支持的单位: " + unit);
        }
    }

    /**
     * 将字节数转换为指定单位的大小
     *
     * @param bytes 字节数
     * @param unit 单位（B、KB、MB、GB、TB）
     * @return 转换后的大小
     */
    public static double convertBytesTo(long bytes, String unit) {
        if (unit.equalsIgnoreCase("B")) {
            return bytes;
        } else if (unit.equalsIgnoreCase("KB")) {
            return (double) bytes / KB;
        } else if (unit.equalsIgnoreCase("MB")) {
            return (double) bytes / MB;
        } else if (unit.equalsIgnoreCase("GB")) {
            return (double) bytes / GB;
        } else if (unit.equalsIgnoreCase("TB")) {
            return (double) bytes / TB;
        } else {
            throw new IllegalArgumentException("不支持的单位: " + unit);
        }
    }

    /**
     * 计算剩余空间
     *
     * @param totalSpace 总空间（字节数）
     * @param usedSpace 已使用空间（字节数）
     * @return 剩余空间（字节数）
     */
    public static long calculateRemainingSpace(long totalSpace, long usedSpace) {
        return Math.max(0, totalSpace - usedSpace);
    }

    /**
     * 检查文件大小是否在允许范围内
     *
     * @param fileSize 文件大小（字节数）
     * @param maxAllowedSize 最大允许大小（字节数）
     * @return 是否允许
     */
    public static boolean isFileSizeAllowed(long fileSize, long maxAllowedSize) {
        return fileSize <= maxAllowedSize;
    }

    /**
     * 计算多个文件的总大小
     *
     * @param fileSizes 文件大小列表
     * @return 总大小（字节数）
     */
    public static long calculateTotalSize(List<Long> fileSizes) {
        long total = 0;
        for (Long size : fileSizes) {
            total += size;
        }
        return total;
    }

    /**
     * 比较两个文件大小
     *
     * @param size1 第一个文件大小（字节数）
     * @param size2 第二个文件大小（字节数）
     * @return 比较结果（-1:小于, 0:等于, 1:大于）
     */
    public static int compareFileSize(long size1, long size2) {
        return Long.compare(size1, size2);
    }

    /**
     * 获取存储详情
     *
     * @param usedSpace 已使用空间（字节数）
     * @param totalSpace 总空间（字节数）
     * @return 存储详情字符串
     */
    public static String getStorageDetails(long usedSpace, long totalSpace) {
        double usagePercentage = calculateUsagePercentage(usedSpace, totalSpace);
        return formatFileSize(usedSpace) + " / " + formatFileSize(totalSpace) 
               + " (" + formatUsagePercentage(usagePercentage) + ")";
    }

    /**
     * 检查存储是否处于警告状态（使用率超过80%）
     *
     * @param usedSpace 已使用空间（字节数）
     * @param totalSpace 总空间（字节数）
     * @return 是否处于警告状态
     */
    public static boolean isStorageWarning(long usedSpace, long totalSpace) {
        double usagePercentage = calculateUsagePercentage(usedSpace, totalSpace);
        return usagePercentage >= 80 && usagePercentage < 90;
    }

    /**
     * 检查存储是否处于危险状态（使用率超过90%）
     *
     * @param usedSpace 已使用空间（字节数）
     * @param totalSpace 总空间（字节数）
     * @return 是否处于危险状态
     */
    public static boolean isStorageDanger(long usedSpace, long totalSpace) {
        double usagePercentage = calculateUsagePercentage(usedSpace, totalSpace);
        return usagePercentage >= 90;
    }

    /**
     * 获取文件大小等级描述
     *
     * @param fileSize 文件大小（字节数）
     * @return 大小等级描述
     */
    public static String getFileSizeLevel(long fileSize) {
        if (fileSize < KB) {
            return "极小";
        } else if (fileSize < 10 * KB) {
            return "很小";
        } else if (fileSize < MB) {
            return "小";
        } else if (fileSize < 10 * MB) {
            return "中等";
        } else if (fileSize < 100 * MB) {
            return "较大";
        } else if (fileSize < GB) {
            return "大";
        } else if (fileSize < 10 * GB) {
            return "很大";
        } else {
            return "超大";
        }
    }
}