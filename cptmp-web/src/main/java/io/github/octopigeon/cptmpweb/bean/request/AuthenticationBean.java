package io.github.octopigeon.cptmpweb.bean.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * 登录消息，用于反序列化一段包含username和password的json
 * @last-check-in 魏啸冲
 * @date 2020/7/9
 */
@Data
public class AuthenticationBean {

    private String username;
    private String password;

}
