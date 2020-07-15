package io.github.octopigeon.cptmpservice.service.team;

import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in Gh Li
 * @date 2020/7/15
 */
public interface PersonalGradeService extends BaseNormalService<PersonalGradeDTO> {

    /**
     * 根据userId和TeamId查询个人成绩
     * @param userId 用户Id
     * @param teamId 团队Id
     * @return
     */
    PersonalGradeDTO findByUserIdAndTeamId(BigInteger userId, BigInteger teamId);

    /**
     * 查询一个团队内所有个人成绩
     * @param teamId 团队Id
     * @return
     */
    List<PersonalGradeDTO> findByTeamId(BigInteger teamId);
}
