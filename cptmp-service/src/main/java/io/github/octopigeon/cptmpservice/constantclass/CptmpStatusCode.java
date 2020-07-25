package io.github.octopigeon.cptmpservice.constantclass;

/**
 * 本类主要用于前后端交互的状态码
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/7
 * @last-check-in 陈若琳
 * @date 2020/7/16
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
    /** 邀请码无效 */
    public static int FAKE_INVITATION_CODE = 14;

    /** 信息访问失败 */
    public static int INFO_ACCESS_FAILED = 15;
    /** 文件上传失败 */
    public static int FILE_UPLOAD_FAILED = 16;
    /** 删除对象失败 */
    public static int REMOVE_FAILED = 17;
    /** 创建失败 */
    public static int CREATE_FAILED = 18;
    /** 签到失败 */
    public static int SIGN_IN_FAILED = 19;

}
