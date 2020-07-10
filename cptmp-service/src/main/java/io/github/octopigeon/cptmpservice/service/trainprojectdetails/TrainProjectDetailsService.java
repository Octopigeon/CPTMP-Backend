package io.github.octopigeon.cptmpservice.service.trainprojectdetails;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
public interface TrainProjectDetailsService<T> {

    /**
     * 用于查找各种实训项目的信息，类型可以是各种DTO
     * @param trainProjectId 实训项目的id
     * @return 返回一个DTO，具体由实现类决定
     */
     T findTrainProject(BigInteger trainProjectId);

}
