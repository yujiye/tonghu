		</div>
	  </div>
	</div>
  </div>
</div>
<!-- 主内容模块结束 end -->

<!-- 退出系统提示框 start -->
<ul id="logoutConfirm" class="msg_Tip_box width300" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="showAlertInfo">
		<h4>提示：是否要退出系统？</h4><br>
		<input type="button" class="botton" id="yesLogout" value="  确  定  " />
		<input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 退出系统提示框 end -->

<!-- 认证失效提示框 start -->
<ul id="sessionTimeoutConfirm" class="msg_Tip_box width300" style="display:none">
	<a class="mask_close pr10" href="javascript:window.location.href='${ctx}/logout'"></a>
	<div class="showAlertInfo">
		<h4>提示：您的认证失效，请重新登录</h4><br>
		<input type="button" class="botton" id="yesLogout" value="  确  定  " />
	</div>
</ul>
<!-- 认证失效提示框 end -->

<!-- 操作结果提示框 start -->
<ul id="operResultWin" class="msg_Tip_box width350" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="showAlertInfo">
		<h4 id="oper_result_label"></h4><br>
		<input type="button" class="botton" onclick="javascript:$.unblockUI();return false;" value="  确  定  " />
	</div>
</ul>
<!-- 操作结果提示框 end -->

<!-- 用户信息修改选择窗口 start -->
<ul id="userInfoSetWin" class="msg_Tip_box width300" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table width="100%" class="tableWin">
	    <tr><td colspan='2' class="top">账 户 信 息 设 置</td></tr>
	    <tr>
	      <td class="center" width="50%"><input type="button" class="botton" id="changeUserBaseInfo" value="修改基本信息" /></td>
	      <td class="center" width="50%"><input type="button" class="botton" id="changeUserPass" value="修改登录密码" /></td>
	    </tr>
	  </table>
	  <input type="button" class="botton" id="cancel" value="  关  闭  " />
	</div>
</ul>
<!-- 用户信息修改选择窗口 end -->

<!-- 用户密码修改窗口 start -->
<ul id="changeUserPassWin" class="msg_Tip_box width500" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table class="tableWin" border="1">
	  	<tr><td colspan='2' class="top">修 改 账 户 密 码 信 息</td></tr>
	  	<tr>
	  	  <td class='leftTd' width='25%'><span class="red">*</span>原密码：</td>
	  	  <td width='75%'>
	  	    <input type="password" maxlength="15" class="width150" id="old_password" name="old_password" value="" />
	  	    <span id="old_password_error" class="red"></span>
	  	  </td>
	  	</tr>
		<tr>
		  <td class='leftTd'><span class="red">*</span>新密码：</td>
		  <td>
		    <input type="password" class="width150" maxlength="15" id="new_password" name="new_password" value="" />
		    <span id="new_password_error" class="red"></span>
		  </td>
		</tr>
		<tr>
		  <td class='leftTd'><span class="red">*</span>确认密码：</td>
		  <td>
		    <input type="password" class="width150" maxlength="15" id="confirm_new_password" name="confirm_new_password" value="" />
		    <span id="confirm_new_password_error" class="red"></span>
		  </td>
		</tr>
	  </table>
	  <input type="button" class="botton" id="saveChangeUserPassInfo" value="  确  定  " />
	  <input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 用户密码修改窗口 end -->

<!-- 用户基本信息修改窗口 start -->
<ul id="changeUserBaseInfoWin" class="msg_Tip_box width500" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table class="tableWin" border="1">
	  	<tr><td colspan='2' class="top">修 改 账 户 基 本 信 息</td></tr>
	  	<tr>
	  	  <td class='leftTd' width='25%'><span class="red">*</span>用户名：</td>
	  	  <td width='75%'>
	  	    <input type="text" maxlength="30" class="width150" id="change_user_name" name="change_user_name" value="" />
	  	    <span id="change_user_name_error" class="red"></span>
	  	  </td>
	  	</tr>
		<tr>
		  <td class='leftTd'><span class="red">*</span>真实姓名：</td>
		  <td>
		    <input type="text" class="width150" maxlength="15" id="change_true_name" name="change_true_name" value="" />
		    <span id="change_true_name_error" class="red"></span>
		  </td>
		</tr>
		<tr>
		  <td class='leftTd'>电子邮箱：</td>
		  <td>
		    <input type="text" class="width220" maxlength="30" id="change_user_email" name="change_user_email" value="" />
		    <span id="change_user_email_error" class="red"></span>
		  </td>
		</tr>
		<tr>
		  <td class='leftTd'>手机号码：</td>
		  <td>
		    <input type="text" class="width150" maxlength="11" id="change_user_mobile" name="change_user_mobile" value="" />
		    <span id="change_user_mobile_error" class="red"></span>
		  </td>
		</tr>
		<tr>
		  <td class='leftTd'>座机号码：</td>
		  <td>
		    <input type="text" class="width150" maxlength="15" id="change_user_phone" name="change_user_phone" value="" />
		    <span id="change_user_phone_error" class="red"></span>
		  </td>
		</tr>
		<tr>
		  <td class='leftTd'>备注：</td>
		  <td>
		    <input type="text" class="width220" maxlength="120" id="change_note" name="change_note" value="" />
		    <span id="change_note_error" class="red"></span>
		  </td>
		</tr>
	  </table>
	  <input type="button" class="botton" id="saveChangeUserBaseInfo" value="  确  定  " />
	  <input type="button" class="botton" id="cancel" value="  取  消  " />
	</div>
