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
 * @version 2.0
 * @date 2020/07/12
 * @last-check-in 李国鹏
 * @date 2020/07/12
 */
@Repository
@Mapper
public interface OrganizationMapper {

    String COLUMNS="uk_organization_name,organization_description,official_website_url,gmt_modified,gmt_create,gmt_deleted,invitation_code";
    String PROPS="#{name}, #{organizationDescription},#{websiteUrl},#{gmtModified},#{gmtCreate},#{gmtDeleted},#{invitationCode}";

    String UPDATE_CONTENT="uk_organization_name = #{name},organization_description = #{organizationDescription}," +
            "official_website_url = #{websiteUrl}, gmt_modified = #{gmtModified},invitation_code = #{invitationCode}";

    /**
     * 创建组织
     * @param organization 组织对象
     */
    @Insert("insert into cptmp_organization (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addOrganization(Organization organization);

    /**
     * 根据id修改组织信息
     * @param id 组织id
     * @param organizationDescription 组织简介
     * @param websiteUrl 组织官网
     * @param gmtModified 最后修改时间
     * @param invitationCode 邀请码
     */
    @Update("update cptmp_organization set "+UPDATE_CONTENT+"where id = #{id} and gmt_deleted is null")
    void updateOrganizationById(BigInteger id,Date gmtModified,String name,String organizationDescription,String websiteUrl,String invitationCode);

    /**
     * 测试删除
     * @param gmtDeleted 删除日期
     */
    @Deprecated
    @Delete("delete from cptmp_organization")
    void removeAllOrganizationTest(Date gmtDeleted);

    /**
     * 删除所有组织
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_organization set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeAllOrganization(Date gmtDeleted);
    /**
     * 根据id删除组织(软删除）
     * @param id 组织id
     * @param gmtDeleted 删除时间
     */
    @Update("update cptmp_organization set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeOrganizationById(BigInteger id,Date gmtDeleted);


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
            @Result(column = "uk_organization_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "organization_description", property = "organizationDescription", jdbcType = JdbcType.VARCHAR),
            @Result(column = "official_website_url", property = "websiteUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "invitation_code", property = "invitationCode", jdbcType = JdbcType.VARCHAR)
    })
    List<Organization> findAllOrganization();


    /**
     * 根据id查询组织
     * @param id 组织id
     * @return 组织列表
     */
    @Select("select uk_id, " + COLUMNS + " from organization where uk_id = #{id} and gmt_deleted is null")
    @ResultMap("cptmpOrganization")
    Organization findOrganizationById(BigInteger id);

    /**
     * 根据名称查询组织
     * @param name 组织名称
     * @return 组织列表
     */
    @Select("select uk_id, " + COLUMNS + " from university where uk_organization_name = #{name} and gmt_deleted is null")
    @ResultMap("cptmpOrganization")
    Organization findOrganizationByName(String name);


}
