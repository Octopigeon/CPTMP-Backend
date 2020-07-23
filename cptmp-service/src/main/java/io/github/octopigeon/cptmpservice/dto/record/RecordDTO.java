package io.github.octopigeon.cptmpservice.dto.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 记录类：主要用来记录学生的签到和作业提交等情况
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class RecordDTO {
    /** 记录卫衣标识Id */
    private BigInteger id;
    /** 记录的创建时间 */
    @JsonProperty("gmt_create")
    private Date gmtCreate;
    /** 实训唯一标识Id */
    @JsonProperty("train_id")
    private BigInteger trainId;
    /** 团队唯一标识Id */
    @JsonProperty("team_id")
    private BigInteger teamId;
    /** 用户唯一标识Id */
    @JsonProperty("user_id")
    private BigInteger userId;
    /** 流程唯一标识Id */
    @JsonProperty("process_id")
    private BigInteger processId;
    /** 事件唯一标识Id */
    @JsonProperty("event_id")
    private BigInteger eventId;
    /** 提交的作业库 可为空 */
    @JsonProperty("assignments_lib")
    private String assignmentsLib;
    /** 数据库中对应关联的Id */
    @JsonIgnore
    private BigInteger processEventId;
}
