package com.filemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件分享实体类
 *
 * @author filemanager
 */
@Data
@TableName("file_share")
@ApiModel(value = "文件分享实体", description = "文件分享信息")
public class FileShare implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "分享ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分享标题
     */
    @ApiModelProperty(value = "分享标题", example = "重要文档分享")
    @TableField("share_title")
    private String shareTitle;

    /**
     * 分享类型：1-文件分享，2-文件夹分享
     */
    @ApiModelProperty(value = "分享类型", example = "1", notes = "1-文件分享，2-文件夹分享")
    @TableField("share_type")
    private Integer shareType;

    /**
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID", example = "101")
    @TableField("file_id")
    private Long fileId;

    /**
     * 文件夹ID
     */
    @ApiModelProperty(value = "文件夹ID", example = "201")
    @TableField("folder_id")
    private Long folderId;

    /**
     * 创建用户ID
     */
    @ApiModelProperty(value = "创建用户ID", example = "1001")
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 分享链接URL
     */
    @ApiModelProperty(value = "分享链接URL", example = "http://example.com/s/aBcD1234")
    @TableField("share_url")
    private String shareUrl;

    /**
     * 分享提取码
     */
    @ApiModelProperty(value = "分享提取码", example = "AB12")
    @TableField("share_code")
    private String shareCode;

    /**
     * 过期类型：0-永久有效，1-1天，2-7天，3-30天，4-自定义
     */
    @ApiModelProperty(value = "过期类型", example = "2", notes = "0-永久有效，1-1天，2-7天，3-30天，4-自定义")
    @TableField("expire_type")
    private Integer expireType;

    /**
     * 自定义过期天数
     */
    @ApiModelProperty(value = "自定义过期天数", example = "15")
    @TableField("expire_days")
    private Integer expireDays;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间", example = "2023-12-31 23:59:59")
    @TableField("expire_time")
    private LocalDateTime expireTime;

    /**
     * 是否需要提取码：true-是，false-否
     */
    @ApiModelProperty(value = "是否需要提取码", example = "true")
    @TableField("need_code")
    private Boolean needCode;

    /**
     * 是否允许下载：1-允许，0-不允许
     */
    @ApiModelProperty(value = "是否允许下载", example = "1", notes = "1-允许，0-不允许")
    @TableField("allow_download")
    private Integer allowDownload;

    /**
     * 访问次数限制（0表示无限制）
     */
    @ApiModelProperty(value = "访问次数限制", example = "100", notes = "0表示无限制")
    @TableField("access_limit")
    private Integer accessLimit;

    /**
     * 已访问次数
     */
    @ApiModelProperty(value = "已访问次数", example = "50")
    @TableField("access_count")
    private Integer accessCount;

    /**
     * 状态：0-已失效，1-有效
     */
    @ApiModelProperty(value = "状态", example = "1", notes = "0-已失效，1-有效")
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "项目相关文档，请查看")
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2023-01-02 15:30:00")
    @TableField("update_time")
    private LocalDateTime updateTime;
}