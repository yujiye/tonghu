package com.tonghu.pub.model.business.po;

import com.google.gson.annotations.SerializedName;
import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author liangyongjian
 * @desc IOT 设备信息
 * @create 2018-07-14 16:56
 **/
@Getter
@Setter
public class IotProPotsInfo extends BasePo {

    @SerializedName("AccessToken")
    private String accessToken;

    @SerializedName("IntelligenceDevices")
    private List<DeviceInfo> intelligenceDeviceList;

    @Getter
    @Setter
    public static class DeviceInfo{

        @SerializedName("DeviceName")
        private String deviceName;

        @SerializedName("DeviceType")
        private String deviceType;

        @SerializedName("DeviceList")
        private List<Device> deviceList;

    }

    @Getter
    @Setter
    public static class Device {

        private String name;

        private String x;

        private String y;

        @SerializedName("pID")
        private String pId;

        @SerializedName("ID")
        private String id;

        @SerializedName("api")
        private String apiUrl;

        @SerializedName("modelTypeID")
        private String modelTypeId;
    }

}
