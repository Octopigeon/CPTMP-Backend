package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencentcloudapi.kms.v20190118.models.ListAlgorithmsRequest;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/10
 * 用于提供各种与用户信息交互的接口
 * @last-check-in 魏啸冲
 * @date 2020/7/21
 */
@RestController
public class UserDetailsController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户名，得到用户基本信息
     * @return 返回用户基本信息json
     */
    @GetMapping("/api/user/me/basic-info")
    public RespBeanWithBaseUserInfoDTO getMyBasicInfo() throws JsonProcessingException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RespBeanWithBaseUserInfoDTO respBean = new RespBeanWithBaseUserInfoDTO();
        // 通过用户名查找用户详细信息
        respBean.setBaseUserInfoDTO(userInfoService.findByUsername(username));
        return respBean;
    }

    /**
     *获取所有用户信息
     * @return
     */
    @GetMapping("/api/user")
    public RespBeanWithBaseUserInfoList getAllUser(
            @RequestParam(value = "offset") Integer offset,
            @RequestParam(value = "page") Integer page
    ) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<BaseUserInfoDTO> pageInfo = userInfoService.findAllByPage(page,offset);
            List<BaseUserInfoDTO> userList = pageInfo.getList();
            return new RespBeanWithBaseUserInfoList(userList,pages.getTotal());
        }catch (Exception e)
        {
            return  new RespBeanWithBaseUserInfoList(CptmpStatusCode.INFO_ACCESS_FAILED,"get user info failed");
        }
    }

    @GetMapping("api/user/search/{property}")
    public RespBeanWithBaseUserInfoList searchUser(@RequestBody String json, @PathVariable("property") String property) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        int page = objectMapper.readValue(json, ObjectNode.class).get("page").asInt();
        int offset = objectMapper.readValue(json, ObjectNode.class).get("offset").asInt();

        try{
            switch (property)
            {
                case "organization_id":
                    BigInteger organizationId = BigInteger.valueOf(objectMapper.readValue(json, ObjectNode.class).get("key_word").asInt());
                    PageInfo<BaseUserInfoDTO> searchById = userInfoService.findByOrganizationId(page,offset,organizationId);
                    return new RespBeanWithBaseUserInfoList(
                            searchById.getList(),
                            searchById.getTotal()
                    );
                case "role_name":
                    String roleName = objectMapper.readValue(json, ObjectNode.class).get("key_word").asText();
                    PageInfo<BaseUserInfoDTO> searchByRoleName = userInfoService.findByRoleName(page,offset,roleName);
                    return new RespBeanWithBaseUserInfoList(
                            searchByRoleName.getList(),
                            searchByRoleName.getTotal()
                    );
                case "real_name":
                    String realName = objectMapper.readValue(json, ObjectNode.class).get("key_word").asText();
                    PageInfo<BaseUserInfoDTO> searchByName = userInfoService.findByName(page,offset,realName);
                    return new RespBeanWithBaseUserInfoList(
                            searchByName.getList(),
                            searchByName.getTotal()
                    );
                default:
                    return new RespBeanWithBaseUserInfoList(CptmpStatusCode.INFO_ACCESS_FAILED,"wrong property");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithBaseUserInfoList(CptmpStatusCode.INFO_ACCESS_FAILED,"get user failed");
        }
    }


    /**
     * 修改用户信息，如姓名，性别，简介信息等
     * @param json 包含姓名、性别和简介的json
     * @return ok-成功 error-失败
     */
    @PutMapping("/api/user/me/basic-info")
    public RespBean updateMyBasicInfo(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO() {
        };
        // 前端发来的json包含name，gender，introduction三个字段
        String name = objectMapper.readValue(json, ObjectNode.class).get("name").asText();
        JsonNode genderNode = objectMapper.readValue(json, ObjectNode.class).get("gender");
        Boolean gender;
        if (genderNode.asText().equals("null")) {
            gender = null;
        } else {
            gender = genderNode.asBoolean();
        }
        String introduction = objectMapper.readValue(json, ObjectNode.class).get("introduction").asText();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BigDecimal phoneNumber = BigDecimal.valueOf(objectMapper.readValue(json, ObjectNode.class).get("phone_number").asDouble());
        // 将要修改的信息打包，传到service层
        baseUserInfoDTO.setName(name);
        baseUserInfoDTO.setUsername(username);
        baseUserInfoDTO.setGender(gender);
        baseUserInfoDTO.setIntroduction(introduction);
        baseUserInfoDTO.setPhoneNumber(phoneNumber);
        try {
            if (userInfoService.modify(baseUserInfoDTO)) {
                return RespBean.ok("update basic info successfully");
            } else {
                return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "update basic info failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "modify info failed");
        }
    }

    /**
     * 修改别的用户信息，如姓名，性别，简介信息等
     * @param json 包含姓名、性别和简介的json
     * @return ok-成功 error-失败
     */
    @Secured(CptmpRole.ROLE_SYSTEM_ADMIN)
    @PutMapping("/api/user/{id}/basic-info")
    public RespBean updateOtherBasicInfo(
            @RequestBody String json,
            @PathVariable(value = "id") BigInteger userId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO() {
        };
        // 前端发来的json包含name，gender，introduction三个字段
        String name = objectMapper.readValue(json, ObjectNode.class).get("name").asText();
        JsonNode genderNode = objectMapper.readValue(json, ObjectNode.class).get("gender");
        Boolean gender;
        if (genderNode.asText().equals("null")) {
            gender = null;
        } else {
            gender = genderNode.asBoolean();
        }
        String introduction = objectMapper.readValue(json, ObjectNode.class).get("introduction").asText();
        String username = userInfoService.findById(userId).getUsername();
        BigDecimal phoneNumber = BigDecimal.valueOf(objectMapper.readValue(json, ObjectNode.class).get("phone_number").asDouble());
        // 将要修改的信息打包，传到service层
        baseUserInfoDTO.setName(name);
        baseUserInfoDTO.setUsername(username);
        baseUserInfoDTO.setGender(gender);
        baseUserInfoDTO.setIntroduction(introduction);
        baseUserInfoDTO.setPhoneNumber(phoneNumber);
        try {
            if (userInfoService.modify(baseUserInfoDTO)) {
                return RespBean.ok("update basic info successfully");
            } else {
                return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "update basic info failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "modify info failed");
        }
    }

    /**
     * 根据id批量获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("api/user/basic-info")
    public RespBeanWithBaseUserInfoList getUserInfoById(@RequestParam("user_id")BigInteger[] userId)
    {
        List<BaseUserInfoDTO>userInfoDTOList = new ArrayList<>();
        try{
            for (BigInteger id:userId)
            {
                userInfoDTOList.add(userInfoService.findById(id));
            }
            return new RespBeanWithBaseUserInfoList(userInfoDTOList,userInfoDTOList.size());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithBaseUserInfoList(CptmpStatusCode.INFO_ACCESS_FAILED,"get user info failed");
        }
    }

    /**
     * 修改用户密码
     * @param json 包含用户名，用户输入的原密码，用户输入的新密码
     * @return 返回重置是否成功的信息
     */
    @PutMapping("/api/user/me/password")
    public RespBean updateUserPassword(
            @RequestBody String json
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 用户提交的原密码，马上会和数据库中的比较
        String originPassword = objectMapper.readValue(json, ObjectNode.class).get("origin_password").asText();
        String newPassword = objectMapper.readValue(json, ObjectNode.class).get("new_password").asText();
        if (!userInfoService.validateOriginPassword(username, originPassword)) {
            return RespBean.error(CptmpStatusCode.UPDATE_PASSWORD_FAILED, "wrong origin password");
        } else {
            userInfoService.updatePassword(username, newPassword);
            return RespBean.ok("reset password success");
        }
    }


    /**
     * 处理上传头像的api
     * @param avatar 前端发来的key为file的一个图片文件
     * @return 返回更新avatar后的用户基本信息
     */
    @PutMapping("/api/user/me/avatar")
    public RespBeanWithBaseUserInfoDTO updateAvatar(
            @RequestParam("file") MultipartFile avatar
            )  {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RespBeanWithBaseUserInfoDTO respBeanWithBaseUserInfoDTO;
        try {
            respBeanWithBaseUserInfoDTO = new RespBeanWithBaseUserInfoDTO();
            // 调用service方法更新头像
            userInfoService.uploadAvatar(avatar, username);
            respBeanWithBaseUserInfoDTO.setBaseUserInfoDTO(userInfoService.findByUsername(username));
            return respBeanWithBaseUserInfoDTO;
        } catch (Exception e) {
            respBeanWithBaseUserInfoDTO = new RespBeanWithBaseUserInfoDTO(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "update avatar failed");
            return respBeanWithBaseUserInfoDTO;
        }
    }

    // TODO 等teamPerson和record接口做好后可以联合测试
    /**
     * 企业管理员批量删除用户
     * @param json 传来包含批量删除用户的信息(userId)
     * @return 返回删除失败的信息
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN})
    @DeleteMapping("/api/user")
    public RespBeanWithFailedList deleteUsers(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] deleteList = objectMapper.readValue(json, BigInteger[].class);
        List<Integer> deleteFailedList = new ArrayList<>();
        String principalRoleName = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        principalRoleName = principalRoleName.substring(1, principalRoleName.length() - 1);
        for (int i = 0; i < deleteList.length; i++) {
            try {
                BaseUserInfoDTO baseUserInfoDTO = userInfoService.findById(deleteList[i]);
                // 企业管理员无权限删除企业管理员
                if ((principalRoleName.equals(CptmpRole.ROLE_ENTERPRISE_ADMIN)
                        && baseUserInfoDTO.getRoleName().equals(CptmpRole.ROLE_ENTERPRISE_ADMIN))) {
                    deleteFailedList.add(i);
                } else {
                    userInfoService.remove(baseUserInfoDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
                deleteFailedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(deleteFailedList);
    }

    /**
     * 企业管理员批量恢复删除用户
     * @param json 传来包含批量恢复删除用户的信息(userId)
     * @return 返回恢复删除失败的信息
     */
    @Secured(CptmpRole.ROLE_ENTERPRISE_ADMIN)
    @PutMapping("/api/user")
    public RespBeanWithFailedList restoreAccount(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] restoreList = objectMapper.readValue(json, BigInteger[].class);
        List<Integer> restoreFailedList = new ArrayList<>();
        for (int i = 0; i < restoreList.length; i++) {
            try {
                userInfoService.activateAccount(restoreList[i]);
            } catch (Exception e) {
                e.printStackTrace();
                restoreFailedList.add(i);
            }
        }
        return RespBeanWithFailedList.report(restoreFailedList);
    }

    @Secured(CptmpRole.ROLE_SYSTEM_ADMIN)
    @PutMapping("api/user/pwd")
    public RespBean setPassword(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String username = objectMapper.readValue(json, ObjectNode.class).get("username").asText();
        String newPassword = objectMapper.readValue(json, ObjectNode.class).get("new_password").asText();
        try{
            userInfoService.updatePassword(username,newPassword);
            return RespBean.ok("update password successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED,"update password failed");
        }
    }

}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithBaseUserInfoDTO extends RespBean {

    public RespBeanWithBaseUserInfoDTO() {
        super();
    }

    public RespBeanWithBaseUserInfoDTO(Integer status, String msg) {
        super(status, msg);
    }

    @JsonProperty("data")
    private BaseUserInfoDTO baseUserInfoDTO;

}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithBaseUserInfoList extends RespBean {

    public RespBeanWithBaseUserInfoList(List<BaseUserInfoDTO> baseUserInfoList,long totalRows)
    {
        super();
        this.baseUserInfoList = baseUserInfoList;
        this.totalRows = totalRows;
    }

    public RespBeanWithBaseUserInfoList(Integer status, String msg)
    {
        super(status, msg);
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<BaseUserInfoDTO> baseUserInfoList;

}

