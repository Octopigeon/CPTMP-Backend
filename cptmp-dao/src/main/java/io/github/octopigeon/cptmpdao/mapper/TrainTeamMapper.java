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
 * @version 1.2
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/10
 */
@Repository
@Mapper
public interface TrainTeamMapper {

    /**
     * 添加团队
     * @param trainTeam 团队
     */
    String COLUMNS="gmt_create, gmt_modified, gmt_deleted, idx_train_project_id, idx_team_name, idx_master_user_id, idx_pm_user_id, idx_po_user_id, code_base_url, team_grade";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainProjectId}, #{teamName}, #{masterUserId}, #{pmUserId}, #{poUserId}, #{codeBaseUrl}, #{teamGrade}";
    String UPDATE_CONTENT="gmt_modified = #{gmtModified}, idx_train_project_id = #{trainProjectId}, idx_team_name = #{teamName}, " +
            "idx_master_user_id = #{masterUserId}, idx_pm_user_id = #{masterUserId}, idx_po_user_id = #{masterUserId},team_grade = #{teamGrade}";

    @Insert("insert into train_team (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainTeam(TrainTeam trainTeam);

    /**
     * 测试用
     * @param gmtDeleted 删除时间
     */
    @Deprecated
    @Delete("update train_team set gmt_deleted = #{gmtDeleted}")
    void removeAllTrainTeam(Date gmtDeleted);
    /**
     * 根据id删除对应的团队
     * @param id：团队id
     * @param gmtDeleted 删除日期
     */
    @Delete("update train_team set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeTrainTeamById(BigInteger id,Date gmtDeleted);

    /**
     * 根据团队名称删除团队
     * @param teamName 团队名称
     * @param gmtDeleted 删除日期
     */
    @Delete("update train_team set gmt_deleted = #{gmtDeleted} where idx_team_name = #{teamName} and gmt_deleted is null")
    void removeTrainTeamByTeamName(String teamName,Date gmtDeleted);

    /**
     * 根据团队id修改
     * @param id 团队id
     * @param gmtModified 修改时间
     * @param trainProjectId 项目id
     * @param teamName 团队名称
     * @param masterUserId 项目经理
     * @param pmUserId 产品经理
     * @param poUserId 运营经理
     * @param teamGrade 成绩
     */
    @Update("update train_team set "+ UPDATE_CONTENT+"  where id = #{id} and gmt_deleted is null")
    void updateTrainTeamById(BigInteger id, Date gmtModified, BigInteger trainProjectId, String teamName, BigInteger masterUserId, BigInteger pmUserId, BigInteger poUserId, BigDecimal teamGrade);

    /**
     * 修改代码库
     * @param id 团队id
     * @param gmtModified 删除日期
     * @param codeBase 代码库
     */
    @Update("update train_team set gmt_deleted = #{gmtDeleted}, code_base_url = #{codeBaseUrl} where id = #{id} and gmt_deleted is null")
    void updateTrainTeamCodeBaseById(BigInteger id, Date gmtModified,String codeBase);
    /**
     * 根据团队名修改
     * @param gmtModified 修改时间
     * @param trainProjectId 项目id
     * @param teamName 团队名称
     * @param masterUserId 项目经理
     * @param pmUserId 产品经理
     * @param poUserId 运营经理
     * @param teamGrade 成绩
     */
    @Update("update train_team set "+ UPDATE_CONTENT+"  where idx_team_name = #{teamName} and gmt_deleted is null")
    void updateTrainTeamByTeamName( String teamName,Date gmtModified, BigInteger trainProjectId, BigInteger masterUserId, BigInteger pmUserId, BigInteger poUserId, BigDecimal teamGrade);

    /**
     * 修改代码库
     * @param teamName 团队名称
     * @param gmtModified 删除日期
     * @param codeBase 代码库
     */
    @Update("update train_team set gmt_deleted = #{gmtDeleted}, code_base_url = #{codeBaseUrl} where idx_team_name = #{teamName} and gmt_deleted is null")
    void updateTrainTeamCodeBaseByTeamName(String teamName, Date gmtModified,String codeBase);


    /**
     * 查询所有团队
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from train_team where gmt_deleted is null")
    @Results(id = "trainTeam", value = {
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
    List<TrainTeam> findAllTrainTeam();

    /**
     * 根据团队id查找
     * @param teamId：团队id
     * @return 团队
     */
    @Select("select id, " + COLUMNS + " from train_team where id = #{id} and gmt_deleted is null")
    @ResultMap("trainTeam")
    TrainTeam findTrainTeamByTeamId(BigInteger teamId);

    /**
     * 根据团队名查找项目
     * @param teamName：团队名称
     * @return 团队列表
     */
    @Select("select id, " + COLUMNS + " from train_team where team_name = #{teamName} and gmt_deleted is null")
    @ResultMap("trainTeam")
    TrainTeam findTrainTeamByTeamName(String teamName);
}
