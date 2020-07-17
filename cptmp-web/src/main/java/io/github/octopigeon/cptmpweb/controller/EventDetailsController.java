package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.processevent.EventDTO;
import io.github.octopigeon.cptmpservice.service.processevent.EventService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("api/event")
    public RespBean addEvent(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        EventDTO event = objectMapper.readValue(json,EventDTO.class);
        try{


        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REGISTER_FAILED,"event add failed");
        }
        return null;
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

    public RespBeanWithEventList(List<EventDTO> events)
    {
        super();
        this.events = events;
    }

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
