package com.filemanager.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储工具类
 *
 * @author filemanager
 */
@Component
public class StorageUtils {

    /**
     * 存储单位数组
     */
    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB", "PB"};
    
    /**
     * 存储容量警告阈值（默认80%）
     */
    private static final double WARNING_THRESHOLD = 0.8;
    
    /**
     * 存储容量危险阈值（默认95%）
     */
    private static final double DANGER_THRESHOLD = 0.95;
    
    /**
     * 格式化文件大小
     *
     * @param size 文件大小（字节）
     * @return 格式化后的大小字符串
     */
    public static String formatFileSize(long size) {
        if (size <= 0) {
            return "0 B";
        }
        
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        if (digitGroups >= UNITS.length) {
            digitGroups = UNITS.length - 1;
        }
        
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return df.format(size / Math.pow(1024, digitGroups)) + " " + UNITS[digitGroups];
    }
    
    /**
     * 解析文件大小字符串为字节数
     *
     * @param sizeStr 大小字符串，如 "10MB", "1.5GB"
     * @return 字节数
     */
    public static long parseFileSize(String sizeStr) {
        if (sizeStr == null || sizeStr.trim().isEmpty()) {
            return 0;
        }
        
        sizeStr = sizeStr.trim().toUpperCase();
        if (sizeStr.equals("0") || sizeStr.equals("0B")) {
            return 0;
        }
        
        // 匹配数字和单位
        String numPart = sizeStr.replaceAll("[^0-9\\.]", "");
        String unitPart = sizeStr.replaceAll("[0-9\\.]", "").trim();
        
        double size;
        try {
            size = Double.parseDouble(numPart);
        } catch (NumberFormatException e) {
            return 0;
        }
        
        // 根据单位转换为字节
        switch (unitPart) {
            case "B":
                return (long) size;
            case "KB":
            case "K":
                return (long) (size * 1024);
            case "MB":
            case "M":
                return (long) (size * 1024 * 1024);
            case "GB":
            case "G":
                return (long) (size * 1024 * 1024 * 1024);
            case "TB":
            case "T":
                return (long) (size * 1024 * 1024 * 1024 * 1024);
            case "PB":
            case "P":
                return (long) (size * 1024 * 1024 * 1024 * 1024 * 1024);
            default:
                return (long) size;
        }
    }
    
    /**
     * 检查是否有足够的存储空间
     *
     * @param path 存储路径
     * @param requiredBytes 所需的字节数
     * @return 是否有足够空间
     */
    public static boolean hasEnoughSpace(String path, long requiredBytes) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        
        File file = new File(path);
        long usableSpace = file.getUsableSpace();
        
