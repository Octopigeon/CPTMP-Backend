package io.github.octopigeon.cptmpservice.service.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.octopigeon.cptmpdao.mapper.RecordMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.model.Record;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
import io.github.octopigeon.cptmpservice.service.attachmentfile.AttachmentFileService;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class RecordServiceImpl extends BaseFileServiceImpl implements RecordService{

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private AttachmentFileService attachmentFileService;

    private final String assignmentlibJsonName = "assignments";

    @Autowired
    public RecordServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(RecordDTO dto) throws Exception {
        try{
            Record record = new Record();
            dto.setProcessEventId(getProcessEventId(dto.getProcessId(), dto.getEventId()));
            BeanUtils.copyProperties(dto, record);
            record.setGmtCreate(new Date());
            JSONObject object = new JSONObject();
            List<FileDTO> fileDTOS = new ArrayList<>();
            object.put(this.assignmentlibJsonName, fileDTOS);
            record.setAssignmentsLib(object.toJSONString());
            recordMapper.addRecord(record);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Add Record filed");
        }
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
     * 修改相关信息
     * @param dto 实体
     * @return 是否修改成功
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
     * @return 记录信息列表
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
     * @return 记录信息列表
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
     * 使用流程Id和事件Id进行查询
     *
     * @param processId 流程Id
     * @param eventId   事件Id
     * @return 记录列表
     */
    @Override
    public List<RecordDTO> findByProcessIdAndEventId(BigInteger processId, BigInteger eventId) {
        List<Record> records = recordMapper.findRecordByProcessEventId(getProcessEventId(processId, eventId));
        List<RecordDTO> results = new ArrayList<>();
        for (Record record: records) {
            results.add(convertRecord(record));
        }
        return results;
    }

    /**
     * 上传作业
     *
     * @param file     文件
     * @param recordId 活动记录id
     * @throws Exception 上传文件异常
     */
    @Override
    public void uploadAssignment(MultipartFile file, BigInteger recordId) throws Exception {
        FileDTO fileInfo = storePrivateFile(file);
        Record record = recordMapper.findRecordById(recordId);
        JSONObject object = JSON.parseObject(record.getAssignmentsLib());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.assignmentlibJsonName).toJSONString(), FileDTO.class);
        resourceLib.add(fileInfo);
        object.put(this.assignmentlibJsonName, resourceLib);
        record.setGmtModified(new Date());
        record.setAssignmentsLib(object.toJSONString());
        recordMapper.updateRecordById(record);
        attachmentFileService.add(fileInfo);
    }

    /**
     * 删除资源库中文件
     *
     * @param recordId 记录Id
     * @param file     fileDTO
     */
    @Override
    public void removeAssignment(BigInteger recordId, FileDTO file) throws Exception {
        Record record = recordMapper.findRecordById(recordId);
        JSONObject object = JSON.parseObject(record.getAssignmentsLib());
        List<FileDTO> resourceLib = JSON.parseArray(object.getJSONArray(this.assignmentlibJsonName).toJSONString(), FileDTO.class);
        resourceLib.remove(file);
        object.put(this.assignmentlibJsonName, resourceLib);
        record.setGmtModified(new Date());
        record.setAssignmentsLib(object.toJSONString());
        recordMapper.updateRecordById(record);
        attachmentFileService.remove(file);
        removeFile(file.getFilePath());
    }

    /**
     * 将record类型转成recordDTO类型
     * @param record record的model
     * @return recordDto类型
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
     * @param processId process唯一标识Id
     * @param eventId event唯一标识Id
     * @return processEventId
     */
    private BigInteger getProcessEventId(BigInteger processId, BigInteger eventId){
        return processEventMapper.findProcessEventByProcessIdAndEventId(processId, eventId).getId();
    }

    /**
     * 根据processEventId获取processId和EventId
     * @param processEventId processEvent唯一标识符
     * @return process与event唯一标识符
     */
    private BigInteger[] getProcessIdAndEventId(BigInteger processEventId){
        BigInteger[] ids = new BigInteger[2];
        ProcessEvent processEvent = processEventMapper.findProcessEventById(processEventId);
        ids[0] = processEvent.getProcessId();
        ids[1] = processEvent.getEventId();
        return ids;
    }
}
