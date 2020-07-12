package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.StudentTeam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 陈若琳
 * @date 2020/07/12
 */

@Repository
@Mapper
public interface StudentTeamMapper {

    String COLUMNS="user_id,team_id,gmt_create";
    String PROPS="#{userId}, #{teamId},#{gmtCreate}";

    String UPDATE_CONTENT="user_id = #{userId},team_id = #{teamId},grade = #{grade}";

    /**
     * 添加学生和团队的对应项
     * @param studentTeam
     */
    @Insert("insert into student_team (" + COLUMNS + ") " +
            "values (" + PROPS + ")")
    void addStudentTeam(StudentTeam studentTeam);

    /**
     * 更新个人成绩
     * @param teamId 团队id
     * @param grade 个人成绩
     * @param userId 个人id
     * @param gmtModified 最后修改时间
     */
    @Update("update student_team set grade = #{grade},gmt_modified = #{gmtModified} where team_id = #{teamId} and user_id = #{userId}")
    void updateGradeById(BigInteger teamId,BigInteger userId,int grade,Date gmtModified);

    /**
     * 根据id删除记录
     * @param teamId 团队id
     * @param userId 学生id
     * @param isDelete 删除时间
     */
    @Update("update student_team set is_delete = #{isDelete} where team_id = #{teamId} and user_id = #{userId}")
    void DeleteStudentTeamById(BigInteger teamId,BigInteger userId,Date isDelete);

    /**
     * 根据id删除对应信息
     * @param teamId 团队id
     * @param userId 用户id
     */
    @Delete("delete from student_team where team_id = #{teamId} and user_id = #{userId}")
    void removeStudentTeamById(BigInteger teamId,BigInteger userId);


    /**
     * 查询所有对应关系
     * @return 对应关系列表
     */
    @Select("select " + COLUMNS + ",gmt_modified,grade from student_team where is_delete is null")
    @Results({
            @Result(column = "team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "grade", property = "grade", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT)
    })
    List<StudentTeam> findAllStudentTeam();

    /**
     * 根据团队id查询对应关系
     * @param teamId 团队id
     * @return 对应关系列表
     */
    @Select("select " + COLUMNS + ",gmt_modified,grade from student_team where team_id = #{teamId} and is_delete is null")
    @Results({
            @Result(column = "team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "grade", property = "grade", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT)
    })
    List<StudentTeam> findStudentTeamByTeamId(BigInteger teamId);

    /**
     * 根据用户id查询对应关系
     * @param userId 用户id
     * @return 对应关系列表
     */
    @Select("select " + COLUMNS + ",gmt_modified,grade from student_team where user_id = #{userId} and is_delete is null")
    @Results({
            @Result(column = "team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "grade", property = "grade", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT)
    })
    List<StudentTeam> findStudentTeamByUserId(BigInteger userId);


}
