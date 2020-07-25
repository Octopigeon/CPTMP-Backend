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
 * @last-check-in 李国鹏
 * @date 2020/7/15
 */
@Repository
@Mapper
public interface ProjectTrainMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, train_id, project_id";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainId}, #{projectId}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, train_id = #{trainId}, project_id = #{projectId}";

    /**
     * 添加项目与实训联系
     * @param projectTrain 对象
     */
    @Insert("insert into project_train (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addProjectTrain(ProjectTrain projectTrain);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from project_train")
    void removeAllProjectTrain();

    /**
     * 通过id移除
     * @param id id
     */
    @Delete("delete from project_train where id = #{id}")
    void removeProjectTrainsById(BigInteger id);

    /**
     * 通过项目id移除
     * @param projectId 项目id
     */
    @Delete("delete from project_train where project_id = #{projectId}")
    void removeProjectTrainsByProjectId(BigInteger projectId);

    /**
     * 通过实训id移除
     * @param trainId 实训id
     */
    @Delete("delete from project_train where train_id = #{trainId}")
    void removeProjectTrainsByTrainId(BigInteger trainId);

    /**
     * 通过项目和实训id移除
     * @param projectId 项目id
     * @param trainId 实训id
     */
    @Delete("delete from project_train where project_id = #{projectId} and train_id = #{trainId}")
    void removeProjectTrainsByProjectIdAndTrainId(BigInteger projectId, BigInteger trainId);

    /**
     * 通过id更新
     * @param projectTrain 对象
     */
    @Update("update project_train set " + UPDATE_CONTENT + " where id = #{id}")
    void updateProjectTrainById(ProjectTrain projectTrain);

    /**
     * 通过id查找
     * @param id id
     * @return
     */
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

    /**
     * 通过项目id查
     * @param projectId 项目id
     * @return 关系列表
     */
    @Select("select id, " + COLUMNS + " from project_train where project_id = #{projectId}")
    @ResultMap("project_train")
    List<ProjectTrain> findProjectTrainsByProjectId(BigInteger projectId);

    /**
     * 通过实训id查
     * @param trainId 实训id
     * @return 关系列表
     */
    @Select("select id, " + COLUMNS + " from project_train where train_id = #{trainId}")
    @ResultMap("project_train")
    List<ProjectTrain> findProjectTrainsByTrainId(BigInteger trainId);

    /**
     * 通过项目id和实训id查
     * @param projectId 项目id
     * @param trainId 实训id
     * @return 关系列表
     */
    @Select("select id, " + COLUMNS + " from project_train where project_id = #{projectId} and train_id = #{trainId}")
    @ResultMap("project_train")
    ProjectTrain findProjectTrainByProjectIdAndTrainId(BigInteger projectId, BigInteger trainId);

    /**
     * 测试用
     * @return 列表
     */
    @Deprecated
    @Select("select id, " + COLUMNS + " from project_train")
    @ResultMap("project_train")
    List<ProjectTrain> findAllProjectTrains();

    /**
     * 通过实训id查项目id
     * @param trainId 实训id
     * @return 关系列表
     */
    @Select("select id, " + COLUMNS + " from project_train where train_id = #{trainId}")
    @ResultMap("project_train")
    List<ProjectTrain> findProjectIdByTrainId(BigInteger trainId);

}
