package io.github.octopigeon.cptmpservice.service.trainprojectdetails;

import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDuringTimeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
@Service
public class TrainProjectDuringTimeDetailsServiceImpl implements TrainProjectDetailsService<TrainProjectDuringTimeDTO> {

    @Autowired
    private TrainProjectMapper trainProjectMapper;

    @Override
    public TrainProjectDuringTimeDTO findTrainProject(BigInteger trainProjectId) {
        TrainProjectDuringTimeDTO trainProjectDuringTimeDTO = new TrainProjectDuringTimeDTO();
        TrainProject trainProject = trainProjectMapper.findTrainProjectById(trainProjectId);
        BeanUtils.copyProperties(trainProject, trainProjectDuringTimeDTO);
        return trainProjectDuringTimeDTO;
    }
}
