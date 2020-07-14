package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.AttachmentFileMapper;
import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/12
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
public class AttachmentFileMapperTest extends BaseTest {
    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    @Test
    public void test() {
        /**
         * 设置两条数据
         */
        AttachmentFile attachmentFile1 = new AttachmentFile();
        attachmentFile1.setGmtCreate(new Date());
        attachmentFile1.setFileName("test1");
        attachmentFile1.setFilePath("test1");
        attachmentFile1.setFileType("test1");
        attachmentFile1.setOriginName("test1");
        attachmentFile1.setFileSize(BigInteger.valueOf(1));


        AttachmentFile attachmentFile2 = new AttachmentFile();
        attachmentFile2.setGmtCreate(new Date());
        attachmentFile2.setFileName("test2");
        attachmentFile2.setFilePath("test2");
        attachmentFile2.setFileType("test2");
        attachmentFile2.setOriginName("test2");
        attachmentFile2.setFileSize(BigInteger.valueOf(2));

        /**
         * 添加
         */
        attachmentFileMapper.removeAllAttachmentFileTest();
        attachmentFileMapper.addAttachmentFile(attachmentFile1);
        attachmentFileMapper.addAttachmentFile(attachmentFile2);
        Assertions.assertEquals(2, attachmentFileMapper.findAllAttachmentFile().size());

        /**
         * 删除
         */
        attachmentFileMapper.removeAttachmentFileByName("test1", new Date());
        Assertions.assertEquals(1, attachmentFileMapper.findAllAttachmentFile().size());

        /**
         * 更新
         */
        attachmentFileMapper.updateOriginNameByFileName("test2", new Date(), "test3");
        Assertions.assertEquals("test3", attachmentFileMapper.findAllAttachmentFile().get(0).getOriginName());

        AttachmentFile attachmentFile3=attachmentFileMapper.findAllAttachmentFile().get(0);
        attachmentFile3.setFilePath("test4");
        attachmentFileMapper.updateAttachmentFileByFileName(attachmentFile3);
        Assertions.assertEquals("test4", attachmentFileMapper.findAllAttachmentFile().get(0).getFilePath());

        attachmentFileMapper.removeAllAttachmentFile(new Date());
        Assertions.assertEquals(0, attachmentFileMapper.findAllAttachmentFile().size());

    }
    }
