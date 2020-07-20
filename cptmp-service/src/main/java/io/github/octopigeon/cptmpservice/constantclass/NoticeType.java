package io.github.octopigeon.cptmpservice.constantclass;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in Gh Li
 * @date 2020/7/20
 */
public enum NoticeType {
    //警告通知
    WARNING_NOTICE("WARNING"),
    //Deadline通知
    DEADLINE_NOTICE("DEADLINE"),
    //Message通知
    MESSAGE_NOTICE("MESSAGE");

    private final String noticeType;

    NoticeType(String type) {
        this.noticeType = type;
    }
}
