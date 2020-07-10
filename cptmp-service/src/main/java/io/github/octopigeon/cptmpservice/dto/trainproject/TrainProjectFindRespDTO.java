package io.github.octopigeon.cptmpservice.dto.trainproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 查询实训项目操作的返回POJO
 * @last-check-in anlow
 * @date 2020/7/10
 */
@Data
public class TrainProjectFindRespDTO {

    @JsonProperty("id")
    private BigInteger id;
    /** 前端用于显示在搜索栏的名字 */
    @JsonProperty("project_name")
    private String projectName;

}
