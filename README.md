# 简单文件管理系统 (Simple File Manager)

基于Spring Boot的简单文件管理系统，提供文件上传、下载、分享和管理功能。

## 功能特点

- 文件上传：支持单文件和批量上传，可指定上传文件夹
- 文件下载：单文件下载，支持断点续传
- 文件管理：移动、复制、重命名、删除文件
- 文件夹管理：创建、删除、重命名文件夹
- 文件分享：生成分享链接，支持提取码和过期时间设置
- 文件收藏：收藏常用文件
- 回收站：删除的文件可恢复或永久删除
- 文件预览：在线预览图片、文档、音视频等
- 秒传功能：基于MD5的文件秒传
- 多种存储方式：本地存储、阿里云OSS、腾讯云COS等

## 技术栈

- 后端：Spring Boot 2.7.x
- 数据库：MySQL 8.x
- 持久层：MyBatis Plus 3.5.x
- 缓存：Redis
- 安全：Spring Security
- API文档：Knife4j (基于Swagger)
- 工具库：Hutool、Lombok、FastJSON

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+

### 本地开发

1. 克隆项目
```bash
git clone https://github.com/shenkeju123/simple-file-manager.git
```

2. 初始化数据库
```sql
# 执行src/main/resources/db/schema.sql文件
```

3. 配置application.yml
```yaml
# 根据你的环境修改数据库连接等配置
```

4. 启动应用
```bash
mvn spring-boot:run
```

5. 访问API文档
```
http://localhost:8080/doc.html
```

## 接口说明

### 文件上传

```
POST /api/file/upload
```

### 文件下载

```
GET /api/file/download/{fileId}
```

### 获取文件列表

```
GET /api/file/list?folderId={folderId}
```

### 文件分享

```
POST /api/file/share
```

## 部署

### Docker部署

```bash
# 构建Docker镜像
docker build -t simple-file-manager .

# 运行容器
docker run -d -p 8080:8080 --name file-manager simple-file-manager
```

### jar包部署

```bash
# 打包
mvn clean package

# 运行
java -jar target/simple-file-manager-1.0.0.jar
```

## 配置说明

### 存储配置

默认使用本地存储，文件保存在`${user.home}/file-manager/upload`目录下。

支持的存储类型：
- 本地存储
- 阿里云OSS
- 腾讯云COS
- 七牛云
- MinIO

### 文件上传配置

```yaml
file:
  upload:
    path: ${user.home}/file-manager/upload
    url-prefix: /files
    max-size: 104857600
    allow-types: jpg,jpeg,png,gif,doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar,7z,mp3,mp4,avi,flv
```

## 开发计划

- [ ] 支持WebDAV协议
- [ ] 支持文件在线编辑
- [ ] 支持多文件打包下载
- [ ] 支持文件版本控制
- [ ] 添加WebUploader大文件分片上传
- [ ] 完善权限管理系统
- [ ] 支持Docker Compose一键部署

## 贡献指南

欢迎贡献代码或提出建议，请先fork项目，创建分支，提交变更后发起Pull Request。

## 协议

本项目采用 [MIT 许可证](LICENSE)。