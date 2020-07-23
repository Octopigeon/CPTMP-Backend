package io.github.octopigeon.cptmpservice.service.basefileservice;

import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件传输服务
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in 李国豪
 * @date 2020/7/10
 */
public interface BaseFileService {
    /**
     * 公开文件接收服务
     * @param file 多文件
     * @return 文件名
     * @throws Exception
     */
    FileDTO storePublicFile(MultipartFile file) throws Exception;

    /**
     * 私密附件接收服务
     * @param file 多文件
     * @return 文件名
     * @throws Exception
     */
    FileDTO storePrivateFile(MultipartFile file) throws Exception;

    /**
     * 文件下载服务
     * @param fileName 文件名
     * @param year 上传时间年份
     * @param month 上传时间月份
     * @param day 上传时间日期
     * @return 资源文件
     * @throws Exception
     */
    Resource loadPublicFile(String fileName, String year, String month, String day) throws Exception;

    /**
     * 文件下载服务
     * @param fileName 文件名
     * @param year 上传时间年份
     * @param month 上传时间月份
     * @param day 上传时间日期
     * @return 资源文件
     * @throws Exception
     */
    Resource loadPrivateFile(String fileName, String year, String month, String day) throws Exception;

    /**
     * 移除路径中的文件
     * @param path 路径
     * @return 是否从存储路径上移除成功
     * @throws Exception
     */
    Boolean removeFile(String path) throws Exception;
}
