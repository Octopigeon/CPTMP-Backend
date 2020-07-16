package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.assignment.AssignmentDTO;
import io.github.octopigeon.cptmpservice.dto.record.RecordDTO;
import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.ProjectDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.assignment.AssignmentService;
import io.github.octopigeon.cptmpservice.service.record.RecordService;
import io.github.octopigeon.cptmpservice.service.trainproject.TrainService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/16
 * @last-check-in 魏啸冲
 * @date 2020/7/16
 */
@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private TrainService trainService;

    /**
     * 老师权限以上的人可以查找提交文件记录详细信息
     *
     * @param id 提交文件的编号
     * @return 查找结果
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN, CptmpRole.ROLE_SCHOOL_ADMIN,
            CptmpRole.ROLE_SCHOOL_TEACHER})
    @GetMapping("/api/assignment/{id}")
    public RespBeanWithAssignmentDTO getAssignmentInfo(
            @PathVariable(value = "id") BigInteger id
    ) {
        try {
            AssignmentDTO assignmentDTO = assignmentService.findById(id);
            return new RespBeanWithAssignmentDTO(assignmentDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithAssignmentDTO(CptmpStatusCode.INFO_ACCESS_FAILED, "get assigment info failed");
        }
    }

//    @Secured(CptmpRole.ROLE_STUDENT_MEMBER)
//    @GetMapping("/api/assignment/me")
//    public RespBeanWithAssignmentDTO getMyAssignmentInfo() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        try {
//            TrainDTO trainDTO = trainService.find
//            RecordDTO recordDTO = recordService.find()
//            AssignmentDTO assignmentDTO = assignmentService.
//        }
//    }

//    @Secured(CptmpRole.ROLE_STUDENT_MEMBER)
//    @PostMapping("/api/train/{trainId}/team/{teamId}/assignment")
//    public RespBean uploadTeamMaterial(
//            @RequestParam(value = "file") MultipartFile teamMaterial
//    ) {
//        assignmentService.uploadResourceLib();
//    }

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithAssignmentDTO extends RespBean {

    public RespBeanWithAssignmentDTO(AssignmentDTO assignmentDTO) {
        super();
        this.assignmentDTO = assignmentDTO;
    }

    public RespBeanWithAssignmentDTO(Integer status, String msg) {
        super(status, msg);
    }

    @JsonProperty("data")
    private AssignmentDTO assignmentDTO;

}

