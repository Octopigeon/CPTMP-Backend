package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.processevent.ProcessDTO;
import io.github.octopigeon.cptmpservice.service.processevent.ProcessService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/16
 * @last-check-in 陈若琳
 * @date 2020/07/16
 */

@RestController
public class ProcessDetailsController {

    @Autowired
    ProcessService processService;

    /**
     * 创建process
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/process")
    public RespBean addProcess(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ProcessDTO process = objectMapper.readValue(json, ProcessDTO.class);
        try{
            processService.add(process);
            return RespBean.ok("create process successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"create process failed");
        }
    }

    /**
     * 根据id删除process
     * @param processId
     * @return
     */
    @DeleteMapping("api/process/{process_id}")
    public RespBean deleteProcess(@PathVariable("process_id")BigInteger processId)
    {
        try{
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setId(processId);
            processService.remove(processDTO);
            return RespBean.ok("delete process successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"delete process failed");
        }
    }

    /**
     * 根据ID更新流程
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping("api/process")
    public RespBean updateProcess(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ProcessDTO process = objectMapper.readValue(json, ProcessDTO.class);
        try{
            processService.modify(process);
            return RespBean.ok("update process successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED,"update process failed");
        }
    }

    /**
     * 根据id获取process信息
     * @param processId
     * @return
     */
    @GetMapping("api/process/{process_id}")
    public RespBeanWithProcessDTO getProcessById(@PathVariable("process_id") BigInteger processId)
    {
        try{
            ProcessDTO processDTO = processService.findById(processId);
            return new RespBeanWithProcessDTO(processDTO);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithProcessDTO(CptmpStatusCode.INFO_ACCESS_FAILED,"get process failed");
        }
    }

    /**
     * 在process中添加event
     * @param processId
     * @param eventId
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping("api/process_event")
    public RespBean addEvent(@RequestParam("process_id")BigInteger processId,@RequestParam("event_id")BigInteger eventId) throws JsonProcessingException
    {
        try{
            processService.addEvent(processId,eventId);
            return RespBean.ok("add event successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"add event failed");
        }
    }

    /**
     * TODO:service没有对不存在的id进行过滤
     * 在process中移除event
     * @param processId
     * @return
     * @throws JsonProcessingException
     */
    @DeleteMapping("api/process_event")
    public RespBean removeEvent(@RequestParam("process_id")BigInteger processId,@RequestParam("event_id")BigInteger eventId)
    {
        try{
            processService.removeEvent(processId,eventId);
            return RespBean.ok("remove event successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"remove event failed");
        }
    }

    /**
     * TODO：service没有对不存在的id进行过滤
     * 根据实训id获取流程
     * @param trainId
     * @return
     */
    @GetMapping("api/train_process/{train_id}")
    public RespBeanWithProcessList getProcessByTrainId(@PathVariable("train_id") BigInteger trainId)
    {
        try{
            List<ProcessDTO> processDTOList = processService.findByTrainId(trainId);
            return new RespBeanWithProcessList(processDTOList);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithProcessList(CptmpStatusCode.INFO_ACCESS_FAILED,"get process failed");
        }
    }

    /**
     * 移除特定实训中的流程
     * @param trainId
     * @return
     */
    @DeleteMapping("api/train_process/{train_id}")
    public RespBean removeProcessByTrainId(@PathVariable("train_id") BigInteger trainId)
    {
        try{
            processService.removeByTrainId(trainId);
            return RespBean.ok("remove process successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"remove process failed");
        }
    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithProcessDTO extends RespBean
{
    public RespBeanWithProcessDTO(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithProcessDTO(ProcessDTO processDTO)
    {
        super();
        this.processDTO = processDTO;
    }

    @JsonProperty("data")
    private ProcessDTO processDTO;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithProcessList extends RespBean
{
    public RespBeanWithProcessList(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithProcessList(List<ProcessDTO> processDTOList)
    {
        super();
        this.processDTOList = processDTOList;
    }

    @JsonProperty("data")
    private List<ProcessDTO> processDTOList;
}