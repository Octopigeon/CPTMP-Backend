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
 * @date 2020/7/14
 */
@Repository
@Mapper
public interface TrainMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, idx_name, organization_id, start_time, " +
            "end_time, content, accept_standard, resource_library, gps_info, limits";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{name}, #{organizationId}, #{startTime}, " +
            "#{endTime}, #{content}, #{acceptStandard}, #{resourceLibrary}, #{gpsInfo}, #{limits}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, " +
            "idx_name = #{name}, organization_id = #{organizationId}, start_time = #{startTime}, end_time = #{endTime}, " +
            "content = #{content}, accept_standard = #{acceptStandard}, resource_library = #{resourceLibrary}, " +
            "gps_info = #{gpsInfo}, limits = #{limits}";
    String SOFT_DELETE_TAIL = "gmt_deleted is null";

    /**
     * 插入实训
     *
     * @param train：实训
     */
    @Insert("insert into train (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTrain(Train train);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from train")
    void removeAllTrain();

    /**
     * 测试用
     */
    @Deprecated
    @Update("update train set gmt_deleted = null")
    void restoreAllTrain();

    /**
     * 根据实训id删除
     *
     * @param id：实训id
     * @param gmtDeleted 删除日期
     */
    @Update("update train set gmt_deleted = #{gmtDeleted} where id = #{id} and " + SOFT_DELETE_TAIL)
    void hideTrainById(BigInteger id, Date gmtDeleted);

    /**
     * 根据id恢复
     * @param id id
     */
    @Update("update train set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreTrainById(BigInteger id);

    /**
     * 根据组织删除
     *
     * @param organizationId 组织id
     * @param gmtDeleted     删除日期
     */
    @Update("update train set gmt_deleted = #{gmtDeleted} where organization_id = #{organizationId} and " + SOFT_DELETE_TAIL)
    void hideTrainsByOrganizationId(BigInteger organizationId, Date gmtDeleted);

    /**
     * 根据组织恢复
     * @param organizationId 组织id
     */
    @Update("update train set gmt_deleted = null where organization_id = #{organizationId} and gmt_deleted is not null")
    void restoreTrainsByOrganizationId(BigInteger organizationId);

    /**
     * 按id更新train的信息
     *
     * @param train 实训
     */
    @Update("update train set " + UPDATE_CONTENT + " where id = #{id} and " + SOFT_DELETE_TAIL)
    void updateTrainById(Train train);

    /**
     * 根据实训更新资源库
     * @param trainId 实训id
     * @param date 修改日期
     * @param resourceLib 资源库
     */
    @Update("update train set gmt_modified = #{date}, resource_library = #{resourceLib} where id = #{trainId} and " + SOFT_DELETE_TAIL)
    void updateTrainProjectResourceById(BigInteger trainId, Date date, String resourceLib);

    /**
     * 查询所有项目
     *
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train where " + SOFT_DELETE_TAIL)
    @Results(id = "train", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "organization_id", property = "organizationId", jdbcType = JdbcType.BIGINT),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.DATE),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.DATE),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accept_standard", property = "acceptStandard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "resource_library", property = "resourceLibrary", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gps_info", property = "gpsInfo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "limits", property = "limits", jdbcType = JdbcType.VARCHAR)
    })
    List<Train> findAllTrain();

    /**
     * 根据实训id查找
     *
     * @param id：实训id
     * @return 实训
     */
    @Select("select id, " + COLUMNS + " from train where id = #{id} and " + SOFT_DELETE_TAIL)
    @ResultMap("train")
    Train findTrainById(BigInteger id);

    /**
     * 根据项目名查找项目，模糊查找
     *
     * @param name：项目名称
     * @return 项目
     */
    @Select("select id, " + COLUMNS + " from train where idx_name like concat('%', #{name}, '%') and " + SOFT_DELETE_TAIL)
    @ResultMap("train")
    List<Train> findTrainByNameAmbiguously(String name);

    /**
     * 根据组织id查找
     * @param organizationId 组织id
     * @return 实训列表
     */
    @Select("select id, " + COLUMNS + " from train where organization_id = #{organizationId} and " + SOFT_DELETE_TAIL)
    @ResultMap("train")
    List<Train> findTrainByOrganizationId(BigInteger organizationId);

    /**
     * 查找正在进行中的train
     * @last-check-in 李国豪
     * @return 项目列表
     */
    @Select("select id, " + COLUMNS + " from train where start_time<=NOW() and end_time>=Now() and " + SOFT_DELETE_TAIL)
    @ResultMap("train")
    List<Train> findValidTrains();
    //@Select("select id, " + COLUMNS + " from train where (NOW() between start_time and end_time) and " + SOFT_DELETE_TAIL)
}
