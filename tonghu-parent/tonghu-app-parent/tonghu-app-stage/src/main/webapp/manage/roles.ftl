<#include "/top.ftl">

<!-- 主内容模块 右侧内容列表模块开始 start -->
<div class="content_wrapper">
  <div class="contents">
    <div class="row-fluid">
      <div class="span6">
        <div class="ico_16_${pageName} content_header left"><h3>${pageHanName}管理</h3></div>
        <div class="left button"><button class="btn" id="addNewRole" type="button"><i class="icon-plus"></i>&nbsp;添加新角色&nbsp;</button></div>
      </div>
    </div>
    <div class="separator"><span></span></div>
              
    <div id="msg" class="msg"></div>
			  
    <!-- 搜索模块开始 start -->
    <div class="row-fluid"> 
      <div class="span12">
        <div class="widget_wrapper">
          <div class="widget_header">
            <h3 class="icos_${pageName}">${pageHanName}搜索</h3>
          </div>
          <div class="widget_content no-padding">
			<div class="form_inputs clearfix">
			  <div class="row-fluid">
			    <form method="post" aciton="${ctx}${currentPageUrl}" id="searchForm">
			      <input type="hidden" value="${totalInfo.currentPage}" id="pageNumInput" name="pageNumInput" />
				       角色名称：<input id="roleNameSearch" name="roleNameSearch" type="text" value="<#if roleName??>${roleName}</#if>" />
                  <input class="btn btn-fsjbut" type="button" id="searchMatch" value="搜索" />
                  <input class="btn btn-fsjbut" type="button" id="searchAll" value="查看全部" />
                                                 第 <span class="bold">${totalInfo.currentPage}</span> 页  共 <span class="bold">${totalInfo.pageTotal}</span> 页  每页 <span class="bold">${totalInfo.pageSize}</span> 条记录 共 <span class="bold">${totalInfo.totalCount}</span> 条记录
			    </form>
			  </div>  
			</div>
		  </div>
		</div>
      </div>
    </div>
    <!-- 搜索模块结束 end -->
			  
    <!-- 活动列表模块开始 start -->
    <div class="row-fluid">
	  <div class="span12">
	    <div class="widget_wrapper">
	      <div class="widget_header">
	        <div class="widget_header_option"><h3>${pageHanName}列表</h3></div>                                                 
          </div>
					
		  <div class="widget_content no-padding">
		  <!-- 活动列表表格模块开始 start -->
            <table cellspacing="0" cellpadding="0" class="default_table selectable_table" width="100%">
              <thead>
                <tr>
			      <td width="5%">序号</td>
			      <td width="30%">角色名称</td>
			      <td width="18%">创建时间</td>
			      <td width="18%">最后修改时间</td>
			      <td width="29%">动作</td>
                </tr>
              </thead>
              <tbody>
              <#if rolesList?? && (rolesList?size > 0)>
                <#assign num = totalInfo.startNum>
                <#list rolesList as roleInfo>
                <#assign num = num + 1>
                <tr>  
			      <td class="botValue">${num}</td>
			      <td class="botValue"><label class="columnLable" id="show_role_${roleInfo.id}">${roleInfo.roleName}</label></td>
			      <td class="botValue">${roleInfo.createTime}</td>
			      <td class="botValue">${roleInfo.updateTime}</td>
			      <td class="botValue">
			        <a class="btn" id="showRole_${roleInfo.id}"><i class="icon-eye-open"></i>查看</a>&nbsp;
			        <#if roleInfo.editable == 1>
			          <a class="btn" id="updateRole_${roleInfo.id}"><i class="icon-edit"></i>修改</a>&nbsp;
			          <a class="btn" id="removeRole_${roleInfo.id}"><i class="icon-trash"></i>删除</a>
			          <a class="btn" id="assignAuth_${roleInfo.id}"><i class="icon-trash"></i>分配权限</a>
			        </#if>
			      </td>
			    </tr>
			    </#list>
              <#else>
                <tr>  
			      <td class="botValue" colspan="5" style="padding:10px 0;">暂无角色信息！</td>
			    </tr>
              </#if>
		      </tbody>
		    </table> 
		    <!-- 活动列表表格模块结束 end -->
					  
		    <!-- 活动列表分页模块开始 start -->
		    <div id="paginate" class="dataTables_paginate paging_full_numbers">
		     <#if (totalInfo.totalCount > 0)>
		      <#if (totalInfo.currentPage > 1)>
		        <input class="paginate_button" type="button" id="pageTurn${totalInfo.currentPage-1}" value="上一页" />
		        <input class="paginate_button" type="button" id="pageTurn1" value="1" />
		      </#if>
		      
		      <#if (totalInfo.currentPage - 2 > 1)>
		        <#assign minPage = totalInfo.currentPage - 2>
		      <#else>
		        <#if (totalInfo.currentPage == 1)>
		          <#assign minPage = 1>
		        <#else>
		          <#assign minPage = 2>
		        </#if>
		      </#if>
		      
		      <#if (minPage - 1 > 1)>
		        ...
		      </#if>
		      
		      <#if (totalInfo.pageTotal - 2 > totalInfo.currentPage)>
		        <#assign maxPage = totalInfo.currentPage + 2>
		      <#else>
		        <#if (totalInfo.currentPage == totalInfo.pageTotal) || (totalInfo.pageTotal - totalInfo.currentPage == 1)>
		          <#assign maxPage = totalInfo.currentPage>
		        <#else>
		          <#assign maxPage = totalInfo.currentPage + 1>
		        </#if>
		      </#if>
		      
		      <#list minPage..maxPage as t>
		        <#if (totalInfo.currentPage == t)>
			      <input class="btnPage btn-fsjbut" type="button" id="pageTurn${t}" value="${t}" />
			    <#else> 
			      <input class="paginate_button" type="button" id="pageTurn${t}" value="${t}" />
			    </#if>
			  </#list>
		      
		      
		      <#if (totalInfo.pageTotal - maxPage > 1)>
		        ...
		      </#if>
		      
		      <#if (totalInfo.currentPage < totalInfo.pageTotal)>
		        <input class="paginate_button" type="button" id="pageTurn${totalInfo.pageTotal}" value="${totalInfo.pageTotal}" />
		        <input class="paginate_button" type="button" id="pageTurn${totalInfo.currentPage+1}" value="下一页" />
		      </#if>
		      <input type="hidden" id="pageTotal" value="${totalInfo.pageTotal}">
		     </#if>
		    </div>
		    <!-- 活动列表分页模块结束 end -->
                     
		  </div>
        </div>
      </div>
    </div>
  <!-- 活动列表模块结束 end -->
  </div>
