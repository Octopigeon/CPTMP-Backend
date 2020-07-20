package io.github.octopigeon.cptmpweb.servicetest;

import io.github.octopigeon.cptmpservice.dto.github.ContributorDTO;
import io.github.octopigeon.cptmpservice.service.github.ContributorStaticsService;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/17
 * @last-check-in 魏啸冲
 * @date 2020/7/17
 */
public class ContributorStaticsServiceTest extends BaseTest {

    @Autowired
    private ContributorStaticsService contributorStaticsService;

    @Test
    public void test() {

        try {
            List<ContributorDTO> contributorDTOs = contributorStaticsService.getContributorStatics("",
                    "", "");
            Assertions.assertEquals(5, contributorDTOs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
