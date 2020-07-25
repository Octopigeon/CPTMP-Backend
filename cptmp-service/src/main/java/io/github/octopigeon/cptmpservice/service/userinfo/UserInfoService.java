package io.github.octopigeon.cptmpservice.service.userinfo;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 陈若琳
 * @date 2020/7/25
 */
@Service
public interface UserInfoService extends BaseNormalService<BaseUserInfoDTO>, BaseFileService {

    /**
     * 用于验证用户输入的原密码是否正确
     * @param username 用户名
     * @param originPassword 用户输入的原密码
     * @return 是否正确
     */
    Boolean validateOriginPassword(String username, String originPassword);

    /**
     * 分页查询所有用户
     * @param page 页号
     * @param offset 每页数量
     * @return 用户分页列表
     */
    PageInfo<BaseUserInfoDTO> findAllByPage(int page, int offset);

    /**
     * 根据用户名得到用户基本信息，以及角色类型
     * @param username 用户名
     * @return 返回一个userInfo
     */
    BaseUserInfoDTO findByUsername(String username);

    /**
     * 根据真实姓名进行模糊查询
     * @param page 页号
     * @param offset 一页容量
     * @param name 真实姓名
     * @return userInfo
     */
    PageInfo<BaseUserInfoDTO> findByName(int page, int offset, String name);

    /**
     * 根据email进行查询
     * @param email 邮箱
     * @return userInfo
     */
    BaseUserInfoDTO findByEmail(String email);

    /**
     * 根据实训id进行模糊查询
     * @param trainId 实训id
     * @return userInfo
     */
    List<BaseUserInfoDTO> findByTrain(BigInteger trainId);

    /**
     * 汇总过滤查询
     * @param id 用户id
     * @param username 用户名
     * @param email 邮箱
     * @param name 真实姓名
     * @return 用户分页列表
     * @throws Exception 查找异常
     */
    List<BaseUserInfoDTO> findByPersonalFilter(BigInteger id, String username, String email, String name) throws Exception;

    /**
     * 根据组织id进行用户查询
     * @param page 页号
     * @param offset 偏移
     * @param organizationId 组织号
     * @return 用户分页列表
     */
    PageInfo<BaseUserInfoDTO> findByOrganizationId(int page, int offset, BigInteger organizationId);

    /**
     * 根据权限进行用户查询
     * @param page 页号
     * @param offset 页内数量
     * @param roleName 权限名
     * @return 用户分页列表
     */
    PageInfo<BaseUserInfoDTO> findByRoleName(int page, int offset, String roleName);

    /**
     * 根据组织id和权限名进行查询
     * @param page 页号
     * @param offset 页内数量
     * @param organizationId 组织id
     * @param roleName 权限名
     * @return 用户分页列表
     */
    PageInfo<BaseUserInfoDTO> findByGroupFilter(int page, int offset, BigInteger organizationId, String roleName);

    /**
     * 根据团队Id查询用户
     * @param teamId 团队Id
     * @return 用户信息列表
     */
    List<BaseUserInfoDTO> findByTeamId(BigInteger teamId);

    /**
     * 激活账号
     * @param userId 用户唯一标识符
     */
    void activateAccount(BigInteger userId);

    /**
     * 删除账号（软删除）
     * @param userId 用户唯一标识符
     */
    void disableAccount(BigInteger userId);

    /**
     * 更新密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    void updatePassword(String username, String newPassword);

    /**
     * 上传用户头像
     * @param file 文件
     * @param username 用户名
     * @return 头像Url
     * @throws Exception 文件上传故障
     */
    String uploadAvatar(MultipartFile file, String username) throws Exception;
}
