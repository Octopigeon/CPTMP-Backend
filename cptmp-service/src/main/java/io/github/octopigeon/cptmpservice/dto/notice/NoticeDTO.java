package io.github.octopigeon.cptmpservice.dto.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 通知dto类
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class NoticeDTO {
    /** 通知唯一标识符 */
    private BigInteger id;
    /** 通知的创建时间 */
    @JsonProperty("gmt_create")
    private Date gmtCreate;
    /** 发送用户的唯一标识符 */
    @JsonProperty("sender_id")
    private BigInteger senderId;
    /** 接受用户的唯一标识符 */
    @JsonProperty("receiver_id")
    private BigInteger receiverId;
    /** 接收团队的唯一标识符 */
    @JsonProperty("team_id")
    private BigInteger teamId;
    /** 通知的类型 */
    @JsonProperty("type")
    private String noticeType;
    /** 通知的内容 */
    private String content;
    /** 通知是否已读 */
    @JsonProperty("is_read")
    private Boolean isRead;
}
