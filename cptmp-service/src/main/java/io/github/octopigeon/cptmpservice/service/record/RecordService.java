package io.github.octopigeon.cptmpservice.service.record;

import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
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
public interface RecordService extends BaseNormalService<RecordDTO> {

    /**
     * 使用实训id和userId进行查询
     * @param trainId 实训id
     * @param userId 用户id
     * @return
     */
    List<RecordDTO> findByTrainIdAndUserId(BigInteger trainId, BigInteger userId);

    /**
     * 使用实训id和团队Id进行查询
     * @param teamId 团队id
     * @return
     */
    List<RecordDTO> findByTeamId(BigInteger teamId);
}
