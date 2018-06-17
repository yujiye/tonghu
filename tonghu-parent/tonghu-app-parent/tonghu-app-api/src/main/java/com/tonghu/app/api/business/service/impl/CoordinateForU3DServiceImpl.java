package com.tonghu.app.api.business.service.impl;

import com.google.gson.Gson;
import com.tonghu.app.api.business.service.CoordinateForU3DService;
import com.tonghu.app.api.controller.CoordinateForU3DController;
import com.tonghu.pub.business.dao.PositionTestDao;
import com.tonghu.pub.common.utils.GsonWrapper;
import com.tonghu.pub.model.business.po.PositionTest;
import com.tonghu.pub.model.coordinate.DevicePosition;
import com.tonghu.pub.model.coordinate.Position;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangyongjian
 * @desc
 * @create 2018-06-17 18:48
 **/
@Service("coordinateForU3DService")
public class CoordinateForU3DServiceImpl implements CoordinateForU3DService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateForU3DServiceImpl.class);

    private static Gson GSON = new Gson();

    @Autowired
    private PositionTestDao positionTestDao;

    @Override
    public DevicePosition saveDevicePosition(String coordinateInfo, ModelMap model){
        DevicePosition dp = parseCoordinateInfo(coordinateInfo);
        if (dp == null) {
            model.put("errorInfo", "parse json failed");
            return null;
        }

        if (dp.getPosition().getX() == null ||
                dp.getPosition().getY() == null ||
                dp.getPosition().getZ() == null) {
            model.put("errorInfo", "x,y,z all value can not null");
            return null;
        }

        PositionTest pt = new PositionTest();
        pt.setX(dp.getPosition().getX());
        pt.setY(dp.getPosition().getY());
        pt.setZ(dp.getPosition().getZ());
        positionTestDao.addOne(pt);
        return dp;
    }

    @Override
    public List<Position> getAllDevicePosition() {
        List<PositionTest> ptList = positionTestDao.getAllPositionTestInfo();
        if (!CollectionUtils.isEmpty(ptList)) {
            List<Position> positions = new ArrayList<>();
            for (PositionTest pt : ptList) {
                positions.add(new Position(pt.getX(), pt.getY(), pt.getZ()));
            }
            return positions;
        }
        return null;
    }


    private DevicePosition parseCoordinateInfo(String coordinateInfo) {
        DevicePosition dp = null;
        if (StringUtils.isEmpty(coordinateInfo)) {
            return null;
        }

        try {
            if (StringUtils.isNotEmpty(coordinateInfo)) {
                dp = GSON.fromJson(coordinateInfo, DevicePosition.class);
            }
        } catch (Exception e) {
            LOGGER.error("the json spell of append coordinateInfo is wrong: coordinateInfo: {}", coordinateInfo);
            return null;
        }

        return dp;
    }

}
