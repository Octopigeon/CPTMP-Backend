package io.github.octopigeon.cptmpservice.dto.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 陈若琳
 * @date 2020/7/22
 */
@Data
public class NoticeDTO {
    private BigInteger id;
    @JsonIgnore
    private Date gmtCreate;
    @JsonProperty("sender_id")
    private BigInteger senderId;
    @JsonProperty("receiver_id")
    private BigInteger receiverId;
    @JsonProperty("team_id")
    private BigInteger teamId;
    @JsonProperty("type")
    private String noticeType;
    private String content;
    @JsonProperty("is_read")
    private Boolean isRead;
}
