package com.tonghu.app.stage.security;

import com.tonghu.app.stage.security.service.UserRoleService;
import com.tonghu.app.stage.security.service.UsersService;
import com.tonghu.pub.model.security.po.UserRole;
import com.tonghu.pub.model.security.po.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailServiceImpl.class);
	
	@Resource
	private UsersService usersService;
	
	@Resource
	private UserRoleService userRoleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LOGGER.debug("MyUserDetailServiceImpl:loadUserByUsername 正在加载用户名和密码，用户名为：{}", username);
		
		Users user = usersService.getUserInfoByUserName(username);
		if(user == null){
			LOGGER.error("用户名没有找到! {}", username);
			throw new UsernameNotFoundException("用户名不存在!");
		}
		
		boolean enabled = true;
        boolean accountNonExpired = true;        //是否过期
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = user.getIsLock().compareTo(new Integer(0)) == 0 ? true : false;  //是否可用 
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		//一个用户可能有多个角色
		List<UserRole> userRoleList = userRoleService.getRoleIdListByUserId(user.getId());
		for(UserRole userRole : userRoleList){
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.getRoleId().toString());
			authorities.add(grantedAuthority);
		}
		User userDetail = new User(user.getUserName(), user.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return userDetail;
	}

}
