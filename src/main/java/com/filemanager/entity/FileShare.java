package com.filemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件分享实体类
 */
@Data
@TableName("sys_file_share")
@ApiModel(value = "文件分享信息", description = "系统文件分享信息实体")
public class FileShare implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分享ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "分享ID", example = "1")
    private Long id;

    /**
     * 分享标题
     */
    @ApiModelProperty(value = "分享标题", example = "重要文档分享")
    private String shareTitle;

    /**
     * 分享类型（1-文件，2-文件夹）
     */
    @ApiModelProperty(value = "分享类型", example = "1", notes = "1-文件，2-文件夹")
    private Integer shareType;

    /**
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID", example = "10")
    private Long fileId;

    /**
     * 文件夹ID
     */
    @ApiModelProperty(value = "文件夹ID", example = "0")
    private Long folderId;

    /**
     * 创建用户ID
     */
    @ApiModelProperty(value = "创建用户ID", example = "1")
    private Long createUserId;

    /**
     * 分享链接
     */
    @ApiModelProperty(value = "分享链接", example = "http://example.com/s/abcdef")
    private String shareUrl;

    /**
     * 分享码
     */
    @ApiModelProperty(value = "分享码", example = "abcdef")
    private String shareCode;

    /**
     * 提取码
     */
    @ApiModelProperty(value = "提取码", example = "123456")
    private String extractCode;

    /**
     * 过期类型（0-永久有效，1-天数，2-自定义时间）
     */
    @ApiModelProperty(value = "过期类型", example = "1", notes = "0-永久有效，1-天数，2-自定义时间")
    private Integer expireType;

    /**
     * 过期天数
     */
    @ApiModelProperty(value = "过期天数", example = "7")
    private Integer expireDays;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expireTime;

    /**
     * 是否需要提取码（0-否，1-是）
     */
    @ApiModelProperty(value = "是否需要提取码", example = "1", notes = "0-否，1-是")
    private Integer needCode;

    /**
     * 是否允许下载（0-否，1-是）
     */
    @ApiModelProperty(value = "是否允许下载", example = "1", notes = "0-否，1-是")
    private Integer allowDownload;

    /**
     * 访问次数限制（0-无限制）
     */
    @ApiModelProperty(value = "访问次数限制", example = "10", notes = "0-无限制")
    private Integer accessLimit;

    /**
     * 已访问次数
     */
    @ApiModelProperty(value = "已访问次数", example = "3")
    private Integer accessCount;

    /**
     * 分享状态（0-已失效，1-正常）
     */
    @TableLogic
    @ApiModelProperty(value = "分享状态", example = "1", notes = "0-已失效，1-正常")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "这是一个文件分享")
    private String remark;

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