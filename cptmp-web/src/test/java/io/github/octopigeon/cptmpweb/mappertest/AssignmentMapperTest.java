package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.AssignmentMapper;
import io.github.octopigeon.cptmpdao.model.Assignment;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.2
 * @date 2020/7/8
 *
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
public class AssignmentMapperTest extends BaseTest {
    @Autowired
    private AssignmentMapper assignmentMapper;

    @Test
    public void test()
    {
        /**
         * 设置数据
         */
        Assignment assignment1 = new Assignment();
        assignment1.setGmtCreate(new Date());
        assignment1.setFile(false);
        assignment1.setDocumentPath("test1");
        assignment1.setTitle("test1");
        assignment1.setContent("test1");

        Assignment assignment2 = new Assignment();
        assignment2.setGmtCreate(new Date());
        assignment2.setFile(true);
        assignment2.setDocumentPath("test2");
        assignment2.setTitle("test2");
        assignment2.setContent("test2");


        /**
         * 添加
         */
        assignmentMapper.removeAllAssignmentTest();
        assignmentMapper.addAssignment(assignment1);
        assignmentMapper.addAssignment(assignment2);
        Assertions.assertEquals(2, assignmentMapper.findAllAssignment().size());

        /**
         * 删除
         */
        assignmentMapper.removeAssignmentById(assignmentMapper.findAllAssignment().get(0).getId(),new Date());
        Assertions.assertEquals(1, assignmentMapper.findAllAssignment().size());

        /**
         * 更新
         */
        Assignment assignment3 = assignmentMapper.findAllAssignment().get(0);
        assignment3.setContent("test3");
        assignmentMapper.updateAssignmentById(assignment3);
        Assertions.assertEquals("test3", assignmentMapper.findAllAssignment().get(0).getContent());

        assignmentMapper.removeAllAssignment(new Date());
        Assertions.assertEquals(0, assignmentMapper.findAllAssignment().size());


    }
}
