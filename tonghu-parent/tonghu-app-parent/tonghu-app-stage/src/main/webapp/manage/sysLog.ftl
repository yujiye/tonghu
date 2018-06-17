<#include "/top.ftl">

<!-- 主内容模块 右侧内容列表模块开始 start -->
<div class="content_wrapper">
    <div class="contents">
        <div class="row-fluid">
            <div class="span6">
                <div class="ico_16_${pageName} content_header left"><h3>${pageHanName}管理</h3></div>
            </div>
        </div>
        <div class="separator"><span></span></div>

        <div id="msg" class="msg"></div>

        <!-- 搜索模块开始 start -->
        <div class="row-fluid">
            <div class="span12">
                <div class="widget_wrapper">
                    <div class="widget_header"><h3 class="icos_${pageName}">${pageHanName}管理</h3></div>
                    <div class="widget_content no-padding">
                        <form method="post" aciton="${ctx}${currentPageUrl}" id="searchForm">
                            <div class="form_inputs clearfix">
                                <div class="row-fluid">
                                    <input type="hidden" value="${totalInfo.currentPage}" id="pageNumInput" name="pageNumInput" />
                                    用户名称：<select class="u_select" id="userSearch" name="userSearch">
                                    <option <#if userId?? && userId==-1>selected="selected"</#if> value="-1">请选用户名称</option>
								<#if userList?? && (userList?size > 0)>
									<#list userList as userInfo>
                                        <option value="${userInfo.id}" <#if userId?? && userId==userInfo.id>selected="selected"</#if>>${userInfo.trueName}</option>
									</#list>
								</#if>
                                </select>&nbsp;
                                    操作结果：<select class="u_select" id="operResultSearch" name="operResultSearch">
                                    <option <#if operResult?? && operResult==-1>selected="selected"</#if> value="-1">请选择操作结果</option>
                                    <option <#if operResult?? && operResult==1>selected="selected"</#if> value="1">操作成功</option>
                                    <option <#if operResult?? && operResult==0>selected="selected"</#if> value="0">操作失败</option>
                                </select>&nbsp;
                                    操作时间：<input id="operateDateStartSearch" name="operateDateStartSearch" type="text" class="width100" value="<#if operateDateStart??>${operateDateStart}</#if>" data-date-format="yyyy-mm-dd" />
                                    - <input id="operateDateEndSearch" name="operateDateEndSearch" type="text" value="<#if operateDateEnd??>${operateDateEnd}</#if>" class="width100" data-date-format="yyyy-mm-dd" />&nbsp;
                                    <input class="btn btn-fsjbut" type="button" id="searchMatch" value="搜索" />
                                    <input class="btn btn-fsjbut" type="button" id="searchAll" value="查看全部" />
                                    <br/><br/>
                                    第 <span class="bold">${totalInfo.currentPage}</span> 页  共 <span class="bold">${totalInfo.pageTotal}</span> 页  每页 <span class="bold">${totalInfo.pageSize}</span> 条记录 共 <span class="bold">${totalInfo.totalCount}</span> 条记录
                                    <br>
                                    <div class="separator"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 搜索模块结束 end -->

        <!-- 专家列表模块开始 start -->
        <div class="row-fluid">
            <div class="span12">
                <div class="widget_wrapper">
                    <div class="widget_header">
                        <div class="widget_header_option">
                            <h3>${pageHanName}列表</h3>
                            <div class="widget_option">
                            </div>
                        </div>
                    </div>
                    <div class="widget_content no-padding">
                        <!-- 专家列表表格模块开始 start -->
                        <table cellspacing="0" cellpadding="0" class="default_table selectable_table special_table" width="100%">
                            <thead>
                            <thead>
                            <tr>
                                <td width="5%">序号</td>
                                <td width="9%">用户名称</td>
                                <td width="9%">用户ip</td>
                                <td width="8%">操作类型</td>
                                <td width="8%">操作结果</td>
                                <td width="23%">操作内容</td>
                                <td width="23%">备注</td>
                                <td width="15%">操作时间</td>
                            </tr>
                            </thead>
                            </thead>
                            <tbody>

							<#if sysLogList?? && (sysLogList?size > 0)>
								<#assign num = totalInfo.startNum>
								<#list sysLogList as sysLogInfo>
									<#assign num = num + 1>
                                <tr>
                                    <td class="botValue">${num}</td>
                                    <td class="botValue">${sysLogInfo.userName}</td>
                                    <td class="botValue">${sysLogInfo.userIp}</td>
                                    <td class="botValue">${sysLogInfo.operatorTypeName}</td>
                                    <td class="botValue">${sysLogInfo.success}</td>
                                    <td class="botValue">${sysLogInfo.content}</td>
                                    <td class="botValue">${sysLogInfo.cause}</td>
                                    <td class="botValue">${sysLogInfo.createTime}</td>
                                </tr>
								</#list>
							<#else>
                            <tr>
                                <td class="botValue" colspan="10" style="padding:10px 0;">暂无系统日志信息！</td>
                            </tr>
							</#if>
                            </tbody>
                        </table>
                        <!-- 专家列表表格模块结束 end -->

                        <!-- 专家列表分页模块开始 start -->
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
                        <!-- 专家列表分页模块结束 end -->
                    </div>
                </div>
            </div>
        </div>
        <!-- 专家列表模块结束 end -->

    </div>
</div>
<!-- 主内容模块 右侧内容列表模块结束 end -->
<#include "/foot.ftl">

<!-- 批量导出的提示框 end -->
</body>
</html>