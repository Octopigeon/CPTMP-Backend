package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpservice.service.trainproject.ProjectService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/11
 * 提供各种用户注册，学校注册，实训项目注册，实训注册的api（所有可以批量导入的）
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@RestController
public class RegisterController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 企业管理员账户由系统管理员导入，权限限定为系统管理员
     * @param json 一个列表，每项包含username, password, name, email, commonId, orgId
     * @return 返回失败名单
     */
    @Secured(CptmpRole.ROLE_SYSTEM_ADMIN)
    @PostMapping("/api/user/enterprise-admin")
    public RespBeanWithFailedList registerEnterpriseAdmin(@RequestBody String json) throws JsonProcessingException {
        return registerRole(json, CptmpRole.ROLE_ENTERPRISE_ADMIN);
    }

    /**
     * 企业管理员可以导入学校管理员账号
     * @param json 其中orgId是各个学校的id，因此必须先创建学校，
     *             才能导入其中的教师和学生
     * @return 注册失败列表
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PostMapping("/api/user/teacher-admin")
    public RespBeanWithFailedList registerSchoolAmdin(@RequestBody String json) throws JsonProcessingException {
       return registerRole(json, CptmpRole.ROLE_SCHOOL_ADMIN);
    }

    /**
     * 学校管理员可以导入学生账号
     * @param json 与教师一样
     * @return 注册失败列表
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN, CptmpRole.ROLE_SCHOOL_ADMIN})
    @PostMapping("/api/user/student")
    public RespBeanWithFailedList registerStudent(@RequestBody String json) throws JsonProcessingException {
        return registerRole(json, CptmpRole.ROLE_STUDENT_MEMBER);
    }

    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN, CptmpRole.ROLE_SCHOOL_ADMIN})
    @PostMapping("api/user/teacher")
    public RespBeanWithFailedList registerTeacher(@RequestBody String json) throws  JsonProcessingException {
        return registerRole(json, CptmpRole.ROLE_SCHOOL_TEACHER);
    }

    /**
     * 解析json，根据角色名注册用户
     * @param json 前端发来的注册json，包含username，password，email，name，orgId五个字段
     * @param roleName 设定的角色名
     * @return 包含注册失败条目列表的信息
     */
    private RespBeanWithFailedList registerRole(String json, String roleName) throws JsonProcessingException {
        // 解析前端发来的一个json数组，每项包含username，name，password，email四项
        // common_id由用户名拆解得到，organization_id由系统管理员的organization_id决定（都为企业），对于学生和老师
        // 则为学校对应的id
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithUserRegisterInfo[] reqBeanWithUserRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithUserRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithUserRegisterInfos.length; i++) {
            // 遍历每个信息，逐一执行注册操作
            ReqBeanWithUserRegisterInfo reqBeanWithUserRegisterInfo
                    = reqBeanWithUserRegisterInfos[i];
           try {
               String organizationName = organizationService.findById(reqBeanWithUserRegisterInfo.getOrganizationId()).getName();
               BaseUserInfoDTO baseUserInfoDTO = ReqBeanWithUserRegisterInfo.registerTo(
                        reqBeanWithUserRegisterInfo,
                        roleName,
                        organizationName
                );
                userInfoService.add(baseUserInfoDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(registerFailedList);
    }

    /**
     * 企业管理员创建学校
     * @param json 包含学校代号，学校名字，官方网站，简介
     * @return 注册失败列表
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PostMapping("/api/org")
    public RespBeanWithFailedList registerOrganization(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithOrganizationRegisterInfo[] reqBeanWithOrganizationRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithOrganizationRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithOrganizationRegisterInfos.length; i++) {
            ReqBeanWithOrganizationRegisterInfo reqBeanWithOrganizationRegisterInfo
                    = reqBeanWithOrganizationRegisterInfos[i];
            try {
                OrganizationDTO organizationDTO = new OrganizationDTO();
                organizationDTO.setName(reqBeanWithOrganizationRegisterInfo.getCode());
                organizationDTO.setRealName(reqBeanWithOrganizationRegisterInfo.getRealName());
                organizationDTO.setDescription(reqBeanWithOrganizationRegisterInfo.getDescription());
                organizationDTO.setWebsiteUrl(reqBeanWithOrganizationRegisterInfo.getWebsiteUrl());
                organizationService.add(organizationDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(registerFailedList);
    }

    /**
     * 企业管理员创建实训项目获取json中的name，project_level，project_content三个字段
     * @param json 包含上述三个字段的json
     * @return 返回一个包含导入失败条目列表的json
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @PostMapping("/api/train-project")
    public RespBeanWithFailedList registerTrainProject(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithTrainProjectRegisterInfo[] reqBeanWithTrainProjectRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithTrainProjectRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithTrainProjectRegisterInfos.length; i++) {
            ReqBeanWithTrainProjectRegisterInfo reqBeanWithTrainProjectRegisterInfo
                    = reqBeanWithTrainProjectRegisterInfos[i];
            try {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setName(reqBeanWithTrainProjectRegisterInfo.getProjectName());
                projectDTO.setLevel(reqBeanWithTrainProjectRegisterInfo.getProjectLevel());
                projectDTO.setContent(reqBeanWithTrainProjectRegisterInfo.getProjectContent());
                projectService.add(projectDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(registerFailedList);
    }

    /**
     * 验证邀请码的正确性，并返回状态给前端
     * @return 邀请码是否有效
     */
    @GetMapping("/api/user/student/invite")
    public RespBeanWithOrganizationDTO registerGuard(@RequestBody String json) throws JsonProcessingException {
        String invitationCode = new ObjectMapper().readValue(json, ObjectNode.class).get("invitation_code").asText();
        try {
            OrganizationDTO organizationDTO = organizationService.findByInvitationCode(invitationCode);
            if (organizationDTO.getInvitationCode().equals(invitationCode)) {
                return new RespBeanWithOrganizationDTO(organizationDTO);
            } else {
                return new RespBeanWithOrganizationDTO(CptmpStatusCode.FAKE_INVITATION_CODE, "fake invitation code");
            }
        } catch (Exception e) {
            return new RespBeanWithOrganizationDTO(CptmpStatusCode.FAKE_INVITATION_CODE, "fake invitation code");
        }
    }

    /**
     * 学生通过邀请码注册，此路径不需要登录
     * @param json 包含学校信息，以及学生自己填写的个人信息，以及邀请码
     * @return 注册结果（成功/失败）
     */
    @PostMapping("/api/user/student/invite")
    public RespBean registerStudentByInvitationCode(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String name = objectMapper.readValue(json, ObjectNode.class).get("name").asText();
        String email = objectMapper.readValue(json, ObjectNode.class).get("email").asText();
        // 学号
        String username = objectMapper.readValue(json, ObjectNode.class).get("username").asText();
        String password = objectMapper.readValue(json, ObjectNode.class).get("password").asText();
        String invitationCode = objectMapper.readValue(json, ObjectNode.class).get("invitation_code").asText();
        try {
            BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
            // 前缀 + 学号
            baseUserInfoDTO.setUsername(username);
            baseUserInfoDTO.setCommonId(username.split("-")[1]);
            baseUserInfoDTO.setRoleName(CptmpRole.ROLE_STUDENT_MEMBER);
            baseUserInfoDTO.setName(name);
            baseUserInfoDTO.setPassword(password);
            baseUserInfoDTO.setEmail(email);
            baseUserInfoDTO.setOrganizationId(organizationService.findByInvitationCode(invitationCode).getId());
            userInfoService.add(baseUserInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REGISTER_FAILED, "register failed");
        }
        return RespBean.ok("register success");
    }


}

@Data
class ReqBeanWithUserRegisterInfo {

    /** 学号或者工号 */
    @JsonProperty("common_id")
    private String commonId;
    private String name;
    private String password;
    private String email;
    @JsonProperty("organization_id")
    private BigInteger organizationId;

    /**
     * 用于将username, name, password, email, orgId封装成一个BaseUserInfo
     * @param reqBeanWithUserRegisterInfo json子节点
     * @return 一个拥有上述五种属性的BaseUserInfo
     */
    public static BaseUserInfoDTO registerTo(ReqBeanWithUserRegisterInfo reqBeanWithUserRegisterInfo
            , String roleName
            , String organizationName) {
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
        // 用户名是一个前缀 + 横杠 + 工
        baseUserInfoDTO.setUsername(organizationName + "-" + reqBeanWithUserRegisterInfo.getCommonId());
        baseUserInfoDTO.setCommonId(reqBeanWithUserRegisterInfo.getCommonId());
        baseUserInfoDTO.setRoleName(roleName);
        baseUserInfoDTO.setName(reqBeanWithUserRegisterInfo.getName());
        baseUserInfoDTO.setPassword(reqBeanWithUserRegisterInfo.getPassword());
        baseUserInfoDTO.setEmail(reqBeanWithUserRegisterInfo.getEmail());
        baseUserInfoDTO.setOrganizationId(reqBeanWithUserRegisterInfo.getOrganizationId());
        return baseUserInfoDTO;
    }
}

@Data
class  ReqBeanWithOrganizationRegisterInfo {

    /** 学校简称 */
    @JsonProperty("code")
    private String code;
    /** 学校真实名字 */
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("website_url")
    private String websiteUrl;
    @JsonProperty("description")
    private String description;

}

@Data
class  ReqBeanWithTrainProjectRegisterInfo {

    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("project_level")
    private Integer projectLevel;
    /** 实训简介 */
    @JsonProperty("project_content")
    private String projectContent;

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithFailedList extends RespBean {

    public RespBeanWithFailedList(Integer status, String msg) {
        super(status, msg);
    }

    /**
     * 用于根据注册失败的条目序号生成返回体
     * @param failedList 失败条目序号列表
     * @return 一个返回体
     */
    public static RespBeanWithFailedList report(List<Integer> failedList) {
        if (failedList.size() == 0) {
            return new RespBeanWithFailedList(CptmpStatusCode.OK, "all set");
        } else {
            RespBeanWithFailedList respBeanWithFailedList
                    = new RespBeanWithFailedList(CptmpStatusCode.REGISTER_FAILED, "operation failed");
            respBeanWithFailedList.setFailedList(failedList);
            return respBeanWithFailedList;
        }
    }

    @JsonProperty("data")
    private List<Integer> failedList;

}


@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithOrganizationDTO extends RespBean {

    public RespBeanWithOrganizationDTO(OrganizationDTO organizationDTO) {
        super();
        this.organizationDTO = organizationDTO;
    }

    public RespBeanWithOrganizationDTO(Integer status, String msg) {
        super(status, msg);
    }

    @JsonProperty("data")
    private OrganizationDTO organizationDTO;

}