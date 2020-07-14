package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@RestController
public class OrganizationDetailsController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 修改学校基本信息，学校真实名字，描述，官方网站
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

}
