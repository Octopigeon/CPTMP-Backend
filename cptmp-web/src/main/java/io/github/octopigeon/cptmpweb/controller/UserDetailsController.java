package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 用于提供各种与用户信息交互的接口
 * @last-check-in anlow
 * @date 2020/7/11
 */
@RestController
public class UserDetailsController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户名，得到用户基本信息
     * @return 返回用户基本信息json
     */
    @GetMapping("/api/user/me/basic-info")
    public RespBeanWithBaseUserInfoDTO getMyBasicInfo() throws JsonProcessingException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RespBeanWithBaseUserInfoDTO respBean = new RespBeanWithBaseUserInfoDTO();
        respBean.setBaseUserInfoDTO(userInfoService.findBaseUserInfoByUsername(username));
        return respBean;
    }

    /**
     * 修改性别，简介信息
     * @param json 包含性别和简介的json
     * @return ok-成功 error-失败
     */
    @PutMapping("/api/user/me/basic-info")
    public RespBean updateMyBasicInfo(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO() {
        };
        // TODO 等数据库重构，把name加进BaseUserInfoDTO中
        String name = objectMapper.readValue(json, ObjectNode.class).get("name").asText();
        Boolean gender = objectMapper.readValue(json, ObjectNode.class).get("gender").asBoolean();
        String introduction = objectMapper.readValue(json, ObjectNode.class).get("introduction").asText();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        baseUserInfoDTO.setUsername(username);
        baseUserInfoDTO.setGender(gender);
        baseUserInfoDTO.setIntroduction(introduction);
        try {
            if (userInfoService.modify(baseUserInfoDTO)) {
                return RespBean.ok("update basic info successfully");
            } else {
                return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "update basic info failed");
            }
        } catch (Exception e) {
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "modify info failed");
        }
    }

    /**
     * 修改用户密码
     * @param json 包含用户名，用户输入的原密码，用户输入的新密码
     * @return 返回重置是否成功的信息
     */
    @PutMapping("/api/user/me/password")
    public RespBean updateUserPassword(
            @RequestBody String json
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 用户提交的原密码，马上会和数据库中的比较
        String originPassword = objectMapper.readValue(json, ObjectNode.class).get("origin_password").asText();
        String newPassword = objectMapper.readValue(json, ObjectNode.class).get("new_password").asText();
        if (!userInfoService.validateOriginPassword(username, originPassword)) {
            return RespBean.error(CptmpStatusCode.UPDATE_PASSWORD_FAILED, "wrong origin password");
        } else {
            userInfoService.updatePassword(username, newPassword);
            return RespBean.ok("reset password success");
        }
    }

    // TODO 修改用户信息的接口

}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithBaseUserInfoDTO extends RespBean {

    @JsonProperty("data")
    private BaseUserInfoDTO baseUserInfoDTO;

}
