package io.github.octopigeon.cptmpservice.service.recruitment;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.recruitment.RecruitmentDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in Gh Li
 * @date 2020/7/20
 */
public interface RecruitmentService extends BaseNormalService<RecruitmentDTO>, BaseFileService {

    /**
     * 查找所有招聘
     * @param page 页号
     * @param offset 页容量
     * @return
     */
    PageInfo<RecruitmentDTO> findAll(int page, int offset);

    /**
     * 上传图片
     * @param photo 图片文件
     * @param recruitmentId 招聘资料id
     */
    void uploadPhoto(MultipartFile photo, BigInteger recruitmentId);

    void removePhoto(BigInteger recruitmentId);
}
