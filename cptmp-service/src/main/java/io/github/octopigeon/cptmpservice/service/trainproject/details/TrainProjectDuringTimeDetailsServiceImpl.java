package io.github.octopigeon.cptmpservice.service.trainproject.details;

import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDuringTimeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 查询实训项目起止时间
 * @last-check-in anlow
 * @date 2020/7/10
 */
@Service
public class TrainProjectDuringTimeDetailsServiceImpl implements TrainProjectDetailsService<TrainProjectDuringTimeDTO> {

    @Autowired
    private TrainProjectMapper trainProjectMapper;

    @Override
    public TrainProjectDuringTimeDTO findTrainProject(BigInteger trainProjectId) throws IllegalArgumentException {
        TrainProjectDuringTimeDTO trainProjectDuringTimeDTO = new TrainProjectDuringTimeDTO();
        TrainProject trainProject = trainProjectMapper.findTrainProjectById(trainProjectId);
        // 为空则抛出异常
        BeanUtils.copyProperties(trainProject, trainProjectDuringTimeDTO);
        return trainProjectDuringTimeDTO;
    }
}
