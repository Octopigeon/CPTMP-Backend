package io.github.octopigeon.cptmpservice.service.trainproject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
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
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Service
public class TrainServiceImpl extends BaseFileServiceImpl implements TrainService{

    private final String libJsonName = "resourceLib";

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private PersonalGradeMapper personalGradeMapper;

    @Autowired
    private AttachmentFileService attachmentFileService;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    public TrainServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 分页查询所有实训
     *
     * @param page   页号
     * @param offset 一页的数量
     * @return 实训分页信息
     */
    @Override
    public PageInfo<TrainDTO> findAll(int page, int offset) {
        List<Train> trains = trainMapper.findAllTrain();
        return getTrainDTOPageInfo(trains);
    }

    /**
     * 根据组织id查询实训
     *
     * @param page           页号
     * @param offset         偏移量
     * @param organizationId 组织id
     * @return 实训分页信息
     */
    @Override
    public PageInfo<TrainDTO> findByOrganizationId(int page, int offset, BigInteger organizationId) {
        List<Train> trains = trainMapper.findTrainByOrganizationId(organizationId);
        return getTrainDTOPageInfo(trains);
    }

    /**
     * 根据实训名称进行模糊查询
     *
     * @param page 页号
     * @param offset 页容量
     * @param likeName 模糊名称
     * @return 实训分页信息
     */
    @Override
    public PageInfo<TrainDTO> findByLikeName(int page, int offset, String likeName) {
        List<Train> trains = trainMapper.findTrainByNameAmbiguously(likeName);
        return getTrainDTOPageInfo(trains);
    }

    /**
     * 查询一个项目出现的所有实训
     *
     * @param page      页号
     * @param offset    页容量
     * @param projectId 项目Id
     * @return 实训分页信息
     */
    @Override
    public PageInfo<TrainDTO> findByProjectId(int page, int offset, BigInteger projectId) {
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByProjectId(projectId);
        List<Train> trains = new ArrayList<>();
        for (ProjectTrain projectTrain: projectTrains) {
            trains.add(trainMapper.findTrainById(projectTrain.getTrainId()));
        }
        return getTrainDTOPageInfo(trains);
    }

    /**
     * 将实训实体列表转化成实训分页信息
     * @param trains 实训实体列表
     * @return 实训dto分页信息
     */
    @NotNull
    private PageInfo<TrainDTO> getTrainDTOPageInfo(List<Train> trains) {
        List<TrainDTO> results = new ArrayList<>();
        for (Train train: trains) {
            TrainDTO result = new TrainDTO();
            BeanUtils.copyProperties(train, result);
            results.add(result);
        }
        return new PageInfo<>(results);
    }
    

