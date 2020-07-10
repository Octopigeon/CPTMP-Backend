package io.github.octopigeon.cptmpweb.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
@Data
public class KeyWordBean {

    @JsonProperty("key_word")
    private String keyWord;

}
