package com.tonghu.pub.proxy.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class OuterUrl {

	@Value("#{outerUrlProp['iot-token-expire-error-msg']}")
	private String iotTokenExpireErrorMsg;

	/**
	 * 获取产品列表信息接口
	 */
	@Value("#{outerUrlProp['get-accesskey-auth-url']}")
	private String getAccesskeyAuthUrl;

	/**
	 * 获取产品列表信息接口
	 */
	@Value("#{outerUrlProp['get-product-list-url']}")
	private String getProductListUrl;

	/**
	 * 获取给定产品的详细信息接口
	 */
	@Value("#{outerUrlProp['get-product-info-url']}")
	private String getProductInfoUrl;

	/**
	 * 获取给定产品下设备列表信息接口
	 */
	@Value("#{outerUrlProp['get-device-list-url']}")
	private String getDeviceListUrl;

	/**
	 * 获取给定产品下给定设备详细信息接口
	 */
	@Value("#{outerUrlProp['get-device-info-url']}")
	private String getDeviceInfoUrl;

	/**
	 * 获取给定产品下给定虚拟设备详细信息接口
	 */
	@Value("#{outerUrlProp['get-virtual-device-info-url']}")
	private String getVirtualDeviceInfoUrl;

}
