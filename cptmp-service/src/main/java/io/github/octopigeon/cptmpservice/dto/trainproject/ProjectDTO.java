package io.github.octopigeon.cptmpservice.dto.trainproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * 存储项目的相关信息
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class ProjectDTO {
    /** 项目唯一标识符 */
    private BigInteger id;
    /** 项目名称 */
    private String name;
    /** 项目的难度水平 */
    private Integer level;
    /** 项目的内容介绍 */
    private String content;
    /** 项目的资源库 */
    @JsonProperty("resource_library")
    private String resourceLibrary;
}
