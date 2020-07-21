package io.github.octopigeon.cptmpservice.service.notice;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.notice.NoticeDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in Gh Li
 * @date 2020/7/20
 */
@Service
public interface NoticeService extends BaseNormalService<NoticeDTO> {
    /**
     * 根据接收者去获取通知
     * @param page 页号
     * @param offset 页容量
     * @param receiverId 接受者Id
     * @return
     */
    PageInfo<NoticeDTO> findByReceiverId(int page, int offset, BigInteger receiverId);

    /**
     * 根据团队Id获取通知
     * @param page 页号
     * @param offset 页容量
     * @param teamId 团队Id
     * @return
     */
    PageInfo<NoticeDTO> findByTeamId(int page, int offset, BigInteger teamId);

    /**
     * 移除已读且超过30天的过期消息
     */
    void removeExpiredNotice();
}
