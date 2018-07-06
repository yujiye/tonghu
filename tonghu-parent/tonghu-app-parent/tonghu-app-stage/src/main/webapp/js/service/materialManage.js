/**
 * 资源管理JS代码
 * author: liangyongjian
 * createDate: 2018-06-26 19:24:45
 */
$(document).ready(function() {

    $('#material_type').change(function() {
        if ($('#material_type').val() == 1) {
            $('#uploadImgFile').show();
            $('#uploadVideoFile').hide();
        } else if ($('#material_type').val() == 2) {
            $('#uploadImgFile').hide();
            $('#uploadVideoFile').show();
        }
    });

    $('#change_material_type').change(function() {
        if ($('#change_material_type').val() == 1) {
            $('#change_uploadImgFile').show();
            $('#change_uploadVideoFile').hide();
        } else if ($('#change_material_type').val() == 2) {
            $('#change_uploadImgFile').hide();
            $('#change_uploadVideoFile').show();
        }
    });

    //图片 附件上传
    $("input[id*='ImgUpload']").live("click",function() {
        var objId = $(this).attr("id");
        var objIdPrefix = objId.replace("ImgUpload", "");
        setButtonStatus(objIdPrefix + "ImgSelect", true);
        if($("#" + objIdPrefix + "Img").val() == "") {
            setButtonStatus(objIdPrefix + "ImgSelect", false);
            $('#oper_result_label').html("尚未选择要上传的图片！");
            blockUIOpen('operResultWin');
        } else {
            $.ajaxFileUpload({
                url : ctx + "/fileImageUpload.do",
                secureuri : false,
                fileElementId : objIdPrefix + "Img",
                dataType : 'json',
                success: function (data, status){
                    if (data.status == "success") {
                        seterror("material_file", "", true);
                        $("#" + objIdPrefix + "ImgId").val(data.data);
                        $("#" + objIdPrefix + "ImgSuccess").html("上传成功，图片地址：<BR>" + hostNameSuffix + data.imgRelativeUrl);
                        alert("上传成功！");
                    } else {
                        $('#oper_result_label').html(data.data);
                        blockUIOpen('operResultWin');
                    }
                    setButtonStatus(objIdPrefix + "ImgSelect", false);
                },
                error: function (data, status, e){
                    setButtonStatus(objIdPrefix + "ImgSelect", false);
                }
            });
        }
    });

    //视频 附件上传
    $("input[id*='VideoUpload']").live("click",function() {
        var objId = $(this).attr("id");
        var objIdPrefix = objId.replace("VideoUpload", "");
        setButtonStatus(objIdPrefix + "VideoSelect", true);
        if($("#" + objIdPrefix + "Video").val() == "") {
            setButtonStatus(objIdPrefix + "VideoSelect", false);
            $('#oper_result_label').html("尚未选择要上传的视频！");
            blockUIOpen('operResultWin');
        } else {
            $.ajaxFileUpload({
                url : ctx + "/videoUpload.do",
                secureuri : false,
                fileElementId : objIdPrefix + "Video",
                dataType : 'json',
                success: function (data, status){
                    if (data.status == "success") {
                        seterror("material_file", "", true);
                        $("#" + objIdPrefix + "VideoId").val(data.data);
                        $("#" + objIdPrefix + "VideoSuccess").html("上传成功，视频地址：<BR>" + hostNameSuffix + data.fileRelativeUrl);
                        alert("上传成功！");
                    } else {
                        $('#oper_result_label').html(data.data);
                        blockUIOpen('operResultWin');
                    }
                    setButtonStatus(objIdPrefix + "VideoSelect", false);
                },
                error: function (data, status, e){
                    setButtonStatus(objIdPrefix + "VideoSelect", false);
                }
            });
        }
    });

    //点击新增资源
    $('#addNewMaterialInfo').click(function() {
        seterror("material_file", "", true);
        $('#material_name').val(""); $('#material_name').attr("class", "width400");
        $("#material_name_error").show(); $("#material_name_error").html("");
        $('#material_type').val(1); $('#material_type').attr("class", "u_select");
        $("#material_type").prev().html("图片");
        $("#materialFileImgSuccess").html("");
        $("#materialFileImgId").val("");
        $("#materialFileImg").val("");
        $("#materialFileVideoSuccess").html("");
        $("#materialFileVideoId").val("");
        $("#materialFileVideo").val("");
        $('#uploadImgFile').show();
        $('#uploadVideoFile').hide();
        $('#material_desc').val(""); $('#material_desc').attr("class", "width400");
        $("#material_desc_error").show(); $("#material_desc_error").html("");
        blockUIOpen('addNewMaterialInfoWin');
        setButtonStatus("saveNewMaterialInfo", false); //设置按钮不可用
    });

    //保存新增加的资源信息
    $('#saveNewMaterialInfo').live("click", function() {
        setButtonStatus("saveNewMaterialInfo", true); //设置按钮不可用
        $('#material_name').blur();
        $('#material_desc').blur();
        seterror("material_file", "", true);
        var is_error = false;
        var _error = ["material_name", "data_value"];
        $.each(_error, function(key, val) {
            var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
                if (error_flag.indexOf("wrong") >= 0) {
                    is_error = true;
                    return false;
                }
        });

        var attachId = "";

        if ($('#material_type').val() == 1) {
            if ($('#materialFileImgId').val() == "") {
                is_error = true;
                seterror("material_file", "请上传图片文件", false);
            } else {
                attachId = $('#materialFileImgId').val();
            }
        }

        if ($('#material_type').val() == 2) {
            if ($('#materialFileVideoId').val() == "") {
                is_error = true;
                seterror("material_file", "请上传视频文件", false);
            } else {
                attachId = $('#materialFileVideoId').val();
            }
        }

        if (is_error) {
            setButtonStatus("saveNewMaterialInfo", false); //设置按钮可用
            return false;
        }
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/saveNewMaterial.do',
            dataType : 'json',
            data : {
                materialName : $('#material_name').val(),
                materialType : $('#material_type').val(),
                attachId : attachId,
                materialDesc : $('#material_desc').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status != "error") {
                        $('#oper_result_label').html(data.data + "页面即将刷新！");
                        blockUIOpen('operResultWin');
                        reloadPageWithDelay("", "", "/materialManage.do", "");
                    } else {
                        $.each(data.data, function(i, item) {
                            seterror(item.key, item.value, false);
                        });
                        setButtonStatus("saveNewMaterialInfo", false); //设置按钮可用
                    }
                }
            },
            error : function(data) {}
        });
    });

    //查看资源信息
    $("a[id*='showMaterialInfo']").on('click', function() {
        var objId = $(this).attr("id");
        var dataId = objId.replace("showMaterialInfo_", "");
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/showMaterial.do',
            dataType : 'json',
            data : {
                dataId : dataId
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#label_material_name').html(data.data.materialName);
                    $('#label_material_type').html(data.data.materialTypeDesc);
                    $('#label_material_attach_url').html(data.data.attachFileUrl);
                    $('#label_material_desc').html(data.data.materialDesc);
                    $('#label_material_create_time').html(data.data.createTime);
                    $('#label_material_create_userName').html(data.data.createUserName);
                    $('#label_material_update_time').html(data.data.updateTime);
                    $('#label_material_update_userName').html(data.data.updateUserName);
                    blockUIOpen('showMaterialInfoWin');
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //修改资源信息 将资源信息铺回到页面
    $("a[id*='editMaterialInfo']").on('click', function() {
        var objId = $(this).attr("id");
        var dataId = objId.replace("editMaterialInfo_", "");
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/showMaterial.do',
            dataType : 'json',
            data : {
                dataId : dataId
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#change_material_id').val(dataId);
                    $('#change_material_name').val(data.data.materialName);
                    $('#change_material_type').val(data.data.materialType);
                    $("#change_material_type").prev().html($("#change_material_type").find("option:selected").text());
                    if (data.data.materialType == 1) {
                        $('#change_uploadImgFile').show();
                        $('#change_uploadVideoFile').hide();
                        $('#change_materialFileImgId').val(data.data.attachId);
                        $('#change_materialFileImgSuccess').html("图片地址：<BR>" + data.data.attachFileUrl);
                        $('#change_materialFileVideo').val("");
                        $('#change_materialFileVideoId').val("");
                        $('#change_materialFileVideoSuccess').html("");
                    } else {
                        $('#change_uploadImgFile').hide();
                        $('#change_uploadVideoFile').show();
                        $('#change_materialFileVideoId').val(data.data.attachId);
                        $('#change_materialFileVideoSuccess').html("视频地址：<BR>" + data.data.attachFileUrl);
                        $('#change_materialFileImg').val("");
                        $('#change_materialFileImgId').val("");
                        $('#change_materialFileImgSuccess').html("");
                    }
                    $('#change_material_desc').val(data.data.materialDesc);
                    $('#change_material_name').blur();
                    $('#change_material_desc').blur();
                    seterror("change_material_file", "", true);
                    blockUIOpen('editMaterialInfoWin');
                    setButtonStatus("saveChangeMaterialInfo", false); //设置按钮可用
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //点击删除资源的按钮
    $("a[id*='removeMaterialInfo']").on('click', function() {
        var objId = $(this).attr("id");
        var dataId = objId.replace("removeMaterialInfo_", "");
        $('#remove_alert_info').html("是否要删除资源“" + $("#show_material_name_" + dataId).html() + "”？");
        $('#remove_material_id').val(dataId);
        blockUIOpen('removeMaterialInfoConfirm');
        setButtonStatus("yesRemoveMaterialInfo", false); //设置按钮可用
    });

    //删除资源
    $('#yesRemoveMaterialInfo').live("click", function() {
        setButtonStatus("yesRemoveMaterialInfo", true); //设置按钮不可用
        $.blockUIClose;
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/removeMaterial.do',
            dataType : 'json',
            data : {
                dataId : $('#remove_material_id').val()
            },
            success : function(data) {
                if (data && data.status == "success") {
                    $('#oper_result_label').html(data.data + "页面即将刷新！");
                    blockUIOpen('operResultWin');
                    reloadPageWithDelay("", "", "/materialManage.do", "");
                } else {
                    $('#oper_result_label').html(data.data);
                    blockUIOpen('operResultWin');
                }
            },
            error : function(data) {}
        });
    });

    //保存所修改的资源信息
    $('#saveChangeMaterialInfo').live("click", function() {
        setButtonStatus("saveChangeMaterialInfo", true); //设置按钮不可用
        $('#change_material_name').blur();
        $('#change_material_desc').blur();
        seterror("change_material_file", "", true);
        var is_error = false;
        var _error = ["change_material_name", "change_data_value", "change_material_desc"];
        $.each(_error, function(key, val) {
            var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
                if (error_flag.indexOf("wrong") >= 0) {
                    is_error = true;
                    return false
                }
        });

        var attachId = "";

        if ($('#change_material_type').val() == 1) {
            if ($('#change_materialFileImgId').val() == "") {
                is_error = true;
                seterror("change_material_file", "请上传图片文件", false);
            } else {
                attachId = $('#change_materialFileImgId').val();
            }
        }

        if ($('#change_material_type').val() == 2) {
            if ($('#change_materialFileVideoId').val() == "") {
                is_error = true;
                seterror("change_material_file", "请上传视频文件", false);
            } else {
                attachId = $('#change_materialFileVideoId').val();
            }
        }

        if (is_error) {
            setButtonStatus("saveChangeMaterialInfo", false); //设置按钮可用
            return false;
        }
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/changeMaterial.do',
            dataType : 'json',
            data : {
                id : $('#change_material_id').val(),
                materialName : $('#change_material_name').val(),
                materialType : $('#change_material_type').val(),
                attachId : attachId,
                materialDesc : $('#change_material_desc').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status != "error") {
                        $('#oper_result_label').html(data.data + "页面即将刷新！");
                        blockUIOpen('operResultWin');
                        reloadPageWithDelay("", "", "/materialManage.do", "");
                    } else {
                        $.each(data.data, function(i, item) {
                            seterror(item.key, item.value, false);
                        });
                        setButtonStatus("saveChangeMaterialInfo", false); //设置按钮可用
                    }
                }
            },
            error : function(data) {}
        });
    });

    //资源名称校验
    $('#material_name').change(function() {
        checkMaterialName(this);
    });
    $('#material_name').blur(function() {
        checkMaterialName(this);
    });

    //资源名称校验
    $('#change_material_name').change(function() {
        checkMaterialName(this);
    });
    $('#change_material_name').blur(function() {
        checkMaterialName(this);
    });

    //资源备注校验
    $('#material_desc').change(function() {
        checkMaterialDesc(this);
    });
    $('#material_desc').blur(function() {
        checkMaterialDesc(this);
    });

    //资源备注校验
    $('#change_material_desc').change(function() {
        checkMaterialDesc(this);
    });
    $('#change_material_desc').blur(function() {
        checkMaterialDesc(this);
    });

});

//==========================================
//Purpose: 验证资源名称
//==========================================
function checkMaterialName(obj) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "请输入资源名称", false);
        return false;
    } else if ($(obj).val().length > 25) {
        seterror(objId, "资源名称不能超过25个字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证备注的输入
//==========================================
function checkMaterialDesc(obj) {
    var objId = $(obj).attr("id");
    checkEmpty(obj);
    if ($(obj).val() != null && $(obj).val() != "" && $(obj).val().length > 120) {
        seterror(objId, "资源描述限制为120字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}
