package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.ProjectMapper;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

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
    private ProjectMapper projectMapper;
//    @Test
//    public void test(){
//        /**
//         * 设置数据
//         */
//        TrainProject trainProject1=new TrainProject();
//        trainProject1.setGmtCreate(new Date());
//        trainProject1.setTrainId(BigInteger.valueOf(1));
//        trainProject1.setProjectName("test1");
//        trainProject1.setProjectLevel(1);
//        trainProject1.setProjectContent("test1");
//        trainProject1.setResourceLibrary("test1");
//
//        TrainProject trainProject2=new TrainProject();
//        trainProject2.setGmtCreate(new Date());
//        trainProject2.setTrainId(BigInteger.valueOf(2));
//        trainProject2.setProjectName("test2");
//        trainProject2.setProjectLevel(2);
//        trainProject2.setProjectContent("test2");
//        trainProject2.setResourceLibrary("test2");
//
//        /**
//         * 添加
//         */
//        trainProjectMapper.removeAllTrainProjects(new Date());
//        trainProjectMapper.addTrainProject(trainProject1);
//        trainProjectMapper.addTrainProject(trainProject2);
//        Assertions.assertEquals(2,trainProjectMapper.findAllTrainProject().size());
//
//        /**
//         * 删除
//         */
//        trainProjectMapper.removeTrainProjectByProjectName("test1",new Date());
//        Assertions.assertEquals(trainProject2.getProjectContent(),trainProjectMapper.findTrainProjectByProjectNameAmbiguously("2").get(0).getProjectContent());
//        Assertions.assertEquals(1,trainProjectMapper.findAllTrainProject().size());
//
//        /**
//         * 更新
//         */
//        trainProjectMapper.updateTrainProjectByProjectName("test2",new Date(), BigInteger.valueOf(2),BigInteger.valueOf(2),2,"test3");
//        Assertions.assertEquals("test3",trainProjectMapper.findTrainProjectByProjectNameAmbiguously("test2").get(0).getProjectContent());
//
//        Assertions.assertEquals(1, trainProjectMapper.findTrainProjectByProjectNameAmbiguously("test2").size());
//
//    }
}

