package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.model.*;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpservice.utils.Utils;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * 重要提示：此测试程序请勿删除，此测试文件测试覆盖率为100%
 * @last-check-in 李国鹏
 * @date 2020/7/21
 */
public class TrainAndProcessAndEventAndProjectTrainMapperTest extends BaseTest {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

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
        organizationMapper.removeAllOrganizationTest();
        trainMapper.removeAllTrain();
        projectMapper.removeAllTrainProjectsTest();
        projectTrainMapper.removeAllProjectTrain();
        processMapper.removeAllProcesses();
        processEventMapper.removeAllProcessEvents();
        eventMapper.removeAllEvents();

        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("THU");
        organizationDTO.setRealName("清华大学");
        organizationDTO.setDescription("北京市清华大学");
        organizationDTO.setWebsiteUrl("www.thu.wdu.cn");
        organizationService.add(organizationDTO);
        Organization organization = organizationMapper.findOrganizationByName("THU").get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(organization).length);

        Train train = new Train();
        train.setGmtCreate(new Date());
        train.setName("清华大学暑期实训");
        train.setOrganizationId(organization.getId());
        train.setStartTime(new Date());
        train.setEndTime(new Date());
        train.setContent("啊这");
        train.setAcceptStandard("啊这也");
        train.setResourceLibrary("{}");
        train.setGpsInfo("{}");
        trainMapper.addTrain(train);
        Train train1 = trainMapper.findAllTrain().get(0);
        Assertions.assertEquals(3, Utils.getNullPropertyNames(train1).length);
        train = trainMapper.findTrainById(train1.getId());
        Assertions.assertEquals(3, Utils.getNullPropertyNames(train).length);


        // 总共加了两个
        // 创建工程
        Project project = new Project();
        project.setName("cptmp");
        project.setContent("xxx");
        project.setGmtCreate(new Date());
        project.setLevel(1);
        project.setResourceLibrary("file/233");
        projectMapper.addTrainProject(project);
        project = projectMapper.findAllTrainProject().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(project).length);
        projectMapper.hideTrainProjectById(project.getId(), new Date());
        Assertions.assertEquals(0, projectMapper.findAllTrainProject().size());
        projectMapper.restoreTrainProjectById(project.getId());
        Assertions.assertEquals(1, projectMapper.findAllTrainProject().size());
        projectMapper.restoreAllTrainProjects();
        Assertions.assertEquals(1, projectMapper.findAllTrainProject().size());
        projectMapper.hideTrainProjectByName(project.getName(), new Date());
        projectMapper.restoreTrainProjectByName("cptmp");
        Assertions.assertEquals(1,projectMapper.findAllTrainProject().size());
        projectMapper.restoreAllTrainProjects();
        Assertions.assertEquals(1, projectMapper.findAllTrainProject().size());
        project.setName("aptmp");
        projectMapper.updateTrainProjectById(project);
        project = projectMapper.findAllTrainProject().get(0);
        Assertions.assertEquals("aptmp", project.getName());
        projectMapper.updateTrainProjectResourceById(project.getId(), new Date(), "233");
        project = projectMapper.findAllTrainProject().get(0);
        Assertions.assertEquals("233", project.getResourceLibrary());
        project = projectMapper.findTrainProjectById(project.getId());
        Assertions.assertEquals(1, Utils.getNullPropertyNames(project).length);
        Assertions.assertEquals(1, projectMapper.findTrainProjectByNameAmbiguously("p").size());


        ProjectTrain projectTrain = new ProjectTrain();
        projectTrain.setGmtCreate(new Date());
        projectTrain.setProjectId(project.getId());
        projectTrain.setTrainId(train1.getId());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        projectTrainMapper.removeProjectTrainsById(projectTrain.getId());
        Assertions.assertEquals(0, projectTrainMapper.findAllProjectTrains().size());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        projectTrainMapper.removeProjectTrainsByProjectId(projectTrain.getProjectId());
        Assertions.assertEquals(0, projectTrainMapper.findAllProjectTrains().size());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        projectTrainMapper.removeProjectTrainsByTrainId(projectTrain.getTrainId());
        Assertions.assertEquals(0, projectTrainMapper.findAllProjectTrains().size());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        projectTrainMapper.removeProjectTrainsByProjectIdAndTrainId(projectTrain.getProjectId(), projectTrain.getTrainId());
        Assertions.assertEquals(0, projectTrainMapper.findAllProjectTrains().size());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        projectTrain.setGmtModified(new Date());
        projectTrainMapper.updateProjectTrainById(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        Assertions.assertEquals(1, Utils.getNullPropertyNames(projectTrain).length);
        projectTrain = projectTrainMapper.findProjectTrainById(projectTrain.getId());
        Assertions.assertEquals(1, Utils.getNullPropertyNames(projectTrain).length);
        projectTrain = projectTrainMapper.findProjectTrainsByTrainId(projectTrain.getTrainId()).get(0);
        Assertions.assertEquals(1, Utils.getNullPropertyNames(projectTrain).length);
        projectTrain = projectTrainMapper.findProjectTrainsByProjectId(projectTrain.getProjectId()).get(0);
        Assertions.assertEquals(1, Utils.getNullPropertyNames(projectTrain).length);
        projectTrain = projectTrainMapper.findProjectTrainByProjectIdAndTrainId(projectTrain.getProjectId(), projectTrain.getTrainId());
        Assertions.assertEquals(1, Utils.getNullPropertyNames(projectTrain).length);


        trainMapper.addTrain(train);
        List<Train> trains = trainMapper.findTrainByNameAmbiguously("清华");
        Assertions.assertEquals(2, trains.size());
        Assertions.assertEquals(3, Utils.getNullPropertyNames(trains.get(0)).length);
        train.setName("北京大学暑期实训");
        trainMapper.updateTrainById(train);
        trains = trainMapper.findTrainByNameAmbiguously("清华");
        Assertions.assertEquals(1, trains.size());
        Train train2 = trainMapper.findTrainByOrganizationId(trains.get(0).getOrganizationId()).get(0);
        Assertions.assertEquals(3, Utils.getNullPropertyNames(train2).length);
        trainMapper.hideTrainById(trains.get(0).getId(), new Date());
        Assertions.assertEquals(1,  trainMapper.findAllTrain().size());
        trainMapper.restoreTrainById(trains.get(0).getId());
        Assertions.assertEquals(2,  trainMapper.findAllTrain().size());
        trainMapper.restoreAllTrain();
        BigInteger restoreTrainTest = trains.get(0).getOrganizationId();
        trainMapper.hideTrainsByOrganizationId(trains.get(0).getOrganizationId(), new Date());
        Assertions.assertEquals(0, trainMapper.findAllTrain().size());
        trainMapper.restoreTrainsByOrganizationId(trains.get(0).getOrganizationId());
        Assertions.assertEquals(2, trainMapper.findAllTrain().size());
        trainMapper.restoreAllTrain();
        Assertions.assertEquals(2, trainMapper.findAllTrain().size());


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


        BigInteger restoreTestId = processMapper.findAllProcesses().get(0).getId();
        processMapper.hideProcessById(new Date(),processMapper.findAllProcesses().get(0).getId());
        Assertions.assertEquals(1,processMapper.findAllProcesses().size());
        BigInteger restoreTestTrainId = processMapper.findAllProcesses().get(0).getTrainId();
        processMapper.hideProcessesByTrainId(new Date(),processMapper.findAllProcesses().get(0).getTrainId());
        Assertions.assertEquals(0,processMapper.findAllProcesses().size());
        processMapper.restoreProcessById(restoreTestId);
        Assertions.assertEquals(1,processMapper.findAllProcesses().size());
        processMapper.restoreProcessesByTrainId(restoreTestTrainId);
        Assertions.assertEquals(2,processMapper.findAllProcesses().size());

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
        processEventMapper.removeProcessEventById(processEvent.getId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        processEvent = processEventMapper.findAllProcessEvents().get(0);
        processEventMapper.removeProcessEventsByEventId(processEvent.getEventId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        processEvent = processEventMapper.findAllProcessEvents().get(0);
        processEventMapper.removeProcessEventsByProcessId(processEvent.getProcessId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        processEvent = processEventMapper.findAllProcessEvents().get(0);
        processEventMapper.removeProcessEventByProcessIdAndEventId(processEvent.getProcessId(), processEvent.getEventId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());


        event = events.get(0);
        event.setGmtModified(new Date());
        eventMapper.updateEventById(event);
        event = eventMapper.findEventById(event.getId());
        Assertions.assertEquals(1, Utils.getNullPropertyNames(event).length);

        //删除与恢复
        Assertions.assertEquals(2, eventMapper.findAllEvents().size());
        eventMapper.hideAllEvent(new Date());
        Assertions.assertEquals(0, eventMapper.findAllEvents().size());
        eventMapper.restoreAllEvent();
        Assertions.assertEquals(2, eventMapper.findAllEvents().size());

        BigInteger restoreTestId1 = eventMapper.findAllEvents().get(0).getId();
        eventMapper.hideEventById(eventMapper.findAllEvents().get(0).getId(),new Date());
        Assertions.assertEquals(1, eventMapper.findAllEvents().size());
        eventMapper.restoreEventById(restoreTestId1);
        Assertions.assertEquals(2, eventMapper.findAllEvents().size());
    }

}
