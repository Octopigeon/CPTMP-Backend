package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/7
 */
@Repository
@Mapper
public interface EnterpriseAdminMapper {
    /**
     * 向enterprise admin中插入一条数据
     * @param enterpriseAdmin：类
     */
    @Insert("insert into enterprise_admin (gmt_create, idx_name, uk_user_id, uk_employee_id) values (#{gmtCreate}, #{name}, #{userId}, #{employeeId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addEnterpriseAdmin(EnterpriseAdmin enterpriseAdmin);

    /**
     * 根据userId删除对应的企业管理员信息
     * @param userId：用户id
     */
    @Delete("delete from enterprise_admin where uk_user_id = #{userId}")
    void removeEnterprseAdminByUserId(BigInteger userId);

    /**
     * 根据用户id更新数据
     * @param userId：用户id
     * @param gmtModified：更新时间
     * @param name：员工名称
     * @param employeeId：员工工号
     */
    @Update("update enterprise_admin set gmt_modified = #{gmtModified}, idx_name = #{name}, uk_employee_id = #{employeeId} where uk_user_id = #{userId}")
    void updateEnterprseAdminByUserId(BigInteger userId, Date gmtModified, String name, BigInteger employeeId);

    /**
     * 查找所有的企业管理员的账号、姓名、工号
     * @return 企业管理员列表
     */
    @Select("select uk_user_id, idx_name, uk_employee_id from enterprise_admin")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT)
    })
    List<EnterpriseAdmin> findAllEnterpriseAdmins();

    /**
     * 根据userId查找员工账号、姓名、工号
     * @param userId：用户账号
     * @return 企业管理员实例
     */
    @Select("select uk_user_id, idx_name, uk_employee_id from enterprise_admin where uk_user_id = #{userId}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT)
    })
    EnterpriseAdmin findEnterpriseAdminByUserId(BigInteger userId);
}
