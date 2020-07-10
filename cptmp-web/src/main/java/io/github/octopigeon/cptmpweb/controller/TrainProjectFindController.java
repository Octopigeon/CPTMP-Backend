package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectFindRespDTO;
import io.github.octopigeon.cptmpservice.service.trainproject.find.TrainProjectEnterpriseAdminFindServiceImpl;
import io.github.octopigeon.cptmpweb.bean.request.KeyWordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestController
public class TrainProjectFindController {

    @Autowired
    private TrainProjectEnterpriseAdminFindServiceImpl trainProjectEnterpriseAdminFindService;

    @Secured("ROLE_ENTERPRISE_ADMIN")
    @GetMapping("/api/enterprise-admin/find/train-project")
    public List<TrainProjectFindRespDTO> enterpriseAdminFindTrainProject(
            @RequestBody KeyWordBean keyWord
            ) {
        return trainProjectEnterpriseAdminFindService.findTrainProjects(keyWord.getKeyWord());
    }

}
