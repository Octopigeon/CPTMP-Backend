package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.ProjectService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/15
 * 实训项目的改、删、查
 * @last-check-in 陈若琳
 * @date 2020/7/17
 */
@RestController
public class ProjectDetailsController {

    @Autowired
    private ProjectService projectService;

    /**
     * 得到项目基本信息，参加projectDTO中的内容
     *
     * @param id 项目id
     * @return 返回查询到的信息
     */
    @GetMapping("/api/train-project/{id}/basic-info")
    public RespBeanWithProjectDTO getTrainProjectInfo(
            @PathVariable(value = "id") BigInteger id) {
        try {
            ProjectDTO projectDTO = projectService.findById(id);
            RespBeanWithProjectDTO respBeanWithProjectDTO = new RespBeanWithProjectDTO();
            respBeanWithProjectDTO.setProjectDTO(projectDTO);
            return respBeanWithProjectDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithProjectDTO(CptmpStatusCode.INFO_ACCESS_FAILED, "find project info failed");
        }
    }

    /**
     * 跟新项目的名字，简介，等级信息
     *
     * @param json 包含上述三个json字段
     * @param id   项目的id
     * @return 返回更新结果
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PutMapping("/api/train-project/{id}/basic-info")
    public RespBean updateTrainProjectInfo(
            @RequestBody String json,
            @PathVariable(value = "id") BigInteger id
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(id);
        projectDTO.setName(objectMapper.readValue(json, ObjectNode.class).get("name").asText());
        projectDTO.setContent(objectMapper.readValue(json, ObjectNode.class).get("content").asText());
        projectDTO.setLevel(objectMapper.readValue(json, ObjectNode.class).get("level").asInt());
        try {
            projectService.modify(projectDTO);
            return RespBean.ok("update success");
        } catch (Exception e) {
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "update project basic info failed");
        }
    }

    /**
     * 处理实训项目有关文档的上传信息
     *
     * @param resource 上传的文件
     * @param id       项目id
     * @return 更新是否成功
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PostMapping("/api/train-project/{id}/resource-lib")
    public RespBean updateProjectResourceLib(
            @RequestParam("file") MultipartFile resource,
            @PathVariable(value = "id") BigInteger id) {
        try {
            projectService.uploadResourceLib(resource, id);
            return RespBean.ok("upload resource files success");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED, "upload resource files failed");
        }
    }

    /**
     * 删除项目有关文档
     *
     * @return 删除是否成功
     */
    @DeleteMapping("/api/train-project/{project_id}/resource-lib")
    public RespBean deleteProjectResource(
            @RequestParam(value = "fileName") String fileName,
            @PathVariable(value = "project_id") BigInteger projectId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName(fileName);
            projectService.removeResourceLib(projectId, fileDTO);
            return RespBean.ok("remove resource files success");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED, "remove resource files failed");
        }
    }

    /**
     * 批量删除实训项目
     *
     * @param json 包含需要删除的项目id
     * @return 删除失败列表
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @DeleteMapping("/api/train-project")
    public RespBeanWithFailedList removeTrainProjects(
            @RequestBody String json
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] deleteList = objectMapper.readValue(json, BigInteger[].class);
        List<Integer> deleteFailedList = new ArrayList<>();
        for (int i = 0; i < deleteList.length; i++) {
            try {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(deleteList[i]);
                projectService.remove(projectDTO);
            } catch (Exception e) {
                e.printStackTrace();
                deleteFailedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(deleteFailedList);
    }


    /**
     * 获取所有项目
     *
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/project")
    public RespBeanWithProjectList getAllProjects(
            @RequestParam("offset") int offset, @RequestParam("page") int page) {
        try {
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<ProjectDTO> pageInfo = projectService.findAll(page, offset);
            return new RespBeanWithProjectList(pageInfo.getList(), pages.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithProjectList(CptmpStatusCode.INFO_ACCESS_FAILED, "get project failed");
        }

    }

    /**
     * 根据名称获取项目
     *
     * @param page
     * @param offset
     * @param keyWord
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/project/search/name")
    public RespBeanWithProjectList searchProject(
            @RequestParam("page") int page,
            @RequestParam("offset") int offset,
            @RequestParam("key_word") String keyWord) {
        try {
            PageInfo<ProjectDTO> searchByName = projectService.findByLikeName(page, offset, keyWord);
            return new RespBeanWithProjectList(
                    searchByName.getList(),
                    searchByName.getTotal()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithProjectList(CptmpStatusCode.INFO_ACCESS_FAILED, "get project failed");
        }
    }

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithProjectDTO extends RespBean {

    public RespBeanWithProjectDTO() {
        super();
    }

    public RespBeanWithProjectDTO(Integer status, String msg) {
        super(status, msg);
    }

    @JsonProperty("data")
    private ProjectDTO projectDTO;

}



