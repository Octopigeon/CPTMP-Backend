package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.CptmpUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 2.0
 * @date 2020/7/7
 * @last-check-in 李国鹏
 * @date 2020/7/25
 */
@Repository
@Mapper
public interface CptmpUserMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, uk_email, phone_number, " +
            "gender, avatar, idx_name, uk_common_id, idx_organization_id, uk_username, idx_password, idx_role_name, enabled, " +
            "account_non_expired, credentials_non_expired, account_non_locked, introduction";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{email}, #{phoneNumber}, " +
            "#{gender}, #{avatar}, #{name}, #{commonId}, #{organizationId}, #{username}, #{password}, #{roleName}, #{enabled}, " +
            "#{accountNonExpired}, #{credentialsNonExpired}, #{accountNonLocked}, #{introduction}";
    String UPDATE_HEADER = "update cptmp_user set ";
    String UPDATE_TAIL_USERNAME = " where (uk_username = #{username})";
    String UPDATE_CONTENT = " gmt_modified = #{gmtModified}, " +
            "uk_email = #{email}, phone_number = #{phoneNumber}, gender = #{gender}, uk_username = #{username}, idx_password = #{password}, " +
            "idx_name = #{name}, uk_common_id = #{commonId}, idx_organization_id = #{organizationId},idx_role_name = #{roleName}, enabled = #{enabled}, account_non_expired = #{accountNonExpired}, " +
            "credentials_non_expired = #{credentialsNonExpired}, account_non_locked = #{accountNonLocked}, introduction = #{introduction}";

    String REMOVE_CONTENT = " gmt_deleted = #{gmtDeleted}, gmt_modified = null, uk_email = null, phone_number = null, " +
            "gender = null, avatar = null";

    /**
     * 插入实训
     *
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
     * 软删除用户(隐藏)
     *
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set gmt_deleted = #{gmtDeleted}  where gmt_deleted is null")
    void hideAllUsers(Date gmtDeleted);

    /**
     * 删除用户
     *
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set " + REMOVE_CONTENT + " where gmt_deleted is null")
    void removeAllUsers(Date gmtDeleted);


    /**
     * 恢复用户
     */
    @Update("update cptmp_user set gmt_deleted = null where gmt_deleted is not null")
    void restoreAllUsers();

    /**
     * 通过id删除（隐藏）
     *
     * @param id         用户id
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideUserById(BigInteger id, Date gmtDeleted);


    /**
     * 通过id删除
     *
     * @param id         用户id
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_user set " + REMOVE_CONTENT + " where id = #{id} and gmt_deleted is null")
    void removeUserById(BigInteger id, Date gmtDeleted);

    /**
     * 通过id恢复
     * @param id 用户id
     */
    @Update("update cptmp_user set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreUserById(BigInteger id);

    /**
     * 通过用户名恢复
     * @param username 用户名
     */
    @Update("update cptmp_user set gmt_deleted = null where uk_username = #{username} and gmt_deleted is not null")
    void restoreUserByUsername(String username);

//
//    /**
//     * 通过organizationId删除
//     *
//     * @param organizationId   组织id
//     * @param gmtDeleted 删除日期
//     */
//    @Update("update cptmp_user set " + REMOVE_CONTENT + " where idx_organization_id = #{organizationId} and gmt_deleted is null")
//    void hideUsersByOrganizationId(BigInteger organizationId, Date gmtDeleted);

    /**
     * 通过用户名获取用户，可以用来进行登录验证
     *
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
     *
     * @param id id
     * @return 用户
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where id = #{id} and gmt_deleted is null")
    @ResultMap("user")
    CptmpUser findUserById(BigInteger id);

    /**
     * 根据email查询
     *
     * @param email email
     * @return 用户
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where uk_email = #{email} and gmt_deleted is null")
    @ResultMap("user")
    CptmpUser findUserByEmail(String email);

    /**
     * 根据姓名模糊查询
     * @param name 姓名
     * @return 用户列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where idx_name like concat('%', #{name}, '%') and gmt_deleted is null")
    @ResultMap("user")
    List<CptmpUser> findUsersByName(String name);

    /**
     * 根据组织id查询
     * @param organizationId 组织id
     * @return 用户列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where idx_organization_id = #{organizationId} and gmt_deleted is null")
    @ResultMap("user")
    List<CptmpUser> findUsersByOrganizationId(BigInteger organizationId);

    /**
     * 根据角色名查询
     * @param roleName 角色名
     * @return 用户列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where idx_role_name = #{roleName} and gmt_deleted is null")
    @ResultMap("user")
    List<CptmpUser> findUsersByRoleName(String roleName);

    /**
     * 根据组织id和角色名查询
     * @param organizationId 组织id
     * @param roleName 角色名
     * @return 用户列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where idx_organization_id = #{organizationId} and idx_role_name = #{roleName} and gmt_deleted is null")
    @ResultMap("user")
    List<CptmpUser> findUsersByGroupFilter(BigInteger organizationId, String roleName);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    @Select("select idx_password from cptmp_user where uk_username = #{username} and gmt_deleted is null")
    @Result(column = "idx_password", property = "password", jdbcType = JdbcType.VARCHAR)
    String findPasswordByUsername(String username);

    /**
     * 得到所有用户（调试性方法）
     *
     * @return 所有用户的列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where gmt_deleted is null")
    @ResultMap(value = "user")
    List<CptmpUser> findAllUsers();

    /**
     * 根据id更新
     * @param cptmpUser 用户
     */
    @Update(UPDATE_HEADER + UPDATE_CONTENT + " where (id = #{id}) and gmt_deleted is null")
    void updateUserById(CptmpUser cptmpUser);

    /**
     * 根据用户名更新
     * @param cptmpUser 用户
     */
    @Update(UPDATE_HEADER + UPDATE_CONTENT + " where (uk_username = #{username}) and gmt_deleted is null")
    void updateUserByUserName(CptmpUser cptmpUser);

    /**
     * 根据用户名更新是否注销
     * @param username 用户名
     * @param enabled 是否注销
     */
    @Update(UPDATE_HEADER + "enabled = #{enabled}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updateEnabledByUsername(String username, Boolean enabled);

    /**
     * 根据用户名更新头像
     * @param username 用户名
     * @param gmtModified 修改时间
     * @param avatar 头像url
     */
    @Update(UPDATE_HEADER + "gmt_modified=#{gmtModified}, avatar=#{avatar}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updateAvatarByUsername(String username, Date gmtModified, String avatar);


    /**
     * 根据用户名更新密码
     * @param username 用户名
     * @param gmtModified 修改时间
     * @param password 密码
     */
    @Update(UPDATE_HEADER + "idx_password = #{password}" + UPDATE_TAIL_USERNAME + " and gmt_deleted is null")
    void updatePasswordByUsername(String username, Date gmtModified, String password);

}
