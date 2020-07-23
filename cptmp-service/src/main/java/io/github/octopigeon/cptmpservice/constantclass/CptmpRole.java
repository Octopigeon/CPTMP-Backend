package io.github.octopigeon.cptmpservice.constantclass;

/**
 * 本类主要用于web层的security权限控制
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/13
 */
public final class CptmpRole {

    /** 系统管理员 */
    public static final String ROLE_SYSTEM_ADMIN = "ROLE_SYSTEM_ADMIN";
    /** 企业管理员 */
    public static final String ROLE_ENTERPRISE_ADMIN = "ROLE_ENTERPRISE_ADMIN";
    /** 学校管理员 */
    public static final String ROLE_SCHOOL_ADMIN = "ROLE_SCHOOL_ADMIN";
    /** 学校老师 */
    public static final String ROLE_SCHOOL_TEACHER = "ROLE_SCHOOL_TEACHER";
    /** 学生 */
    public static final String ROLE_STUDENT_MEMBER = "ROLE_STUDENT_MEMBER";
}
