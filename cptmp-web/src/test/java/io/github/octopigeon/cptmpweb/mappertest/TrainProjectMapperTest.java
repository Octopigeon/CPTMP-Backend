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
 * last-check-in anlow
 * @date 2020/7/10
 */
public class TrainProjectMapperTest extends BaseTest {
    @Autowired
    private TrainProjectMapper trainProjectMapper;
    @Test
    public void test(){
        TrainProject trainProject1=new TrainProject();
        trainProject1.setGmtCreate(new Date());
        trainProject1.setTrainId(BigInteger.valueOf(1));
        trainProject1.setProjectName("test1");
        trainProject1.setProjectLevel(1);
        trainProject1.setProjectContent("test1");
        trainProject1.setResourceLibrary("test1");

        TrainProject trainProject2=new TrainProject();
        trainProject2.setGmtCreate(new Date());
        trainProject2.setTrainId(BigInteger.valueOf(2));
        trainProject2.setProjectName("test2");
        trainProject2.setProjectLevel(2);
        trainProject2.setProjectContent("test2");
        trainProject2.setResourceLibrary("test2");

        trainProjectMapper.removeAllTrainProjects();
        trainProjectMapper.addTrainProject(trainProject1);
        trainProjectMapper.addTrainProject(trainProject2);
        Assertions.assertEquals(2,trainProjectMapper.findAllTrainProject().size());

        trainProjectMapper.removeTrainProjectByProjectName("test1");
        Assertions.assertEquals(trainProject2.getProjectContent(),trainProjectMapper.findTrainProjectByProjectNameAmbiguously("2").get(0).getProjectContent());
        Assertions.assertEquals(1,trainProjectMapper.findAllTrainProject().size());

        trainProjectMapper.updateTrainProjectByProjectName(new Date(), BigInteger.valueOf(2), "test2", 3,"test3","www");
        Assertions.assertEquals("test3",trainProjectMapper.findTrainProjectByProjectNameAmbiguously("test2").get(0).getProjectContent());

        Assertions.assertEquals(1, trainProjectMapper.findTrainProjectByProjectNameAmbiguously("test2").size());

    }
}

