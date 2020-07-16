package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.TrainService;
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
 * @date 2020/07/15
 * @last-check-in 陈若琳
 * @date 2020/07/15
 */

@RestController
public class TrainDetailsController {

    @Autowired
    TrainService trainService;

    /**
     * 创建实训
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/train")
    public RespBean createTrain(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        TrainDTO train = objectMapper.readValue(json, TrainDTO.class);
        try{
            trainService.add(train);
            return RespBean.ok("create train successfully");
        }catch(Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REGISTER_FAILED,"Train create failed");
        }
    }

    /**
     * 获取所有实训
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/train")
    public RespBeanWithTrainList getAllTrains(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        int offset = objectMapper.readValue(json, ObjectNode.class).get("offset").asInt();
        int page = objectMapper.readValue(json, ObjectNode.class).get("page").asInt();
        try{
            PageInfo<TrainDTO> pageInfo = trainService.findAll(page,offset);
            return new RespBeanWithTrainList(
                    pageInfo.getList(),
                    pageInfo.getPageSize(),
                    pageInfo.getPages()
            );

        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTrainList(CptmpStatusCode.INFO_ACCESS_FAILED,"something wrong");
        }
    }

    /**
     * 根据关键词获取实训
     * @param json
     * @param property 关键词对应属性
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/train/search/{property}")
    public RespBeanWithTrainList searchTrain(@RequestBody String json, @PathVariable("property") String property) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        int page = objectMapper.readValue(json, ObjectNode.class).get("page").asInt();
        int offset = objectMapper.readValue(json, ObjectNode.class).get("offset").asInt();

        try{
            switch (property)
            {
                case "organization_id":
                    BigInteger organizationId = BigInteger.valueOf(objectMapper.readValue(json, ObjectNode.class).get("key_word").asInt());
                    PageInfo<TrainDTO> searchById = trainService.findByOrganizationId(page,offset,organizationId);
                    return new RespBeanWithTrainList(
                            searchById.getList(),
                            searchById.getPageSize(),
                            searchById.getPages()
                    );
                case "name":
                    String trainName = objectMapper.readValue(json, ObjectNode.class).get("key_word").asText();
                    PageInfo<TrainDTO> searchByName = trainService.findByLikeName(page,offset,trainName);
                    return new RespBeanWithTrainList(
                            searchByName.getList(),
                            searchByName.getPageSize(),
                            searchByName.getPages()
                    );
                default:
                    return new RespBeanWithTrainList(CptmpStatusCode.INFO_ACCESS_FAILED,"wrong property");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTrainList(CptmpStatusCode.INFO_ACCESS_FAILED,"get train failed");
        }
    }

    /**
     * 通过id获取实训
     * @param trainId
     * @return
     */
    @GetMapping("api/train/{id}")
    public RespBeanWithTrainInfo getTrainById(@PathVariable("id") BigInteger trainId)
    {
        try{
            TrainDTO train = trainService.findById(trainId);
            return new RespBeanWithTrainInfo(train);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTrainInfo(CptmpStatusCode.INFO_ACCESS_FAILED,"get train failed");
        }
    }

    /**
     * 根据id删除实训
     * @param trainId
     * @return
     */
    @DeleteMapping("api/train/{id}")
    public RespBean deleteTrain(@PathVariable("id") BigInteger trainId)
    {
        try{
            trainService.remove(trainService.findById(trainId));
            return RespBean.ok("train remove successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBean(CptmpStatusCode.REMOVE_FAILED,"train remove failed");
        }

    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithTrainInfo extends RespBean
{
    public RespBeanWithTrainInfo(TrainDTO train)
    {
        super();
        this.train = train;
    }

    public RespBeanWithTrainInfo(Integer status, String msg)
    {
        super(status,msg);
    }

    @JsonProperty("data")
    private TrainDTO train;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithTrainList extends RespBean
{
    public RespBeanWithTrainList(List<TrainDTO> trains,int pageSize,int totalPages)
    {
        super();
        this.trains = trains;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }

    public RespBeanWithTrainList(Integer status, String msg)
    {
        super(status,msg);
    }

    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("data")
    private List<TrainDTO> trains;
}
