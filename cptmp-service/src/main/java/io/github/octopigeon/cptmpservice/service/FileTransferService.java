package io.github.octopigeon.cptmpservice.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * 文件传输服务
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Repository
public interface FileTransferService {
    /**
     * 单个文件上传接收服务
     * @param file 多文件
     * @param userId 上传的用户
     * @param teamId 上传所属团队，可为空
     * @return 文件名
     * @throws Exception
     */
    String singleStoreFile(MultipartFile file, BigInteger userId, BigInteger teamId) throws Exception;

    /**
     * 文件下载服务
     * @param fileName 文件名
     * @return 资源文件
     * @throws Exception
     */
    Resource loadFile(String fileName, String year, String month, String day) throws Exception;

    /**
     * 删除文件
     * @param fileName 文件名
     * @return 是否删除成功
     * @throws Exception
     */
    Boolean deleteFile(String fileName, String year, String month, String day) throws Exception;
}
