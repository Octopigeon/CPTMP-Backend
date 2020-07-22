package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Process;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElementDecl;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国鹏
 * @date 2020/7/21
 */
@Repository
@Mapper
public interface ProcessMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, train_id, start_time, end_time";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainId}, #{startTime}, #{endTime}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, train_id = #{trainId}" +
            ", start_time = #{startTime}, end_time = #{endTime}";
    String BY_ID_TAIL = " where id = #{id} and gmt_deleted is null";
    String SOFT_DELETE_TAIL = " and gmt_deleted is null";

    /**
     * 插入流程
     * @param process 流程
     */
    @Insert("insert into cptmp_process (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addProcess(Process process);

    /**
     * 删除一个实训下的所有流程（当实训结束的时候）
     * @param gmtDeleted 删除日期
     * @param trainId 实训id
     */
    @Update("update cptmp_process set gmt_deleted = #{gmtDeleted} where train_id = #{trainId} and gmt_deleted is null")
    void hideProcessesByTrainId(Date gmtDeleted, BigInteger trainId);

    /**
     * 删除一个实训下的所有流程（当实训结束的时候）
     * @param trainId 实训id
     */
    @Update("update cptmp_process set gmt_deleted = null where train_id = #{trainId} and gmt_deleted is not null")
    void restoreProcessesByTrainId(BigInteger trainId);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from cptmp_process")
    void removeAllProcesses();

    /**
     * 根据id删除
     * @param gmtDeleted 删除日期
     * @param id id
     */
    @Update("update cptmp_process set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideProcessById(Date gmtDeleted, BigInteger id);

    /**
     * 根据id 恢复
     * @param id id
     */
    @Update("update cptmp_process set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreProcessById(BigInteger id);

    /**
     * 根据id 更新
     * @param process 流程
     */
    @Update("update cptmp_process set " + UPDATE_CONTENT + BY_ID_TAIL)
    void updateProcessById(Process process);

    /**
     * 查询全部
     * @return 流程列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_process where gmt_deleted is null")
    @Results(id = "process", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "train_id", property = "trainId", jdbcType = JdbcType.BIGINT),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.DATE),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.DATE)
    })
    List<Process> findAllProcesses();

    /**
     * 根据实训id查找
     * @param trainId 实训id
     * @return 流程列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_process where train_id = #{trainId}" + SOFT_DELETE_TAIL)
    @ResultMap("process")
    List<Process> findProcessesByTrainId(BigInteger trainId);

    /**
     * 根据id查
     * @param id id
     * @return 流程
     */
    @Select("select id, " + COLUMNS + " from cptmp_process where id = #{id} and gmt_deleted is null")
    @ResultMap("process")
    Process findProcessById(BigInteger id);

    @Select("select id, " + COLUMNS + " from cptmp_process where train_id = #{trainId} and (NOW() between start_time and end_time) and gmt_deleted is null")
    @ResultMap("process")
    List<Process> findProcessesByTrainIdAndTime(BigInteger trainId);
}
