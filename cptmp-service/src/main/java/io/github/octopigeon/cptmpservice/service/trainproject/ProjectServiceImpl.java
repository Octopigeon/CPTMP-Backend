package io.github.octopigeon.cptmpservice.service.trainproject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.ProjectMapper;
import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.model.Project;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.attachmentfile.AttachmentFileService;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileServiceImpl;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.jetbrains.annotations.NotNull;
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
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private AttachmentFileService attachmentFileService;

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
            Project project = new Project();
            BeanUtils.copyProperties(dto, project);
            project.setGmtCreate(new Date());
            JSONObject object = new JSONObject();
            List<FileDTO> fileDTOS = new ArrayList<>();
            object.put(this.libJsonName, fileDTOS);
            project.setResourceLibrary(object.toJSONString());
            projectMapper.addTrainProject(project);
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
            Project project = projectMapper.findTrainProjectById(dto.getId());
            if(project != null){
                projectMapper.hideTrainProjectById(project.getId(), new Date());
                projectTrainMapper.removeProjectTrainsByProjectId(dto.getId());
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
            Project project = projectMapper.findTrainProjectById(dto.getId());
            BeanUtils.copyProperties(dto, project, Utils.getNullPropertyNames(dto));
            project.setGmtModified(new Date());
            projectMapper.updateTrainProjectById(project);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 查询所有项目
     *
     * @param page   页号
     * @param offset 页容量
     * @return
     */
    @Override
    public PageInfo<ProjectDTO> findAll(int page, int offset) {
        List<Project> projects = projectMapper.findAllTrainProject();
        Page pages = PageHelper.startPage(page, offset);
        List<ProjectDTO> results = new ArrayList<>();
        for (Project project: projects) {
            ProjectDTO result = new ProjectDTO();
            BeanUtils.copyProperties(project, result);
            results.add(result);
        }
        return new PageInfo<>(pages);
    }

    /**
     * 根据名字进行模糊查找
     *
     * @param page   页号
     * @param offset 页内数量
     * @param name   项目名
     * @return
     */
    @Override
    public PageInfo<ProjectDTO> findByLikeName(int page, int offset, String name) {
        PageHelper.startPage(page, offset);
        List<Project> projects = projectMapper.findTrainProjectByNameAmbiguously(name);
        return getProjectDTOPageInfo(projects);
    }

    @NotNull
    private PageInfo<ProjectDTO> getProjectDTOPageInfo(List<Project> projects) {
        List<ProjectDTO> results = new ArrayList<>();
        for (Project project: projects) {
            ProjectDTO result = new ProjectDTO();
            BeanUtils.copyProperties(project, result);
            results.add(result);
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据项目id查实训
     *
     * @param page      页号
     * @param offset    页内数量
     * @param projectId 项目id
     * @return
     */
    @Override
    public PageInfo<TrainDTO> findTrainsById(int page, int offset, BigInteger projectId) {
        PageHelper.startPage(page, offset);
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByProjectId(projectId);
        List<TrainDTO> results = new ArrayList<>();
        for (ProjectTrain projectTrain: projectTrains) {
            Train train = trainMapper.findTrainById(projectTrain.getTrainId());
            TrainDTO result = new TrainDTO();
            BeanUtils.copyProperties(train, result);
            results.add(result);
        }
        return new PageInfo<>(results);
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
        Project project = projectMapper.findTrainProjectById(projectId);
        JSONObject object = JSON.parseObject(project.getResourceLibrary());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.libJsonName).toJSONString(), FileDTO.class);
        resourceLib.add(fileInfo);
        object.put(this.libJsonName, resourceLib);
        attachmentFileService.add(fileInfo);
        projectMapper.updateTrainProjectResourceById(projectId, new Date(), object.toJSONString());
    }

    /**
     * 删除资源库中文件
     *
     * @param projectId 项目Id
     * @param file      fileDTO
     */
    @Override
    public void removeResourceLib(BigInteger projectId, FileDTO file) throws Exception {
        Project project = projectMapper.findTrainProjectById(projectId);
        JSONObject object = JSON.parseObject(project.getResourceLibrary());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.libJsonName).toJSONString(), FileDTO.class);
        resourceLib.remove(file);
        object.put(this.libJsonName, resourceLib);
        projectMapper.updateTrainProjectResourceById(projectId, new Date(), object.toJSONString());
        attachmentFileService.remove(file);
        removeFile(file.getFilePath());
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public ProjectDTO findById(BigInteger id) throws Exception {
        Project project = projectMapper.findTrainProjectById(id);
        if(project == null){
            throw new ValueException("project is not existed!");
        }
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);
        return projectDTO;
    }
}
