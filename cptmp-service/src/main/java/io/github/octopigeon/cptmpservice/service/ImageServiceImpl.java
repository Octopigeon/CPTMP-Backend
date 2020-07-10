package io.github.octopigeon.cptmpservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
public class ImageServiceImpl implements ImageService{
    /**
     * 图片上传
     *
     * @param image 图片
     * @return 图片存储的url
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public String imageUpload(MultipartFile image) throws IllegalStateException, IOException {
        return null;
    }

    /**
     * 图片下载
     *
     * @param imageUrl 图片url
     * @return 是否传输成功
     * @throws IOException
     */
    @Override
    public Boolean imageDownload(String imageUrl) throws IOException {
        return null;
    }
}
