package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in 李国鹏
 * @date 2020/7/21
 */
@Repository
@Mapper
public interface PasswordResetTokenMapper {

    String COLUMNS = "gmt_create, gmt_modified, uk_token, idx_email";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{token}, #{email}";

    /**
     * 添加密码
     * @param passwordResetToken 密码
     */
    @Insert("insert password_reset_token (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addPasswordResetToken(PasswordResetToken passwordResetToken);


    /**
     * 测试删除
     */
    @Deprecated
    @Delete("delete from password_reset_token")
    void removeAllPasswordResetTokens();

    /**
     * 测试
     * @param token token
     * @param email email
     */
    @Deprecated
    @Update("update password_reset_token set uk_token = #{token} where idx_email = #{email}")
    void updateTokenByEmail(String token, String email);

    /**
     * 用于验证后删除该用户的所有token
     * @param email token对应的用户email
     */
    @Delete("delete from password_reset_token where idx_email = #{email}")
    void removePasswordResetTokensByEmail(String email);

    /**
     * 用于比对某一token时，需要从数据库中得到该token的完整信息（目前主要是拿到创建时间）
     * @param token 当前token
     * @return 返回找到的token对象
     */
    @Select("select id, " + COLUMNS + " from password_reset_token where uk_token = #{token}")
    @ResultMap("passwordResetToken")
    PasswordResetToken findPasswordResetTokenByToken(String token);

    /**
     * 用于发现该token过期时，删除该token
     * @param token 当前token
     */
    @Delete("delete from password_reset_token where uk_token = #{token}")
    void removePasswordResetTokenByToken(String token);

    /**
     * 测试用
     * @return 所有token
     */
    @Deprecated
    @Select("select * from password_reset_token")
    @Results(id = "passwordResetToken", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "uk_token", property = "token", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_email", property = "email", jdbcType = JdbcType.VARCHAR)
    })
    List<PasswordResetToken> findAllPasswordResetTokens();


}
