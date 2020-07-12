package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
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
 * 提供各种用户注册的api
 * @last-check-in 魏啸冲
 * @date 2020/7/13
 */
@RestController
public class RegisterController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 企业管理员账户由系统管理员导入，权限限定为系统管理员
     * @param json 一个列表，每项包含username, password, name, email, commonId, orgId
     * @return 返回失败名单
     */
    @Secured(CptmpRole.ROLE_SYSTEM_ADMIN)
    @PostMapping("/api/user/enterprise-admin")
    public RespBeanWithFailedRegisterList registerEnterpriseAdmin(@RequestBody String json) throws JsonProcessingException {
        // 解析前端发来的一个json数组，每项包含username，name，password，email四项
        // common_id由用户名拆解得到，organization_id由系统管理员的organization_id决定（都为企业）
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithEnterpriseAdminRegisterInfo[] reqBeanWithEnterpriseAdminRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithEnterpriseAdminRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithEnterpriseAdminRegisterInfos.length; i++) {
            // 遍历每个信息，逐一执行注册操作
            ReqBeanWithEnterpriseAdminRegisterInfo reqBeanWithEnterpriseAdminRegisterInfo
                    = reqBeanWithEnterpriseAdminRegisterInfos[i];
            try {
                BaseUserInfoDTO baseUserInfoDTO = ReqBeanWithEnterpriseAdminRegisterInfo.registerTo(
                        reqBeanWithEnterpriseAdminRegisterInfo
                );
                // 用户名是一个前缀 + 横杠 + 工号
                baseUserInfoDTO.setCommonId(reqBeanWithEnterpriseAdminRegisterInfo.getUsername().split("-")[1]);
                baseUserInfoDTO.setRoleName(RoleEnum.ROLE_ENTERPRISE_ADMIN.name());
                userInfoService.add(baseUserInfoDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedRegisterList.report(registerFailedList);
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
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithEnterpriseAdminRegisterInfo[] reqBeanWithEnterpriseAdminRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithEnterpriseAdminRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithEnterpriseAdminRegisterInfos.length; i++) {
            ReqBeanWithEnterpriseAdminRegisterInfo reqBeanWithEnterpriseAdminRegisterInfo
                    = reqBeanWithEnterpriseAdminRegisterInfos[i];
            try {
                BaseUserInfoDTO baseUserInfoDTO = ReqBeanWithEnterpriseAdminRegisterInfo.registerTo(
                        reqBeanWithEnterpriseAdminRegisterInfo
                );
                // 用户名是一个前缀 + 横杠 + 工号
                baseUserInfoDTO.setCommonId(reqBeanWithEnterpriseAdminRegisterInfo.getUsername().split("-")[1]);
                baseUserInfoDTO.setRoleName(RoleEnum.ROLE_SCHOOL_TEACHER.name());
                userInfoService.add(baseUserInfoDTO);
            } catch (Exception e) {
                e.printStackTrace();
                registerFailedList.add(i);
            }
        }
        return RespBeanWithFailedRegisterList.report(registerFailedList);
    }

}

@Data
class ReqBeanWithEnterpriseAdminRegisterInfo {

    private String username;
    private String name;
    private String password;
    private String email;
    @JsonProperty("organization_id")
    private BigInteger organizationId;

    /**
     * 用于将username, name, password, email, orgId封装成一个BaseUserInfo
     * @param reqBeanWithEnterpriseAdminRegisterInfo json子节点
     * @return 一个拥有上述五种属性的BaseUserInfo
     */
    public static BaseUserInfoDTO registerTo(ReqBeanWithEnterpriseAdminRegisterInfo reqBeanWithEnterpriseAdminRegisterInfo) {
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
        baseUserInfoDTO.setUsername(reqBeanWithEnterpriseAdminRegisterInfo.getUsername());
        baseUserInfoDTO.setName((reqBeanWithEnterpriseAdminRegisterInfo.getName()));
        baseUserInfoDTO.setPassword(reqBeanWithEnterpriseAdminRegisterInfo.getPassword());
        baseUserInfoDTO.setEmail(reqBeanWithEnterpriseAdminRegisterInfo.getEmail());
        baseUserInfoDTO.setOrganizationId(reqBeanWithEnterpriseAdminRegisterInfo.getOrganizationId());
        return baseUserInfoDTO;
    }
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
