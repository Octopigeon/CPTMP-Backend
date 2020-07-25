package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Assignment;
import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 目前处于废弃不用状态
 * @author 李国鹏
 * @version 2.0
 * @date 2020/7/8
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/25
 */
@Deprecated
@Repository
@Mapper
public interface AssignmentMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted,  title, content, is_file, document_path";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted},  #{title}, #{content}, #{isFile}, #{documentPath}";
    String UPDATE_CONTENT = " gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted},title = #{title}, content = #{content},  is_file = #{isFile}";

    /**
     *插入作业
     * @param assignment 作业
     */
    @Insert("insert into assignment (" +COLUMNS+ ") values (" +PROPS+ ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addAssignment(Assignment assignment);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from assignment")
    void removeAllAssignmentTest();

    /**
     *软删除作业
     * @param gmtDeleted 删除日期
     */
    @Update("update assignment set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void hideAllAssignment(Date gmtDeleted);

    /**
     *软删除恢复作业
     */
    @Update("update assignment set gmt_deleted = null where gmt_deleted is not null")
    void restoreAllAssignment();

    /**
     * 根据作业id删除对应的作业信息
     * @param id：作业id
     * @param gmtDeleted 删除日期
     */
    @Update("update assignment set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideAssignmentById(BigInteger id, Date gmtDeleted);

//    /**
//     * 根据用户id删除日志
//     * @param userId 用户id
//     * @param gmtDeleted 删除日期
//     */
//    @Update("update assignment set gmt_deleted = #{gmtDeleted} where idx_user_id = #{userId} and gmt_deleted is null")
//    void hideAssignmentByUserId(BigInteger userId, Date gmtDeleted);
//
//    /**
//     * 根据用户id恢复日志
//     * @param userId 用户id
//     */
//    @Update("update assignment set gmt_deleted = null where idx_user_id = #{userId} and gmt_deleted is not null")
//    void restoreAssignmentByUserId(BigInteger userId);
//
//    /**
//     * 根据团队id删除
//     * @param teamId 团队id
//     * @param gmtDeleted 删除日期
//     */
//    @Update("update assignment set gmt_deleted = #{gmtDeleted} where idx_team_id = #{teamId} and gmt_deleted is null")
//    void hideAssignmentByTeamId(BigInteger teamId, Date gmtDeleted);
//    /**
//     * 根据团队id恢复
//     * @param teamId 团队id
//     */
//    @Update("update assignment set gmt_deleted = null where idx_team_id = #{teamId} and gmt_deleted is not null")
//    void restoreAssignmentByTeamId(BigInteger teamId);

    /**
     * 根据作业id修改作业
     * @param assignment 作业
     */
    @Update("update assignment set "+ UPDATE_CONTENT+" where id = #{id} and gmt_deleted is null")
    void updateAssignmentById(Assignment assignment);

    /**
     * 根据id改文件路径
     * @param id 日志id
     * @param gmtModified 修改时间
     * @param documentPath 文件路径
     */
    @Update("update assignment set gmt_modified = #{gmtModified}, document_path = #{documentPath} where id = #{id} and gmt_deleted is null")
    void updateAssignmentDocumentById(BigInteger id,Date gmtModified,String documentPath);

//
//    /**
//     * 根据用户id修改日志
//     */
//    @Update("update assignment set "+UPDATE_CONTENT+" where idx_user_id = #{userId} and gmt_deleted is null")
//    void updateAssignmentByUserId(Assignment Assignment);
//
//    /**
//     * 根据用户id改文件路径
//     * @param userId 用户id
//     * @param gmtModified 修改时间
//     * @param documentPath 文件路径
//     * @return
//     */
//    @Update("update assignment set gmt_modified = #{gmtModified}, document_path = #{documentPath} where idx_user_id = #{userId} and gmt_deleted is null")
//    void updateAssignmentDocumentByUserId(BigInteger userId,Date gmtModified, String documentPath);
//
//
//    /**
//     * 根据团队id修改日志
//     */
//    @Update("update assignment set "+UPDATE_CONTENT+" where idx_team_id = #{teamId} and gmt_deleted is null")
//    void updateAssignmentByTeamId( Assignment Assignment);
//
//    /**
//     * 根据团队改文件路径
//     * @param teamId 团队id
//     * @param gmtModified 修改时间
//     * @param documentPath 文件路径
//     *
//     */
//    @Update("update assignment set gmt_modified = #{gmtModified}, document_path = #{documentPath} where idx_team_id = #{teamId} and gmt_deleted is null")
//    void updateAssignmentDocumentByTeamId(BigInteger teamId,Date gmtModified,String documentPath);


    /**
     * 查询所有日志
     * @return 日志列表
     */
    @Select("select id, " + COLUMNS + " from assignment where gmt_deleted is null")
    @Results(id = "assignment", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_file", property = "isFile", jdbcType = JdbcType.INTEGER),
            @Result(column = "document_path", property = "documentPath", jdbcType = JdbcType.VARCHAR)

    })
    List<Assignment> findAllAssignment();

    /**
     * 根据id查找
     * @param id id
     * @return 作业
     */
    @Select("select id, " + COLUMNS + " from assignment where id = #{id} and gmt_deleted is null")
    @ResultMap("assignment")
    Assignment findAssignmentById(BigInteger id);

//    /**
//     * 根据用户id查找日志
//     * @param userId：用户id
//     * @return 日志
//     */
//    @Select("select id, " + COLUMNS + " from assignment where idx_user_id = #{userId} and gmt_deleted is null")
//    @ResultMap("assignment")
//    List<Assignment> findAssignmentByUserId(BigInteger userId);
//
//    /**
//     * 根据用户id查找日志
//     * @param teamId：用户id
//     * @return 日志
//     */
//    @Select("select id, " + COLUMNS + " from assignment where idx_team_id = #{teamId} and gmt_deleted is null")
//    @ResultMap("assignment")
//    List<Assignment> findAssignmentByTeamId(BigInteger teamId);



}
