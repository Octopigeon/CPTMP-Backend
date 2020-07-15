package io.github.octopigeon.cptmpservice.dto.assignment;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in Gh Li
 * @date 2020/7/14
 */
@Data
public class AssignmentDTO {
    private BigInteger id;
    private Date gmtCreate;
    private Boolean isFile;
    private String title;
    private String content;
    private String documentPath;
}
