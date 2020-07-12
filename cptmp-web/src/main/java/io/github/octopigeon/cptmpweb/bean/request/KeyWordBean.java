package io.github.octopigeon.cptmpweb.bean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/10
 * 用于接收搜索项目时的关键词
 * @last-check-in 魏啸冲
 * @date 2020/7/10
 */
@Data
public class KeyWordBean {

    // TODO 与前端商议后，该类可能在未来会被删除

    @JsonProperty("key_word")
    private String keyWord;

}
