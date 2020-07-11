package io.github.octopigeon.cptmpservice.dto.trainproject;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
@Data
public class TrainProjectDTO {
    private BigInteger id;
    private String name;
    private Integer level;
    private String content;
    private String resourceLibrary;
}
