package io.github.octopigeon.cptmpweb.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author anlow
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
