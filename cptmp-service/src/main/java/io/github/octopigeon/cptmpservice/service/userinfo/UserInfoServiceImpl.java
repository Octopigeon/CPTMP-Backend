package io.github.octopigeon.cptmpservice.service.userinfo;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.EnterpriseAdminMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolInstructorMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import io.github.octopigeon.cptmpdao.model.SchoolInstructor;
import io.github.octopigeon.cptmpdao.model.SchoolStudent;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.TeacherInfoDTO;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileServiceImpl;
import io.github.octopigeon.cptmpservice.service.otherservice.EmailService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
public class UserInfoServiceImpl extends BaseFileServiceImpl implements UserInfoService{

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private EnterpriseAdminMapper enterpriseAdminMapper;

    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    @Autowired
    private SchoolInstructorMapper schoolInstructorMapper;

    @Autowired
    private EmailService emailService;

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

    /**
     * 验证邀请码
     * TODO 等lgp学校验证码
     * @param userInfo ：用户信息类
     * @return 验证码是否有效
     */
    @Override
    public Boolean validateInvitationCode(BaseUserInfoDTO userInfo) {
        //
        return null;
    }

    // 添加注册用户

    /**
     * 批量添加
     * 批量注册用户
     * @param dtos
     */
    @Override
    public void bulkAdd(List<BaseUserInfoDTO> dtos) throws Exception {
        try
        {
            for (BaseUserInfoDTO userInfo : dtos) {
                add(userInfo);
            }
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 添加数据
     * 邀请注册用户
     * @param dto ：dto实体
     */
    @Override
    public void add(BaseUserInfoDTO dto) throws Exception {
        try
        {
            BaseUserInfoDTO parsedUserInfo = parseUserInfo(dto);
            CptmpUser user = userInfoToUser(parsedUserInfo);
            //添加用户
            cptmpUserMapper.addUser(user);
            CptmpUser registeredUser = cptmpUserMapper.findUserByUsername(user.getUsername());
            parsedUserInfo.setUserId(registeredUser.getId());
            //添加角色
            addRole(parsedUserInfo);
            //TODO 调用邮件服务， 发送激活链接admin/activate/{token}
            emailService.sendSimpleMessage(registeredUser.getEmail(), "章鱼鸽实训平台", "");
        }catch (Exception e) {
            throw new Exception(e);
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
        if(userInfo.getUserId()==null|| "".equals(userInfo.getUsername()))
        {
            throw new ValueException("userId or username is illegal!");
        }
        try{
            CptmpUser user = cptmpUserMapper.findUserByUsername(userInfo.getUsername());
            BaseUserInfoDTO baseUserInfo = completeUserInfo(user, userInfo);
            cptmpUserMapper.updateUserInfoByUsername(baseUserInfo.getUsername(),baseUserInfo.getNickname(),
                    new Date(),baseUserInfo.getIntroduction(),baseUserInfo.getGender());
            RoleEnum role = RoleEnum.valueOf(RoleEnum.class, baseUserInfo.getRoleName());
            // 学生
            if(role.compareTo(RoleEnum.ROLE_STUDENT_MEMBER) >= 0)
            {
                //TODO 此处不应该存在studentface修改，告知lgp修改mapper
                StudentInfoDTO studentInfo = (StudentInfoDTO)baseUserInfo;
                schoolStudentMapper.updateSchoolStudetnByUserId(studentInfo.getUserId(),new Date(),
                        studentInfo.getName(),studentInfo.getStudentId(),studentInfo.getSchoolName());
            }
            // 老师
            else if(role.compareTo(RoleEnum.ROLE_SCHOOL_ADMIN) >= 0)
            {
                TeacherInfoDTO teacherInfo = (TeacherInfoDTO)baseUserInfo;
                schoolInstructorMapper.updateSchoolInstructorByUserId(teacherInfo.getUserId(),new Date(),
                        teacherInfo.getName(),teacherInfo.getEmployeeId(),teacherInfo.getSchoolName());
            }
            // 企业管理员
            else if(role.compareTo(RoleEnum.ROLE_ENTERPRISE_ADMIN) >= 0)
            {
                EnterpriseAdminInfoDTO enterpriseAdminInfo = (EnterpriseAdminInfoDTO)baseUserInfo;
                enterpriseAdminMapper.updateEnterprseAdminByUserId(enterpriseAdminInfo.getUserId(),new Date(),
                        enterpriseAdminInfo.getName(),enterpriseAdminInfo.getEmployeeId());
            }
            return true;
        }catch (Exception ex){
            throw new Exception("Modify userInfo failed");
        }
    }

    /**
     * 激活账号
     * @param userId 用户id
     */
    @Override
    public void activateAccount(BigInteger userId) {
        CptmpUser cptmpUser = cptmpUserMapper.findUserById(userId);
        cptmpUserMapper.updateEnabledByUsername(cptmpUser.getUsername(), true);
    }

    /**
     * 更新密码
     * @param username 用户名
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
     * @param userId 用户id
     * @return 头像链接
     */
    @Override
    public String uploadAvatar(MultipartFile file, BigInteger userId) throws Exception {
        try{
            FileDTO fileInfo = storePublicFile(file);
            cptmpUserMapper.updateAvatarById(userId, new Date(), fileInfo.getFilePath());
            return fileInfo.getFilePath();
        }catch (Exception e){
            throw new Exception("Avatar upload failed!");
        }
    }

    /**
     * 上传人脸数据
     *
     * @param file   人脸图片
     * @param userId 用户id
     */
    @Override
    public void uploadFace(MultipartFile file, BigInteger userId) throws Exception {
        try{
            FileDTO fileInfo = storePublicFile(file);
            CptmpUser user = cptmpUserMapper.findUserById(userId);
            if(RoleEnum.ROLE_STUDENT_MEMBER.name().equals(user.getRoleName())){
                schoolStudentMapper.updateFaceInfoByUserId(userId, new Date(), fileInfo.getFilePath());
            }
        }catch (Exception e){
            throw new Exception("Face info upload failed!");
        }
    }

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
     * @param cptmpUser usrModel
     * @return userInfo类
     */
    private BaseUserInfoDTO completeUserInfo(CptmpUser cptmpUser, BaseUserInfoDTO userInfo) throws IllegalAccessException {

        BaseUserInfoDTO originUserInfo = getFullUserInfo(cptmpUser);
        RoleEnum role = RoleEnum.valueOf(RoleEnum.class, cptmpUser.getRoleName());
        if ( role.compareTo(RoleEnum.ROLE_STUDENT_MEMBER) >= 0) {

            StudentInfoDTO originStudentInfo = (StudentInfoDTO) originUserInfo;
            StudentInfoDTO studentInfo = (StudentInfoDTO) userInfo;
            return compareUserInfo(originStudentInfo, studentInfo);

        }else if (role.compareTo(RoleEnum.ROLE_SCHOOL_ADMIN) >= 0) {

            TeacherInfoDTO originTeacherInfo = (TeacherInfoDTO) originUserInfo;
            TeacherInfoDTO teacherInfo = (TeacherInfoDTO) userInfo;
            return compareUserInfo(originTeacherInfo, teacherInfo);
        }else if (role.compareTo(RoleEnum.ROLE_ENTERPRISE_ADMIN) >= 0) {

            EnterpriseAdminInfoDTO originAdminInfo = (EnterpriseAdminInfoDTO) originUserInfo;
            EnterpriseAdminInfoDTO adminInfo = (EnterpriseAdminInfoDTO) userInfo;
            return compareUserInfo(originAdminInfo, adminInfo);
        }
        return userInfo;
    }

    private BaseUserInfoDTO compareUserInfo(BaseUserInfoDTO originUserInfo, BaseUserInfoDTO userInfo) throws IllegalAccessException {
        Class<? extends BaseUserInfoDTO> cls = originUserInfo.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);//设置属性可读
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

    private BaseUserInfoDTO getFullUserInfo(CptmpUser cptmpUser){
        RoleEnum role = RoleEnum.valueOf(RoleEnum.class, cptmpUser.getRoleName());
        BigInteger userId = cptmpUser.getId();
        // TODO 增加系统管理员
        // 依次学生-老师-企业管理员
        BaseUserInfoDTO baseUserInfoDTO;
        if ( role.compareTo(RoleEnum.ROLE_STUDENT_MEMBER) >= 0) {
            StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
            BeanUtils.copyProperties(cptmpUser, studentInfoDTO);
            BeanUtils.copyProperties(schoolStudentMapper.findSchoolStudentByUserId(userId)
                    , studentInfoDTO);
            baseUserInfoDTO = studentInfoDTO;
        } else if (role.compareTo(RoleEnum.ROLE_SCHOOL_ADMIN) >= 0) {
            TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();
            BeanUtils.copyProperties(cptmpUser, teacherInfoDTO);
            BeanUtils.copyProperties(schoolInstructorMapper.findSchoolInstructorByUserId(userId)
                    , teacherInfoDTO);
            baseUserInfoDTO = teacherInfoDTO;
        } else if (role.compareTo(RoleEnum.ROLE_ENTERPRISE_ADMIN) >= 0) {
            EnterpriseAdminInfoDTO enterpriseAdminInfoDTO = new EnterpriseAdminInfoDTO();
            BeanUtils.copyProperties(cptmpUser, enterpriseAdminInfoDTO);
            BeanUtils.copyProperties(enterpriseAdminMapper.findEnterpriseAdminByUserId(userId)
                    , enterpriseAdminInfoDTO);
            baseUserInfoDTO = enterpriseAdminInfoDTO;
        } else {
            baseUserInfoDTO = new BaseUserInfoDTO() {
            };
        }
        return baseUserInfoDTO;
    }

    /**
     * 为userInfo添加默认值
     * @param userInfo userInfo类
     * @return userInfo类
     */
    private BaseUserInfoDTO parseUserInfo(BaseUserInfoDTO userInfo) throws ValueException
    {
        userInfo.setPassword(userInfo.getPassword());
        if(userInfo.getUsername() == null)
        {
            userInfo.setUsername(productUserName(userInfo));
        }
        if(userInfo.getNickname() == null)
        {
            userInfo.setNickname(productNickname());
        }
        if(!validateEmailFormat(userInfo.getEmail()))
        {
            throw new ValueException("E-mail format is invalidation");
        }
        if(userInfo.getPhoneNumber()!=null && validatePhoneNumberFormat(userInfo.getPhoneNumber().toString()))
        {
            throw new ValueException("Phone number format is invalidation");
        }
        return userInfo;
    }

    /**
     * 产生20位字母命名
     * @return 随机昵称
     */
    private String productNickname()
    {
        return RandomStringUtils.randomAlphabetic(20);
    }

    /**
     * 产生用户名
     * @param userInfo
     * @return
     */
    private String productUserName(BaseUserInfoDTO userInfo)
    {
        String roleName = userInfo.getRoleName();
        RoleEnum role = RoleEnum.valueOf(RoleEnum.class, roleName);
        String userName = "";
        // 学生
        if(role.compareTo(RoleEnum.ROLE_STUDENT_MEMBER) >= 0)
        {
            StudentInfoDTO studentInfo = (StudentInfoDTO)userInfo;
            userName = studentInfo.getSchoolName() + studentInfo.getStudentId();
        }
        // 老师
        else if(role.compareTo(RoleEnum.ROLE_SCHOOL_ADMIN) >= 0)
        {
            TeacherInfoDTO teacherInfo = (TeacherInfoDTO)userInfo;
            userName = teacherInfo.getSchoolName() + teacherInfo.getEmployeeId();
        }
        // 企业管理员
        else if(role.compareTo(RoleEnum.ROLE_ENTERPRISE_ADMIN) >= 0)
        {
            EnterpriseAdminInfoDTO adminInfo = (EnterpriseAdminInfoDTO)userInfo;
            userName = adminInfo.getEmployeeId();
        }
        return userName;
    }

    private Boolean validateEmailFormat(String email)
    {
        String check = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})*$";
        boolean tag = true;
        if (!email.matches(check)) {
            tag = false;
        }
        return tag;
    }

    private Boolean validatePhoneNumberFormat(String phoneNumber)
    {
        String check = "^(?:\\+?86)?1(?:3(?:4[^9\\D]|[5-9]\\d)|5[^3-6\\D]\\d|7[28]\\d|8[23478]\\d|9[578]\\d)\\d{7}$";
        boolean tag = true;
        if (!phoneNumber.matches(check)) {
            tag = false;
        }
        return tag;
    }

    /**
     * 将BaseUserInfoDTO转化成CptmpUser
     * @param userInfo；BaseUserInfoDTO类
     * @return CptmpUser
     */
    private CptmpUser userInfoToUser(BaseUserInfoDTO userInfo)
    {
        CptmpUser user = new CptmpUser();
        user.setEmail(userInfo.getEmail());
        user.setRoleName(userInfo.getRoleName());
        user.updatePassword(userInfo.getPassword());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setMale(userInfo.getGender());
        user.setUsername(userInfo.getUsername());
        user.setNickname(userInfo.getNickname());
        user.setGmtCreate(new Date());
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        // TODO 默认头像链接待定
        user.setAvatar("");
        user.setIntroduction("");
        return user;
    }

    /**
     * 进行角色的添加
     * @param userInfo BaseUserInfoDTO类
     */
    private void addRole(BaseUserInfoDTO userInfo)
    {
        String roleName = userInfo.getRoleName();
        RoleEnum role = RoleEnum.valueOf(RoleEnum.class, roleName);
        // 学生
        if(role.compareTo(RoleEnum.ROLE_STUDENT_MEMBER) >= 0)
        {
            addStudentRole(userInfo);
        }
        // 老师
        else if(role.compareTo(RoleEnum.ROLE_SCHOOL_ADMIN) >= 0)
        {
            addTeacherRole(userInfo);
        }
        // 企业管理员
        else if(role.compareTo(RoleEnum.ROLE_ENTERPRISE_ADMIN) >= 0)
        {
            addEnterpriseAdminRole(userInfo);
        }
    }

    private void addStudentRole(BaseUserInfoDTO userInfo)
    {
        StudentInfoDTO studentInfo = (StudentInfoDTO)userInfo;
        SchoolStudent student = new SchoolStudent();
        student.setStudentId(studentInfo.getStudentId());
        student.setName(studentInfo.getName());
        student.setSchoolName(studentInfo.getSchoolName());
        student.setUserId(studentInfo.getUserId());
        student.setGmtCreate(new Date());
        schoolStudentMapper.addSchoolStudent(student);
    }

    private void addEnterpriseAdminRole(BaseUserInfoDTO userInfo)
    {
        EnterpriseAdminInfoDTO adminInfo = (EnterpriseAdminInfoDTO)userInfo;
        EnterpriseAdmin admin = new EnterpriseAdmin();
        admin.setEmployeeId(adminInfo.getEmployeeId());
        admin.setName(adminInfo.getName());
        admin.setUserId(adminInfo.getUserId());
        admin.setGmtCreate(new Date());
        enterpriseAdminMapper.addEnterpriseAdmin(admin);
    }

    private void addTeacherRole(BaseUserInfoDTO userInfo)
    {
        TeacherInfoDTO teacherInfo = (TeacherInfoDTO)userInfo;
        SchoolInstructor teacher = new SchoolInstructor();
        teacher.setEmployeeId(teacherInfo.getEmployeeId());
        teacher.setName(teacherInfo.getName());
        teacher.setSchoolName(teacherInfo.getSchoolName());
        teacher.setUserId(teacherInfo.getUserId());
        teacher.setGmtCreate(new Date());
        schoolInstructorMapper.addSchoolInstructor(teacher);
    }

}
