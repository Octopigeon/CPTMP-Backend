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
        train1.setStandard("test1");
        train1.setOrganizationId(BigInteger.valueOf(1));
        train1.setContent("test1");
        train1.setName("test1");
        train1.setStarTime(new Date());
        train1.setEndTime(new Date());
        train1.setGmtCreate(new Date());
        train1.setResourceLibrary("test1");
        train1.setGpsInfo("test1");

        Train train2 =new Train();
        train2.setStandard("test2");
        train2.setName("test2");
        train2.setOrganizationId(BigInteger.valueOf(2));
        train2.setContent("test2");
        train2.setStarTime(new Date());
        train2.setEndTime(new Date());
        train2.setGmtCreate(new Date());
        train2.setResourceLibrary("test2");
        train2.setGpsInfo("test2");


        /**
         * 添加
         */
        trainMapper.removeAllTrainTest();
        trainMapper.addTrain(train1);
        trainMapper.addTrain(train2);
        Assertions.assertEquals(2, trainMapper.findAllTrain().size());

//        /**
//         * 删除
//         */
//        Assertions.assertEquals("test1",trainMapper.findAllTrain().get(0).getStandard());
//        trainMapper.removeTrainById(trainMapper.findAllTrain().get(0).getId(),new Date());
//
//        /**
//         * 更新
//         */
//        Train train3=trainMapper.findAllTrain().get(0);
//        train3.setContent("test3");
//        trainMapper.updateTrainProjectById(train3);
//        Assertions.assertEquals("test3",trainMapper.findAllTrain().get(0).getContent());
//        Assertions.assertEquals(1,trainMapper.findAllTrain().size());
//
//        trainMapper.removeAllTrain(new Date());
//        Assertions.assertEquals(0,trainMapper.findAllTrain().size());

    }
}

