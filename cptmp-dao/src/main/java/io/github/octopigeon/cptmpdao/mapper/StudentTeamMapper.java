package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.StudentTeam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 李国鹏
 * @date 2020/07/12
 */

@Repository
@Mapper
public interface StudentTeamMapper {

    String COLUMNS="gmt_create, gmt_modified, gmt_deleted,user_id,team_id,student_grade";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted},#{userId}, #{teamId},#{studentGrade}";

    String UPDATE_CONTENT="gmt_modified = #{gmtModified},user_id = #{userId},team_id = #{teamId},student_grade = #{studentGrade}";

    /**
     * 添加学生和团队的对应项
     * @param studentTeam
     */
    @Insert("insert into student_team (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addStudentTeam(StudentTeam studentTeam);

    /**
     *  删除
     */
    @Deprecated
    @Delete("delete from student_team")
    void removeActivityRecordByAllTest();

    /**
     *  删除
     * @param gmtDeleted 删除日期
     */
    @Update("update student_team set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeActivityRecordByAll(Date gmtDeleted);

    /**
     * 根据id删除记录
     * @param id id
     * @param gmtDeleted 删除时间
     */
    @Update("update student_team set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeStudentTeamById(BigInteger id, Date gmtDeleted);


    /**
     * 更新个人成绩
     */
    @Update("update student_team set "+UPDATE_CONTENT+" where id = #{id} and gmt_deleted is null")
    void updateGradeById(StudentTeam studentTeam);




    /**
     * 查询所有对应关系
     * @return 对应关系列表
     */
    @Select("select id, " + COLUMNS + " from student_team where gmt_deleted is null")
    @Results(id = "studentTeam", value = {
            @Result(column = "team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "student_grade", property = "studentGrade", jdbcType = JdbcType.DECIMAL)

    })
    List<StudentTeam> findAllStudentTeam();

    /**
     * 根据团队id查询对应关系
     * @param teamId 团队id
     * @return 对应关系列表
     */
    @Select("select id, " + COLUMNS + " from student_team where team_id = #{teamId} and gmt_deleted is null")
    @ResultMap("studentTeam")
    List<StudentTeam> findStudentTeamByTeamId(BigInteger teamId);

    /**
     * 根据用户id查询对应关系
     * @param userId 用户id
     * @return 对应关系列表
     */
    @Select("select id, " + COLUMNS + " from student_team where user_id = #{userId} and gmt_deleted is null")
    @ResultMap("studentTeam")
    List<StudentTeam> findStudentTeamByUserId(BigInteger userId);


}
