package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.CptmpUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 *
 * @last-check-in anlow
 * @date 2020/7/9
 */
@Repository
@Mapper
public interface CptmpUserMapper {

    String COLUMNS = "gmt_create, gmt_modified, introduction, uk_email, phone_number, " +
            "gender, avatar, uk_username, idx_password, idx_role_name, enabled, " +
            "account_non_expired, credentials_non_expired, account_non_locked, invitation_code, nickname";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{introduction}, #{email}, #{phoneNumber}, " +
            "#{male}, #{avatar}, #{username}, #{password}, #{roleName}, #{enabled}, " +
            "#{accountNonExpired}, #{credentialsNonExpired}, #{accountNonLocked}, #{invitationCode}, #{nickname}";
    String UPDATE_HEADER = "update cptmp_user set ";
    String UPDATE_TAIL_USERNAME = " where (uk_username = #{username})";

    @Insert("insert into cptmp_user (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(CptmpUser cptmpUser);

    @Delete("delete from cptmp_user")
    void removeAllUsers();

    /**
     * 通过用户名获取用户，可以用来进行登录验证
     * @param username 用户名，唯一
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where uk_username = #{username}")
    @Results(id = "user", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "introduction", property = "introduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone_number", property = "phoneNumber", jdbcType = JdbcType.DECIMAL),
            @Result(column = "gender", property = "male", jdbcType = JdbcType.TINYINT),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname", property = "nickname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.TINYINT),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.TINYINT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.TINYINT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.TINYINT),
            @Result(column = "invitation_code", property = "invitationCode", jdbcType = JdbcType.VARCHAR)
    })
    CptmpUser findUserByUsername(String username);

    /**
     * 得到所有用户（调试性方法）
     * @return 所有用户的列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user")
    @ResultMap(value = "user")
    List<CptmpUser> findAllUsers();

    @Update(UPDATE_HEADER + "enabled = #{enabled}" + UPDATE_TAIL_USERNAME)
    void updateEnabledByUsername(String username, Boolean enabled);

    @Update(UPDATE_HEADER + "account_non_expired = #{accountNonExpired}" + UPDATE_TAIL_USERNAME)
    void updateAccountNonExpiredByUsername(String username, Boolean accountNonExpired);

    @Update(UPDATE_HEADER + "credentials_non_expired = #{credentialsNonExpired}" + UPDATE_TAIL_USERNAME)
    void updateCredentialsNonExpiredByUsername(String username, Boolean credentialsNonExpired);

    @Update(UPDATE_HEADER + "account_non_locked = #{accountNonLocked}" + UPDATE_TAIL_USERNAME)
    void updateAccountNonLockedByUsername(String username, Boolean accountNonLocked);

    @Update(UPDATE_HEADER + "invitation_code = #{invitationCode}" + UPDATE_TAIL_USERNAME)
    void updateInvitationCodeByUsername(String username, String invitationCode);

    @Update(UPDATE_HEADER + "gmt_modified=#{gmtModified}, avatar=#{avatar}" + UPDATE_TAIL_USERNAME)
    void updateAvatarByUsername(String username, Date gmtModified, String avatar);
    /**
     * 更新用户常规信息
     * @param username
     * @param nickname
     * @param gmtModified
     * @param introduction
     * @param male
     */
    @Update(UPDATE_HEADER + "nickname = #{nickname},gmt_modified = #{gmtModified},introduction = #{introduction},gender = #{male}" + UPDATE_TAIL_USERNAME)
    void updateUserInfoByUsername(String username, String nickname, Date gmtModified, String introduction, boolean male);

}
