package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpdao.mapper.AttachmentFileMapper;
import io.github.octopigeon.cptmpdao.model.AttachmentFile;
import io.github.octopigeon.cptmpweb.config.FileProperties;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Service
public class FileTransferServiceImpl implements FileTransferService{
    // 文件在本地存储的地址
    private final Path fileStorageLocation;

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    @Autowired
    public FileTransferServiceImpl(FileProperties fileProperties) throws Exception {

        String path = fileProperties.getUploadBaseDir()+fileProperties.getUploadFile();
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * 单个文件上传接收服务
     * @param file 多文件
     * @param userId 上传的用户
     * @param teamId 上传所属团队，可为空
     * @return 文件对应的url
     * @throws Exception
     */
    @Override
    public String singleStoreFile(MultipartFile file, BigInteger userId, BigInteger teamId) throws Exception {
        String originName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try{
            if(originName.contains("..")){
                throw new Exception("Sorry! Filename contains invalid path sequence "+originName);
            }
            // 创建路径
            Calendar cal = Calendar.getInstance();
            String relativePath = String.format("%d\\%d\\%d\\",cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
            Path realPath = Paths.get(this.fileStorageLocation.toString(), relativePath).toAbsolutePath().normalize();
            try {
                Files.createDirectories(realPath);
            } catch (Exception ex) {
                throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
            }
            String extension = FilenameUtils.getExtension(originName);
            String fileName = productFilename(extension);
            Path filePath = realPath.resolve(fileName);
            // 文件存储
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            //数据库操作
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setFileName(fileName);
            attachmentFile.setFilePath(filePath.toString());
            attachmentFile.setFileSize(BigInteger.valueOf(file.getSize()));
            attachmentFile.setFileType(file.getContentType());
            attachmentFile.setGmtCreate(new Date());
            attachmentFile.setOriginName(originName);
            attachmentFile.setUserId(userId);
            attachmentFile.setTeamId(teamId);
            attachmentFileMapper.addAttachmentFile(attachmentFile);
            return fileName;
        } catch (Exception ex) {
            throw new Exception("Could not store file " + originName + ". Please try again!", ex);
        }
    }

    /**
     * 文件下载服务
     *
     * @param fileName 文件的url
     * @return 是否传输成功
     * @throws IOException
     */
    @Override
    public Resource loadFile(String fileName, String year, String month, String day) throws Exception {
        try {
            Path path = Paths.get(this.fileStorageLocation.toString(), year, month, day);
            Path filePath = path.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return 是否删除成功
     * @throws Exception
     */
    @Override
    public Boolean deleteFile(String fileName, String year, String month, String day) throws Exception {
        try {
            Path path = Paths.get(this.fileStorageLocation.toString(), year, month, day);
            Path filePath = path.resolve(fileName).normalize();
            if(attachmentFileMapper.findAttachmentFileByfileName(fileName) != null)
            {
                // 删除索引
                attachmentFileMapper.removeAttachmentFile(fileName);
                // 删除文件
                if(filePath.toFile().delete()){
                    return true;
                }
                else {
                    throw new Exception("File delete failed");
                }
            }else {
                throw new Exception("File not found " + fileName);
            }
        } catch (Exception e){
            throw new Exception("File not found " + fileName, e);
        }
    }

    private String productFilename(String extension){
        Random rand = new Random();
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);

        return String.format("%d_%d_%d_%d.%s", intYear,intMonth,intDay,(random > 0 ? random : ( -1) * random), extension);
    }
}
