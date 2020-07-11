package io.github.octopigeon.cptmpservice.service.otherservice;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
public interface EmailService {
    /**
     * 邮件服务
     * @param to 目的地
     * @param subject 可能是主题
     * @param text 文本
     */
    void sendSimpleMessage(String to, String subject, String text);
}
