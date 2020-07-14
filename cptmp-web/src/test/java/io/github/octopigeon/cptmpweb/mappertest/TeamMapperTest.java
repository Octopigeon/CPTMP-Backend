package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.TeamMapper;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.2
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/10
 */
public class TeamMapperTest extends BaseTest {
    @Autowired
    private TeamMapper teamMapper;
    @Test
    public void test(){
        /**
         * 设置数据
         */
        Team team1 =new Team();
        team1.setGmtCreate(new Date());
        team1.setTeamName("test1");
        team1.setCodeBaseUrl("test1");
        team1.setMasterUserId(BigInteger.valueOf(1));
        team1.setPmUserId(BigInteger.valueOf(1));
        team1.setPoUserId(BigInteger.valueOf(1));
        team1.setTrainProjectId(BigInteger.valueOf(1));
        team1.setTeamGrade(BigDecimal.valueOf(100.0));

        Team team2 =new Team();
        team2.setGmtCreate(new Date());
        team2.setTeamName("test2");
        team2.setCodeBaseUrl("test2");
        team2.setMasterUserId(BigInteger.valueOf(2));
        team2.setPmUserId(BigInteger.valueOf(2));
        team2.setPoUserId(BigInteger.valueOf(2));
        team2.setTrainProjectId(BigInteger.valueOf(2));
        team2.setTeamGrade(BigDecimal.valueOf(90.0));


        /**
         * 添加
         */
        teamMapper.removeAllTrainTeamTest();
        teamMapper.addTrainTeam(team1);
        teamMapper.addTrainTeam(team2);
        Assertions.assertEquals(2, teamMapper.findAllTrainTeam().size());

        /**
         * 删除
         */
        teamMapper.removeTrainTeamByTeamName("test1",new Date());
        Assertions.assertEquals(1, teamMapper.findAllTrainTeam().size());

        /**
         * 更新
         */
        Team team3 = teamMapper.findAllTrainTeam().get(0);
        team3.setCodeBaseUrl("test3");
        teamMapper.updateTrainTeamByTeamName(team3);
        Assertions.assertEquals("test3", teamMapper.findAllTrainTeam().get(0).getCodeBaseUrl());

        teamMapper.removeAllTrainTeam(new Date());
        Assertions.assertEquals(0, teamMapper.findAllTrainTeam().size());

    }
}
