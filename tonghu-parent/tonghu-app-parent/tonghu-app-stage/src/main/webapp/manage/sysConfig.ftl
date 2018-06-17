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

        <!-- 列表模块开始 start -->
        <div class="row-fluid">
            <div class="span12">
                <div class="widget_wrapper">
                    <div class="widget_header">
                        <div class="widget_header_option"><h3>${pageHanName}列表</h3></div>
                    </div>

                    <div class="widget_content no-padding">
                        <!-- 列表表格模块开始 start -->
                        <table cellspacing="0" cellpadding="0" class="default_table selectable_table special_table" width="100%">
                            <thead>
                            <tr>
                                <td width="5%">序号</td>
                                <td width="30%">配置项名称</td>
                                <td width="25%">配置值</td>
                                <td width="15%">值类型</td>
                                <td width="26%">动作</td>
                            </tr>
                            </thead>
                            <tbody>
							<#if sysConfigList?? && (sysConfigList?size > 0)>
								<#assign num = 0>
								<#list sysConfigList as sysConfigInfo>
									<#assign num = num + 1>
                                <tr>
                                    <td class="botValue">${num}</td>
                                    <td class="botValue">${sysConfigInfo.configName}</td>
                                    <td class="botValue">
                                        <#if sysConfigInfo.valueType == "integer">
                                            ${sysConfigInfo.configValue}
                                        <#elseif sysConfigInfo.valueType == "file">
                                            <A href="#" onclick="return downLoadFile('${sysConfigInfo.configValue}')">点击下载配置文件</A>
                                        </#if>
                                    </td>
                                    <td class="botValue">${sysConfigInfo.valueTypeStr}</td>
                                    <td class="botValue">
                                        <a class="btn" id="showSysConfig_${sysConfigInfo.id}"><i class="icon-eye-open"></i>查看</a>&nbsp;
                                        <a class="btn" id="updateSysConfig_${sysConfigInfo.id}"><i class="icon-edit"></i>修改</a>&nbsp;
                                        <input type="hidden" id="config_item" value="${sysConfigInfo.configItem}" />
                                    </td>
                                </tr>
								</#list>
							<#else>
                            <tr>
                                <td class="botValue" colspan="6" style="padding:10px 0;">暂无系统配置项信息！</td>
                            </tr>
							</#if>
                            </tbody>
                        </table>
                        <!-- 列表表格模块结束 end -->

                    </div>
                </div>
            </div>
        </div>
        <!-- 列表模块结束 end -->
    </div>
</div>
<!-- 主内容模块 右侧内容列表模块结束 end -->
<#include "/foot.ftl">
<script type="text/javascript" src="${ctx}/js/common/ajaxfileupload.js"></script>

<!-- 系统配置信息展示窗口 start -->
<ul id="sysConfigInfoShowWin" class="msg_Tip_box width600" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table class="tableWin" border="1">
            <tr><td colspan='4' class="top">展 示 系 统 配 置 项 信 息</td></tr>
            <tr>
                <td class='leftTd'>配置项：</td><td colspan='3'><label id="label_config_name"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>配置值：</td><td><label id="label_config_value"></label></td>
                <td class='leftTd'>值类型：</td><td><label id="label_value_type_str"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>配置项简介：</td><td colspan='3'><label id="label_config_note"></label></td>
            </tr>
            <tr>
                <td class='leftTd' width='18%'>创建时间：</td><td width='32%'><label id="label_config_create_time"></label></td>
                <td class='leftTd' width='18%'>创建用户：</td><td width='32%'><label id="label_config_create_userName"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>修改时间：</td><td><label id="label_config_update_time"></label></td>
                <td class='leftTd'>修改用户：</td><td><label id="label_config_update_userName"></label></td>
            </tr>
        </table>
        <input type="button" class="botton" onclick="javascript:$.unblockUI();return false;" value="  关  闭  " />
    </div>
</ul>
<!-- 系统配置信息展示窗口 end -->

<!-- 系统配置信息修改窗口 start -->
<ul id="changeConfigValueWin" class="msg_Tip_box width500" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table class="tableWin" border="1">
            <tr>
                <td colspan='2' class="top">修 改 系 统 配 置 项 信 息</td>
            </tr>
            <tr>
                <td class='leftTd' width='25%'>配置项：</td>
                <td width='75%'><label id="change_config_name"></label></td>
                <input type="hidden" id="change_config_item" value="" />
                <input type="hidden" value="" id="change_sys_config_id" />
            </tr>
            <tr id="simpleValueTr">
                <td class='leftTd'>配置值：</td>
                <td>
                    <input type="text" maxlength="20" class="width150" id="change_config_value" name="change_config_value" value="" />
                    <span id="change_config_value_error" class="red"></span>
                </td>
            </tr>
            <tr id="frontConfigFileTr" class="hide">
                <td class='leftTd'>配置值：</td>
                <td>
                    <div id="frontConfigFile_success" class="frontConfigFile"></div>
                    <div class="frontConfigFile">
                        <input type="file" id="frontConfigFile" name="frontConfigFile" onchange="fileChange(this,'frontConfigFile', ${maxFileSize}, ${fileSuffix});" />
                        <input class="btn btn-fsjbut" type="button" id="uploadFrontConfigFile" value="上传" />
                        <input type="hidden" id="frontConfigFileId" value="" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>值类型：</td>
                <td>
                    <label id="change_value_type_str"></label>
                    <input type="hidden" value="" id="change_value_type" />
                </td>
            </tr>
            <tr>
                <td class='leftTd'>配置项简介：</td>
                <td><label id="change_config_note"></label></td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveChangeConfigValue" value="  确  定  " />
        <input type="button" class="botton" onclick="javascript:$.unblockUI();return false;" value="  取  消  " />
    </div>
</ul>
<!-- 系统配置信息修改窗口 end -->
</body>
</html>