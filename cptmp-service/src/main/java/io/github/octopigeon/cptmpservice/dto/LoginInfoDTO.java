package io.github.octopigeon.cptmpservice.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 * 登录成功后返回的信息
 */
@Data
public class LoginInfoDTO {

    @JSONField(name = "login_date")
    private Date loginDate;

    @JSONField(name = "status_code")
    private Integer statusCode;

}