</div>
<!-- 主内容模块 右侧内容列表模块结束 end -->
<#include "/foot.ftl">
<!-- 添加角色窗口 start -->
<ul id="addNewRoleWin" class="msg_Tip_box width500" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table width="100%" class="tableWin">
	    <tr><td colspan='2' class="top">添 加 新 角 色</td></tr>
	    <tr>
	  	  <td class='leftTd' width='25%'><span class="red">*</span>角色名称：</td>
	  	  <td width='75%'>
	  	    <input type="text" maxlength="15" class="width130" id="role_name" name="role_name" value="" />
	  	    <span id="role_name_error" class="red"></span>
	  	  </td>
	  	</tr>
		<tr>
		  <td class='leftTd'>备注：</td>
		  <td>
		    <input type="text" class="width220" maxlength="120" id="role_note" name="role_note" value="" />
		    <span id="role_note_error" class="red"></span>
		  </td>
		</tr>
	  </table>
	  <input type="button" class="botton" id="saveNewRole" value="  确  定  " />
	  <input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 添加角色窗口 end -->

<!-- 角色信息展示窗口 start -->
<ul id="roleInfoWin" class="msg_Tip_box width600" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table class="tableWin" border="1">
	  	<tr><td colspan='4' class="top">角 色 信 息</td></tr>
		<tr>
		  <td class='leftTd'>角色名称：</td><td colspan='3'><label id="label_role_name"></label></td>
		</tr>
		<tr>
		  <td class='leftTd' width='18%'>创建时间：</td><td width='32%'><label id="label_role_create_time"></label></td>
		  <td class='leftTd' width='18%'>创建用户：</td><td width='32%'><label id="label_role_create_userName"></label></td>
		</tr>
		<tr>
		  <td class='leftTd'>修改时间：</td><td><label id="label_role_update_time"></label></td>
		  <td class='leftTd'>修改用户：</td><td><label id="label_role_update_userName"></label></td>
		</tr>
		<tr><td class='leftTd'>备注：</td><td colspan='3'><label id="label_role_note"></label></td></tr>
	  </table>
	  <input type="button" class="botton" id="cancel" value="  关  闭  " />
	</div>
</ul>
<!-- 角色信息展示窗口 end -->

<!-- 角色信息修改窗口 start -->
<ul id="changeRoleWin" class="msg_Tip_box width500" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table class="tableWin" border="1">
	  	<tr><td colspan='2' class="top">修 改 角 色 信 息<input type="hidden" value="" id="change_role_id" /></td></tr>
	  	<tr>
	  	  <td class='leftTd' width='25%'><span class="red">*</span>角色名称：</td>
	  	  <td width='75%'>
	  	    <input type="text" maxlength="15" class="width130" id="change_role_name" name="change_role_name" value="" />
	  	    <span id="change_role_name_error" class="red"></span>
	  	  </td>
	  	</tr>
		<tr>
		  <td class='leftTd'>备注：</td>
		  <td>
		    <input type="text" class="width220" maxlength="120" id="change_role_note" name="change_role_note" value="" />
		    <span id="change_role_note_error" class="red"></span>
		  </td>
		</tr>
	  </table>
	  <input type="button" class="botton" id="saveChangeRole" value="  确  定  " />
	  <input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 角色信息修改窗口 end -->

<!-- 角色权限修改窗口 start -->
<ul id="changeRoleAuthWin" class="msg_Tip_box width600" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table class="tableWin" border="1" id="changeRoleAuthTable">
	  	<tr><td colspan="5" class="top">修 改 角 色 权 限 信 息<input type="hidden" value="" id="change_auth_role_id" /></td></tr>
	  </table>
	  <input type="button" class="botton" id="saveChangeRoleAuth" value="  确  定  " />
	  <input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 角色权限修改窗口 end -->

<!-- 删除角色提示框 start -->
<ul id="removeRoleConfirm" class="msg_Tip_box width300" style="display:none" id="show">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="showAlertInfo">
		<h4 id="remove_alert_info"></h4><br>
		<input type="hidden" id="remove_role_id" value="" />
		<input type="button" class="botton" id="yesRemoveRole" value="  确  定  " />
		<input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 删除角色提示框 end -->
</body>
</html>