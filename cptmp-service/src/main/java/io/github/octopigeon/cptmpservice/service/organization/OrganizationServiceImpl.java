package io.github.octopigeon.cptmpservice.service.organization;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 组织服务实现类
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

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private PersonalGradeMapper personalGradeMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private EventMapper eventMapper;

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
            Organization organization = organizationMapper.findOrganizationById(dto.getId());
            if(organization != null){
                //级联删除
                List<CptmpUser> users = cptmpUserMapper.findUsersByOrganizationId(dto.getId());
                for (CptmpUser user: users) {
                    reomoveUser(user);
                }
                List<Train> trains = trainMapper.findTrainByOrganizationId(dto.getId());
                for (Train train: trains) {
                    removeTrain(train);
                }
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
     * 用于级联删除用户
     * @param user Cptmp用户
     */
    private void reomoveUser(CptmpUser user){
        cptmpUserMapper.removeUserById(user.getId(), new Date());
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByUserId(user.getId());
        for (TeamPerson teamPerson: teamPeople) {
            personalGradeMapper.hidePersonalGradeByTeamPersonId(teamPerson.getId(), new Date());
            teamPersonMapper.removeTeamPersonById(teamPerson.getId());
        }
    }

    /**
     * 用于级联删除实训
     * @param train 实训
     */
    private void removeTrain(Train train){
        if(train != null) {
            List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(train.getId());
            List<io.github.octopigeon.cptmpdao.model.Process> processes = processMapper.findProcessesByTrainId(train.getId());
            for (ProjectTrain projectTrain: projectTrains) {
                removeTeams(projectTrain);
            }
            for (Process process: processes) {
                List<ProcessEvent> processEvents = processEventMapper.findProcessEventsByProcessId(process.getId());
                for (ProcessEvent processEvent: processEvents) {
                    if(eventMapper.findEventById(processEvent.getEventId()) != null){
                        eventMapper.hideEventById(processEvent.getEventId(), new Date());
                    }
                }
                processEventMapper.removeProcessEventsByProcessId(process.getId());
            }
            processMapper.hideProcessesByTrainId(new Date(), train.getId());
            trainMapper.hideTrainById(train.getId(), new Date());
        }
    }

    /**
     * 用于级联删除项目-实训关联表
     * @param projectTrain 项目实训信息
     */
    private void removeTeams(ProjectTrain projectTrain) {
        List<Team> teams = teamMapper.findTeamsByProjectTrainId(projectTrain.getId());
        for (Team team: teams) {
            List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(team.getId());
            for (TeamPerson teamPerson: teamPeople) {
                personalGradeMapper.hidePersonalGradeByTeamPersonId(teamPerson.getId(), new Date());
                teamPersonMapper.removeTeamPersonById(teamPerson.getId());
            }
            teamMapper.hideTeamById(team.getId(), new Date());
        }
        projectTrainMapper.removeProjectTrainsById(projectTrain.getId());
    }

    /**
     * 修改相关信息
     *
     * @param dto 实体
     * @return 是否修改成功
     */
    @Override
    public Boolean modify(OrganizationDTO dto) throws Exception {
        try{
            Organization organization = organizationMapper.findOrganizationById(dto.getId());
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
     * @param id 查询Id
     * @return dto
     */
    @Override
    public OrganizationDTO findById(BigInteger id) throws Exception {
        Organization organization = organizationMapper.findOrganizationById(id);
        if(organization == null){
            throw new Exception("Organization is not exist!");
        }
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }


    /**
     * 分页查询所有组织
     *
     * @param page   页号
     * @param offset 页偏移
     * @return 分页组织列表
     */
    @Override
    public PageInfo<OrganizationDTO> findAll(int page, int offset) {
        List<Organization> organizationList = organizationMapper.findAllOrganization();
        return getOrganizationDTOPageInfo(organizationList);
    }

    /**
     * 使用组织名进行查询
     *
     * @param name 组织的名称
     * @return 组织相关信息
     */
    @Override
    public PageInfo<OrganizationDTO> findByName(int page, int offset, String name){
        List<Organization> organizations = organizationMapper.findOrganizationByName(name);
        return getOrganizationDTOPageInfo(organizations);
    }

    /**
     * 根据组织全名进行模糊查询
     * @param realName 组织全名
     * @return 组织相关信息
     */
    @Override
    public PageInfo<OrganizationDTO> findByRealName(int page, int offset, String realName) {
        List<Organization> organizations = organizationMapper.findOrganizationByRealName(realName);
        return getOrganizationDTOPageInfo(organizations);
    }

    /**
     * 将组织model转化成dto分页信息
     * @param organizations 组织列表
     * @return 组织dto分页列表
     */
    @NotNull
    private PageInfo<OrganizationDTO> getOrganizationDTOPageInfo(List<Organization> organizations) {
        List<OrganizationDTO> results = new ArrayList<>();
        for (Organization organization: organizations) {
            OrganizationDTO result = new OrganizationDTO();
            BeanUtils.copyProperties(organization, result);
            results.add(result);
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据邀请码进行查询
     *
     * @param code 邀请码
     * @return 组织相关信息
     */
    @Override
    public OrganizationDTO findByInvitationCode(String code){
        Organization organization = organizationMapper.findOrganizationByInvitationCode(code);
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    /**
     * 判断用户权限能否修改
     *
     * @param operatorId     操作者的Id
     * @param operatedObject 被操作对象
     * @return 是否有权限进行操作
     */
    @Override
    public Boolean verifyPermissions(BigInteger operatorId, OrganizationDTO operatedObject) {
        CptmpUser operator = cptmpUserMapper.findUserById(operatorId);
        RoleEnum role = RoleEnum.valueOf(RoleEnum.class, operator.getRoleName());
        if(RoleEnum.ROLE_ENTERPRISE_ADMIN.compareTo(role) >= 0){
            return true;
        }else {
            return operator.getOrganizationId().equals(operatedObject.getId());
        }
    }

    /**
     * 产生邀请码
     * @return 唯一邀请码
     */
    private String productInvitationCode(){
        return UUID.randomUUID().toString();
    }
}
