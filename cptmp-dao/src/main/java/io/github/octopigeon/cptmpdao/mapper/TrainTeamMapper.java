package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpdao.model.TrainTeam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/9
 */
@Repository
@Mapper
public interface TrainTeamMapper {

    /**
     * 插入实训团队
     * @param trainTeam：实训团队
     */
    @Insert("insert into train_team (gmt_create, train_project_id, idx_team_name, team_manager_id, code_base_url, team_grade) " +
            "values (#{gmtCreate}, #{trainProjectId}, #{teamName}, #{teamManagerId}, #{codeBaseUrl}, #{teamGrade})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainTeam(TrainTeam trainTeam);

    /**
     * 根据id删除对应的团队
     * @param id：团队id
     */
    @Delete("delete from train_team where id = #{id}")
    void removeTrainTeamById(BigInteger id);

    /**
     * 根据团队名称删除团队
     * @param teamName 团队名称
     */
    @Delete("delete from train_team where idx_team_name = #{teamName}")
    void removeTrainTeamByTeamName(String teamName);

    /**
     * 根据id修改团队
     * @param id 团队id
     * @param gmtModified 修改时间
     * @param trainProjectId 项目id
     * @param teamName 团队名称
     * @param teamManagerId 负责人id
     * @param codeBaseUrl 代码库地址
     * @param teamGrade 团队成绩
     */
    @Update("update train_team set gmt_modified = #{gmtModified}, train_project_id = #{trainProjectId}, idx_team_name = #{teamName}, " +
            "team_manager_id = #{teamManagerId}, code_base_url = #{codeBaseUrl}, team_grade = #{teamGrade} where id = #{id}")
    void updateTrainTeamById(BigInteger id, Date gmtModified, BigInteger trainProjectId, String teamName, BigInteger teamManagerId, String codeBaseUrl, BigDecimal teamGrade);

    /**
     * 根据团队名修改团队
     * @param gmtModified 修改时间
     * @param trainProjectId 项目id
     * @param teamName 团队名称
     * @param teamManagerId 负责人id
     * @param codeBaseUrl 代码库地址
     * @param teamGrade 团队成绩
     */
    @Update("update train_team set gmt_modified = #{gmtModified}, train_project_id = #{trainProjectId}, idx_team_name = #{teamName}, " +
            "team_manager_id = #{teamManagerId}, code_base_url = #{codeBaseUrl}, team_grade = #{teamGrade} where idx_team_name = #{teamName}")
    void updateTrainTeamByTeamName(Date gmtModified, BigInteger trainProjectId, String teamName, BigInteger teamManagerId, String codeBaseUrl, BigDecimal teamGrade);


    /**
     * 查询所有团队
     * @return 团队列表
     */
    @Select("select * from train_team")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "train_project_id", property = "trainProjectId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_name", property = "teamName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_manager_id", property = "teamManagerId", jdbcType = JdbcType.BIGINT),
            @Result(column = "code_base_url", property = "codeBaseUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.DECIMAL)

    })
    List<TrainTeam> findAllTrainTeam();

    /**
     * 根据团队id查找
     * @param teamId：团队id
     * @return 团队
     */
    @Select("select * from train_team where id = #{id}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "train_project_id", property = "trainProjectId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_name", property = "teamName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_manager_id", property = "teamManagerId", jdbcType = JdbcType.BIGINT),
            @Result(column = "code_base_url", property = "codeBaseUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.DECIMAL)

    })
    List<TrainTeam> findTrainTeamByTeamId(BigInteger teamId);

    /**
     * 根据团队名查找项目
     * @param teamName：团队名称
     * @return 团队列表
     */
    @Select("select * from train_team where team_name = #{teamName}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "train_project_id", property = "trainProjectId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_name", property = "teamName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_manager_id", property = "teamManagerId", jdbcType = JdbcType.BIGINT),
            @Result(column = "code_base_url", property = "codeBaseUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "team_grade", property = "teamGrade", jdbcType = JdbcType.DECIMAL)
    })
    List<TrainTeam> findTrainTeamByTeamName(String teamName);
}
