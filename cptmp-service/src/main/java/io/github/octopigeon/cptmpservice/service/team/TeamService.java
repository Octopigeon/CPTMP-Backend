package io.github.octopigeon.cptmpservice.service.team;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
public interface TeamService extends BaseNormalService<TeamDTO>, BaseFileService {

    /**
     * 上传用户头像
     * @param file 文件
     * @param teamId 用户名
     * @return 用户头像的Url
     * @throws Exception 异常
     */
    String uploadAvatar(MultipartFile file, BigInteger teamId) throws Exception;

    /**
     * 根据团队名称进行模糊查询
     * @param page 页号
     * @param offset 一页容量
     * @param name 团队名称
     * @return 团队分页信息
     */
    PageInfo<TeamDTO> findByLikeName(int page, int offset, String name);

    /**
     * 根据实训id获取团队
     * @param page 页号
     * @param offset 页容量
     * @param trainId 实训Id
     * @return 团队分页列表
     */
    PageInfo<TeamDTO> findByTrainId(int page, int offset, BigInteger trainId);

    /**
     *
     * @param page
     * @param offset
     * @param projectId
     * @return
     */
    PageInfo<TeamDTO> findByProjectId(int page, int offset, BigInteger projectId);

    /**
     *
     * @param page
     * @param offset
     * @param trainId
     * @param projectId
     * @return
     */
    PageInfo<TeamDTO> findByProjectIdAndTrainId(int page, int offset, BigInteger trainId, BigInteger projectId);

    /**
     * 查找团队中的所有成员
     * @param teamId 团队Id
     * @return 用户id列表
     */
    List<BigInteger> findUsersByTeamId(BigInteger teamId);

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
