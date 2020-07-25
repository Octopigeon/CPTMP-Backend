package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.service.team.TeamService;
import io.github.octopigeon.cptmpservice.service.trainproject.ProjectService;
import io.github.octopigeon.cptmpservice.service.trainproject.TrainService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/15
 * @last-check-in 陈若琳
 * @date 2020/07/15
 */

@RestController
public class TeamDetailsController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TrainService trainService;
    @Autowired
    private ProjectService projectService;

    /**
     * 创建团队
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/team")
    public RespBean createTeam(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDTO team = objectMapper.readValue(json,TeamDTO.class);
        try{
            teamService.add(team);
            return RespBean.ok("create team successfully");
        }catch(Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REGISTER_FAILED,"Team create failed");
        }
    }

    /**
     * 删除团队
     * @param id
     * @return
     */
    @DeleteMapping("api/team/{team_id}")
    public RespBean deleteTeam(@PathVariable("team_id") BigInteger id)
    {
        try{
            TeamDTO team = new TeamDTO();
            team.setId(id);
            teamService.remove(team);
            return RespBean.ok("team remove successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"team remove failed");
        }
    }


    /**
     * 根据名称模糊分页查询
     * @param keyWord
     * @param offset
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/team/search/name")
    public RespBeanWithTeamList searchTeamByName(
            @RequestParam("key_word")String keyWord,
            @RequestParam("offset") int offset,
            @RequestParam("page")int page)
    {
        try {
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<TeamDTO> pageInfo = teamService.findByLikeName(page, offset, keyWord);
            List<TeamDTO>teamInfoDTOList = new ArrayList<>();
            for (TeamDTO team :pageInfo.getList())
            {
                teamInfoDTOList.add(convertTeam(team));
            }
            return new RespBeanWithTeamList(teamInfoDTOList, pages.getTotal());
        }catch(Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeamList(CptmpStatusCode.INFO_ACCESS_FAILED,"find team failed");
        }
    }


    /**
     * 根据id获取团队信息
     * @param teamId
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/team/{team_id}")
    public RespBeanWithTeam getTeamById(@PathVariable(value = "team_id") BigInteger teamId)
    {
        try{
            TeamDTO team = teamService.findById(teamId);
            return new RespBeanWithTeam(convertTeam(team));
        }catch(Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeam(CptmpStatusCode.INFO_ACCESS_FAILED,"find team failed");
        }
    }

    /**
     * 修改团队信息
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping("api/team")
    public RespBean updateTeam(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDTO newTeam = objectMapper.readValue(json,TeamDTO.class);
        try{
            teamService.modify(newTeam);
            return RespBean.ok("update team info successfully");
        }catch(Exception e)
        {
            e.printStackTrace();
            return new RespBean(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED,"update team info failed");
        }
    }

    /**
     * 增加团队成员
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/team/{team_id}/member")
    public RespBeanWithFailedList addMemberToTheTeam(@RequestBody String json,@PathVariable("team_id") BigInteger teamId) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] userId = objectMapper.readValue(json,BigInteger[].class);
        List<Integer> failedList = new ArrayList<>();
        for (int i=0;i<userId.length;i++) {
            try{
                teamService.addUser(teamId,userId[i]);
            }catch (Exception e)
            {
                failedList.add(i);
                e.printStackTrace();
            }
        }
        return RespBeanWithFailedList.report(failedList);
    }

    /**
     * 删除团队成员
     * @return
     * @throws JsonProcessingException
     */
    @DeleteMapping("api/team/{team_id}/member")
    public RespBeanWithFailedList deleteMember(@RequestParam("user_id") BigInteger[] userId ,@PathVariable("team_id") BigInteger teamId) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> failedList = new ArrayList<>();
        for(int i=0;i<userId.length;i++)
        {
            try{
                teamService.removeUser(teamId,userId[i]);

            }catch (Exception e)
            {
                failedList.add(i);
                e.printStackTrace();
            }
        }
        return RespBeanWithFailedList.report(failedList);
    }


    /**
     * 获取团队成员信息
     * @param teamId
     * @return
     */
    @GetMapping("api/team/{team_id}/member")
    public RespBeanWithUsers getTeamMember(@PathVariable(value = "team_id") BigInteger teamId)
    {
        try{
            List<BaseUserInfoDTO> userInfoList = userInfoService.findByTeamId(teamId);
            return new RespBeanWithUsers(userInfoList);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithUsers(CptmpStatusCode.INFO_ACCESS_FAILED,"get member failed");
        }
    }

    /**
     * 上传团队logo
     * @param teamId
     * @param resource
     * @return
     */
    @PostMapping("api/team/{team_id}/uploadAvatar")
    public RespBean uploadAvatar(
            @PathVariable("team_id")BigInteger teamId,
            @RequestParam("file") MultipartFile resource)
    {
        try{
            teamService.uploadAvatar(resource,teamId);
            return RespBean.ok("upload Avatar successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.FILE_UPLOAD_FAILED,"upload Avatar failed");
        }
    }

    /**
     * 根据实训id获取团队
     * @param trainId
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/team/train/{train_id}")
    public RespBeanWithTeamList getTeamByTrainId(
            @PathVariable("train_id")BigInteger trainId,
            @RequestParam("offset")int offset,
            @RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<TeamDTO> pageInfo = teamService.findByTrainId(page,offset,trainId);
            List<TeamDTO>teamInfoDTOList = new ArrayList<>();
            for (TeamDTO team :pageInfo.getList())
            {
                teamInfoDTOList.add(convertTeam(team));
            }
            return new RespBeanWithTeamList(teamInfoDTOList,pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeamList(CptmpStatusCode.INFO_ACCESS_FAILED,"get team failed");
        }
    }

    /**
     * 根据项目id获取团队
     * @param projectId
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/team/project/{project_id}")
    public RespBeanWithTeamList getTeamByProjectId(
            @PathVariable("project_id")BigInteger projectId,
            @RequestParam("offset")int offset,
            @RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<TeamDTO> pageInfo = teamService.findByProjectId(page,offset,projectId);
            List<TeamDTO>teamInfoDTOList = new ArrayList<>();
            for (TeamDTO team :pageInfo.getList())
            {
                teamInfoDTOList.add(convertTeam(team));
            }
            return new RespBeanWithTeamList(teamInfoDTOList,pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeamList(CptmpStatusCode.INFO_ACCESS_FAILED,"get team failed");
        }
    }


    /**
     * 根据用户id获取团队
     * @param userId
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/team/user/{user_id}")
    public RespBeanWithTeamList getTeamByUserId(
            @PathVariable("user_id")BigInteger userId,
            @RequestParam("offset")int offset,
            @RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<TeamDTO> pageInfo = teamService.findByUserId(page, offset, userId);
            List<TeamDTO>teamInfoDTOList = new ArrayList<>();
            for (TeamDTO team :pageInfo.getList())
            {
                teamInfoDTOList.add(convertTeam(team));
            }
            return new RespBeanWithTeamList(teamInfoDTOList,pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeamList(CptmpStatusCode.INFO_ACCESS_FAILED,"get team failed");
        }
    }

    /**
     * 通过实训id和项目id获取团队
     * @param projectId
     * @param trainId
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/team/train_project")
    public RespBeanWithTeamList getTeamByTrainProjectId(
            @RequestParam("project_id")BigInteger projectId,
            @RequestParam("train_id")BigInteger trainId,
            @RequestParam("offset")int offset,
            @RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<TeamDTO> pageInfo = teamService.findByProjectIdAndTrainId(page, offset, trainId, projectId);
            List<TeamDTO>teamInfoDTOList = new ArrayList<>();
            for (TeamDTO team :pageInfo.getList())
            {
                teamInfoDTOList.add(convertTeam(team));
            }
            return new RespBeanWithTeamList(teamInfoDTOList,pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeamList(CptmpStatusCode.INFO_ACCESS_FAILED,"get team failed");
        }
    }

    private TeamDTO convertTeam(TeamDTO team) throws Exception
    {
        TeamDTO teamInfoDTO = new TeamDTO();
        BeanUtils.copyProperties(team, teamInfoDTO);
        teamInfoDTO.setProjectName(projectService.findById(team.getProjectId()).getName());
        teamInfoDTO.setTrainName(trainService.findById(team.getTrainId()).getName());
        teamInfoDTO.setTeamMasterName(userInfoService.findById(team.getTeamMasterId()).getName());
        teamInfoDTO.setSize(userInfoService.findByTeamId(team.getId()).size());
        return teamInfoDTO;
    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithTeamList extends RespBean
{
    public RespBeanWithTeamList(List<TeamDTO> teams, long totalRows)
    {
        super();
        this.teams = teams;
        this.totalRows = totalRows;
    }

    public  RespBeanWithTeamList(Integer status, String msg)
    {
        super(status,msg);
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    private List<TeamDTO> teams;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithTeam extends RespBean
{
    public RespBeanWithTeam(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithTeam(TeamDTO team)
    {
        super();
        this.team = team;
    }

    @JsonProperty("data")
    private TeamDTO team;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithUsers extends RespBean
{
    public RespBeanWithUsers(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithUsers(List<BaseUserInfoDTO> userInfoList)
    {
        super();
        this.userInfoList = userInfoList;
    }

    @JsonProperty("data")
    List<BaseUserInfoDTO> userInfoList;
}
