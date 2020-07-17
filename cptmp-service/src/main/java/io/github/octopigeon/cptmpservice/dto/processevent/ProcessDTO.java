package io.github.octopigeon.cptmpservice.dto.processevent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 陈若琳
 * @date 2020/7/16
 */
@Data
public class ProcessDTO {
    private BigInteger id;

    @JsonProperty("train_id")
    private BigInteger trainId;
    @JsonProperty("start_time")
    private Date startTime;
    @JsonProperty("end_time")
    private Date endTime;
}
