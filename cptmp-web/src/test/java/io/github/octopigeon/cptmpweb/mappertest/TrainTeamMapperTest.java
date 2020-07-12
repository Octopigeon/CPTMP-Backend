package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.mapper.TrainProjectMapper;
import io.github.octopigeon.cptmpdao.mapper.TrainTeamMapper;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpdao.model.TrainTeam;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.2
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/10
 */
public class TrainTeamMapperTest extends BaseTest {
    @Autowired
    private TrainTeamMapper trainTeamMapper;
    @Test
    public void test(){
        /**
         * 设置数据
         */
        TrainTeam trainTeam1=new TrainTeam();
        trainTeam1.setGmtCreate(new Date());
        trainTeam1.setTeamName("test1");
        trainTeam1.setCodeBaseUrl("test1");
        trainTeam1.setMasterUserId(BigInteger.valueOf(1));
        trainTeam1.setPmUserId(BigInteger.valueOf(1));
        trainTeam1.setPoUserId(BigInteger.valueOf(1));
        trainTeam1.setTrainProjectId(BigInteger.valueOf(1));
        trainTeam1.setTeamGrade(BigDecimal.valueOf(100.0));

        TrainTeam trainTeam2=new TrainTeam();
        trainTeam2.setGmtCreate(new Date());
        trainTeam2.setTeamName("test2");
        trainTeam2.setCodeBaseUrl("test2");
        trainTeam2.setMasterUserId(BigInteger.valueOf(2));
        trainTeam2.setPmUserId(BigInteger.valueOf(2));
        trainTeam2.setPoUserId(BigInteger.valueOf(2));
        trainTeam2.setTrainProjectId(BigInteger.valueOf(2));
        trainTeam2.setTeamGrade(BigDecimal.valueOf(90.0));


        /**
         * 添加
         */
        trainTeamMapper.removeAllTrainTeam(new Date());
        trainTeamMapper.addTrainTeam(trainTeam1);
        trainTeamMapper.addTrainTeam(trainTeam2);
        Assertions.assertEquals(2,trainTeamMapper.findAllTrainTeam().size());

        /**
         * 删除
         */
        trainTeamMapper.removeTrainTeamByTeamName("test1",new Date());
        Assertions.assertEquals(1,trainTeamMapper.findAllTrainTeam().size());

        /**
         * 更新
         */
        trainTeamMapper.updateTrainTeamByTeamName("test2",new Date(), BigInteger.valueOf(2), BigInteger.valueOf(3),BigInteger.valueOf(3),BigInteger.valueOf(3),BigDecimal.valueOf(99));
        Assertions.assertEquals(BigInteger.valueOf(3),trainTeamMapper.findAllTrainTeam().get(0).getPoUserId());


    }
}
