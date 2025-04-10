package com.filemanager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.filemanager.entity.FileShare;

/**
 * 文件分享服务接口
 *
 * @author filemanager
 */
public interface FileShareService extends IService<FileShare> {

    /**
     * 创建分享链接
     *
     * @param fileShare 分享对象
     * @param userId 用户ID
     * @return 分享对象
     */
    FileShare createShare(FileShare fileShare, Long userId);

    /**
     * 取消分享
     *
     * @param shareId 分享ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean cancelShare(Long shareId, Long userId);

    /**
     * 获取分享信息
     *
     * @param shareUrl 分享URL
     * @return 分享对象
     */
    FileShare getShareInfo(String shareUrl);

    /**
     * 获取用户的分享列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<FileShare> listUserShares(Page<FileShare> page, Long userId);
    
    /**
     * 检查分享是否有效
     *
     * @param shareUrl 分享URL
     * @param shareCode 提取码
     * @return 有效的分享对象或null
     */
    FileShare checkShareValid(String shareUrl, String shareCode);
    
    /**
     * 增加分享的访问次数
     *
     * @param shareId 分享ID
     * @return 是否成功
     */
    boolean incrementAccessCount(Long shareId);
    
    /**
     * 更新分享信息
     *
     * @param fileShare 分享对象
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean updateShare(FileShare fileShare, Long userId);
}