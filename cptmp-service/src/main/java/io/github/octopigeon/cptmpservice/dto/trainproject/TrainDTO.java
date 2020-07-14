package io.github.octopigeon.cptmpservice.dto.trainproject;

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
public class TrainDTO {
    private BigInteger id;
    private BigInteger organizationId;
    private String name;
    private Date startTime;
    private Date endTime;
    private String content;
    private String standard;
    private String resourceLibrary;
    private String gpsInfo;
}
