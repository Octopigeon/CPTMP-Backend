package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpdao.model.SchoolStudent;
import io.github.octopigeon.cptmpdao.model.SchoolStudent;
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
public class SchoolStudentMapperTest extends BaseTest {
    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    @Test
    public void test()
    {
        initData();
        List<SchoolStudent> students;

        students = schoolStudentMapper.findAllSchoolStudents();
        Assertions.assertEquals(2, students.size());

        students = schoolStudentMapper.findSchoolStudentsBySchool("武汉大学");
        Assertions.assertEquals(1, students.size());

        SchoolStudent student = schoolStudentMapper.findSchoolStudentByUserId(new BigInteger(String.valueOf(2)));
        student.setSchoolName("武汉大学");
        schoolStudentMapper.updateSchoolStudentByUserId(student.getUserId(), new Date(), student.getName(), student.getStudentId(), student.getSchoolName(), student.getStudentFace());

        students = schoolStudentMapper.findSchoolStudentsBySchool("武汉大学");
        Assertions.assertEquals(2, students.size());

        schoolStudentMapper.removeSchoolStudentByUserId(new BigInteger(String.valueOf(2)));

        students = schoolStudentMapper.findAllSchoolStudents();
        Assertions.assertEquals(1, students.size());
    }

    /**
     * 假设user表中已经存在id为1和2的用户
     */
    @Test
    public void initData()
    {
        SchoolStudent student1 = new SchoolStudent();
        student1.setGmtCreate(new Date());
        student1.setUserId(new BigInteger(String.valueOf(1)));
        student1.setName("lisa");
        student1.setSchoolName("武汉大学");
        student1.setStudentId(new BigInteger(String.valueOf(12345)));

        SchoolStudent student2 = new SchoolStudent();
        student2.setGmtCreate(new Date());
        student2.setUserId(new BigInteger(String.valueOf(2)));
        student2.setName("mary");
        student2.setSchoolName("长江大学");
        student2.setStudentId(new BigInteger(String.valueOf(123445)));

        schoolStudentMapper.addSchoolStudent(student1);
        schoolStudentMapper.addSchoolStudent(student2);
    }
}
