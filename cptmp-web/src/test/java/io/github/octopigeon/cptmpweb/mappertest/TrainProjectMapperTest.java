package io.github.octopigeon.cptmpweb.mappertest;

import org.junit.Test;
import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.model.TrainProject;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

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
        trainProject1.setProjectName("test1");
        trainProject1.setAcceptStandard("test1");
        trainProject1.setContent("test1  content");
        trainProject1.setStartDate(new Date());
        trainProject1.setFinishDate(new Date());
        trainProject1.setGmtCreate(new Date());
        trainProject1.setResourceLibrary("www.baidu.com");

        TrainProject trainProject2=new TrainProject();
        trainProject2.setProjectName("test2");
        trainProject2.setAcceptStandard("test2");
        trainProject2.setContent("test2  content");
        trainProject2.setStartDate(new Date());
        trainProject2.setFinishDate(new Date());
        trainProject2.setGmtCreate(new Date());
        trainProject2.setResourceLibrary("www.hao123.com");

        trainProjectMapper.removeAllTrainProjects();
        trainProjectMapper.addTrainProject(trainProject1);
        trainProjectMapper.addTrainProject(trainProject2);
        Assertions.assertEquals(2,trainProjectMapper.findAllTrainProject().size());

        trainProjectMapper.removeTrainProjectByProjectName("test1");
        Assertions.assertEquals(trainProject2.getContent(),trainProjectMapper.findTrainProjectByProjectNameAmbiguously("2").get(0).getContent());
        Assertions.assertEquals(1,trainProjectMapper.findAllTrainProject().size());

        trainProjectMapper.updateTrainProjectByProjectName(new Date(),"test2",new Date(), new Date(),"test3 content", "test3","www.hao123.com");
        Assertions.assertEquals("test3 content",trainProjectMapper.findTrainProjectByProjectNameAmbiguously("2").get(0).getContent());

        Assertions.assertEquals(1, trainProjectMapper.findTrainProjectByProjectNameAmbiguously("test").size());

    }
}

