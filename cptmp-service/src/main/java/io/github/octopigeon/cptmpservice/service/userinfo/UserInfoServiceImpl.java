package io.github.octopigeon.cptmpservice.service.userinfo;

import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileServiceImpl;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/11
 */
@Service
public class UserInfoServiceImpl extends BaseFileServiceImpl implements UserInfoService {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    @Override
    public Boolean validateOriginPassword(String username, String originPassword) {
        return passwordEncoder.matches(originPassword, cptmpUserMapper.findPasswordByUsername(username));
    }

    // 添加注册用户

    /**
     * 批量添加
     * 批量注册用户
     *
     * @param dtos
     */
    @Override
    public void bulkAdd(List<BaseUserInfoDTO> dtos) throws Exception {
        try {
            for (BaseUserInfoDTO userInfo : dtos) {
                add(userInfo);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 验证码注册
     * @param dto 用户信息类
     * @param invitationCode 邀请码
     * @throws Exception
     */
    @Override
    public void addByInvitationCode(BaseUserInfoDTO dto, String invitationCode) throws Exception {
        try{
            List<Organization> organizations = organizationMapper.findAllOrganization();
            BigInteger organizationId = null;
            for (Organization organization: organizations) {
                if(organization.getInvitationCode().equals(invitationCode)){
                    organizationId = organization.getId();
                    break;
                }
            }
            if(organizationId == null){
                throw new ValueException("invatitation code is not existed!");
            }
            BaseUserInfoDTO parsedUserInfo = parseUserInfo(dto);
            parsedUserInfo.setOrganizationId(organizationId);
            CptmpUser user = userInfoToUser(parsedUserInfo);
            cptmpUserMapper.addUser(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 添加数据
     * 邀请注册用户
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(BaseUserInfoDTO dto) throws Exception {
        try {
            BaseUserInfoDTO parsedUserInfo = parseUserInfo(dto);
            CptmpUser user = userInfoToUser(parsedUserInfo);
            //添加用户
            cptmpUserMapper.addUser(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    @Deprecated
    public void remove(BaseUserInfoDTO dto) throws Exception {
    }

    // 修改用户信息

    /**
     * 修改用户信息
     *
     * @param userInfo 修改后的用户信息
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(BaseUserInfoDTO userInfo) throws Exception {
        if (userInfo.getUserId() == null || "".equals(userInfo.getUsername())) {
            throw new ValueException("userId or username is illegal!");
        }
        try {
            CptmpUser user = cptmpUserMapper.findUserByUsername(userInfo.getUsername());
            BaseUserInfoDTO baseUserInfo = completeUserInfo(user, userInfo);
            cptmpUserMapper.updateUserInfoByUsername(baseUserInfo.getUsername(),
                    new Date(), baseUserInfo.getIntroduction(), baseUserInfo.getGender());
            return true;
        } catch (Exception ex) {
            throw new Exception("Modify userInfo failed");
        }
    }

    /**
     * 激活账号
     *
     * @param userId 用户id
     */
    @Override
    public void activateAccount(BigInteger userId) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserById(userId);
        cptmpUserMapper.updateEnabledByUsername(cptmpUser.getUsername(), true);
    }

    /**
     * 更新密码
     *
     * @param username    用户名
     * @param newPassword 新密码
     */
    @Override
    public void updatePassword(String username, String newPassword) {
        String newPasswordEncoded = new CptmpUser().updatePassword(newPassword).getPassword();
        cptmpUserMapper.updatePasswordByUsername(username, new Date(), newPasswordEncoded);
    }

    /**
     * 上传用户头像
     *
     * @param file   文件
     * @param username 用户名
     * @return 头像链接
     */
    @Override
    public String uploadAvatar(MultipartFile file, String username) throws Exception {
        try{
            FileDTO fileInfo = storePublicFile(file);
            cptmpUserMapper.updateAvatarByUsername(username, new Date(), fileInfo.getFilePath());
            return fileInfo.getFilePath();
        } catch (Exception e) {
            throw new Exception("Avatar upload failed!");
        }
    }

//    /**
//     * 上传人脸数据
//     *
//     * @param file   人脸图片
//     * @param username 用户名
//     */
//    @Override
//    public void uploadFace(MultipartFile file, String username) throws Exception {
//        try{
//            FileDTO fileInfo = storePublicFile(file);
//            CptmpUser user = cptmpUserMapper.findUserByUsername(username);
//            if (RoleEnum.ROLE_STUDENT_MEMBER.name().equals(user.getRoleName())) {
//                cptmpUserMapper.updateFaceInfoById(user.getId(), new Date(), fileInfo.getFilePath());
//            }
//        } catch (Exception e) {
//            throw new Exception("Face info upload failed!");
//        }
//    }

    // 查询服务

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public BaseUserInfoDTO findById(BigInteger id) throws Exception {
        CptmpUser cptmpUser = cptmpUserMapper.findUserById(id);
        return getFullUserInfo(cptmpUser);
    }

    /**
     * 根据用户名得到用户基本信息，以及角色类型
     *
     * @param username 用户名
     * @return 返回一个父抽象类型，然后controller根据roleName转换成相应的子类
     */
    @Override
    public BaseUserInfoDTO findBaseUserInfoByUsername(String username) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername(username);
        return getFullUserInfo(cptmpUser);
    }

    /**
     * 补全userInfo
     *
     * @param cptmpUser usrModel
     * @return userInfo类
     */
    private BaseUserInfoDTO completeUserInfo(CptmpUser cptmpUser, BaseUserInfoDTO userInfo) throws IllegalAccessException {

        BaseUserInfoDTO originUserInfo = getFullUserInfo(cptmpUser);
        return compareUserInfo(originUserInfo, userInfo);
    }

    private BaseUserInfoDTO compareUserInfo(BaseUserInfoDTO originUserInfo, BaseUserInfoDTO userInfo) throws IllegalAccessException {
        Class<? extends BaseUserInfoDTO> cls = originUserInfo.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            //设置属性可读
            f.setAccessible(true);
            try {
                if (f.get(userInfo) == null) {
                    f.set(userInfo, f.get(originUserInfo));
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalAccessException();
            }
        }
        return userInfo;
    }

    private BaseUserInfoDTO getFullUserInfo(CptmpUser cptmpUser) {
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
        BeanUtils.copyProperties(cptmpUser, baseUserInfoDTO);
        return baseUserInfoDTO;
    }

    /**
     * 为userInfo添加默认值
     *
     * @param userInfo userInfo类
     * @return userInfo类
     */
    private BaseUserInfoDTO parseUserInfo(BaseUserInfoDTO userInfo) throws ValueException {
        userInfo.setPassword(userInfo.getPassword());
        if (userInfo.getUsername() == null) {
            userInfo.setUsername(productUserName(userInfo));
        }
        if (!validateEmailFormat(userInfo.getEmail())) {
            throw new ValueException("E-mail format is invalidation");
        }
        if (userInfo.getPhoneNumber() != null && validatePhoneNumberFormat(userInfo.getPhoneNumber().toString())) {
            throw new ValueException("Phone number format is invalidation");
        }
        return userInfo;
    }

    /**
     * 产生用户名
     *
     * @param userInfo
     * @return
     */
    private String productUserName(BaseUserInfoDTO userInfo) {
        //根据organizationId查找organizationName
        String organizationName = "";
        String userName = organizationName + userInfo.getCommonId();
        return userName;
    }

    private Boolean validateEmailFormat(String email) {
        String check = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})*$";
        boolean tag = true;
        if (!email.matches(check)) {
            tag = false;
        }
        return tag;
    }

    private Boolean validatePhoneNumberFormat(String phoneNumber) {
        String check = "^(?:\\+?86)?1(?:3(?:4[^9\\D]|[5-9]\\d)|5[^3-6\\D]\\d|7[28]\\d|8[23478]\\d|9[578]\\d)\\d{7}$";
        boolean tag = true;
        if (!phoneNumber.matches(check)) {
            tag = false;
        }
        return tag;
    }

    /**
     * 将BaseUserInfoDTO转化成CptmpUser
     *
     * @param userInfo；BaseUserInfoDTO类
     * @return CptmpUser
     */
    private CptmpUser userInfoToUser(BaseUserInfoDTO userInfo) {
        CptmpUser user = new CptmpUser();
        user.setEmail(userInfo.getEmail());
        user.setRoleName(userInfo.getRoleName());
        user.updatePassword(userInfo.getPassword());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setGender(userInfo.getGender());
        user.setUsername(userInfo.getUsername());
        user.setGmtCreate(new Date());
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIntroduction("");
        user.setName(userInfo.getName());
        user.setCommonId(userInfo.getCommonId());
        user.setOrganizationId(userInfo.getOrganizationId());
        return user;
    }
}
