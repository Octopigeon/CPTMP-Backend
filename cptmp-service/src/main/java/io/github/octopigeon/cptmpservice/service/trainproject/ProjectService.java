package io.github.octopigeon.cptmpservice.service.trainproject;

import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/11
 */
@Service
public interface ProjectService extends BaseNormalService<ProjectDTO>, BaseFileService {

    /**
     * 根据名字进行模糊查找
     * @param name 项目名
     * @return 列表
     */
    List<ProjectDTO> findByLikeName(String name);

    /**
     * 给资源库上传文件
     * @param file 文件
     * @param projectId 项目Id
     */
    void uploadResourceLib(MultipartFile file, BigInteger projectId) throws Exception;
}
