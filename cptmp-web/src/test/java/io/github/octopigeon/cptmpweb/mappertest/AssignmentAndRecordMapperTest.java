package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.*;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
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

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/14
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/14
 */
public class AssignmentAndRecordMapperTest extends BaseTest {

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private RecordMapper recordMapper;


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationService organizationService;

    @Test
    public void test() throws Exception {
        // 创建作业表
        Assignment assignment = new Assignment();
        assignment.setGmtCreate(new Date());
        assignment.setFile(false);
        assignment.setDocumentPath("test1");
        assignment.setTitle("test1");
        assignment.setContent("test1");
        /**
         * 添加
         */
        assignmentMapper.removeAllAssignmentTest();
        assignmentMapper.addAssignment(assignment);
        assignmentMapper.addAssignment(assignment);
        Assertions.assertEquals(2, assignmentMapper.findAllAssignment().size());
        Assertions.assertEquals(2,Utils.getNullPropertyNames(assignmentMapper.findAllAssignment().get(0)).length);
        /**
         * 删除
         */
        assignmentMapper.removeAssignmentById(assignmentMapper.findAllAssignment().get(0).getId(),new Date());
        Assertions.assertEquals(1, assignmentMapper.findAllAssignment().size());

        /**
         * 更新
         */
        Assignment assignment3 = assignmentMapper.findAllAssignment().get(0);
        assignment3.setContent("test3");
        assignmentMapper.updateAssignmentById(assignment3);
        Assertions.assertEquals("test3", assignmentMapper.findAllAssignment().get(0).getContent());

        assignmentMapper.removeAllAssignment(new Date());
        Assertions.assertEquals(0, assignmentMapper.findAllAssignment().size());

        //实训
        trainMapper.removeAllTrain();
        Train train =new Train();
        train.setGmtCreate(new Date());
        train.setStartTime(new Date());
        train.setEndTime(new Date());
        train.setName("test1");
        train.setOrganizationId(BigInteger.valueOf(1));
        train.setGpsInfo("test1");
        train.setContent("test1");
        train.setAcceptStandard("test1");
        train.setResourceLibrary("test1");
        trainMapper.addTrain(train);
        trainMapper.addTrain(train);
        Assertions.assertEquals(2,trainMapper.findAllTrain().size());
        Assertions.assertEquals(2,Utils.getNullPropertyNames(trainMapper.findAllTrain().get(0)).length);

        //团队
        teamMapper.removeAllTeamTest();
        Team team = new Team();
        team.setGmtCreate(new Date());
        team.setProjectId(BigInteger.valueOf(1));
        team.setName("test1");
        team.setTeamGrade(1);
        teamMapper.addTeam(team);
        teamMapper.addTeam(team);
        Assertions.assertEquals(2,teamMapper.findAllTeam().size());
        Assertions.assertEquals(5,Utils.getNullPropertyNames(teamMapper.findAllTeam().get(0)).length);

        //用户
        // 创建学校
        organizationMapper.removeAllOrganizationTest();
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("WHU");
        organizationDTO.setRealName("武汉大学");
        organizationDTO.setDescription("湖北省武汉市武汉大学");
        organizationDTO.setWebsiteUrl("www.whu.edu.cn");
        organizationService.add(organizationDTO);
        organizationDTO = organizationService.findByName("WHU");

        cptmpUserMapper.removeAllUsersTest();
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

        //流程事件
        processEventMapper.removeAllProcessEvents();
        ProcessEvent processEvent =new ProcessEvent();
        processEvent.setGmtCreate(new Date());
        processEvent.setProcessId(BigInteger.valueOf(1));
        processEvent.setEventId(BigInteger.valueOf(1));
        processEventMapper.addProcessEvent(processEvent);
        processEventMapper.addProcessEvent(processEvent);
        Assertions.assertEquals(2,processEventMapper.findAllProcessEvents().size());
        Assertions.assertEquals(2,Utils.getNullPropertyNames(processEventMapper.findAllProcessEvents().get(0)).length);



        //活动记录
        Record record1 =new Record();
        record1.setGmtCreate(new Date());
        record1.setUserId(BigInteger.valueOf(1));
        record1.setTeamId(BigInteger.valueOf(1));
        record1.setProcessEventId(BigInteger.valueOf(1));
        record1.setTrainId(BigInteger.valueOf(1));
        record1.setAssignmentId(BigInteger.valueOf(1));



        /**
         * 添加
         */
        recordMapper.removeAllRecordTest();
        recordMapper.addRecord(record1);
        recordMapper.addRecord(record1);
        Assertions.assertEquals(2, recordMapper.findAllRecord().size());
//
//        /**
//         * 查询
//         */
//        Record record3=record1;
//        record3.setTrainId(BigInteger.valueOf(1));
//        recordMapper.updateRecordById(record3);
//        Assertions.assertEquals(BigInteger.valueOf(1), recordMapper.findRecordByTeamId(BigInteger.valueOf(2)).get(0).getTrainId());
//
//        recordMapper.removeRecordById(recordMapper.findAllRecord().get(0).getId(),new Date());
//        Assertions.assertEquals(1, recordMapper.findAllRecord().size());
//
//        recordMapper.removeRecordByAll(new Date());
//        Assertions.assertEquals(0, recordMapper.findAllRecord().size());

    }
}
