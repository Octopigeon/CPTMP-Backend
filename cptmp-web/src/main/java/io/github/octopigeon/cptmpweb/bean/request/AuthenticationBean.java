package io.github.octopigeon.cptmpweb.bean.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
 * @date 2020/7/9
 */
@Data
public class AuthenticationBean {

    private String username;
    private String password;

}
