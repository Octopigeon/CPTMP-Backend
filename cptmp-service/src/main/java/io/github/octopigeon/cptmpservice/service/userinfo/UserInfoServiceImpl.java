package io.github.octopigeon.cptmpservice.service.userinfo;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Project;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileServiceImpl;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Service
public class UserInfoServiceImpl extends BaseFileServiceImpl implements UserInfoService {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private PersonalGradeMapper personalGradeMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private TeamMapper teamMapper;


    @Autowired
    public UserInfoServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 验证密码
     * @param username 用户名
     * @param originPassword 用户输入的原密码
     * @return 密码是否一致
     */
    @Override
    public Boolean validateOriginPassword(String username, String originPassword) {
        return passwordEncoder.matches(originPassword, cptmpUserMapper.findPasswordByUsername(username));
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
            BaseUserInfoDTO parsedUserInfo = convertUserInfo(dto);
            CptmpUser user = userInfoToUser(parsedUserInfo);
            //添加用户
            cptmpUserMapper.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 移除数据
     * @param dto ：dto实体
     */
    @Override
    public void remove(BaseUserInfoDTO dto) throws Exception {
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByUserId(dto.getId());
        for (TeamPerson teamPerson: teamPeople) {
            personalGradeMapper.hidePersonalGradeByTeamPersonId(teamPerson.getId(), new Date());
            teamPersonMapper.removeTeamPersonById(teamPerson.getId());
        }
        cptmpUserMapper.removeUserById(dto.getId(), new Date());
    }


    /**
     * 修改用户信息
     * @last-check-in 魏啸冲 修改了一个bug，以及更换更新的方法
     * @param userInfo 修改后的用户信息
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(BaseUserInfoDTO userInfo) throws Exception {
        if (userInfo.getId() == null && "".equals(userInfo.getUsername())) {
            throw new ValueException("userId or username is illegal!");
        }
        try {
            CptmpUser user = cptmpUserMapper.findUserByUsername(userInfo.getUsername());
            List<String> ignoreProperties = Arrays.asList(Utils.getNullPropertyNames(userInfo));
            String[] ignoreProps = null;
            for (int i = 0; i < ignoreProperties.size(); i++) {
                if ("gender".equals(ignoreProperties.get(i))) {
                    ignoreProps = new String[ignoreProperties.size() - 1];
                    break;
                }
            }
            if (ignoreProps == null) {
                ignoreProps = new String[ignoreProperties.size()];
            }
            int p = 0;
            for (String ignoreProperty : ignoreProperties) {
                if (!"gender".equals(ignoreProperty)) {
                    ignoreProps[p++] = ignoreProperty;
                }
            }
            BeanUtils.copyProperties(userInfo, user, ignoreProps);
            // 设置修改日期
            user.setGmtModified(new Date());
            cptmpUserMapper.updateUserByUserName(user);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Modify userInfo failed");
        }
    }

    /**
     * 激活账号（用于删除账号错误时恢复）
     *
     * @param userId 用户id
     */
    @Override
    public void activateAccount(BigInteger userId) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserById(userId);
        cptmpUserMapper.updateEnabledByUsername(cptmpUser.getUsername(), true);
    }

