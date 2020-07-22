package io.github.octopigeon.cptmpservice.dto.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 陈若琳
 * @date 2020/7/22
 */
@Data
public class RecordDTO {
    private BigInteger id;
    @JsonIgnore
    private Date gmtCreate;
    @JsonProperty("train_id")
    private BigInteger trainId;
    @JsonProperty("team_id")
    private BigInteger teamId;
    @JsonProperty("user_id")
    private BigInteger userId;
    @JsonProperty("process_id")
    private BigInteger processId;
    @JsonProperty("event_id")
    private BigInteger eventId;
    @JsonProperty("assignments_lib")
    private String assignmentsLib;
    @JsonIgnore
    private BigInteger processEventId;
}
