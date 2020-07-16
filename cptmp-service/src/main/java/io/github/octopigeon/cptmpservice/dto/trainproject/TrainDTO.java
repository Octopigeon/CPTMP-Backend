package io.github.octopigeon.cptmpservice.dto.trainproject;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
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
    private String acceptStandard;
    private String resourceLibrary;
    private String gpsInfo;
}
