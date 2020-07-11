package io.github.octopigeon.cptmpservice.service.trainproject.find;

import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDuringTimeDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectFindRespDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
@Service
public class TrainProjectEnterpriseAdminFindServiceImpl implements TrainProjectFindService {

    @Autowired
    private TrainProjectMapper trainProjectMapper;

    @Autowired
    private TrainProjectEnterpriseAdminFindServiceImpl trainProjectEnterpriseAdminFindService;

    @Override
    public List<TrainProjectFindRespDTO> findTrainProjects(String keywords) {
        List<TrainProject> trainProjects = trainProjectMapper.findTrainProjectByProjectNameAmbiguously(keywords);
        return translateIntoDTOs(trainProjects);
    }

    public List<TrainProjectFindRespDTO> findAllTrainProjects() {
        List<TrainProject> trainProjects = trainProjectMapper.findAllTrainProject();
        return translateIntoDTOs(trainProjects);
    }

    private List<TrainProjectFindRespDTO> translateIntoDTOs(List<TrainProject> trainProjects) {
        List<TrainProjectFindRespDTO> trainProjectFindRespDTOs = new ArrayList<>();
        for (TrainProject trainProject : trainProjects) {
            TrainProjectFindRespDTO trainProjectFindRespDTO = new TrainProjectFindRespDTO();
            BeanUtils.copyProperties(trainProject, trainProjectFindRespDTO);
            trainProjectFindRespDTOs.add(trainProjectFindRespDTO);
        }
        return trainProjectFindRespDTOs;
    }

}
