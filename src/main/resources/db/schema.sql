-- 创建数据库
CREATE DATABASE IF NOT EXISTS file_manager DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE file_manager;

-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT 0 COMMENT '性别（0-未知，1-男，2-女）',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `post_id` bigint(20) DEFAULT NULL COMMENT '职位ID',
  `role_ids` varchar(100) DEFAULT NULL COMMENT '角色ID列表，多个用逗号分隔',
  `status` tinyint(4) DEFAULT 1 COMMENT '用户状态（0-禁用，1-正常）',
  `storage_limit` bigint(20) DEFAULT 1073741824 COMMENT '存储空间限制（字节）',
  `storage_used` bigint(20) DEFAULT 0 COMMENT '已使用存储空间（字节）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除（0-未删除，1-已删除）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  INDEX `idx_status` (`status`),
  INDEX `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 文件信息表
CREATE TABLE IF NOT EXISTS `sys_file_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  `original_name` varchar(255) NOT NULL COMMENT '文件原始名称',
  `file_path` varchar(500) NOT NULL COMMENT '文件路径',
  `file_url` varchar(1000) DEFAULT NULL COMMENT '文件URL路径',
  `file_ext` varchar(20) DEFAULT NULL COMMENT '文件后缀名',
  `file_size` bigint(20) DEFAULT 0 COMMENT '文件大小(字节)',
  `file_type` tinyint(4) DEFAULT 0 COMMENT '文件所属类型（0-普通文件，1-图片，2-文档，3-视频，4-音频，5-压缩包）',
  `mime_type` varchar(100) DEFAULT NULL COMMENT '文件MIME类型',
  `storage_type` tinyint(4) DEFAULT 0 COMMENT '存储类型（0-本地存储，1-阿里云OSS，2-腾讯云COS，3-七牛云，4-MinIO）',
  `folder_id` bigint(20) DEFAULT 0 COMMENT '所属文件夹ID',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新用户ID',
  `belong_type` tinyint(4) DEFAULT 0 COMMENT '所属类型（0-个人文件，1-部门文件，2-公共文件）',
  `dept_id` bigint(20) DEFAULT 0 COMMENT '部门ID，当belongType=1时有效',
  `file_md5` varchar(32) DEFAULT NULL COMMENT '文件MD5值，用于秒传',
  `status` tinyint(4) DEFAULT 1 COMMENT '文件状态（0-已删除，1-正常）',
  `is_favorite` tinyint(4) DEFAULT 0 COMMENT '是否收藏（0-否，1-是）',
  `is_shared` tinyint(4) DEFAULT 0 COMMENT '是否共享（0-否，1-是）',
  `is_public` tinyint(4) DEFAULT 0 COMMENT '是否公开（0-否，1-是）',
  `download_count` int(11) DEFAULT 0 COMMENT '下载次数',
  `preview_count` int(11) DEFAULT 0 COMMENT '预览次数',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间，用于回收站功能',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_folder_id` (`folder_id`),
  INDEX `idx_create_user_id` (`create_user_id`),
  INDEX `idx_file_md5` (`file_md5`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';

-- 文件夹表
CREATE TABLE IF NOT EXISTS `sys_folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件夹ID',
  `folder_name` varchar(100) NOT NULL COMMENT '文件夹名称',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父级文件夹ID',
  `folder_path` varchar(500) DEFAULT '/' COMMENT '文件夹路径',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新用户ID',
  `belong_type` tinyint(4) DEFAULT 0 COMMENT '所属类型（0-个人文件，1-部门文件，2-公共文件）',
  `dept_id` bigint(20) DEFAULT 0 COMMENT '部门ID，当belongType=1时有效',
  `status` tinyint(4) DEFAULT 1 COMMENT '文件夹状态（0-已删除，1-正常）',
  `is_favorite` tinyint(4) DEFAULT 0 COMMENT '是否收藏（0-否，1-是）',
  `is_public` tinyint(4) DEFAULT 0 COMMENT '是否公开（0-否，1-是）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  INDEX `idx_parent_id` (`parent_id`),
  INDEX `idx_create_user_id` (`create_user_id`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件夹表';

-- 文件分享表
CREATE TABLE IF NOT EXISTS `sys_file_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分享ID',
  `share_title` varchar(100) DEFAULT NULL COMMENT '分享标题',
  `share_type` tinyint(4) NOT NULL COMMENT '分享类型（1-文件，2-文件夹）',
  `file_id` bigint(20) DEFAULT NULL COMMENT '文件ID',
  `folder_id` bigint(20) DEFAULT NULL COMMENT '文件夹ID',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户ID',
  `share_url` varchar(255) NOT NULL COMMENT '分享链接',
  `share_code` varchar(50) NOT NULL COMMENT '分享码',
  `extract_code` varchar(20) DEFAULT NULL COMMENT '提取码',
  `expire_type` tinyint(4) DEFAULT 0 COMMENT '过期类型（0-永久有效，1-天数，2-自定义时间）',
  `expire_days` int(11) DEFAULT NULL COMMENT '过期天数',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `need_code` tinyint(4) DEFAULT 1 COMMENT '是否需要提取码（0-否，1-是）',
  `allow_download` tinyint(4) DEFAULT 1 COMMENT '是否允许下载（0-否，1-是）',
  `access_limit` int(11) DEFAULT 0 COMMENT '访问次数限制（0-无限制）',
  `access_count` int(11) DEFAULT 0 COMMENT '已访问次数',
  `status` tinyint(4) DEFAULT 1 COMMENT '分享状态（0-已失效，1-正常）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_share_code` (`share_code`),
  INDEX `idx_file_id` (`file_id`),
  INDEX `idx_folder_id` (`folder_id`),
  INDEX `idx_create_user_id` (`create_user_id`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件分享表';

-- 存储配置表
CREATE TABLE IF NOT EXISTS `sys_storage_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `storage_type` tinyint(4) NOT NULL COMMENT '存储类型（0-本地存储，1-阿里云OSS，2-腾讯云COS，3-七牛云，4-MinIO）',
  `storage_name` varchar(50) NOT NULL COMMENT '存储名称',
  `access_key` varchar(100) DEFAULT NULL COMMENT 'AccessKey',
  `secret_key` varchar(100) DEFAULT NULL COMMENT 'SecretKey',
  `endpoint` varchar(255) DEFAULT NULL COMMENT '服务端点',
  `bucket_name` varchar(100) DEFAULT NULL COMMENT '存储桶名称',
  `domain` varchar(255) DEFAULT NULL COMMENT '访问域名',
  `base_path` varchar(255) DEFAULT '/' COMMENT '基础路径',
  `is_https` tinyint(4) DEFAULT 1 COMMENT '是否使用HTTPS（0-否，1-是）',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态（0-禁用，1-启用）',
  `is_default` tinyint(4) DEFAULT 0 COMMENT '是否默认（0-否，1-是）',
  `region` varchar(100) DEFAULT NULL COMMENT '区域',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_storage_type` (`storage_type`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储配置表';

-- 默认管理员账号
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `status`, `storage_limit`, `create_time`) 
VALUES (1, 'admin', '$2a$10$cKEVEDBvEXtYKk9nMCV.2.KL3jC1Fj6F.h8UJE2aNQ0p/mGarHYE6', '系统管理员', 'admin@example.com', 1, 10737418240, NOW());

-- 添加存储配置
INSERT INTO `sys_storage_config` (`id`, `storage_type`, `storage_name`, `domain`, `base_path`, `is_https`, `status`, `is_default`, `remark`, `create_user_id`, `create_time`) 
VALUES (1, 0, '本地存储', 'http://localhost:8080', '/upload', 0, 1, 1, '本地文件存储', 1, NOW());

-- 创建根目录文件夹
INSERT INTO `sys_folder` (`id`, `folder_name`, `parent_id`, `folder_path`, `belong_type`, `create_user_id`, `status`, `create_time`) 
VALUES (1, '根目录', 0, '/', 2, 1, 1, NOW());