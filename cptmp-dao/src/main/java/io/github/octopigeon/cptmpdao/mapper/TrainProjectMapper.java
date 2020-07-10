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
 * @version 1.0
 * @date 2020/7/9
 * <p>
 * last-check-in anlow
 * @date 2020/7/10
 */
@Repository
@Mapper
public interface TrainProjectMapper {

    String COLUMNS = "gmt_create, gmt_modified, uk_project_name, start_date, finish_date, " +
            "content, accept_standard, resource_library";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{projectName}, #{startDate}, #{finishDate}, " +
            "#{content}, #{acceptStandard}, #{resourceLibrary}";
    String UPDATE_HEADER = "update train_project set ";
    String TAIL_PROJECT_NAME = "where uk_project_name = #{projectName}";

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
     * 根据项目id修改项目内容
     * @param gmtModified  修改时间
     * @param projectName 项目名称
     * @param startDate 开始时间
     * @param finishDate  结束时间
     * @param content  项目内容
     * @param acceptStandard  验收标准
     * @param resourceLibrary 资源库
     */
    @Update("update train_project set gmt_modified = #{gmtModified}, uk_project_name = #{projectName}, start_date = #{startDate}, " +
            "finish_date = #{finishDate}, content = #{content}, accept_standard = #{acceptStandard}, resource_library = #{resourceLibrary} where id = #{id}")
    void updateTrainProjectById(BigInteger id, Date gmtModified, String projectName, Date startDate, Date finishDate, String content, String acceptStandard, String resourceLibrary);

    /**
     * 根据项目名修改项目内容
     * @param gmtModified  修改时间
     * @param projectName 项目名称
     * @param startDate 开始时间
     * @param finishDate  结束时间
     * @param content  项目内容
     * @param acceptStandard  验收标准
     * @param resourceLibrary 资源库
     */
    @Update("update train_project set gmt_modified = #{gmtModified}, uk_project_name = #{projectName}, start_date = #{startDate}, " +
            "finish_date = #{finishDate}, content = #{content}, accept_standard = #{acceptStandard}, resource_library = #{resourceLibrary} where uk_project_name = #{projectName}")
    void updateTrainProjectByProjectName(Date gmtModified, String projectName, Date startDate, Date finishDate, String content, String acceptStandard, String resourceLibrary);

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
            @Result(column = "start_date", property = "startDate", jdbcType = JdbcType.DATE),
            @Result(column = "finish_date", property = "finishDate", jdbcType = JdbcType.DATE),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accept_standard", property = "acceptStandard", jdbcType = JdbcType.VARCHAR),
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
