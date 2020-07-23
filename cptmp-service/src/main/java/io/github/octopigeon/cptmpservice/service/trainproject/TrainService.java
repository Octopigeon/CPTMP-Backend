package io.github.octopigeon.cptmpservice.service.trainproject;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Service
public interface TrainService extends BaseNormalService<TrainDTO>, BaseFileService {

    /**
     * 分页查询所有实训
     * @param page 页号
     * @param offset 一页的数量
     * @return 实训分页信息
     */
    PageInfo<TrainDTO> findAll(int page, int offset);

    /**
     * 根据组织id查询实训
     * @param page 页号
     * @param offset 页容量
     * @param organizationId 组织id
     * @return 实训分页信息
     */
    PageInfo<TrainDTO> findByOrganizationId(int page, int offset, BigInteger organizationId);

    /**
     * 根据实训名称进行模糊查询
     * @param page 页号
     * @param offset 页容量
     * @param likeName 模糊名称
     * @return 实训分页信息
     */
    PageInfo<TrainDTO> findByLikeName(int page, int offset, String likeName);

    /**
     * 查询一个项目出现的所有实训
     * @param page 页号
     * @param offset 页容量
     * @param projectId 项目Id
     * @return 实训分页信息
     */
    PageInfo<TrainDTO> findByProjectId(int page, int offset, BigInteger projectId);

    /**
     * 给资源库上传文件
     * @param file 文件
     * @param trainId 实训Id
     */
    void uploadResourceLib(MultipartFile file, BigInteger trainId) throws Exception;

    /**
     * 删除资源库中文件
     * @param trainId 实训Id
     * @param fileDTO 文件信息
     */
    void removeResourceLib(BigInteger trainId, FileDTO fileDTO) throws Exception;

    /**
     * 向实训中添加项目
     * @param trainId 实训id
     * @param projectId 项目id
     */
    void addProject(BigInteger trainId, BigInteger projectId);

    /**
     * 从实训中移除项目
     * @param trainId 实训id
     * @param projecId 项目id
     */
    void removeProject(BigInteger trainId, BigInteger projecId) throws Exception;
}
