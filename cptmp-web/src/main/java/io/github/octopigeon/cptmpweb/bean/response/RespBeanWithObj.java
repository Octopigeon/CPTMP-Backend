package io.github.octopigeon.cptmpweb.bean.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.Delete;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
public class RespBeanWithObj<T> extends RespBean {

    @JsonRawValue
    T obj;

    public RespBeanWithObj() {
        super();
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return this.obj;
    }

}
