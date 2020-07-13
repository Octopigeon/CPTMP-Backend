package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpservice.service.trainproject.TrainProjectService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
 * @date 2020/7/13
 */
@RestController
public class RegisterController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TrainProjectService trainProjectService;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 企业管理员账户由系统管理员导入，权限限定为系统管理员
     * @param json 一个列表，每项包含username, password, name, email, commonId, orgId
     * @return 返回失败名单
     */
    @Secured(CptmpRole.ROLE_SYSTEM_ADMIN)
    @PostMapping("/api/user/enterprise-admin")
    public RespBeanWithFailedRegisterList registerEnterpriseAdmin(@RequestBody String json) throws JsonProcessingException {
        return registerRole(json, CptmpRole.ROLE_ENTERPRISE_ADMIN);
    }

    /**
     * 企业管理员可以导入教师账号
     * @param json 其中orgId是各个学校的id，因此必须先创建学校，
     *             才能导入其中的教师和学生
     * @return 注册失败列表
     */
    @Secured(CptmpRole.ROLE_ENTERPRISE_ADMIN)
    @PostMapping("/api/user/teacher")
    public RespBeanWithFailedRegisterList registerTeacher(@RequestBody String json) throws JsonProcessingException {
       return registerRole(json, CptmpRole.ROLE_SCHOOL_TEACHER);
    }

    /**
     * 企业管理员可以导入学生账号
     * @param json 与教师一样
     * @return 注册失败列表
     */
    @Secured(CptmpRole.ROLE_ENTERPRISE_ADMIN)
    @PostMapping("/api/user/student")
    public RespBeanWithFailedRegisterList registerStudent(@RequestBody String json) throws JsonProcessingException {
        return registerRole(json, CptmpRole.ROLE_STUDENT_MEMBER);
    }

    /**
     * 解析json，根据角色名注册用户
     * @param json 前端发来的注册json，包含username，password，email，name，orgId五个字段
     * @param roleName 设定的角色名
     * @return 包含注册失败条目列表的信息
     */
    private RespBeanWithFailedRegisterList registerRole(String json, String roleName) throws JsonProcessingException {
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
                BaseUserInfoDTO baseUserInfoDTO = ReqBeanWithUserRegisterInfo.registerTo(
                        reqBeanWithUserRegisterInfo
                );
                // 用户名是一个前缀 + 横杠 + 工号
                baseUserInfoDTO.setCommonId(reqBeanWithUserRegisterInfo.getUsername().split("-")[1]);
                baseUserInfoDTO.setRoleName(roleName);
                userInfoService.add(baseUserInfoDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedRegisterList.report(registerFailedList);
    }

    @Secured(CptmpRole.ROLE_ENTERPRISE_ADMIN)
    @PostMapping("/api/org")
    public RespBeanWithFailedRegisterList registerOrganization(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithOrganizationRegisterInfo[] reqBeanWithOrganizationRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithOrganizationRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithOrganizationRegisterInfos.length; i++) {
            ReqBeanWithOrganizationRegisterInfo reqBeanWithOrganizationRegisterInfo
                    = reqBeanWithOrganizationRegisterInfos[i];
            try {
                OrganizationDTO organizationDTO = new OrganizationDTO();
                organizationDTO.setName(reqBeanWithOrganizationRegisterInfo.getName());
                // TODO 等李国豪加上真实名字字段
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedRegisterList.report(registerFailedList);
    }

    /**
     * 获取json中的name，project_level，project_content三个字段
     * @param json 包含上述三个字段的json
     * @return 返回一个包含导入失败条目列表的json
     */
    @Secured(CptmpRole.ROLE_ENTERPRISE_ADMIN)
    @PostMapping("/api/train-project")
    public RespBeanWithFailedRegisterList registerTrainProject(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithTrainProjectRegisterInfo[] reqBeanWithTrainProjectRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithTrainProjectRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithTrainProjectRegisterInfos.length; i++) {
            ReqBeanWithTrainProjectRegisterInfo reqBeanWithTrainProjectRegisterInfo
                    = reqBeanWithTrainProjectRegisterInfos[i];
            try {
                TrainProjectDTO trainProjectDTO = new TrainProjectDTO();
                trainProjectDTO.setName(reqBeanWithTrainProjectRegisterInfo.getProjectName());
                trainProjectDTO.setLevel(reqBeanWithTrainProjectRegisterInfo.getProjectLevel());
                trainProjectDTO.setContent(reqBeanWithTrainProjectRegisterInfo.getProjectContent());
                trainProjectService.add(trainProjectDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedRegisterList.report(registerFailedList);
    }

}

@Data
class ReqBeanWithUserRegisterInfo {

    private String username;
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
    public static BaseUserInfoDTO registerTo(ReqBeanWithUserRegisterInfo reqBeanWithUserRegisterInfo) {
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
        baseUserInfoDTO.setUsername(reqBeanWithUserRegisterInfo.getUsername());
        baseUserInfoDTO.setName((reqBeanWithUserRegisterInfo.getName()));
        baseUserInfoDTO.setPassword(reqBeanWithUserRegisterInfo.getPassword());
        baseUserInfoDTO.setEmail(reqBeanWithUserRegisterInfo.getEmail());
        baseUserInfoDTO.setOrganizationId(reqBeanWithUserRegisterInfo.getOrganizationId());
        return baseUserInfoDTO;
    }
}

@Data
class  ReqBeanWithOrganizationRegisterInfo {

    // TODO 等lgh创建学校名字段

    private String name;
    private String realName;
    private String websiteUrl;
    private String description;

}

@Data
class  ReqBeanWithTrainProjectRegisterInfo {

    private String projectName;
    private Integer projectLevel;
    /** 实训简介 */
    private String projectContent;

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithFailedRegisterList extends RespBean {

    public RespBeanWithFailedRegisterList(Integer status, String msg) {
        super(status, msg);
    }

    /**
     * 用于根据注册失败的条目序号生成返回体
     * @param failedList 失败条目序号列表
     * @return 一个返回体
     */
    public static RespBeanWithFailedRegisterList report(List<Integer> failedList) {
        if (failedList.size() == 0) {
            return new RespBeanWithFailedRegisterList(CptmpStatusCode.OK, "all set");
        } else {
            RespBeanWithFailedRegisterList respBeanWithFailedRegisterList
                    = new RespBeanWithFailedRegisterList(CptmpStatusCode.REGISTER_FAILED, "register failed");
            respBeanWithFailedRegisterList.setFailedList(failedList);
            return respBeanWithFailedRegisterList;
        }
    }

    @JsonProperty("data")
    private List<Integer> failedList;

}
