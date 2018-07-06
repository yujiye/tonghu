<#include "/top.ftl">

<!-- 主内容模块 右侧内容列表模块开始 start -->
<div class="content_wrapper">
    <div class="contents">
        <div class="row-fluid">
            <div class="span6">
                <div class="ico_16_${pageName} content_header left"><h3>${pageHanName}管理</h3></div>
                <div class="left button"><button class="btn" id="addNewMockData" type="button"><i class="icon-plus"></i>&nbsp;添加模拟数据&nbsp;</button></div>
            </div>
        </div>
        <div class="separator"><span></span></div>

        <div id="msg" class="msg"></div>

        <!-- 模拟数据列表模块开始 start -->
        <div class="row-fluid">
            <div class="span12">
                <div class="widget_wrapper">
                    <div class="widget_header">
                        <div class="widget_header_option"><h3>${pageHanName}列表</h3></div>
                    </div>

                    <div class="widget_content no-padding">
                        <!-- 模拟数据列表表格模块开始 start -->
                        <table cellspacing="0" cellpadding="0" class="default_table selectable_table special_table" width="100%">
                            <thead>
                            <tr>
                                <td width="5%">序号</td>
                                <td width="30%">数据项名称</td>
                                <td width="15%">数据项类型</td>
                                <td width="15%">接口地址</td>
                                <td width="35%">动作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <#if mockDataList?? && (mockDataList?size > 0)>
                                <#assign num = 0>
                                <#list mockDataList as mockData>
                                    <#assign num = num + 1>
                                <tr>
                                    <td class="botValue">${num}</td>
                                    <td class="botValue">
                                        <label class="columnLable" id="show_data_name_${mockData.id}">${mockData.dataName}</label>

                                    </td>
                                    <td class="botValue">${mockData.dataTypeName}</td>
                                    <td class="botValue"><A href="${mockData.getInfoApiUrl}" target="_blank">点击查看</A></td>
                                    <td class="botValue">
                                        <a class="btn" id="showMockData_${mockData.id}"><i class="icon-eye-open"></i>查看</a>&nbsp;
                                        <a class="btn" id="editMockData_${mockData.id}"><i class="icon-edit"></i>修改</a>&nbsp;
                                        <a class="btn" id="removeMockData_${mockData.id}"><i class="icon-trash"></i>删除</a>&nbsp;
                                    </td>
                                </tr>
                                </#list>
                            <#else>
                            <tr>
                                <td class="botValue" colspan="5" style="padding:10px 0;">暂无模拟数据信息！</td>
                            </tr>
                            </#if>
                            </tbody>
                        </table>
                        <!-- 模拟数据表格模块结束 end -->

                    </div>
                </div>
            </div>
        </div>
        <!-- 模拟数据列表模块结束 end -->
    </div>
</div>
<!-- 主内容模块 右侧内容列表模块结束 end -->
<#include "/foot.ftl">
<!-- 添加模拟数据窗口 start -->
<ul id="addNewMockDataWin" class="msg_Tip_box width800" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='2' class="top">添 加 模 拟 数 据</td></tr>
            <tr>
                <td class='leftTd' width='16%'><span class="red">*</span>数据项名称：</td>
                <td width='84%'>
                    <input type="text" maxlength="25" class="width400" id="data_name" name="data_name" value="" />
                    <span id="data_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据类型：</td>
                <td><label>JSON字符串</label></td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据值：</td>
                <td>
                    <textarea rows="8" cols="80" class="width550 marginBot5" id="data_value" name="data_value" ></textarea>
                    <span id="data_value_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>备注：</td>
                <td>
                    <input type="text" maxlength="120" class="width400" id="data_note" name="data_note" value="" />
                    <span id="data_note_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveNewMockData" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 添加模拟数据窗口 end -->

<!-- 编辑模拟数据窗口 start -->
<ul id="editMockDataWin" class="msg_Tip_box width800" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='2' class="top">修 改 模 拟 数 据</td></tr>
            <tr>
                <td class='leftTd' width='16%'><span class="red">*</span>数据项名称：</td>
                <td width='84%'>
                    <input type="text" maxlength="25" class="width400" id="change_data_name" name="change_data_name" value="" />
                    <span id="change_data_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据类型：</td>
                <td><label id="change_data_type"></label></td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据值：</td>
                <td>
                    <textarea rows="8" cols="80" class="width550 marginBot5" id="change_data_value" name="change_data_value" ></textarea>
                    <span id="change_data_value_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>备注：</td>
                <td>
                    <input type="text" maxlength="120" class="width400" id="change_data_note" name="change_data_note" value="" />
                    <span id="change_data_note_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="hidden" id="change_data_id" name="change_data_id" value="" />
        <input type="button" class="botton" id="saveChangeMockData" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 编辑模拟数据窗口 end -->

<!-- 查看模拟数据窗口 start -->
<ul id="showMockDataWin" class="msg_Tip_box width700" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='4' class="top">查 看 模 拟 数 据</td></tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据项名称：</td>
                <td colspan="3"><label id="label_data_name"></label></td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据类型：</td>
                <td colspan="3"><label id="label_data_type"></label></td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>数据值：</td>
                <td colspan="3"><label id="label_data_value"></label></td>
            </tr>
            <tr>
                <td class='leftTd' width="18%">创建时间：</td><td width="32%"><label id="label_mock_data_create_time"></label></td>
                <td class='leftTd' width="18%">创建用户：</td><td width="32%"><label id="label_mock_data_create_userName"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>修改时间：</td><td><label id="label_mock_data_update_time"></label></td>
                <td class='leftTd'>修改用户：</td><td><label id="label_mock_data_update_userName"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>备注：</td>
                <td colspan="3"><label id="label_data_note"></label></td>
            </tr>
        </table>
        <input type="button" class="botton" id="cancel" value="  关  闭  " />
    </div>
</ul>
<!-- 查看模拟数据窗口 end -->

<!-- 删除模拟数据提示框 start -->
<ul id="removeMockDataConfirm" class="msg_Tip_box width300" style="display:none" id="show">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="showAlertInfo">
        <h4 id="remove_alert_info"></h4><br>
        <input type="hidden" id="remove_data_id" value="" />
        <input type="button" class="botton" id="yesRemoveMockData" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 删除模拟数据提示框 end -->

</body>
</html>