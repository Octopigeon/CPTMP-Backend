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
     *
     * @param team 团队
     */
    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, avatar, uk_project_train_id, idx_team_name, repo_url, team_grade,evaluation";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{avatar},#{projectTrainId}, #{name}, #{repoUrl}, #{teamGrade},#{evaluation}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, uk_project_train_id = #{projectTrainId}, idx_team_name = #{name}, evaluation = #{evaluation}, " +
            " repo_url = #{repoUrl},team_grade = #{teamGrade},avatar = #{avatar}";

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

    @Update("update team set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreTeamById(BigInteger id);

    /**
     * 根据团队id修改
     */
    @Update("update team set " + UPDATE_CONTENT + "  where id = #{id} and gmt_deleted is null")
    void updateTeamById(Team team);

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
            @Result(column = "uk_project_train_id", property = "projectTrainId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "repo_url", property = "repoUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "evaluation", property = "evaluation", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.SMALLINT)

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

    @Select("select id, " + COLUMNS + " from team where uk_project_train_id=#{id} and gmt_deleted is null")
    @ResultMap("team")
    List<Team> findTeamsByProjectTrainId(BigInteger id);
}
