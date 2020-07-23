package io.github.octopigeon.cptmpservice.constantclass;

/**
 * 本类主要用于service层进行相关的权限比较等场景
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in 李国豪
 * @date 2020/7/9
 */
public enum RoleEnum {
    //系统管理员
    ROLE_SYSTEM_ADMIN("系统管理员"),
    //企业管理员
    ROLE_ENTERPRISE_ADMIN("企业管理员"),
    //学校管理员老师
    ROLE_SCHOOL_ADMIN("学校管理员"),
    //学校普通老师
    ROLE_SCHOOL_TEACHER("指导老师"),
    //普通学生
    ROLE_STUDENT_MEMBER("普通成员学生");

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
