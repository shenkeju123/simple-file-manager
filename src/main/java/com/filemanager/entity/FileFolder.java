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
 * 文件夹实体类
 */
@Data
@TableName("sys_folder")
@ApiModel(value = "文件夹信息", description = "系统文件夹信息实体")
public class FileFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件夹ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "文件夹ID", example = "1")
    private Long id;

    /**
     * 文件夹名称
     */
    @ApiModelProperty(value = "文件夹名称", example = "我的文档")
    private String folderName;

    /**
     * 父级文件夹ID
     */
    @ApiModelProperty(value = "父级文件夹ID", example = "0")
    private Long parentId;

    /**
     * 文件夹路径
     */
    @ApiModelProperty(value = "文件夹路径", example = "/1/2/")
    private String folderPath;

    /**
     * 创建用户ID
     */
    @ApiModelProperty(value = "创建用户ID", example = "1")
    private Long createUserId;

    /**
     * 更新用户ID
     */
    @ApiModelProperty(value = "更新用户ID", example = "1")
    private Long updateUserId;

    /**
     * 所属类型（0-个人文件，1-部门文件，2-公共文件）
     */
    @ApiModelProperty(value = "所属类型", example = "0", notes = "0-个人文件，1-部门文件，2-公共文件")
    private Integer belongType;

    /**
     * 部门ID，当belongType=1时有效
     */
    @ApiModelProperty(value = "部门ID", example = "0")
    private Long deptId;

    /**
     * 文件夹状态（0-已删除，1-正常）
     */
    @TableLogic
    @ApiModelProperty(value = "文件夹状态", example = "1", notes = "0-已删除，1-正常")
    private Integer status;

    /**
     * 是否收藏（0-否，1-是）
     */
    @ApiModelProperty(value = "是否收藏", example = "0", notes = "0-否，1-是")
    private Integer isFavorite;

    /**
     * 是否公开（0-否，1-是）
     */
    @ApiModelProperty(value = "是否公开", example = "0", notes = "0-否，1-是")
    private Integer isPublic;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "这是一个重要文件夹")
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

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
}