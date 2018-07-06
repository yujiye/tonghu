/**
 * 系统配置项管理JS代码
 * author: liangyongjian
 * createDate: 2017-11-26 19:24:45
 */
$(document).ready(function() {

    //查看系统配置值信息
    $("a[id*='showSysConfig']").on('click', function() {
        var objId = $(this).attr("id");
        var sysConfigId = objId.replace("showSysConfig_", "");
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/showSysConfig.do',
            dataType : 'json',
            data : {
                sysConfigId : sysConfigId
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#label_config_name').html(data.data.configName);
                    if (data.data.valueType == "integer" || data.data.valueType == "string") {
                        $('#label_config_value').html(data.data.configValue);
                    } else if (data.data.valueType == "file") {
                        $('#label_config_value').html('<A href="#" ' +
                            'onclick="return downLoadFile(\''+data.data.configValue+'\')">点击下载配置文件</A>');
                    }
                    $('#label_value_type_str').html(data.data.valueTypeStr);
                    $('#label_config_note').html(data.data.configNote);
                    $('#label_config_create_time').html(data.data.createTime);
                    $('#label_config_create_userName').html(data.data.createUserName);
                    $('#label_config_update_time').html(data.data.updateTime);
                    $('#label_config_update_userName').html(data.data.updateUserName);
                    blockUIOpen('sysConfigInfoShowWin');
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //修改系统配置值信息 将系统配置值信息铺回到页面
    $("a[id*='updateSysConfig']").on('click', function() {
        var objId = $(this).attr("id");
        var sysConfigId = objId.replace("updateSysConfig_", "");
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/showSysConfig.do',
            dataType : 'json',
            data : {
                sysConfigId : sysConfigId
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#frontConfigFile_success').html("");
                    $('#frontConfigFileId').val("");
                    $('#change_config_value').val("");
                    $('#change_config_name').html(data.data.configName);
                    $('#change_config_item').val(data.data.configItem);
                    $('#change_sys_config_id').val(data.data.id);
                    $('#change_value_type_str').html(data.data.valueTypeStr);
                    $('#change_value_type').val(data.data.valueType);
                    if (data.data.valueType == "integer" || data.data.valueType == "string") {
                        $('#change_config_value').val(data.data.configValue);
                        $("#simpleValueTr").show();
                        $("#frontConfigFileTr").hide();
                    } else if (data.data.valueType == "file") {
                        $('#frontConfigFile_success').html('<A href="#" ' +
                            'onclick="return downLoadFile(\''+data.data.configValue+'\')">点击下载配置文件</A>');
                        $('#frontConfigFileId').val(data.data.configValue);
                        $("#simpleValueTr").hide();
                        $("#frontConfigFileTr").show();
                    }
                    $('#change_config_note').html(data.data.configNote);
                    $('#change_config_value').blur();
                    blockUIOpen('changeConfigValueWin');
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //保存所修改的系统配置值信息
    $('#saveChangeConfigValue').live("click", function() {
        setButtonStatus("saveChangeConfigValue", true); //设置按钮不可用
        $('#change_config_value').blur();
        var is_error = false;
        var _error = ["change_config_value"];
        $.each(_error, function(key, val) {
            var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
                if (error_flag.indexOf("wrong") >= 0) {
                    is_error = true;
                    return false
                }
        });
        if (is_error) {
            setButtonStatus("saveChangeConfigValue", false); //设置按钮可用
            return false;
        }
        var configValue = "";
        if ($('#change_value_type').val() == "integer" || $('#change_value_type').val() == "string") {
            configValue = $('#change_config_value').val();
        } else if ($('#change_value_type').val() == "file") {
            configValue = $('#frontConfigFileId').val();
        }

        jQuery.ajax({
            type : 'POST',
            url : ctx + '/changeSysConfig.do',
            dataType : 'json',
            data : {
                id : $('#change_sys_config_id').val(),
                configItem : $('#change_config_item').val(),
                configValue : configValue
            },
            success : function(data) {
                if (data) {
                    if (data.status != "error") {
                        $('#oper_result_label').html(data.data + "页面即将刷新！");
                        blockUIOpen('operResultWin');
                        reloadPage("sysConfig.do");
                    } else {
                        $.each(data.data, function(i, item) {
                            seterror(item.key, item.value, false);
                        });
                        setButtonStatus("saveChangeConfigValue", false); //设置按钮可用
                    }
                }
            },
            error : function(data) {}
        });
    });


    $('#uploadFrontConfigFile').click(function() {
        setButtonStatus("uploadFrontConfigFile", true);
        if($("#frontConfigFile").val() == "") {
            setButtonStatus("uploadFrontConfigFile", false);
            $('#oper_result_label').html("尚未选择要上传的文件！");
            blockUIOpen('operResultWin');
        } else {
            $.ajaxFileUpload({
                url : ctx + "/fileUpload.do",
                secureuri : false,
                fileElementId : 'frontConfigFile',
                dataType : 'json',
                success: function (data, status){
                    if (data.status == "success") {
                        $("#frontConfigFile").val();
                        $("#frontConfigFileId").val(data.data);
                        $("#frontConfigFile_success").html(
                            '<A href="#" onclick="return downLoadFile(\''+data.data+'\')">'
                            + data.fileName + '</A>');
                        var file = document.getElementById("frontConfigFile");
                        file.outerHTML = file.outerHTML;
                    } else {
                        $('#oper_result_label').html(data.data);
                        blockUIOpen('operResultWin');
                    }
                    setButtonStatus("uploadFrontConfigFile", false);
                },
                error: function (data, status, e){
                    setButtonStatus("uploadFrontConfigFile", false);
                }
            });
        }
    });

    $('#deleteFile').live('click', function() {
        $(this).parent().next().val("");
        $(this).parent().html("");
    });

    //系统配置值校验
    $('#change_config_value').change(function() {
        checkChangeConfigValue(this);
    });
    $('#change_config_value').blur(function() {
        checkChangeConfigValue(this);
    });

});

//==========================================
//Purpose: 验证配置值的输入
//==========================================
function checkChangeConfigValue(obj) {
    var valueType = $("#change_value_type").val();
    if (valueType == "integer") {
        checkLongValue(obj);
    }
}