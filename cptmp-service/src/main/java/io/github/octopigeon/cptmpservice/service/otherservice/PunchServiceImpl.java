package io.github.octopigeon.cptmpservice.service.otherservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Train;
import io.github.octopigeon.cptmpservice.config.FaceProperties;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/16
 * @last-check-in 李国豪
 * @date 2020/7/18
 */
@Service
public class PunchServiceImpl implements PunchService{

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private FaceProperties faceProperties;

    /** 以下为调用人脸识别接口所用的常量 */
    private final String personId = "PersonId";
    private final String image = "Image";
    private final String groupId = "GroupId";
    private final String personName = "PersonName";

    /**
     * 根据位置进行打卡
     *
     * @param trainId    实训id
     * @param longitude 签到的经度
     * @param latitude   签到的纬度
     * @return 是否签到成功
     */
    @Override
    public Boolean locationPunch(BigInteger trainId, double longitude, double latitude) {
        Train train = trainMapper.findTrainById(trainId);
        Double limits;
        if(train.getLimits() == null){
            limits = 100.;
        }else {
            limits = train.getLimits();
        }
        JSONObject object = JSON.parseObject(train.getGpsInfo());
        double latitudeFrom = object.getDoubleValue("latitude");
        double longitudeFrom = object.getDoubleValue("longitude");
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitude, longitude);
        double distance = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
        return distance <= limits;
    }

    /**
     * 采用人脸进行打卡
     *
     * @param image    人脸文件
     * @param username 用户名
     * @return 是否签到成功
     */
    @Override
    public Boolean facePunch(MultipartFile image, String username) throws Exception {
        if(cptmpUserMapper.findUserByUsername(username) == null){
            throw new Exception("User is not exist!");
        }
        try{
            IaiClient client = createClient();
            String base64 = convertMultiFileToBase(image);
            // 发起访问
            JSONObject params = new JSONObject();
            params.put(this.image, base64);
            params.put(this.personId, username);
            if(validateImageFace(client, base64)){
                VerifyFaceRequest req = VerifyFaceRequest.fromJsonString(params.toJSONString(), VerifyFaceRequest.class);
                //处理返回
                VerifyFaceResponse resp = client.VerifyFace(req);
                return resp.getIsMatch();
            }else {
                throw new ValueException("图片中不存在人脸信息");
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 添加人脸信息
     *
     * @param image    人脸图片
     * @param username 用户名
     */
    @Override
    public void addFaceInfo(MultipartFile image, String username) throws Exception {
        CptmpUser user = cptmpUserMapper.findUserByUsername(username);
        if(user == null){
            throw new Exception("User is not exist!");
        }
        try{
            IaiClient client = createClient();

            JSONObject params = new JSONObject();
            String base64 = convertMultiFileToBase(image);
            params.put(this.groupId, faceProperties.getGroupId());
            params.put(this.personName, user.getName());
            params.put(this.personId, user.getUsername());
            params.put(this.image, base64);
            if(validateImageFace(client, base64)){
                CreatePersonRequest req = CreatePersonRequest.fromJsonString(params.toJSONString(), CreatePersonRequest.class);
                CreatePersonResponse resp = client.CreatePerson(req);
                System.out.println(CreatePersonResponse.toJsonString(resp));
            }else {
                throw new ValueException("图片中不存在人脸信息");
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 删除人脸联系
     *
     * @param username 用户名
     */
    @Override
    public void removeFaceInfo(String username) throws Exception {
        CptmpUser user = cptmpUserMapper.findUserByUsername(username);
        if(user == null){
            throw new Exception("User is not exist!");
        }
        try{
            IaiClient client = createClient();

            JSONObject params = new JSONObject();
            params.put(this.groupId, faceProperties.getGroupId());
            params.put(this.personId, user.getUsername());
            DeletePersonFromGroupRequest req = DeletePersonFromGroupRequest.fromJsonString(params.toJSONString(), DeletePersonFromGroupRequest.class);
            DeletePersonFromGroupResponse resp = client.DeletePersonFromGroup(req);
            System.out.println(DeletePersonFromGroupResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 将multifile转换层base64编码
     * @param file 文件
     * @return Base64编码
     * @throws Exception 转base64编码异常
     */
    private String convertMultiFileToBase(MultipartFile file) throws Exception {
        try{
            return Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Covert multifile to base64 failed!");
        }
    }

    /**
     * 创建与腾讯云链接的客户端
     * @return 客户端
     */
    private IaiClient createClient(){
        Credential cred = new Credential(faceProperties.getSecretId(), faceProperties.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(faceProperties.getEndpoint());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new IaiClient(cred, faceProperties.getRegion(), clientProfile);
    }

    /**
     * 验证图片是否存在人脸
     * @param client 客户端
     * @param imageBase64 图片的base64编码
     * @return 是否存在人脸
     */
    private Boolean validateImageFace(IaiClient client, String imageBase64) throws TencentCloudSDKException {
        JSONObject params = new JSONObject();
        params.put(this.image, imageBase64);
        DetectFaceRequest req = DetectFaceRequest.fromJsonString(params.toJSONString(), DetectFaceRequest.class);
        DetectFaceResponse response = client.DetectFace(req);
        return response.getFaceInfos() != null;
    }
}
