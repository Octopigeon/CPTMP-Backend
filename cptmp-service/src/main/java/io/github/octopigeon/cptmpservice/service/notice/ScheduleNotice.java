package io.github.octopigeon.cptmpservice.service.notice;

import io.github.octopigeon.cptmpdao.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/21
 * @last-check-in Gh Li
 * @date 2020/7/21
 */
@Component
public class ScheduleNotice {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 每天的0点更新deadline提醒
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void addDeadlineNotice(){

    }

    /**
     * 每天自动检测notice表，移除已读且超过30天的过期notice
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredNotice(){
        noticeMapper.removeExpiredNotices();
    }

    /**
     * 每隔30秒检测一次作业，签到情况
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 30000)
    public void addWarningNotice(){

    }
}
