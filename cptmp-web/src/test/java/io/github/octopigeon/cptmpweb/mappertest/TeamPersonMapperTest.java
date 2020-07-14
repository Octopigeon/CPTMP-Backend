package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 李国鹏
 * @date 2020/07/12
 */
public class TeamPersonMapperTest extends BaseTest {

    @Autowired
    TeamPersonMapper teamPersonMapper;

//    @Test
//    public void Test()
//    {
//        /**
//         * 设置数据
//         */
//        TeamPerson teamPerson1 = new TeamPerson();
//        teamPerson1.setGmtCreate(new Date());
//        teamPerson1.setTeamId(BigInteger.valueOf(1));
//        teamPerson1.setUserId(BigInteger.valueOf(1));
//        teamPerson1.setPersonalGrade(BigDecimal.valueOf(1));
//
//        TeamPerson teamPerson2 = new TeamPerson();
//        teamPerson2.setGmtCreate(new Date());
//        teamPerson2.setTeamId(BigInteger.valueOf(2));
//        teamPerson2.setUserId(BigInteger.valueOf(2));
//        teamPerson2.setPersonalGrade(BigDecimal.valueOf(2));
//
//        /**
//         * 添加
//         */
//        teamPersonMapper.removeActivityRecordByAllTest(new Date());
//        teamPersonMapper.addStudentTeam(teamPerson1);
//        teamPersonMapper.addStudentTeam(teamPerson2);
//
//        Assertions.assertEquals(2, teamPersonMapper.findAllStudentTeam().size());
//
//        /**
//         * 删除
//         */
//        teamPersonMapper.removeStudentTeamById(teamPersonMapper.findAllStudentTeam().get(0).getId(),new Date());
//        Assertions.assertEquals(1, teamPersonMapper.findAllStudentTeam().size());
//
//        /**
//         * 更新
//         */
//        teamPersonMapper.updateGradeById(BigInteger.valueOf(1),BigInteger.valueOf(1),new Date(),BigInteger.valueOf(1),BigDecimal.valueOf(1));
//        Assertions.assertEquals(BigDecimal.valueOf(2), teamPersonMapper.findAllStudentTeam().get(0).getPersonalGrade());
//
//
//
//
//    }
}
