package com.filemanager.constant;

/**
 * 文件系统常量
 *
 * @author filemanager
 */
public class FileConstants {

    /**
     * 分享URL前缀
     */
    public static final String SHARE_URL_PREFIX = "/s/";

    /**
     * 文件分享类型 - 文件
     */
    public static final Integer SHARE_TYPE_FILE = 1;

    /**
     * 文件分享类型 - 文件夹
     */
    public static final Integer SHARE_TYPE_FOLDER = 2;

    /**
     * 分享状态 - 有效
     */
    public static final Integer SHARE_STATUS_ACTIVE = 1;

    /**
     * 分享状态 - 已取消
     */
    public static final Integer SHARE_STATUS_CANCELED = 2;

    /**
     * 分享状态 - 已过期
     */
    public static final Integer SHARE_STATUS_EXPIRED = 3;
    
    /**
     * 过期类型 - 永不过期
     */
    public static final Integer EXPIRE_TYPE_NEVER = 1;
    
    /**
     * 过期类型 - 天数
     */
    public static final Integer EXPIRE_TYPE_DAYS = 2;
    
    /**
     * 存储类型 - 本地存储
     */
    public static final Integer STORAGE_TYPE_LOCAL = 1;
    
    /**
     * 存储类型 - 阿里云OSS
     */
    public static final Integer STORAGE_TYPE_ALIYUN = 2;
    
    /**
     * 存储类型 - 七牛云
     */
    public static final Integer STORAGE_TYPE_QINIU = 3;
    
    /**
     * 存储类型 - 腾讯云COS
     */
    public static final Integer STORAGE_TYPE_TENCENT = 4;
    
    /**
     * 存储类型 - MinIO
     */
    public static final Integer STORAGE_TYPE_MINIO = 5;
    
    /**
     * 文件状态 - 正常
     */
    public static final Integer FILE_STATUS_NORMAL = 1;
    
    /**
     * 文件状态 - 回收站
     */
    public static final Integer FILE_STATUS_RECYCLE = 2;
    
    /**
     * 文件状态 - 已删除
     */
    public static final Integer FILE_STATUS_DELETED = 3;
}