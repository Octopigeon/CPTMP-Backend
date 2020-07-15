package io.github.octopigeon.cptmpservice.service.team;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in Gh Li
 * @date 2020/7/14
 */
public interface TeamService extends BaseNormalService<TeamDTO>, BaseFileService {

    /**
     * 查找团队中的所有成员
     * @param teamId 团队Id
     * @return 用户id列表
     */
    List<BigInteger> findUsersByTeamId(BigInteger teamId);

    /**
     * 根据团队名称进行模糊查询
     * @param page 页号
     * @param offset 一页容量
     * @param name 团队名称
     * @return
     */
    PageInfo<TeamDTO> findByLikeName(int page, int offset, String name);

    /**
     * 向团队中添加成员
     * @param teamId 团队Id
     * @param userId 用户Id
     */
    void addUser(BigInteger teamId, BigInteger userId);

    /**
     * 从团队中移除成员
     * @param teamId 团队Id
     * @param userId 用户Id
     */
    void removeUser(BigInteger teamId, BigInteger userId);
}
