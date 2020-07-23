package io.github.octopigeon.cptmpservice.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 招聘企业的相关信息表
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class RecruitmentDTO {
    /** 招聘企业唯一标识符 */
    private BigInteger id;
    /** 招聘开始时间 */
    @JsonProperty("start_time")
    private Date startTime;
    /** 招聘结束时间 */
    @JsonProperty("end_time")
    private Date endTime;
    /** 图片的url */
    private String photo;
    /** 招聘的标题 */
    private String title;
    /** 招聘企业的招聘网站链接 */
    @JsonProperty("website_url")
    private String websiteUrl;
}
