package io.github.octopigeon.cptmpservice.service.record;

import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public interface RecordService extends BaseNormalService<RecordDTO>, BaseFileService {

    /**
     * 使用实训id和userId进行查询
     * @param trainId 实训id
     * @param userId 用户id
     * @return 记录列表
     */
    List<RecordDTO> findByTrainIdAndUserId(BigInteger trainId, BigInteger userId);

    /**
     * 使用实训id和团队Id进行查询
     * @param teamId 团队id
     * @return 记录列表
     */
    List<RecordDTO> findByTeamId(BigInteger teamId);

    /**
     * 使用流程Id和事件Id进行查询
     * @param processId 流程Id
     * @param eventId 事件Id
     * @return 记录列表
     */
    List<RecordDTO> findByProcessIdAndEventId(BigInteger processId, BigInteger eventId);

    /**
     * 上传作业
     * @param file 文件
     * @param recordId 活动记录id
     * @throws Exception
     */
    void uploadAssignment(MultipartFile file, BigInteger recordId) throws Exception;

    /**
     * 删除资源库中文件
     * @param recordId 记录Id
     * @param file fileDTO
     */
    void removeAssignment(BigInteger recordId, FileDTO file) throws Exception;
}
