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
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Repository
@Mapper
public interface ProcessMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, train_id, start_time, end_time";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainId}, #{startTime}, #{endTime}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, train_id = #{trainId}" +
            ", start_time = #{startTime}, end_time = #{endTime}";
    String BY_ID_TAIL = " where id = #{id} and gmt_deleted is null";
    String SOFT_DELETE_TAIL = " and gmt_deleted is null";

    @Insert("insert into cptmp_process (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addProcess(Process process);

    /**
     * 删除一个实训下的所有流程（当实训结束的时候）
     * @param gmtDelete
     * @param trainId
     */
    @Update("update cptmp_process set gmt_deleted = #{gmtDeleted} where train_id = #{trainId} and gmt_deleted is null")
    void hideProcessesByTrainId(Date gmtDelete, BigInteger trainId);

    /**
     * 删除一个实训下的所有流程（当实训结束的时候）
     * @param trainId
     */
    @Update("update cptmp_process set gmt_deleted = null where train_id = #{trainId} and gmt_deleted is not null")
    void restoreProcessesByTrainId(BigInteger trainId);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from cptmp_process")
    void removeAllProcesses();

    @Update("update cptmp_process set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_delete is null")
    void hideProcessById(Date gmtDelete, BigInteger id);

    @Update("update cptmp_process set gmt_deleted = null where id = #{id} and gmt_delete is not null")
    void restoreProcessById(BigInteger id);

    @Update("update cptmp_process set " + UPDATE_CONTENT + BY_ID_TAIL)
    void updateProcessById(Process process);

    @Select("select id, " + COLUMNS + " from cptmp_process" + BY_ID_TAIL)
    @Results(id = "process", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "train_id", property = "trainId", jdbcType = JdbcType.BIGINT),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.DATE),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.DATE)
    })
    Process findProcessById(BigInteger id);

    @Select("select id, " + COLUMNS + " from cptmp_process where train_id = #{trainId}" + SOFT_DELETE_TAIL)
    @ResultMap("process")
    List<Process> findProcessesByTrainId(BigInteger trainId);

    @Select("select id, " + COLUMNS + " from cptmp_process where gmt_deleted is null")
    @ResultMap("process")
    List<Process> findAllProcesses();


}
