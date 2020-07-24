package io.github.octopigeon.cptmpservice.service.team;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
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
 * @last-check-in 陈若琳
 * @date 2020/7/23
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
    private TrainMapper trainMapper;

    @Autowired
    private ProjectMapper projectMapper;

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
     * 修改信息
     *
     * @param dto 实体
     * @return 是否修改成功
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
     * @return 头像Url
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
     * 根据团队名称进行模糊查询
     *
     * @param page   页号
     * @param offset 一页容量
     * @param name   团队名称
     * @return 团队分页信息
     */
    @Override
    public PageInfo<TeamDTO> findByLikeName(int page, int offset, String name) {
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
    public void addUser(BigInteger teamId, BigInteger userId) throws Exception {
        Team team = teamMapper.findTeamByTeamId(teamId);
        CptmpUser user = cptmpUserMapper.findUserById(userId);
        if(team == null){
            throw new ValueException("Team is not exist!");
        }
        if(user == null){
            throw new ValueException("User is not exist!");
        }
        //如果是学生
        if(RoleEnum.ROLE_STUDENT_MEMBER.name().equals(user.getRoleName())){
            //检查是否已经加入同期实训其他队伍了
            ProjectTrain tmp = projectTrainMapper.findProjectTrainById(team.getProjectTrainId());
            List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(tmp.getTrainId());
            List<Team> teams = new ArrayList<>();
            for (ProjectTrain projectTrain: projectTrains) {
                teams.addAll(teamMapper.findTeamsByProjectTrainId(projectTrain.getTrainId()));
            }
            for (Team teamTmp: teams) {
                if(teamPersonMapper.findTeamPersonByTeamIdAndUserId(teamTmp.getId(), userId) != null){
                    throw new Exception("你已经加入了其他队伍！");
                }
            }
        }
        //添加成员
        TeamPerson teamPerson = new TeamPerson();
        teamPerson.setGmtCreate(new Date());
        teamPerson.setTeamId(teamId);
        teamPerson.setUserId(userId);
        teamPersonMapper.addTeamPerson(teamPerson);
        //如果是学生，在personalgrade表中插入一条数据
        if(RoleEnum.ROLE_STUDENT_MEMBER.name().equals(user.getRoleName())){
            TeamPerson re = teamPersonMapper.findTeamPersonByTeamIdAndUserId(teamId, userId);
            PersonalGrade personalGrade = new PersonalGrade();
            personalGrade.setGmtCreate(new Date());
            personalGrade.setTeamPersonId(re.getId());
            personalGradeMapper.addPersonalGrade(personalGrade);
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

    /**
     * 根据实训id获取团队
     * @param page 页号
     * @param offset 页容量
     * @param trainId 实训Id
     * @return 团队分页列表
     */
    @Override
    public PageInfo<TeamDTO> findByTrainId(int page, int offset, BigInteger trainId)
    {
        List<TeamDTO> results = new ArrayList<>();
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(trainId);
        for (ProjectTrain projectTrain: projectTrains)
        {
            List<Team> teams = teamMapper.findTeamsByProjectTrainId(projectTrain.getId());
            results.addAll(convertTeamList(teams));
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据项目Id查找团队
     *
     * @param page      页号
     * @param offset    页容量
     * @param projectId 项目Id
     * @return 团队分页列表
     */
    @Override
    public PageInfo<TeamDTO> findByProjectId(int page, int offset, BigInteger projectId) {
        List<TeamDTO> results = new ArrayList<>();
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByProjectId(projectId);
        for (ProjectTrain projectTrain: projectTrains)
        {
            List<Team> teams = teamMapper.findTeamsByProjectTrainId(projectTrain.getId());
            results.addAll(convertTeamList(teams));
        }
        return new PageInfo<>(results);
    }

    /**
     * 根据实训ID和团队Id查找团队
     *
     * @param page      页号
     * @param offset    页容量
     * @param trainId   实训Id
     * @param projectId 团队Id
     * @return 团队分页列表
     */
    @Override
    public PageInfo<TeamDTO> findByProjectIdAndTrainId(int page, int offset, BigInteger trainId, BigInteger projectId) {
        List<Team> teams = teamMapper.findTeamsByProjectTrainId(getTrainProjectId(trainId, projectId));
        List<TeamDTO> results = convertTeamList(teams);
        return new PageInfo<>(results);
    }

    /**
     * 根据用户Id查找相应的团队
     *
     * @param page 页号
     * @param offset 页容量
     * @param userId 用户Id
     * @return 团队分页列表
     */
    @Override
    public PageInfo<TeamDTO> findByUserId(int page, int offset, BigInteger userId) {
        List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByUserId(userId);
        List<TeamDTO> results = new ArrayList<>();
        for (TeamPerson teamPerson: teamPeople) {
            results.add(convertTeam(teamMapper.findTeamByTeamId(teamPerson.getTeamId())));
        }
        return new PageInfo<>(results);
    }

    /**
     * 将Team转为TeamDTO
     * @param team 团队Model
     * @return teamDto
     */
    private TeamDTO convertTeam(Team team){
        TeamDTO teamDTO = new TeamDTO();
        BeanUtils.copyProperties(team, teamDTO);
        BigInteger[] ids = getTrainIdAndProjectId(teamDTO.getProjectTrainId());
        teamDTO.setTrainId(ids[0]);
        teamDTO.setProjectId(ids[1]);
        teamDTO.setTrainName(trainMapper.findTrainById(teamDTO.getTrainId()).getName());
        teamDTO.setProjectName(projectMapper.findTrainProjectById(teamDTO.getProjectId()).getName());
        teamDTO.setSize(teamPersonMapper.findTeamPersonByTeamId(teamDTO.getId()).size());
        return teamDTO;
    }

    /**
     * 将Team列表转为TeamDTO列表
     * @param teamList 团队列表
     * @return 团队DTO列表
     */
    private List<TeamDTO> convertTeamList(List<Team> teamList)
    {
        List<TeamDTO>teamDTOList = new ArrayList<>();
        for (Team team :teamList)
        {
            teamDTOList.add(convertTeam(team));
        }
        return teamDTOList;
    }

    /**
     * 根据trainId和projectId获取TrainProjectId
     * @param trainId 实训Id
     * @param projectId 项目Id
     * @return trainProjectId
     */
    private BigInteger getTrainProjectId(BigInteger trainId, BigInteger projectId){
        return projectTrainMapper.findProjectTrainByProjectIdAndTrainId(projectId, trainId).getId();
    }

    /**
     * 根据TrainProjectId获取trainId和projectId
     * @param trainProjectId 实训项目ID
     * @return trainId和projectId
     */
    private BigInteger[] getTrainIdAndProjectId(BigInteger trainProjectId){
        BigInteger[] ids = new BigInteger[2];
        ProjectTrain trainProject = projectTrainMapper.findProjectTrainById(trainProjectId);
        ids[0] = trainProject.getTrainId();
        ids[1] = trainProject.getProjectId();
        return ids;
    }
}
