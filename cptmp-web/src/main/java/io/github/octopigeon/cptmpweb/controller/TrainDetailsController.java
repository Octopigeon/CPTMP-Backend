package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.model.Project;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.ProjectService;
import io.github.octopigeon.cptmpservice.service.trainproject.TrainService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
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
    @Autowired
    ProjectService projectService;

    /**
     * 创建实训
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
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
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"Train create failed");
        }
    }

    /**
     * 获取所有实训
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @GetMapping("api/train")
    public RespBeanWithTrainList getAllTrains(
            @RequestParam(value = "offset") Integer offset,
            @RequestParam(value = "page") Integer page
    )
    {
        try{
            PageInfo<TrainDTO> pageInfo = trainService.findAll(page,offset);
            return new RespBeanWithTrainList(
                    pageInfo.getList(),
                    pageInfo.getTotal()
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
                            searchById.getTotal()
                    );
                case "name":
                    String trainName = objectMapper.readValue(json, ObjectNode.class).get("key_word").asText();
                    PageInfo<TrainDTO> searchByName = trainService.findByLikeName(page,offset,trainName);
                    return new RespBeanWithTrainList(
                            searchByName.getList(),
                            searchByName.getTotal()
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
     * 通过id获取实训，学生和老师只能获取自己学校的实训信息
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
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @DeleteMapping("api/train/{train_id}")
    public RespBean deleteTrain(@PathVariable("train_id") BigInteger trainId)
    {
        try{
            trainService.remove(trainService.findById(trainId));
            return RespBean.ok("train remove successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBean(CptmpStatusCode.REMOVE_FAILED,"train remove failed");
        }

    }

    /**
     * 修改实训信息
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping("api/train")
    public RespBean updateTrainInfo(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        TrainDTO train = objectMapper.readValue(json, TrainDTO.class);
        try{
            trainService.modify(train);
            return RespBean.ok("update train successfully");
        }catch(Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"Train update failed");
        }

    }

    /**
     * 在实训中批量添加项目
     * @param json
     * @param trainId
     * @return
     * @throws JsonProcessingException
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PutMapping("api/train/{train_id}/project")
    public RespBeanWithFailedList addProject(@RequestBody String json,@PathVariable("train_id") BigInteger trainId) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] projectId = objectMapper.readValue(json,BigInteger[].class);
        List<Integer> failedList = new ArrayList<>();
        for(int i=0;i<projectId.length;i++)
        {
            try{
                trainService.addProject(trainId,projectId[i]);
            }catch (Exception e)
            {
                e.printStackTrace();;
                failedList.add(i+1);
            }
        }
        return RespBeanWithFailedList.report(failedList);
    }

    /**
     * 根据id删除实训中的项目
     * @param json
     * @param trainId
     * @return
     * @throws JsonProcessingException
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @DeleteMapping("api/train/{train_id}/project")
    public RespBeanWithFailedList deleteProject(@RequestBody String json,@PathVariable("train_id") BigInteger trainId) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] projectId = objectMapper.readValue(json,BigInteger[].class);
        List<Integer> failedList = new ArrayList<>();
        for(int i=0;i<projectId.length;i++)
        {
            try{
                trainService.removeProject(trainId,projectId[i]);
            }catch (Exception e)
            {
                e.printStackTrace();;
                failedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(failedList);
    }

    /**
     * 获取实训的所有项目
     * @param json
     * @param trainId
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/train/{train_id}/project")
    public RespBeanWithProjectList getProject(@RequestBody String json,@PathVariable("train_id") BigInteger trainId) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        int page = objectMapper.readValue(json, ObjectNode.class).get("page").asInt();
        int offset = objectMapper.readValue(json, ObjectNode.class).get("offset").asInt();
        try{
            PageInfo<BigInteger> pageInfo = trainService.findProjectIdsById(page,offset,trainId);
            List<BigInteger> projectIds = pageInfo.getList();
            List<ProjectDTO> projectList = new ArrayList<>();
            for (BigInteger projectId:projectIds)
            {
                projectList.add(projectService.findById(projectId));
            }
            return new RespBeanWithProjectList(projectList,pageInfo.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithProjectList(CptmpStatusCode.INFO_ACCESS_FAILED,"get project failed");
        }
    }

    /**
     * 处理实训有关文档的上传信息
     * @param resource 上传的文件
     * @param trainId
     * @return 更新是否成功
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PostMapping("/api/train/{train_id}/resource-lib")
    public RespBean updateTrainResourceLib(
            @RequestParam("file") MultipartFile resource,
            @PathVariable(value = "train_id") BigInteger trainId) {
        try {
            trainService.uploadResourceLib(resource, trainId);
            return RespBean.ok("upload resource files success");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED, "upload resource files failed");
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
    public RespBeanWithTrainList(List<TrainDTO> trains,long totalRows)
    {
        super();
        this.trains = trains;
        this.totalRows = totalRows;
    }

    public RespBeanWithTrainList(Integer status, String msg)
    {
        super(status,msg);
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<TrainDTO> trains;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithProjectList extends RespBean
{
    public RespBeanWithProjectList(List<ProjectDTO> projects,long totalRows)
    {
        super();
        this.projects = projects;
        this.totalRows = totalRows;
    }

    public RespBeanWithProjectList(Integer status, String msg)
    {
        super(status,msg);
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<ProjectDTO> projects;
}
