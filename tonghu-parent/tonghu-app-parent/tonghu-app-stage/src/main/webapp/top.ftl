<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0014)http://m4er.cn -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Expires" CONTENT="-1">
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="潼湖科技小镇 | 潼湖科技小镇可视化平台管理系统 | ${pageHanName}" />
<meta name="author" content="liangyongjian" />
<link rel="shortcut  icon" type="image/x-icon" href="${ctx}/img/favicon.ico" media="screen" />
<title>潼湖科技小镇可视化平台管理系统--${pageHanName}</title>
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" /> 
<link href="${ctx}/css/style.css" rel="stylesheet" />
<link href="${ctx}/css/plugins.css" rel="stylesheet" />
<#if mulitselect??><link href="${ctx}/css/${mulitselect}.css" rel="stylesheet" /></#if>
<#if showImg??><link href="${ctx}/css/${showImg}.css" rel="stylesheet" /></#if>
<link href="${ctx}/css/datepicker.css" rel="stylesheet" />
<script src="${ctx}/js/common/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common/jquery.isotope.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common/jquery.infinitescroll.min.js" type="text/javascript"></script>
<script type="text/javascript">
  var ctx = '${ctx}';
</script>
</head>
<body>
<DIV id=bg></DIV>
<!-- 头部模块 start -->
<div class="header_wrapper">
  <div class="top-container-fluid">
    <div class="row-fluid">

      <ul class="user_nav">
        <li><a id="userInfo" title="用户信息" rel="tooltip" class="icon_user"></a></li>
        <li><a id="userInfoSet" title="设置" rel="tooltip" class="icon_settings"></a></li>
        <li><a id="logout" title="退出" rel="tooltip" class="icon_logout"></a></li>
      </ul>
    </div>
  </div>
</div>
<!-- 头部模块结束 end -->

<!-- 主内容模块 start -->
<div class="contentainer_wrapper">
  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">
        <div class="wrapper">
        
        <!-- 主内容模块 左侧菜单模块结束 start -->
		  <div class="sidebar_navigation gradient">
		    <ul id="menuUl" showMenu="-1">
		    <#list menuList as menu>
		      <#assign note = menu.note>
		      
		      <#assign active = "">
              <#assign tab_label = "tab_label">
		      <#if menu.url == currentPageUrl>
		      	<#assign active = " active">
                  <#assign tab_label = "tab_labela">
		      </#if>
		      <#list menu.resourceList as resource>
     			<#if resource_index == 0>
     			  <#if resource.resourceUrl == menu.url>
     			    <#assign active = "">
     			  </#if>
     			</#if>
    		  </#list>
		      
		      <li class="tip-right${active}" title="${menu.moduleName}">
                <#if menu.resourceList?? && menu.resourceList?size gt 0>
                    <a href="javascript:showSubMenu(${menu.id});" class="i_${note} tip-right">
                <#else>
                    <a href="${ctx}${menu.url}" class="i_${note} tip-right">
                </#if>
                  <span class="${tab_label}">${menu.moduleName}</span>
                </a>
              </li>
              <#assign hide = " hide">
              <#list menu.resourceList as resource>
                <#if menu.url == currentPageUrl><#assign hide = " "></#if>
              	<#if resource.resourceUrl == currentPageUrl><#assign hide = " "></#if>
              </#list>
			  <#list menu.resourceList as resource>
			    <#assign rUrl = resource.resourceUrl>
			    <li class="tip-right liul <#if resource.resourceUrl == currentPageUrl>active</#if>${hide}" title="${resource.resourceName}" menuId="${menu.id}">
                  <a href="${ctx}${resource.resourceUrl}" class="">
                    <span class="tab_labelc<#if resource.resourceUrl == currentPageUrl>a</#if>">${resource.resourceName}</span>
                  </a>
                </li>
			  </#list>
		    </#list>
			  
		    </ul>
		  </div>
		  <!-- 主内容模块 左侧菜单模块结束 end -->