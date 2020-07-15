package io.github.octopigeon.cptmpweb.mappertest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Project;
import io.github.octopigeon.cptmpdao.model.Team;
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

import java.util.Date;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
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

    @Test
    public void test() throws Exception {
        // 创建学校
        organizationMapper.removeAllOrganizationTest();
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("WHU");
        organizationDTO.setRealName("武汉大学");
        organizationDTO.setDescription("湖北省武汉市武汉大学");
        organizationDTO.setWebsiteUrl("www.whu.edu.cn");
        organizationService.add(organizationDTO);
        organizationDTO = organizationService.findByName("WHU");

        // 创建用户
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

        // 创建工程
        projectMapper.removeAllTrainProjectsTest();
        Project project = new Project();
        project.setName("cptmp");
        project.setContent("xxx");
        project.setGmtCreate(new Date());
        project.setLevel(1);
        project.setResourceLibrary("file/233");
        projectMapper.addTrainProject(project);
        project = projectMapper.findAllTrainProject().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(project).length);

        // 创建队伍
        teamMapper.removeAllTeamTest();
        Team team = new Team();
        team.setGmtCreate(new Date());
        team.setRepoUrl("123456.github.io");
        team.setAvatar("abc.com");
        team.setEvaluation("good");
        team.setName("octopigeon");
        team.setTeamGrade(99);
        team.setProjectId(project.getId());
        teamMapper.addTeam(team);
        team = teamMapper.findAllTeam().get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(team).length);

        // 创建队伍-用户关系
        teamPersonMapper.removeAllTeamPersonsTest();
        TeamPerson teamPerson = new TeamPerson();
        teamPerson.setGmtCreate(new Date());
        teamPerson.setEvaluation("good");
        teamPerson.setPersonalGrade(96);
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
        teamPerson.setPersonalGrade(100);
        teamPersonMapper.updateTeamPersonById(teamPerson);
        teamPerson = teamPersonMapper.findAllTeamPerson().get(0);
        Assertions.assertEquals(100, teamPerson.getPersonalGrade());
        teamPerson.setPersonalGrade(0);
        teamPersonMapper.updateTeamPersonByTeamIdAndUserId(teamPerson);
        teamPerson = teamPersonMapper.findAllTeamPerson().get(0);
        Assertions.assertEquals(0, teamPerson.getPersonalGrade());

        teamPerson = teamPersonMapper.findTeamPersonByUserId(cptmpUser.getId());
        Assertions.assertEquals(2, Utils.getNullPropertyNames(teamPerson).length);
        teamPerson = teamPersonMapper.findTeamPersonByTeamId(team.getId()).get(0);
        Assertions.assertEquals(2, Utils.getNullPropertyNames(teamPerson).length);


    }

}
