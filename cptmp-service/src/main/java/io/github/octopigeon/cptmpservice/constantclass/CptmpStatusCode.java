package io.github.octopigeon.cptmpservice.constantclass;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/7
 * @last-check-in 魏啸冲
 * @date 2020/7/13
 */
public class CptmpStatusCode {

    /** 成功信息 */
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
    /** 尝试修改信息失败 */
    public static int UPDATE_BASIC_INFO_FAILED = 10;
    /** 注册失败 */
    public static int REGISTER_FAILED = 11;

    /** 邮箱验证失败 */
    public static int EMAIL_VALIDATE_FAILED = 12;
    /** 发送验证邮件失败 */
    public static int SEND_TOKEN_EMAIL_FAILED = 13;

}
