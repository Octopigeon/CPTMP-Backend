package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDuringTimeDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.details.TrainProjectDuringTimeDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 用于获取实训项目各种详情
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestController
public class TrainProjectDetailsController {

    @Autowired
    private TrainProjectDuringTimeDetailsServiceImpl trainProjectDuringTimeDetailsService;

    @GetMapping("/api/train-project/{id}/details/during-time")
    public TrainProjectDuringTimeDTO findTrainProjectDuringTime(@PathVariable(value = "id") BigInteger trainProjectId) throws NoHandlerFoundException {
        TrainProjectDuringTimeDTO trainProjectDuringTimeDTO = new TrainProjectDuringTimeDTO();
        try {
            trainProjectDuringTimeDTO = trainProjectDuringTimeDetailsService.findTrainProject(trainProjectId);
        } catch (IllegalArgumentException e) {
            throw new NoHandlerFoundException(HttpMethod.GET.name(), "/api/train-project/{id}/details/during-time", HttpHeaders.EMPTY);
        }
        return trainProjectDuringTimeDTO;
    }

}
