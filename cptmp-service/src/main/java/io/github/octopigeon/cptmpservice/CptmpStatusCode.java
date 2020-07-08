package io.github.octopigeon.cptmpservice;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 */
public class CptmpStatusCode {

    public static int OK = 0;

    /** 登录验证失败 */
    public static int AUTH_FAILED_BAD_CREDENTIALS = 1;
    /** 登录用户名不存在 */
    public static int AUTH_FAILED_USERNAME_NOT_FOUND = 2;
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

}
