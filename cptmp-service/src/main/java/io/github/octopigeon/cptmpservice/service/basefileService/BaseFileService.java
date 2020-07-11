package io.github.octopigeon.cptmpservice.service.basefileService;

import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件传输服务
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
public interface BaseFileService {
    /**
     * 单个文件上传接收服务
     * @param file 多文件
     * @return 文件名
     * @throws Exception
     */
    FileDTO storeFile(MultipartFile file) throws Exception;

    /**
     * 文件下载服务
     * @param fileName 文件名
     * @param year 上传时间年份
     * @param month 上传时间月份
     * @param day 上传时间日期
     * @return 资源文件
     * @throws Exception
     */
    Resource loadFile(String fileName, String year, String month, String day) throws Exception;
}
