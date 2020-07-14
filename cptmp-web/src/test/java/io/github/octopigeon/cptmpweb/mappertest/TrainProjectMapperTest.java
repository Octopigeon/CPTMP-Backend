package io.github.octopigeon.cptmpweb.mappertest;

import org.junit.Test;
import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
public class TrainProjectMapperTest extends BaseTest {
    @Autowired
    private TrainProjectMapper trainProjectMapper;
    @Test
    public void test(){
        /**
         * 设置数据
         */
        TrainProject trainProject1=new TrainProject();
        trainProject1.setGmtCreate(new Date());
        trainProject1.setContent("test1");
        trainProject1.setLevel(1);
        trainProject1.setName("test1");
        trainProject1.setResourceLibrary("test1");

        TrainProject trainProject2=new TrainProject();
        trainProject2.setGmtCreate(new Date());
        trainProject2.setGmtCreate(new Date());
        trainProject2.setContent("test2");
        trainProject2.setLevel(2);
        trainProject2.setName("test2");
        trainProject2.setResourceLibrary("test2");

        /**
         * 添加
         */
        trainProjectMapper.removeAllTrainProjectsTest();
        trainProjectMapper.addTrainProject(trainProject1);
        trainProjectMapper.addTrainProject(trainProject2);
        Assertions.assertEquals(2,trainProjectMapper.findAllTrainProject().size());

        /**
         * 删除
         */
        trainProjectMapper.removeTrainProjectByName("test1",new Date());
        Assertions.assertEquals(trainProject2.getContent(),trainProjectMapper.findTrainProjectByNameAmbiguously("2").get(0).getContent());
        Assertions.assertEquals(1,trainProjectMapper.findAllTrainProject().size());

        /**
         * 更新
         */
        TrainProject trainProject3=trainProjectMapper.findAllTrainProject().get(0);
        trainProject3.setContent("test3");
        trainProjectMapper.updateTrainProjectByName(trainProject3);
        Assertions.assertEquals("test3",trainProjectMapper.findTrainProjectByNameAmbiguously("test2").get(0).getContent());

        Assertions.assertEquals(1, trainProjectMapper.findTrainProjectByNameAmbiguously("test2").size());

        trainProjectMapper.removeAllTrainProjects(new Date());
        Assertions.assertEquals(0,trainProjectMapper.findAllTrainProject().size());
    }
}

