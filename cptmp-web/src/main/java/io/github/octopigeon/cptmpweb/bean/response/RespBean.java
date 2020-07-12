package io.github.octopigeon.cptmpweb.bean.response;

import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * 所有的返回体均继承此类，便于用Jackson进行序列化
 * @last-check-in 魏啸冲
 * @date 2020/7/10
 */
@Data
public class RespBean {
    /** 状态码 */
    protected Integer status;
    /** 时间 */
    protected final Date date;
    /** 返回信息 */
    protected String msg;

    /**
     * 返回成功的基本信息
     */
    public RespBean() {
        this.status = CptmpStatusCode.OK;
        this.date = new Date();
        this.msg = "success";
    }

    /**
     * 返回成功，并加上自定义信息
     * @param msg 自定义信息
     * @return 一个返回体
     */
    public static RespBean ok(String msg) {
        return new RespBean(CptmpStatusCode.OK, msg);
    }

    /**
     * 自定义返回成功的状态码以及信息
     * @param status 自定义状态码
     * @param msg 自定义信息
     * @return 一个返回体
     */
    public static RespBean ok(Integer status, String msg) {
        return new RespBean(status, msg);
    }

    /**
     * 自定义错误码以及信息
     * @param status 自定义错误码
     * @param msg 自定义消息
     * @return 一个返回体
     */
    public static RespBean error(Integer status, String msg) {
        return new RespBean(status, msg);
    }

    /**
     * 自定义状态码以及消息的构造函数，方便子类直接继承
     * @param status 自定义状态码
     * @param msg 自定义消息
     */
    public RespBean(Integer status, String msg) {
        this.status = status;
        this.date = new Date();
        this.msg = msg;
    }

}