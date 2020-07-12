// TODO 此controller与下层对接的相应接口尚未完成，等完成后再去掉注释
//package io.github.octopigeon.cptmpweb.controller;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectFindRespDTO;
//import io.github.octopigeon.cptmpservice.service.trainproject.find.TrainProjectEnterpriseAdminFindServiceImpl;
//import io.github.octopigeon.cptmpweb.bean.request.KeyWordBean;
//import io.github.octopigeon.cptmpweb.bean.response.RespBean;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author 魏啸冲
// * @version 1.0
// * @date 2020/7/10
// * 所有实训项目查询的接口
// * @last-check-in 魏啸冲
// * @date 2020/7/10
// */
//@RestController
//public class TrainProjectFindController {
//
//    @Autowired
//    private TrainProjectEnterpriseAdminFindServiceImpl trainProjectEnterpriseAdminFindService;
//
//    /**
//     * 企业管理员模糊查询项目，范围是所有项目
//     * @param keyWord 查询关键词
//     * @return 返回包含若干项目信息的json
//     */
//    @Secured("ROLE_ENTERPRISE_ADMIN")
//    @GetMapping("/api/enterprise-admin/train-project/find")
//    public RespBeanWithTrainProjectFindRespDTOs enterpriseAdminFindTrainProject(
//            @RequestBody KeyWordBean keyWord
//            ) {
//        return new RespBeanWithTrainProjectFindRespDTOs(
//                trainProjectEnterpriseAdminFindService
//                        .findTrainProjects(keyWord.getKeyWord())
//        );
//    }
//
//    /**
//     * 直接返回所有实训项目
//     * @return 所有项目
//     */
//    @Secured("ROLE_ENTERPRISE_ADMIN")
//    @GetMapping("/api/enterprise-admin/train-project/find-all")
//    public RespBeanWithTrainProjectFindRespDTOs enterpriseAdminListTrainProject() {
//        return new RespBeanWithTrainProjectFindRespDTOs(
//                trainProjectEnterpriseAdminFindService.findAllTrainProjects()
//        );
//    }
//
//}
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//class RespBeanWithTrainProjectFindRespDTOs extends RespBean {
//
//    public RespBeanWithTrainProjectFindRespDTOs(List<TrainProjectFindRespDTO> trainProjectFindRespDTOs) {
//        super();
//        this.trainProjectFindRespDTOs = trainProjectFindRespDTOs;
//    }
//
//    @JsonProperty("data")
//    private List<TrainProjectFindRespDTO> trainProjectFindRespDTOs;
//
//}
