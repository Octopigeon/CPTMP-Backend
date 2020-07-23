package io.github.octopigeon.cptmpservice.dto.assignment;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 目前是废弃不用的状态
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Deprecated
@Data
public class AssignmentDTO {
    private BigInteger id;
    private Date gmtCreate;
    private Boolean isFile;
    private String title;
    private String content;
    private String documentPath;
}
