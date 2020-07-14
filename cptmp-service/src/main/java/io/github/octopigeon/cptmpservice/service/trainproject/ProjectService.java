package io.github.octopigeon.cptmpservice.service.trainproject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

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
     * @param page 页号
     * @param offset 页内数量
     * @param name 项目名
     * @return
     */
    PageInfo<ProjectDTO> findByLikeName(int page, int offset, String name);

    /**
     * 根据项目id查实训
     * @param page 页号
     * @param offset 页内数量
     * @param projectId 项目id
     * @return
     */
    PageInfo<TrainDTO> findTrainsById(int page, int offset, BigInteger projectId);

    /**
     * 给资源库上传文件
     * @param file 文件
     * @param projectId 项目Id
     */
    void uploadResourceLib(MultipartFile file, BigInteger projectId) throws Exception;
}
