package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 陈若琳
 * @version 2.1
 * @date 2020/07/12
 * @last-check-in 李国鹏
 * @date 2020/07/21
 */
@Repository
@Mapper
public interface OrganizationMapper {

    String COLUMNS = "uk_name, uk_real_name, organization_description, official_website_url, gmt_modified" +
            ", gmt_create, gmt_deleted, invitation_code";
    String PROPS = "#{name}, #{realName}, #{description}, #{websiteUrl}, #{gmtModified}, #{gmtCreate}, #{gmtDeleted}, #{invitationCode}";

    String UPDATE_CONTENT = "uk_name = #{name}, organization_description = #{description}, uk_real_name = #{realName}," +
            "official_website_url = #{websiteUrl}, gmt_modified = #{gmtModified}, invitation_code = #{invitationCode}";

    /**
     * 创建组织
     * @param organization 组织对象
     */
    @Insert("insert into cptmp_organization (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addOrganization(Organization organization);

    /**
     * 根据id修改组织信息
     */
    @Update("update cptmp_organization set " + UPDATE_CONTENT + " where id = #{id} and gmt_deleted is null")
    void updateOrganizationById(Organization organization);

    /**
     * 测试删除
     */
    @Deprecated
    @Delete("delete from cptmp_organization")
    void removeAllOrganizationTest();

    /**
     * 删除所有组织
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_organization set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void hideAllOrganization(Date gmtDeleted);

    /**
     * 恢复所有组织
     */
    @Update("update cptmp_organization set gmt_deleted = null where gmt_deleted is not null")
    void restoreAllOrganization();

    /**
     * 根据id删除组织(软删除）
     *
     * @param id         组织id
     * @param gmtDeleted 删除时间
     */
    @Update("update cptmp_organization set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideOrganizationById(BigInteger id, Date gmtDeleted);

    /**
     * 根据id恢复组织(软删除）
     * @param id         组织id
     */
    @Update("update cptmp_organization set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreOrganizationById(BigInteger id);


    /**
     * 查询所有组织
     * @return 组织列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_organization where gmt_deleted is null")
    @Results(id = "cptmpOrganization", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "uk_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_real_name", property = "realName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "organization_description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "official_website_url", property = "websiteUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "invitation_code", property = "invitationCode", jdbcType = JdbcType.VARCHAR)
    })
    List<Organization> findAllOrganization();


    /**
     * 根据id查询组织
     *
     * @param id 组织id
     * @return 组织对象
     */
    @Select("select id, " + COLUMNS + " from cptmp_organization where id = #{id} and gmt_deleted is null")
    @ResultMap("cptmpOrganization")
    Organization findOrganizationById(BigInteger id);

    /**
     * 根据名称查询组织
     * @param name 组织名称
     * @return 组织列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_organization where uk_name like concat('%', #{name}, '%') and gmt_deleted is null")
    @ResultMap("cptmpOrganization")
    List<Organization> findOrganizationByName(String name);

    /**
     * 根据组织邀请码进行查询
     * @param invitationCode 邀请码
     * @return 组织
     */
    @Select("select id, " + COLUMNS + " from cptmp_organization where invitation_code = #{invitationCode} and gmt_deleted is null")
    @ResultMap("cptmpOrganization")
    Organization findOrganizationByInvitationCode(String invitationCode);

    /**
     * 根据名字查询
     * @param realName 名称
     * @return 组织列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_organization where uk_real_name like concat('%', #{realName}, '%') and gmt_deleted is null")
    @ResultMap("cptmpOrganization")
    List<Organization> findOrganizationByRealName(String realName);
}
