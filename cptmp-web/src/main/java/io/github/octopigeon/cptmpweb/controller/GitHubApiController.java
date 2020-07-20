package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.github.ContributorDTO;
import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import io.github.octopigeon.cptmpservice.service.github.ContributorStaticsService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/18
 * GitHub api
 * @last-check-in 魏啸冲
 * @date 2020/7/18
 */
@RestController
public class GitHubApiController {

    @Autowired
    private ContributorStaticsService contributorStaticsService;

    @GetMapping("/api/team/{id}/contributor/statics")
    public RespBeanWithContributorStaticsDTOList getContributorStatics(
            @PathVariable(value = "id") BigInteger teamId
    ) {
        try {
            return new RespBeanWithContributorStaticsDTOList(
                    contributorStaticsService.getContributorStatics(teamId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithContributorStaticsDTOList(CptmpStatusCode.INFO_ACCESS_FAILED, "get contributor info failed");
        }
    }

}

@EqualsAndHashCode(callSuper = true)
@Data
class RespBeanWithContributorStaticsDTOList extends RespBean {

    public RespBeanWithContributorStaticsDTOList(List<ContributorDTO> contributorDTOList) {
        super();
        this.contributorDTOList = contributorDTOList;
    }

    public RespBeanWithContributorStaticsDTOList(Integer status, String msg) {
        super(status, msg);
    }

    private List<ContributorDTO> contributorDTOList;

}
