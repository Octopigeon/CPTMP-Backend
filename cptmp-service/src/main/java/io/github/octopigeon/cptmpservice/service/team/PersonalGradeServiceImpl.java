package io.github.octopigeon.cptmpservice.service.team;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.PersonalGradeMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.PersonalGrade;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 李国豪
 * @date 2020/7/15
 */
public class PersonalGradeServiceImpl implements PersonalGradeService{

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private PersonalGradeMapper personalGradeMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    /**
     * 根据userId和TeamId查询个人成绩
     *
     * @param userId
     * @param teamId
     * @return
     */
    @Override
    public PersonalGradeDTO findByUserIdAndTeamId(BigInteger userId, BigInteger teamId) {
        return convertPersonalGrade(personalGradeMapper.findPersonalGradeByTeamPersonId(getTeamUserId(teamId, userId)));
    }

    /**
     * 查询一个团队内所有个人成绩
     *
     * @param teamId 团队Id
     * @return
     */
    @Override
    public List<PersonalGradeDTO> findByTeamId(BigInteger teamId) {
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(teamId);
        List<PersonalGradeDTO> results = new ArrayList<>();
        for (TeamPerson teamPerson : teamPeople) {
            results.add(convertPersonalGrade(personalGradeMapper.findPersonalGradeById(teamPerson.getId())));
        }
        return results;
    }

    /**
     * 使用用户名查询个人成绩
     *
     * @param username 用户名
     * @return
     */
    @Override
    public List<PersonalGradeDTO> findByUsername(String username) {
        BigInteger userId = cptmpUserMapper.findUserByUsername(username).getId();
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByUserId(userId);
        List<PersonalGradeDTO> results = new ArrayList<>();
        for (TeamPerson teamPerson : teamPeople) {
            results.add(convertPersonalGrade(personalGradeMapper.findPersonalGradeById(teamPerson.getId())));
        }
        return results;
    }

    /**
     * 比较操作权限，判断调用用户是否有权限
     *
     * @param operatorId     操作者的userId
     * @param operatedObject 被操作的对象
     * @return 是是否有权限
     */
    @Override
    public Boolean verifyPermission(BigInteger operatorId, PersonalGradeDTO operatedObject) {
        CptmpUser operator = cptmpUserMapper.findUserById(operatorId);
        RoleEnum role = RoleEnum.valueOf(RoleEnum.class, operator.getRoleName());
        if(RoleEnum.ROLE_ENTERPRISE_ADMIN.compareTo(role) >= 0){
            return true;
        }else {
            CptmpUser operated = cptmpUserMapper.findUserById(operatedObject.getUserId());
            if(operator.getOrganizationId().equals(operated.getOrganizationId())){
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(PersonalGradeDTO dto) throws Exception {
        try{
            PersonalGrade personalGrade = new PersonalGrade();
            dto.setTeamPersonId(getTeamUserId(dto.getTeamId(), dto.getUserId()));
            BeanUtils.copyProperties(dto, personalGrade);
            personalGrade.setGmtCreate(new Date());
            personalGradeMapper.addPersonalGrade(personalGrade);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(PersonalGradeDTO dto) throws Exception {
        if(personalGradeMapper.findPersonalGradeById(dto.getId()) != null){
            personalGradeMapper.hidePersonalGradeById(dto.getId(), new Date());
        }else {
            throw new ValueException("personalGrade is not existed!");
        }
    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(PersonalGradeDTO dto) throws Exception {
        try{
            PersonalGrade personalGrade = personalGradeMapper.findPersonalGradeById(dto.getId());
            BeanUtils.copyProperties(dto, personalGrade, Utils.getNullPropertyNames(dto));
            personalGrade.setGmtModified(new Date());
            personalGradeMapper.updatePersonalGradeById(personalGrade);
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
    public PersonalGradeDTO findById(BigInteger id) throws Exception {
        return convertPersonalGrade(personalGradeMapper.findPersonalGradeById(id));
    }

    private PersonalGradeDTO convertPersonalGrade(PersonalGrade personalGrade){
        PersonalGradeDTO personalGradeDTO = new PersonalGradeDTO();
        BeanUtils.copyProperties(personalGrade, personalGradeDTO);
        BigInteger[] ids = getTeamIdAndUserId(personalGrade.getTeamPersonId());
        personalGradeDTO.setTeamId(ids[0]);
        personalGradeDTO.setUserId(ids[1]);
        return personalGradeDTO;
    }

    /**
     * 将teamId和userId转成teamUserId
     * @param teamId
     * @param userId
     * @return teamUserId
     */
    private BigInteger getTeamUserId(BigInteger teamId, BigInteger userId){
        TeamPerson teamUser = teamPersonMapper.findTeamPersonByTeamIdAndUserId(teamId, userId);
        return teamUser.getId();
    }

    /**
     * 将teamUserId转成teamId和userId
     * @param teamUserId
     * @return
     */
    private BigInteger[] getTeamIdAndUserId(BigInteger teamUserId){
        BigInteger[] ids = new BigInteger[2];
        TeamPerson teamUser = teamPersonMapper.findTeamPersonById(teamUserId);
        ids[0] = teamUser.getTeamId();
        ids[1] = teamUser.getUserId();
        return ids;
    }
}
