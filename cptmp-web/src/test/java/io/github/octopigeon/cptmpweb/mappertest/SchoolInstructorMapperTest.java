package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.SchoolInstructorMapper;
import io.github.octopigeon.cptmpdao.model.SchoolInstructor;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
public class SchoolInstructorMapperTest extends BaseTest {
    @Autowired
    private SchoolInstructorMapper schoolInstructorMapper;

    @Test
    public void test()
    {
        initData();
        List<SchoolInstructor> teachers;

        teachers = schoolInstructorMapper.findAllSchoolInstructors();
        Assertions.assertEquals(2, teachers.size());

        teachers = schoolInstructorMapper.findSchoolInstructorsBySchool("武汉大学");
        Assertions.assertEquals(1, teachers.size());

        SchoolInstructor teacher = schoolInstructorMapper.findSchoolInstructorByUserId(new BigInteger(String.valueOf(2)));
        teacher.setSchoolName("武汉大学");
        schoolInstructorMapper.updateSchoolInstructorByUserId(teacher.getUserId(), new Date(), teacher.getName(), teacher.getEmployeeId(), teacher.getSchoolName());

        teachers = schoolInstructorMapper.findSchoolInstructorsBySchool("武汉大学");
        Assertions.assertEquals(2, teachers.size());

        schoolInstructorMapper.removeSchoolInstructorByUserId(new BigInteger(String.valueOf(2)));

        teachers = schoolInstructorMapper.findAllSchoolInstructors();
        Assertions.assertEquals(1, teachers.size());
    }

    /**
     * 假设user表中已经存在id为1和2的用户
     */
    @Test
    public void initData()
    {
        SchoolInstructor teacher1 = new SchoolInstructor();
        teacher1.setGmtCreate(new Date());
        teacher1.setUserId(new BigInteger(String.valueOf(1)));
        teacher1.setName("lisa");
        teacher1.setSchoolName("武汉大学");
        teacher1.setEmployeeId(new BigInteger(String.valueOf(12345)));

        SchoolInstructor teacher2 = new SchoolInstructor();
        teacher2.setGmtCreate(new Date());
        teacher2.setUserId(new BigInteger(String.valueOf(2)));
        teacher2.setName("mary");
        teacher2.setSchoolName("长江大学");
        teacher2.setEmployeeId(new BigInteger(String.valueOf(123445)));

        schoolInstructorMapper.addSchoolInstructor(teacher1);
        schoolInstructorMapper.addSchoolInstructor(teacher2);
    }
}
