package io.github.octopigeon.cptmpservice.service.attachmentfile;

import io.github.octopigeon.cptmpdao.mapper.AttachmentFileMapper;
import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

/**
 * 附件的相关service，主要由service内部调用
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Service
public class AttachmentFileServiceImpl implements AttachmentFileService {

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    /**
     * 添加附件
     * @param fileInfo 文件dto
     * @throws Exception 填加失败异常
     */
    @Override
    public void add(FileDTO fileInfo) throws Exception {
        try{
            AttachmentFile attachmentFile = new AttachmentFile();
            BeanUtils.copyProperties(fileInfo, attachmentFile);
            attachmentFile.setGmtCreate(new Date());
            attachmentFileMapper.addAttachmentFile(attachmentFile);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("add attachment file failed!");
        }
    }

    /**
     * 移除数据
     * @param dto ：dto实体
     * @throws Exception 没有找到对应的附件
     */
    @Override
    public void remove(FileDTO dto) throws Exception {
        String fileName = dto.getFileName();
        if(attachmentFileMapper.findAttachmentFileByFileName(fileName) != null)
        {
            attachmentFileMapper.hideAttachmentFileByName(fileName, new Date());
        }else {
            throw new Exception("File not found " + fileName);
        }
    }

    /**
     * 更新的文件实体
     * 废弃不用
     * @param dto
     * @return 是否修改成功
     */
    @Override
    @Deprecated
    public Boolean modify(FileDTO dto) throws Exception {
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     * 废弃不用
     * @param id 查询
     * @return dto
     */
    @Override
    @Deprecated
    public FileDTO findById(BigInteger id) throws Exception {
        return null;
    }
}
