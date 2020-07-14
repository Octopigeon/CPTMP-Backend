package io.github.octopigeon.cptmpweb.mappertest;

import org.junit.Test;
import io.github.octopigeon.cptmpdao.mapper.ProjectMapper;
import io.github.octopigeon.cptmpdao.model.Project;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
public class ProjectMapperTest extends BaseTest {
    @Autowired
    private ProjectMapper projectMapper;
    @Test
    public void test(){
        /**
         * 设置数据
         */
        Project project1 =new Project();
        project1.setGmtCreate(new Date());
        project1.setContent("test1");
        project1.setLevel(1);
        project1.setName("test1");
        project1.setResourceLibrary("test1");

        Project project2 =new Project();
        project2.setGmtCreate(new Date());
        project2.setGmtCreate(new Date());
        project2.setContent("test2");
        project2.setLevel(2);
        project2.setName("test2");
        project2.setResourceLibrary("test2");

        /**
         * 添加
         */
        projectMapper.removeAllTrainProjectsTest();
        projectMapper.addTrainProject(project1);
        projectMapper.addTrainProject(project2);
        Assertions.assertEquals(2, projectMapper.findAllTrainProject().size());

        /**
         * 删除
         */
        projectMapper.removeTrainProjectByName("test1",new Date());
        Assertions.assertEquals(project2.getContent(), projectMapper.findTrainProjectByNameAmbiguously("2").get(0).getContent());
        Assertions.assertEquals(1, projectMapper.findAllTrainProject().size());

        /**
         * 更新
         */
        Project project3 = projectMapper.findAllTrainProject().get(0);
        project3.setContent("test3");
        projectMapper.updateTrainProjectByName(project3);
        Assertions.assertEquals("test3", projectMapper.findTrainProjectByNameAmbiguously("test2").get(0).getContent());

        Assertions.assertEquals(1, projectMapper.findTrainProjectByNameAmbiguously("test2").size());

        projectMapper.removeAllTrainProjects(new Date());
        Assertions.assertEquals(0, projectMapper.findAllTrainProject().size());
    }
}

