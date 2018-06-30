<#include "/top.ftl">

<!-- 主内容模块 右侧内容列表模块开始 start -->
<div class="content_wrapper">
    <div class="contents">
        <div class="row-fluid">
            <div class="span6">
                <div class="ico_16_${pageName} content_header left"><h3>${pageHanName}管理</h3></div>
                <div class="left button"><button class="btn" id="addNewUser" type="button"><i class="icon-plus"></i>&nbsp;添加新用户&nbsp;</button></div>
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
                                    用户名：<input id="userNameSearch" name="userNameSearch" type="text" value="<#if userName??>${userName}</#if>" />
                                    &nbsp;
                                    用户邮箱：<input id="userEmailSearch" name="userEmailSearch" type="text" value="<#if userEmail??>${userEmail}</#if>" />
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
                                <td width="20%">用户名</td>
                                <td width="12%">电子邮箱</td>
                                <td width="12%">真实姓名</td>
                                <td width="12%">用户状态</td>
                                <td width="12%">用户角色</td>
                                <td width="27%">动作</td>
                            </tr>
                            </thead>
                            <tbody>
							<#if usersList?? && (usersList?size > 0)>
								<#assign num = totalInfo.startNum>
								<#list usersList as userInfo>
									<#assign num = num + 1>
                                <tr>
                                    <td class="botValue">${num}</td>
                                    <td class="botValue"><label class="columnLable" id="show_user_${userInfo.id}">${userInfo.userName}</label></td>
                                    <td class="botValue">${userInfo.userEmail}</td>
                                    <td class="botValue">${userInfo.trueName}</td>
                                    <td class="botValue">${userInfo.lockStr}</td>
                                    <td class="botValue">${userInfo.roleName}</td>
                                    <td class="botValue">
                                        <a class="btn" id="showUser_${userInfo.id}"><i class="icon-eye-open"></i>查看</a>&nbsp;
										<#if userInfo.editable == 1>
                                            <a class="btn" id="updateUser_${userInfo.id}" userName="${userInfo.userName}"><i class="icon-edit"></i>编辑</a>&nbsp;
                                            <a class="btn" id="removeUser_${userInfo.id}"><i class="icon-trash"></i>删除</a>
										</#if>
                                    </td>
                                </tr>
								</#list>
							<#else>
                            <tr>
                                <td class="botValue" colspan="8" style="padding:10px 0;">暂无用户信息！</td>
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
<script type="text/javascript" src="${ctx}/js/map.js"></script>

<!-- 添加用户窗口 start -->
<ul id="addNewUserWin" class="msg_Tip_box width500" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='2' class="top">添 加 新 用 户</td></tr>
            <tr>
                <td class='leftTd' width='25%'><span class="red">*</span>用户名：</td>
                <td width='75%'>
                    <input type="text" maxlength="30" class="width150" id="user_name" name="user_name" value="" />
                    <span id="user_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>登录密码：</td>
                <td>
                    <input type="password" class="width150" maxlength="15" id="user_pass" name="user_pass" value="" />
                    <span id="user_pass_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>确认密码：</td>
                <td>
                    <input type="password" class="width150" maxlength="15" id="confirm_user_pass" name="confirm_user_pass" value="" />
                    <span id="confirm_user_pass_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>真实姓名：</td>
                <td>
                    <input type="text" class="width150" maxlength="15" id="true_name" name="true_name" value="" />
                    <span id="true_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>电子邮箱：</td>
                <td>
                    <input type="text" class="width220" maxlength="30" id="user_email" name="user_email" value="" />
                    <span id="user_email_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>用户状态：</td>
                <td>
                    <select class="u_select" id="user_lock" name="user_lock">
                        <option value="0" selected="selected">正常</option>
                        <option value="1">锁定</option>
                    </select>
                    <span id="user_lock_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>角色：</td>
                <td>
                    <select class="u_select" id="user_role" name="user_role">
                        <option value="-1" selected="selected">请选择角色</option>
                    <#if rolesList?? && (rolesList?size > 0)>
                        <#list rolesList as roleInfo>
                            <option value="${roleInfo.id}">${roleInfo.roleName}</option>
                        </#list>
                    </#if>
                    </select>
                    <span id="user_role_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>手机号码：</td>
                <td>
                    <input type="text" class="width150" maxlength="11" id="user_mobile" name="user_mobile" value="" />
                    <span id="user_mobile_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>座机号码：</td>
                <td>
                    <input type="text" class="width150" maxlength="15" id="user_phone" name="user_phone" value="" />
                    <span id="user_phone_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>备注：</td>
                <td>
                    <input type="text" class="width220" maxlength="120" id="user_note" name="user_note" value="" />
                    <span id="user_note_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveNewUser" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 添加用户窗口 end -->

