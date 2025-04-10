package com.filemanager.service.impl;

import com.filemanager.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 本地存储服务实现
 */
@Slf4j
@Service("localStorageService")
public class LocalStorageServiceImpl implements StorageService {

    @Value("${file.upload.path:${user.home}/file-manager/upload}")
    private String uploadPath;

    @Value("${file.upload.url-prefix:/files}")
    private String urlPrefix;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * 获取文件完整存储路径
     *
     * @param path 文件路径
     * @return 完整存储路径
     */
    private String getFullPath(String path) {
        return uploadPath + File.separator + path.replace("/", File.separator);
    }

    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            return uploadFile(file.getInputStream(), path, file.getSize(), file.getContentType());
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String path, long size, String contentType) {
        try {
            // 创建目录
            String fullPath = getFullPath(path);
            File targetFile = new File(fullPath);
            File directory = targetFile.getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 返回URL
            return getFileUrl(path);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public boolean deleteFile(String path) {
        try {
            File file = new File(getFullPath(path));
            return !file.exists() || file.delete();
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return false;
        }
    }

    @Override
    public boolean batchDeleteFiles(String[] paths) {
        boolean allSuccess = true;
        for (String path : paths) {
            if (!deleteFile(path)) {
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    @Override
    public String getFileUrl(String path) {
        String cleanContextPath = contextPath.endsWith("/") 
                ? contextPath.substring(0, contextPath.length() - 1) 
                : contextPath;
        
        String cleanUrlPrefix = urlPrefix.startsWith("/") 
                ? urlPrefix 
                : "/" + urlPrefix;
        
        return cleanContextPath + cleanUrlPrefix + (path.startsWith("/") ? path : "/" + path);
    }

    @Override
    public String getPresignedUrl(String path, Long expireTime) {
        // 本地存储不支持签名URL，直接返回普通URL
        return getFileUrl(path);
    }

    @Override
    public Integer getStorageType() {
        return 0; // 0-本地存储
    }

    @Override
    public boolean copyFile(String sourcePath, String targetPath) {
        try {
            File sourceFile = new File(getFullPath(sourcePath));
            File targetFile = new File(getFullPath(targetPath));
            
            if (!sourceFile.exists()) {
                return false;
            }
            
            // 创建目标目录
            File targetDir = targetFile.getParentFile();
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            
            // 复制文件
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            log.error("复制文件失败", e);
            return false;
        }
    }

    @Override
    public boolean moveFile(String sourcePath, String targetPath) {
        try {
            // 先复制文件
            if (copyFile(sourcePath, targetPath)) {
                // 再删除源文件
                return deleteFile(sourcePath);
            }
            return false;
        } catch (Exception e) {
            log.error("移动文件失败", e);
            return false;
        }
    }

    @Override
    public boolean exists(String path) {
        File file = new File(getFullPath(path));
        return file.exists();
    }

    @Override
    public long getSize(String path) {
        File file = new File(getFullPath(path));
        return file.exists() ? file.length() : 0;
    }
}