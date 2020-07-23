package io.github.octopigeon.cptmpservice.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/17
 * @last-check-in 魏啸冲
 * @date 2020/7/17
 */
@Data
public class WeekContributionsDTO {

    /** 一周开始时间 */
    @JsonProperty("w")
    private Date weekStartTime;
    /** 一周添加的代码行数 */
    @JsonProperty("a")
    private Long additions;
    /** 一周删除代码行数 */
    @JsonProperty("d")
    private Long deletions;
    /** 一周的commit数量 */
    @JsonProperty("c")
    private Long commits;

}
