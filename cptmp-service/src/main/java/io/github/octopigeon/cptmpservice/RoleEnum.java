package io.github.octopigeon.cptmpservice;

/**
 * 前后端都存英文标识符
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in Gh Li
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
    //项目经理学生
    ROLE_STUDENT_MASTER("项目经理学生"),
    //技术总管学生
    ROLE_STUDENT_PM("技术总管学生"),
    //产品经理学生
    ROLE_STUDENT_PO("产品经理学生"),
    //普通学生
    ROLE_STUDENT_MEMBER("普通成员学生");

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
