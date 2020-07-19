package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
import io.github.octopigeon.cptmpservice.service.record.RecordService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
public class RecordDetailsController {

    @Autowired
    RecordService recordService;

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

