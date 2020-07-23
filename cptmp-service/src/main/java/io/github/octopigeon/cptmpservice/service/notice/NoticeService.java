package io.github.octopigeon.cptmpservice.service.notice;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.notice.NoticeDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * 通知服务
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/20
 */
@Service
public interface NoticeService extends BaseNormalService<NoticeDTO> {

    /**
     * 根据发送者Id获取已发通知
     * @param page 页号
     * @param offset 页容量
     * @param senderId 发送者Id
     * @return 通知列表
     */
    PageInfo<NoticeDTO> findBySenderId(int page, int offset, BigInteger senderId);

    /**
     * 根据接收者去获取通知
     * @param page 页号
     * @param offset 页容量
     * @param receiverId 接受者Id
     * @return 通知列表
     */
    PageInfo<NoticeDTO> findByReceiverId(int page, int offset, BigInteger receiverId);

    /**
     * 根据团队Id获取通知
     * @param page 页号
     * @param offset 页容量
     * @param teamId 团队Id
     * @return 通知列表
     */
    PageInfo<NoticeDTO> findByTeamId(int page, int offset, BigInteger teamId);
}
