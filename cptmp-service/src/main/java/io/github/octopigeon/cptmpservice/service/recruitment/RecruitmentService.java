package io.github.octopigeon.cptmpservice.service.recruitment;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.recruitment.RecruitmentDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/20
 */
@Service
public interface RecruitmentService extends BaseNormalService<RecruitmentDTO>, BaseFileService {

    /**
     * 查找所有招聘
     * @param page 页号
     * @param offset 页容量
     * @return 所有招聘信息
     */
    PageInfo<RecruitmentDTO> findAll(int page, int offset);

    /**
     * 上传图片
     * @param photo 图片文件
     * @param recruitmentId 招聘资料id
     */
    void uploadPhoto(MultipartFile photo, BigInteger recruitmentId) throws Exception;

}
