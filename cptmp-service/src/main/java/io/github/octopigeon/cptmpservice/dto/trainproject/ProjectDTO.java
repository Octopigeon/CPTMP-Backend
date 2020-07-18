package io.github.octopigeon.cptmpservice.dto.trainproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 陈若琳
 * @date 2020/7/16
 */
@Data
public class ProjectDTO {
    private BigInteger id;
    private String name;
    private Integer level;
    private String content;
    @JsonProperty("resource_library")
    private String resourceLibrary;
}
