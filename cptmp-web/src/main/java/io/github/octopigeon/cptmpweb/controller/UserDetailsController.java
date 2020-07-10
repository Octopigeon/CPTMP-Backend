package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.service.GetUserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import io.github.octopigeon.cptmpweb.bean.response.RespBeanWithObj;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private GetUserInfoService getUserInfoService;

    /**
     * 根据用户名，得到用户基本信息
     * @param json 前端传来的json
     * @return 返回用户基本信息json
     */
    @GetMapping("/api/user/me/basic-info")
    public RespBean getMyBasicInfo(
        @RequestBody String json
    ) throws JsonProcessingException {
        RespBeanWithBaseUserInfoDTO respBean = new RespBeanWithBaseUserInfoDTO();
        respBean.setBaseUserInfoDTO(getUserInfoService.findBaseUserInfoByUsername(new ObjectMapper().readValue(json, ObjectNode.class)
                .get("username").asText()));
        return respBean;
    }

    // TODO 修改用户信息的接口

}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithBaseUserInfoDTO extends RespBean {

    @JsonProperty("basic_info")
    private BaseUserInfoDTO baseUserInfoDTO;

}
