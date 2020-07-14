package io.github.octopigeon.cptmpservice.service.organization;

import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in Gh Li
 * @date 2020/7/13
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(OrganizationDTO dto) throws Exception {
        try {
            Organization organization = new Organization();
            BeanUtils.copyProperties(dto, organization);
            organization.setGmtCreate(new Date());
            organization.setInvitationCode(productInvitationCode());
            organizationMapper.addOrganization(organization);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("add organization failed！");
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(OrganizationDTO dto) throws Exception {
        try {
            Organization organization = organizationMapper.findOrganizationByName(dto.getName());
            if(organization != null){
                organizationMapper.removeOrganizationById(organization.getId(), new Date());
            }
            else {
                throw new ValueException("organization is not existed!");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(OrganizationDTO dto) throws Exception {
        try{
            Organization organization = organizationMapper.findOrganizationByName(dto.getName());
            BeanUtils.copyProperties(dto, organization, Utils.getNullPropertyNames(dto));
            organizationMapper.updateOrganizationById(organization);
            organizationMapper.updateOrganizationById(organization);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public OrganizationDTO findById(BigInteger id) throws Exception {
        Organization organization = organizationMapper.findOrganizationById(id);
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }


    /**
     * 使用组织名进行查询
     *
     * @param name 组织的名称
     * @return 组织相关信息
     */
    @Override
    public OrganizationDTO findByName(String name){
        Organization organization = organizationMapper.findOrganizationByName(name);
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    /**
     * 根据邀请码进行查询
     *
     * @param code 邀请码
     * @return
     */
    @Override
    public OrganizationDTO findByInvitationCode(String code){
        Organization organization = organizationMapper.findOrganizationByInvitationCode(code);
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }


    /**
     * 产生邀请码
     * @return
     */
    private String productInvitationCode(){
        return UUID.randomUUID().toString();
        // return RandomStringUtils.randomAlphabetic(8);
    }
}
