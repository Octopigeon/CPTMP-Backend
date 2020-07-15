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
 * @last-check-in 魏啸冲
 * @date 2020/7/15
 */
@Repository
@Mapper
public interface PersonalGradeMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, uk_team_person_id, personal_grade, evaluation";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{teamPersonId}, #{personalGrade}, #{evaluation}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, uk_team_person_id = #{teamPersonId}, personal_grade = #{personalGrade}, " +
            "evaluation = #{evaluation}";
    String SOFT_DELETE_TAIL = "gmt_deleted is null";


    @Insert("insert into personal_grade (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void addPersonalGrade(PersonalGrade personalGrade);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from personal_grade")
    void removeAllPersonalGrades();

    @Update("update personal_grade set gmt_deleted = #{gmtDeleted} where id = #{id} and " + SOFT_DELETE_TAIL)
    void hidePersonalGradeById(BigInteger id, Date gmtDeleted);

    @Update("update personal_grade set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restorePersonalGradeById(BigInteger id);

    @Update("update personal_grade set gmt_deleted = #{gmtDeleted} where uk_team_person_id = #{teamPersonId} and " + SOFT_DELETE_TAIL)
    void hidePersonalGradeByTeamPersonId(BigInteger teamPersonId, Date gmtDeleted);

    @Update("update personal_grade set gmt_deleted = null where uk_team_person_id = #{teamPersonId} and and gmt_deleted is not null")
    void restorePersonalGradeByTeamPersonId(BigInteger teamPersonId);

    /**
     * 测试用
     */
    @Deprecated
    @Update("update personal_grade set gmt_deleted = null")
    void restoreAllPersonalGrade();

    @Update("update personal_grade set " + UPDATE_CONTENT + " where id = #{id} and " + SOFT_DELETE_TAIL)
    void updatePersonalGradeById(PersonalGrade personalGrade);

    @Update("update personal_grade set " + UPDATE_CONTENT + " where uk_team_person_id = #{teamPersonId} and " + SOFT_DELETE_TAIL)
    void updatePersonalGradeByTeamPersonId(PersonalGrade personalGrade);

    @Select("select id, " + COLUMNS + " from personal_grade where id = #{id} and " + SOFT_DELETE_TAIL)
    @Results(id = "personal_grade", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "uk_team_person_id", property = "teamPersonId", jdbcType = JdbcType.BIGINT),
            @Result(column = "personal_grade", property = "personalGrade", jdbcType = JdbcType.SMALLINT),
            @Result(column = "evaluation", property = "evaluation", jdbcType = JdbcType.VARCHAR)
    })
    PersonalGrade findPersonalGradeById(BigInteger id);

    @Select("select id, " + COLUMNS + " from personal_grade where uk_team_person_id = #{teamPersonId} and " + SOFT_DELETE_TAIL)
    @ResultMap("personal_grade")
    PersonalGrade findPersonalGradeByTeamPersonId(BigInteger teamPersonId);

    @Select("select id, " + COLUMNS + " from personal_grade where " + SOFT_DELETE_TAIL)
    @ResultMap("personal_grade")
    List<PersonalGrade> findAllPersonalGrades();

}
