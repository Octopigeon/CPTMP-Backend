package io.github.octopigeon.cptmpservice.service.trainproject;

import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/11
 */
@Service
public class TrainProjectServiceImpl extends BaseFileServiceImpl implements TrainProjectService {

    @Autowired
    private TrainProjectMapper trainProjectMapper;

    @Autowired
    public TrainProjectServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 添加数据
     * 添加实训信息
     * @param dto ：dto实体
     */
    @Override
    public void add(TrainProjectDTO dto) throws Exception {
        try {
            TrainProject project = new TrainProject();
            project.setGmtCreate(new Date());
            project.setProjectContent(dto.getContent());
            project.setProjectLevel(dto.getLevel());
            project.setProjectName(dto.getName());
            project.setTrainId(dto.getTrainId());
            trainProjectMapper.addTrainProject(project);
        }catch (Exception e){
            throw new Exception("Add train project failed");
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(TrainProjectDTO dto) throws Exception {

    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(TrainProjectDTO dto) throws Exception {
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public TrainProjectDTO findById(BigInteger id) throws Exception {

        return null;
    }
}
