package io.github.octopigeon.cptmpservice.constantclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 魏啸冲
 * @date 2020/7/13
 */
@Component
public final class EmailTemplate {

    @Autowired
    private Environment environment;

    /** 邮件抬头 */
    public final String ACTIVATE_SUBJECT = "嗨你好，这里是🐙🕊！";

    /** 激活链接抬头 */
    public final String ACTIVATE_TEXT = "http://" + environment.getProperty("domain.name") + "/verify/email;token=";

    /** 生成激活链接 */
    public String generateLink(String token, String email) {
        return ACTIVATE_TEXT + token + ";email=" + email;
    }

}
