package io.github.octopigeon.cptmpweb.bean.response;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import lombok.Data;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
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

    public RespBean() {
        this.status = CptmpStatusCode.OK;
        this.date = new Date();
        this.msg = "success";
    }

    public static RespBean ok(String msg) {
        return new RespBean(CptmpStatusCode.OK, msg);
    }

    public static RespBean ok(Integer status, String msg) {
        return new RespBean(status, msg);
    }

    public static RespBean error(Integer status, String msg) {
        return new RespBean(status, msg);
    }

    public RespBean(Integer status, String msg) {
        this.status = status;
        this.date = new Date();
        this.msg = msg;
    }

}