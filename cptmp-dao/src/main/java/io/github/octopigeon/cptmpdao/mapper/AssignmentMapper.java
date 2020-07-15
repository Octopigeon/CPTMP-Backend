package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Assignment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 2.0
 * @date 2020/7/8
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
@Repository
@Mapper
public interface AssignmentMapper {
    /**
     * 添加日志
     * @param assignment：日志类
     */
    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted,  title, content, is_file, document_path";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted},  #{title}, #{content}, #{isFile}, #{documentPath}";
    String UPDATE_CONTENT = " gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted},title = #{title}, content = #{content},  is_file = #{isFile}";
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
     * @param gmtDeleted 删除日期
     */
    @Delete("delete from assignment")
    void removeAllAssignment(Date gmtDeleted);

    /**
     * 根据日志id删除对应的日志信息
     * @param id：日志id
     * @param gmtDeleted 删除日期
     */
    @Update("update assignment set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeAssignmentById(BigInteger id,Date gmtDeleted);

//    /**
//     * 根据用户id删除日志
//     * @param userId 用户id
//     * @param gmtDeleted 删除日期
//     */
//    @Update("update assignment set gmt_deleted = #{gmtDeleted} where idx_user_id = #{userId} and gmt_deleted is null")
//    void removeAssignmentByUserId(BigInteger userId,Date gmtDeleted);
//
//    /**
//     * 根据团队id删除
//     * @param teamId 团队id
//     * @param gmtDeleted 删除日期
//     */
//    @Update("update assignment set gmt_deleted = #{gmtDeleted} where idx_team_id = #{teamId} and gmt_deleted is null")
//    void removeAssignmentByTeamId(BigInteger teamId,Date gmtDeleted);

    /**
     * 根据日志id修改日志
     */
    @Update("update assignment set "+ UPDATE_CONTENT+" where id = #{id} and gmt_deleted is null")
    void updateAssignmentById(Assignment assignment);

    /**
     * 根据id改文件路径
     * @param id 日志id
     * @param gmtModified 修改时间
     * @param documentPath 文件路径
     * @return
     */
    @Update("update assignment set gmt_modified = #{gmtModified}, document_path = #{documentPath} where id = #{id} and gmt_deleted is null")
    Void updateAssignmentDocumentById(BigInteger id,Date gmtModified,String documentPath);

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
