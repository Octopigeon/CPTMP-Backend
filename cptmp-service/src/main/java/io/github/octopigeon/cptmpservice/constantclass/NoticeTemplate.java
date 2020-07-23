package io.github.octopigeon.cptmpservice.constantclass;

import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/23
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
public final class NoticeTemplate {
    /**
     * deadline提醒内容
     * @param content 事务内容
     * @param deadline 最后期限
     * @return deadline提醒消息
     */
    public static String generateDeadlineNotice(String trainName, String content, Date deadline){
        return String.format("提醒：在 %s 中，您有一个 %s 待完成，最后期限为：%s",trainName, content, deadline.toString());
    }

    /**
     * 警告消息提醒内容
     * @param username 用户名
     * @param content 事务内容
     * @return 用户警告消息
     */
    public static String generateUserWarningNotice(String username, String content){
        return String.format("警告：用户 %s 未正常完成 %s", username, content);
    }

    /**
     * 警告消息提醒内容
     * @param teamName 用户名
     * @param content 事务内容
     * @return 团队警告消息
     */
    public static String generateTeamWarningNotice(String teamName, String content){
        return String.format("警告：团队 %s 未正常完成 %s", teamName, content);
    }
}
