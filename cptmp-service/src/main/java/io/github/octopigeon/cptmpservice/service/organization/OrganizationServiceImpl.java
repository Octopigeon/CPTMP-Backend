package io.github.octopigeon.cptmpservice.service.organization;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in 李国豪
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
                organizationMapper.hideOrganizationById(organization.getId(), new Date());
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
     * 分页查询所有组织
     *
     * @param page   页号
     * @param offset 页偏移
     * @return
     */
    @Override
    public PageInfo<OrganizationDTO> findAll(int page, int offset) {
        PageHelper.startPage(page,offset);
        List<Organization> organizationList = organizationMapper.findAllOrganization();
        List<OrganizationDTO> results = new ArrayList<>();
        for (Organization organization: organizationList) {
            OrganizationDTO  result = new OrganizationDTO();
            BeanUtils.copyProperties(organization, result);
            results.add(result);
        }
        return new PageInfo<>(results);
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
     * 根据组织全名进行模糊查询
     * TODO Dao层建立模糊查询
     * @param realName 组织全名
     * @return
     */
    @Override
    public OrganizationDTO findByRealName(String realName) {
        Organization organization = organizationMapper.findOrganizationByRealName(realName);
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
