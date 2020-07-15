package io.github.octopigeon.cptmpservice.service.team;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.web.multipart.MultipartFile;

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
     * 上传用户头像
     * @param file 文件
     * @param teamId 用户名
     * @return
     */
    String uploadAvatar(MultipartFile file, BigInteger teamId) throws Exception;

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
