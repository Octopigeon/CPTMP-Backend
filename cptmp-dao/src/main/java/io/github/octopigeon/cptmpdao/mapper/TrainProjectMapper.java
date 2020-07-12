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
 * @version 2.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
@Repository
@Mapper
public interface TrainProjectMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, idx_train_id, idx_project_name, project_level, " +
            "project_content, resource_library";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainId}, #{projectName}, #{projectLevel}, " +
            "#{projectContent}, #{resourceLibrary}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, idx_train_id = #{trainId}, idx_project_name = #{projectName}, " +
            "project_level = #{projectLevel}, project_content = #{projectContent}";

    /**
     * 插入实训项目
     * @param trainProject：实训项目
     */
    @Insert("insert into train_project (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainProject(TrainProject trainProject);


    /**
     * 测试用
     */
    @Deprecated
    @Update("update train_project set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeAllTrainProjects(Date gmtDeleted);


    /**
     * 根据项目id删除对应的项目
     * @param id：项目id
     * @param gmtDeleted 删除日期
     */
    @Update("update train_project set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeTrainProjectById(BigInteger id,Date gmtDeleted);

    /**
     * 根据项目名称删除对应的项目
     * @param projectName：项目名称
     * @param gmtDeleted 删除日期
     */
    @Update("update train_project set gmt_deleted = #{gmtDeleted} where idx_project_name = #{projectName} and gmt_deleted is null")
    void removeTrainProjectByProjectName(String projectName,Date gmtDeleted);



    /**
     * 根据id修改实训项目
     * @param id 实训id
     * @param gmtModified 修改时间
     * @param trainId 实训id
     * @param projectName 项目姓名
     * @param projectLevel 项目难度
     * @param projectContent 项目内容描述
     */
    @Update("update train_project set "+ UPDATE_CONTENT +" where id = #{id} and gmt_deleted is null")
    void updateTrainProjectById(BigInteger id, Date gmtModified, BigInteger trainId, String projectName, Integer projectLevel, String projectContent);


    /**
     * 更改资源
     * @param id 项目id
     * @param gmtModified 修改时间
     * @param resourceLibrary 资源地址
     */
    @Update("update train_project set resource_library = #{resourceLibrary}, gmt_modified = #{gmtModified} where id = #{id} and gmt_deleted is null")
    void updateTrainProjectResourceById(BigInteger id,Date gmtModified,String resourceLibrary);

    /**
     * 根据名称修改实训项目
     * @param gmtModified 修改时间
     * @param trainId 实训id
     * @param projectName 项目姓名
     * @param projectLevel 项目难度
     * @param projectContent 项目内容描述
     */
    @Update("update train_project set "+ UPDATE_CONTENT +" where idx_project_name = #{projectName} and gmt_deleted is null")
    void updateTrainProjectByProjectName(String projectName,Date gmtModified,BigInteger id,  BigInteger trainId,  Integer projectLevel, String projectContent);


    /**
     * 更改资源
     * @param projectName 项目名称
     * @param gmtModified 修改时间
     * @param resourceLibrary 资源地址
     */
    @Update("update train_project set resource_library = #{resourceLibrary}, gmt_modified = #{gmtModified} where idx_project_name = #{projectName} and gmt_deleted is null")
    void updateTrainProjectResourceByProjectName(String projectName,Date gmtModified,String resourceLibrary);


    /**
     * 查询所有项目
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train_project where gmt_deleted is null")
    @Results(id = "trainProject", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_project_name", property = "projectName", jdbcType = JdbcType.VARCHAR),
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
    @Select("select id, " + COLUMNS + " from train_project where id = #{id} and gmt_deleted is null")
    @ResultMap("trainProject")
    TrainProject findTrainProjectById(BigInteger id);

    /**
     * 根据项目名查找项目，模糊查找
     * @param projectName：项目名称
     * @return 项目
     */
    @Select("select id, " + COLUMNS + " from train_project where idx_project_name like concat('%', #{projectName}, '%') and gmt_deleted is null")
    @ResultMap("trainProject")
    List<TrainProject> findTrainProjectByProjectNameAmbiguously(String projectName);
}
