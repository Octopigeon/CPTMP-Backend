package io.github.octopigeon.cptmpservice.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 * 登录成功后返回的信息
 */
@Data
public class LoginInfoDTO {

    @JSONField(name = "username")
    private String username;

    @JSONField(name = "role")
    private String role;

    @JSONField(name = "status_code")
    private Integer statusCode;

}
