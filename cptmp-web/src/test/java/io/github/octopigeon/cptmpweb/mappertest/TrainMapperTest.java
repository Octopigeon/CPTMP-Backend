package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
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
public class TrainMapperTest extends BaseTest {

    @Autowired
    private TrainMapper trainMapper;
    @Test
    public void test(){
        /**
         * 设置数据
         */
        Train train1 =new Train();
        train1.setProcessId(BigInteger.valueOf(1));
        train1.setAcceptStandard("test1");
        train1.setSchoolId(BigInteger.valueOf(1));
        train1.setSchoolId(BigInteger.valueOf(1));
        train1.setContent("test1");
        train1.setStartDate(new Date());
        train1.setFinishDate(new Date());
        train1.setGmtCreate(new Date());
        train1.setResourceLibrary("test1");

        Train train2 =new Train();
        train2.setProcessId(BigInteger.valueOf(2));
        train2.setAcceptStandard("test2");
        train2.setSchoolId(BigInteger.valueOf(2));
        train2.setSchoolId(BigInteger.valueOf(2));
        train2.setContent("test2");
        train2.setStartDate(new Date());
        train2.setFinishDate(new Date());
        train2.setGmtCreate(new Date());
        train2.setResourceLibrary("test2");


        /**
         * 添加
         */
        trainMapper.removeAllTrain(new Date());
        trainMapper.addTrainProject(train1);
        trainMapper.addTrainProject(train2);
        Assertions.assertEquals(2, trainMapper.findAllTrain().size());

        /**
         * 删除
         */
        Assertions.assertEquals("test1",trainMapper.findAllTrain().get(0).getAcceptStandard());
        trainMapper.removeTrainProjectById(trainMapper.findAllTrain().get(0).getId(),new Date());

        /**
         * 更新
         */
        trainMapper.updateTrainProjectById(trainMapper.findAllTrain().get(0).getId(),new Date(),BigInteger.valueOf(2),BigInteger.valueOf(22),new Date(),new Date(),"test3","test3");
        Assertions.assertEquals("test3",trainMapper.findAllTrain().get(0).getContent());
        Assertions.assertEquals(1,trainMapper.findAllTrain().size());


//        实训项目
//        Assertions.assertEquals(train2.getContent(), trainMapper.findTrainProjectByProjectNameAmbiguously("2").get(0).getContent());
//
//
//        trainMapper.updateTrainProjectByProjectName(new Date(),"test2",new Date(), new Date(),"test3 content", "test3","www.hao123.com");
//        Assertions.assertEquals("test3 content", trainMapper.findTrainProjectByProjectNameAmbiguously("2").get(0).getContent());



    }
}

