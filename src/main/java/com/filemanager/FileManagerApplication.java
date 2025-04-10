package com.filemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

/**
 * 文件管理系统启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
public class FileManagerApplication {

    public static void main(String[] args) {
        // 确保上传目录存在
        String uploadPath = System.getProperty("user.home") + "/file-manager/upload";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        SpringApplication.run(FileManagerApplication.class, args);
    }
}