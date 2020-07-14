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
        team1.setName("test1");
        team1.setCodeUrl("test1");
        team1.setAvatar("test1");
        team1.setEvaluation("test1");
        team1.setProjectId(BigInteger.valueOf(1));
        team1.setTeamGrade(100);

        Team team2 =new Team();
        team2.setGmtCreate(new Date());
        team2.setName("test2");
        team2.setCodeUrl("test2");
        team2.setAvatar("test2");
        team2.setEvaluation("test2");
        team2.setProjectId(BigInteger.valueOf(2));
        team2.setTeamGrade(90);


        /**
         * 添加
         */
        teamMapper.removeAllTeamTest();
        teamMapper.addTeam(team1);
        teamMapper.addTeam(team2);
        Assertions.assertEquals(2, teamMapper.findAllTeam().size());

        /**
         * 删除
         */
        teamMapper.removeTeamByName("test1",new Date());
        Assertions.assertEquals(1, teamMapper.findAllTeam().size());

        /**
         * 更新
         */
        Team team3 = teamMapper.findAllTeam().get(0);
        team3.setEvaluation("test3");
        teamMapper.updateTeamByName(team3);
        Assertions.assertEquals("test3", teamMapper.findAllTeam().get(0).getEvaluation());

        teamMapper.removeAllTeam(new Date());
        Assertions.assertEquals(0, teamMapper.findAllTeam().size());

    }
}
