package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 用于提供各种用户信息查询接口
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestController
public class UserDetailsController {

    @Autowired
    private UserInfoService UserInfoService;

    /**
     * 根据用户名，得到用户基本信息
     * @param json 前端传来的json
     * @return 返回用户基本信息json
     */
    @GetMapping("/api/user/me/basic-info")
    public BaseUserInfoDTO getMyBasicInfo(
        @RequestBody String json
    ) throws JsonProcessingException {
        return UserInfoService.findBaseUserInfoByUsername(new ObjectMapper().readValue(json, ObjectNode.class)
                .get("username").asText());
    }

}
