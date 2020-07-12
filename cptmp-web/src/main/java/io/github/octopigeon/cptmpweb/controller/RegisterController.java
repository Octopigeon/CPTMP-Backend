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
import jdk.nashorn.internal.ir.ObjectNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/11
 * 提供各种用户注册的api
 * @last-check-in 魏啸冲
 * @date 2020/7/11
 */
@RestController
public class RegisterController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 企业管理员账户由系统管理员导入，权限限定为系统管理员
     * @param json 一个列表，每项包含username, password, email
     * @return 返回失败名单
     */
    @Secured(CptmpRole.ROLE_SYSTEM_ADMIN)
    @PostMapping("/api/user/enterprise-admin")
    public RespBeanWithFailedRegisterList registerEnterpriseAdmin(@RequestBody String json) throws JsonProcessingException {
        // 解析前端发来的一个json数组，每项包含username，name，password，email四项
        ObjectMapper objectMapper = new ObjectMapper();
        ReqBeanWithEnterpriseAdminRegisterInfo[] reqBeanWithEnterpriseAdminRegisterInfos
                = objectMapper.readValue(json, ReqBeanWithEnterpriseAdminRegisterInfo[].class);
        List<Integer> registerFailedList = new ArrayList<>();
        for (int i = 0; i < reqBeanWithEnterpriseAdminRegisterInfos.length; i++) {
            // 遍历每个信息，逐一执行注册操作
            ReqBeanWithEnterpriseAdminRegisterInfo reqBeanWithEnterpriseAdminRegisterInfo
                    = reqBeanWithEnterpriseAdminRegisterInfos[i];
            try {
                BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
                baseUserInfoDTO.setUsername(reqBeanWithEnterpriseAdminRegisterInfo.getUsername());
                baseUserInfoDTO.setName((reqBeanWithEnterpriseAdminRegisterInfo.getName()));
                baseUserInfoDTO.setPassword(reqBeanWithEnterpriseAdminRegisterInfo.getPassword());
                baseUserInfoDTO.setEmail(reqBeanWithEnterpriseAdminRegisterInfo.getEmail());
                // 用户名是一个前缀加工号
                baseUserInfoDTO.setCommonId(reqBeanWithEnterpriseAdminRegisterInfo.getUsername().split("-")[1]);
                baseUserInfoDTO.setRoleName(RoleEnum.ROLE_ENTERPRISE_ADMIN.name());
                userInfoService.add(baseUserInfoDTO);
            } catch (Exception e) {
                // TODO 等服务层细化异常，尽量把每个条目的错误信息做出来
                registerFailedList.add(i);
            }
        }
        if (registerFailedList.size() == 0) {
            return new RespBeanWithFailedRegisterList(CptmpStatusCode.OK, "all set");
        } else {
            RespBeanWithFailedRegisterList respBeanWithFailedRegisterList
                    = new RespBeanWithFailedRegisterList(CptmpStatusCode.REGISTER_FAILED, "register failed");
            respBeanWithFailedRegisterList.setFailedList(registerFailedList);
            return respBeanWithFailedRegisterList;
        }

    }

}

@Data
class ReqBeanWithEnterpriseAdminRegisterInfo {

    private String username;
    private String name;
    private String password;
    private String email;

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithFailedRegisterList extends RespBean {

    public RespBeanWithFailedRegisterList(Integer status, String msg) {
        super(status, msg);
    }

    @JsonProperty("data")
    private List<Integer> failedList;

}
