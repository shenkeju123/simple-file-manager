package com.filemanager.common;

import lombok.Getter;

/**
 * 结果状态码枚举
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    
    /**
     * 失败
     */
    ERROR(500, "操作失败"),
    
    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),
    
    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),
    
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    
    /**
     * 请求参数错误
     */
    PARAM_ERROR(400, "请求参数错误"),
    
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    
    /**
     * 令牌过期
     */
    TOKEN_EXPIRED(50014, "令牌已过期"),
    
    /**
     * 令牌无效
     */
    TOKEN_INVALID(50008, "令牌无效"),
    
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(50012, "验证码错误或已过期"),
    
    /**
     * 用户名或密码错误
     */
    USERNAME_PASSWORD_ERROR(50001, "用户名或密码错误"),
    
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(50002, "用户不存在"),
    
    /**
     * 用户已禁用
     */
    USER_DISABLED(50003, "用户已被禁用"),
    
    /**
     * 超出存储空间限制
     */
    STORAGE_SPACE_FULL(60001, "存储空间已满"),
    
    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(60002, "文件不存在"),
    
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(60003, "文件上传失败"),
    
    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_ERROR(60004, "文件下载失败"),
    
    /**
     * 文件大小超出限制
     */
    FILE_SIZE_LIMIT(60005, "文件大小超出限制"),
    
    /**
     * 文件类型不支持
     */
    FILE_TYPE_NOT_SUPPORT(60006, "文件类型不支持"),
    
    /**
     * 文件夹不存在
     */
    FOLDER_NOT_FOUND(60007, "文件夹不存在"),
    
    /**
     * 分享不存在
     */
    SHARE_NOT_FOUND(60008, "分享不存在或已失效"),
    
    /**
     * 分享码错误
     */
    SHARE_CODE_ERROR(60009, "提取码错误"),
    
    /**
     * 分享已过期
     */
    SHARE_EXPIRED(60010, "分享已过期"),
    
    /**
     * 分享访问次数已达上限
     */
    SHARE_ACCESS_LIMIT(60011, "分享访问次数已达上限");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 消息
     */
    private final String message;
    
    /**
     * 构造方法
     *
     * @param code    状态码
     * @param message 消息
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}