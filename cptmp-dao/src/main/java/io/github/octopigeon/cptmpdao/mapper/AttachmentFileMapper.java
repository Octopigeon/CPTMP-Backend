package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Repository
@Mapper
public interface AttachmentFileMapper {

    /**
     * 新增文件
     * @param file:attachment类
     */
    @Insert("insert into attachment_file (gmt_create, uk_file_name, uk_file_path, origin_name, file_size, idx_file_type, idx_user_id, idx_team_id) " +
            "values (#{gmtCreate}, #{fileName}, #{filePath}, #{originName}, #{fileSize}, #{fileType}, #{userId}, #{teamId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addAttachmentFile(AttachmentFile file);

    /**
     * 根据文件名进行文件删除
     * @param fileName：文件名
     */
    @Delete("delete from attachment_file where uk_file_name = #{fileName}")
    void removeAttachmentFile(String fileName);

    /**
     * 修改原始文件名称
     * @param fileName 存储文件名
     * @param gmtModified 修改时间
     * @param originName 用户所见文件名
     */
    @Update("update attachment_file set gmt_modified = #{gmtModified}, origin_name = #{originName} where uk_file_name = #{fileName}")
    void updateOriginNameByFileName(String fileName, Date gmtModified, String originName);

    /**
     * 根据用户名查找文件路径与原始文件名
     * @param fileName 唯一文件名
     */
    @Select("select uk_file_path, origin_name from attachment_file where uk_file_name = {fileName}")
    @Results({
            @Result(column = "uk_file_path", property = "filePath", jdbcType =JdbcType.VARCHAR),
            @Result(column = "origin_name", property = "originName", jdbcType = JdbcType.VARCHAR),
    })
    AttachmentFile findAttachmentFileByfileName(String fileName);

    /**
     * 查询用户上传的文件
     * @param userId 用户id
     * @return 文件类列表
     */
    @Select("select gmt_create, uk_file_name, uk_file_path, origin_name, file_size from attachment_file where idx_user_id = {userId}")
    @Results({
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "uk_file_name", property = "fileName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_file_path", property = "filePath", jdbcType =JdbcType.VARCHAR),
            @Result(column = "origin_name", property = "originName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "file_size", property = "fileSize", jdbcType = JdbcType.BIGINT)
    })
    List<AttachmentFile> findAttachmentFilesByUserId(BigInteger userId);

    /**
     * 查询团队上传的文件
     * @param teamId 团队id
     * @return 文件类列表
     */
    @Select("select gmt_create, uk_file_name, uk_file_path, origin_name, file_size from attachment_file where idx_team_id = {teamId}")
    @Results({
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "uk_file_name", property = "fileName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_file_path", property = "filePath", jdbcType =JdbcType.VARCHAR),
            @Result(column = "origin_name", property = "originName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "file_size", property = "fileSize", jdbcType = JdbcType.BIGINT)
    })
    List<AttachmentFile> findAttachmentFilesByTeamId(BigInteger teamId);
}