</ul>
<!-- 用户基本信息修改窗口 end -->

<!-- 用户基本信息展示窗口 start -->
<ul id="currentUserInfoWin" class="msg_Tip_box width600" style="display:none">
	<a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
	<div class="center tabDivInfo">
	  <table class="tableWin" border="1">
	  	<tr><td colspan='4' class="top">您 的 账 户 信 息</td></tr>
		<tr>
		  <td class='leftTd' width='18%'>用户名：</td><td width='32%'><label id="label_user_name"></label></td>
		  <td class='leftTd' width='18%'>真实姓名：</td><td width='32%'><label id="label_true_name"></label></td>
		</tr>
		<tr><td class='leftTd'>电子邮箱：</td><td colspan='3'><label id="label_user_email"></label></td></tr>
		<tr>
		  <td class='leftTd'>手机号码：</td><td><label id="label_user_mobile"></label></td>
		  <td class='leftTd'>座机号码：</td><td><label id="label_user_phone"></label></td>
		</tr>
		<tr><td class='leftTd'>创建时间：</td><td colspan='3'><label id="label_create_time"></label></td></tr>
		<tr><td class='leftTd'>备注：</td><td colspan='3'><label id="label_note"></label></td></tr>
	  </table>
	  <input type="button" class="botton" id="cancel" value="  关  闭  " />
	</div>
</ul>
<!-- 用户基本信息展示窗口 end -->

<!-- 等待处理的滚动条 start -->
<ul id="handleWating" class="msg_Tip_box width300" style="display:none">
	<div class="center tabDivInfo"><h3><img src="img/busy.gif" /> 处理中，请稍后...</h3></div>
</ul>
<!-- 等待处理的滚动条  end -->

<!-- 页面加载成功后的提示  start -->
<div class="growlUI" style="display:none">
  <h2>页面加载成功！</h2>
</div>
<!-- 页面加载成功后的提示  end -->
<script type="text/javascript" src="${ctx}/js/common/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.ui.spinner.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/common/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.elastic.source.js"></script>
<script type="text/javascript" src="${ctx}/js/common/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/js/common/bootstrap-colorpicker.js"></script>
<script type="text/javascript" src="${ctx}/js/common/bootstrap-typeahead.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.maskedinput-1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.uniform.js"></script>
<script type="text/javascript" src="${ctx}/js/common/chosen.jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.ibutton.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.smartWizard-2.0.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.filedrop.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jshashtable-2.1_src.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.numberformatter-1.2.3.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.dependClass-0.1.js"></script>
<script type="text/javascript" src="${ctx}/js/common/draggable-0.1.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.slider.js"></script>
<script type="text/javascript" src="${ctx}/js/common/fullcalendar.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.livequery.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.easytabs.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.cleditor.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.colorbox.js"></script>
<script type="text/javascript" src="${ctx}/js/common/blocksit.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.montage.js"></script>
<script type="text/javascript" src="${ctx}/js/common/raphael.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.embedly.js"></script>
<script type="text/javascript" src="${ctx}/js/common/script.js"></script>
<script type="text/javascript" src="${ctx}/js/common/block.js"></script>
<script type="text/javascript" src="${ctx}/js/common/util.js"></script>
<script type="text/javascript" src="${ctx}/js/common/maskui.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<#if pageName??><script type="text/javascript" src="${ctx}/js/service/${pageName}.js?1233"></script></#if>
<#if showImg??><script type="text/javascript" src="${ctx}/js/service/${showImg}.js"></script></#if>