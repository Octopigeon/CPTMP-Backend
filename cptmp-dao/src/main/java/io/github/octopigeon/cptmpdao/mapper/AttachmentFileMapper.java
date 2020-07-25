package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 2.0
 *
 * @last-check-in 李国鹏
 * @date 2020/7/25
 */
@Repository
@Mapper
public interface AttachmentFileMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, uk_file_name, uk_file_path,uk_file_url, origin_name, file_size, idx_file_type";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{fileName}, #{filePath}, #{fileUrl}, #{originName}, #{fileSize}, #{fileType}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, uk_file_name = #{fileName}, " +
            " uk_file_path = #{filePath}, uk_file_url = #{fileUrl}, origin_name = #{originName}, file_size = #{fileSize}, idx_file_type = #{fileType}";


    /**
     * 添加文件
     * @param file 文件
     */
    @Insert("insert into attachment_file (" + COLUMNS + ") values ( " + PROPS +" )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addAttachmentFile(AttachmentFile file);


    /**
     * 测试
     */
    @Deprecated
    @Delete("delete from attachment_file")
    void removeAllAttachmentFileTest();

    /**
     * 软删除所有文件
     * @param gmtDeleted 删除日期
     */
    @Update("update attachment_file set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void hideAllAttachmentFile(Date gmtDeleted);

    /**
     * 恢复所有文件
     */
    @Update("update attachment_file set gmt_deleted = null where gmt_deleted is not null")
    void restoreAllAttachmentFile();

    /**
     * 根据文件id进行文件删除
     * @param id：文件id
     * @param gmtDeleted 删除日期
     */
    @Update("update attachment_file set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideAttachmentFileById(BigInteger id, Date gmtDeleted);

    /**
     * 根据文件id进行文件恢复
     * @param id：文件id
     */
    @Update("update attachment_file set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreAttachmentFileById(BigInteger id);

    /**
     * 根据文件名进行文件删除
     * @param fileName：文件名
     * @param gmtDeleted 删除日期
     */
    @Update("update attachment_file set gmt_deleted = #{gmtDeleted} where uk_file_name = #{fileName} and gmt_deleted is null")
    void hideAttachmentFileByName(String fileName, Date gmtDeleted);

    /**
     * 根据文件名进行文件恢复
     * @param fileName：文件名
     */
    @Update("update attachment_file set gmt_deleted = null where uk_file_name = #{fileName} and gmt_deleted is not null")
    void restoreAttachmentFileByName(String fileName);

    /**
     * 根据id修改
     * @param attachmentFile 文件
     */
    @Update("update attachment_file set "+ UPDATE_CONTENT +" where id = #{id} and gmt_deleted is null ")
    void updateAttachmentFileById(AttachmentFile attachmentFile);

    /**
     * 根据文件名修改
     * @param attachmentFile 文件
     */
    @Update("update attachment_file set "+ UPDATE_CONTENT +" where uk_file_name = #{fileName} and gmt_deleted is null")
    void updateAttachmentFileByFileName(AttachmentFile attachmentFile);

    /**
     * 修改原始文件名称
     * @param fileName 存储文件名
     * @param gmtModified 修改时间
     * @param originName 用户所见文件名
     */
    @Update("update attachment_file set gmt_modified = #{gmtModified}, origin_name = #{originName} where uk_file_name = #{fileName} and gmt_deleted is null ")
    void updateOriginNameByFileName(String fileName, Date gmtModified, String originName);


    /**
     * 查找所有记录
     * @return 记录列表
     */
    @Select("select id, " + COLUMNS + " from attachment_file where gmt_deleted is null")
    @Results(id = "attachmentFile", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "uk_file_name", property = "fileName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_file_path", property = "filePath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_file_url", property = "fileUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "origin_name", property = "originName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_file_type", property = "fileType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "file_size", property = "fileSize", jdbcType = JdbcType.BIGINT)
    })
    List<AttachmentFile> findAllAttachmentFile();
    /**
     * 查找id所有记录
     * @param id id
     * @return 根据id记录列表
     */
    @Select("select id, " + COLUMNS + " from attachment_file where id = #{id} and gmt_deleted is null")
    @ResultMap("attachmentFile")
    AttachmentFile findAttachmentFileById(BigInteger id);
    /**
     * 查找文件名所有记录
     * @param fileName 文件名
     * @return 根据文件名记录列表
     */
    @Select("select id, " + COLUMNS + " from attachment_file where uk_file_name = #{fileName} and gmt_deleted is null")
    @ResultMap("attachmentFile")
    AttachmentFile findAttachmentFileByFileName(String fileName);
//    /**
//     * 根据文件名查找文件路径与原始文件名
//     * @param fileName 唯一文件名
//     */
//    @Select("select uk_file_path, origin_name from attachment_file where uk_file_name = #{fileName}")
//    @Results({
//            @Result(column = "uk_file_path", property = "filePath", jdbcType =JdbcType.VARCHAR),
//            @Result(column = "origin_name", property = "originName", jdbcType = JdbcType.VARCHAR),
//    })
//    AttachmentFile findPathAndOriginNameByfileName(String fileName);


}
