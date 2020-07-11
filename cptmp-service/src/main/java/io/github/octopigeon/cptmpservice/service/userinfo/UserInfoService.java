package io.github.octopigeon.cptmpservice.service.userinfo;

import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
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
     * 验证邀请码
     * @param userInfo：用户信息类
     * @return 验证码是否有效
     */
    Boolean validateInvitationCode(BaseUserInfoDTO userInfo);

    /**
     * 批量添加注册
     */
    void bulkAdd(List<BaseUserInfoDTO> dtos) throws Exception;

    /**
     * 根据用户名得到用户基本信息，以及角色类型
     * @param username 用户名
     * @return 返回一个父抽象类型，然后controller根据roleName转换成相应的子类
     */
    BaseUserInfoDTO findBaseUserInfoByUsername(String username);

    /**
     * 激活账号
     * @param userId
     */
    void activateAccount(BigInteger userId);

    /**
     * 更新密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    void updatePassword(String username, String newPassword);

    /**
     * 上传用户头像
     * @param file 文件
     * @param userId 用户id
     * @return
     */
    String uploadAvatar(MultipartFile file, BigInteger userId) throws Exception;

    /**
     * 上传人脸数据
     * @param file 人脸图片
     * @param userId 用户id
     */
    void uploadFace(MultipartFile file, BigInteger userId) throws Exception;

}
