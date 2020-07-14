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
 * @date 2020/7/12
 */
@Repository
@Mapper
public interface TeamMapper {

    /**
     * 添加团队
     * @param team 团队
     */
    String COLUMNS="gmt_create, gmt_modified, gmt_deleted, idx_train_project_id, idx_team_name, idx_master_user_id, idx_pm_user_id, idx_po_user_id, code_base_url, team_grade";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainProjectId}, #{teamName}, #{masterUserId}, #{pmUserId}, #{poUserId}, #{codeBaseUrl}, #{teamGrade}";
    String UPDATE_CONTENT="gmt_modified = #{gmtModified}, idx_train_project_id = #{trainProjectId}, idx_team_name = #{teamName}, " +
            "idx_master_user_id = #{masterUserId}, idx_pm_user_id = #{masterUserId}, idx_po_user_id = #{masterUserId}, code_base_url = #{codeBaseUrl},team_grade = #{teamGrade}";

    @Insert("insert into team (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainTeam(Team team);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from team")
    void removeAllTrainTeamTest();
    /**
     * @param gmtDeleted 删除时间
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeAllTrainTeam(Date gmtDeleted);
    /**
     * 根据id删除对应的团队
     * @param id：团队id
     * @param gmtDeleted 删除日期
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeTrainTeamById(BigInteger id,Date gmtDeleted);

    /**
     * 根据团队名称删除团队
     * @param teamName 团队名称
     * @param gmtDeleted 删除日期
     */
    @Update("update team set gmt_deleted = #{gmtDeleted} where idx_team_name = #{teamName} and gmt_deleted is null")
    void removeTrainTeamByTeamName(String teamName,Date gmtDeleted);

    /**
     * 根据团队id修改
     */
    @Update("update team set "+ UPDATE_CONTENT+"  where id = #{id} and gmt_deleted is null")
    void updateTrainTeamById(Team team);

    /**
     * 修改代码库
     * @param id 团队id
     * @param gmtModified 删除日期
     * @param codeBase 代码库
     */
    @Update("update team set gmt_deleted = #{gmtDeleted}, code_base_url = #{codeBaseUrl} where id = #{id} and gmt_deleted is null")
    void updateTrainTeamCodeBaseById(BigInteger id, Date gmtModified,String codeBase);
    /**
     * 根据团队名修改
     */
    @Update("update team set "+ UPDATE_CONTENT+"  where idx_team_name = #{teamName} and gmt_deleted is null")
    void updateTrainTeamByTeamName(Team team);

    /**
     * 修改代码库
     * @param teamName 团队名称
     * @param gmtModified 删除日期
     * @param codeBase 代码库
     */
    @Update("update team set gmt_deleted = #{gmtDeleted}, code_base_url = #{codeBaseUrl} where idx_team_name = #{teamName} and gmt_deleted is null")
    void updateTrainTeamCodeBaseByTeamName(String teamName, Date gmtModified,String codeBase);


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
            @Result(column = "idx_train_project_id", property = "trainProjectId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_name", property = "teamName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_master_user_id", property = "masterUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_pm_user_id", property = "pmUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_po_user_id", property = "poUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "code_base_url", property = "codeBaseUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.DECIMAL)

    })
    List<Team> findAllTrainTeam();

    /**
     * 根据团队id查找
     * @param id：团队id
     * @return 团队
     */
    @Select("select id, " + COLUMNS + " from team where id = #{id} and gmt_deleted is null")
    @ResultMap("team")
    Team findTrainTeamByTeamId(BigInteger id);

    /**
     * 根据团队名查找项目
     * @param teamName：团队名称
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from team where team_name = #{teamName} and gmt_deleted is null")
    @ResultMap("team")
    Team findTrainTeamByTeamName(String teamName);
}
