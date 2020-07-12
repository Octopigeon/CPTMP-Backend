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
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 陈若琳
 * @date 2020/07/12
 */
@Repository
@Mapper
public interface OrganizationMapper {

    String COLUMNS="uk_organization_name,description,official_website_url,gmt_modified,gmt_create,gmt_delete,invitation_code";
    String PROPS="#{name}, #{description},#{websiteUrl},#{gmtModified},#{gmtCreate},#{gmtDelete},#{invitationCode}";

    String UPDATE_CONTENT="uk_organization_name = #{name},description = #{description}," +
            "official_website_url = #{websiteUrl}, gmt_modified = #{gmtModified},invitation_code = #{invitationCode}";

    /**
     * 创建组织
     * @param organization 组织对象
     */
    @Insert("insert into organization (" + COLUMNS + ") " +
            "values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addOrganization(Organization organization);

    /**
     * 根据id修改组织信息
     * @param id 组织id
     * @param description 组织简介
     * @param website_urlUrl 组织官网
     * @param gmtModified 最后修改时间
     * @param invitationCode 邀请码
     */
    @Update("update organization set "+UPDATE_CONTENT+"where id = #{id} and gmt_delete is null")
    void updateOrganization(BigInteger id,Date gmtModified,String description,String website_urlUrl,String invitationCode);

    /**
     * 根据id删除组织(软删除）
     * @param id 组织id
     * @param gmtDelete 删除时间
     */
    @Update("update organization set gmt_delete = #{gmtDelete} where id = #{id} and gmt_delete is null")
    void deleteOrganization(BigInteger id,Date gmtDelete);


    /**
     * 根据id删除组织
     * @param id 组织id
     */
    @Delete("delete from Organization where uk_id = #{id}")
    void removeOrganizationById(BigInteger id);


    /**
     * 查询所有组织
     * @return 组织列表
     */
    @Select("select id, " + COLUMNS + " from organization")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_delete", property = "gmtDelete", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "uk_organization_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "official_website_url", property = "websiteUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "invitation_code", property = "invitationCode", jdbcType = JdbcType.VARCHAR)
    })
    List<Organization> findAllOrganization();


    /**
     * 根据id查询组织
     * @param id 组织id
     * @return 组织列表
     */
    @Select("select uk_id, " + COLUMNS + " from organization where uk_id = #{id}")
    @Results({
            @Result(column = "uk_id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_delete", property = "gmtDelete", jdbcType = JdbcType.DATE),
            @Result(column = "uk_organization_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "official_website_url", property = "websiteUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "invitation_code", property = "invitationCode", jdbcType = JdbcType.VARCHAR)
    })
    Organization findOrganizationById(BigInteger id);

    /**
     * 根据名称查询组织
     * @param name 组织名称
     * @return 组织列表
     */
    @Select("select uk_id, " + COLUMNS + " from university where uk_organization_name = #{name}")
    @Results({
            @Result(column = "uk_id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_delete", property = "gmtDelete", jdbcType = JdbcType.DATE),
            @Result(column = "uk_organization_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "official_website_url", property = "websiteUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "invitation_code", property = "invitationCode", jdbcType = JdbcType.VARCHAR)
    })
    Organization findOrganizationByName(String name);


}