<!-- 用户信息展示窗口 start -->
<ul id="userInfoWin" class="msg_Tip_box width700" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table class="tableWin" border="1">
            <tr><td colspan='4' class="top">用 户 信 息</td></tr>
            <tr>
                <td class='leftTd' width='18%'>用户名：</td><td width='32%'><label id="users_label_user_name"></label></td>
                <td class='leftTd' width='18%'>真实姓名：</td><td width='32%'><label id="users_label_true_name"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>电子邮箱：</td><td><label id="users_label_user_email"></label></td>
                <td class='leftTd'>用户状态：</td><td><label id="users_label_lock"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>角色：</td><td colspan="3"><label id="users_label_role"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>手机号码：</td><td><label id="users_label_user_mobile"></label></td>
                <td class='leftTd'>座机号码：</td><td><label id="users_label_user_phone"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>创建时间：</td><td><label id="users_label_create_time"></label></td>
                <td class='leftTd'>创建用户：</td><td><label id="users_label_create_userName"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>修改时间：</td><td><label id="users_label_update_time"></label></td>
                <td class='leftTd'>修改用户：</td><td><label id="users_label_update_userName"></label></td>
            </tr>
            <tr><td class='leftTd'>备注：</td><td colspan='3'><label id="users_label_note"></label></td></tr>
        </table>
        <input type="button" class="botton" id="cancel" value="  关  闭  " />
    </div>
</ul>
<!-- 用户信息展示窗口 end -->

<!-- 用户信息修改窗口 start -->
<ul id="changeUserWin" class="msg_Tip_box width500" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table class="tableWin" border="1">
            <tr><td colspan='2' class="top">修 改 用 户 信 息<input type="hidden" value="" id="users_change_user_id" /></td></tr>
            <tr>
                <td class='leftTd' width='25%'><span class="red">*</span>用户名：</td>
                <td width='75%'>
                    <input type="text" maxlength="30" class="width150" id="users_change_user_name" name="users_change_user_name" value="" />
                    <span id="users_change_user_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>真实姓名：</td>
                <td>
                    <input type="text" class="width150" maxlength="15" id="users_change_true_name" name="users_change_true_name" value="" />
                    <span id="users_change_true_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>电子邮箱：</td>
                <td>
                    <input type="text" class="width220" maxlength="30" id="users_change_user_email" name="users_change_user_email" value="" />
                    <span id="users_change_user_email_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>用户状态：</td>
                <td>
                    <select class="u_select" id="users_change_user_lock" name="user_lock">
                        <option value="0" selected="selected">正常</option>
                        <option value="1">锁定</option>
                    </select>
                    <span id="user_lock_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>角色：</td>
                <td>
                    <select class="u_select" id="users_change_user_role" name="users_change_user_role">
                        <option value="-1" selected="selected">请选择角色</option>
                    <#if rolesList?? && (rolesList?size > 0)>
                        <#list rolesList as roleInfo>
                            <option value="${roleInfo.id}">${roleInfo.roleName}</option>
                        </#list>
                    </#if>
                    </select>
                    <span id="users_change_user_role_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>手机号码：</td>
                <td>
                    <input type="text" class="width150" maxlength="11" id="users_change_user_mobile" name="users_change_user_mobile" value="" />
                    <span id="users_change_user_mobile_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>座机号码：</td>
                <td>
                    <input type="text" class="width150" maxlength="15" id="users_change_user_phone" name="users_change_user_phone" value="" />
                    <span id="users_change_user_phone_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>备注：</td>
                <td>
                    <input type="text" class="width220" maxlength="120" id="users_change_user_note" name="users_change_user_note" value="" />
                    <span id="users_change_user_note_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveChangeUser" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 用户信息修改窗口 end -->

<!-- 删除用户提示框 start -->
<ul id="removeUserConfirm" class="msg_Tip_box width300" style="display:none" id="show">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="showAlertInfo">
        <h4 id="remove_alert_info"></h4><br>
        <input type="hidden" id="remove_user_id" value="" />
        <input type="button" class="botton" id="yesRemoveUser" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 删除用户提示框 end -->

<!-- 用户信息修改选择窗口 start -->
<ul id="userInfoChangeWin" class="msg_Tip_box width420" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr>
                <td colspan='3' class="top">用 户 信 息 设 置</td>
                <input type="hidden" id="user_info_change_id" value="" />
                <input type="hidden" id="user_info_change_name" value="" />
            </tr>
            <tr>
                <td class="center" width="33%"><input type="button" class="botton" id="updateUserInfo" value="修改用户信息" /></td>
                <td class="center" width="33%"><input type="button" class="botton" id="changeUserPassInfo" value="修改用户密码" /></td>
                <td class="center" width="33%"><input type="button" class="botton" id="setUserRole" value="分配角色" /></td>
            </tr>
            <tr id="forEditorTr">
                <td class="center" colspan="3"><input type="button" class="botton" id="setUserProject" value="分配管理项目" /></td>
            </tr>
        </table>
        <input type="button" class="botton" id="cancel" value="  关  闭  " />
    </div>
</ul>
<!-- 用户信息修改选择窗口 end -->

