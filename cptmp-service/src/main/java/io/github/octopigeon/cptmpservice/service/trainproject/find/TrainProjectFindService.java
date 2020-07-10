package io.github.octopigeon.cptmpservice.service.trainproject.find;

import io.github.octopigeon.cptmpservice.dto.trainproject.TrainProjectFindRespDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 查找服务的抽象，定义一个用项目名查找的方法，需要对
 * 面向不同角色的用户分别继承，企业管理员可以查询到所
 * 有项目，老师和学生只能查询到本校和企业提供的项目
 * @last-check-in anlow
 * @date 2020/7/10
 */
public interface TrainProjectFindService {

    /**
     * 根据一段字符串对实训项目名字段模糊查找
     * @param keywords 搜索关键词
     * @return 返回所有模糊查询的结果
     */
    List<TrainProjectFindRespDTO> findTrainProjects(String keywords);

}
