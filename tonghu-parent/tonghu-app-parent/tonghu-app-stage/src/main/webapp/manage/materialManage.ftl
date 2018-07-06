<#include "/top.ftl">

<!-- 主内容模块 右侧内容列表模块开始 start -->
<div class="content_wrapper">
    <div class="contents">
        <div class="row-fluid">
            <div class="span6">
                <div class="ico_16_${pageName} content_header left"><h3>${pageHanName}管理</h3></div>
                <div class="left button"><button class="btn" id="addNewMaterialInfo" type="button"><i class="icon-plus"></i>&nbsp;添加资源资源&nbsp;</button></div>
            </div>
        </div>
        <div class="separator"><span></span></div>

        <div id="msg" class="msg"></div>

        <!-- 资源资源列表模块开始 start -->
        <div class="row-fluid">
            <div class="span12">
                <div class="widget_wrapper">
                    <div class="widget_header">
                        <div class="widget_header_option"><h3>${pageHanName}列表</h3></div>
                    </div>

                    <div class="widget_content no-padding">
                        <!-- 资源资源列表表格模块开始 start -->
                        <table cellspacing="0" cellpadding="0" class="default_table selectable_table special_table" width="100%">
                            <thead>
                            <tr>
                                <td width="5%">序号</td>
                                <td width="25%">资源名称</td>
                                <td width="10%">资源类型</td>
                                <td width="35%">资源地址</td>
                                <td width="25%">动作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <#if materialList?? && (materialList?size > 0)>
                                <#assign num = 0>
                                <#list materialList as materialInfo>
                                    <#assign num = num + 1>
                                <tr>
                                    <td class="botValue">${num}</td>
                                    <td class="botValue">
                                        <label class="columnLable" id="show_material_name_${materialInfo.id}">${materialInfo.materialName}</label>

                                    </td>
                                    <td class="botValue">${materialInfo.materialTypeDesc}</td>
                                    <td class="botValue">${materialInfo.attachFileUrl}</td>
                                    <td class="botValue">
                                        <a class="btn" id="showMaterialInfo_${materialInfo.id}"><i class="icon-eye-open"></i>查看</a>&nbsp;
                                        <a class="btn" id="editMaterialInfo_${materialInfo.id}"><i class="icon-edit"></i>修改</a>&nbsp;
                                        <a class="btn" id="removeMaterialInfo_${materialInfo.id}"><i class="icon-trash"></i>删除</a>&nbsp;
                                    </td>
                                </tr>
                                </#list>
                            <#else>
                            <tr>
                                <td class="botValue" colspan="5" style="padding:10px 0;">暂无资源资源信息！</td>
                            </tr>
                            </#if>
                            </tbody>
                        </table>
                        <!-- 资源资源表格模块结束 end -->

                    </div>
                </div>
            </div>
        </div>
        <!-- 资源资源列表模块结束 end -->
    </div>
</div>
<!-- 主内容模块 右侧内容列表模块结束 end -->
<script type="text/javascript">var hostNameSuffix = "${hostName}";</script>
<script type="text/javascript" src="${ctx}/js/common/ajaxfileupload.js"></script>
<#include "/foot.ftl">
<!-- 添加资源资源窗口 start -->
<ul id="addNewMaterialInfoWin" class="msg_Tip_box width800" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='2' class="top">添 加 资 源 信 息</td></tr>
            <tr>
                <td class='leftTd' width='16%'><span class="red">*</span>资源名称：</td>
                <td width='84%'>
                    <input type="text" maxlength="25" class="width400" id="material_name" name="material_name" value="" />
                    <span id="material_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>资源类型：</td>
                <td>
                    <select class="u_select" id="material_type" name="material_type">
                        <option value="1" selected="selected">图片</option>
                        <option value="2">视频</option>
                    </select>
                    <span id="material_type_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>资源文件：</td>
                <td>
                    <div id="uploadImgFile">
                        <div class="materialFileImg">
                            <input type="button" OnClick='javascript:$("#materialFileImg").click();' id="materialFileImgSelect" value="上 传" class="btn btn-fsjbut" >
                            <input style="display: none;" type="file" name="materialFileImg" id="materialFileImg" onchange="fileChange(this,'materialFileImg', ${maxImageSize}, '${imageSuffix}');" />
                            <input type="hidden" id="materialFileImgId" value="" />
                            <input class="hide" type="button" id="materialFileImgUpload">
                        </div>
                        <div id="materialFileImgSuccess" class="materialFileImg"></div>
                    </div>
                    <div id="uploadVideoFile" style="display: none;">
                        <div class="materialVideoFile">
                            <input type="button" OnClick='javascript:$("#materialFileVideo").click();' id="materialFileVideoSelect" value="上 传" class="btn btn-fsjbut" >
                            <input style="display: none;" type="file" name="materialFileVideo" id="materialFileVideo" onchange="fileChange(this,'materialFileVideo', ${maxVideoSize}, '${videoSuffix}');" />
                            <input type="hidden" id="materialFileVideoId" value="" />
                            <input class="hide" type="button" id="materialFileVideoUpload">
                        </div>
                        <div id="materialFileVideoSuccess" class="materialFileImg"></div>
                    </div>
                    <span id="material_file_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'>资源描述：</td>
                <td>
                    <input type="text" maxlength="120" class="width400" id="material_desc" name="material_desc" value="" />
                    <span id="material_desc_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="button" class="botton" id="saveNewMaterialInfo" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 添加资源资源窗口 end -->

