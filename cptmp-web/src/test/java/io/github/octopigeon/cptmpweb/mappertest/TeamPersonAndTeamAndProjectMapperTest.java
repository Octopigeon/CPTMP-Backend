package io.github.octopigeon.cptmpweb.mappertest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Project;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
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
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * 重要提示：此测试程序请勿删除，此测试文件测试覆盖率为100%
 * @last-check-in 魏啸冲
 * @date 2020/07/14
 */
public class TeamPersonAndTeamAndProjectMapperTest extends BaseTest {

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Test
    public void test() throws Exception {
        projectMapper.removeAllTrainProjectsTest();
        organizationMapper.removeAllOrganizationTest();
        cptmpUserMapper.removeAllUsersTest();
        projectMapper.removeAllTrainProjectsTest();
        trainMapper.removeAllTrain();
        teamMapper.removeAllTeamTest();
        teamPersonMapper.removeAllTeamPersonsTest();
        // 创建学校
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("WHU");
        organizationDTO.setRealName("武汉大学");
        organizationDTO.setDescription("湖北省武汉市武汉大学");
        organizationDTO.setWebsiteUrl("www.whu.edu.cn");
        organizationService.add(organizationDTO);
        organizationDTO = organizationService.findByName("WHU");

        // 创建用户
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

        // 创建实训
        Train train = new Train();
        train.setGmtCreate(new Date());
        train.setName("清华大学暑期实训");
        train.setOrganizationId(organizationDTO.getId());
        train.setStartTime(new Date());
        train.setEndTime(new Date());
        train.setContent("啊这");
        train.setAcceptStandard("啊这也");
        train.setResourceLibrary("{}");
        train.setGpsInfo("{}");
        trainMapper.addTrain(train);
        Train train1 = trainMapper.findAllTrain().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(train1).length);

        ProjectTrain projectTrain = new ProjectTrain();
        projectTrain.setGmtCreate(new Date());
        projectTrain.setTrainId(train1.getId());
        projectTrain.setProjectId(project.getId());
        projectTrainMapper.addProjectTrain(projectTrain);
        projectTrain = projectTrainMapper.findAllProjectTrains().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(projectTrain).length);

        // 创建队伍
        Team team = new Team();
        team.setGmtCreate(new Date());
        team.setRepoUrl("123456.github.io");
        team.setAvatar("abc.com");
        team.setEvaluation("good");
        team.setName("octopigeon");
        team.setTeamGrade(99);
        team.setProjectTrainId(projectTrain.getId());
        teamMapper.addTeam(team);
        team = teamMapper.findAllTeam().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(team).length);
        teamMapper.removeTeamById(team.getId(), new Date());
        Assertions.assertEquals(0, teamMapper.findAllTeam().size());
        teamMapper.restoreTeamById(team.getId());
        team.setName("111");
        teamMapper.updateTeamById(team);
        team = teamMapper.findAllTeam().get(0);
        Assertions.assertEquals("111", team.getName());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(teamMapper.findTeamByTeamId(team.getId())).length);
        Assertions.assertEquals(1, teamMapper.findTeamByName("111").size());


        // 创建队伍-用户关系
        TeamPerson teamPerson = new TeamPerson();
        teamPerson.setGmtCreate(new Date());
        teamPerson.setTeamId(team.getId());
        teamPerson.setUserId(cptmpUser.getId());
        teamPersonMapper.addTeamPerson(teamPerson);
        teamPerson = teamPersonMapper.findAllTeamPerson().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(teamPerson).length);

        teamPersonMapper.removeTeamPersonByTeamId(team.getId());
        Assertions.assertEquals(0, teamPersonMapper.findAllTeamPerson().size());
        teamPersonMapper.addTeamPerson(teamPerson);
        teamPersonMapper.removeTeamPersonByUserId(cptmpUser.getId());
        Assertions.assertEquals(0, teamPersonMapper.findAllTeamPerson().size());
        teamPersonMapper.addTeamPerson(teamPerson);
        teamPerson = teamPersonMapper.findAllTeamPerson().get(0);
        teamPersonMapper.removeTeamPersonById(teamPerson.getId());
        Assertions.assertEquals(0, teamPersonMapper.findAllTeamPerson().size());
        teamPersonMapper.addTeamPerson(teamPerson);

        teamPerson = teamPersonMapper.findTeamPersonByUserId(cptmpUser.getId());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(teamPerson).length);
        teamPerson = teamPersonMapper.findTeamPersonByTeamId(team.getId()).get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(teamPerson).length);

        BigInteger originTeamId = teamPerson.getTeamId();
        teamMapper.addTeam(team);
        teamMapper.addTeam(team);
        Team team1 = teamMapper.findAllTeam().get(1);
        Team team2 = teamMapper.findAllTeam().get(2);
        teamPerson.setTeamId(team1.getId());
        teamPersonMapper.updateTeamPersonById(teamPerson);
        teamPerson = teamPersonMapper.findAllTeamPerson().get(0);
        Assertions.assertNotEquals(originTeamId, teamPerson.getTeamId());
        originTeamId = teamPerson.getTeamId();
        teamPerson.setTeamId(team2.getId());
        teamPersonMapper.updateTeamPersonByTeamIdAndUserId(teamPerson);
        teamPerson = teamPersonMapper.findAllTeamPerson().get(0);
        Assertions.assertNotEquals(originTeamId, teamPerson.getTeamId());
    }

}
