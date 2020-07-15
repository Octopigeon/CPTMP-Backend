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
            "end_time, content, accept_standard, resource_library, gps_info";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{name}, #{organizationId}, #{startTime}, " +
            "#{endTime}, #{content}, #{acceptStandard}, #{resourceLibrary}, #{gpsInfo}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, " +
            "idx_name = #{name}, organization_id = #{organizationId}, start_time = #{startTime}, end_time = #{endTime}, " +
            "content = #{content}, accept_standard = #{acceptStandard}, resource_library = #{resourceLibrary}, " +
            "gps_info = #{gpsInfo}";
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
    void removeTrainById(BigInteger id, Date gmtDeleted);

    /**
     * 删除一个学校的所有实训项目
     *
     * @param organizationId 组织id
     * @param gmtDeleted     删除日期
     */
    @Update("update train set gmt_deleted = #{gmtDeleted} where organization_id = #{organizationId} and " + SOFT_DELETE_TAIL)
    void removeTrainsByOrganizationId(BigInteger organizationId, Date gmtDeleted);

    /**
     * 按id更新train的信息
     *
     * @param train
     */
    @Update("update train set " + UPDATE_CONTENT + " where id = #{id} and " + SOFT_DELETE_TAIL)
    void updateTrainById(Train train);

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
            @Result(column = "gps_info", property = "gpsInfo", jdbcType = JdbcType.VARCHAR)
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
    List<Train> findTrainsByLikeName(String name);

    /**
     * 根据组织id查找shixun
     *
     * @param organizationId
     * @return
     */
    @Select("select id, " + COLUMNS + " from train where organization_id = #{organizationId} and " + SOFT_DELETE_TAIL)
    @ResultMap("train")
    List<Train> findTrainsByOrganizationId(BigInteger organizationId);
}
