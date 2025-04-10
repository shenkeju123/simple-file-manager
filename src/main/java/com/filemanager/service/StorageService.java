package com.filemanager.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 存储服务接口
 */
public interface StorageService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 存储路径
     * @return 访问URL
     */
    String uploadFile(MultipartFile file, String path);

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param path        存储路径
     * @param size        文件大小
     * @param contentType 内容类型
     * @return 访问URL
     */
    String uploadFile(InputStream inputStream, String path, long size, String contentType);

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 是否成功
     */
    boolean deleteFile(String path);

    /**
     * 批量删除文件
     *
     * @param paths 文件路径列表
     * @return 是否成功
     */
    boolean batchDeleteFiles(String[] paths);

    /**
     * 获取文件访问URL
     *
     * @param path 文件路径
     * @return 访问URL
     */
    String getFileUrl(String path);

    /**
     * 获取带签名的临时访问URL
     *
     * @param path       文件路径
     * @param expireTime 过期时间（秒）
     * @return 临时访问URL
     */
    String getPresignedUrl(String path, Long expireTime);

    /**
     * 获取存储类型
     *
     * @return 存储类型
     */
    Integer getStorageType();

    /**
     * 复制文件
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @return 是否成功
     */
    boolean copyFile(String sourcePath, String targetPath);

    /**
     * 移动文件
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @return 是否成功
     */
    boolean moveFile(String sourcePath, String targetPath);

    /**
     * 文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    boolean exists(String path);

    /**
     * 获取文件大小
     *
     * @param path 文件路径
     * @return 文件大小
     */
    long getSize(String path);
}