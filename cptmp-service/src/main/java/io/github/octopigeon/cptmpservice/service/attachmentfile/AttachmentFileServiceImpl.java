package io.github.octopigeon.cptmpservice.service.attachmentfile;

import io.github.octopigeon.cptmpdao.mapper.AttachmentFileMapper;
import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
@Service
public class AttachmentFileServiceImpl implements AttachmentFileService {

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    /**
     * 添加文件
     * @param fileInfo 文件dto
     * @throws Exception
     */
    @Override
    public void add(FileDTO fileInfo) throws Exception {
        try{
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setFileName(fileInfo.getFileName());
            attachmentFile.setFilePath(fileInfo.getFilePath());
            attachmentFile.setFileSize(BigInteger.valueOf(fileInfo.getFileSize()));
            attachmentFile.setFileType(fileInfo.getFileType());
            attachmentFile.setGmtCreate(fileInfo.getGmtCreate());
            attachmentFile.setOriginName(fileInfo.getOriginalName());
            attachmentFileMapper.addAttachmentFile(attachmentFile);
        }catch (Exception e){
            throw new Exception("文件添加失败");
        }
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(FileDTO dto) throws Exception {
        String fileName = dto.getFileName();
        if(attachmentFileMapper.findAttachmentFileByfileName(fileName) != null)
        {
            // 删除索引
            attachmentFileMapper.removeAttachmentFile(fileName);
        }else {
            throw new Exception("File not found " + fileName);
        }
    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    @Deprecated
    public Boolean modify(FileDTO dto) throws Exception {
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    @Deprecated
    public FileDTO findById(BigInteger id) throws Exception {
        return null;
    }
}
