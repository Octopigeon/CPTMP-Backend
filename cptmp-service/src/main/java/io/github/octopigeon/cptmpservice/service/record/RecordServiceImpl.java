package io.github.octopigeon.cptmpservice.service.record;

import io.github.octopigeon.cptmpdao.mapper.RecordMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.model.Record;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(RecordDTO dto) throws Exception {
        Record record = new Record();
        BeanUtils.copyProperties(dto, record);
        record.setGmtCreate(new Date());
        recordMapper.addRecord(record);
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Deprecated
    @Override
    public void remove(RecordDTO dto) throws Exception {

    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Deprecated
    @Override
    public Boolean modify(RecordDTO dto) throws Exception {
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public RecordDTO findById(BigInteger id) throws Exception {
        return convertRecord(recordMapper.findRecordById(id));
    }

    /**
     * 使用实训id和userId进行查询
     *
     * @param trainId 实训id
     * @param userId  用户id
     * @return
     */
    @Override
    public List<RecordDTO> findByTrainIdAndUserId(BigInteger trainId, BigInteger userId) {
        List<Record> records = recordMapper.findRecordByUserId(trainId, userId);
        List<RecordDTO> results = new ArrayList<>();
        for (Record record: records) {
            results.add(convertRecord(record));
        }
        return results;
    }

    /**
     * 使用实训id和团队Id进行查询
     * @param teamId  团队id
     * @return
     */
    @Override
    public List<RecordDTO> findByTeamId(BigInteger teamId) {
        List<Record> records = recordMapper.findRecordByTeamId(teamId);
        List<RecordDTO> results = new ArrayList<>();
        for (Record record: records) {
            results.add(convertRecord(record));
        }
        return results;
    }

    /**
     * 将record类型转成recordDTO类型
     * @param record
     * @return
     */
    private RecordDTO convertRecord(Record record){
        RecordDTO recordDTO = new RecordDTO();
        BeanUtils.copyProperties(record, recordDTO);
        BigInteger[] ids = getProcessIdAndEventId(recordDTO.getProcessEventId());
        recordDTO.setProcessId(ids[0]);
        recordDTO.setEventId(ids[1]);
        return recordDTO;
    }

    /**
     * 根据进程id和事件id获取processEventId
     * @param processId
     * @param eventId
     * @return
     */
    private BigInteger getProcessEventId(BigInteger processId, BigInteger eventId){
        return processEventMapper.findProcessEventByProcessIdAndEventId(processId, eventId).getId();
    }

    /**
     * 根据processEventId获取processId和EventId
     * @param processEventId
     * @return
     */
    private BigInteger[] getProcessIdAndEventId(BigInteger processEventId){
        BigInteger[] ids = new BigInteger[2];
        ProcessEvent processEvent = processEventMapper.findProcessEventById(processEventId);
        ids[0] = processEvent.getProcessId();
        ids[1] = processEvent.getEventId();
        return ids;
    }
}
