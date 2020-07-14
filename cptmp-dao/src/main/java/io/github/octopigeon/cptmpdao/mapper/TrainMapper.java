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
 * @version 2.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
@Repository
@Mapper
public interface TrainMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted,uk_name, organization_id, start_date, " +
            "finish_date, content, standard, resource_library, gps_info";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted},#{name}, #{organizationId}, #{startDate}, " +
            "#{finishDate}, #{content}, #{standard}, #{resourceLibrary},#{gpsInfo}";
    String UPDATE_CONTENT = "organization_id = #{organizationId}, gmt_modified = #{gmtModified},uk_name = #{name}, start_date = #{startDate}, " +
            "finish_date = #{finishDate}, content = #{content}, standard = #{standard},gps_info = #{gpsInfo}";

    /**
     * 插入实训
     * @param train：实训
     */
    @Insert("insert into train (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrainProject(Train train);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from train")
    void removeAllTrainTest();

    /**
     * 测试用
     * @param gmtDeleted 删除日期
     */
    @Update("update train set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeAllTrain(Date gmtDeleted);

    /**
     * 根据实训id删除
     * @param id：实训id
     * @param gmtDeleted 删除日期
     */

    @Update("update train set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeTrainProjectById(BigInteger id,Date gmtDeleted);



    /**
     * 根据实训id改
     */
    @Update("update train set "+ UPDATE_CONTENT +" where id = #{id} and gmt_deleted is null")
    void updateTrainProjectById(Train train);


    /**
     * 根据id改资源
     * @param id 实训id
     * @param gmtModified 修改时间
     * @param resourceLibrary 资源库
     */
    @Update("update train set resource_library = #{resourceLibrary} where id = #{id} and gmt_deleted is null")
    void updateTrainProjectResourceById(BigInteger id,Date gmtModified,String resourceLibrary);


    /**
     * 查询所有项目
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train where gmt_deleted is null")
    @Results(id = "train", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "uk_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "organization_id", property = "organizationId", jdbcType = JdbcType.BIGINT),
            @Result(column = "start_date", property = "startDate", jdbcType = JdbcType.DATE),
            @Result(column = "finish_date", property = "finishDate", jdbcType = JdbcType.DATE),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "standard", property = "standard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "resource_library", property = "resourceLibrary", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gps_info", property = "gpsInfo", jdbcType = JdbcType.VARCHAR)

    })
    List<Train> findAllTrain();

    /**
     * 根据实训id查找
     * @param id：实训id
     * @return 实训
     */
    @Select("select id, " + COLUMNS + " from train where id = #{id} and gmt_deleted is null")
    @ResultMap("train")
    Train findTrainById(BigInteger id);

//    /**
//     * 根据项目名查找项目，模糊查找
//     * @param projectName：项目名称
//     * @return 项目
//     */
//    @Select("select id, " + COLUMNS + " from train where uk_project_name like concat('%', #{projectName}, '%') and gmt_deleted is null")
//    @ResultMap("train")
//    List<Train> findTrainProjectByProjectNameAmbiguously(String projectName);
}
