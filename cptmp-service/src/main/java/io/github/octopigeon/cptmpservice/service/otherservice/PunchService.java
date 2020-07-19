package io.github.octopigeon.cptmpservice.service.otherservice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/16
 * @last-check-in Gh Li
 * @date 2020/7/16
 */
@Service
public interface PunchService {

    /**
     * 根据位置进行打卡
     * @param trainId 实训id
     * @param longitude 签到的经度
     * @param latitude 签到的纬度
     * @return
     */
    Boolean locationPunch(BigInteger trainId, double longitude, double latitude);

    /**
     * 采用人脸进行打卡
     * @param image 人脸文件
     * @param username 用户名
     * @return
     */
    Boolean facePunch(MultipartFile image, String username) throws Exception;

    /**
     * 添加人脸信息
     * @param image 人脸图片
     * @param username 用户名
     */
    void addFaceInfo(MultipartFile image, String username) throws Exception;

    /**
     * 删除人脸联系
     * @param username 用户名
     */
    void removeFaceInfo(String username) throws Exception;
}
