package io.github.octopigeon.cptmpservice.service.team;

import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 李国豪
 * @date 2020/7/15
 */
@Service
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

    /**
     * 使用用户名查询个人成绩
     * @param username 用户名
     * @return
     */
    List<PersonalGradeDTO> findByUsername(String username);

    /**
     * 比较操作权限，判断调用用户是否有权限
     * @param operatorId 操作者的userId
     * @param operatedObject 被操作的对象
     * @return 是是否有权限
     */
    Boolean verifyPermission(BigInteger operatorId, PersonalGradeDTO operatedObject);
}
