package io.github.octopigeon.cptmpservice.dto.trainproject;

import lombok.Data;

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

    private String projectName;
    private Date startDate;
    private Date finishDate;

}