        return usableSpace >= requiredBytes;
    }
    
    /**
     * 计算存储使用百分比
     *
     * @param path 存储路径
     * @return 使用百分比（0-1之间的小数）
     */
    public static double calculateUsagePercentage(String path) {
        if (path == null || path.trim().isEmpty()) {
            return 0;
        }
        
        File file = new File(path);
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        
        if (totalSpace <= 0) {
            return 0;
        }
        
        return (double) (totalSpace - freeSpace) / totalSpace;
    }
    
    /**
     * 格式化使用百分比
     *
     * @param percentage 百分比（0-1之间的小数）
     * @return 格式化的百分比字符串
     */
    public static String formatUsagePercentage(double percentage) {
        if (percentage < 0) {
            percentage = 0;
        } else if (percentage > 1) {
            percentage = 1;
        }
        
        DecimalFormat df = new DecimalFormat("0.##%");
        return df.format(percentage);
    }
    
    /**
     * 将各种单位转换为字节
     *
     * @param value 值
     * @param unit 单位（B, KB, MB, GB, TB, PB）
     * @return 字节数
     */
    public static long convertToBytes(double value, String unit) {
        if (value < 0) {
            return 0;
        }
        
        unit = unit.toUpperCase();
        switch (unit) {
            case "B":
                return (long) value;
            case "KB":
                return (long) (value * 1024);
            case "MB":
                return (long) (value * 1024 * 1024);
            case "GB":
                return (long) (value * 1024 * 1024 * 1024);
            case "TB":
                return (long) (value * 1024 * 1024 * 1024 * 1024);
            case "PB":
                return (long) (value * 1024 * 1024 * 1024 * 1024 * 1024);
            default:
                return (long) value;
        }
    }
    
    /**
     * 将字节转换为指定单位
     *
     * @param bytes 字节数
     * @param unit 目标单位（KB, MB, GB, TB, PB）
     * @return 转换后的值
     */
    public static double convertBytesTo(long bytes, String unit) {
        if (bytes < 0) {
            return 0;
        }
        
        unit = unit.toUpperCase();
        switch (unit) {
            case "B":
                return bytes;
            case "KB":
                return bytes / 1024.0;
            case "MB":
                return bytes / (1024.0 * 1024);
            case "GB":
                return bytes / (1024.0 * 1024 * 1024);
            case "TB":
                return bytes / (1024.0 * 1024 * 1024 * 1024);
            case "PB":
                return bytes / (1024.0 * 1024 * 1024 * 1024 * 1024);
            default:
                return bytes;
        }
    }
    
    /**
     * 计算剩余空间
     *
     * @param path 存储路径
     * @return 剩余空间字节数
     */
    public static long calculateRemainingSpace(String path) {
        if (path == null || path.trim().isEmpty()) {
            return 0;
        }
        
        File file = new File(path);
        return file.getUsableSpace();
    }
    
    /**
     * 检查文件大小是否在允许范围内
     *
     * @param fileSize 文件大小（字节）
     * @param maxSize 最大允许大小（字节）
     * @return 是否允许
     */
    public static boolean isFileSizeAllowed(long fileSize, long maxSize) {
        if (maxSize <= 0) {
            // 如果最大大小为0或负数，表示无限制
            return true;
        }
        
        return fileSize <= maxSize;
    }
    
    /**
     * 计算多个文件的总大小
     *
     * @param fileSizes 文件大小列表
     * @return 总大小
     */
    public static long calculateTotalSize(List<Long> fileSizes) {
        if (fileSizes == null || fileSizes.isEmpty()) {
            return 0;
        }
        
        long total = 0;
        for (Long size : fileSizes) {
            if (size != null && size > 0) {
                total += size;
            }
        }
        
        return total;
    }
    
    /**
     * 比较文件大小
     *
     * @param size1 大小1
     * @param unit1 单位1
     * @param size2 大小2
     * @param unit2 单位2
     * @return 比较结果，负数表示size1 < size2，0表示相等，正数表示size1 > size2
     */
    public static int compareFileSize(double size1, String unit1, double size2, String unit2) {
        long bytes1 = convertToBytes(size1, unit1);
        long bytes2 = convertToBytes(size2, unit2);
        
        return Long.compare(bytes1, bytes2);
    }
    
    /**
     * 获取格式化的存储详情
     *
     * @param path 存储路径
     * @return 格式化的存储信息列表 [总空间, 已用空间, 可用空间, 使用百分比]
     */
    public static List<String> getStorageDetails(String path) {
        List<String> details = new ArrayList<>();
        
        if (path == null || path.trim().isEmpty()) {
            details.add("0 B"); // 总空间
            details.add("0 B"); // 已用空间
            details.add("0 B"); // 可用空间
            details.add("0%");  // 使用百分比
            return details;
        }
        
        File file = new File(path);
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        double usagePercentage = (double) usedSpace / totalSpace;
        
        details.add(formatFileSize(totalSpace));
        details.add(formatFileSize(usedSpace));
        details.add(formatFileSize(freeSpace));
        details.add(formatUsagePercentage(usagePercentage));
        
        return details;
    }
    
    /**
     * 检查存储空间是否达到警告阈值
     *
     * @param path 存储路径
     * @return 是否达到警告阈值
     */
    public static boolean isStorageWarning(String path) {
        double usagePercentage = calculateUsagePercentage(path);
        return usagePercentage >= WARNING_THRESHOLD;
    }
    
    /**
     * 检查存储空间是否达到危险阈值
     *
     * @param path 存储路径
     * @return 是否达到危险阈值
     */
    public static boolean isStorageDanger(String path) {
        double usagePercentage = calculateUsagePercentage(path);
        return usagePercentage >= DANGER_THRESHOLD;
    }
    
    /**
     * 获取文件大小级别描述
     *
     * @param size 文件大小（字节）
     * @return 级别描述
     */
    public static String getFileSizeLevel(long size) {
        if (size < 1024 * 1024) { // 小于1MB
            return "小";
        } else if (size < 1024 * 1024 * 10) { // 小于10MB
            return "中";
        } else if (size < 1024 * 1024 * 100) { // 小于100MB
            return "大";
        } else if (size < 1024L * 1024 * 1024) { // 小于1GB
            return "较大";
        } else {
            return "超大";
        }
    }
}