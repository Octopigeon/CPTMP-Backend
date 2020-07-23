package io.github.octopigeon.cptmpservice.dto.processevent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 流程类，与train类存在联系，一般跨度为一周
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class ProcessDTO {
    /** 流程唯一标识符 */
    private BigInteger id;
    /** 实训Id */
    @JsonProperty("train_id")
    private BigInteger trainId;
    /** 流程开始时间 */
    @JsonProperty("start_time")
    private Date startTime;
    /** 流程结束时间 */
    @JsonProperty("end_time")
    private Date endTime;
    /** 流程中事件列表 */
    List<EventDTO> events;
}
