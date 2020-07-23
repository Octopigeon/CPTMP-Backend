package io.github.octopigeon.cptmpservice.service.recruitment;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.RecruitmentMapper;
import io.github.octopigeon.cptmpdao.model.Recruitment;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.recruitment.RecruitmentDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileServiceImpl;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/20
 */
@Service
public class RecruitmentServiceImpl extends BaseFileServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentMapper recruitmentMapper;

    @Autowired
    public RecruitmentServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 查找所有招聘
     *
     * @param page   页号
     * @param offset 页容量
     * @return 所有招聘信息
     */
    @Override
    public PageInfo<RecruitmentDTO> findAll(int page, int offset) {
        List<Recruitment> recruitments = recruitmentMapper.findAllRecruitment();
        List<RecruitmentDTO> results = new ArrayList<>();
        for (Recruitment recruitment: recruitments) {
            RecruitmentDTO result = new RecruitmentDTO();
            BeanUtils.copyProperties(recruitment, result);
            results.add(result);
        }
        return new PageInfo<>(results);
    }

    /**
     * 上传图片
     *
     * @param photo         图片文件
     * @param recruitmentId 招聘资料id
     */
    @Override
    public void uploadPhoto(MultipartFile photo, BigInteger recruitmentId) throws Exception {
        FileDTO image = storePublicFile(photo);
        Recruitment recruitment = recruitmentMapper.findRecruitmentById(recruitmentId);
        recruitment.setPhoto(image.getFileUrl());
        recruitment.setGmtModified(new Date());
        recruitmentMapper.updateRecruitmentById(recruitment);
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(RecruitmentDTO dto) throws Exception {
        Recruitment recruitment = new Recruitment();
        BeanUtils.copyProperties(dto, recruitment);
        recruitment.setGmtCreate(new Date());
        recruitmentMapper.addRecruitment(recruitment);
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(RecruitmentDTO dto) throws Exception {
        if(recruitmentMapper.findRecruitmentById(dto.getId()) != null){
            recruitmentMapper.hideRecruitmentById(dto.getId(), new Date());
        }
    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(RecruitmentDTO dto) throws Exception {
        try {
            Recruitment recruitment = recruitmentMapper.findRecruitmentById(dto.getId());
            if(recruitment == null){
                throw new ValueException("Recruitment is not exist!");
            }
            BeanUtils.copyProperties(dto, recruitment, Utils.getNullPropertyNames(dto));
            recruitment.setGmtModified(new Date());
            recruitmentMapper.updateRecruitmentById(recruitment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public RecruitmentDTO findById(BigInteger id) throws Exception {
        Recruitment recruitment = recruitmentMapper.findRecruitmentById(id);
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        BeanUtils.copyProperties(recruitment, recruitmentDTO);
        return recruitmentDTO;
    }
}
