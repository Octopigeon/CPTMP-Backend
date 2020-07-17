package io.github.octopigeon.cptmpservice.service.team;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.PersonalGradeMapper;
import io.github.octopigeon.cptmpdao.mapper.TeamMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.PersonalGrade;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileServiceImpl;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Service
public class TeamServiceImpl extends BaseFileServiceImpl implements TeamService{

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private PersonalGradeMapper personalGradeMapper;

    @Autowired
    public TeamServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }


    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(TeamDTO dto) throws Exception {
        try{
            Team team = new Team();
            dto.setProjectTrainId(getTrainProjectId(dto.getTrainId(), dto.getProjectId()));
            BeanUtils.copyProperties(dto, team);
            team.setGmtCreate(new Date());
            teamMapper.addTeam(team);
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
    public void remove(TeamDTO dto) throws Exception {
        try{
            if(teamMapper.findTeamByTeamId(dto.getId()) == null){
                throw new ValueException("Team is not exist");
            }
            teamMapper.hideTeamById(dto.getId(), new Date());
            List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(dto.getId());
            for (TeamPerson teamPerson: teamPeople) {
                personalGradeMapper.hidePersonalGradeByTeamPersonId(teamPerson.getId(), new Date());
                teamPersonMapper.removeTeamPersonById(teamPerson.getId());
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
    public Boolean modify(TeamDTO dto) throws Exception {
        try{
            Team team = new Team();
            dto.setProjectTrainId(getTrainProjectId(dto.getTrainId(), dto.getProjectId()));
            BeanUtils.copyProperties(dto, team, Utils.getNullPropertyNames(dto));
            team.setGmtModified(new Date());
            teamMapper.updateTeamById(team);
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
    public TeamDTO findById(BigInteger id) throws Exception {
        try{
            Team team = teamMapper.findTeamByTeamId(id);
            return convertTeam(team);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 上传用户头像
     *
     * @param file   文件
     * @param teamId 用户名
     * @return
     */
    @Override
    public String uploadAvatar(MultipartFile file, BigInteger teamId) throws Exception {
        try{
            FileDTO fileInfo = storePublicFile(file);
            teamMapper.updateAvatarById(teamId, new Date(), fileInfo.getFileUrl());
            return fileInfo.getFilePath();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Avatar upload failed!");
        }
    }

    /**
     * 查找团队中的所有成员
     *
     * @param teamId 团队Id
     * @return 所有成员id
     */
    @Override
    public List<BigInteger> findUsersByTeamId(BigInteger teamId) {
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(teamId);
        List<BigInteger> results = new ArrayList<>();
        for (TeamPerson teamPerson: teamPeople) {
            results.add(teamPerson.getId());
        }
        return results;
    }

    /**
     * 根据团队名称进行模糊查询
     *
     * @param page   页号
     * @param offset 一页容量
     * @param name   团队名称
     * @return
     */
    @Override
    public PageInfo<TeamDTO> findByLikeName(int page, int offset, String name) {
        PageHelper.startPage(page, offset);
        List<TeamDTO> results = new ArrayList<>();
        List<Team> teams= teamMapper.findTeamByName(name);
        for (Team team:teams) {
            results.add(convertTeam(team));
        }
        return new PageInfo<>(results);
    }

    /**
     * 向团队中添加成员
     *
     * @param teamId 团队Id
     * @param userId 用户Id
     */
    @Override
    public void addUser(BigInteger teamId, BigInteger userId) {
        if(teamMapper.findTeamByTeamId(teamId) == null){
            throw new ValueException("Team is not exist!");
        }
        if(cptmpUserMapper.findUserById(userId) == null){
            throw new ValueException("User is not exist!");
        }
        //检查成员是不是已经被加入
        if(teamPersonMapper.findTeamPersonByTeamIdAndUserId(teamId, userId) == null){
            //添加成员
            TeamPerson teamPerson = new TeamPerson();
            teamPerson.setGmtCreate(new Date());
            teamPerson.setTeamId(teamId);
            teamPerson.setUserId(userId);
            teamPersonMapper.addTeamPerson(teamPerson);
            //如果是学生，在personalgrade表中插入一条数据
            if(RoleEnum.ROLE_STUDENT_MEMBER.name().equals(cptmpUserMapper.findUserById(userId).getRoleName())){
                TeamPerson re = teamPersonMapper.findTeamPersonByTeamIdAndUserId(teamId, userId);
                PersonalGrade personalGrade = new PersonalGrade();
                personalGrade.setGmtCreate(new Date());
                personalGrade.setTeamPersonId(re.getId());
                personalGradeMapper.addPersonalGrade(personalGrade);
            }
        }
    }

    /**
     * 从团队中移除成员
     *
     * @param teamId 团队Id
     * @param userId 用户Id
     */
    @Override
    public void removeUser(BigInteger teamId, BigInteger userId) {
        TeamPerson teamPerson = teamPersonMapper.findTeamPersonByTeamIdAndUserId(teamId, userId);
        personalGradeMapper.hidePersonalGradeByTeamPersonId(teamPerson.getId(), new Date());
        teamPersonMapper.removeTeamPersonById(teamPerson.getId());
    }

    private TeamDTO convertTeam(Team team){
        TeamDTO teamDTO = new TeamDTO();
        BeanUtils.copyProperties(team, teamDTO);
        BigInteger[] ids = getTrainIdAndProjectId(teamDTO.getProjectTrainId());
        teamDTO.setTrainId(ids[0]);
        teamDTO.setProjectId(ids[1]);
        return teamDTO;
    }

    /**
     * 根据trainId和projectId获取TrainProjectId
     * @param trainId 实训Id
     * @param projectId 项目Id
     * @return
     */
    private BigInteger getTrainProjectId(BigInteger trainId, BigInteger projectId){
        return projectTrainMapper.findProjectTrainByProjectIdAndTrainId(projectId, trainId).getId();
    }

    /**
     * 根据TrainProjectId获取trainId和projectId
     * @param trainProjectId
     * @return
     */
    private BigInteger[] getTrainIdAndProjectId(BigInteger trainProjectId){
        BigInteger[] ids = new BigInteger[2];
        ProjectTrain trainProject = projectTrainMapper.findProjectTrainById(trainProjectId);
        ids[0] = trainProject.getTrainId();
        ids[1] = trainProject.getProjectId();
        return ids;
    }
}