    /**
     * 给资源库上传文件
     *
     * @param file    文件
     * @param trainId 实训Id
     */
    @Override
    public void uploadResourceLib(MultipartFile file, BigInteger trainId) throws Exception {
        FileDTO fileInfo = storePrivateFile(file);
        Train train = trainMapper.findTrainById(trainId);
        JSONObject object = JSON.parseObject(train.getResourceLibrary());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.libJsonName).toJSONString(), FileDTO.class);
        resourceLib.add(fileInfo);
        object.put(this.libJsonName, resourceLib);
        trainMapper.updateTrainProjectResourceById(trainId, new Date(), object.toJSONString());
        attachmentFileService.add(fileInfo);
    }

    /**
     * 删除资源库中文件
     *
     * @param fileDTO 文件信息
     */
    @Override
    public void removeResourceLib(BigInteger trainId, FileDTO fileDTO) throws Exception {
        Train train = trainMapper.findTrainById(trainId);
        JSONObject object = JSON.parseObject(train.getResourceLibrary());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.libJsonName).toJSONString(), FileDTO.class);
        resourceLib.remove(fileDTO);
        object.put(this.libJsonName, resourceLib);
        trainMapper.updateTrainProjectResourceById(trainId, new Date(), object.toJSONString());
        attachmentFileService.remove(fileDTO);
        removeFile(fileDTO.getFilePath());
    }

    /**
     * 向实训中添加项目
     *
     * @param trainId   实训id
     * @param projectId 项目id
     */
    @Override
    public void addProject(BigInteger trainId, BigInteger projectId) {
        if(trainMapper.findTrainById(trainId) == null){
            throw new ValueException("Train is not existed!");
        }
        if(projectMapper.findTrainProjectById(projectId) == null){
            throw new ValueException("Project is not existed!");
        }
        if(projectTrainMapper.findProjectTrainByProjectIdAndTrainId(projectId, trainId) == null){
            ProjectTrain trainProject = new ProjectTrain();
            trainProject.setGmtCreate(new Date());
            trainProject.setTrainId(trainId);
            trainProject.setProjectId(projectId);
            projectTrainMapper.addProjectTrain(trainProject);
        }
    }

    /**
     * 从实训中移除项目
     *
     * @param trainId  实训id
     * @param projectId 项目id
     */
    @Override
    public void removeProject(BigInteger trainId, BigInteger projectId) throws Exception {
        try{
            if(trainMapper.findTrainById(trainId) == null){
                throw new ValueException("Train is not existed!");
            }
            if(projectMapper.findTrainProjectById(projectId) == null){
                throw new ValueException("Project is not existed!");
            }
            ProjectTrain projectTrain = projectTrainMapper.findProjectTrainByProjectIdAndTrainId(projectId, trainId);
            removeTeams(projectTrain);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Remove project from train failed！");
        }
    }

    /**
     * 移除实训项目下的队伍
     * @param projectTrain 实训项目关联
     */
    private void removeTeams(ProjectTrain projectTrain) {
        List<Team> teams = teamMapper.findTeamsByProjectTrainId(projectTrain.getId());
        for (Team team: teams) {
            List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(team.getId());
            for (TeamPerson teamPerson: teamPeople) {
                personalGradeMapper.hidePersonalGradeByTeamPersonId(teamPerson.getId(), new Date());
                teamPersonMapper.removeTeamPersonById(teamPerson.getId());
            }
            teamMapper.hideTeamById(team.getId(), new Date());
        }
        projectTrainMapper.removeProjectTrainsById(projectTrain.getId());
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(TrainDTO dto) throws Exception {
        try {
            Train train = new Train();
            BeanUtils.copyProperties(dto, train);
            train.setGmtCreate(new Date());
            JSONObject object = new JSONObject();
            List<FileDTO> fileDTOS = new ArrayList<>();
            object.put(this.libJsonName, fileDTOS);
            train.setResourceLibrary(object.toJSONString());
            if(dto.getGpsInfo() == null){
                JSONObject gps = new JSONObject();
                gps.put("latitude", 30.538869);
                gps.put("longitude", 114.357762);
                train.setGpsInfo(gps.toJSONString());
            }
            trainMapper.addTrain(train);
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
    public void remove(TrainDTO dto) throws Exception {
        Train train = trainMapper.findTrainById(dto.getId());
        if(train != null){
            List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(train.getId());
            List<Process> processes = processMapper.findProcessesByTrainId(dto.getId());
            for (ProjectTrain projectTrain: projectTrains) {
                removeTeams(projectTrain);
            }
            for (Process process: processes) {
                List<ProcessEvent> processEvents = processEventMapper.findProcessEventsByProcessId(process.getId());
                for (ProcessEvent processEvent: processEvents) {
                    if(eventMapper.findEventById(processEvent.getEventId()) != null){
                        eventMapper.hideEventById(processEvent.getEventId(), new Date());
                    }
                }
                processEventMapper.removeProcessEventsByProcessId(process.getId());
            }
            processMapper.hideProcessesByTrainId(new Date(), dto.getId());
            trainMapper.hideTrainById(train.getId(), new Date());
        }else{
            throw new Exception("The train not exist！");
        }
    }

    /**
     * 修改信息
     * @param dto 实体
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(TrainDTO dto) throws Exception {
        try{
            Train train = trainMapper.findTrainById(dto.getId());
            BeanUtils.copyProperties(dto, train, Utils.getNullPropertyNames(dto));
            train.setGmtModified(new Date());
            trainMapper.updateTrainById(train);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Modify train failed!");
        }
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public TrainDTO findById(BigInteger id) throws Exception {
        Train train = trainMapper.findTrainById(id);
        if(train == null){
            throw new Exception("Train is not exist!");
        }
        TrainDTO result = new TrainDTO();
        BeanUtils.copyProperties(train, result);
        return result;
    }
}
