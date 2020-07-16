package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.bind.v2.TODO;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.dto.trainproject.TrainDTO;
import io.github.octopigeon.cptmpservice.service.team.TeamService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class TeamDetialsController {

    @Autowired
    private TeamService teamService;

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
    @DeleteMapping("api/team/{id}")
    public RespBean deleteTeam(@PathVariable("id") BigInteger id)
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
     * 根据属性分页查询
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/team/search/{property}")
    public RespBeanWithTeamList searchTeam(@RequestBody String json,@PathVariable("property") String property) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        int offset = objectMapper.readValue(json, ObjectNode.class).get("offset").asInt();
        int page = objectMapper.readValue(json, ObjectNode.class).get("page").asInt();
        try{
            switch (property)
            {
                case "name":
                    String name = objectMapper.readValue(json, ObjectNode.class).get("key_word").asText();
                    PageInfo<TeamDTO> pageInfoByName = teamService.findByLikeName(page,offset,name);
                    return new RespBeanWithTeamList(
                            pageInfoByName.getList(),
                            pageInfoByName.getPageSize(),
                            pageInfoByName.getPages()
                    );
                default:
                    return new RespBeanWithTeamList(CptmpStatusCode.INFO_ACCESS_FAILED,"property is wrong");
            }
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
            return new RespBeanWithTeam(team);
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
    @PostMapping("api/team/member/{team_id}")
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
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @DeleteMapping("api/team/member/{team_id}")
    public RespBeanWithFailedList deleteMember(@RequestBody String json,@PathVariable("team_id") BigInteger teamId) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger[] userId = objectMapper.readValue(json,BigInteger[].class);
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
     * TODO:问一下前端要不要用户信息
     * 获取团队成员id
     * @param teamId
     * @return
     */
    @GetMapping("api/team/member/{team_id}")
    public  RespBeanWithUserId getTeamMember(@PathVariable(value = "team_id") BigInteger teamId)
    {
        try{
            List<BigInteger> userId = teamService.findUsersByTeamId(teamId);
            return new RespBeanWithUserId(userId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithUserId(CptmpStatusCode.INFO_ACCESS_FAILED,"get member failed");
        }

    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithTeamList extends RespBean
{
    public RespBeanWithTeamList(List<TeamDTO> teams, int pageSize, int totalPages)
    {
        super();
        this.teams = teams;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }

    public  RespBeanWithTeamList(Integer status, String msg)
    {
        super(status,msg);
    }

    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("total_pages")
    private int totalPages;
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
        this.team = team;
    }

    @JsonProperty("data")
    private TeamDTO team;
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithUserId extends RespBean
{
    public RespBeanWithUserId(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithUserId(List<BigInteger> userId)
    {
        this.userId = userId;
    }

    @JsonProperty("data")
    List<BigInteger> userId;
}
