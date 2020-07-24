package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.model.Recruitment;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.recruitment.RecruitmentDTO;
import io.github.octopigeon.cptmpservice.service.recruitment.RecruitmentService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 3.0
 * @date 2020/07/22
 * @last-check-in 陈若琳
 * @date 2020/07/22
 */

@RestController
public class RecruitmentDetailsController {

    @Autowired
    private RecruitmentService recruitmentService;

    /**
     * 新增招聘信息
     * @param json
     * @return
     */
    @PostMapping("api/recruitment")
    public RespBean addRecruitment(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        RecruitmentDTO recruitmentDTO = objectMapper.readValue(json,RecruitmentDTO.class);
        try{
            recruitmentService.add(recruitmentDTO);
            return RespBean.ok("add Recruitment successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"add Recruitment failed");
        }
    }

    /**
     * 移除招聘信息
     * @param recruitmentId
     * @return
     */
    @DeleteMapping("api/recruitment/{recruitment_id}")
    public RespBean deleteRecruitment(@PathVariable("recruitment_id")BigInteger recruitmentId)
    {
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        recruitmentDTO.setId(recruitmentId);
        try{
            recruitmentService.remove(recruitmentDTO);
            return RespBean.ok("delete recruitment successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"delete recruitment failed");
        }
    }

    /**
     * 更新招聘信息
     * @param json
     * @return
     */
    @PutMapping("api/recruitment")
    public RespBean updateRecruitment(@RequestBody String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        try{
            recruitmentService.modify(recruitmentDTO);
            return RespBean.ok("update recruitment successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED,"update recruitment failed");
        }
    }

    /**
     * 根据id获取招聘信息
     * @param recruitmentId
     * @return
     */
    @GetMapping("api/recruitment/{recruitment_id}")
    public RespBeanWithRecruitment getRecruitmentById(@PathVariable("recruitment_id")BigInteger recruitmentId)
    {
        try{
            RecruitmentDTO recruitmentDTO = recruitmentService.findById(recruitmentId);
            return new RespBeanWithRecruitment(recruitmentDTO);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithRecruitment(CptmpStatusCode.INFO_ACCESS_FAILED,"get info failed");
        }
    }

    /**
     * 获取所有的招聘信息
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/recruitment")
    public RespBeanWithRecruitmentList getAll(@RequestParam("offset")int offset,@RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<RecruitmentDTO> pageInfo = recruitmentService.findAll(page,offset);
            return new RespBeanWithRecruitmentList(pageInfo.getList(),pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithRecruitmentList(CptmpStatusCode.INFO_ACCESS_FAILED,"get Info failed");
        }
    }

    /**
     * 上传图片资源
     * @param resource
     * @param recruitmentId
     * @return
     */
    @PostMapping("api/recruitment/{recruitment_id}/photo")
    public RespBean uploadPhoto(
            @RequestParam("file") MultipartFile resource,
            @PathVariable("recruitment_id")BigInteger recruitmentId)
    {
        try{
            recruitmentService.uploadPhoto(resource,recruitmentId);
            return RespBean.ok("upload photo successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED,"file upload failed");
        }
    }

}

class RespBeanWithRecruitment extends RespBean
{
    public RespBeanWithRecruitment(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithRecruitment(RecruitmentDTO recruitmentDTO)
    {
        super();
        this.recruitmentDTO = recruitmentDTO;
    }

    @JsonProperty("data")
    private RecruitmentDTO recruitmentDTO;
}

class RespBeanWithRecruitmentList extends RespBean
{
    public RespBeanWithRecruitmentList(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithRecruitmentList(List<RecruitmentDTO> recruitmentDTOList,long totalRows)
    {
        super();
        this.totalRows = totalRows;
        this.recruitmentDTOList = recruitmentDTOList;
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<RecruitmentDTO> recruitmentDTOList;
}