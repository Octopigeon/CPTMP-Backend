package io.github.octopigeon.cptmpdao.model.relation;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * 流程和事件的关联表
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Data
public class ProcessEvent {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private BigInteger processId;
    private BigInteger eventId;

}
