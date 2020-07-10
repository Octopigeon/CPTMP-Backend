package io.github.octopigeon.cptmpservice.dto.trainproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 包含项目名和起止时间
 * @last-check-in anlow
 * @date 2020/7/10
 */
@Data
public class TrainProjectDuringTimeDTO {

    @JsonProperty(value = "project_name")
    private String projectName;
    @JsonProperty(value = "start_date")
    private Date startDate;
    @JsonProperty(value = "finish_date")
    private Date finishDate;

}
