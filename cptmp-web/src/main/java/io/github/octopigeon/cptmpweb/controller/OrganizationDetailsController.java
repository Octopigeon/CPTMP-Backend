package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 陈若琳
 * @date 2020/7/18
 */

@RestController
public class OrganizationDetailsController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 修改学校基本信息，学校真实名字，描述，官方网站
     *
     * @param json 包含上述字段code, real_name, description, website_url
     * @return 更新结果
     */
    @Secured({CptmpRole.ROLE_SYSTEM_ADMIN, CptmpRole.ROLE_ENTERPRISE_ADMIN, CptmpRole.ROLE_SCHOOL_ADMIN})
    @PutMapping("/api/org/basic-info")
    public RespBean updateOrganizationBasicInfo(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String name = objectMapper.readValue(json, ObjectNode.class).get("code").asText();
        String realName = objectMapper.readValue(json, ObjectNode.class).get("real_name").asText();
        String description = objectMapper.readValue(json, ObjectNode.class).get("description").asText();
        String websiteUrl = objectMapper.readValue(json, ObjectNode.class).get("website_url").asText();
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName(name);
        organizationDTO.setRealName(realName);
        organizationDTO.setDescription(description);
        organizationDTO.setWebsiteUrl(websiteUrl);
        try {
            organizationService.modify(organizationDTO);
            return RespBean.ok("update organization info success");
        } catch (Exception e) {
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED, "update organization info failed");
        }
    }

    /**
     * 根据id获取学校信息
     *
     * @param orgId
     * @return
     */
    @GetMapping("api/org/{org_id}")
    public RespBeanWithOrganization getOrganizationById(@PathVariable("org_id") BigInteger orgId) {
        try {
            return new RespBeanWithOrganization(organizationService.findById(orgId));
        } catch (Exception e) {
            return new RespBeanWithOrganization(CptmpStatusCode.INFO_ACCESS_FAILED, "get org info failed");
        }
    }

    /**
     * 根据id删除组织
     *
     * @param orgId
     * @return
     */
    @DeleteMapping("api/org/{org_id}")
    public RespBean deleteOrg(@PathVariable("org_id") BigInteger orgId) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(orgId);
        try {
            organizationService.remove(organizationDTO);
            return RespBean.ok("delete org successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED, "delete org failed");
        }
    }

    /**
     * 根据id获取学校名称
     *
     * @return
     */
    @GetMapping("api/org/name")
    public RespBeanWithOrgNameList getOrganizationName(
           @RequestParam(value = "org_id") BigInteger[] orgId
    ) {
        try {
            List<String> organizationList = new ArrayList<>();
            for (int i = 0; i < orgId.length; i++) {
                OrganizationDTO organizationDTO = organizationService.findById(orgId[i]);
                organizationList.add(organizationDTO.getRealName());
            }
            return new RespBeanWithOrgNameList(organizationList);
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithOrgNameList(CptmpStatusCode.INFO_ACCESS_FAILED, "get org info failed");
        }
    }

    /**
     * 分页获取所有组织
     *
     * @param page   页号
     * @param offset 每页最大条目数
     * @return 所有组织信息
     */
    @GetMapping("api/org")
    public RespBeanWithOrganizationList getAllOrganization(
            @RequestParam("page") int page,
            @RequestParam("offset") int offset) {
        try {
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<OrganizationDTO> pageInfo = organizationService.findAll(page, offset);
            return new RespBeanWithOrganizationList(pageInfo.getList(), pages.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithOrganizationList(CptmpStatusCode.INFO_ACCESS_FAILED, "get org failed");
        }
    }

    /**
     * 根据属性查询组织
     *
     * @param page   页号
     * @param offset 每页最大条目数
     * @return 所有组织信息
     */
    @GetMapping("api/org/real_name")
    public RespBeanWithOrganizationList searchOrganization(
            @RequestParam("page") int page,
            @RequestParam("offset") int offset,
            @RequestParam("key_word") String keyword) {
        try {
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<OrganizationDTO> pageInfo = organizationService.findByRealName(page, offset, keyword);
            return new RespBeanWithOrganizationList(pageInfo.getList(), pages.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBeanWithOrganizationList(CptmpStatusCode.INFO_ACCESS_FAILED, "get org failed");
        }
    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithOrganization extends RespBean {
    public RespBeanWithOrganization(Integer status, String msg) {
        super(status, msg);
    }

    public RespBeanWithOrganization(OrganizationDTO organizationDTO) {
        super();
        this.organizationDTO = organizationDTO;
    }

    @JsonProperty("data")
    private OrganizationDTO organizationDTO;

}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithOrganizationList extends RespBean {
    public RespBeanWithOrganizationList(Integer status, String msg) {
        super(status, msg);
    }

    public RespBeanWithOrganizationList(List<OrganizationDTO> organizationDTOList, long totalRows) {
        super();
        this.totalRows = totalRows;
        this.organizationDTOList = organizationDTOList;
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<OrganizationDTO> organizationDTOList;

}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithOrgNameList extends RespBean {
    public RespBeanWithOrgNameList(Integer status, String msg) {
        super(status, msg);
    }

    public RespBeanWithOrgNameList(List<String> organizations) {
        super();
        this.organizations = organizations;
    }

    @JsonProperty("data")
    private List<String> organizations;

}