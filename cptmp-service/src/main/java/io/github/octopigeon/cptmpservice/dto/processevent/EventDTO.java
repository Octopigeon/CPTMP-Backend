package io.github.octopigeon.cptmpservice.dto.processevent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 事件类，与流程类存在关联
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class EventDTO {
    /** 事件的唯一标识符 */
    private BigInteger id;
    /** 事件的开始时间 */
    @JsonProperty("start_time")
    private Date startTime;
    /** 事件的截止时间 */
    @JsonProperty("end_time")
    private Date endTime;
    /** 事件的内容 */
    private String content;
    /** 该事件是否面向个人或团队 true代表个人， false代表团队*/
    @JsonProperty("person_or_team")
    private Boolean personOrTeam;
}
