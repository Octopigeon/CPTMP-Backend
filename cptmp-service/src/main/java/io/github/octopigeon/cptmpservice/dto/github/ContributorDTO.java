package io.github.octopigeon.cptmpservice.dto.github;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /** total commits */
    @JsonProperty("total")
    private Long totalCommits;
    @JsonProperty("total_additions")
    private Long totalAdditions;
    @JsonProperty("total_deletions")
    private Long totalDeletions;
    /** week statics */
    @JsonProperty("weeks")
    private List<WeekContributionsDTO> weeks;
    /** contributor info */
    @JsonProperty("author")
    private AuthorDTO author;

    @JsonGetter("total_additions")
    public Long getTotalAdditions() {
        long totalAdditions = 0L;
        for (WeekContributionsDTO week : weeks) {
            totalAdditions += week.getAdditions();
        }
        this.totalAdditions = totalAdditions;
        return totalAdditions;
    }

    @JsonGetter("total_deletions")
    public Long getTotalDeletions() {
        long totalDeletions = 0L;
        for (WeekContributionsDTO week : weeks) {
            totalAdditions += week.getDeletions();
        }
        this.totalDeletions = totalDeletions;
        return totalDeletions;
    }
}
