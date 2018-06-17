<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Expires" CONTENT="-1">
<meta http-equiv="Pragma" CONTENT="no-cache">
<link rel="shortcut  icon" type="image/x-icon" href="${ctx}/img/favicon.ico" media="screen" />
<title>欢迎使用潼湖科技小镇可视化平台管理系统——请登录</title>
<link href="${ctx}/css/login.css" type="text/css" rel="stylesheet">
</head>

<body class="sso">
  <div class="login_wrap">
    <div class="rbac_cont">
      <div class="c_top">
        <div class="c_top_middle">潼湖科技小镇可视化平台管理系统</div>
        <span class="c_top_radius"></span></div>
        <span class="c_top_radius"></span>
      </div>
      <form method="post" action="${ctx}/login_check" class="fm-v clearfix" id="fm1">
        <div class="c_middle">
          <p>
             <label for="username">登录账号</label>
			 <input type="text" placeholder="请输入您的账号" name="username" id="username" value="<#if username??>${username}</#if>">
			 <span id="error" class="error">
				<span id="status" class="errors">${message}</span>
			 </span>
		  </p>
		  <p class="p_special">
			 <label for="password">密&nbsp;&nbsp;码</label>
			 <input type="password" placeholder="请输入你的密码" name="password" id="password">   
	      </p>
        </div>
          <div class="c_bottom">
              <p><input type="submit" value="&nbsp;&nbsp;"></p>
              <span class="c_bottom_radius"></span>
            </div>
            <span class="rbac_cont_shadow"></span>
        </form></div>
        
    </div>

</body>
</html>