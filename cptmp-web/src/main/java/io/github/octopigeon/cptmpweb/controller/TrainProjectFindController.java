package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectFindRespDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.find.TrainProjectEnterpriseAdminFindServiceImpl;
import io.github.octopigeon.cptmpweb.bean.request.KeyWordBean;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 所有实训项目查询的接口
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestController
public class TrainProjectFindController {

    @Autowired
    private TrainProjectEnterpriseAdminFindServiceImpl trainProjectEnterpriseAdminFindService;

    @Secured("ROLE_ENTERPRISE_ADMIN")
    @GetMapping("/api/enterprise-admin/find/train-project")
    public RespBeanWithTrainProjectFindRespDTOList enterpriseAdminFindTrainProject(
            @RequestBody KeyWordBean keyWord
            ) {
        return new RespBeanWithTrainProjectFindRespDTOList(
                CptmpStatusCode.OK,
                "success",
                trainProjectEnterpriseAdminFindService
                        .findTrainProjects(keyWord.getKeyWord())
        );
    }

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithTrainProjectFindRespDTOList extends RespBean {

    public RespBeanWithTrainProjectFindRespDTOList(Integer status, String msg, List<TrainProjectFindRespDTO> trainProjectFindRespDTOList) {
        super(status, msg);
        this.trainProjectFindRespDTOList = trainProjectFindRespDTOList;
    }

    @JsonProperty("results")
    private List<TrainProjectFindRespDTO> trainProjectFindRespDTOList;

}
