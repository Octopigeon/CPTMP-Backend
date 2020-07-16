package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import io.github.octopigeon.cptmpservice.service.team.PersonalGradeService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/15
 * 个人参加实训时所有可能会用到的接口
 * @last-check-in 魏啸冲
 * @date 2020/7/15
 */
@RestController
public class PersonTrainController {

    @Autowired
    private PersonalGradeService personalGradeService;

    @Autowired
    private UserInfoService userInfoService;

    // TODO 本controller由于service层尚未完成，不能测试

    /**
     * 学生查询自己的成绩
     *
     * @return 返回分数DTO
     */
    @Secured(CptmpRole.ROLE_STUDENT_MEMBER)
    @GetMapping("/api/student/me/grade")
    public RespBeanWithPersonalGradeDTO getMyGrade() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BigInteger userId = userInfoService.findByUsername(username).getId();
        try {
            // TODO 需要service层提供通过用户名和userId查找功能
            //PersonalGradeDTO personalGradeDTO = personalGradeService
            return new RespBeanWithPersonalGradeDTO()
        }
    }

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithPersonalGradeDTO extends RespBean {

    @JsonProperty("data")
    private PersonalGradeDTO personalGradeDTO;

    public RespBeanWithPersonalGradeDTO(PersonalGradeDTO personalGradeDTO) {
        super();
        this.personalGradeDTO = personalGradeDTO;
    }

    public RespBeanWithPersonalGradeDTO(Integer status, String msg) {
        super(status, msg);
    }

}
