package io.github.octopigeon.cptmpservice.dto.processevent;

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
public class ProcessDTO {
    private BigInteger id;
    private BigInteger trainId;
    private Date startTime;
    private Date endTime;
}
