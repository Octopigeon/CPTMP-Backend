package io.github.octopigeon.cptmpservice.dto.github;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/17
 * GitHub statics api调用
 * 地址：https://developer.github.com/v3/repos/statistics/
 * @last-check-in 魏啸冲
 * @date 2020/7/17
 */
@Data
public class ContributorDTO {

    /** 总的commit数 */
    @JsonProperty("total")
    private Long totalCommits;
    /** 总共添加代码行数 */
    @JsonProperty("total_additions")
    private Long totalAdditions;
    /** 总共删除代码行数 */
    @JsonProperty("total_deletions")
    private Long totalDeletions;
    /** 一周的贡献数据 */
    @JsonProperty("weeks")
    private List<WeekContributionsDTO> weeks;
    /** 贡献者的相关信息 */
    @JsonProperty("author")
    private AuthorDTO author;

    /**
     * 获取总添加代码行数
     * @return 总添加代码行数
     */
    @JsonGetter("total_additions")
    public Long getTotalAdditions() {
        long totalAdditions = 0L;
        for (WeekContributionsDTO week : weeks) {
            totalAdditions += week.getAdditions();
        }
        this.totalAdditions = totalAdditions;
        return totalAdditions;
    }

    /**
     * 获取总删除代码行数
     * @return 总删除代码行数
     */
    @JsonGetter("total_deletions")
    public Long getTotalDeletions() {
        long totalDeletions = 0L;
        for (WeekContributionsDTO week : weeks) {
            totalDeletions += week.getDeletions();
        }
        this.totalDeletions = totalDeletions;
        return totalDeletions;
    }
}
