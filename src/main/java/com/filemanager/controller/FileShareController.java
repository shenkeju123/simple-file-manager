package com.filemanager.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filemanager.common.Result;
import com.filemanager.entity.LoginUser;
import com.filemanager.entity.FileShare;
import com.filemanager.exception.BusinessException;
import com.filemanager.service.FileShareService;
import com.filemanager.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件分享控制器
 *
 * @author filemanager
 */
@Api(tags = "文件分享接口")
@RestController
@RequestMapping("/api/file/share")
@RequiredArgsConstructor
public class FileShareController {

    private final FileShareService fileShareService;

    @ApiOperation("创建分享")
    @PostMapping("/create")
    public Result<Map<String, Object>> createShare(@RequestBody FileShare fileShare) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            FileShare share = fileShareService.createShare(fileShare, userId);
            Map<String, Object> result = new HashMap<>(4);
            result.put("shareId", share.getId());
            result.put("shareUrl", share.getShareUrl());
            result.put("shareCode", share.getShareCode());
            result.put("needCode", share.getNeedCode());
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("创建分享失败：" + e.getMessage());
        }
    }

    @ApiOperation("快速分享文件")
    @PostMapping("/file/{fileId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(name = "expireType", value = "过期类型：0-永久，1-1天，2-7天，3-30天，4-自定义"),
            @ApiImplicitParam(name = "expireDays", value = "自定义过期天数，当expireType=4时有效"),
            @ApiImplicitParam(name = "needCode", value = "是否需要提取码：true-是，false-否"),
            @ApiImplicitParam(name = "allowDownload", value = "是否允许下载：1-允许，0-不允许")
    })
    public Result<Map<String, Object>> shareFile(@PathVariable("fileId") Long fileId,
                                  @RequestParam(value = "expireType", defaultValue = "2") Integer expireType,
                                  @RequestParam(value = "expireDays", required = false) Integer expireDays,
                                  @RequestParam(value = "needCode", defaultValue = "true") Boolean needCode,
                                  @RequestParam(value = "allowDownload", defaultValue = "1") Integer allowDownload) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            FileShare share = fileShareService.shareFile(fileId, userId, expireType, needCode, allowDownload);
            Map<String, Object> result = new HashMap<>(4);
            result.put("shareId", share.getId());
            result.put("shareUrl", share.getShareUrl());
            result.put("shareCode", share.getShareCode());
            result.put("needCode", share.getNeedCode());
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("分享文件失败：" + e.getMessage());
        }
    }

    @ApiOperation("快速分享文件夹")
    @PostMapping("/folder/{folderId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folderId", value = "文件夹ID", required = true),
            @ApiImplicitParam(name = "expireType", value = "过期类型：0-永久，1-1天，2-7天，3-30天，4-自定义"),
            @ApiImplicitParam(name = "expireDays", value = "自定义过期天数，当expireType=4时有效"),
            @ApiImplicitParam(name = "needCode", value = "是否需要提取码：true-是，false-否"),
            @ApiImplicitParam(name = "allowDownload", value = "是否允许下载：1-允许，0-不允许")
    })
    public Result<Map<String, Object>> shareFolder(@PathVariable("folderId") Long folderId,
                                    @RequestParam(value = "expireType", defaultValue = "2") Integer expireType,
                                    @RequestParam(value = "expireDays", required = false) Integer expireDays,
                                    @RequestParam(value = "needCode", defaultValue = "true") Boolean needCode,
                                    @RequestParam(value = "allowDownload", defaultValue = "1") Integer allowDownload) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            FileShare share = fileShareService.shareFolder(folderId, userId, expireType, needCode, allowDownload);
            Map<String, Object> result = new HashMap<>(4);
            result.put("shareId", share.getId());
            result.put("shareUrl", share.getShareUrl());
            result.put("shareCode", share.getShareCode());
            result.put("needCode", share.getNeedCode());
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("分享文件夹失败：" + e.getMessage());
        }
    }

    @ApiOperation("取消分享")
    @DeleteMapping("/{shareId}")
    public Result<Boolean> cancelShare(@PathVariable("shareId") Long shareId) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            boolean result = fileShareService.cancelShare(shareId, userId);
            if (result) {
                return Result.success(true, "取消分享成功");
            } else {
                return Result.error("取消分享失败，分享不存在或已过期");
            }
        } catch (Exception e) {
            return Result.error("取消分享失败：" + e.getMessage());
        }
    }

    @ApiOperation("批量取消分享")
    @DeleteMapping("/batch")
    public Result<Boolean> batchCancelShare(@RequestBody List<Long> shareIds) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            boolean result = fileShareService.batchCancelShare(shareIds, userId);
            if (result) {
                return Result.success(true, "批量取消分享成功");
            } else {
                return Result.error("批量取消分享失败");
            }
        } catch (Exception e) {
            return Result.error("批量取消分享失败：" + e.getMessage());
        }
    }

    @ApiOperation("更新分享信息")
    @PutMapping("/update")
    public Result<Boolean> updateShare(@RequestBody FileShare fileShare) {
        try {
            if (fileShare.getId() == null) {
                return Result.error("分享ID不能为空");
            }
            Long userId = SecurityUtils.getCurrentUserId();
            boolean result = fileShareService.updateShare(fileShare, userId);
            if (result) {
                return Result.success(true, "更新分享成功");
            } else {
                return Result.error("更新分享失败，分享不存在或已过期");
            }
        } catch (Exception e) {
            return Result.error("更新分享失败：" + e.getMessage());
        }
    }

    @ApiOperation("获取我的分享列表")
    @GetMapping("/list")
    public Result<List<FileShare>> getMyShares() {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            List<FileShare> shares = fileShareService.getUserShares(userId);
            return Result.success(shares);
        } catch (Exception e) {
            return Result.error("获取分享列表失败：" + e.getMessage());
        }
    }

    @ApiOperation("分页获取我的分享列表")
    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true),
            @ApiImplicitParam(name = "status", value = "状态：0-已失效，1-有效", required = false),
            @ApiImplicitParam(name = "shareType", value = "分享类型：1-文件，2-文件夹", required = false)
    })
    public Result<IPage<FileShare>> pageMyShares(@RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam(value = "status", required = false) Integer status,
                                  @RequestParam(value = "shareType", required = false) Integer shareType) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            Page<FileShare> page = new Page<>(pageNum, pageSize);
            IPage<FileShare> result = fileShareService.getUserShareList(page, userId, status, shareType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取分享列表失败：" + e.getMessage());
        }
    }

    @ApiOperation("获取分享统计数据")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getShareStatistics() {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            Map<String, Object> statistics = fileShareService.statisticsShare(userId);
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取分享统计数据失败：" + e.getMessage());
        }
    }

    @ApiOperation("获取分享信息")
    @GetMapping("/info/{shareCode}")
    public Result<Map<String, Object>> getShareInfo(@PathVariable("shareCode") String shareCode) {
        try {
            // 检查分享是否有效
            FileShare shareInfo = fileShareService.checkShareValid(shareCode);
            if (shareInfo == null) {
                return Result.error("分享链接不存在或已失效");
            }

            Map<String, Object> result = new HashMap<>(8);
            result.put("shareId", shareInfo.getId());
            result.put("shareTitle", shareInfo.getShareTitle());
            result.put("shareType", shareInfo.getShareType());
            result.put("needCode", shareInfo.getNeedCode());
            result.put("createUserId", shareInfo.getCreateUserId());
            result.put("createTime", shareInfo.getCreateTime());

            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取分享信息失败：" + e.getMessage());
        }
    }

    @ApiOperation("验证分享提取码")
    @PostMapping("/verify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareCode", value = "分享码", required = true),
            @ApiImplicitParam(name = "extractCode", value = "提取码", required = true)
    })
    public Result<Boolean> verifyShareCode(@RequestParam("shareCode") String shareCode,
                               @RequestParam("extractCode") String extractCode) {
        try {
            boolean verified = fileShareService.verifyShareExtractCode(shareCode, extractCode);
            if (verified) {
                return Result.success(true, "验证成功");
            } else {
                return Result.error("提取码错误或分享已失效");
            }
        } catch (Exception e) {
            return Result.error("验证分享提取码失败：" + e.getMessage());
        }
    }

    @ApiOperation("访问分享内容")
    @GetMapping("/access")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareCode", value = "分享码", required = true),
            @ApiImplicitParam(name = "extractCode", value = "提取码", required = false)
    })
    public Result<Map<String, Object>> accessShare(@RequestParam("shareCode") String shareCode,
                                    @RequestParam(value = "extractCode", required = false) String extractCode) {
        try {
            Map<String, Object> result = fileShareService.accessShare(shareCode, extractCode);
            if (Boolean.TRUE.equals(result.get("valid"))) {
                return Result.success(result);
            } else {
                return Result.error((String) result.get("message"), result);
            }
        } catch (Exception e) {
            return Result.error("访问分享内容失败：" + e.getMessage());
        }
    }

    @ApiOperation("下载分享文件")
    @GetMapping("/download/{shareCode}/{fileId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareCode", value = "分享码", required = true),
            @ApiImplicitParam(name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(name = "extractCode", value = "提取码", required = false)
    })
    public void downloadShareFile(@PathVariable("shareCode") String shareCode,
                                  @PathVariable("fileId") Long fileId,
                                  @RequestParam(value = "extractCode", required = false) String extractCode,
                                  HttpServletResponse response) {
        try {
            fileShareService.downloadShareFile(shareCode, fileId, extractCode, response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("下载分享文件失败：" + e.getMessage());
        }
    }

    @ApiOperation("获取分享内容列表")
    @GetMapping("/content")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareCode", value = "分享码", required = true),
            @ApiImplicitParam(name = "extractCode", value = "提取码", required = false),
            @ApiImplicitParam(name = "folderId", value = "文件夹ID，用于浏览子文件夹", required = false)
    })
    public Result<Map<String, Object>> getShareContentList(@RequestParam("shareCode") String shareCode,
                                             @RequestParam(value = "extractCode", required = false) String extractCode,
                                             @RequestParam(value = "folderId", required = false) Long folderId) {
        try {
            Map<String, Object> result = fileShareService.getShareContentList(shareCode, extractCode, folderId);
            return Result.success(result);
        } catch (BusinessException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("获取分享内容列表失败：" + e.getMessage());
        }
    }

    @ApiOperation("保存分享内容到我的文件夹")
    @PostMapping("/save")
    public Result<Map<String, Object>> saveShareContent(@RequestBody Map<String, Object> params) {
        try {
            String shareCode = (String) params.get("shareCode");
            String extractCode = (String) params.get("extractCode");
            Long targetFolderId = Long.valueOf(params.get("targetFolderId").toString());
            List<Long> fileIds = (List<Long>) params.get("fileIds");
            List<Long> folderIds = (List<Long>) params.get("folderIds");
            
            if (StringUtils.isBlank(shareCode)) {
                return Result.error("分享码不能为空");
            }
            
            Long userId = SecurityUtils.getCurrentUserId();
            Map<String, Object> result = fileShareService.saveShareContent(shareCode, fileIds, folderIds, 
                                                                         targetFolderId, userId, extractCode);
            return Result.success(result);
        } catch (BusinessException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("保存分享内容失败：" + e.getMessage());
        }
    }
}