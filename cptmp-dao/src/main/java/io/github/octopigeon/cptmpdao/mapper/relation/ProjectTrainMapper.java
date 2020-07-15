package io.github.octopigeon.cptmpdao.mapper.relation;

import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Repository
@Mapper
public interface ProjectTrainMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, train_id, project_id";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainId}, #{projectId}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, train_id = #{trainId}, project_id = #{projectId}";

    @Insert("insert into project_train (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addProjectTrain(ProjectTrain projectTrain);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from project_train")
    void removeAllProjectTrain();

    @Delete("delete from project_train where id = #{id}")
    void removeProjectTrainsById(BigInteger id);

    @Delete("delete from project_train where project_id = #{projectId}")
    void removeProjectTrainsByProjectId(BigInteger projectId);

    @Delete("delete from project_train where train_id = #{trainId}")
    void removeProjectTrainsByTrainId(BigInteger trainId);

    @Delete("delete from project_train where project_id = #{projectId} and train_id = #{trainId}")
    void removeProjectTrainsByProjectIdAndTrainId(BigInteger projectId, BigInteger trainId);

    @Update("update project_train set " + UPDATE_CONTENT + " where id = #{id}")
    void updateProjectTrainById(ProjectTrain projectTrain);

    @Select("select id, " + COLUMNS + " from project_train where id = #{id}")
    @Results(id = "project_train", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "project_id", property = "projectId", jdbcType = JdbcType.BIGINT),
            @Result(column = "train_id", property = "trainId", jdbcType = JdbcType.BIGINT)
    })
    ProjectTrain findProjectTrainById(BigInteger id);

    @Select("select id, " + COLUMNS + " from project_train where project_id = #{projectId}")
    @ResultMap("project_train")
    List<ProjectTrain> findProjectTrainsByProjectId(BigInteger projectId);

    @Select("select id, " + COLUMNS + " from project_train where train_id = #{trainId}")
    @ResultMap("project_train")
    List<ProjectTrain> findProjectTrainsByTrainId(BigInteger trainId);

    @Select("select id, " + COLUMNS + " from project_train where project_id = #{projectId} and train_id = #{trainId}")
    @ResultMap("project_train")
    ProjectTrain findProjectTrainByProjectIdAndTrainId(BigInteger projectId, BigInteger trainId);

    /**
     * 测试用
     * @return
     */
    @Deprecated
    @Select("select id, " + COLUMNS + " from project_train")
    @ResultMap("project_train")
    List<ProjectTrain> findAllProjectTrains();


}