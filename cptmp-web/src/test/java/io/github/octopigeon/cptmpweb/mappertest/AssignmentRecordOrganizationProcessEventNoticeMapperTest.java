package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.model.*;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpservice.utils.Utils;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/14
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/19
 */
public class AssignmentRecordOrganizationProcessEventNoticeMapperTest extends BaseTest {

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

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;


    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private RecordMapper recordMapper;


    @Autowired
    private UserInfoService userInfoService;


    @Test
    public void test() throws Exception {


        // 创建作业表
        Assignment assignment = new Assignment();
        assignment.setGmtCreate(new Date());
        assignment.setFile(false);
        assignment.setDocumentPath("test1");
        assignment.setTitle("test1");
        assignment.setContent("test1");
        //添加作业
        assignmentMapper.removeAllAssignmentTest();
        assignmentMapper.addAssignment(assignment);
        assignmentMapper.addAssignment(assignment);
        Assertions.assertEquals(2, assignmentMapper.findAllAssignment().size());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(assignmentMapper.findAllAssignment().get(0)).length);
        //删除作业
        assignmentMapper.hideAssignmentById(assignmentMapper.findAllAssignment().get(0).getId(), new Date());
        Assertions.assertEquals(1, assignmentMapper.findAllAssignment().size());

        //更新作业
        Assignment assignment3 = assignmentMapper.findAllAssignment().get(0);
        assignment3.setContent("test3");
        assignmentMapper.updateAssignmentById(assignment3);
        Assertions.assertEquals("test3", assignmentMapper.findAllAssignment().get(0).getContent());
        assignmentMapper.updateAssignmentDocumentById(assignmentMapper.findAllAssignment().get(0).getId(), new Date(), "test3");
        Assertions.assertEquals("test3", assignmentMapper.findAllAssignment().get(0).getDocumentPath());
        //移除所有作业
        assignmentMapper.hideAllAssignment(new Date());
        Assertions.assertEquals(0, assignmentMapper.findAllAssignment().size());
        //添加作业，为以后测试做准备
        assignmentMapper.restoreAllAssignment();
        Assertions.assertEquals(2, assignmentMapper.findAllAssignment().size());


        //组织
        organizationMapper.removeAllOrganizationTest();
        Organization organization = new Organization();
        organization.setGmtCreate(new Date());
        organization.setRealName("test1");
        organization.setName("test1");
        organization.setInvitationCode("test1");
        //添加组织
        organizationMapper.addOrganization(organization);
        organization.setRealName("test2");
        organization.setName("test2");
        organization.setInvitationCode("test2");
        organizationMapper.addOrganization(organization);
        Assertions.assertEquals(2, organizationMapper.findAllOrganization().size());
        Assertions.assertEquals(4, Utils.getNullPropertyNames(organizationMapper.findAllOrganization().get(0)).length);
        //根据id修改组织
        Organization organization3 = organizationMapper.findAllOrganization().get(1);
        organization3.setName("test3");
        organizationMapper.updateOrganizationById(organization3);
        Assertions.assertEquals("test3", organizationMapper.findAllOrganization().get(1).getName());
        //删除组织
        organizationMapper.hideAllOrganization(new Date());
        Assertions.assertEquals(0, organizationMapper.findAllOrganization().size());
        organizationMapper.restoreAllOrganization();
        Assertions.assertEquals(2, organizationMapper.findAllOrganization().size());
        BigInteger restoreTestId = organizationMapper.findAllOrganization().get(1).getId();
        organizationMapper.hideOrganizationById(organizationMapper.findAllOrganization().get(1).getId(), new Date());
        Assertions.assertEquals(1, organizationMapper.findAllOrganization().size());
        organizationMapper.restoreOrganizationById(restoreTestId);
        Assertions.assertEquals(2, organizationMapper.findAllOrganization().size());
        //查询(find 与 get 操作对象不同，为了测试)
        Assertions.assertEquals("test1", organizationMapper.findOrganizationByRealName("test1").get(0).getInvitationCode());
        Assertions.assertEquals("test1", organizationMapper.findOrganizationByInvitationCode("test1").getName());
        Assertions.assertEquals("test1", organizationMapper.findOrganizationByName("test1").get(0).getRealName());

