package io.github.octopigeon.cptmpservice.service.processevent;

import io.github.octopigeon.cptmpservice.dto.processevent.EventDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Service
public interface EventService extends BaseNormalService<EventDTO> {

    /**
     * 查询所有event
     * @return
     */
    List<EventDTO> findAllEvents();
}
