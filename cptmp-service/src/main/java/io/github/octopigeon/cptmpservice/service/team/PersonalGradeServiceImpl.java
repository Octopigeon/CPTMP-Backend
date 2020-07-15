package io.github.octopigeon.cptmpservice.service.team;

import io.github.octopigeon.cptmpservice.dto.team.PersonalGradeDTO;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in Gh Li
 * @date 2020/7/15
 */
public class PersonalGradeServiceImpl implements PersonalGradeService{
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
}
