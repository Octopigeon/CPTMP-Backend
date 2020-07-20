package io.github.octopigeon.cptmpservice.dto.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in Gh Li
 * @date 2020/7/20
 */
@Data
public class NoticeDTO {
    private Date gmtCreate;
    private BigInteger senderId;
    private BigInteger receiverId;
    private BigInteger teamId;
    @JsonProperty("type")
    private String noticeType;
    private String content;
    private Boolean isRead;
}
