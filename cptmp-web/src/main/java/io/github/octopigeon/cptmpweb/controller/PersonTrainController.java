package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 学生查询自己在某个队伍里的成绩
     *
     * @return 返回分数DTO
     */
    @Secured(CptmpRole.ROLE_STUDENT_MEMBER)
    @GetMapping("/api/student/me/team/{teamId}/remark")
    public RespBeanWithPersonalGradeDTO getMyGrade(
            @PathVariable(value = "teamId") BigInteger teamId
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BigInteger userId = userInfoService.findByUsername(username).getId();
        try {
            PersonalGradeDTO personalGradeDTO = personalGradeService.findByUserIdAndTeamId(userId, teamId);
            return new RespBeanWithPersonalGradeDTO(personalGradeDTO);
        } catch (Exception e) {
            return new RespBeanWithPersonalGradeDTO(CptmpStatusCode.INFO_ACCESS_FAILED, "get grade failed");
        }
    }

    /**
     * 老师查询学生的成绩
     * @param userId 用户id
     * @param teamId 队伍id
     * @return 返回查询到的结果
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN, CptmpRole.ROLE_SCHOOL_ADMIN,
    CptmpRole.ROLE_SCHOOL_TEACHER})
    @GetMapping("/api/student/{userId}/team/{teamId}/remark")
    public RespBeanWithPersonalGradeDTO getStudentGrade(
            @PathVariable(value = "userId") BigInteger userId,
            @PathVariable(value = "teamId") BigInteger teamId
    ) {
        String operatorUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        BigInteger operatorId = userInfoService.findByUsername(operatorUsername).getId();
        try {
            PersonalGradeDTO personalGradeDTO = personalGradeService.findByUserIdAndTeamId(userId, teamId);
            if (personalGradeService.verifyPermission(operatorId, personalGradeDTO)) {
                return new RespBeanWithPersonalGradeDTO(personalGradeDTO);
            } else {
                return new RespBeanWithPersonalGradeDTO(CptmpStatusCode.INFO_ACCESS_FAILED, "get student grade failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithPersonalGradeDTO(CptmpStatusCode.INFO_ACCESS_FAILED, "get student grade failed");
        }
    }

    /**
     * 老师权限及以上的人可以修改学生的分数
     * @param userId 待修改学生的id
     * @param teamId 待修改学生所在队伍id
     * @param json 一个json，包含修改的分数以及评价
     * @return 修改是否成功
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN, CptmpRole.ROLE_SCHOOL_ADMIN,
            CptmpRole.ROLE_SCHOOL_TEACHER})
    @PutMapping("/api/student/{userId}/team/{teamId}/remark")
    public RespBean updateStudentGrade(
            @PathVariable(value = "userId") BigInteger userId,
            @PathVariable(value = "teamId") BigInteger teamId,
            @RequestBody String json
    ) throws JsonProcessingException {
        String operatorUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        BigInteger operatorId = userInfoService.findByUsername(operatorUsername).getId();
        ObjectMapper objectMapper = new ObjectMapper();
        Integer managePt = objectMapper.readValue(json, ObjectNode.class).get("manage_point").asInt();
        Integer codePt = objectMapper.readValue(json, ObjectNode.class).get("code_point").asInt();
        Integer techPt = objectMapper.readValue(json, ObjectNode.class).get("tech_point").asInt();
        Integer frameworkPt = objectMapper.readValue(json, ObjectNode.class).get("framework_point").asInt();
        Integer communicationPt = objectMapper.readValue(json, ObjectNode.class).get("communication_point").asInt();

        String evaluation = objectMapper.readValue(json, ObjectNode.class).get("evaluation").asText();
        try {
            PersonalGradeDTO personalGradeDTO = new PersonalGradeDTO();
            personalGradeDTO.setId(personalGradeService.findByUserIdAndTeamId(userId, teamId).getId());
            personalGradeDTO.setUserId(userId);
            personalGradeDTO.setTeamId(teamId);
            personalGradeDTO.setEvaluation(evaluation);
            personalGradeDTO.setManagePt(managePt);
            personalGradeDTO.setCodePt(codePt);
            personalGradeDTO.setCommunicationPt(communicationPt);
            personalGradeDTO.setFrameworkPt(frameworkPt);
            personalGradeDTO.setTechPt(techPt);
            if (personalGradeService.verifyPermission(operatorId, personalGradeDTO)) {
                personalGradeService.modify(personalGradeDTO);
                return RespBean.ok("modify student grade success");
            } else {
                return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "modify student grade failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "modify student grade failed");
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
