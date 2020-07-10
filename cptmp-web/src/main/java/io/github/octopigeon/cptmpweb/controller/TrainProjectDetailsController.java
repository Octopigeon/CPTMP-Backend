package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDuringTimeDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.details.TrainProjectDuringTimeDetailsServiceImpl;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import io.github.octopigeon.cptmpweb.bean.response.RespBeanWithObj;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

    // TODO 修改LGP数据库，然后单元测试

    /**
     * 查看实训项目起止时间
     * @param trainProjectId 实训项目id
     * @return 返回起止时间
     * @throws NoHandlerFoundException 若找不到则抛出404异常并交给自定义异常处理器处理
     */
    @GetMapping("/api/train-project/{id}/details/during-time")
    public RespBeanWithTrainProjectDuringTimeDTO findTrainProjectDuringTime(@PathVariable(value = "id") BigInteger trainProjectId) throws NoHandlerFoundException {
        try {
            return new RespBeanWithTrainProjectDuringTimeDTO(
                    CptmpStatusCode.OK,
                    "success",
                    trainProjectDuringTimeDetailsService.findTrainProject(trainProjectId)
            );
        } catch (IllegalArgumentException e) {
            throw new NoHandlerFoundException(HttpMethod.GET.name(), "/api/train-project/{id}/details/during-time", HttpHeaders.EMPTY);
        }
    }

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithTrainProjectDuringTimeDTO extends RespBean {

    public RespBeanWithTrainProjectDuringTimeDTO(Integer status, String msg, TrainProjectDuringTimeDTO trainProjectDuringTimeDTO) {
        super(status, msg);
        this.trainProjectDuringTimeDTO = trainProjectDuringTimeDTO;
    }

    private TrainProjectDuringTimeDTO trainProjectDuringTimeDTO;

}
