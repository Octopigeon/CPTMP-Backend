package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.SchoolStudent;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/7
 */
@Repository
@Mapper
public interface SchoolStudentMapper {
    /**
     * 向表中插入一条数据
     * @param schoolStudent：类
     */
    @Insert("insert into school_student (gmt_create, idx_name, uk_user_id, idx_student_id, idx_school_name, uk_student_face) values (#{gmtCreate}, #{name}, #{userId}, #{studentId}, #{schoolName}, #{studentFace})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addSchoolStudent(SchoolStudent schoolStudent);

    /**
     * 根据userId删除对应的学生信息
     * @param userId：用户id
     */
    @Delete("delete from school_student where uk_user_id = #{userId}")
    void removeSchoolStudentByUserId(BigInteger userId);

    /**
     * 根据用户id更新数据
     * @param userId：用户id
     * @param gmtModified：更新时间
     * @param name：姓名
     * @param studentId：学号
     * @param schoolName:学校名称
     */
    @Update("update school_student set gmt_modified = #{gmtModified}, idx_name = #{name}, idx_student_id = #{studentId}, idx_school_name = #{schoolName} where uk_user_id = #{userId}")
    void updateSchoolStudetnByUserId(BigInteger userId, Date gmtModified, String name, String studentId, String schoolName);

    @Update("update school_student set gmt_modified = #{gmtModified}, uk_student_face = #{studentFace} where uk_user_id = #{userId}")
    void updateFaceInfoByUserId(BigInteger userId, Date gmtModified, String studentFace);

    /**
     * 查找所有的学生的账号、姓名、学号、所属学校
     * @return 学生列表
     */
    @Select("select uk_user_id, idx_name, idx_student_id, idx_school_name from school_student group by idx_school_name")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_student_id", property = "studentId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_school_name", property = "schoolName", jdbcType = JdbcType.VARCHAR)
    })
    List<SchoolStudent> findAllSchoolStudents();

    /**
     * 查找某个学校所有学生的账号、姓名、学号、所属学校
     * @return 学生列表
     */
    @Select("select uk_user_id, idx_name, idx_student_id, idx_school_name from school_student where idx_school_name = #{schoolName}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_student_id", property = "studentId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_school_name", property = "schoolName", jdbcType = JdbcType.VARCHAR)
    })
    List<SchoolStudent> findSchoolStudentsBySchool(String schoolName);

    /**
     * 根据userId查找学生的账号、姓名、学号、学校
     * @param userId：用户账号
     * @return 学生实例
     */
    @Select("select uk_user_id, idx_name, idx_student_id, idx_school_name from school_student where uk_user_id = #{userId}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_student_id", property = "studentId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_school_name", property = "schoolName", jdbcType = JdbcType.VARCHAR)
    })
    SchoolStudent findSchoolStudentByUserId(BigInteger userId);
}
