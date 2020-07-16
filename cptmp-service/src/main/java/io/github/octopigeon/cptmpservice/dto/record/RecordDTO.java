package io.github.octopigeon.cptmpservice.dto.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in Gh Li
 * @date 2020/7/14
 */
@Data
public class RecordDTO {
    private BigInteger id;
    private Date gmtCreate;
    private BigInteger trainId;
    private BigInteger teamId;
    private BigInteger userId;
    private BigInteger processId;
    private BigInteger eventId;
    private BigInteger assignmentId;
    @JsonIgnore
    private BigInteger processEventId;
}
