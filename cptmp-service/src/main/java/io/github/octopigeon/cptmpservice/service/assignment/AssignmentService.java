package io.github.octopigeon.cptmpservice.service.assignment;

import io.github.octopigeon.cptmpservice.dto.assignment.AssignmentDTO;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Deprecated
public interface AssignmentService extends BaseNormalService<AssignmentDTO>, BaseFileService {
    /**
     * 给资源库上传文件
     * @param file 文件
     * @param assignmentId 作业Id
     */
    void uploadDocument(MultipartFile file, BigInteger assignmentId) throws Exception;

    /**
     * 删除资源库中文件
     * @param assignmentId 作业Id
     * @param file fileDTO
     */
    void removeDocument(BigInteger assignmentId, FileDTO file) throws Exception;
}
