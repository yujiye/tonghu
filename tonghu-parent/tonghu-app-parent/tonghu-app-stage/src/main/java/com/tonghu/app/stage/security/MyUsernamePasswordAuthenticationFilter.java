package com.tonghu.app.stage.security;

import com.tonghu.app.stage.security.service.UserRoleService;
import com.tonghu.app.stage.security.service.UsersService;
import com.tonghu.app.stage.security.service.impl.SysLogContext;
import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.common.constant.EnumIsSuccess;
import com.tonghu.pub.common.constant.EnumOperatorType;
import com.tonghu.pub.model.security.po.Users;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description: Spring Security的登陆验证
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUsernamePasswordAuthenticationFilter.class);
	
	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	@Resource
	private UsersService usersService;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private SysLogContext sysLogContext;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("NOT_SUPPORTED_GET");
		}
		
		//checkValidateCode(request);
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if (StringUtils.isBlank(username)) {
			sysLogContext.saveSysLog(request, EnumOperatorType.LOGIN.getType(), "登录系统",
					EnumIsSuccess.FAILURE.getStatus(),
					Constant.LONGIN_EXCEPTION.LONGIN_EXCEPTION_MAP.get("USERNAME_IS_NULL"));
			throw new AuthenticationServiceException("USERNAME_IS_NULL");
		}
		
		request.getSession().setAttribute("LOGIN_USER_NAME", username);
		
		if (StringUtils.isBlank(password)) {
			sysLogContext.saveSysLog(request, EnumOperatorType.LOGIN.getType(), "登录系统：" + username, 
					EnumIsSuccess.FAILURE.getStatus(), 
					Constant.LONGIN_EXCEPTION.LONGIN_EXCEPTION_MAP.get("PASSWORD_IS_NULL"));
			throw new AuthenticationServiceException("PASSWORD_IS_NULL");
		}
		
		//这里的作用就只是判断登录名是否存在于数据库中
		Users user = usersService.getUserInfoByUserName(username);

		if(user == null) {
			//记录访问日志
			sysLogContext.saveSysLog(request, EnumOperatorType.LOGIN.getType(), "登录系统：" + username, 
					EnumIsSuccess.FAILURE.getStatus(), 
					Constant.LONGIN_EXCEPTION.LONGIN_EXCEPTION_MAP.get("USERNAME_IS_NOT_EXIST"));
			throw new AuthenticationServiceException("USERNAME_IS_NOT_EXIST"); 
		}
		user.setUserRoleList(userRoleService.getRoleIdListByUserId(user.getId()));
		//将用户对象中的密码置为空，然后将用户信息放入到session中
		user.setPassword("");
		request.getSession().setAttribute("CURRENT_USER_INFO", user);
		
		//UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		// 允许子类设置详细属性
        setDetails(request, authRequest);
		
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
        Authentication authentication = null;
		try {
			authentication = this.getAuthenticationManager().authenticate(authRequest);
		} catch (AuthenticationServiceException e) {
			e.getMessage();
			sysLogContext.saveSysLog(request, EnumOperatorType.LOGIN.getType(), "登录系统：" + username, 
	        		 EnumIsSuccess.FAILURE.getStatus(), e.getMessage());
			throw e; 
		} catch (BadCredentialsException e) {
			e.getMessage();
			sysLogContext.saveSysLog(request, EnumOperatorType.LOGIN.getType(), "登录系统：" + username, 
	        		EnumIsSuccess.FAILURE.getStatus(), "密码错误");
			throw e; 
		}
		
		sysLogContext.saveSysLog(request, EnumOperatorType.LOGIN.getType(), "登录系统：" + username, 
        		EnumIsSuccess.SUCCESS.getStatus(), "");
		return authentication;
	}
	
	protected void checkValidateCode(HttpServletRequest request) { 
		HttpSession session = request.getSession();
		
	    String sessionValidateCode = obtainSessionValidateCode(session); 
	    //让上一次的验证码失效
	    session.setAttribute(VALIDATE_CODE, null);
	    String validateCodeParameter = obtainValidateCodeParameter(request);  
	    if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {  
	        throw new AuthenticationServiceException("validateCode.notEquals");  
	    }  
	}
	
	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString().trim();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString().trim();
	}

}
