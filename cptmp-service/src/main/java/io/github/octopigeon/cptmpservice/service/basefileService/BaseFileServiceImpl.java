package io.github.octopigeon.cptmpservice.service.basefileService;

import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;

/**
 * @author 李国豪
 * @version 1.1
 * @date 2020/7/10
 * @last-check-in 李国豪
 * @date 2020/7/11
 */
@Service
public class BaseFileServiceImpl implements BaseFileService {
    // 公开文件在本地存储的地址
    private final Path publicFileStorageLocation;
    // 隐私文件在本地存储的地址
    private final Path privateFileStorageLocation;
    private final String publicFileUrl = "storage";
    private final String privateFileUrl = "api/storage";
    private final String domain;

    @Autowired
    public BaseFileServiceImpl(FileProperties fileProperties) throws Exception {

        String path = fileProperties.getUploadDir();
        // this.domain = fileProperties.getDomain();
        this.domain = "";
        this.publicFileStorageLocation = Paths.get(path, "storage").toAbsolutePath().normalize();
        this.privateFileStorageLocation = Paths.get(path, "private").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.publicFileStorageLocation);
            Files.createDirectories(this.privateFileStorageLocation);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * 公开文件上传接收服务
     * @param file 文件流
     * @return 文件相关信息
     * @throws Exception
     */
    @Override
    public FileDTO storePublicFile(MultipartFile file) throws Exception {
        return storeFile(file, this.publicFileStorageLocation.toString(), this.publicFileUrl);
    }

    /**
     * 私密附件接收服务
     * @param file 多文件
     * @return 文件名
     * @throws Exception
     */
    @Override
    public FileDTO storePrivateFile(MultipartFile file) throws Exception {
        return storeFile(file, this.privateFileStorageLocation.toString(), this.privateFileUrl);
    }

    /**
     * 文件下载服务
     *
     * @param fileName 文件的url
     * @return 是否传输成功
     * @throws IOException
     */
    @Override
    public Resource loadPublicFile(String fileName, String year, String month, String day) throws Exception {
        return loadFile(fileName, year, month, day, this.publicFileStorageLocation.toString());
    }

    /**
     * 文件下载服务
     *
     * @param fileName 文件的url
     * @return 是否传输成功
     * @throws IOException
     */
    @Override
    public Resource loadPrivateFile(String fileName, String year, String month, String day) throws Exception {
        return loadFile(fileName, year, month, day, this.privateFileStorageLocation.toString());
    }

    private Resource loadFile(String fileName, String year, String month, String day, String storagePath) throws Exception{
        try {
            Path path = Paths.get(storagePath, year, month, day);
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

    private FileDTO storeFile(MultipartFile file, String storagePath, String url) throws Exception{
        String originName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try{
            if(originName.contains("..")){
                throw new Exception("Sorry! Filename contains invalid path sequence "+originName);
            }
            // 创建路径
            Calendar cal = Calendar.getInstance();
            String relativePath = String.format("%d\\%d\\%d\\",cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
            Path realPath = Paths.get(storagePath, relativePath).toAbsolutePath().normalize();
            try {
                Files.createDirectories(realPath);
            } catch (Exception ex) {
                throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
            }
            String extension = FilenameUtils.getExtension(originName);
            String fileName = String.format("%s.%s", productFilename(), extension);
            Path filePath = realPath.resolve(fileName);
            // 文件存储
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            //构建返回fileDTO
            FileDTO fileResp = new FileDTO();
            fileResp.setGmtCreate(new Date());
            fileResp.setFileName(fileName);
            fileResp.setFilePath(String.format("%s/%s/%d/%d/%d/%s", this.domain, url, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), fileName));
            fileResp.setOriginalName(originName);
            fileResp.setFileSize(file.getSize());
            fileResp.setFileType(file.getContentType());
            return fileResp;
        } catch (Exception ex) {
            throw new Exception("Could not store file " + originName + ". Please try again!", ex);
        }
    }

    /**
     * 产生文件名，不需要管扩展名
     * @return
     */
    private String productFilename(){

        return UUID.randomUUID().toString();
    }
}
