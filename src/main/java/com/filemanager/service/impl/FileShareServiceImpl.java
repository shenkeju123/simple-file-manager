package com.filemanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.filemanager.constant.FileConstants;
import com.filemanager.entity.FileShare;
import com.filemanager.mapper.FileShareMapper;
import com.filemanager.service.FileShareService;
import com.filemanager.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件分享服务实现类
 *
 * @author filemanager
 */
@Service
@Slf4j
public class FileShareServiceImpl extends ServiceImpl<FileShareMapper, FileShare> implements FileShareService {

    @Override
    public FileShare createShare(FileShare fileShare, Long userId) {
        // 设置分享URL和提取码
        fileShare.setShareUrl(generateShareUrl());
        if (fileShare.getNeedCode()) {
            fileShare.setShareCode(generateShareCode());
        }
        
        // 设置过期时间
        setExpireTime(fileShare);
        
        // 设置创建者和初始状态
        fileShare.setCreateUserId(userId);
        fileShare.setStatus(FileConstants.SHARE_STATUS_ACTIVE);
        fileShare.setAccessCount(0);
        fileShare.setCreateTime(LocalDateTime.now());
        
        // 保存分享记录
        save(fileShare);
        return fileShare;
    }

    @Override
    public boolean cancelShare(Long shareId, Long userId) {
        LambdaQueryWrapper<FileShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileShare::getId, shareId)
                   .eq(FileShare::getCreateUserId, userId);
        
        FileShare fileShare = new FileShare();
        fileShare.setStatus(FileConstants.SHARE_STATUS_CANCELED);
        
        return update(fileShare, queryWrapper);
    }

    @Override
    public FileShare getShareInfo(String shareUrl) {
        if (StringUtils.isBlank(shareUrl)) {
            return null;
        }
        
        LambdaQueryWrapper<FileShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileShare::getShareUrl, shareUrl)
                   .eq(FileShare::getStatus, FileConstants.SHARE_STATUS_ACTIVE);
        
        return getOne(queryWrapper);
    }

    @Override
    public IPage<FileShare> listUserShares(Page<FileShare> page, Long userId) {
        LambdaQueryWrapper<FileShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileShare::getCreateUserId, userId)
                   .orderByDesc(FileShare::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public FileShare checkShareValid(String shareUrl, String shareCode) {
        FileShare share = getShareInfo(shareUrl);
        
        if (share == null) {
            return null;
        }
        
        // 检查是否过期
        if (isShareExpired(share)) {
            // 更新分享状态为已过期
            FileShare updateShare = new FileShare();
            updateShare.setId(share.getId());
            updateShare.setStatus(FileConstants.SHARE_STATUS_EXPIRED);
            updateById(updateShare);
            return null;
        }
        
        // 检查提取码
        if (share.getNeedCode() && !StringUtils.equals(shareCode, share.getShareCode())) {
            return null;
        }
        
        return share;
    }

    @Override
    public boolean incrementAccessCount(Long shareId) {
        FileShare share = getById(shareId);
        if (share == null) {
            return false;
        }
        
        FileShare updateShare = new FileShare();
        updateShare.setId(shareId);
        updateShare.setAccessCount(share.getAccessCount() + 1);
        
        return updateById(updateShare);
    }

    @Override
    public boolean updateShare(FileShare fileShare, Long userId) {
        // 检查分享记录是否存在且属于当前用户
        FileShare existShare = getById(fileShare.getId());
        if (existShare == null || !Objects.equals(existShare.getCreateUserId(), userId)) {
            return false;
        }
        
        // 重新设置过期时间
        if (!Objects.equals(existShare.getExpireType(), fileShare.getExpireType()) 
                || !Objects.equals(existShare.getExpireDays(), fileShare.getExpireDays())) {
            setExpireTime(fileShare);
        }
        
        // 如果需要提取码但当前没有，则生成新的
        if (fileShare.getNeedCode() && StringUtils.isBlank(existShare.getShareCode())) {
            fileShare.setShareCode(generateShareCode());
        }
        
        fileShare.setUpdateTime(LocalDateTime.now());
        return updateById(fileShare);
    }
    
    /**
     * 设置分享的过期时间
     * 
     * @param fileShare 分享对象
     */
    private void setExpireTime(FileShare fileShare) {
        if (FileConstants.EXPIRE_TYPE_NEVER.equals(fileShare.getExpireType())) {
            fileShare.setExpireTime(null);
        } else if (FileConstants.EXPIRE_TYPE_DAYS.equals(fileShare.getExpireType())) {
            LocalDateTime expireTime = LocalDateTime.now().plusDays(fileShare.getExpireDays());
            fileShare.setExpireTime(expireTime);
        }
    }
    
    /**
     * 检查分享是否已过期
     * 
     * @param share 分享对象
     * @return 是否已过期
     */
    private boolean isShareExpired(FileShare share) {
        if (FileConstants.EXPIRE_TYPE_NEVER.equals(share.getExpireType())) {
            return false;
        }
        
        return share.getExpireTime() != null && LocalDateTime.now().isAfter(share.getExpireTime());
    }
    
    /**
     * 生成分享链接的唯一标识
     * 
     * @return 分享URL
     */
    private String generateShareUrl() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
    
    /**
     * 生成提取码
     * 
     * @return 提取码
     */
    private String generateShareCode() {
        // 生成4位随机数字
        int random = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(random);
    }
}