package io.github.octopigeon.cptmpservice.service.team;

import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in Gh Li
 * @date 2020/7/15
 */
public class PersonalGradeServiceImpl implements PersonalGradeService{

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(PersonalGradeDTO dto) throws Exception {

    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(PersonalGradeDTO dto) throws Exception {

    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(PersonalGradeDTO dto) throws Exception {
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public PersonalGradeDTO findById(BigInteger id) throws Exception {
        return null;
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
