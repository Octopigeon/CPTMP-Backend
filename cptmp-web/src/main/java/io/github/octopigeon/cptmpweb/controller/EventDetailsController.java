package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.processevent.EventDTO;
import io.github.octopigeon.cptmpservice.service.processevent.EventService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/16
 * @last-check-in 陈若琳
 * @date 2020/07/16
 */

@RestController
public class EventDetailsController {

    @Autowired
    EventService eventService;

    /**
     * 导入event
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN,
            CptmpRole.ROLE_ENTERPRISE_ADMIN,
            CptmpRole.ROLE_SCHOOL_TEACHER,
            CptmpRole.ROLE_SCHOOL_ADMIN})
    @PostMapping("api/event")
    public RespBean addEvent(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        EventDTO event = objectMapper.readValue(json,EventDTO.class);
        try{
            eventService.add(event);
            return RespBean.ok("add event successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REGISTER_FAILED,"event add failed");
        }
    }

    /**
     * 根据id删除event
     * @param eventId
     * @return
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN,
            CptmpRole.ROLE_ENTERPRISE_ADMIN,
            CptmpRole.ROLE_SCHOOL_TEACHER,
            CptmpRole.ROLE_SCHOOL_ADMIN})
    @DeleteMapping("api/event/{event_id}")
    public RespBean deleteEvent(@PathVariable("event_id")BigInteger eventId)
    {
        try{
            EventDTO event = new EventDTO();
            event.setId(eventId);
            eventService.remove(event);
            return RespBean.ok("delete event successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"event delete failed");
        }
    }

    /**
     * 更新event信息
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN,
            CptmpRole.ROLE_ENTERPRISE_ADMIN,
            CptmpRole.ROLE_SCHOOL_TEACHER,
            CptmpRole.ROLE_SCHOOL_ADMIN})
    @PutMapping("api/event")
    public RespBean updateEvent(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        EventDTO newEvent = objectMapper.readValue(json,EventDTO.class);
        try{
            eventService.modify(newEvent);
            return RespBean.ok("update event successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED,"update event failed");
        }
    }

    /**
     * 分页获取所有event
     * @return
     */
    @GetMapping("api/event")
    public RespBeanWithEventList getAllEvents(@RequestParam("page") int page,@RequestParam("offset") int offset)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<EventDTO> pageInfo = eventService.findAllEvents(page, offset) ;
            List<EventDTO> eventList = pageInfo.getList();
            return new RespBeanWithEventList(eventList,pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithEventList(CptmpStatusCode.INFO_ACCESS_FAILED,"get events failed");
        }
    }

    /**
     * 根据id获取event
     * @param eventId
     * @return
     */
    @GetMapping("api/event/{event_id}")
    public RespBeanWithEvent getEventById(@PathVariable("event_id") BigInteger eventId)
    {
        try{
            EventDTO event = eventService.findById(eventId);
            return new RespBeanWithEvent(event);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithEvent(CptmpStatusCode.INFO_ACCESS_FAILED,"get event failed");
        }
    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithEventList extends RespBean
{
    public RespBeanWithEventList(Integer status, String msg)
    {
        super(status, msg);
    }

    public RespBeanWithEventList(List<EventDTO> events,long totalRows)
    {
        super();
        this.events = events;
        this.totalRows = totalRows;
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<EventDTO> events;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithEvent extends RespBean
{
    public RespBeanWithEvent(Integer status, String msg)
    {
        super(status, msg);
    }

    public RespBeanWithEvent(EventDTO event)
    {
        super();
        this.event = event;
    }

    @JsonProperty("data")
    private EventDTO event;
}
