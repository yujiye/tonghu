package com.tonghu.pub.common.web;

import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.common.constant.MappingErrorCode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 不是单例
 */
public class PubModelMap {
	
	private Map<String, Object> modelMap; //Spring MVC的返回值
	private HttpServletRequest request; //当前请求对象

	public PubModelMap(HttpServletRequest request) {
		super();
		this.request = request;
		this.modelMap = new HashMap<String, Object>();
	}
	
	public void put(String key, Object value) {
		this.modelMap.put(key, value);
		if (key.equals("status")) {
			//在这里往request对象中写入处理code和处理结果，是供日志过滤器使用
			if (String.valueOf(value).equals("success")) {
				//处理成功!
				request.setAttribute(Constant.RESULT_CODE, Constant.COMMON_SUCCESS.SUCCESS_CODE);
				request.setAttribute(Constant.RESULT_MSG, Constant.COMMON_SUCCESS.SUCCESS_MSG);
				
			} else if (String.valueOf(value).equals("error")) {
				//数据校验失败!
				request.setAttribute(Constant.RESULT_CODE, Constant.COMMON_ERROR.ERROR_CODE);
				request.setAttribute(Constant.RESULT_MSG, Constant.COMMON_ERROR.ERROR_MSG);
				
			} else if (String.valueOf(value).equals("failure")) {
				//处理失败!
				request.setAttribute(Constant.RESULT_CODE, Constant.COMMON_FAIL.FAIL_CODE);
				request.setAttribute(Constant.RESULT_MSG, Constant.COMMON_FAIL.FAIL_MSG);
				modelMap.put("data", MappingErrorCode.getValue(Constant.COMMON_FAIL.FAIL_CODE));
				
			} else if (String.valueOf(value).equals("exception")) {
				//系统内部异常!
				request.setAttribute(Constant.RESULT_CODE, Constant.COMMON_EXCEPTION.EXCEPTION_CODE);
				request.setAttribute(Constant.RESULT_MSG, Constant.COMMON_EXCEPTION.EXCEPTION_MSG);
				modelMap.put("data", MappingErrorCode.getValue(Constant.COMMON_EXCEPTION.EXCEPTION_CODE));
				
			}
		}
	}
	
	public Object get(String key) {
		return this.modelMap.get(key);
	}
	
	public Map<String, Object> getModelMap() {
		return this.modelMap;
	}

	public void setModelMap(Map<String, Object> modelMap) {
		this.modelMap = modelMap;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
