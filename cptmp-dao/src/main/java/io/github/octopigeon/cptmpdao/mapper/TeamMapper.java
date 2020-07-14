package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Team;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 2.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/14
 */
@Repository
@Mapper
public interface TeamMapper {

    /**
     * 添加团队
     * @param team 团队
     */
    String COLUMNS="gmt_create, gmt_modified, gmt_deleted, avatar,idx_project_id, idx_team_name, code_url, team_grade,evaluation";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{avatar},#{projectId}, #{name}, #{codeUrl}, #{teamGrade},#{evaluation}";
    String UPDATE_CONTENT="gmt_modified = #{gmtModified}, idx_project_id = #{projectId}, idx_team_name = #{name}, evaluation = #{evaluation}, " +
            " code_url = #{codeUrl},team_grade = #{teamGrade},avatar = #{avatar}";

    @Insert("insert into team (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTeam(Team team);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from team")
    void removeAllTeamTest();
    /**
     * @param gmtDeleted 删除时间
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeAllTeam(Date gmtDeleted);
    /**
     * 根据id删除对应的团队
     * @param id：团队id
     * @param gmtDeleted 删除日期
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeTeamById(BigInteger id,Date gmtDeleted);

    /**
     * 根据团队名称删除团队
     * @param name 团队名称
     * @param gmtDeleted 删除日期
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where idx_team_name = #{name} and gmt_deleted is null")
    void removeTeamByName(String name,Date gmtDeleted);

    /**
     * 根据团队id修改
     */
    @Update("update team set "+ UPDATE_CONTENT+"  where id = #{id} and gmt_deleted is null")
    void updateTeamById(Team team);

    /**
     * 修改代码库
     * @param id 团队id
     * @param gmtModified 删除日期
     * @param codeUrl 代码库
     */
    @Update("update team set gmt_deleted = #{gmtDeleted}, code_url = #{codeUrl} where id = #{id} and gmt_deleted is null")
    void updateTeamCodeUrlById(BigInteger id, Date gmtModified,String codeUrl);
    /**
     * 根据团队名修改
     */
    @Update("update team set "+ UPDATE_CONTENT+"  where idx_team_name = #{name} and gmt_deleted is null")
    void updateTeamByName(Team team);

    /**
     * 修改代码库
     * @param name 团队名称
     * @param gmtModified 删除日期
     * @param codeUrl 代码库
     */
    @Update("update team set gmt_deleted = #{gmtDeleted}, code_url = #{codeUrl} where idx_team_name = #{name} and gmt_deleted is null")
    void updateTeamCodeUrlByName(String name, Date gmtModified,String codeUrl);


    /**
     * 查询所有团队
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where gmt_deleted is null")
    @Results(id = "team", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_project_id", property = "projectId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "code_url", property = "codeUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "evaluation", property = "evaluation", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.SMALLINT)

    })
    List<Team> findAllTeam();

    /**
     * 根据团队id查找
     * @param id：团队id
     * @return 团队
     */
    @Select("select id, " + COLUMNS + " from team where id = #{id} and gmt_deleted is null")
    @ResultMap("team")
    Team findTeamByTeamId(BigInteger id);

    /**
     * 根据团队名查找项目
     * @param name：团队名称
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where team_name = #{name} and gmt_deleted is null")
    @ResultMap("team")
    Team findTeamByName(String name);
}
