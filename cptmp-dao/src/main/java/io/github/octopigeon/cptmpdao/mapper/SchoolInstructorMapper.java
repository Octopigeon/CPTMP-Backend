package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.SchoolInstructor;
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
public interface SchoolInstructorMapper {
    /**
     * 向表中插入一条数据
     * @param schoolInstructor：类
     */
    @Insert("insert into school_instructor (gmt_create, idx_name, uk_user_id, uk_employee_id, idx_school_name) values (#{gmtCreate}, #{name}, #{userId}, #{employeeId}, #{schoolName})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addSchoolInstructor(SchoolInstructor schoolInstructor);

    /**
     * 根据userId删除对应的指导老师信息
     * @param userId：用户id
     */
    @Delete("delete from school_instructor where uk_user_id = #{userId}")
    void removeSchoolInstructorByUserId(BigInteger userId);

    /**
     * 根据用户id更新数据
     * @param userId：用户id
     * @param gmtModified：更新时间
     * @param name：员工名称
     * @param employeeId：员工工号
     * @param schoolName:学校名称
     */
    @Update("update school_instructor set gmt_modified = #{gmtModified}, idx_name = #{name}, uk_employee_id = #{employeeId}, idx_school_name = #{schoolName} where uk_user_id = #{userId}")
    void updateSchoolInstructorByUserId(BigInteger userId, Date gmtModified, String name, BigInteger employeeId, String schoolName);

    /**
     * 查找所有的指导老师的账号、姓名、工号、所属学校
     * @return 指导老师列表
     */
    @Select("select uk_user_id, idx_name, uk_employee_id, idx_school_name from school_instructor group by idx_school_name")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_school_name", property = "schoolName", jdbcType = JdbcType.VARCHAR)
    })
    List<SchoolInstructor> findAllSchoolInstructors();

    /**
     * 查找某个学校所有的指导老师的账号、姓名、工号
     * @return 指导老师列表
     */
    @Select("select uk_user_id, idx_name, uk_employee_id, idx_school_name from school_instructor where idx_school_name = #{schoolName}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_school_name", property = "schoolName", jdbcType = JdbcType.VARCHAR)
    })
    List<SchoolInstructor> findSchoolInstructorsBySchool(String schoolName);

    /**
     * 根据userId查找指导老师的账号、姓名、工号、学校
     * @param userId：用户账号
     * @return 指导老师实例
     */
    @Select("select uk_user_id, idx_name, uk_employee_id, idx_school_name from school_instructor where uk_user_id = #{userId}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_school_name", property = "schoolName", jdbcType = JdbcType.VARCHAR)
    })
    SchoolInstructor findSchoolInstructorByUserId(BigInteger userId);
}
