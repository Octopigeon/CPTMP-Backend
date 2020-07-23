package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 2.1
 * @date 2020/7/9
 * 对应train_project表
 * last-check-in 李国鹏
 * @date 2020/7/15
 */
@Repository
@Mapper
public interface ProjectMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, idx_name, project_level, " +
            "project_content, resource_library";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{name}, #{level}, " +
            "#{content}, #{resourceLibrary}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, idx_name = #{name}, " +
            "project_level = #{level}, project_content = #{content}";

    /**
     * 插入实训项目
     *
     * @param project：实训项目
     */
    @Insert("insert into train_project (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainProject(Project project);


    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from train_project")
    void removeAllTrainProjectsTest();

    /**
     * 测试用
     */
    @Update("update train_project set gmt_deleted = null")
    void restoreAllTrainProjects();

    /**
     * 根据项目id删除对应的项目
     *
     * @param id：项目id
     * @param gmtDeleted 删除日期
     */
    @Update("update train_project set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideTrainProjectById(BigInteger id, Date gmtDeleted);

    /**
     * 根据id恢复
     * @param id id
     */
    @Update("update train_project set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreTrainProjectById(BigInteger id);
    /**
     * 根据项目名称删除对应的项目
     *
     * @param name：项目名称
     * @param gmtDeleted 删除日期
     */
    @Update("update train_project set gmt_deleted = #{gmtDeleted} where idx_name = #{name} and gmt_deleted is null")
    void hideTrainProjectByName(String name, Date gmtDeleted);


    /**
     * 根据名字恢复
     * @param name 名字
     */
    @Update("update train_project set gmt_deleted = null where idx_name = #{name} and gmt_deleted is not null")
    void restoreTrainProjectByName(String name);

    /**
     * 根据id修改实训项目
     * @param project 项目
     */
    @Update("update train_project set " + UPDATE_CONTENT + " where id = #{id} and gmt_deleted is null")
    void updateTrainProjectById(Project project);


    /**
     * 更改资源
     *
     * @param id              项目id
     * @param gmtModified     修改时间
     * @param resourceLibrary 资源地址
     */
    @Update("update train_project set resource_library = #{resourceLibrary}, gmt_modified = #{gmtModified} where id = #{id} and gmt_deleted is null")
    void updateTrainProjectResourceById(BigInteger id, Date gmtModified, String resourceLibrary);

    /**
     * 查询所有项目
     *
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train_project where gmt_deleted is null")
    @Results(id = "project", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "project_level", property = "level", jdbcType = JdbcType.INTEGER),
            @Result(column = "project_content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "resource_library", property = "resourceLibrary", jdbcType = JdbcType.VARCHAR)

    })
    List<Project> findAllTrainProject();

    /**
     * 根据项目id查找项目
     *
     * @param id：项目id
     * @return 项目
     */
    @Select("select id, " + COLUMNS + " from train_project where id = #{id} and gmt_deleted is null")
    @ResultMap("project")
    Project findTrainProjectById(BigInteger id);

    /**
     * 根据项目名查找项目，模糊查找
     *
     * @param name：项目名称
     * @return 项目
     */
    @Select("select id, " + COLUMNS + " from train_project where idx_name like concat('%', #{name}, '%') and gmt_deleted is null")
    @ResultMap("project")
    List<Project> findTrainProjectByNameAmbiguously(String name);

    /**
     * 根据项目难度进行查询
     * @param level 项目难度
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train_project where project_level = #{level} and gmt_deleted is null")
    @ResultMap("project")
    List<Project> findTrainProjectByLevel(Integer level);
}
