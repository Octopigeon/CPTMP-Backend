package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Train;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

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
public interface TrainMapper {

    String COLUMNS = "gmt_create, gmt_modified, school_id, process_id, start_date, " +
            "finish_date, content, accept_standard, resource_library";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{schoolId}, #{processId}, #{startDate}, " +
            "#{finishDate}, #{content}, #{acceptStandard}, #{resourceLibrary}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, school_id = #{schoolId}, process_id = #{processId}, start_date = #{startDate}, " +
            "finish_date = #{finishDate}, content = #{content}, accept_standard = #{acceptStandard}, resource_library = #{resourceLibrary}";

    /**
     * 插入实训
     * @param train：实训
     */
    @Insert("insert into train (" + COLUMNS + ") " +
            "values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainProject(Train train);

    /**
     * 根据实训id删除
     * @param id：实训id
     */
    @Delete("delete from train where id = #{id}")
    void removeTrainProjectById(BigInteger id);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from train")
    void removeAllTrainProjects();

//    /**
//     * 根据实训名称删除
//     * @param projectName 项目名称
//     */
//    @Delete("delete from train where uk_project_name = #{projectName}")
//    void removeTrainProjectByProjectName(String projectName);

    /**
     * 根据实训id改
     * @param id 实训id
     * @param gmtModified 修改时间
     * @param schoolId 学校id
     * @param processId 实训流程id
     * @param startDate 开始日期
     * @param finishDate 结束日期
     * @param content 实训内容
     * @param acceptStandard 验收标准
     * @param resourceLibrary 资源库
     */
    @Update("update train set "+ UPDATE_CONTENT +" where id = #{id}")
    void updateTrainProjectById(BigInteger id, Date gmtModified, BigInteger schoolId, BigInteger processId, Date startDate, Date finishDate, String content, String acceptStandard, String resourceLibrary);


    /**
     * 查询所有项目
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train")
    @Results(id = "train", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "school_id", property = "schoolId", jdbcType = JdbcType.BIGINT),
            @Result(column = "process_id", property = "processId", jdbcType = JdbcType.BIGINT),
            @Result(column = "start_date", property = "startDate", jdbcType = JdbcType.DATE),
            @Result(column = "finish_date", property = "finishDate", jdbcType = JdbcType.DATE),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accept_standard", property = "acceptStandard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "resource_library", property = "resourceLibrary", jdbcType = JdbcType.VARCHAR)

    })
    List<Train> findAllTrain();

    /**
     * 根据实训id查找
     * @param id：实训id
     * @return 实训
     */
    @Select("select id, " + COLUMNS + " from train where id = #{id}")
    @ResultMap("train")
    Train findTrainById(BigInteger id);

//    /**
//     * 根据项目名查找项目，模糊查找
//     * @param projectName：项目名称
//     * @return 项目
//     */
//    @Select("select id, " + COLUMNS + " from train where uk_project_name like concat('%', #{projectName}, '%')")
//    @ResultMap("train")
//    List<Train> findTrainProjectByProjectNameAmbiguously(String projectName);
}
