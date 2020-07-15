package io.github.octopigeon.cptmpdao.mapper.relation;

import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
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
 * @last-check-in 魏啸冲
 * @date 2020/07/14
 */

@Repository
@Mapper
public interface TeamPersonMapper {

    String COLUMNS="gmt_create, gmt_modified, gmt_deleted, user_id, team_id";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted},#{userId}, #{teamId}";

    String UPDATE_CONTENT="gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified} , gmt_deleted = #{gmtDeleted}, " +
            "user_id = #{userId}, team_id = #{teamId}";

    // 关系表中均使用硬删除

    /**
     * 添加学生和团队的对应项
     * @param teamPerson
     */
    @Insert("insert into team_person (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTeamPerson(TeamPerson teamPerson);

    /**
     *  测试用删除
     */
    @Deprecated
    @Delete("delete from team_person")
    void removeAllTeamPersonsTest();

    /**
     * 根据id删除记录
     * @param id id
     */
    @Delete("delete from team_person where id = #{id}")
    void removeTeamPersonById(BigInteger id);

    /**
     * 删除一个队时，应当在此表中执行此操作
     * @param teamId
     */
    @Delete("delete from team_person where team_id = #{teamId}")
    void removeTeamPersonByTeamId(BigInteger teamId);

    /**
     * 某一个队删除某个队员时，执行此操作
     * @param userId
     */
    @Delete("delete from team_person where user_id = #{userId}")
    void removeTeamPersonByUserId(BigInteger userId);

    @Update("update team_person set " + UPDATE_CONTENT + " where id = #{id}")
    void updateTeamPersonById(TeamPerson teamPerson);

    /**
     * 根据用户id定位用户，一般修改用户所属队伍
     * @param teamPerson
     */
    @Update("update team_person set " + UPDATE_CONTENT + " where user_id = #{userId}")
    void updateTeamPersonByTeamIdAndUserId(TeamPerson teamPerson);

    /**
     * 查询所有对应关系
     * @return 对应关系列表
     */
    @Select("select id, " + COLUMNS + " from team_person")
    @Results(id = "team_person", value = {
            @Result(column = "team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE)

    })
    List<TeamPerson> findAllTeamPerson();

    /**
     * 根据团队id查询对应关系
     * @param teamId 团队id
     * @return 对应关系列表
     */
    @Select("select id, " + COLUMNS + " from team_person where team_id = #{teamId}")
    @ResultMap("team_person")
    List<TeamPerson> findTeamPersonByTeamId(BigInteger teamId);

    /**
     * 根据用户id查询对应关系
     * @param userId 用户id
     * @return 对应关系列表
     */
    @Select("select id, " + COLUMNS + " from team_person where user_id = #{userId}")
    @ResultMap("team_person")
    TeamPerson findTeamPersonByUserId(BigInteger userId);


}