        //用户
        // 创建学校
        cptmpUserMapper.removeAllUsersTest();
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("WHU");
        organizationDTO.setRealName("武汉大学");
        organizationDTO.setDescription("湖北省武汉市武汉大学");
        organizationDTO.setWebsiteUrl("www.whu.edu.cn");
        organizationService.add(organizationDTO);
        organizationDTO = organizationService.findByName(1,1,"WHU").getList().get(0);

        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
        baseUserInfoDTO.setUsername("WHU-2018302060342");
        baseUserInfoDTO.setCommonId("2018302060342");
        baseUserInfoDTO.setRoleName(CptmpRole.ROLE_SCHOOL_TEACHER);
        baseUserInfoDTO.setName("魏啸冲");
        baseUserInfoDTO.setPassword("123");
        baseUserInfoDTO.setEmail("wxcnb@qq.com");
        baseUserInfoDTO.setOrganizationId(organizationDTO.getId());
        userInfoService.add(baseUserInfoDTO);
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername("WHU-2018302060342");

        //项目
        projectMapper.removeAllTrainProjectsTest();
        Project project = new Project();
        project.setGmtCreate(new Date());
        project.setLevel(2);
        project.setContent("test1");
        project.setResourceLibrary("test1");
        project.setName("test1");
        projectMapper.addTrainProject(project);
        projectMapper.addTrainProject(project);
        Assertions.assertEquals(2, projectMapper.findAllTrainProject().size());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(projectMapper.findAllTrainProject().get(0)).length);

        //实训
        trainMapper.removeAllTrain();
        Train train = new Train();
        train.setGmtCreate(new Date());
        train.setStartTime(new Date());
        train.setEndTime(new Date());
        train.setName("test1");
        train.setOrganizationId(organization.getId());
        train.setGpsInfo("test1");
        train.setContent("test1");
        train.setAcceptStandard("test1");
        train.setResourceLibrary("test1");
        trainMapper.addTrain(train);
        trainMapper.addTrain(train);
        Assertions.assertEquals(2, trainMapper.findAllTrain().size());
        Assertions.assertEquals(3, Utils.getNullPropertyNames(trainMapper.findAllTrain().get(0)).length);

        projectTrainMapper.removeAllProjectTrain();
        ProjectTrain projectTrain = new ProjectTrain();
        projectTrain.setGmtCreate(new Date());
        projectTrain.setProjectId(project.getId());
        projectTrain.setTrainId(train.getId());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrainMapper.addProjectTrain(projectTrain);
        Assertions.assertEquals(2, projectTrainMapper.findAllProjectTrains().size());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(projectTrainMapper.findAllProjectTrains().get(0)).length);


        //团队
        teamMapper.removeAllTeamTest();
        Team team = new Team();
        team.setGmtCreate(new Date());
        team.setProjectTrainId(projectTrain.getId());
        team.setName("test1");
        team.setTeamGrade(1);
        team.setTeamMasterId(cptmpUser.getId());
        teamMapper.addTeam(team);
        team.setProjectTrainId(projectTrainMapper.findAllProjectTrains().get(0).getId());
        teamMapper.addTeam(team);
        Assertions.assertEquals(2, teamMapper.findAllTeam().size());
        Assertions.assertEquals(7, Utils.getNullPropertyNames(teamMapper.findAllTeam().get(0)).length);

        //信息
        noticeMapper.removeAllNoticeTest();
        Notice notice1 =new Notice();
        notice1.setGmtCreate(new Date());
        notice1.setReceiverId(cptmpUser.getId());
        notice1.setIsRead(false);
        notice1.setContent("test1");
        notice1.setNoticeType("test1");
        //增加
        noticeMapper.addNotice(notice1);
        noticeMapper.addNotice(notice1);
        Assertions.assertEquals(2,noticeMapper.findAllNotice().size());
        Assertions.assertEquals(4, Utils.getNullPropertyNames(noticeMapper.findAllNotice().get(0)).length);
        //删除,恢复
        BigInteger restoreNoticeId = noticeMapper.findAllNotice().get(0).getId();
        noticeMapper.hideNoticeById(noticeMapper.findAllNotice().get(0).getId(),new Date());
        Assertions.assertEquals(1,noticeMapper.findAllNotice().size());
        noticeMapper.restoreNoticeById(restoreNoticeId);
        Assertions.assertEquals(2,noticeMapper.findAllNotice().size());
        //修改
        notice1 = noticeMapper.findAllNotice().get(0);
        notice1.setContent("test2");
        noticeMapper.updateNoticeById(notice1);
        Assertions.assertEquals("test2",noticeMapper.findNoticeById(noticeMapper.findAllNotice().get(0).getId()).getContent());
        //整体删除恢复
        noticeMapper.hideNoticeByAll(new Date());
        Assertions.assertEquals(0,noticeMapper.findAllNotice().size());
        noticeMapper.restoreNoticeByAll();
        Assertions.assertEquals(2,noticeMapper.findAllNotice().size());

        //流程
        processMapper.removeAllProcesses();
        Process process = new Process();
        process.setGmtCreate(new Date());
        process.setTrainId(train.getId());
        process.setStartTime(new Date());
        process.setEndTime(new Date());
        processMapper.addProcess(process);
        processMapper.addProcess(process);
        List<Process> processes = processMapper.findAllProcesses();
        //事件
        eventMapper.removeAllEvents();
        Event event = new Event();
        event.setGmtCreate(new Date());
        event.setStartTime(new Date());
        event.setEndTime(new Date());
        event.setContent("test1");
        event.setPersonOrTeam(true);
        eventMapper.addEvent(event);
        eventMapper.addEvent(event);

        //流程事件
        processEventMapper.removeAllProcessEvents();
        ProcessEvent processEvent = new ProcessEvent();
        processEvent.setGmtCreate(new Date());
        processEvent.setProcessId(process.getId());
        processEvent.setEventId(event.getId());
        //增加流程事件
        processEventMapper.addProcessEvent(processEvent);
        processEventMapper.addProcessEvent(processEvent);
        Assertions.assertEquals(2, processEventMapper.findAllProcessEvents().size());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(processEventMapper.findAllProcessEvents().get(0)).length);

        //删除
        processEventMapper.removeProcessEventById(processEventMapper.findAllProcessEvents().get(1).getId());
        Assertions.assertEquals(1, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        Assertions.assertEquals(2, processEventMapper.findAllProcessEvents().size());
        processEventMapper.removeProcessEventsByProcessId(process.getId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        Assertions.assertEquals(1, processEventMapper.findAllProcessEvents().size());
        processEventMapper.removeProcessEventsByEventId(event.getId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        Assertions.assertEquals(1, processEventMapper.findAllProcessEvents().size());
        processEventMapper.removeProcessEventByProcessIdAndEventId(process.getId(), event.getId());
        Assertions.assertEquals(0, processEventMapper.findAllProcessEvents().size());
        processEventMapper.addProcessEvent(processEvent);
        processEventMapper.addProcessEvent(processEvent);
        Assertions.assertEquals(2, processEventMapper.findAllProcessEvents().size());

        //修改流程事件
        processEvent.setProcessId(processMapper.findAllProcesses().get(1).getId());
        processEventMapper.updateProcessEventById(processEvent);
        Assertions.assertEquals(processMapper.findAllProcesses().get(1).getId(), processEventMapper.findAllProcessEvents().get(0).getProcessId());


        //活动记录
        recordMapper.removeAllRecordTest();
        Record record1 = new Record();
        record1.setGmtCreate(new Date());
        record1.setUserId(cptmpUserMapper.findAllUsers().get(0).getId());
        record1.setTeamId(teamMapper.findAllTeam().get(0).getId());
        record1.setProcessEventId(processEventMapper.findAllProcessEvents().get(0).getId());
        record1.setTrainId(trainMapper.findAllTrain().get(0).getId());
        record1.setAssignmentsLib("test1");

        //添加活动记录
        recordMapper.addRecord(record1);
        recordMapper.addRecord(record1);
        Assertions.assertEquals(2, recordMapper.findAllRecord().size());

        //查询
        Record record3 = record1;
        record3.setTeamId(teamMapper.findAllTeam().get(1).getId());
        recordMapper.updateRecordById(record3);
        Assertions.assertEquals(teamMapper.findAllTeam().get(1).getId(), recordMapper.findRecordByTeamId(teamMapper.findAllTeam().get(1).getId()).get(0).getTeamId());

        BigInteger restoreRecordTestId = recordMapper.findAllRecord().get(0).getId();
        recordMapper.hideRecordById(recordMapper.findAllRecord().get(0).getId(), new Date());
        Assertions.assertEquals(1, recordMapper.findAllRecord().size());
        recordMapper.restoreRecordById(restoreRecordTestId);
        Assertions.assertEquals(2, recordMapper.findAllRecord().size());

        Record record = recordMapper.findAllRecord().get(0);
        Assertions.assertEquals(2, recordMapper.findRecordByUserId(record.getTrainId(), record.getUserId()).size());

        recordMapper.hideRecordByAll(new Date());
        Assertions.assertEquals(0, recordMapper.findAllRecord().size());
        recordMapper.restoreRecordByAll();
        Assertions.assertEquals(2, recordMapper.findAllRecord().size());
    }
}
