package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import io.github.octopigeon.cptmpservice.service.ModifyInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ruby
 * @version 1.0
 * @date 2020/07/10
 * @last-check-in Ruby
 * @date 2020/07/10
 */

@RestController
public class ModifyInfoController {

    @Autowired
    private ModifyInfoService modifyInfoService;

    @PutMapping("/api/user/me")
    public RespBean modifyInfo(@RequestBody String json) throws Exception
    {
        String role = new ObjectMapper().readValue(json,ObjectNode.class).get("roleName").asText();
        if(role.equals(RoleEnum.ROLE_STUDENT_MASTER.name())||role.equals(RoleEnum.ROLE_STUDENT_PM.name())
                ||role.equals(RoleEnum.ROLE_STUDENT_PO.name())||role.equals(RoleEnum.ROLE_STUDENT_MEMBER.name()))
        {
            StudentInfoDTO studentInfoDTO = new ObjectMapper().readValue(json, StudentInfoDTO.class);
            if(modifyInfoService.modifyUserInfo(studentInfoDTO)==0)
            {
                return RespBean.error(10000,"Username or id is empty.");
            }
            modifyInfoService.modifyStudentInfo(studentInfoDTO);
        }
        else if(role.equals(RoleEnum.ROLE_ENTERPRISE_ADMIN.name())||role.equals(RoleEnum.ROLE_SYSTEM_ADMIN.name()))
        {
            EnterpriseAdminInfoDTO enterpriseAdminInfoDTO = new ObjectMapper().readValue(json, EnterpriseAdminInfoDTO.class);
            if(modifyInfoService.modifyUserInfo(enterpriseAdminInfoDTO)==0)
            {
                return RespBean.error(10000,"Username or id is empty.");
            }
            modifyInfoService.modifyEnterpriseAdminInfo(enterpriseAdminInfoDTO);
        }
        else if(role.equals(RoleEnum.ROLE_SCHOOL_ADMIN.name())||role.equals(RoleEnum.ROLE_SCHOOL_TEACHER.name()))
        {
            TeacherInfoDTO teacherInfoDTO = new ObjectMapper().readValue(json, TeacherInfoDTO.class);
            if(modifyInfoService.modifyUserInfo(teacherInfoDTO)==0)
            {
                return RespBean.error(10000,"Username or id is empty.");
            }
            modifyInfoService.modifyTeacherInfo(teacherInfoDTO);
        }
        else
        {
            return RespBean.error(10001,"Rolename is empty.");
        }

        return RespBean.ok("Updated Successfully.");
    }
}
