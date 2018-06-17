package com.tonghu.app.api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tonghu.app.api.business.service.CoordinateForU3DService;
import com.tonghu.pub.common.utils.GsonWrapper;
import com.tonghu.pub.common.web.ApiResponse;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.coordinate.DevicePosition;
import com.tonghu.pub.model.coordinate.Position;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author liangyongjian
 * @desc U3D设置设备坐标信息
 * @create 2018-06-17 14:16
 **/
@Controller
@RequestMapping("/coordinate")
public class CoordinateForU3DController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateForU3DController.class);

    @Autowired
    CoordinateForU3DService coordinateForU3DService;

	@RequestMapping(value = "/setCoordinate.do", method = RequestMethod.GET)
    public @ResponseBody
    ApiResponse<? extends Object, ? extends Object, ? extends Object> setCoordinate(
            HttpServletRequest request, HttpServletResponse response, ModelMap model,
            @RequestParam(value="coordinateInfo", required=true)String coordinateInfo) {
        LOGGER.info("U3D设置设备坐标信息，接收到的参数为 coordinateInfo={}", coordinateInfo);

        DevicePosition dp = coordinateForU3DService.saveDevicePosition(coordinateInfo, model);

        if (dp == null) {
            LOGGER.info("U3D设置设备坐标信息，参数解析失败 coordinateInfo={} errorInfo={}",
                    coordinateInfo, model.get("errorInfo"));
            return new ApiResponse<Object, Object,Object>(null, model.get("errorInfo"),-1);
        }
        LOGGER.info("U3D设置设备坐标信息，设置成功 coordinateInfo={}", coordinateInfo);

        return new ApiResponse<Object, Object,Object>(null,"success",0);
    }

    @RequestMapping(value = "/getCoordinate.do", method = RequestMethod.GET)
    public @ResponseBody
    ApiResponse<? extends Object, ? extends Object, ? extends Object> getCoordinate(
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("U3D获取设备坐标信息");
        List<Position> positions = coordinateForU3DService.getAllDevicePosition();
        Map<String, List<Position>> map = new HashMap<>();
        map.put("positions", positions);
        return new ApiResponse<Object, Object,Object>(map,"success",0);
    }

}
