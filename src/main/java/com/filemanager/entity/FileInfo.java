package com.filemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件信息实体类
 */
@Data
@TableName("sys_file_info")
@ApiModel(value = "文件信息", description = "系统文件信息实体")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "文件ID", example = "1")
    private Long id;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称", example = "测试文档.docx")
    private String fileName;

    /**
     * 文件原始名称
     */
    @ApiModelProperty(value = "文件原始名称", example = "原始文档.docx")
    private String originalName;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径", example = "/files/documents/202305/")
    private String filePath;

    /**
     * 文件URL路径
     */
    @ApiModelProperty(value = "文件URL路径", example = "/api/file/download/1")
    private String fileUrl;

    /**
     * 文件后缀名
     */
    @ApiModelProperty(value = "文件后缀名", example = "docx")
    private String fileExt;

    /**
     * 文件大小(字节)
     */
    @ApiModelProperty(value = "文件大小(字节)", example = "1024000")
    private Long fileSize;

    /**
     * 文件所属类型（0-普通文件，1-图片，2-文档，3-视频，4-音频，5-压缩包）
     */
    @ApiModelProperty(value = "文件所属类型", example = "2", notes = "0-普通文件，1-图片，2-文档，3-视频，4-音频，5-压缩包")
    private Integer fileType;

    /**
     * 文件MIME类型
     */
    @ApiModelProperty(value = "文件MIME类型", example = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    private String mimeType;

    /**
     * 存储类型（0-本地存储，1-阿里云OSS，2-腾讯云COS，3-七牛云，4-MinIO）
     */
    @ApiModelProperty(value = "存储类型", example = "0", notes = "0-本地存储，1-阿里云OSS，2-腾讯云COS，3-七牛云，4-MinIO")
    private Integer storageType;

    /**
     * 所属文件夹ID
     */
    @ApiModelProperty(value = "所属文件夹ID", example = "10")
    private Long folderId;

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
     * 文件MD5值，用于秒传
     */
    @ApiModelProperty(value = "文件MD5值", example = "d41d8cd98f00b204e9800998ecf8427e")
    private String fileMd5;

    /**
     * 文件状态（0-已删除，1-正常）
     */
    @TableLogic
    @ApiModelProperty(value = "文件状态", example = "1", notes = "0-已删除，1-正常")
    private Integer status;

    /**
     * 是否收藏（0-否，1-是）
     */
    @ApiModelProperty(value = "是否收藏", example = "0", notes = "0-否，1-是")
    private Integer isFavorite;

    /**
     * 是否共享（0-否，1-是）
     */
    @ApiModelProperty(value = "是否共享", example = "0", notes = "0-否，1-是")
    private Integer isShared;

    /**
     * 是否公开（0-否，1-是）
     */
    @ApiModelProperty(value = "是否公开", example = "0", notes = "0-否，1-是")
    private Integer isPublic;

    /**
     * 下载次数
     */
    @ApiModelProperty(value = "下载次数", example = "0")
    private Integer downloadCount;

    /**
     * 预览次数
     */
    @ApiModelProperty(value = "预览次数", example = "0")
    private Integer previewCount;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "这是一个重要文档")
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

    /**
     * 获取文件扩展名
     */
    public String getFileExtension() {
        if (StringUtils.isNotBlank(this.fileExt)) {
            return this.fileExt;
        }
        
        if (StringUtils.isBlank(this.fileName)) {
            return "";
        }
        
        int lastDotIndex = this.fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < this.fileName.length() - 1) {
            return this.fileName.substring(lastDotIndex + 1);
        }
        
        return "";
    }
    
    /**
     * 设置文件扩展名
     * 同时会更新fileExt属性
     *
     * @param extension 文件扩展名，不包含点(.)
     * @return 返回当前对象，方便链式调用
     */
    public FileInfo setFileExtension(String extension) {
        if (extension != null) {
            this.fileExt = extension.startsWith(".") ? extension.substring(1) : extension;
        } else {
            this.fileExt = null;
        }
        return this;
    }
}