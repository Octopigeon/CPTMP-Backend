package io.github.octopigeon.cptmpservice.dto.cptmpuser;

import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生用户相关信息类
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentInfoDTO extends BaseUserInfoDTO {

    private String name;
    private String schoolName;
    private String studentId;
    private String studentFace;
}
