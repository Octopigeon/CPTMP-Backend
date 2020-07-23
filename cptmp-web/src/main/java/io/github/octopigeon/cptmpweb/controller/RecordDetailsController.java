package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
import io.github.octopigeon.cptmpservice.service.record.RecordService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.soap.Addressing;
import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/17
 * @last-check-in 陈若琳
 * @date 2020/07/17
 */
@RestController
public class RecordDetailsController {

    @Autowired
    private RecordService recordService;

    /**
     * 创建记录
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/record")
    public RespBean createRecord(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        RecordDTO recordDTO = objectMapper.readValue(json, RecordDTO.class);
        try{
            recordService.add(recordDTO);
            return RespBean.ok("create record successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"create record failed");
        }
    }

    /**
     * 根据id获取记录
     * @param recordId
     * @return
     */
    @GetMapping("api/record/{record_id}")
    public RespBeanWithRecord getRecordById(@PathVariable("record_id")BigInteger recordId)
    {
        try{
            return new RespBeanWithRecord(recordService.findById(recordId));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithRecord(CptmpStatusCode.INFO_ACCESS_FAILED,"get record failed");
        }
    }

    /**
     * 根据实训id和用户id获取记录
     * @param trainId
     * @param userId
     * @return
     */
    @GetMapping("api/record/user")
    public RespBeanWithRecordList getRecordByTrainIdAndUserId(
            @RequestParam("train_id")BigInteger trainId,
            @RequestParam("user_id")BigInteger userId)
    {
        try{
            return new RespBeanWithRecordList(recordService.findByTrainIdAndUserId(trainId, userId));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithRecordList(CptmpStatusCode.INFO_ACCESS_FAILED,"get records failed");
        }
    }

    /**
     * 根据团队id获取记录
     * @param teamId
     * @return
     */
    @GetMapping("api/record/team/{team_id}")
    public RespBeanWithRecordList getRecordByTrainIdAndUserId(@PathVariable("team_id")BigInteger teamId)
    {
        try{
            return new RespBeanWithRecordList(recordService.findByTeamId(teamId));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithRecordList(CptmpStatusCode.INFO_ACCESS_FAILED,"get records failed");
        }
    }

    /**
     * 处理上传的文件
     * @param resource
     * @param recordId
     * @return
     */
    @PostMapping("api/record/{record_id}/file")
    public RespBean updateRecordResource(
            @RequestParam("file") MultipartFile resource,
            @PathVariable(value = "record_id") BigInteger recordId) {
        try {
            recordService.uploadAssignment(resource,recordId);
            return RespBean.ok("upload resource files successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED, "upload resource files failed");
        }
    }

    /**
     * 删除文档
     * @param json
     * @return 删除是否成功
     */
    @DeleteMapping("api/record/{record_id}/file")
    public RespBean deleteRecordResource(
            @RequestBody String json,
            @PathVariable(value = "record_id") BigInteger recordId)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FileDTO fileDTO = objectMapper.readValue(json,FileDTO.class);
            recordService.removeAssignment(recordId,fileDTO);
            return RespBean.ok("remove resource files success");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED, "remove resource files failed");
        }
    }



}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithRecord extends RespBean
{
    public RespBeanWithRecord(Integer status, String msg)
    {
        super(status,msg);
    }
    public RespBeanWithRecord(RecordDTO recordDTO)
    {
        super();
        this.recordDTO = recordDTO;
    }

    @JsonProperty("data")
    private RecordDTO recordDTO;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithRecordList extends RespBean
{
    public RespBeanWithRecordList(Integer status, String msg)
    {
        super(status,msg);
    }
    public RespBeanWithRecordList(List<RecordDTO> recordDTOList)
    {
        super();
        this.recordDTOList = recordDTOList;
    }

    @JsonProperty("data")
    private List<RecordDTO> recordDTOList;
}

