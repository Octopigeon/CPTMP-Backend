package io.github.octopigeon.cptmpservice.service.notice;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.notice.NoticeDTO;
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
public class NoticeServiceImpl implements NoticeService{
    /**
     * 根据接收者去获取通知
     *
     * @param receiverId 接受者Id
     * @return
     */
    @Override
    public PageInfo<NoticeDTO> findByReceiverId(BigInteger receiverId) {
        return null;
    }

    /**
     * 根据团队Id获取通知
     *
     * @param teamId 团队Id
     * @return
     */
    @Override
    public PageInfo<NoticeDTO> findByTeamId(BigInteger teamId) {
        return null;
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(NoticeDTO dto) throws Exception {

    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(NoticeDTO dto) throws Exception {

    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(NoticeDTO dto) throws Exception {
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public NoticeDTO findById(BigInteger id) throws Exception {
        return null;
    }
}
