package io.github.octopigeon.cptmpservice.service.attachmentfile;

import io.github.octopigeon.cptmpdao.mapper.AttachmentFileMapper;
import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in Gh Li
 * @date 2020/7/11
 */
@Service
public class AttachmentFileServiceImpl extends BaseFileServiceImpl implements AttachmentFileService {

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    @Autowired
    public AttachmentFileServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 移除文件
     * @param fileName 文件名
     * @throws Exception
     */
    @Override
    public void remove(String fileName) throws Exception {
        if(attachmentFileMapper.findAttachmentFileByfileName(fileName) != null)
        {
            // 删除索引
            attachmentFileMapper.removeAttachmentFile(fileName);
        }else {
            throw new Exception("File not found " + fileName);
        }
    }

    /**
     * 添加文件
     * @param file 文件
     * @throws Exception
     */
    @Override
    public void add(MultipartFile file) throws Exception {
        try{
            FileDTO fileInfo = storeFile(file);
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
}
