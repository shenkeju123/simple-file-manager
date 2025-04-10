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
 * 用户实体类
 */
@Data
@TableName("sys_user")
@ApiModel(value = "用户信息", description = "系统用户信息实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "******")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "系统管理员")
    private String nickname;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱", example = "admin@example.com")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "13800138000")
    private String mobile;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", example = "/avatar/default.png")
    private String avatar;

    /**
     * 性别（0-未知，1-男，2-女）
     */
    @ApiModelProperty(value = "性别", example = "1", notes = "0-未知，1-男，2-女")
    private Integer gender;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", example = "1")
    private Long deptId;

    /**
     * 职位ID
     */
    @ApiModelProperty(value = "职位ID", example = "1")
    private Long postId;

    /**
     * 角色ID列表，多个用逗号分隔
     */
    @ApiModelProperty(value = "角色ID列表", example = "1,2,3")
    private String roleIds;

    /**
     * 用户状态（0-禁用，1-正常）
     */
    @ApiModelProperty(value = "用户状态", example = "1", notes = "0-禁用，1-正常")
    private Integer status;

    /**
     * 存储空间限制（字节）
     */
    @ApiModelProperty(value = "存储空间限制（字节）", example = "1073741824")
    private Long storageLimit;

    /**
     * 已使用存储空间（字节）
     */
    @ApiModelProperty(value = "已使用存储空间（字节）", example = "102400")
    private Long storageUsed;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "这是系统管理员账号")
    private String remark;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录IP", example = "192.168.1.1")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 是否删除（0-未删除，1-已删除）
     */
    @TableLogic
    @ApiModelProperty(value = "是否删除", example = "0", notes = "0-未删除，1-已删除")
    private Integer deleted;

    /**
     * 创建者ID
     */
    @ApiModelProperty(value = "创建者ID", example = "1")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者ID
     */
    @ApiModelProperty(value = "更新者ID", example = "1")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}