package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.StudentTeamMapper;
import io.github.octopigeon.cptmpdao.model.StudentTeam;
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
 * @last-check-in 陈若琳
 * @date 2020/07/12
 */
public class StudentTeamMapperTest extends BaseTest {

    @Autowired
    StudentTeamMapper studentTeamMapper;

    @Test
    public void Test()
    {
        StudentTeam studentTeam1 = new StudentTeam();
        studentTeam1.setGmtCreate(new Date());
        studentTeam1.setTeamId(BigInteger.valueOf(1));
        studentTeam1.setUserId(BigInteger.valueOf(1));
        studentTeam1.setStudentGrade(BigDecimal.valueOf(1));

        StudentTeam studentTeam2 = new StudentTeam();
        studentTeam2.setGmtCreate(new Date());
        studentTeam2.setTeamId(BigInteger.valueOf(2));
        studentTeam2.setUserId(BigInteger.valueOf(2));
        studentTeam2.setStudentGrade(BigDecimal.valueOf(2));

        studentTeamMapper.removeActivityRecordByAllTest(new Date());
        studentTeamMapper.addStudentTeam(studentTeam1);
        studentTeamMapper.addStudentTeam(studentTeam2);

        Assertions.assertEquals(2,studentTeamMapper.findAllStudentTeam().size());

        studentTeamMapper.removeStudentTeamById(studentTeamMapper.findAllStudentTeam().get(0).getId(),new Date());
        Assertions.assertEquals(1,studentTeamMapper.findAllStudentTeam().size());

        studentTeamMapper.updateGradeById(BigInteger.valueOf(1),BigInteger.valueOf(1),new Date(),BigInteger.valueOf(1),BigDecimal.valueOf(1));
        Assertions.assertEquals(BigDecimal.valueOf(2),studentTeamMapper.findAllStudentTeam().get(0).getStudentGrade());




    }
}
