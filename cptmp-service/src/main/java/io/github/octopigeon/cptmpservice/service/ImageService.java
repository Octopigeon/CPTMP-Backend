package io.github.octopigeon.cptmpservice.service;

import org.springframework.core.io.Resource;
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
public interface ImageService {
    /**
     * 图片上传
     * @param image 图片
     * @param username 用户名
     * @return 图片存储的url
     * @throws IllegalStateException
     * @throws IOException
     */
    String storeImage(MultipartFile image, String username, String avatarOrFace) throws Exception;

    /**
     * 图片下载
     * @param imageName 图片url
     * @return 是否传输成功
     * @throws IOException
     */
    Resource uploadImage(String imageName, String avatarOrFace) throws Exception;
}
