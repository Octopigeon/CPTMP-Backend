package io.github.octopigeon.cptmpservice.service.trainproject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileServiceImpl;
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
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/11
 */
@Service
public class ProjectServiceImpl extends BaseFileServiceImpl implements ProjectService {

    private final String libJsonName = "resourceLib";

    @Autowired
    private TrainProjectMapper trainProjectMapper;

    @Autowired
    public ProjectServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 添加数据
     * 添加实训信息
     * @param dto ：dto实体
     */
    @Override
    public void add(ProjectDTO dto) throws Exception {
        try {
            TrainProject project = new TrainProject();
            BeanUtils.copyProperties(dto, project);
            project.setGmtCreate(new Date());
            JSONObject object = new JSONObject();
            List<FileDTO> fileDTOS = new ArrayList<>();
            object.put(this.libJsonName, fileDTOS);
            project.setResourceLibrary(object.toJSONString());
            trainProjectMapper.addTrainProject(project);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Add train project failed");
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(ProjectDTO dto) throws Exception {
        try{
            TrainProject trainProject = trainProjectMapper.findTrainProjectById(dto.getId());
            if(trainProject != null){
                trainProjectMapper.removeTrainProjectById(trainProject.getId(), new Date());
            }
            else {
                throw new ValueException("trainproject is not existed!");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(ProjectDTO dto) throws Exception {
        try {
            TrainProject trainProject = trainProjectMapper.findTrainProjectById(dto.getId());
            BeanUtils.copyProperties(dto, trainProject, Utils.getNullPropertyNames(dto));
            trainProjectMapper.updateTrainProjectById(trainProject.getId(), new Date(), trainProject.getName(), trainProject.getLevel(), trainProject.getContent());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 给资源库上传文件
     *
     * @param file      文件
     * @param projectId 项目Id
     */
    @Override
    public void uploadResourceLib(MultipartFile file, BigInteger projectId) throws Exception {
        FileDTO fileInfo = storePrivateFile(file);
        TrainProject trainProject = trainProjectMapper.findTrainProjectById(projectId);
        JSONObject object = JSON.parseObject(trainProject.getResourceLibrary());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.libJsonName).toJSONString(), FileDTO.class);
        resourceLib.add(fileInfo);
        object.put(this.libJsonName, resourceLib);
        trainProjectMapper.updateTrainProjectResourceById(projectId, new Date(), object.toJSONString());
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public ProjectDTO findById(BigInteger id) throws Exception {
        TrainProject trainProject = trainProjectMapper.findTrainProjectById(id);
        if(trainProject == null){
            throw new ValueException("project is not existed!");
        }
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(trainProject, projectDTO);
        return projectDTO;
    }

    /**
     * 根据名字进行模糊查找
     *
     * @param name 项目名
     * @return 列表
     */
    @Override
    public List<ProjectDTO> findByLikeName(String name) {
        List<TrainProject> trainProjects = trainProjectMapper.findTrainProjectByProjectNameAmbiguously(name);
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for (TrainProject trainProject: trainProjects) {
            ProjectDTO projectDTO = new ProjectDTO();
            BeanUtils.copyProperties(trainProject, projectDTO);
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }
}
