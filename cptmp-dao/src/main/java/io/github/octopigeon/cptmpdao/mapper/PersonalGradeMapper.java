package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.PersonalGrade;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 李国鹏
 * @date 2020/7/21
 */
@Repository
@Mapper
public interface PersonalGradeMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, uk_team_person_id, evaluation, manage_pt, code_pt, tech_pt, framework_pt, communication_pt";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{teamPersonId}, #{evaluation}, #{managePt}, #{codePt}, #{techPt}, #{frameworkPt}, #{communicationPt}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, uk_team_person_id = #{teamPersonId}, evaluation = #{evaluation}, " +
            "manage_pt = #{managePt}, code_pt = #{codePt}, tech_pt = #{techPt}, framework_pt = #{frameworkPt}, communication_pt = #{communicationPt}";
    String SOFT_DELETE_TAIL = "gmt_deleted is null";


    /**
     * 插入个人成绩
     * @param personalGrade 个人成绩对象
     */
    @Insert("insert into personal_grade (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void addPersonalGrade(PersonalGrade personalGrade);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from personal_grade")
    void removeAllPersonalGrades();

    /**
     * 根据id软删除
     * @param id id
     * @param gmtDeleted 删除日期
     */
    @Update("update personal_grade set gmt_deleted = #{gmtDeleted} where id = #{id} and " + SOFT_DELETE_TAIL)
    void hidePersonalGradeById(BigInteger id, Date gmtDeleted);

    /**
     * 通过id恢复
     * @param id  id
     */
    @Update("update personal_grade set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restorePersonalGradeById(BigInteger id);

    /**
     * 通过团队成员id软删除
     * @param teamPersonId 团队成员id
     * @param gmtDeleted 删除日期
     */
    @Update("update personal_grade set gmt_deleted = #{gmtDeleted} where uk_team_person_id = #{teamPersonId} and " + SOFT_DELETE_TAIL)
    void hidePersonalGradeByTeamPersonId(BigInteger teamPersonId, Date gmtDeleted);

    /**
     * 通过团队成员id恢复
     * @param teamPersonId 团队成员id
     */
    @Update("update personal_grade set gmt_deleted = null where uk_team_person_id = #{teamPersonId} and gmt_deleted is not null")
    void restorePersonalGradeByTeamPersonId(BigInteger teamPersonId);

    /**
     * 测试用
     */
    @Deprecated
    @Update("update personal_grade set gmt_deleted = null")
    void restoreAllPersonalGrade();

    /**
     * 通过id修改
     * @param personalGrade 个人成绩id
     */
    @Update("update personal_grade set " + UPDATE_CONTENT + " where id = #{id} and " + SOFT_DELETE_TAIL)
    void updatePersonalGradeById(PersonalGrade personalGrade);

    /**
     * 通过团队成员id更新
     * @param personalGrade 个人成绩
     */
    @Update("update personal_grade set " + UPDATE_CONTENT + " where uk_team_person_id = #{teamPersonId} and " + SOFT_DELETE_TAIL)
    void updatePersonalGradeByTeamPersonId(PersonalGrade personalGrade);

    /**
     * 通过id查询
     * @param id id
     * @return 个人成绩对象
     */
    @Select("select id, " + COLUMNS + " from personal_grade where id = #{id} and " + SOFT_DELETE_TAIL)
    @Results(id = "personal_grade", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "uk_team_person_id", property = "teamPersonId", jdbcType = JdbcType.BIGINT),
            @Result(column = "evaluation", property = "evaluation", jdbcType = JdbcType.VARCHAR),
            @Result(column = "manage_pt", property = "managePt", jdbcType = JdbcType.SMALLINT),
            @Result(column = "code_pt", property = "codePt", jdbcType = JdbcType.SMALLINT),
            @Result(column = "tech_pt", property = "techPt", jdbcType = JdbcType.SMALLINT),
            @Result(column = "framework_pt", property = "frameworkPt", jdbcType = JdbcType.SMALLINT),
            @Result(column = "communication_pt", property = "communicationPt", jdbcType = JdbcType.SMALLINT)
    })
    PersonalGrade findPersonalGradeById(BigInteger id);

    /**
     * 通过团队成员id查询
     * @param teamPersonId 团队成员id
     * @return  个人成绩对象
     */
    @Select("select id, " + COLUMNS + " from personal_grade where uk_team_person_id = #{teamPersonId} and " + SOFT_DELETE_TAIL)
    @ResultMap("personal_grade")
    PersonalGrade findPersonalGradeByTeamPersonId(BigInteger teamPersonId);

    /**
     * 查询所有个人成绩
     * @return 个人成绩
     */
    @Select("select id, " + COLUMNS + " from personal_grade where " + SOFT_DELETE_TAIL)
    @ResultMap("personal_grade")
    List<PersonalGrade> findAllPersonalGrades();

}
