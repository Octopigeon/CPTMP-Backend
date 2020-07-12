package io.github.octopigeon.cptmpservice.service.basenormalservice;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
public interface BaseNormalService<T> {
    /**
     * 添加数据
     * @param dto：dto实体
     */
    void add(T dto) throws Exception;

    /**
     * 移除数据
     * @param dto：dto实体
     */
    void remove(T dto) throws Exception;

    /**
     * 更新的文件实体
     * @param dto
     * @return 是否删除成功
     */
    Boolean modify(T dto) throws Exception;

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     * @param id 查询
     * @return dto
     */
    T findById(BigInteger id) throws Exception;
}
