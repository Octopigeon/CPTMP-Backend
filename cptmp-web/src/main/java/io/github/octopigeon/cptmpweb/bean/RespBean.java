package io.github.octopigeon.cptmpweb.bean;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import lombok.Data;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
 * @date 2020/7/9
 */
@Data
public class RespBean {
    /** 状态码 */
    private Integer status;
    /** 时间 */
    private final Date date;
    /** 返回信息 */
    private String msg;

    private RespBean() {
        this.date = new Date();
    }

    public static RespBean build() {
        return new RespBean();
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

    private RespBean(Integer status, String msg) {
        this.status = status;
        this.date = new Date();
        this.msg = msg;
    }
}