    /**
     * 删除账号
     * @param userId 用户Id
     */
    @Override
    public void disableAccount(BigInteger userId) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserById(userId);
        cptmpUserMapper.updateEnabledByUsername(cptmpUser.getUsername(), false);
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
            cptmpUserMapper.updateAvatarByUsername(username, new Date(), fileInfo.getFileUrl());
            return fileInfo.getFilePath();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Avatar upload failed!");
        }
    }

    // 查询服务

    /**
     * 分页查询所有用户
     *
     * @param page   页号
     * @param offset 每页数量
     * @return 用户分页信息
     */
    @Override
    public PageInfo<BaseUserInfoDTO> findAllByPage(int page, int offset) {
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findAllUsers();
        List<BaseUserInfoDTO> results = new ArrayList<>();
        for (CptmpUser cptmpUser: cptmpUsers) {
            results.add(getFullUserInfo(cptmpUser));
        }
        return new PageInfo<>(results);
    }

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
     * @return 返回一个userInfo
     */
    @Override
    public BaseUserInfoDTO findByUsername(String username) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername(username);
        return getFullUserInfo(cptmpUser);
    }

    /**
     * 根据真实姓名进行模糊查询
     * @param page 页号
     * @param offset 一页容量
     * @param name 真实姓名
     * @return 用户分页信息
     */
    @Override
    public PageInfo<BaseUserInfoDTO> findByName(int page, int offset, String name) {
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findUsersByName(name);
        List<BaseUserInfoDTO> results = new ArrayList<>();
        for (CptmpUser cptmpUser: cptmpUsers) {
            results.add(getFullUserInfo(cptmpUser));
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据email进行查询
     *
     * @param email 邮箱
     * @return userInfo
     */
    @Override
    public BaseUserInfoDTO findByEmail(String email) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserByEmail(email);
        return getFullUserInfo(cptmpUser);
    }

    /**
     * 根据实训id进行模糊查询
     *
     * @param trainId 实训id
     * @return userInfo
     */
    @Override
    public List<BaseUserInfoDTO> findByTrain(BigInteger trainId) {
        List<ProjectTrain> projectTrainList = projectTrainMapper.findProjectTrainsByTrainId(trainId);
        List<Team>teamList = new ArrayList<>();
        for (ProjectTrain projectTrain:projectTrainList)
        {
            teamList.addAll(teamMapper.findTeamsByProjectTrainId(projectTrain.getId()));
        }
        List<TeamPerson> teamPersonList = new ArrayList<>();
        for (Team team:teamList)
        {
            teamPersonList.addAll(teamPersonMapper.findTeamPersonByTeamId(team.getId()));
        }
        List<BaseUserInfoDTO>userInfoDTOList = new ArrayList<>();
        for (TeamPerson teamPerson:teamPersonList)
        {
            CptmpUser user = cptmpUserMapper.findUserById(teamPerson.getUserId());
            userInfoDTOList.add(getFullUserInfo(user));
        }
        return userInfoDTOList;
    }

    /**
     * 汇总过滤查询
     *
     * @param id       用户id
     * @param username 用户名
     * @param email    邮箱
     * @param name     真实姓名
     * @return 用户分页信息
     */
    @Override
    public List<BaseUserInfoDTO> findByPersonalFilter(BigInteger id, String username, String email, String name) throws Exception {
        List<BaseUserInfoDTO> results = new ArrayList<>();
        if (id != null){
            results.add(findById(id));
            return results;
        }
        if (username != null){
            results.add(findByUsername(username));
            return results;
        }
        if(email != null){
            results.add(findByEmail(email));
            return results;
        }
        if (name != null){
            results = findByName(name);
            return results;
        }
        return null;
    }

    /**
     * 根据真实姓名进行模糊查找
     * @param name 真实姓名
     * @return 用户列表
     */
    private List<BaseUserInfoDTO> findByName(String name){
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findUsersByName(name);
        List<BaseUserInfoDTO> results = new ArrayList<>();
        for (CptmpUser cptmpUser: cptmpUsers) {
            results.add(getFullUserInfo(cptmpUser));
        }
        return results;
    }

    /**
     * 根据组织id进行用户查询
     *
     * @param page           页号
     * @param offset         偏移
     * @param organizationId 组织号
     * @return 用户分页信息
     */
    @Override
    public PageInfo<BaseUserInfoDTO> findByOrganizationId(int page, int offset, BigInteger organizationId) {
        List<BaseUserInfoDTO> results = new ArrayList<>();
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findUsersByOrganizationId(organizationId);
        for (CptmpUser cptmpUser: cptmpUsers) {
            results.add(getFullUserInfo(cptmpUser));
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据权限进行用户查询
     *
     * @param page     页号
     * @param offset   页内数量
     * @param roleName 权限名
     * @return 用户分页信息
     */
    @Override
    public PageInfo<BaseUserInfoDTO> findByRoleName(int page, int offset, String roleName) {
        List<BaseUserInfoDTO> results = new ArrayList<>();
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findUsersByRoleName(roleName);
        for (CptmpUser cptmpUser: cptmpUsers) {
            results.add(getFullUserInfo(cptmpUser));
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据组织id和权限名进行查询
     *
     * @param page           页号
     * @param offset         页内数量
     * @param organizationId 组织id
     * @param roleName       权限名
     * @return 用户分页信息
     */
    @Override
    public PageInfo<BaseUserInfoDTO> findByGroupFilter(int page, int offset, BigInteger organizationId, String roleName) {
        List<BaseUserInfoDTO> results = new ArrayList<>();
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findUsersByGroupFilter(organizationId, roleName);
        for (CptmpUser cptmpUser: cptmpUsers) {
            results.add(getFullUserInfo(cptmpUser));
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据团队Id查询用户
     *
     * @param teamId 团队Id
     * @return 用户信息列表
     */
    @Override
    public List<BaseUserInfoDTO> findByTeamId(BigInteger teamId) {
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(teamId);
        List<BaseUserInfoDTO> results = new ArrayList<>();
        for (TeamPerson teamPerson: teamPeople) {
            CptmpUser user = cptmpUserMapper.findUserById(teamPerson.getUserId());
            results.add(getFullUserInfo(user));
        }
        return results;
    }

    /**
     * 获取完整用户信息
     * @param cptmpUser 用户model
     * @return 用户dto
     */
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
    private BaseUserInfoDTO convertUserInfo(BaseUserInfoDTO userInfo) throws ValueException {
        userInfo.setPassword(userInfo.getPassword());
        if (userInfo.getUsername() == null) {
            throw new ValueException("userName value is invalid!");
        }
        if (!validateEmailFormat(userInfo.getEmail())) {
            throw new ValueException("E-mail format is invalid");
        }
        if (userInfo.getPhoneNumber() != null && validatePhoneNumberFormat(userInfo.getPhoneNumber().toString())) {
            throw new ValueException("Phone number format is invalidation");
        }
        return userInfo;
    }

    /**
     * 验证邮箱是否有效
     * @param email 邮箱
     * @return 是否有效
     */
    private Boolean validateEmailFormat(String email) {
        String check = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})*$";
        boolean tag = true;
        if (!email.matches(check)) {
            tag = false;
        }
        return tag;
    }

    /**
     * 验证电话号码是否有效
     * @param phoneNumber 手机号码
     * @return 是否有效
     */
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
        BeanUtils.copyProperties(userInfo, user);
        user.updatePassword(userInfo.getPassword());
        user.setGmtCreate(new Date());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        return user;
    }
}
