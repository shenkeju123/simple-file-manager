package com.filemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filemanager.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文件信息Mapper接口
 */
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    /**
     * 分页查询文件列表
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return 文件分页列表
     */
    IPage<FileInfo> selectFileInfoPage(Page<FileInfo> page, @Param("params") Map<String, Object> params);

    /**
     * 获取指定文件夹下的文件列表
     *
     * @param folderId 文件夹ID
     * @param userId   用户ID
     * @return 文件列表
     */
    List<FileInfo> selectFileListByFolder(@Param("folderId") Long folderId, @Param("userId") Long userId);

    /**
     * 获取收藏的文件列表
     *
     * @param userId 用户ID
     * @return 收藏的文件列表
     */
    List<FileInfo> selectFavoriteFiles(@Param("userId") Long userId);

    /**
     * 获取回收站文件列表
     *
     * @param userId 用户ID
     * @return 回收站文件列表
     */
    List<FileInfo> selectTrashFiles(@Param("userId") Long userId);

    /**
     * 根据MD5查询文件信息
     *
     * @param fileMd5 文件MD5值
     * @return 文件信息
     */
    FileInfo selectFileByMd5(@Param("fileMd5") String fileMd5);

    /**
     * 搜索文件
     *
     * @param keyword 关键词
     * @param userId  用户ID
     * @return 文件列表
     */
    List<FileInfo> searchFiles(@Param("keyword") String keyword, @Param("userId") Long userId);

    /**
     * 获取用户已使用的存储空间大小
     *
     * @param userId 用户ID
     * @return 已使用的存储空间大小（字节）
     */
    Long selectUserStorageUsed(@Param("userId") Long userId);
}