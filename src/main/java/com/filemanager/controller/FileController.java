package com.filemanager.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filemanager.common.Result;
import com.filemanager.entity.FileInfo;
import com.filemanager.service.FileInfoService;
import com.filemanager.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Api(tags = "文件管理接口", description = "提供文件上传、下载、查询、删除等接口")
public class FileController {

    private final FileInfoService fileInfoService;

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return 1L; // 暂时固定返回1，实际应从认证对象中获取用户ID
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param file     文件
     * @param folderId 文件夹ID
     * @return 文件信息
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件到指定文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "__file"),
            @ApiImplicitParam(name = "folderId", value = "文件夹ID", defaultValue = "0")
    })
    public Result<FileInfo> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderId", required = false, defaultValue = "0") Long folderId) {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            Long userId = getCurrentUserId();
            if (userId == null) {
                return Result.error("未登录或登录已过期");
            }

            FileInfo fileInfo = fileInfoService.uploadFile(file, folderId, userId);
            return Result.success(fileInfo);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 批量上传文件
     *
     * @param files    文件列表
     * @param folderId 文件夹ID
     * @return 文件信息列表
     */
    @PostMapping("/batch-upload")
    @ApiOperation(value = "批量上传文件", notes = "批量上传文件到指定文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "文件列表", required = true, dataType = "__file", allowMultiple = true),
            @ApiImplicitParam(name = "folderId", value = "文件夹ID", defaultValue = "0")
    })
    public Result<List<FileInfo>> batchUpload(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "folderId", required = false, defaultValue = "0") Long folderId) {
        if (files == null || files.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            Long userId = getCurrentUserId();
            if (userId == null) {
                return Result.error("未登录或登录已过期");
            }

            List<FileInfo> fileInfoList = fileInfoService.batchUploadFiles(files, folderId, userId);
            return Result.success(fileInfoList);
        } catch (Exception e) {
            log.error("批量上传文件失败", e);
            return Result.error("批量上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param fileId   文件ID
     * @param response HTTP响应对象
     * @return 文件内容
     */
    @GetMapping("/download/{fileId}")
    @ApiOperation(value = "下载文件", notes = "根据文件ID下载文件")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    public ResponseEntity<byte[]> download(@PathVariable Long fileId, HttpServletResponse response) {
        try {
            Long userId = getCurrentUserId();
            if (userId == null) {
                return ResponseEntity.badRequest().build();
            }

            FileInfo fileInfo = fileInfoService.getById(fileId);
            if (fileInfo == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileData = fileInfoService.getFileBytes(fileId, userId);
            String encodedFileName = URLEncoder.encode(fileInfo.getOriginalName(), StandardCharsets.UTF_8.name())
                    .replaceAll("\\+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(FileUtils.getMimeType(fileInfo.getFileExt())))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(fileData);
        } catch (Exception e) {
            log.error("文件下载失败", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 预览文件
     *
     * @param fileId   文件ID
     * @param response HTTP响应对象
     */
    @GetMapping("/preview/{fileId}")
    @ApiOperation(value = "预览文件", notes = "根据文件ID预览文件")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    public void preview(@PathVariable Long fileId, HttpServletResponse response) {
        try {
            Long userId = getCurrentUserId();
            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            fileInfoService.previewFile(fileId, userId, response);
        } catch (Exception e) {
            log.error("文件预览失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取文件信息
     *
     * @param fileId 文件ID
     * @return 文件信息
     */
    @GetMapping("/{fileId}")
    @ApiOperation(value = "获取文件信息", notes = "根据文件ID获取文件详细信息")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    public Result<FileInfo> getInfo(@PathVariable Long fileId) {
        FileInfo fileInfo = fileInfoService.getById(fileId);
        if (fileInfo == null) {
            return Result.error("文件不存在");
        }
        return Result.success(fileInfo);
    }

    /**
     * 获取文件列表
     *
     * @param folderId 文件夹ID
     * @return 文件列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取文件列表", notes = "获取指定文件夹下的文件列表")
    @ApiImplicitParam(name = "folderId", value = "文件夹ID", defaultValue = "0")
    public Result<List<FileInfo>> getList(@RequestParam(value = "folderId", required = false, defaultValue = "0") Long folderId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            List<FileInfo> fileInfoList = fileInfoService.getFileListByFolder(folderId, userId);
            return Result.success(fileInfoList);
        } catch (Exception e) {
            log.error("获取文件列表失败", e);
            return Result.error("获取文件列表失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询文件列表
     *
     * @param current  当前页码
     * @param size     每页大小
     * @param folderId 文件夹ID
     * @param fileName 文件名关键词
     * @param fileType 文件类型
     * @return 文件分页列表
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询文件列表", notes = "根据条件分页查询文件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页大小", defaultValue = "10"),
            @ApiImplicitParam(name = "folderId", value = "文件夹ID", defaultValue = "0"),
            @ApiImplicitParam(name = "fileName", value = "文件名关键词"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "Integer")
    })
    public Result<IPage<FileInfo>> page(
            @RequestParam(value = "current", required = false, defaultValue = "1") long current,
            @RequestParam(value = "size", required = false, defaultValue = "10") long size,
            @RequestParam(value = "folderId", required = false, defaultValue = "0") Long folderId,
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "fileType", required = false) Integer fileType) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            Page<FileInfo> page = new Page<>(current, size);
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("folderId", folderId);
            params.put("fileName", fileName);
            params.put("fileType", fileType);

            IPage<FileInfo> pageResult = fileInfoService.pageFileList(page, params);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询文件列表失败", e);
            return Result.error("分页查询文件列表失败: " + e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @DeleteMapping("/{fileId}")
    @ApiOperation(value = "删除文件", notes = "根据文件ID删除文件（移入回收站）")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    public Result<Boolean> delete(@PathVariable Long fileId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.deleteFile(fileId, userId);
            return success ? Result.success(true) : Result.error("删除文件失败");
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return Result.error("删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除文件
     *
     * @param fileIds 文件ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除文件", notes = "批量删除文件（移入回收站）")
    @ApiImplicitParam(name = "fileIds", value = "文件ID列表", required = true, dataType = "List")
    public Result<Boolean> batchDelete(@RequestBody List<Long> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return Result.error("文件ID列表不能为空");
        }

        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.batchDeleteFiles(fileIds, userId);
            return success ? Result.success(true) : Result.error("批量删除文件失败");
        } catch (Exception e) {
            log.error("批量删除文件失败", e);
            return Result.error("批量删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 恢复文件
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @PutMapping("/restore/{fileId}")
    @ApiOperation(value = "恢复文件", notes = "根据文件ID恢复文件（从回收站恢复）")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    public Result<Boolean> restore(@PathVariable Long fileId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.restoreFile(fileId, userId);
            return success ? Result.success(true) : Result.error("恢复文件失败");
        } catch (Exception e) {
            log.error("恢复文件失败", e);
            return Result.error("恢复文件失败: " + e.getMessage());
        }
    }

    /**
     * 批量恢复文件
     *
     * @param fileIds 文件ID列表
     * @return 操作结果
     */
    @PutMapping("/restore/batch")
    @ApiOperation(value = "批量恢复文件", notes = "批量恢复文件（从回收站恢复）")
    @ApiImplicitParam(name = "fileIds", value = "文件ID列表", required = true, dataType = "List")
    public Result<Boolean> batchRestore(@RequestBody List<Long> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return Result.error("文件ID列表不能为空");
        }

        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.batchRestoreFiles(fileIds, userId);
            return success ? Result.success(true) : Result.error("批量恢复文件失败");
        } catch (Exception e) {
            log.error("批量恢复文件失败", e);
            return Result.error("批量恢复文件失败: " + e.getMessage());
        }
    }

    /**
     * 永久删除文件
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @DeleteMapping("/permanent/{fileId}")
    @ApiOperation(value = "永久删除文件", notes = "根据文件ID永久删除文件（物理删除）")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    public Result<Boolean> permanentDelete(@PathVariable Long fileId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.permanentDeleteFile(fileId, userId);
            return success ? Result.success(true) : Result.error("永久删除文件失败");
        } catch (Exception e) {
            log.error("永久删除文件失败", e);
            return Result.error("永久删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 移动文件
     *
     * @param fileId        文件ID
     * @param targetFolderId 目标文件夹ID
     * @return 操作结果
     */
    @PutMapping("/move/{fileId}")
    @ApiOperation(value = "移动文件", notes = "移动文件到指定文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "targetFolderId", value = "目标文件夹ID", required = true, dataType = "Long")
    })
    public Result<Boolean> move(
            @PathVariable Long fileId,
            @RequestParam("targetFolderId") Long targetFolderId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.moveFile(fileId, targetFolderId, userId);
            return success ? Result.success(true) : Result.error("移动文件失败");
        } catch (Exception e) {
            log.error("移动文件失败", e);
            return Result.error("移动文件失败: " + e.getMessage());
        }
    }

    /**
     * 重命名文件
     *
     * @param fileId     文件ID
     * @param newFileName 新文件名
     * @return 操作结果
     */
    @PutMapping("/rename/{fileId}")
    @ApiOperation(value = "重命名文件", notes = "修改文件名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "newFileName", value = "新文件名", required = true, dataType = "String")
    })
    public Result<Boolean> rename(
            @PathVariable Long fileId,
            @RequestParam("newFileName") String newFileName) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.renameFile(fileId, newFileName, userId);
            return success ? Result.success(true) : Result.error("重命名文件失败");
        } catch (Exception e) {
            log.error("重命名文件失败", e);
            return Result.error("重命名文件失败: " + e.getMessage());
        }
    }

    /**
     * 收藏/取消收藏文件
     *
     * @param fileId   文件ID
     * @param favorite 是否收藏
     * @return 操作结果
     */
    @PutMapping("/favorite/{fileId}")
    @ApiOperation(value = "收藏/取消收藏文件", notes = "收藏或取消收藏文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "favorite", value = "是否收藏", required = true, dataType = "Boolean")
    })
    public Result<Boolean> favorite(
            @PathVariable Long fileId,
            @RequestParam("favorite") Boolean favorite) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.favoriteFile(fileId, userId, favorite);
            return success ? Result.success(true) : Result.error("操作失败");
        } catch (Exception e) {
            log.error("收藏/取消收藏文件失败", e);
            return Result.error("操作失败: " + e.getMessage());
        }
    }

    /**
     * 获取回收站文件列表
     *
     * @return 回收站文件列表
     */
    @GetMapping("/trash")
    @ApiOperation(value = "获取回收站文件列表", notes = "获取当前用户回收站中的文件列表")
    public Result<List<FileInfo>> getTrashFiles() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            List<FileInfo> fileInfoList = fileInfoService.getTrashFiles(userId);
            return Result.success(fileInfoList);
        } catch (Exception e) {
            log.error("获取回收站文件列表失败", e);
            return Result.error("获取回收站文件列表失败: " + e.getMessage());
        }
    }

    /**
     * 清空回收站
     *
     * @return 操作结果
     */
    @DeleteMapping("/trash/clear")
    @ApiOperation(value = "清空回收站", notes = "清空当前用户回收站中的所有文件")
    public Result<Boolean> clearTrash() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            boolean success = fileInfoService.clearTrash(userId);
            return success ? Result.success(true) : Result.error("清空回收站失败");
        } catch (Exception e) {
            log.error("清空回收站失败", e);
            return Result.error("清空回收站失败: " + e.getMessage());
        }
    }

    /**
     * 获取收藏的文件列表
     *
     * @return 收藏的文件列表
     */
    @GetMapping("/favorites")
    @ApiOperation(value = "获取收藏的文件列表", notes = "获取当前用户收藏的文件列表")
    public Result<List<FileInfo>> getFavoriteFiles() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            List<FileInfo> fileInfoList = fileInfoService.getFavoriteFiles(userId);
            return Result.success(fileInfoList);
        } catch (Exception e) {
            log.error("获取收藏的文件列表失败", e);
            return Result.error("获取收藏的文件列表失败: " + e.getMessage());
        }
    }

    /**
     * 搜索文件
     *
     * @param keyword 关键词
     * @return 文件列表
     */
    @GetMapping("/search")
    @ApiOperation(value = "搜索文件", notes = "根据关键词搜索文件")
    @ApiImplicitParam(name = "keyword", value = "关键词", required = true)
    public Result<List<FileInfo>> searchFiles(@RequestParam("keyword") String keyword) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            List<FileInfo> fileInfoList = fileInfoService.searchFiles(keyword, userId);
            return Result.success(fileInfoList);
        } catch (Exception e) {
            log.error("搜索文件失败", e);
            return Result.error("搜索文件失败: " + e.getMessage());
        }
    }

    /**
     * 秒传文件
     *
     * @param fileMd5  文件MD5值
     * @param fileName 文件名
     * @param folderId 文件夹ID
     * @return 文件信息
     */
    @PostMapping("/rapid-upload")
    @ApiOperation(value = "秒传文件", notes = "通过文件MD5值实现秒传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileMd5", value = "文件MD5值", required = true),
            @ApiImplicitParam(name = "fileName", value = "文件名", required = true),
            @ApiImplicitParam(name = "folderId", value = "文件夹ID", defaultValue = "0")
    })
    public Result<FileInfo> rapidUpload(
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("fileName") String fileName,
            @RequestParam(value = "folderId", required = false, defaultValue = "0") Long folderId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录或登录已过期");
        }

        try {
            FileInfo fileInfo = fileInfoService.rapidUpload(fileMd5, fileName, folderId, userId);
            if (fileInfo != null) {
                return Result.success(fileInfo);
            } else {
                return Result.error("秒传失败，文件不存在");
            }
        } catch (Exception e) {
            log.error("秒传文件失败", e);
            return Result.error("秒传文件失败: " + e.getMessage());
        }
    }

    /**
     * 检查文件是否存在（用于秒传）
     *
     * @param fileMd5 文件MD5值
     * @return 文件信息
     */
    @GetMapping("/check-md5")
    @ApiOperation(value = "检查文件是否存在", notes = "检查文件MD5值是否存在，用于秒传")
    @ApiImplicitParam(name = "fileMd5", value = "文件MD5值", required = true)
    public Result<FileInfo> checkFileMd5(@RequestParam("fileMd5") String fileMd5) {
        try {
            FileInfo fileInfo = fileInfoService.checkFileExistByMd5(fileMd5);
            return Result.success(fileInfo);
        } catch (Exception e) {
            log.error("检查文件MD5失败", e);
            return Result.error("检查文件MD5失败: " + e.getMessage());
        }
    }
}