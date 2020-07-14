package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.CptmpUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 2.0
 * @date 2020/7/7
 *
 * @last-check-in 魏啸冲
 * @date 2020/7/13
 */
@Repository
@Mapper
public interface CptmpUserMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, uk_email, phone_number, " +
            "gender, avatar, idx_name, uk_common_id, idx_organization_id, uk_username, idx_password, idx_role_name, enabled, " +
            "account_non_expired, credentials_non_expired, account_non_locked";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{email}, #{phoneNumber}, " +
            "#{gender}, #{avatar}, #{name}, #{commonId}, #{organizationId}, #{username}, #{password}, #{roleName}, #{enabled}, " +
            "#{accountNonExpired}, #{credentialsNonExpired}, #{accountNonLocked}";
    String UPDATE_HEADER = "update cptmp_user set ";
    String UPDATE_TAIL_USERNAME = " where (uk_username = #{username})";
    String UPDATE_CONTENT = " gmt_modified = #{gmtModified}, " +
            "uk_email = #{email}, phone_number = #{phoneNumber}, gender = #{gender}, uk_username = #{username}, idx_password = #{password}, " +
            "idx_name = #{name}, uk_common_id = #{commonId}, idx_organization_id = #{organizationId},idx_role_name = #{roleName}, enabled = #{enabled}, account_non_expired = #{accountNonExpired}, " +
            "credentials_non_expired = #{credentialsNonExpired}, account_non_locked = #{accountNonLocked}";

    String REMOVE_CONTENT=" gmt_modified = null, gmt_deleted = null, uk_email = null, phone_number = null, " +
            "gender = null, avatar = null";
    /**
     * 插入实训
     * @param cptmpUser 用户
     */
    @Insert("insert into cptmp_user (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(CptmpUser cptmpUser);

    /**
     * 测试删除
     */
    @Deprecated
    @Delete("delete from cptmp_user")
    void removeAllUsersTest();


    /**
     * 删除用户(隐藏)
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void hideAllUsers(Date gmtDeleted);

    /**
     * 删除用户
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set "+ REMOVE_CONTENT +" where gmt_deleted is null")
    void removeAllUsers(Date gmtDeleted);


    /**
     * 恢复用户
     */
    @Update("update cptmp_user set gmt_deleted = null")
    void restoreAllUsers();

    /**
     * 通过id删除（隐藏）
     * @param id 用户id
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideUserById(BigInteger id, Date gmtDeleted);

    /**
     * 通过id删除
     * @param id 用户id
     */
    @Update("update cptmp_user set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void removeUserById(BigInteger id);

    /**
     * 通过id恢复
     * @param id 用户id
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set "+REMOVE_CONTENT+" where id = #{id}")
    void restoreUserById(BigInteger id, Date gmtDeleted);

    /**
     * 通过用户名获取用户，可以用来进行登录验证
     * @param username 用户名，唯一
     * @return 用户
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where uk_username = #{username} and gmt_deleted is null")
    @Results(id = "user", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "uk_email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone_number", property = "phoneNumber", jdbcType = JdbcType.DECIMAL),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.TINYINT),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_common_id", property = "commonId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_organization_id", property = "organizationId", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.TINYINT),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.TINYINT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.TINYINT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.TINYINT),

    })
    CptmpUser findUserByUsername(String username);

    /**
     * 根据id查询
     * @param userId id
     * @return 用户
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where id = #{userID} and gmt_deleted is null")
    @ResultMap("user")
    CptmpUser findUserById(BigInteger userId);

    /**
     * 根据email查询
     * @param email email
     * @return 用户
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where uk_email = #{email} and gmt_deleted is null")
    @ResultMap("user")
    CptmpUser findUserByEmail(String email);

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 用户
     */
    @Select("select idx_password from cptmp_user where uk_username = #{username} and gmt_deleted is null")
    String findPasswordByUsername(String username);

    /**
     * 得到所有用户（调试性方法）
     * @return 所有用户的列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where gmt_deleted is null")
    @ResultMap(value = "user")
    List<CptmpUser> findAllUsers();

    /**
     *根据id更新
     */
    @Update(UPDATE_HEADER+UPDATE_CONTENT+" where (id = #{id}) and gmt_deleted is null")
    void updateUserById(CptmpUser cptmpUser);

    /**
     *根据用户名更新
     */
    @Update(UPDATE_HEADER+UPDATE_CONTENT+UPDATE_TAIL_USERNAME + " where (uk_username = #{username}) and gmt_deleted is null")
    void updateUserByUserName(CptmpUser cptmpUser);

    /**
     *根据用户名更新是否注销
     */
    @Update(UPDATE_HEADER + "enabled = #{enabled}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updateEnabledByUsername(String username, Boolean enabled);

    /**
     *根据用户名更新账号
     */
    @Update(UPDATE_HEADER + "account_non_expired = #{accountNonExpired}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updateAccountNonExpiredByUsername(String username, Boolean accountNonExpired);

    @Update(UPDATE_HEADER + "credentials_non_expired = #{credentialsNonExpired}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updateCredentialsNonExpiredByUsername(String username, Boolean credentialsNonExpired);

    @Update(UPDATE_HEADER + "account_non_locked = #{accountNonLocked}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updateAccountNonLockedByUsername(String username, Boolean accountNonLocked);



    @Update(UPDATE_HEADER + "gmt_modified=#{gmtModified}, avatar=#{avatar}" + UPDATE_TAIL_USERNAME+ " and gmt_deleted is null")
    void updateAvatarByUsername(String username, Date gmtModified, String avatar);
    /**
     * 更新用户常规信息
     * @param username
     * @param gmtModified
     * @param gender
     */
    @Update(UPDATE_HEADER + "gmt_modified = #{gmtModified}, idx_name = #{name},gender = #{gender}" + UPDATE_TAIL_USERNAME)
    void updateUserInfoByUsername(String username, Date gmtModified, String name, boolean gender);

    @Update(UPDATE_HEADER + "idx_password = #{password}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updatePasswordByUsername(String username, Date gmtModified, String password);

    @Update(UPDATE_HEADER + "gmt_modified = #{gmtModified}, avatar = #{avatar} where id = #{userId}")
    void updateAvatarById(BigInteger userId, Date gmtModified, String avatar);

    @Update(UPDATE_HEADER + "gmt_modified = #{gmtModified}, uk_face_info = #{faceInfo} where id = #{userId}")
    void updateFaceInfoById(BigInteger userId, Date gmtModified, String faceInfo);
}
