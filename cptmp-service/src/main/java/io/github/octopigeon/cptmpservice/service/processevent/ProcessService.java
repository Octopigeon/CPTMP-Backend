package io.github.octopigeon.cptmpservice.service.processevent;

import io.github.octopigeon.cptmpservice.dto.processevent.ProcessDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Service
public interface ProcessService extends BaseNormalService<ProcessDTO> {

    /**
     * 向流程添加事件
     * @param processId 流程id
     * @param eventId 事件id
     */
    void addEvent(BigInteger processId, BigInteger eventId);

    /**
     * 从流程移除事件
     * @param processId 流程id
     * @param eventId 事件id
     */
    void removeEvent(BigInteger processId, BigInteger eventId);

    /**
     * 根据实训id查询流程
     * @param trainId 实训id
     * @return 流程dto
     */
    List<ProcessDTO> findByTrainId(BigInteger trainId);

    /**
     * 根据trainId移除流程
     * @param trainId 实训id
     */
    void removeByTrainId(BigInteger trainId);
}
