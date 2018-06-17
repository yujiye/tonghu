package com.tonghu.pub.common.web;

import com.tonghu.pub.common.constant.Constant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 注册Bean使用
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		// 修改StringHttpMessageConverter的supportedMediaTypes，解决response乱码
		if (bean instanceof StringHttpMessageConverter) {
			MediaType mediaType = new MediaType("application", "json",
					Charset.forName(Constant.DEFAULT_ENCODE.STRING_UTF8));
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(mediaType);
			((StringHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		
		if (bean instanceof MappingJackson2HttpMessageConverter) {
			MediaType mediaType = new MediaType("application", "json",
					Charset.forName(Constant.DEFAULT_ENCODE.STRING_UTF8));
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(mediaType);
			((MappingJackson2HttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		
		if (bean instanceof MarshallingHttpMessageConverter) {
			MediaType mediaType = new MediaType("application", "json",
					Charset.forName(Constant.DEFAULT_ENCODE.STRING_UTF8));
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(mediaType);
			((MarshallingHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		
		if (bean instanceof Jaxb2RootElementHttpMessageConverter) {
			MediaType mediaType = new MediaType("application", "json",
					Charset.forName(Constant.DEFAULT_ENCODE.STRING_UTF8));
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(mediaType);
			((Jaxb2RootElementHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}