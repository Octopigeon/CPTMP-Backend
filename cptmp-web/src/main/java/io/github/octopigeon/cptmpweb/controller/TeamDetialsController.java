package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.bind.v2.TODO;
import io.github.octopigeon.cptmpservice.dto.team.TeamDTO;
import io.github.octopigeon.cptmpservice.service.team.TeamService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/15
 * @last-check-in 陈若琳
 * @date 2020/07/15
 */
//TODO：团队增删

@RestController
public class TeamDetialsController {

    @Autowired
    private TeamService teamService;



    /**
     * 根据属性分页查询
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("api/team/search")
    public RespBeanWithTeamList searchTeam(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String property = objectMapper.readValue(json, ObjectNode.class).get("property").asText();
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
                    return new RespBeanWithTeamList(1,"property wrong");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithTeamList(1,"something wrong");
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
            return new RespBeanWithTeam(1,"something wrong");
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
            return new RespBean(1,"something wrong");
        }
    }

    /**
     * 增加团队成员
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/team/member")
    public RespBean addMemberToTheTeam(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger teamId = BigInteger.valueOf(objectMapper.readValue(json,ObjectNode.class).get("team_id").asInt());
        BigInteger userId = BigInteger.valueOf(objectMapper.readValue(json,ObjectNode.class).get("user_id").asInt());
        try{
            List<BigInteger> userIds = teamService.findUsersByTeamId(teamId);
            if(userIds.contains(userId))
            {
                return new RespBean(1,"The user already exists");
            }
            teamService.addUser(teamId,userId);
            return RespBean.ok("add member successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBean(1,"something wrong");
        }
    }

    /**
     * 删除团队成员
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @DeleteMapping("api/team/member")
    public RespBean deleteMember(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        BigInteger teamId = BigInteger.valueOf(objectMapper.readValue(json,ObjectNode.class).get("team_id").asInt());
        BigInteger userId = BigInteger.valueOf(objectMapper.readValue(json,ObjectNode.class).get("user_id").asInt());
        try{
            teamService.removeUser(teamId,userId);
            return RespBean.ok("delete member successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBean(1,"something wrong");
        }
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
            if(userId.size()==0)
            {
                return new RespBeanWithUserId(0,"something wrong");
            }
            return new RespBeanWithUserId(userId);
        }catch (Exception e)
        {
            return new RespBeanWithUserId(1,"something wrong");
        }

    }


}
@Data
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
