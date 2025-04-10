package com.filemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 存储配置实体类
 */
@Data
@TableName("sys_storage_config")
@ApiModel(value = "存储配置信息", description = "系统存储配置信息实体")
public class StorageConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "配置ID", example = "1")
    private Long id;

    /**
     * 存储类型（0-本地存储，1-阿里云OSS，2-腾讯云COS，3-七牛云，4-MinIO）
     */
    @ApiModelProperty(value = "存储类型", example = "0", notes = "0-本地存储，1-阿里云OSS，2-腾讯云COS，3-七牛云，4-MinIO")
    private Integer storageType;

    /**
     * 存储名称
     */
    @ApiModelProperty(value = "存储名称", example = "本地存储")
    private String storageName;

    /**
     * AccessKey
     */
    @ApiModelProperty(value = "AccessKey", example = "LTAI4GxxxxxxxxxxxxxxV")
    private String accessKey;

    /**
     * SecretKey
     */
    @ApiModelProperty(value = "SecretKey", example = "uCxxxxxxxxxxxxxxKXs")
    private String secretKey;

    /**
     * 服务端点
     */
    @ApiModelProperty(value = "服务端点", example = "oss-cn-hangzhou.aliyuncs.com")
    private String endpoint;

    /**
     * 存储桶名称
     */
    @ApiModelProperty(value = "存储桶名称", example = "my-bucket")
    private String bucketName;

    /**
     * 访问域名
     */
    @ApiModelProperty(value = "访问域名", example = "https://example.oss-cn-hangzhou.aliyuncs.com")
    private String domain;

    /**
     * 基础路径
     */
    @ApiModelProperty(value = "基础路径", example = "/")
    private String basePath;

    /**
     * 是否使用HTTPS（0-否，1-是）
     */
    @ApiModelProperty(value = "是否使用HTTPS", example = "1", notes = "0-否，1-是")
    private Integer isHttps;

    /**
     * 状态（0-禁用，1-启用）
     */
    @ApiModelProperty(value = "状态", example = "1", notes = "0-禁用，1-启用")
    private Integer status;

    /**
     * 是否默认（0-否，1-是）
     */
    @ApiModelProperty(value = "是否默认", example = "1", notes = "0-否，1-是")
    private Integer isDefault;

    /**
     * 区域
     */
    @ApiModelProperty(value = "区域", example = "cn-hangzhou")
    private String region;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "这是本地存储配置")
    private String remark;

    /**
     * 创建用户ID
     */
    @ApiModelProperty(value = "创建用户ID", example = "1")
    private Long createUserId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}