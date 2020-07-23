package io.github.octopigeon.cptmpservice.service.processevent;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.processevent.EventDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/18
 */
@Service
public interface EventService extends BaseNormalService<EventDTO> {

    /**
     * 查询所有事件的分页列表
     * @param page 页号
     * @param offset 页容量
     * @return 事件分页列表
     */
    PageInfo<EventDTO> findAllEvents(int page, int offset);

    /**
     * 根据流程Id查找事件
     * @param processId 流程Id
     * @return 事件分页列表
     */
    List<EventDTO> findEventsByProcessId(BigInteger processId);
}
