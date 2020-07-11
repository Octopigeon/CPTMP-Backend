package io.github.octopigeon.cptmpservice.service.attachmentfile;

import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
public interface AttachmentFileService extends BaseFileService {
    /**
     * 移除文件（只移除索引）
     * @param fileName 文件名
     * @throws Exception
     */
    void remove(String fileName) throws Exception;

    /**
     * 添加文件
     * @param file 文件
     * @throws Exception
     */
    void add(MultipartFile file) throws Exception;
}