<!-- 用户密码修改窗口 start -->
<ul id="changeUserPassInfoWin" class="msg_Tip_box width500" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table class="tableWin" border="1">
            <tr>
                <td colspan='2' class="top">修 改 用 户 登 录 密 码 信 息</td>
                <input type="hidden" id="user_pass_change_id" value="" />
            </tr>
            <tr>
                <td class='leftTd' width='25%'>用户名：</td>
                <td width='75%'>
                    <label id="user_pass_change_name"></label>
                </td>
            </tr>
            <tr>
                <td class='leftTd' width='25%'><span class="red">*</span>新密码：</td>
                <td width='75%'>
                    <input type="password" class="width150" maxlength="15" id="users_change_user_pass" name="users_change_user_pass" value="" />
                    <span id="users_change_user_pass_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>确认密码：</td>
                <td>
                    <input type="password" class="width150" maxlength="15" id="users_change_confirm_user_pass" name="users_change_confirm_user_pass" value="" />
                    <span id="users_change_confirm_user_pass_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveUserPassInfo" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 用户密码修改窗口 end -->

<!-- 用户角色修改窗口 start -->
<ul id="changeUserRoleInfoWin" class="msg_Tip_box width400" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table class="tableWin" border="1">
            <tr>
                <td colspan='2' class="top">修 改 用 户 角 色 信 息</td>
                <input type="hidden" id="user_role_change_id" value="" />
            </tr>
            <tr>
                <td class='leftTd' width='25%'>用户名：</td>
                <td width='75%'>
                    <label id="user_role_change_name"></label>
                </td>
            </tr>
            <tr>
                <td class='leftTd' width='35%'>角色：</td>
                <td width='65%'>
                    <select class="u_select" id="change_user_role" name="user_org">
                        <option value="-1" selected="selected">请选择角色</option>
					<#if rolesList?? && (rolesList?size > 0)>
						<#list rolesList as roleInfo>
                            <option value="${roleInfo.id}">${roleInfo.roleName}</option>
						</#list>
					</#if>
                    </select>
                    <span id="change_user_role_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveUserRoleInfo" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 用户密码修改窗口 end -->



<!-- 设置编辑者可管理项目的窗口 start -->
<ul id="setUserProjectWin" class="msg_Tip_box width912 height434" style="display:none">
    <div id="selectProjectItem">
        <div class="tit bgc_ccc move">
            <input type="hidden" id="user_project_change_id" value="" />
            <H2 class="left">为用户 "<span id="user_project_change_name" class="red"></span>" 分配可管理的项目</H2>
            <span class="pointer right"><input class="btn btn-fsjbut" type="button" id="cancelCommitUserProject" onclick="javascript:$.unblockUI()" value="取消" /></span>
            <span class="pointer right"><input class="btn btn-fsjbut" type="button" id="commitUserProject" value="确定" /></span>
        </div>
        <div class="activity_overdiv">
            <div style="float:left;width:600px">
                <div class="cls"></div>
                <div class="cont">
                    <div id="selectProject">
                        <select class="u_select" name="provinceSearch" id="provinceSearch">
                            <option selected="selected" value="-1">请选择省份</option>
                        <#if provinceList?? && (provinceList?size > 0)>
                            <#list provinceList as provinceInfo>
                                <option value="${provinceInfo.id}">${provinceInfo.province}</option>
                            </#list>
                        </#if>
                        </select>&nbsp;
                        <select class="u_select" name="citySearch" id="citySearch">
                            <option selected="selected" value="-1">请选择城市</option>
                        <#if cityList?? && (cityList?size > 0)>
                            <#list cityList as cityInfo>
                                <option value="${cityInfo.id}">${cityInfo.city}</option>
                            </#list>
                        </#if>
                        </select>&nbsp;
                        <span id="countyLevelCitySelectSearchSpan" class="hide">
                            <select class="u_select" name="countyLevelCitySelectSearch" id="countyLevelCitySelectSearch">
                                <option selected="selected" value="-1">请选择县级市</option>
                            </select>
                        </span>
                        <input class="btn btn-fsjbut" type="submit" id="queryProject" value="查询" />
                        <div id="itemListDiv">
                            <div id="allCountryDiv" class="marginTopBot10">
                                <span>
                                    <input type="checkbox" name="allCountry" value="allCountry" id="allCountry">全国所有项目
                                </span>
                            </div>
                            <div id="areaInputDiv" class="marginTopBot10"></div>
                            <div class="separator"></div>
                            <div id="projectItemInputDiv" class="marginTopBot10"></div>
                        </div>

                    </div>
                </div>
            </div>
            <div id="previewProject" style="width:285px;float:right;" >
                <div class="cls"></div>
                <div class="tit bgc_eee c_999"><H2>已选择的项目</H2></div>
                <div class="cont" id="previewAllCountryItem"></div>
                <div class="cont" id="previewAllProvinceItem"></div>
                <div class="cont" id="previewAllCityItem"></div>
                <div class="cont" id="previewAllCountyLevelCityItem"></div>
                <div class="cont" id="previewProjectItem"></div>
            </div>
        </div>
    </div>
</ul>
<!-- 设置编辑者可管理项目的窗口 end -->

</body>
</html>