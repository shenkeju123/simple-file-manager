package com.filemanager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.filemanager.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 文件信息服务接口
 */
public interface FileInfoService extends IService<FileInfo> {

    /**
     * 上传文件
     *
     * @param file     文件
     * @param folderId 文件夹ID
     * @param userId   用户ID
     * @return 文件信息
     */
    FileInfo uploadFile(MultipartFile file, Long folderId, Long userId);

    /**
     * 批量上传文件
     *
     * @param files    文件列表
     * @param folderId 文件夹ID
     * @param userId   用户ID
     * @return 文件信息列表
     */
    List<FileInfo> batchUploadFiles(List<MultipartFile> files, Long folderId, Long userId);

    /**
     * 下载文件
     *
     * @param fileId   文件ID
     * @param userId   用户ID
     * @param response HTTP响应对象
     */
    void downloadFile(Long fileId, Long userId, HttpServletResponse response);

    /**
     * 获取文件内容
     *
     * @param fileId 文件ID
     * @param userId 用户ID
     * @return 文件字节数组
     */
    byte[] getFileBytes(Long fileId, Long userId);

    /**
     * 删除文件（逻辑删除，移入回收站）
     *
     * @param fileId 文件ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean deleteFile(Long fileId, Long userId);

    /**
     * 批量删除文件
     *
     * @param fileIds 文件ID列表
     * @param userId  用户ID
     * @return 是否成功
     */
    boolean batchDeleteFiles(List<Long> fileIds, Long userId);

    /**
     * 恢复文件（从回收站恢复）
     *
     * @param fileId 文件ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean restoreFile(Long fileId, Long userId);

    /**
     * 批量恢复文件
     *
     * @param fileIds 文件ID列表
     * @param userId  用户ID
     * @return 是否成功
     */
    boolean batchRestoreFiles(List<Long> fileIds, Long userId);

    /**
     * 永久删除文件（物理删除）
     *
     * @param fileId 文件ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean permanentDeleteFile(Long fileId, Long userId);

    /**
     * 批量永久删除文件
     *
     * @param fileIds 文件ID列表
     * @param userId  用户ID
     * @return 是否成功
     */
    boolean batchPermanentDeleteFiles(List<Long> fileIds, Long userId);

    /**
     * 移动文件
     *
     * @param fileId        文件ID
     * @param targetFolderId 目标文件夹ID
     * @param userId        用户ID
     * @return 是否成功
     */
    boolean moveFile(Long fileId, Long targetFolderId, Long userId);

    /**
     * 批量移动文件
     *
     * @param fileIds       文件ID列表
     * @param targetFolderId 目标文件夹ID
     * @param userId        用户ID
     * @return 是否成功
     */
    boolean batchMoveFiles(List<Long> fileIds, Long targetFolderId, Long userId);

    /**
     * 复制文件
     *
     * @param fileId        文件ID
     * @param targetFolderId 目标文件夹ID
     * @param userId        用户ID
     * @return 新文件信息
     */
    FileInfo copyFile(Long fileId, Long targetFolderId, Long userId);

    /**
     * 批量复制文件
     *
     * @param fileIds       文件ID列表
     * @param targetFolderId 目标文件夹ID
     * @param userId        用户ID
     * @return 新文件信息列表
     */
    List<FileInfo> batchCopyFiles(List<Long> fileIds, Long targetFolderId, Long userId);

    /**
     * 重命名文件
     *
     * @param fileId     文件ID
     * @param newFileName 新文件名
     * @param userId     用户ID
     * @return 是否成功
     */
    boolean renameFile(Long fileId, String newFileName, Long userId);

    /**
     * 收藏/取消收藏文件
     *
     * @param fileId   文件ID
     * @param userId   用户ID
     * @param favorite 是否收藏
     * @return 是否成功
     */
    boolean favoriteFile(Long fileId, Long userId, boolean favorite);

    /**
     * 分页查询文件列表
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return 文件列表
     */
    IPage<FileInfo> pageFileList(Page<FileInfo> page, Map<String, Object> params);

    /**
     * 获取指定文件夹下的文件列表
     *
     * @param folderId 文件夹ID
     * @param userId   用户ID
     * @return 文件列表
     */
    List<FileInfo> getFileListByFolder(Long folderId, Long userId);

    /**
     * 获取收藏的文件列表
     *
     * @param userId 用户ID
     * @return 收藏的文件列表
     */
    List<FileInfo> getFavoriteFiles(Long userId);

    /**
     * 获取回收站文件列表
     *
     * @param userId 用户ID
     * @return 回收站文件列表
     */
    List<FileInfo> getTrashFiles(Long userId);

    /**
     * 清空回收站
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean clearTrash(Long userId);

    /**
     * 文件预览
     *
     * @param fileId   文件ID
     * @param userId   用户ID
     * @param response HTTP响应对象
     */
    void previewFile(Long fileId, Long userId, HttpServletResponse response);

    /**
     * 检查文件是否存在
     *
     * @param fileMd5 文件MD5值
     * @return 文件信息（如果存在）
     */
    FileInfo checkFileExistByMd5(String fileMd5);

    /**
     * 通过MD5值秒传文件
     *
     * @param fileMd5  文件MD5值
     * @param fileName 文件名
     * @param folderId 文件夹ID
     * @param userId   用户ID
     * @return 文件信息
     */
    FileInfo rapidUpload(String fileMd5, String fileName, Long folderId, Long userId);

    /**
     * 搜索文件
     *
     * @param keyword 关键词
     * @param userId  用户ID
     * @return 文件列表
     */
    List<FileInfo> searchFiles(String keyword, Long userId);
    
    /**
     * 更新用户存储空间使用量
     *
     * @param userId   用户ID
     * @param fileSize 文件大小（字节）
     * @param isAdd    是否增加（true-增加，false-减少）
     * @return 是否成功
     */
    boolean updateUserStorageUsed(Long userId, Long fileSize, boolean isAdd);
}