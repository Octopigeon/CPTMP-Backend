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
public final class EmailTemplate {

    /** 邮件抬头 */
    public static final String ACTIVATE_SUBJECT = "嗨你好，这里是🐙🕊！";

    /** 生成激活链接 */
    public static String generateLink(String domain, String token, String email) {
        return  "http://" + domain + "/verify/email;token=" + token + ";email=" + email;
    }

}
