package io.github.octopigeon.cptmpservice.service.processevent;

import io.github.octopigeon.cptmpdao.mapper.EventMapper;
import io.github.octopigeon.cptmpdao.mapper.ProcessMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.model.Event;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpservice.dto.processevent.EventDTO;
import io.github.octopigeon.cptmpservice.dto.processevent.ProcessDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
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
public class ProcessServiceImpl implements ProcessService{

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(ProcessDTO dto) throws Exception {
        try{
            Process process = new Process();
            BeanUtils.copyProperties(dto, process);
            process.setGmtCreate(new Date());
            processMapper.addProcess(process);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(ProcessDTO dto) throws Exception {
        if(processMapper.findProcessById(dto.getId()) != null){
            processMapper.hideProcessById(new Date(), dto.getId());
        }else {
            throw new Exception("Process is not exist!");
        }
    }

    /**
     * 待更新的实体
     * @param dto 实体
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(ProcessDTO dto) throws Exception {
        try{
            Process process = new Process();
            BeanUtils.copyProperties(dto, process, Utils.getNullPropertyNames(dto));
            process.setGmtModified(new Date());
            processMapper.updateProcessById(process);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public ProcessDTO findById(BigInteger id) throws Exception {
        Process process = processMapper.findProcessById(id);
        if(process == null){
            throw new Exception("Process is not exist!");
        }
        ProcessDTO processDTO = new ProcessDTO();
        BeanUtils.copyProperties(process, processDTO);
        processDTO.setEvents(eventService.findEventsByProcessId(id));
        return processDTO;
    }

    /**
     * 向流程添加事件
     *
     * @param processId 流程id
     * @param eventId   事件id
     */
    @Override
    public void addEvent(BigInteger processId, BigInteger eventId) {
        if(processMapper.findProcessById(processId) == null){
            throw new ValueException("process is not exist!");
        }
        if(eventMapper.findEventById(eventId) == null){
            throw new ValueException("event is not exist!");
        }
        if(processEventMapper.findProcessEventByProcessIdAndEventId(processId, eventId) == null){
            ProcessEvent processEvent = new ProcessEvent();
            processEvent.setGmtCreate(new Date());
            processEvent.setEventId(eventId);
            processEvent.setProcessId(processId);
            processEventMapper.addProcessEvent(processEvent);
        }
    }

    /**
     * 从流程移除事件
     *
     * @param processId 流程id
     * @param eventId   事件id
     */
    @Override
    public void removeEvent(BigInteger processId, BigInteger eventId) {
        processEventMapper.removeProcessEventByProcessIdAndEventId(processId, eventId);
    }

    /**
     * 根据实训id查询流程
     *
     * @param trainId 实训id
     * @return 流程列表
     */
    @Override
    public List<ProcessDTO> findByTrainId(BigInteger trainId) {
        List<Process> processes = processMapper.findProcessesByTrainId(trainId);
        List<ProcessDTO> results = new ArrayList<>();
        for (Process process: processes) {
            ProcessDTO result = new ProcessDTO();
            BeanUtils.copyProperties(process, result);
            result.setEvents(eventService.findEventsByProcessId(process.getId()));
            results.add(result);
        }
        return results;
    }

    /**
     * 根据trainId移除流程
     *
     * @param trainId 实训id
     */
    @Override
    public void removeByTrainId(BigInteger trainId) {
        List<Process> processes = processMapper.findProcessesByTrainId(trainId);
        processMapper.hideProcessesByTrainId(new Date(), trainId);
        for (Process process: processes) {
            processEventMapper.removeProcessEventsByProcessId(process.getId());
        }
    }
}
