package io.github.octopigeon.cptmpservice.service.trainproject;

import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
@Service
public interface TrainProjectService extends BaseNormalService<TrainProjectDTO>, BaseFileService {

}
