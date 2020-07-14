package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.EventMapper;
import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.mapper.ProcessMapper;
import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.model.Event;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpservice.utils.Utils;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
public class TrainAndProcessAndEventMapperTest extends BaseTest {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private EventMapper eventMapper;

    @Test
    public void test() throws Exception {
        organizationMapper.removeAllOrganizationTest(new Date());
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("THU");
        organizationDTO.setRealName("清华大学");
        organizationDTO.setDescription("北京市清华大学");
        organizationDTO.setWebsiteUrl("www.thu.wdu.cn");
        organizationService.add(organizationDTO);
        Organization organization = organizationMapper.findOrganizationByName("THU");
        Assertions.assertEquals(2, Utils.getNullPropertyNames(organization).length);

        trainMapper.removeAllTrain();
        Train train = new Train();
        train.setGmtCreate(new Date());
        train.setName("清华大学暑期实训");
        train.setOrganizationId(organization.getId());
        train.setStartTime(new Date());
        train.setEndTime(new Date());
        train.setContent("啊这");
        train.setStandard("啊这也");
        train.setResourceLibrary("{}");
        train.setGpsInfo("{}");
        trainMapper.addTrain(train);
        Train train1 = trainMapper.findAllTrain().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(train1).length);
        train = trainMapper.findTrainById(train1.getId());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(train).length);
        // 总共加了两个
        trainMapper.addTrain(train);
        List<Train> trains = trainMapper.findTrainByNameAmbiguously("清华");
        Assertions.assertEquals(2, trains.size());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(trains.get(0)).length);
        train.setName("北京大学暑期实训");
        trainMapper.updateTrainById(train);
        trains = trainMapper.findTrainByNameAmbiguously("清华");
        Assertions.assertEquals(1, trains.size());

        processMapper.removeAllProcesses();
        Process process = new Process();
        process.setGmtCreate(new Date());
        process.setTrainId(train1.getId());
        process.setStartTime(new Date());
        process.setEndTime(new Date());
        processMapper.addProcess(process);
        processMapper.addProcess(process);
        List<Process> processes = processMapper.findAllProcesses();
        Process process1 = processes.get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(process1).length);
        Assertions.assertEquals(2, processes.size());
        process = processMapper.findProcessById(process1.getId());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(process).length);
        processes = processMapper.findProcessesByTrainId(train1.getId());
        Assertions.assertEquals(2, processes.size());
        process.setGmtModified(new Date());
        processMapper.updateProcessById(process);
        process = processMapper.findProcessById(process1.getId());
        Assertions.assertEquals(1, Utils.getNullPropertyNames(process).length);

        processEventMapper.removeAllProcessEvents();
        eventMapper.removeAllEvents();
        Event event = new Event();
        event.setGmtCreate(new Date());
        event.setStartTime(new Date());
        event.setEndTime(new Date());
        event.setContent("打卡");
        event.setPersonOrTeam(true);
        eventMapper.addEvent(event);
        event.setContent("交代码");
        eventMapper.addEvent(event);
        List<Event> events = eventMapper.findAllEvents();
        Assertions.assertEquals(2, events.size());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(events.get(0)).length);
        ProcessEvent processEvent = new ProcessEvent();
        processEvent.setEventId(events.get(0).getId());
        processEvent.setGmtCreate(new Date());
        processEvent.setProcessId(process1.getId());
        processEventMapper.addProcessEvent(processEvent);
        processEvent = processEventMapper.findProcessEventById(processEventMapper.findAllProcessEvents().get(0).getId());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(processEvent).length);
        processEvent = processEventMapper.findProcessEventsByEventId(events.get(0).getId()).get(0);
        Assertions.assertEquals(process1.getId(), processEvent.getProcessId());
        processEvent = processEventMapper.findProcessEventsByProcessId(process1.getId()).get(0);
        Assertions.assertEquals(events.get(0).getId(), processEvent.getEventId());
        processEvent = processEventMapper.findProcessEventByProcessIdAndEventId(process1.getId(), events.get(0).getId());
        Assertions.assertEquals(process1.getId(), processEvent.getProcessId());
        Assertions.assertEquals(events.get(0).getId(), processEvent.getEventId());
        processEvent.setEventId(events.get(1).getId());
        processEventMapper.updateProcessEventById(processEvent);
        processEvent = processEventMapper.findAllProcessEvents().get(0);
        Assertions.assertEquals(events.get(1).getId(), processEvent.getEventId());

        event = events.get(0);
        event.setGmtModified(new Date());
        eventMapper.updateEventById(event);
        event = eventMapper.findEventById(event.getId());
        Assertions.assertEquals(1, Utils.getNullPropertyNames(event).length);
    }

}
