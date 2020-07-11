package io.github.octopigeon.cptmpdao.mapper;

import com.alibaba.fastjson.JSON;
import io.github.octopigeon.cptmpdao.model.DailyRecord;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
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
public interface TrainProjectMapper {

    String COLUMNS = "gmt_create, gmt_modified, idx_train_id, uk_project_name, project_level, " +
            "project_content, resource_library";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{trainId}, #{projectName}, #{projectLevel}, " +
            "#{projectContent}, #{resourceLibrary}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, idx_train_id = #{trainId}, uk_project_name = #{projectName}, " +
            "project_level = #{projectLevel}, project_content = #{projectContent}, resource_library = #{resourceLibrary}";

    /**
     * 插入实训项目
     * @param trainProject：实训项目
     */
    @Insert("insert into train_project (" + COLUMNS + ") " +
            "values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainProject(TrainProject trainProject);

    /**
     * 根据项目id删除对应的项目
     * @param id：项目id
     */
    @Delete("delete from train_project where id = #{id}")
    void removeTrainProjectById(BigInteger id);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from train_project")
    void removeAllTrainProjects();

    /**
     * 根据项目名称删除项目
     * @param projectName 项目名称
     */
    @Delete("delete from train_project where uk_project_name = #{projectName}")
    void removeTrainProjectByProjectName(String projectName);

    /**
     * 根据id修改实训项目
     * @param id 实训id
     * @param gmtModified 修改时间
     * @param trainId 实训id
     * @param projectName 项目姓名
     * @param projectLevel 项目难度
     * @param projectContent 项目内容描述
     * @param resourceLibrary 项目资源库
     */
    @Update("update train_project set "+ UPDATE_CONTENT +" where id = #{id}")
    void updateTrainProjectById(BigInteger id, Date gmtModified, BigInteger trainId, String projectName, Integer projectLevel, String projectContent, String resourceLibrary);

    /**
     * 根据项目名称修改
     * @param gmtModified 修改时间
     * @param trainId 实训id
     * @param projectName 项目名称
     * @param projectLevel 项目难度
     * @param projectContent 项目内容描述
     * @param resourceLibrary 项目资源库
     */
    @Update("update train_project set "+ UPDATE_CONTENT +" where uk_project_name = #{projectName}")
    void updateTrainProjectByProjectName(Date gmtModified, BigInteger trainId, String projectName, Integer projectLevel, String projectContent, String resourceLibrary);

    /**
     * 查询所有项目
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train_project")
    @Results(id = "trainProject", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "uk_project_name", property = "projectName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_train_id", property = "trainId", jdbcType = JdbcType.BIGINT),
            @Result(column = "project_level", property = "projectLevel", jdbcType = JdbcType.INTEGER),
            @Result(column = "project_content", property = "projectContent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "resource_library", property = "resourceLibrary", jdbcType = JdbcType.VARCHAR)

    })
    List<TrainProject> findAllTrainProject();

    /**
     * 根据项目id查找项目
     * @param id：项目id
     * @return 项目
     */
    @Select("select id, " + COLUMNS + " from train_project where id = #{id}")
    @ResultMap("trainProject")
    TrainProject findTrainProjectById(BigInteger id);

    /**
     * 根据项目名查找项目，模糊查找
     * @param projectName：项目名称
     * @return 项目
     */
    @Select("select id, " + COLUMNS + " from train_project where uk_project_name like concat('%', #{projectName}, '%')")
    @ResultMap("trainProject")
    List<TrainProject> findTrainProjectByProjectNameAmbiguously(String projectName);
}
