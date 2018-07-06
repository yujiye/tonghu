/**
 * 模拟数据管理JS代码
 * author: liangyongjian
 * createDate: 2018-06-26 19:24:45
 */
$(document).ready(function() {

    //点击新增模拟数据
    $('#addNewMockData').click(function() {
        $('#data_name').val(""); $('#data_name').attr("class", "width400");
        $("#data_name_error").show(); $("#data_name_error").html("");
        $('#data_value').val("");
        $("#data_value_error").show(); $("#data_value_error").html("");
        $('#data_note').val(""); $('#data_note').attr("class", "width400");
        $("#data_note_error").show(); $("#data_note_error").html("");
        blockUIOpen('addNewMockDataWin');
        setButtonStatus("saveNewMockData", false); //设置按钮不可用
    });

    //保存新增加的模拟数据信息
    $('#saveNewMockData').live("click", function() {
        setButtonStatus("saveNewMockData", true); //设置按钮不可用
        $('#data_name').blur();
        $('#data_value').blur();
        var is_error = false;
        var _error = ["data_name", "data_value"];
        $.each(_error, function(key, val) {
            var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
                if (error_flag.indexOf("wrong") >= 0) {
                    is_error = true;
                    return false;
                }
        });
        if (is_error) {
            setButtonStatus("saveNewMockData", false); //设置按钮可用
            return false;
        }
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/saveNewMockData.do',
            dataType : 'json',
            data : {
                dataName : $('#data_name').val(),
                dataType : "JSON_STR",
                dataValue : $('#data_value').val(),
                note : $('#data_note').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status != "error") {
                        $('#oper_result_label').html(data.data + "页面即将刷新！");
                        blockUIOpen('operResultWin');
                        reloadPageWithDelay("", "", "/mockDataManage.do", "");
                    } else {
                        $.each(data.data, function(i, item) {
                            seterror(item.key, item.value, false);
                        });
                        setButtonStatus("saveNewMockData", false); //设置按钮可用
                    }
                }
            },
            error : function(data) {}
        });
    });

    //查看模拟数据信息
    $("a[id*='showMockData']").on('click', function() {
        var objId = $(this).attr("id");
        var dataId = objId.replace("showMockData_", "");
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/showMockData.do',
            dataType : 'json',
            data : {
                dataId : dataId
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#label_data_name').html(data.data.dataName);
                    $('#label_data_type').html(data.data.dataTypeName);
                    $('#label_data_value').html("<A href='" + data.data.getInfoApiUrl + "' target='_blank'>点击查看</A>");
                    $('#label_mock_data_create_time').html(data.data.createTime);
                    $('#label_mock_data_create_userName').html(data.data.createUserName);
                    $('#label_mock_data_update_time').html(data.data.updateTime);
                    $('#label_mock_data_update_userName').html(data.data.updateUserName);
                    $('#label_data_note').html(data.data.note);
                    blockUIOpen('showMockDataWin');
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //修改模拟数据信息 将模拟数据信息铺回到页面
    $("a[id*='editMockData']").on('click', function() {
        var objId = $(this).attr("id");
        var dataId = objId.replace("editMockData_", "");
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/showMockData.do',
            dataType : 'json',
            data : {
                dataId : dataId
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#change_data_id').val(dataId);
                    $('#change_data_name').val(data.data.dataName);
                    $('#change_data_type').html(data.data.dataTypeName);
                    $('#change_data_value').val(data.data.dataValue);
                    $('#change_data_note').val(data.data.note);
                    $('#change_data_name').blur();
                    $('#change_data_value').blur();
                    $('#change_data_note').blur();
                    blockUIOpen('editMockDataWin');
                    setButtonStatus("saveChangeMockData", false); //设置按钮可用
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //点击删除模拟数据的按钮
    $("a[id*='removeMockData']").on('click', function() {
        var objId = $(this).attr("id");
        var dataId = objId.replace("removeMockData_", "");
        $('#remove_alert_info').html("是否要删除模拟数据“" + $("#show_data_name_" + dataId).html() + "”？");
        $('#remove_data_id').val(dataId);
        blockUIOpen('removeMockDataConfirm');
        setButtonStatus("yesRemoveMockData", false); //设置按钮可用
    });

    //删除模拟数据
    $('#yesRemoveMockData').live("click", function() {
        setButtonStatus("yesRemoveMockData", true); //设置按钮不可用
        $.blockUIClose;
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/removeMockData.do',
            dataType : 'json',
            data : {
                dataId : $('#remove_data_id').val()
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#oper_result_label').html(data.data + "页面即将刷新！");
                    blockUIOpen('operResultWin');
                    reloadPageWithDelay("", "", "/mockDataManage.do", "");
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //保存所修改的模拟数据信息
    $('#saveChangeMockData').live("click", function() {
        setButtonStatus("saveChangeMockData", true); //设置按钮不可用
        $('#change_data_name').blur();
        $('#change_data_value').blur();
        $('#change_data_note').blur();
        var is_error = false;
        var _error = ["change_data_name", "change_data_value", "change_data_note"];
        $.each(_error, function(key, val) {
            var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
                if (error_flag.indexOf("wrong") >= 0) {
                    is_error = true;
                    return false
                }
        });
        if (is_error) {
            setButtonStatus("saveChangeMockData", false); //设置按钮可用
            return false;
        }
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/changeMockData.do',
            dataType : 'json',
            data : {
                id : $('#change_data_id').val(),
                dataName : $('#change_data_name').val(),
                dataType : "JSON_STR",
                dataValue : $('#change_data_value').val(),
                note : $('#change_data_note').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status != "error") {
                        $('#oper_result_label').html(data.data + "页面即将刷新！");
                        blockUIOpen('operResultWin');
                        reloadPageWithDelay("", "", "/mockDataManage.do", "");
                    } else {
                        $.each(data.data, function(i, item) {
                            seterror(item.key, item.value, false);
                        });
                        setButtonStatus("saveChangeMockData", false); //设置按钮可用
                    }
                }
            },
            error : function(data) {}
        });
    });

    //模拟数据名称校验
    $('#data_name').change(function() {
        checkDataName(this);
    });
    $('#data_name').blur(function() {
        checkDataName(this);
    });

    //模拟数据名称校验
    $('#change_data_name').change(function() {
        checkDataName(this);
    });
    $('#change_data_name').blur(function() {
        checkDataName(this);
    });

    //模拟数据值校验
    $('#data_value').change(function() {
        checkDataValue(this);
    });
    $('#data_value').blur(function() {
        checkDataValue(this);
    });

    //模拟数据值校验
    $('#change_data_value').change(function() {
        checkDataValue(this);
    });
    $('#change_data_value').blur(function() {
        checkDataValue(this);
    });

    //模拟数据备注校验
    $('#data_note').change(function() {
        checkDataNote(this);
    });
    $('#data_note').blur(function() {
        checkDataNote(this);
    });

    //模拟数据备注校验
    $('#change_data_note').change(function() {
        checkDataNote(this);
    });
    $('#change_data_note').blur(function() {
        checkDataNote(this);
    });

});

//==========================================
//Purpose: 验证模拟数据名称
//==========================================
function checkDataName(obj) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "请输入模拟数据项名称", false);
        return false;
    } else if ($(obj).val().length > 25) {
        seterror(objId, "模拟数据项名称不能超过25个字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证模拟数据值
//==========================================
function checkDataValue(obj) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "<BR/>请输入模拟数据值", false);
        return false;
    } else if ($(obj).val().length > 16777215) {
        seterror(objId, "<BR/>模拟数据值不能超过16777215个字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证备注的输入
//==========================================
function checkDataNote(obj) {
    var objId = $(obj).attr("id");
    checkEmpty(obj);
    if ($(obj).val() != null && $(obj).val() != "" && $(obj).val().length > 120) {
        seterror(objId, "备注长度限制为120字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}