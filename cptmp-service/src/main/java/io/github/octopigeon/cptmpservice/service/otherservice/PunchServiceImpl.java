package io.github.octopigeon.cptmpservice.service.otherservice;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/16
 * @last-check-in Gh Li
 * @date 2020/7/16
 */
@Service
public class PunchServiceImpl implements PunchService{

    private double limits = 10.0;

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
        // TODO 根据trainID获取实训打卡点
        double latitudeFrom = 0.0;
        double longitudeFrom = 0.0;
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitude, longitude);
        double distance = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
        return distance <= this.limits;
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
