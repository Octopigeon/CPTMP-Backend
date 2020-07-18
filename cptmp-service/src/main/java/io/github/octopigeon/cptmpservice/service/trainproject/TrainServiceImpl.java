package io.github.octopigeon.cptmpservice.service.trainproject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.mapper.ProjectMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
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
    private AttachmentFileService attachmentFileService;

    @Autowired
    public TrainServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 分页查询所有实训
     *
     * @param page   页号
     * @param offset 一页的数量
     * @return
     */
    @Override
    public PageInfo<TrainDTO> findAll(int page, int offset) {
        PageHelper.startPage(page, offset);
        List<Train> trains = trainMapper.findAllTrain();
        return getTrainDTOPageInfo(trains);
    }

    /**
     * 根据组织id查询实训
     *
     * @param page           页号
     * @param offset         偏移量
     * @param organizationId 组织id
     * @return
     */
    @Override
    public PageInfo<TrainDTO> findByOrganizationId(int page, int offset, BigInteger organizationId) {
        PageHelper.startPage(page, offset);
        List<Train> trains = trainMapper.findTrainByOrganizationId(organizationId);
        return getTrainDTOPageInfo(trains);
    }

    /**
     * 根据实训名称进行模糊查询
     *
     * @param page
     * @param offset
     * @param likeName 模糊名称
     * @return
     */
    @Override
    public PageInfo<TrainDTO> findByLikeName(int page, int offset, String likeName) {
        PageHelper.startPage(page, offset);
        List<Train> trains = trainMapper.findTrainByNameAmbiguously(likeName);
        return getTrainDTOPageInfo(trains);
    }

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
     * 分页查询实训对应的项目
     * @param page 页号
     * @param offset 一页的数量
     * @param id 实训id
     * @return 项目id列表
     */
    @Override
    public PageInfo<BigInteger> findProjectIdsById(int page, int offset, BigInteger id) {
        PageHelper.startPage(page, offset);
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(id);
        List<BigInteger> results = new ArrayList<>();
        for (ProjectTrain projectTrain: projectTrains) {
            results.add(projectTrain.getProjectId());
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
        trainMapper.updateTrainProjectResourceById(trainId, new Date(), JSONObject.toJSON(object).toString());
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
        trainMapper.updateTrainProjectResourceById(trainId, new Date(), JSONObject.toJSON(object).toString());
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
            projectTrainMapper.removeProjectTrainsByProjectIdAndTrainId(projectId, trainId);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Remove project from train failed！");
        }
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
        if(trainMapper.findTrainById(dto.getId()) != null){
            trainMapper.hideTrainById(dto.getId(), new Date());
            projectTrainMapper.removeProjectTrainsByTrainId(dto.getId());
        }else{
            throw new ValueException("The train not exist！");
        }
    }

    /**
     * 更新的文件实体
     *
     * @param dto
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
        TrainDTO result = new TrainDTO();
        BeanUtils.copyProperties(train, result);
        return result;
    }
}
