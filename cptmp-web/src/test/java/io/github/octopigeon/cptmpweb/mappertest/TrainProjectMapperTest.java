package io.github.octopigeon.cptmpweb.mappertest;

import com.alibaba.fastjson.JSON;
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
 * @date 2020/7/9
 */
public class TrainProjectMapperTest extends BaseTest {
    @Autowired
    private TrainProjectMapper trainProjectMapper;
    @Test
    public void test(){
        TrainProject trainProject1=new TrainProject();
        trainProject1.setProjectName("1");
        trainProject1.setAcceptStandard("test1");
        trainProject1.setContent("test1  content");
        trainProject1.setStartDate(new Date());
        trainProject1.setFinishDate(new Date());
        trainProject1.setGmtCreate(new Date());
        trainProject1.setResourceLibrary("www.baidu.com");

        TrainProject trainProject2=new TrainProject();
        trainProject2.setProjectName("2");
        trainProject2.setAcceptStandard("test2");
        trainProject2.setContent("test2  content");
        trainProject2.setStartDate(new Date());
        trainProject2.setFinishDate(new Date());
        trainProject2.setGmtCreate(new Date());
        trainProject2.setResourceLibrary("www.hao123.com");

        trainProjectMapper.addTrainProject(trainProject1);
        trainProjectMapper.addTrainProject(trainProject2);
        Assertions.assertEquals(2,trainProjectMapper.findAllTrainProject().size());

        trainProjectMapper.removeTrainProjectByProjectName("1");
        Assertions.assertEquals(trainProject2.getContent(),trainProjectMapper.findTrainProjectByProjectName("2").get(0).getContent());
        Assertions.assertEquals(1,trainProjectMapper.findAllTrainProject().size());

        trainProjectMapper.updateTrainProjectByProjectName(new Date(),"2",new Date(), new Date(),"test3 content", "test3","www.hao123.com");

        Assertions.assertEquals("test3 content",trainProjectMapper.findTrainProjectByProjectName("2").get(0).getContent());



    }
}

