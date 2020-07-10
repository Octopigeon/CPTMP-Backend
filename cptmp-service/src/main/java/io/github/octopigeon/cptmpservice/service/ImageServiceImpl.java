package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.SchoolStudent;
import io.github.octopigeon.cptmpservice.FileProperties;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;


/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Service
public class ImageServiceImpl implements ImageService{
    // 文件在本地存储的地址
    private final Path userAvatarLocation;
    private final Path userFaceLocation;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;
    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    @Autowired
    public ImageServiceImpl(FileProperties fileProperties) throws Exception {
        this.userAvatarLocation = Paths.get(fileProperties.getUploadBaseDir()+fileProperties.getUploadUserAvatar()).toAbsolutePath().normalize();
        this.userFaceLocation = Paths.get(fileProperties.getUploadBaseDir()+fileProperties.getUploadUserFace()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.userAvatarLocation);
            Files.createDirectories(this.userFaceLocation);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * 图片上传
     *
     * @param image    图片
     * @param username 用户名
     * @return 图片存储的url
     * @throws Exception
     */
    @Override
    public String storeImage(MultipartFile image, String username, String avatarOrFace) throws Exception {
        String originName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        try {
            if (originName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + originName);
            }
            String extension = FilenameUtils.getExtension(originName);
            String imageName = String.format("%s_%s.%s", avatarOrFace, username, extension);
            Path filePath = getFilePath(avatarOrFace, imageName);
            if(filePath == null)
            {
                throw new Exception("The url is error!");
            }
            // 写入文件
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // 访问数据库
            String relativePath = "";
            if(imageName.contains("avatar")){
                relativePath = String.format("/storage/avatar/%s", imageName);
                cptmpUserMapper.updateAvatarByUsername(username, new Date(), relativePath);
            }
            if(imageName.contains("face")){
                relativePath = String.format("/storage/face/%s", imageName);
                CptmpUser user = cptmpUserMapper.findUserByUsername(username);
                SchoolStudent student = schoolStudentMapper.findSchoolStudentByUserId(user.getId());
                schoolStudentMapper.updateSchoolStudetnByUserId(student.getUserId(), new Date(), student.getName(), student.getStudentId(), student.getSchoolName(), relativePath);
            }
            return relativePath;
        }catch (Exception ex){
            throw new Exception("Could not store file " + originName + ". Please try again!\n"+ ex.getMessage(), ex);
        }
    }

    /**
     * 图片下载
     *
     * @param imageName 图片url
     * @return 是否传输成功
     * @throws Exception
     */
    @Override
    public Resource uploadImage(String imageName, String avatarOrFace) throws Exception {
        try {
            Path filePath = getFilePath(avatarOrFace, imageName);
            if(filePath == null)
            {
                throw new Exception("Image not found");
            }
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("Image not found");
            }
        } catch (MalformedURLException ex) {
            throw new Exception("Image not found");
        }
    }

    private Path getFilePath(String avatarOrFace, String imageName)
    {
        if("avatar".equals(avatarOrFace))
        {
            return Paths.get(this.userAvatarLocation.toString(), imageName).normalize();
        }
        else if("face".equals(avatarOrFace))
        {
            return Paths.get(this.userFaceLocation.toString(), imageName).normalize();
        }
        else {
            return null;
        }
    }
}
