package com.tonghu.app.consumer.service;

import cn.xlink.ddq.sdk.DDQClient;
import cn.xlink.ddq.sdk.DDQClientBuilder;
import cn.xlink.ddq.sdk.subscribe.*;
import cn.xlink.ddq.sdk.subscribe.handler.*;
import com.google.gson.Gson;
import com.tonghu.app.consumer.config.TongHuConsumerConfig;
import com.tonghu.app.consumer.websocket.TongHuWebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author liangyongjian
 * @desc 消费者
 * @create 2018-08-12 21:17
 **/
@Component
public class ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    private static Gson GSON = new Gson();

    @Autowired
    private TongHuConsumerConfig tongHuConsumerConfig;

    @PostConstruct
    public void consumerStart() {
        LOGGER.info("ConsumerService start");
        this.consumerMain();
    }

    public void consumerMain() {

        //授权信息与访问地址
        String appId = tongHuConsumerConfig.getAppId();
        String appSecret = tongHuConsumerConfig.getAppSecret();
        String endpoint = tongHuConsumerConfig.getEndPoint();
        String mqHost = tongHuConsumerConfig.getMqHost();

        LOGGER.info("授权信息 appId=[{}], appSecret=[{}], endpoint=[{}], mqHost=[{}]",
                appId, appSecret, endpoint, mqHost);

        //初始化DDQClient构造器
        DDQClientBuilder builder = new DDQClientBuilder();
        builder.setAppId(appId).setAppSecret(appSecret).setEndPoint(endpoint).setMqHost(mqHost);
        DDQClient ddqClient = builder.buildDDQClient();

        //订阅设备上下线状态
//        int threadNums = 4; //设定设备上线状态处理线程数目
//        ddqClient.subscribeDeviceState(new DeviceStateSubscribeHandler(), threadNums);
        // 也可通过此方法，不指定线程数目，默认为1条线程数。
        ddqClient.subscribeDeviceState(new DeviceStateSubscribeHandler());

        //订阅设备上报数据
//        ddqClient.subscribeDeviceDatapoint(new DeviceDataPointSubscribeHandler(), threadNums);
        ddqClient.subscribeDeviceDatapoint(new DeviceDataPointSubscribeHandler());

        //订阅设备激活信息
        ddqClient.subscribeDeviceActivate(new DeviceActivateSubscribeHandler());

        //订阅设备告警信息
        ddqClient.subscribeDeviceAlert(new DeviceAlertSubscribeHandler());

        //订阅停车子系统业务数据
        ddqClient.subscribeServiceData("parking", new ParkingServiceDataSubscribeHandler());

        //订阅人脸识别子系统业务数据
        ddqClient.subscribeServiceData("face_recognition", new FaceRecognitionServiceDataSubscribeHandler());

        //启动DDQ客户端
        ddqClient.start();

    }

}

/**
 * 设备激活订阅处理器
 * @author shenweiran
 * @date 2018年6月21日 下午12:40:55
 */
class DeviceActivateSubscribeHandler implements IDeviceActivateSubscribeHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceActivateSubscribeHandler.class);

    private static Gson GSON = new Gson();

    @Override
    public void onDeviceActivate(DeviceActivate deviceActivate) {
        String message = GSON.toJson(deviceActivate);
        LOGGER.info("收到设备激活消息 message={}",message);
        TongHuWebSocketServer.sendClientMessage("deviceActivate", message);
    }
}

/**
 * 设备上下线状态订阅处理器
 */
class DeviceStateSubscribeHandler implements IDeviceStateSubscribeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStateSubscribeHandler.class);

    private static Gson GSON = new Gson();

    @Override
    public void onDeviceState(DeviceState state){
        //接收数据，执行相关操作...
        String message = GSON.toJson(state);
        LOGGER.info("收到上下线消息 message={}",message);
        TongHuWebSocketServer.sendClientMessage("deviceState", message);
    }
}

/**
 * 设备上报数据订阅处理器
 */
class DeviceDataPointSubscribeHandler implements IDeviceDatapointSubscribeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDataPointSubscribeHandler.class);

    private static Gson GSON = new Gson();

    @Override
    public void onDeviceDatapoint(Datapoint datapoint){
        //接收数据，执行相关操作...
        String message = GSON.toJson(datapoint);
        LOGGER.info("收到数据上下线状态消息 message={}", message);
        TongHuWebSocketServer.sendClientMessage("deviceDataPoint", message);
    }
}

class DeviceAlertSubscribeHandler implements IDeviceAlertSubscribeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceAlertSubscribeHandler.class);

    private static Gson GSON = new Gson();

    @Override
    public void onDeviceAlert(DeviceAlert deviceAlert) {
        //接收数据，执行相关操作...
        String message = GSON.toJson(deviceAlert);
        LOGGER.info("收到设备告警消息 message={}",message);
        TongHuWebSocketServer.sendClientMessage("deviceAlert", message);
    }

}

/**
 * 停车子系统业务数据处理器
 */
class ParkingServiceDataSubscribeHandler implements IServiceDataSubscribeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingServiceDataSubscribeHandler.class);
    private static Gson GSON = new Gson();

    @Override
    public void onServiceData(ServiceData serviceData){
        //接收数据，执行相关操作...
        String message = GSON.toJson(serviceData);
        LOGGER.info("收到停车子系统业务数据消息 message={}", message);
        TongHuWebSocketServer.sendClientMessage("parking", message);
    }
}

/**
 * 人脸识别子系统业务数据处理器
 */
class FaceRecognitionServiceDataSubscribeHandler implements IServiceDataSubscribeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceRecognitionServiceDataSubscribeHandler.class);
    private static Gson GSON = new Gson();

    @Override
    public void onServiceData(ServiceData serviceData){
        //接收数据，执行相关操作...
        String message = GSON.toJson(serviceData);
        LOGGER.info("收到人脸识别子系统业务数据消息 message={}", message);
        TongHuWebSocketServer.sendClientMessage("faceRecognition", message);
    }
}
