package io.github.octopigeon.cptmpservice.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Repository
public class FileTransferServiceImpl implements FileTransferService{

    /**
     * 单个文件上传接收服务
     *
     * @param file 多文件
     * @return 文件对应的url
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public String singleFileUpload(MultipartFile file) throws IllegalStateException, IOException {
        return null;
    }

    /**
     * 多文件上传接收服务
     *
     * @param files 多文件
     * @return 多个文件的url
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public String[] multiFileUpload(MultipartFile[] files) throws IllegalStateException, IOException {
        return new String[0];
    }

    /**
     * 文件下载服务
     *
     * @param fileUrl 文件的url
     * @return 是否传输成功
     * @throws IOException
     */
    @Override
    public Boolean singleFileDownload(String fileUrl) throws IOException {
        return null;
    }
}
