package io.github.octopigeon.cptmpservice.service.trainproject;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Service
public interface ProjectService extends BaseNormalService<ProjectDTO>, BaseFileService {

    /**
     * 查询所有项目
     * @param page 页号
     * @param offset 页容量
     * @return 项目分页列表
     */
    PageInfo<ProjectDTO> findAll(int page, int offset);

    /**
     * 根据名字进行模糊查找
     * @param page 页号
     * @param offset 页内数量
     * @param name 项目名
     * @return 项目分页列表
     */
    PageInfo<ProjectDTO> findByLikeName(int page, int offset, String name);

    /**
     * 根据项目难度查找项目
     * @param page 页号
     * @param offset 页容量
     * @param level 难度水平
     * @return 项目分页列表
     */
    PageInfo<ProjectDTO> findByLevel(int page, int offset, Integer level);

    /**
     * 根据实训id查项目
     * @param page 页号
     * @param offset 页内数量
     * @param trainId 实训id
     * @return 项目分页列表
     */
    PageInfo<ProjectDTO> findByTrainId(int page, int offset, BigInteger trainId);

    /**
     * 给资源库上传文件
     * @param file 文件
     * @param projectId 项目Id
     */
    void uploadResourceLib(MultipartFile file, BigInteger projectId) throws Exception;

    /**
     * 删除资源库中文件
     * @param projectId 项目Id
     * @param file fileDTO
     */
    void removeResourceLib(BigInteger projectId, FileDTO file) throws Exception;
}
