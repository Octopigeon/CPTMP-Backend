package io.github.octopigeon.cptmpservice.service.processevent;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.EventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.model.Event;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpservice.dto.processevent.EventDTO;
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
 * 事件实现类
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(EventDTO dto) throws Exception {
        try{
            Event event = new Event();
            BeanUtils.copyProperties(dto, event);
            event.setGmtCreate(new Date());
            eventMapper.addEvent(event);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Add Event failed!");
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(EventDTO dto) throws Exception {
        if(eventMapper.findEventById(dto.getId()) != null){
            eventMapper.hideEventById(dto.getId(), new Date());
            processEventMapper.removeProcessEventsByEventId(dto.getId());
        }else {
            throw new ValueException("Event is not Exist!");
        }
    }

    /**
     * 待更新实体
     * @param dto 事件dto
     * @return 是否修改成功
     */
    @Override
    public Boolean modify(EventDTO dto) throws Exception {
        try{
            Event event = new Event();
            BeanUtils.copyProperties(dto, event, Utils.getNullPropertyNames(dto));
            event.setGmtModified(new Date());
            eventMapper.updateEventById(event);
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
    public EventDTO findById(BigInteger id) throws Exception {
        Event event = eventMapper.findEventById(id);
        if(event == null){
            throw new Exception("Event is not exist!");
        }
        EventDTO eventDTO = new EventDTO();
        BeanUtils.copyProperties(event, eventDTO);
        return eventDTO;
    }

    /**
     * 查询所有事件的分页列表
     * @param page 页号
     * @param offset 页容量
     * @return 事件分页列表
     */
    @Override
    public PageInfo<EventDTO> findAllEvents(int page, int offset) {
        List<Event> events = eventMapper.findAllEvents();
        List<EventDTO> results = new ArrayList<>();
        for (Event event: events) {
            EventDTO result = new EventDTO();
            BeanUtils.copyProperties(event, result);
            results.add(result);
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据流程Id查找事件
     * @param processId 流程Id
     * @return 事件分页列表
     */
    @Override
    public List<EventDTO> findEventsByProcessId(BigInteger processId) {
        List<ProcessEvent> processEvents = processEventMapper.findProcessEventsByProcessId(processId);
        List<EventDTO> results = new ArrayList<>();
        for (ProcessEvent processEvent: processEvents) {
            Event event = eventMapper.findEventById(processEvent.getEventId());
            EventDTO result = new EventDTO();
            BeanUtils.copyProperties(event, result);
            results.add(result);
        }
        return results;
    }
}
