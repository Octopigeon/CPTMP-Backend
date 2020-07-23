package io.github.octopigeon.cptmpservice.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * github贡献者的相关信息
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/17
 * @last-check-in 魏啸冲
 * @date 2020/7/17
 */
@Data
public class AuthorDTO {

    /** GitHUb登录名 */
    @JsonProperty("login")
    private String loginName;
    /** GitHub用户id */
    @JsonProperty("id")
    private BigInteger githubId;
    /** 非必要属性 */
    @JsonProperty("node_id")
    private String nodeId;
    /** GitHub头像URL */
    @JsonProperty("avatar_url")
    private String avatarUrl;
    /** GitHub默认头像地址 */
    @JsonProperty("gravatar_id")
    private String gravatarUrl;
    /** GitHub个人信息api调用地址 */
    @JsonProperty("url")
    private String url;
    /** GitHub个人主页地址 */
    @JsonProperty("html_url")
    private String homepageUrl;
    /** 以下均为个人信息相关api的调用地址 */
    @JsonProperty("followers_url")
    private String followersUrl;
    @JsonProperty("following_url")
    private String followingUrl;
    @JsonProperty("gists_url")
    private String gistsUrl;
    @JsonProperty("starred_url")
    private String starredUrl;
    @JsonProperty("subscriptions_url")
    private String subscriptions;
    @JsonProperty("organizations_url")
    private String organizations;
    @JsonProperty("repos_url")
    private String reposUr;
    @JsonProperty("events_url")
    private String eventsUrl;
    @JsonProperty("received_events_url")
    private String receivedEventsUrl;
    /** GitHub用户类型 */
    @JsonProperty("type")
    private String type;
    /** 是否为GitHub网站管理员 */
    @JsonProperty("site_admin")
    private Boolean siteAdmin;

}
