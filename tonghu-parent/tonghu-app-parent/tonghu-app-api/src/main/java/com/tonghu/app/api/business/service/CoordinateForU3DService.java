package com.tonghu.app.api.business.service;

import com.tonghu.pub.model.coordinate.DevicePosition;
import com.tonghu.pub.model.coordinate.Position;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * @author liangyongjian
 * @desc U3D设置设备坐标信息
 * @create 2018-06-17 14:19
 **/
public interface CoordinateForU3DService {

    DevicePosition saveDevicePosition(String coordinateInfo, ModelMap model);

    List<Position> getAllDevicePosition();
}