<!-- 编辑资源资源窗口 start -->
<ul id="editMaterialInfoWin" class="msg_Tip_box width800" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='2' class="top">修 改 资 源 信 息</td></tr>
            <tr>
                <td class='leftTd' width='16%'><span class="red">*</span>资源名称：</td>
                <td width='84%'>
                    <input type="text" maxlength="25" class="width400" id="change_material_name" name="change_material_name" value="" />
                    <span id="change_material_name_error" class="red"></span>
                </td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>资源类型：</td>
                <td>
                    <select class="u_select" id="change_material_type" name="change_material_type">
                        <option value="1" selected="selected">图片</option>
                        <option value="2">视频</option>
                    </select>
                    <span id="change_material_type_error" class="red"></span>
                </td>
            </tr>


            <tr>
                <td class='leftTd'>资源文件：</td>
                <td>
                    <div id="change_uploadImgFile">
                        <div class="materialFileImg">
                            <input type="button" OnClick='javascript:$("#change_materialFileImg").click();' id="change_materialFileImgSelect" value="上 传" class="btn btn-fsjbut" >
                            <input style="display: none;" type="file" name="change_materialFileImg" id="change_materialFileImg" onchange="fileChange(this,'change_materialFileImg', ${maxImageSize}, '${imageSuffix}');" />
                            <input type="hidden" id="change_materialFileImgId" value="" />
                            <input class="hide" type="button" id="change_materialFileImgUpload">
                        </div>
                        <div id="change_materialFileImgSuccess" class="materialFileImg"></div>
                    </div>
                    <div id="change_uploadVideoFile" style="display: none;">
                        <div class="materialVideoFile">
                            <input type="button" OnClick='javascript:$("#change_materialFileVideo").click();' id="change_materialFileVideoSelect" value="上 传" class="btn btn-fsjbut" >
                            <input style="display: none;" type="file" name="change_materialFileVideo" id="change_materialFileVideo" onchange="fileChange(this,'change_materialFileVideo', ${maxVideoSize}, '${videoSuffix}');" />
                            <input type="hidden" id="change_materialFileVideoId" value="" />
                            <input class="hide" type="button" id="change_materialFileVideoUpload">
                        </div>
                        <div id="change_materialFileVideoSuccess" class="materialFileImg"></div>
                    </div>
                    <span id="change_material_file_error" class="red"></span>
                </td>
            </tr>

            <tr>
                <td class='leftTd'>资源描述：</td>
                <td>
                    <input type="text" maxlength="120" class="width400" id="change_material_desc" name="change_material_desc" value="" />
                    <span id="change_material_desc_error" class="red"></span>
                </td>
            </tr>
        </table>
        <input type="hidden" id="change_material_id" name="change_material_id" value="" />
        <input type="button" class="botton" id="saveChangeMaterialInfo" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 编辑资源资源窗口 end -->

<!-- 查看资源资源窗口 start -->
<ul id="showMaterialInfoWin" class="msg_Tip_box width700" style="display:none">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="center tabDivInfo">
        <table width="100%" class="tableWin">
            <tr><td colspan='4' class="top">查 看 资 源 信 息</td></tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>资源名称：</td>
                <td colspan="3"><label id="label_material_name"></label></td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>资源类型：</td>
                <td colspan="3"><label id="label_material_type"></label></td>
            </tr>
            <tr>
                <td class='leftTd'><span class="red">*</span>资源地址：</td>
                <td colspan="3"><label id="label_material_attach_url"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>资源描述：</td>
                <td colspan="3"><label id="label_material_desc"></label></td>
            </tr>
            <tr>
                <td class='leftTd' width="18%">创建时间：</td><td width="32%"><label id="label_material_create_time"></label></td>
                <td class='leftTd' width="18%">创建用户：</td><td width="32%"><label id="label_material_create_userName"></label></td>
            </tr>
            <tr>
                <td class='leftTd'>修改时间：</td><td><label id="label_material_update_time"></label></td>
                <td class='leftTd'>修改用户：</td><td><label id="label_material_update_userName"></label></td>
            </tr>
        </table>
        <input type="button" class="botton" id="cancel" value="  关  闭  " />
    </div>
</ul>
<!-- 查看资源资源窗口 end -->

<!-- 删除资源资源提示框 start -->
<ul id="removeMaterialInfoConfirm" class="msg_Tip_box width300" style="display:none" id="show">
    <a class="mask_close pr10" href="javascript:$.unblockUI()"></a>
    <div class="showAlertInfo">
        <h4 id="remove_alert_info"></h4><br>
        <input type="hidden" id="remove_material_id" value="" />
        <input type="button" class="botton" id="yesRemoveMaterialInfo" value="  确  定  " />
        <input type="button" class="botton" id="cancel" value="  取  消  " />
    </div>
</ul>
<!-- 删除资源资源提示框 end -->

</body>
</html>