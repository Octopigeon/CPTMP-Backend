package io.github.octopigeon.cptmpservice.service.otherservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.octopigeon.cptmpdao.mapper.TrainMapper;
import io.github.octopigeon.cptmpdao.model.Train;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/16
 * @last-check-in 李国豪
 * @date 2020/7/18
 */
@Service
public class PunchServiceImpl implements PunchService{

    @Autowired
    private TrainMapper trainMapper;

    /**
     * 根据位置进行打卡
     *
     * @param trainId    实训id
     * @param longitude 签到的经度
     * @param latitude   签到的纬度
     * @return
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
     * @return
     */
    @Override
    public Boolean facePunch() {
        return null;
    }
}
