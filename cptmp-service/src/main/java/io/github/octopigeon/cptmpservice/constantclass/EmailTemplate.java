package io.github.octopigeon.cptmpservice.constantclass;

/**
 * 本类主要用于生成激活邮件的模板
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
