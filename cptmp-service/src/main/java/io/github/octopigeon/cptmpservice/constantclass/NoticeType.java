package io.github.octopigeon.cptmpservice.constantclass;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/20
 */
public enum NoticeType {
    //警告通知
    WARNING_NOTICE("WARNING"),
    //Deadline通知
    DEADLINE_NOTICE("DEADLINE"),
    //Message通知
    MESSAGE_NOTICE("MESSAGE");

    private String noticeType;

    NoticeType(String type) {
        this.noticeType = type;
    }
}
