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
 * @date 2020/7/25
 */
@Repository
@Mapper
public interface TeamMapper {


    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, avatar, idx_project_train_id, idx_team_name, repo_url, team_grade,evaluation, idx_team_master_id, " +
            "github_username, github_token";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{avatar},#{projectTrainId}, #{name}, #{repoUrl}, #{teamGrade},#{evaluation}, #{teamMasterId}, " +
            "#{githubUsername}, #{githubToken}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, idx_project_train_id = #{projectTrainId}, idx_team_name = #{name}, evaluation = #{evaluation}, " +
            " repo_url = #{repoUrl},team_grade = #{teamGrade},avatar = #{avatar}, idx_team_master_id = #{teamMasterId}, github_username = #{githubUsername}, " +
            "github_token = #{githubToken}";

    /**
     * 添加团队
     *
     * @param team 团队
     */
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
     * 根据id删除对应的团队
     *
     * @param id：团队id
     * @param gmtDeleted 删除日期
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideTeamById(BigInteger id, Date gmtDeleted);


    /**
     * 根据id恢复
     * @param id id
     */
    @Update("update team set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreTeamById(BigInteger id);

    /**
     * 根据团队id修改
     * @param  team 团队
     */
    @Update("update team set " + UPDATE_CONTENT + "  where id = #{id} and gmt_deleted is null")
    void updateTeamById(Team team);

    /**
     * 根据团队id修改头像
     * @param teamId 团队id
     * @param gmtModified 修改时间
     * @param avatar 头像url
     */
    @Update("update team set avatar = #{avatar}, gmt_modified = #{gmtModified} where id = #{teamId} and gmt_deleted is null")
    void updateAvatarById(BigInteger teamId, Date gmtModified, String avatar);

    /**
     * 查询所有团队
     *
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where gmt_deleted is null")
    @Results(id = "team", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_project_train_id", property = "projectTrainId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "repo_url", property = "repoUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "evaluation", property = "evaluation", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.SMALLINT),
            @Result(column = "idx_team_master_id", property = "teamMasterId", jdbcType = JdbcType.BIGINT),
            @Result(column = "github_username", property = "githubUsername", jdbcType = JdbcType.VARCHAR),
            @Result(column = "github_token", property = "githubToken", jdbcType = JdbcType.VARCHAR)

    })
    List<Team> findAllTeam();

    /**
     * 根据团队id查找
     *
     * @param id：团队id
     * @return 团队
     */
    @Select("select id, " + COLUMNS + " from team where id = #{id} and gmt_deleted is null")
    @ResultMap("team")
    Team findTeamByTeamId(BigInteger id);

    /**
     * 根据团队名查找团队
     *
     * @param name：团队名称
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where idx_team_name like concat('%', #{name}, '%') and gmt_deleted is null")
    @ResultMap("team")
    List<Team> findTeamByName(String name);

    /**
     * 通过项目——团队id查团队
     * @param id id
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where idx_project_train_id=#{id} and gmt_deleted is null")
    @ResultMap("team")
    List<Team> findTeamsByProjectTrainId(BigInteger id);
    /**
     * 根据团队队长查找团队
     * @param teamMasterId 队长id
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where idx_team_master_id = #{teamMasterId} and gmt_deleted is null")
    @ResultMap("team")
    List<Team> findTeamByMasterId(BigInteger teamMasterId);
}
