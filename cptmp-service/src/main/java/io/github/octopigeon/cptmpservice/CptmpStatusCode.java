package io.github.octopigeon.cptmpservice;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 * @last-check-in anlow
 * @date 2020/7/11
 */
public class CptmpStatusCode {

    public static int OK = 0;

    /** 登录验证失败 */
    public static int AUTH_FAILED_BAD_CREDENTIALS = 1;
    /** 记住我异常 */
    public static int AUTH_FAILED_REMEMBER_ME_ERROR = 3;
    /** 认证信息过期 */
    public static int AUTH_FAILED_CREDENTIALS_EXPIRED = 4;
    /** 账户状态异常 */
    public static int AUTH_FAILED_ACCOUNT_STATUS_ERROR = 5;
    /** 账户过期 */
    public static int AUTH_FAILED_ACCOUNT_EXPIRED = 6;
    /** 未知错误 */
    public static int AUTH_FAILED_UNKNOWN_ERROR = 7;
    /** 尚未登录 */
    public static int ACCESS_DENY_NOT_LOGIN = 8;

    /** 尝试修改密码失败 */
    public static int UPDATE_PASSWORD_FAILED = 9;
    /** 尝试修改基本信息失败 */
    public static int UPDATE_BASIC_INFO_FAILED = 10;

}
