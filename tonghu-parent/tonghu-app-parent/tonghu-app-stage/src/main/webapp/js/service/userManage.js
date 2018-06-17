/**
 * 用户管理JS代码
 * author: liangyongjian
 * createDate: 2013-10-19 19:24:45
 */
$(document).ready(function() {
	
	//点击新增用户
	$('#addNewUser').click(function() {
		$('#user_name').val(""); $('#user_name').attr("class", "width130");
		$('#true_name').val(""); $('#true_name').attr("class", "width130"); 
		$('#user_pass').val(""); $('#user_pass').attr("class", "width130");
        $('#confirm_user_pass').val(""); $('#confirm_user_pass').attr("class", "width130");
		$('#user_email').val(""); $('#user_email').attr("class", "width220");
		$('#user_lock').val(0); $('#user_lock').attr("class", "u_select");
        $("#user_lock").prev().html("正常");
        $('#user_role').val(-1); $('#user_role').attr("class", "u_select");
        $("#user_role").prev().html("请选择角色");
		$('#user_mobile').val(""); $('#user_mobile').attr("class", "width130");
		$('#user_phone').val(""); $('#user_phone').attr("class", "width130"); 
		$('#user_note').val(""); $('#user_note').attr("class", "width220"); 
		$("#user_name_error").show(); $("#user_name_error").html("");
		$("#user_pass_error").show(); $("#user_pass_error").html("");
        $("#confirm_user_pass_error").show(); $("#confirm_user_pass_error").html("");
		$("#true_name_error").show(); $("#true_name_error").html("");
		$("#user_email_error").show(); $("#user_email_error").html("");
		$("#user_mobile_error").show(); $("#user_mobile_error").html("");
		$("#user_phone_error").show(); $("#user_phone_error").html("");
		$("#user_note_error").show(); $("#user_note_error").html("");
		blockUIOpen('addNewUserWin');
		setButtonStatus("saveNewUser", false); //设置按钮可用
	});
	
	//保存新增加的用户信息
	$('#saveNewUser').live("click", function() {
		setButtonStatus("saveNewUser", true); //设置按钮不可用
		$('#user_name').blur();
		$('#true_name').blur();
		$('#user_pass').blur();
		$('#user_email').blur();
		$('#user_lock').blur();
        $('#user_role').blur();
		$('#user_mobile').blur();
		$('#user_phone').blur();
		$('#user_note').blur(); 
		var is_error = false;
		var _error = ["user_name", "true_name", "user_pass", "user_email", "user_mobile", "user_phone", "user_note"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false;
                }
        });
        if (is_error) {
        	setButtonStatus("saveNewUser", false); //设置按钮可用
        	return false;
        }

        if ($('#user_role_error').html() != null && $('#user_role_error').html() != "") {
            setButtonStatus("saveNewUser", false); //设置按钮可用
            return false;
		}

		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/saveNewUser.do',
		    dataType : 'json',
		    data : {
		    	userName : $('#user_name').val(),
		    	password : $('#user_pass').val(),
		    	trueName : $('#true_name').val(),
		    	userEmail : $('#user_email').val(),
		    	isLock : $('#user_lock').val(),
                roleId : $('#user_role').val(),
		    	userMobile : $('#user_mobile').val(),
		    	userPhone : $('#user_phone').val(),
		    	note : $('#user_note').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			$('#oper_result_label').html(data.data + "页面即将刷新！");
			    		blockUIOpen('operResultWin');
			    		reloadCurrentPage($("#pageTotal").val());
					} else {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		            	});
		            	setButtonStatus("saveNewUser", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//查看用户信息
    $("a[id*='showUser']").on('click', function() {
		var objId = $(this).attr("id");
		var userId = objId.replace("showUser_", "");
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/showUser.do',
		    dataType : 'json',
		    data : {
		    	userId : userId
		    },
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#users_label_user_name').html(data.data.userName);
		    		$('#users_label_true_name').html(data.data.trueName);
		    		$('#users_label_user_email').html(data.data.userEmail);
		    		$('#users_label_lock').html(data.data.lockStr);
                    $('#users_label_role').html(data.data.roleName);
		    		$('#users_label_user_mobile').html(data.data.userMobile);
		    		$('#users_label_user_phone').html(data.data.userPhone);
		    		$('#users_label_create_time').html(data.data.createTime);
		    		$('#users_label_create_userName').html(data.data.createUserName);
		    		$('#users_label_update_time').html(data.data.updateTime);
		    		$('#users_label_update_userName').html(data.data.updateUserName);
		    		$('#users_label_note').html(data.data.note);
		    		blockUIOpen('userInfoWin');
				} else {
					$('#oper_result_label').html(data.data);
			    	blockUIOpen('operResultWin');
				}
		    },
		    error : function(data) {}
		});
	});
	
	//修改用户信息 将用户信息铺回到页面
    $('#updateUserInfo').on('click', function() {
		var userId = $("#user_info_change_id").val();
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/showUser.do',
		    dataType : 'json',
		    data : {
		    	userId : userId
		    },
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#users_change_user_id').val(userId);
		    		$('#users_change_user_name').val(data.data.userName);
		    		$('#users_change_true_name').val(data.data.trueName);
		    		$('#users_change_user_email').val(data.data.userEmail);
		    		$('#users_change_user_lock').val(data.data.isLock);
		    		$("#users_change_user_lock").prev().html($("#users_change_user_lock").find("option:selected").text());
                    $('#users_change_user_role').val(data.data.roleId);
                    $("#users_change_user_role").prev().html($("#users_change_user_role").find("option:selected").text());
		    		$('#users_change_user_mobile').val(data.data.userMobile);
		    		$('#users_change_user_phone').val(data.data.userPhone);
		    		$('#users_change_user_note').val(data.data.note);
		    		$('#users_change_user_name').blur();
					$('#users_change_true_name').blur();
					$('#users_change_user_email').blur();
					$('#users_change_user_lock').blur();
					$('#users_change_user_mobile').blur();
					$('#users_change_user_phone').blur();
					$('#users_change_user_note').blur(); 
		    		blockUIOpen('changeUserWin');
		    		setButtonStatus("saveChangeUser", false); //设置按钮可用
				} else {
					$('#oper_result_label').html(data.data);
			    	blockUIOpen('operResultWin');
				}
		    },
		    error : function(data) {}
		});
    });
	
	//修改用户信息 将用户信息铺回到页面
    $("a[id*='updateUser']").on('click', function() {
    	var objId = $(this).attr("id");
		$("#user_info_change_id").val(objId.replace("updateUser_", ""));
        $("#user_info_change_name").val($(this).attr("userName"));
        $("#forEditorTr").hide();
		blockUIOpen('userInfoChangeWin');
	});

    //修改用户信息 将用户信息铺回到页面
    $("a[id*='updateEditorUser']").on('click', function() {
        var objId = $(this).attr("id");
        $("#user_info_change_id").val(objId.replace("updateEditorUser_", ""));
        $("#user_info_change_name").val($(this).attr("userName"));
        $("#forEditorTr").show();
        blockUIOpen('userInfoChangeWin');
    });
	
	//打开修改用户密码的窗口
	$('#changeUserPassInfo').live("click", function() {
	    $('#users_change_user_pass').val(""); $('#users_change_user_pass').attr("class", "width130");
	    $('#users_change_user_pass_error').show(); $("#users_change_user_pass_error").html("");
	    $('#users_change_confirm_user_pass').val(""); $('#users_change_confirm_user_pass').attr("class", "width130");
	    $('#users_change_confirm_user_pass_error').show(); $("#users_change_confirm_user_pass_error").html("");
	    $("#user_pass_change_id").val($("#user_info_change_id").val());
        $("#user_pass_change_name").html($("#user_info_change_name").val());
        blockUIOpen('changeUserPassInfoWin');
	});
	
	//修改密码
	$('#saveUserPassInfo').on('click', function() {
		setButtonStatus("saveUserPassInfo", true); //设置按钮不可用
		var userId = $("#user_pass_change_id").val();
		var is_error = false;
		var _error = ["users_change_user_pass", "users_change_confirm_user_pass"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false
                }
        });
        if (is_error) {
        	setButtonStatus("saveUserPassInfo", false); //设置按钮可用
        	return false;
        }
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/changeUserPass.do',
		    dataType : 'json',
		    data : {
		    	id : userId,
		    	password : $('#users_change_user_pass').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			$('#oper_result_label').html(data.data + "页面即将刷新！");
			    		blockUIOpen('operResultWin');
			    		reloadCurrentPage($("#pageNumInput").val());
					} else {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		            	});
		            	setButtonStatus("saveUserPassInfof", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
    });
	
	//点击删除用户的按钮
	$("a[id*='removeUser']").on('click', function() {
		var objId = $(this).attr("id");
		var userId = objId.replace("removeUser_", "");
		$('#remove_alert_info').html("是否要删除用户“" + $("#show_user_" + userId).html() + "”？");
		$('#remove_user_id').val(userId);
		blockUIOpen('removeUserConfirm');
		setButtonStatus("yesRemoveUser", false); //设置按钮可用
	});
	
	//删除用户
	$('#yesRemoveUser').live("click", function() {
		setButtonStatus("yesRemoveUser", true); //设置按钮不可用
		$.blockUIClose;
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/removeUser.do',
		    dataType : 'json',
		    data : {
		    	userId : $('#remove_user_id').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			if (data.status == "failure" && data.fresh == "false") {
		    				$('#oper_result_label').html(data.data);
			    			blockUIOpen('operResultWin');
		    			} else {
		    				$('#oper_result_label').html(data.data + "页面即将刷新！");
			    			blockUIOpen('operResultWin');
			    			reloadCurrentPage($("#pageNumInput").val());
		    			}
					} else {
						$('#oper_result_label').html(data.data);
			    		blockUIOpen('operResultWin');
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//保存所修改的用户信息
	$('#saveChangeUser').live("click", function() {
		setButtonStatus("saveChangeUser", true); //设置按钮不可用
		$('#users_change_user_name').blur();
		$('#users_change_true_name').blur();
		$('#users_change_user_email').blur();
		$('#users_change_user_lock').blur();
		$('#users_change_user_mobile').blur();
		$('#users_change_user_phone').blur();
		$('#users_change_user_note').blur(); 
		var is_error = false;
		var _error = ["users_change_user_name", "users_change_true_name", "users_change_user_email", 
				"users_change_user_lock", "users_change_user_mobile", "users_change_user_phone", 
				"users_change_user_note"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false
                }
        });
        if (is_error) {
        	setButtonStatus("saveChangeUser", false); //设置按钮可用
        	return false;
        }
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/changeUser.do',
		    dataType : 'json',
		    data : {
		    	id : $('#users_change_user_id').val(),
		    	userName : $('#users_change_user_name').val(),
		    	trueName : $('#users_change_true_name').val(),
		    	userEmail : $('#users_change_user_email').val(),
		    	isLock : $('#users_change_user_lock').val(),
                roleId : $('#users_change_user_role').val(),
		    	userMobile : $('#users_change_user_mobile').val(),
		    	userPhone : $('#users_change_user_phone').val(),
		    	note : $('#users_change_user_note').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			if (data.status == "failure" && data.fresh == "false") {
		    				$('#oper_result_label').html(data.data);
			    			blockUIOpen('operResultWin');
		    			} else {
		    				$('#oper_result_label').html(data.data + "页面即将刷新！");
			    			blockUIOpen('operResultWin');
			    			reloadCurrentPage($("#pageNumInput").val());
		    			}
					} else {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		            	});
		            	setButtonStatus("saveChangeUser", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//打开修改用户密码的窗口
	$('#setUserRole').live("click", function() {
		$('#change_user_role').attr("class", "u_select");
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/getUserRole.do',
		    dataType : 'json',
		    data : {
		    	userId : $("#user_info_change_id").val()
		    },
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#change_user_role').val(data.data);
		    		$("#change_user_role").prev().html($("#change_user_role").find("option:selected").text());
		    		$('#change_user_role_error').show(); $("#change_user_role_error").html("");
	    			$("#user_role_change_id").val($("#user_info_change_id").val());
                    $("#user_role_change_name").html($("#user_info_change_name").val());
        			blockUIOpen('changeUserRoleInfoWin');
		    		setButtonStatus("saveUserRoleInfo", false); //设置按钮可用
				} else {
					$('#oper_result_label').html(data.data);
			    	blockUIOpen('operResultWin');
				}
		    },
		    error : function(data) {}
		});
	});
	
	//保存所修改的用户信息
	$('#saveUserRoleInfo').live("click", function() {
		setButtonStatus("saveUserRoleInfo", true); //设置按钮不可用
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/changeUserRole.do',
		    dataType : 'json',
		    data : {
		    	userId : $('#user_role_change_id').val(),
		    	roleId : $('#change_user_role').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			if (data.status == "failure" && data.fresh == "false") {
		    				$('#oper_result_label').html(data.data);
			    			blockUIOpen('operResultWin');
		    			} else {
		    				$('#oper_result_label').html(data.data + "页面即将刷新！");
			    			blockUIOpen('operResultWin');
			    			reloadCurrentPage($("#pageNumInput").val());
		    			}
					} else {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		            	});
		            	setButtonStatus("saveUserRoleInfo", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//用户名校验
	$('#user_name').change(function() {
        checkUserName(this);
    });
    $('#user_name').blur(function() {
        checkUserName(this);
    });
    $('#users_change_user_name').change(function() {
        checkUserName(this);
    });
    $('#users_change_user_name').blur(function() {
        checkUserName(this);
    });
    
    //用户登录密码校验
	$('#user_pass').change(function() {
        checkUserPassword(this);
    });
    $('#user_pass').blur(function() {
        checkUserPassword(this);
    });
    $('#users_change_user_pass').change(function() {
        checkUserPassword(this);
    });
    $('#users_change_user_pass').blur(function() {
        checkUserPassword(this);
    });
    
    //用户确认密码校验
    $('#confirm_user_pass').change(function() {
        checkConfirmUserPass(this);
    });
    $('#confirm_user_pass').blur(function() {
        checkConfirmUserPass(this);
    });
    $('#users_change_confirm_user_pass').change(function() {
        checkChangeConfirmUserPass(this);
    });
    $('#users_change_confirm_user_pass').blur(function() {
        checkChangeConfirmUserPass(this);
    });
    
    //用户真实姓名校验
	$('#true_name').change(function() {
        checkIsNull(this, "真实姓名");
    });
    $('#true_name').blur(function() {
        checkIsNull(this, "真实姓名");
    });
    $('#users_change_true_name').change(function() {
        checkIsNull(this, "真实姓名");
    });
    $('#users_change_true_name').blur(function() {
        checkIsNull(this, "真实姓名");
    });
    
    //用户邮件校验
	$('#user_email').change(function() {
        checkUserEmail(this);
    });
    $('#user_email').blur(function() {
        checkUserEmail(this);
    });
    $('#users_change_user_email').change(function() {
        checkUserEmail(this);
    });
    $('#users_change_user_email').blur(function() {
        checkUserEmail(this);
    });
    
    //用户状态校验
	$('#user_lock').change(function() {
        checkUserLock(this);
    });
    $('#user_lock').blur(function() {
        checkUserLock(this);
    });
    $('#users_change_user_lock').change(function() {
        checkUserLock(this);
    });
    $('#users_change_user_lock').blur(function() {
        checkUserLock(this);
    });

    //用户角色校验
    $('#user_role').change(function() {
        checkUserRole(this);
    });
    $('#user_role').blur(function() {
        checkUserRole(this);
    });
    $('#users_change_user_role').change(function() {
        checkUserRole(this);
    });
    $('#users_change_user_role').blur(function() {
        checkUserRole(this);
    });
    
    //用户手机号码校验
	$('#user_mobile').change(function() {
        checkUserMobile(this);
    });
    $('#user_mobile').blur(function() {
        checkUserMobile(this);
    });
    $('#users_change_user_mobile').change(function() {
        checkUserMobile(this);
    });
    $('#users_change_user_mobile').blur(function() {
        checkUserMobile(this);
    });
    
    //座机号码校验
	$('#user_phone').change(function() {
        checkUserPhone(this);
    });
    $('#user_phone').blur(function() {
        checkUserPhone(this);
    });
    $('#users_change_user_phone').change(function() {
        checkUserPhone(this);
    });
    $('#users_change_user_phone').blur(function() {
        checkUserPhone(this);
    });
    
    //用户备注校验
	$('#user_note').change(function() {
        checkUserNote(this);
    });
    $('#user_note').blur(function() {
        checkUserNote(this);
    });
    $('#users_change_user_note').change(function() {
        checkUserNote(this);
    });
    $('#users_change_user_note').blur(function() {
        checkUserNote(this);
    });

    // 为编辑者用户分配项目
    $("input[id='setUserProject']").on('click', function() {
		// 先找到用户能够编辑的项目集合
        $("#user_project_change_id").val($("#user_info_change_id").val());
        $("#user_project_change_name").html($("#user_info_change_name").val());
        $.setEmptyUserProjectWin();
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/getUserProjectInfo.do',
            dataType : 'json',
            data : {
                userId : $('#user_info_change_id').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status == "success"){
                    	if (data.data.isAll == 1) {
                            $("#previewAllCountryItem").html('<input type="checkbox" name="selectedAllCountry" value="allCountry" id="allCountry" checked>全国所有项目');
                            $("#previewAllCountryItem").show();
                            $("input[name='allCountry']").attr("checked", true);
                        } else {
							if (data.data.provinceList != null && data.data.provinceList.length > 0) {
								var pHtmlStr = "";
                                $.each(data.data.provinceList, function(i, item) {
                                    pHtmlStr += '<input type="checkbox" name="selectAllProvince" id="'+item.id+'" value="'+item.province+'" checked>'+item.province+'所有项目';
                                    pHtmlStr += "<br/>";
                                });
                                $("#previewAllProvinceItem").html(pHtmlStr);
                                $("#previewAllProvinceItem").show();
							}

                            if (data.data.cityList != null && data.data.cityList.length > 0) {
                                var cHtmlStr = "";
                                $.each(data.data.cityList, function(i, item) {
                                    cHtmlStr += '<input type="checkbox" name="selectAllCity" id="'+item.id+'" provinceId="'+item.provinceId+'" value="'+item.city+'" checked>'+item.city+'市所有项目';
                                    cHtmlStr += "<br/>";
                                });
                                $("#previewAllCityItem").html(cHtmlStr);
                                $("#previewAllCityItem").show();
                            }

                            if (data.data.countyLevelCityList != null && data.data.countyLevelCityList.length > 0) {
                                var clcHtmlStr = "";
                                $.each(data.data.countyLevelCityList, function(i, item) {
                                    clcHtmlStr += '<input type="checkbox" name="selectAllCountyLevelCity" id="'+item.countyPinyin+'" provinceId="'+item.provinceId+'" cityId="'+item.cityId+'" value="'+item.countyLevelCity+'" checked>'+item.countyLevelCity+'县级市所有项目';
                                    clcHtmlStr += "<br/>";
                                });
                                $("#previewAllCountyLevelCityItem").html(clcHtmlStr);
                                $("#previewAllCountyLevelCityItem").show();
                            }

                            if (data.data.projectList != null && data.data.projectList.length > 0) {
                                var proHtmlStr = "";
                                $.each(data.data.projectList, function(i, item) {
                                    proHtmlStr += '<input type="checkbox" checked name="selectedProjectInput" id="' + item.id + '" projectName="' + item.projectName + '" provinceId="'+item.provinceId+'" cityId="'+item.cityId+'" countyLevelCity="'+item.countyLevelCity+'">' + item.projectName;
                                    proHtmlStr += "<br/>";
                                });
                                $("#previewProjectItem").html(proHtmlStr);
                                $("#previewProjectItem").show();
                            }
						}
                        blockUIOpen('setUserProjectWin');
                    } else {
                        $('#oper_result_label').html(data.data);
                        blockUIOpen('operResultWin');
                    }
                }
            },
            error : function(data) {
                $('#oper_result_label').html("操作失败，出现异常！");
                blockUIOpen('operResultWin');
                setButtonStatus("cancelCommitUserProject", false); //设置按钮可用
                setButtonStatus("commitUserProject", false); //设置按钮可用
            }
        });
    });

    //搜索框：点击城市搜索框后 查看此城市下是否有县级市
    $('#citySearch').change(function() {
        if ($('#citySearch').val() == -1) {
            setEmpty("countyLevelCitySelectSearch", "请选择县级市");
            $("#countyLevelCitySelectSearchSpan").hide("fast");
            return;
        }
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/cityChangeCountyLevelCity.do',
            dataType : 'json',
            data : {
                cityId : $('#citySearch').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status == "success" && data.countyLevelCityMap != null) {
						//联动省份选择框
                        setEmpty("countyLevelCitySelectSearch", "请选择县级市");
                        for(var key in data.countyLevelCityMap) {
                            $("#countyLevelCitySelectSearch").append('<option value="' + key + '">' + data.countyLevelCityMap[key] + '</option>');
                        }
                        $("#countyLevelCitySelectSearchSpan").show("fast");
                    } else {
                        setEmpty("countyLevelCitySelectSearch", "请选择县级市");
                        $("#countyLevelCitySelectSearchSpan").hide("fast");
                    }
                }
            },
            error : function(data) {}
        });
    });

    // 查询项目
    $("input[id='queryProject']").on('click', function() {
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/getProjectInfoForAssignProject.do',
            dataType : 'json',
            data : {
                provinceId : $('#provinceSearch').val(),
                cityId : $('#citySearch').val(),
                countyPinyin : $('#countyLevelCitySelectSearch').val()
            },
            success : function(data) {
                if (data) {
                    if (data.status == "success") {
                        var htmlStr	 = '';

                        var isAllCountry = false;
                        var allCountryInputs = $("#previewAllCountryItem").find("input");
                        for(var i = 0 ; i < allCountryInputs.length ; i++) {
                            if(allCountryInputs[i].checked == true && allCountryInputs[i].value == "allCountry") {
                                isAllCountry = true;
                                break;
                            }
                        }

                        // 已选择的省份
                        var provinceInputMap = new Map();
                        var provinceInputs = $("#previewAllProvinceItem").find("input");
                        for(var i = 0 ; i < provinceInputs.length ; i++) {
                            if(provinceInputs[i].checked == true) {
                                provinceInputMap.put(provinceInputs[i].id, "");
                            }
                        }

                        // 已选择的城市
                        var cityInputMap = new Map();
                        var cityInputs = $("#previewAllCityItem").find("input");
                        for(var i = 0 ; i < cityInputs.length ; i++) {
                            if(cityInputs[i].checked == true) {
                                cityInputMap.put(cityInputs[i].id, "");
                            }
                        }

                        // 已选择的县级市
                        var countyLevelCityInputMap = new Map();
                        var countyLevelCityInputs = $("#previewAllCountyLevelCityItem").find("input");
                        for(var i = 0 ; i < countyLevelCityInputs.length ; i++) {
                            if(countyLevelCityInputs[i].checked == true) {
                                countyLevelCityInputMap.put(countyLevelCityInputs[i].id, "");
                            }
                        }

                        var projectDisabledStr = "", projectCheckedStr = "";
                        var provinceDisabledStr = "", provinceCheckedStr = "";
                        var cityDisabledStr = "", cityCheckedStr = "";
                        var countyLevelCityDisabledStr = "", countyLevelCityCheckedStr = "";
						if (isAllCountry == true) {
                            provinceDisabledStr = "disabled";
                            provinceCheckedStr = "checked";
                            cityDisabledStr = "disabled";
                            cityCheckedStr = "checked";
                            projectDisabledStr = "disabled";
                            projectCheckedStr = "checked";
                            countyLevelCityDisabledStr = "disabled";
                            countyLevelCityCheckedStr = "checked";
                        }

                        $("#projectItemInputDiv").hide();
                        if (data.projectList != null && data.projectList.length > 0) {
                            var projectInputMap = new Map();
                            var projectInputs = $("#previewProjectItem").find("input");
                            for(var i = 0 ; i < projectInputs.length ; i++) {
                                if(projectInputs[i].checked == true) {
                                    projectInputMap.put(projectInputs[i].id, "");
                                }
                            }

                            var projectInfoArray = new Array();

                            $.each(data.projectList, function(i, item) {
                                projectInfoArray.push(item);
                            });

                            projectInfoArray.sort(function(a, b) {
                                var aPrId = a.provinceId == null ? 100000 : a.provinceId;
                                var bPrId = b.provinceId == null ? 100000 : b.provinceId;
                                if (parseInt(aPrId) - parseInt(bPrId) != 0) {
                                    return parseInt(aPrId) - parseInt(bPrId);
                                }
                                var aCId = a.cityId == null ? 100000 : a.cityId;
                                var bCId = b.cityId == null ? 100000 : b.cityId;
                                if (parseInt(aCId) - parseInt(bCId) != 0) {
                                    return parseInt(aCId) - parseInt(bCId);
                                }
                                var aClc = a.countyLevelCity;
                                var bClc = b.countyLevelCity;
                                if (aClc != bClc) {
                                    return aClc - bClc;
                                }
                                return parseInt(a.id) - parseInt(b.id);
                            });

                            $.each(projectInfoArray, function(i, item) {
                                var itemProvinceId = item.provinceId == null ? '' : item.provinceId;
                                var itemCityId = item.cityId == null ? '' : item.cityId;
                                var itemCountyLevelCityId = item.countyPinyin == null ? '' : item.countyPinyin;
                            	if (isAllCountry == false) {
                                    projectCheckedStr = "";
                                    projectDisabledStr = "";
                            		if (provinceInputMap.containsKey(itemProvinceId)
										|| cityInputMap.containsKey(itemCityId)
										|| countyLevelCityInputMap.containsKey(itemCountyLevelCityId)) {
                                        projectCheckedStr = "checked";
                                        projectDisabledStr = "disabled";
									} else {
                                        projectCheckedStr = "";
                                        projectDisabledStr = "";
                                        if (projectInputMap.containsKey(item.id)) {
                                            projectCheckedStr = "checked";
                                        }
									}
								}
                                htmlStr += '<span id="' + item.id + '">';
                                htmlStr += '<input type="checkbox" name="projectInput" id="' + item.id + '" projectName="' + item.projectName + '" '+projectDisabledStr+' '+projectCheckedStr+' provinceId="'+itemProvinceId+'" cityId="'+itemCityId+'" countyLevelCityId="'+item.countyPinyin+'">' + item.projectName;
                                htmlStr += '</span>';
                            });

                        } else {
                            htmlStr = '<span><label>没有符合条件的项目</label></span>';
                        }
                        $("#projectItemInputDiv").html(htmlStr);
                        $("#projectItemInputDiv").show("fast");

                        var area = "";
                        if ($('#provinceSearch').val() != -1) {

                        	var searchProvinceId = $('#provinceSearch').val();
                        	var searchProvinceName = $("#provinceSearch").find("option:selected").text();
                            if (isAllCountry == false) {
                                if (provinceInputMap.containsKey(searchProvinceId)) {
                                    provinceCheckedStr = "checked";
                                    cityCheckedStr = "checked";
                                    cityDisabledStr = "disabled";
                                    countyLevelCityCheckedStr = "checked";
                                    countyLevelCityDisabledStr = "disabled";
                                }
                            }
                            area += '<INPUT type="checkbox" name="allProvince" id="'+searchProvinceId+'" value="'+searchProvinceName+'" '+provinceDisabledStr+' '+provinceCheckedStr+'>'+searchProvinceName+'所有项目';
                            if ($('#citySearch').val() != -1) {
                            	var searchCityId = $('#citySearch').val();
                            	var searchCityName = $("#citySearch").find("option:selected").text();
                            	if (cityInputMap.containsKey(searchCityId)) {
                                    cityCheckedStr = "checked";
                                    countyLevelCityCheckedStr = "checked";
                                    countyLevelCityDisabledStr = "disabled";
                                }
                                area += '<INPUT type="checkbox" name="allCity" id="'+searchCityId+'" value="'+searchCityName+'" '+cityDisabledStr+' '+cityCheckedStr+' provinceId="'+searchProvinceId+'">'+searchCityName+'市所有项目';
                            }

                            if ($('#countyLevelCitySelectSearch').val() != -1) {
                                var searchCountyLevelCityId = $('#countyLevelCitySelectSearch').val();
                                var searchCountyLevelCityName = $("#countyLevelCitySelectSearch").find("option:selected").text();
                                if (countyLevelCityInputMap.containsKey(searchCountyLevelCityId)) {
                                    countyLevelCityCheckedStr = "checked";
                                }
                                area += '<INPUT type="checkbox" name="allCountyLevelCity" id="'+searchCountyLevelCityId+'" value="'+searchCountyLevelCityName+'" '+countyLevelCityDisabledStr+' '+countyLevelCityCheckedStr+' cityId="'+searchCityId+'" provinceId="'+searchProvinceId+'">'+searchCountyLevelCityName+'县级市所有项目';
                            }
                        }
                        $("#areaInputDiv").html(area);
                    } else {
						alert(data.data);
                    }
                }
            },
            error : function(data) {}
        });
	});

    // 在项目列表中选中和不选中项目
    $("input[name='projectInput']").live('click', function() {
        $.checkProjectItem(null);
    });

    // 在已选中的项目框中反勾选项目
    $("input[name='selectedProjectInput']").live('click', function() {
        $.checkProjectItem($(this));
    });

    // 选中全国项目
    $("input[name='allCountry']").live('click', function() {
        $.handleAllCountry($(this));
    });

    $("input[name='selectedAllCountry']").live('click', function() {
        $.handleAllCountry($(this));
	});

    // 选中全省项目 属于这个省的项目将会被选中
    $("input[name='allProvince']").live('click', function() {
        $.handleAllProvince($(this));
    });
    $("input[name='selectAllProvince']").live('click', function() {
        $.handleAllProvince($(this));
    });

	// 选中全市项目
    $("input[name='allCity']").live('click', function() {
        $.handleAllCity($(this));
    });
    // 选中全市项目
    $("input[name='selectAllCity']").live('click', function() {
        $.handleAllCity($(this));
    });

	// 选中全市项目
    $("input[name='allCountyLevelCity']").live('click', function() {
        $.handleAllCountyLevelCity($(this));
    });
    // 选中全市项目
    $("input[name='selectAllCountyLevelCity']").live('click', function() {
        $.handleAllCountyLevelCity($(this));
    });

    $("input[id='commitUserProject']").live('click', function() {
        setButtonStatus("cancelCommitUserProject", true); //设置按钮不可用
        setButtonStatus("commitUserProject", true); //设置按钮不可用
        var isAllCountry = false;
        var provinceIdsStr = "", cityIdsStr = "", countyLevelCityStr = "", projectIdsStr = "";
        var allCountryInputs = $("#previewAllCountryItem").find("input");
        for(var i = 0 ; i < allCountryInputs.length ; i++) {
            if(allCountryInputs[i].checked == true && allCountryInputs[i].value == "allCountry") {
                isAllCountry = true;
                break;
            }
        }
        if (isAllCountry == false) {
			// 已选择的省份
            var provinceInputs = $("#previewAllProvinceItem").find("input");
            for(var i = 0 ; i < provinceInputs.length ; i++) {
                if(provinceInputs[i].checked == true) {
                    provinceIdsStr += provinceInputs[i].id + ",";
                }
            }

            // 已选择的城市
            var cityInputs = $("#previewAllCityItem").find("input");
            for(var i = 0 ; i < cityInputs.length ; i++) {
                if(cityInputs[i].checked == true) {
                    cityIdsStr += cityInputs[i].id + ",";
                }
            }

            // 已选择的县级市
            var countyLevelCityInputs = $("#previewAllCountyLevelCityItem").find("input");
            for(var i = 0 ; i < countyLevelCityInputs.length ; i++) {
                if(countyLevelCityInputs[i].checked == true) {
                    countyLevelCityStr += $(countyLevelCityInputs[i]).attr("cityId") + "-" + countyLevelCityInputs[i].id + ",";
                }
            }

            // 已选择的项目
            var projectInputs = $("#previewProjectItem").find("input");
            for(var i = 0 ; i < projectInputs.length ; i++) {
                if(projectInputs[i].checked == true) {
                    projectIdsStr += projectInputs[i].id + ",";
                }
            }
		}

        jQuery.ajax({
            type : 'POST',
            url : ctx + '/setUserProject.do',
            dataType : 'json',
            data : {
                userId : $('#user_project_change_id').val(),
                isAll : isAllCountry == true ? 1 : 0,
                provinceIds : provinceIdsStr,
                cityIds : cityIdsStr,
                countyLevelCitys : countyLevelCityStr,
                projectIds : projectIdsStr
            },
            success : function(data) {
                setButtonStatus("cancelCommitUserProject", false); //设置按钮可用
                setButtonStatus("commitUserProject", false); //设置按钮可用
                if (data) {
                    if (data.status == "success") {
                        $.unblockUI();
                        $('#oper_result_label').html(data.data + "页面即将刷新！");
                        blockUIOpen('operResultWin');
                        reloadCurrentPage($("#pageTotal").val());
                    } else {
                        $('#oper_result_label').html(data.data);
                        blockUIOpen('operResultWin');
                    }
                }
            },
            error : function(data) {
                $('#oper_result_label').html("操作失败，出现异常！");
                blockUIOpen('operResultWin');
                setButtonStatus("cancelCommitUserProject", false); //设置按钮可用
                setButtonStatus("commitUserProject", false); //设置按钮可用
			}
        });
    });

});

$.handleAllCountry = function(obj) {
    if(obj.is(':checked') == true) {
        $("#previewAllCountryItem").html("");
        var htmlStr = '<input type="checkbox" name="selectedAllCountry" value="allCountry" id="allCountry" checked>全国所有项目';
        $("#previewAllCountryItem").html(htmlStr);
        $("#previewAllCountryItem").show();
        var projectItems = $("#projectItemInputDiv").find("input");
        for(var i = 0 ; i < projectItems.length ; i++) {
            projectItems[i].checked = true;
            projectItems[i].disabled = true;
        }
        $("#previewAllProvinceItem").html("");
        $("#previewAllProvinceItem").hide();
        $("#previewAllCityItem").html("");
        $("#previewAllCityItem").hide();
        $("#previewAllCountyLevelCityItem").html("");
        $("#previewAllCountyLevelCityItem").hide();

        var areaItems = $("#areaInputDiv").find("input");
        for(var i = 0 ; i < areaItems.length ; i++) {
            if (areaItems[i].name != 'allCountry') {
                areaItems[i].checked = true;
                areaItems[i].disabled = true;
            }
        }
        $("#previewProjectItem").html("");
    } else {
    	if ($(obj).attr("name") == "selectedAllCountry") {
            $("input[name='allCountry']").attr("checked", false);
		}
        $("#previewAllCountryItem").html("");
        $("#previewAllCountryItem").hide();
        var projectItems = $("#projectItemInputDiv").find("input");
        for(var i = 0 ; i < projectItems.length ; i++) {
            projectItems[i].checked = false;
            projectItems[i].disabled = false;
        }
        var areaItems = $("#areaInputDiv").find("input");
        for(var i = 0 ; i < areaItems.length ; i++) {
            if (areaItems[i].name != 'allCountry') {
                areaItems[i].checked = false;
                areaItems[i].disabled = false;
            }
        }
        $("#previewProjectItem").html("");
    }
}

$.handleAllProvince = function(obj) {
    var provinceId = obj.attr("id");

    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中
    var inputs = $("#previewAllProvinceItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }
    var projectItems = $("#projectItemInputDiv").find("input");
    var cityObjs = $("#areaInputDiv").find("input[provinceId='" + provinceId + "']");
    if(obj.is(':checked') == true) {
        if (!inputMap.containsKey(provinceId)) {
            var selectObjs = $("#areaInputDiv").find("input[name='allProvince']");
            for(var i = 0 ; i < selectObjs.length ; i++) {
            	if (selectObjs[i].id == provinceId) {
                    inputMap.put(provinceId, selectObjs[i]);
				}
			}
        }

        for(var i = 0 ; i < cityObjs.length ; i++) {
            cityObjs[i].disabled = true;
            cityObjs[i].checked = true;
        }

        for(var i = 0 ; i < projectItems.length ; i++) {
            if ($(projectItems[i]).attr("provinceId") == provinceId) {
                projectItems[i].disabled = true;
                projectItems[i].checked = true;
            }
        }

        // 处理 城市选中的区域信息
        $.setSelectCityDiv(provinceId);

    } else {
        if ($(obj).attr("name") == "selectAllProvince") {
            $("input[name='allProvince']").attr("checked", false);
        }
        if (inputMap.containsKey(provinceId)) {
            inputMap.remove(provinceId);
        }

        for(var i = 0 ; i < cityObjs.length ; i++) {
            cityObjs[i].disabled = false;
            cityObjs[i].checked = false;
        }
        for(var i = 0 ; i < projectItems.length ; i++) {
            if ($(projectItems[i]).attr("provinceId") == provinceId) {
                projectItems[i].checked = false;
                projectItems[i].disabled = false;
            }
        }
    }
    $.checkAllProvince(provinceId);
    if (inputMap.size() > 0) {
    	var inputArray = inputMap.values();
        inputArray.sort(function(a, b){
            return parseInt(a.id) - parseInt(b.id);
        });

        var htmlStr = "";
        $.each(inputArray, function(i, item) {
            htmlStr += '<input type="checkbox" name="selectAllProvince" id="'+item.id+'" value="'+item.value+'" checked>'+item.value+'所有项目';
            htmlStr += "<br/>";
        });
        $("#previewAllProvinceItem").html(htmlStr);
        $("#previewAllProvinceItem").show();
	} else {
        $("#previewAllProvinceItem").html("");
        $("#previewAllProvinceItem").hide();
	}
}

$.handleAllCity = function(obj) {
    var cityId = obj.attr("id");
    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中
    var inputs = $("#previewAllCityItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }
    var projectItems = $("#projectItemInputDiv").find("input");
    var selectObjs = $("#areaInputDiv").find("input[name='allCity']");
    var countyLevelCityObjs = $("#areaInputDiv").find("input[name='allCountyLevelCity']");
    if(obj.is(':checked') == true) {
        // 找到它自己
        if (!inputMap.containsKey(cityId)) {
            for(var i = 0 ; i < selectObjs.length ; i++) {
            	if (selectObjs[i].id == cityId) {
                    inputMap.put(cityId, selectObjs[i]);
				}
            }
        }

        for(var i = 0 ; i < countyLevelCityObjs.length ; i++) {
            if ($(countyLevelCityObjs[i]).attr("cityId") == cityId) {
                countyLevelCityObjs[i].disabled = true;
                countyLevelCityObjs[i].checked = true;
            }
        }

        for(var i = 0 ; i < projectItems.length ; i++) {
            if ($(projectItems[i]).attr("cityId") == cityId) {
                projectItems[i].disabled = true;
                projectItems[i].checked = true;
            }
        }
		// 处理 县级市选中的区域信息
        $.setSelectCountyLevelCityDiv(cityId);
    } else {
        if ($(obj).attr("name") == "selectAllCity") {
            for(var i = 0 ; i < selectObjs.length ; i++) {
                if (selectObjs[i].id == cityId) {
                    selectObjs[i].checked = false;
                }
            }
        }
        if (inputMap.containsKey(cityId)) {
            inputMap.remove(cityId);
        }

        for(var i = 0 ; i < countyLevelCityObjs.length ; i++) {
            if ($(countyLevelCityObjs[i]).attr("cityId") == cityId) {
                countyLevelCityObjs[i].disabled = false;
                countyLevelCityObjs[i].checked = false;
            }
        }

        for(var i = 0 ; i < projectItems.length ; i++) {
            if ($(projectItems[i]).attr("cityId") == cityId) {
                projectItems[i].checked = false;
                projectItems[i].disabled = false;
            }
        }
    }
    $.checkAllCity(cityId);
    if (inputMap.size() > 0) {
        var inputArray = inputMap.values();
        inputArray.sort(function(a, b){
            if (parseInt($(a).attr("provinceId")) != parseInt($(b).attr("provinceId"))) {
                return parseInt($(a).attr("provinceId")) - parseInt($(b).attr("provinceId"));
            }
            return parseInt(a.id) - parseInt(b.id);
        });

        var htmlStr = "";
        $.each(inputArray, function(i, item) {
            htmlStr += '<input type="checkbox" name="selectAllCity" id="'+item.id+'" provinceId="'+$(item).attr("provinceId")+'" value="'+item.value+'" checked>'+item.value+'市所有项目';
            htmlStr += "<br/>";
        });
        $("#previewAllCityItem").html(htmlStr);
        $("#previewAllCityItem").show();
    } else {
        $("#previewAllCityItem").html("");
        $("#previewAllCityItem").hide();
    }
}

$.handleAllCountyLevelCity = function(obj) {
    var countyLevelCityId = obj.attr("id");
    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中
    var inputs = $("#previewAllCountyLevelCityItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }
    var projectItems = $("#projectItemInputDiv").find("input");
    // 找到它自己
    var selectObjs = $("#areaInputDiv").find("input[name='allCountyLevelCity']");

    if(obj.is(':checked') == true) {

        if (!inputMap.containsKey(countyLevelCityId)) {
            for(var i = 0 ; i < selectObjs.length ; i++) {
                if (selectObjs[i].id == countyLevelCityId) {
                    inputMap.put(countyLevelCityId, selectObjs[i]);
                }
            }
        }

        for(var i = 0 ; i < projectItems.length ; i++) {
            if ($(projectItems[i]).attr("countyLevelCityId") == countyLevelCityId) {
                projectItems[i].disabled = true;
                projectItems[i].checked = true;
            }
        }
    } else {
        if ($(obj).attr("name") == "selectAllCountyLevelCity") {
            for(var i = 0 ; i < selectObjs.length ; i++) {
                if (selectObjs[i].id == countyLevelCityId) {
                    selectObjs[i].checked = false;
                }
            }
        }
        if (inputMap.containsKey(countyLevelCityId)) {
            inputMap.remove(countyLevelCityId);
        }

        for(var i = 0 ; i < projectItems.length ; i++) {
            if ($(projectItems[i]).attr("countyLevelCityId") == countyLevelCityId) {
                projectItems[i].checked = false;
                projectItems[i].disabled = false;
            }
        }
    }
    $.checkAllCountyLevelCity(countyLevelCityId);
    if (inputMap.size() > 0) {
        var inputArray = inputMap.values();
        inputArray.sort(function(a, b){
            if (parseInt($(a).attr("provinceId")) != parseInt($(b).attr("provinceId"))) {
                return parseInt($(a).attr("provinceId")) - parseInt($(b).attr("provinceId"));
            }
            if (parseInt($(a).attr("cityId")) != parseInt($(b).attr("cityId"))) {
                return parseInt($(a).attr("cityId")) - parseInt($(b).attr("cityId"));
            }
            return a.id - b.id;
        });

        var htmlStr = "";
        $.each(inputArray, function(i, item) {
            htmlStr += '<input type="checkbox" name="selectAllCountyLevelCity" id="'+item.id+'" provinceId="'+$(item).attr("provinceId")+'" cityId="'+$(item).attr("cityId")+'" value="'+item.value+'" checked>'+item.value+'县级市所有项目';
            htmlStr += "<br/>";
        });
        $("#previewAllCountyLevelCityItem").html(htmlStr);
        $("#previewAllCountyLevelCityItem").show();
    } else {
        $("#previewAllCountyLevelCityItem").html("");
        $("#previewAllCountyLevelCityItem").hide();
    }
}

$.setSelectCountyLevelCityDiv = function(cityId) {
    // 处理县级市
    var selectCountyLevelCityArray = new Array();
    var selectCountyLevelCityItems = $("#previewAllCountyLevelCityItem").find("input");
    for(var i = 0 ; i < selectCountyLevelCityItems.length ; i++) {
        if ($(selectCountyLevelCityItems[i]).attr("cityId") != cityId) {
            selectCountyLevelCityArray.push(selectCountyLevelCityItems[i])
        }
    }
    if (selectCountyLevelCityArray.length > 0) {
        selectCountyLevelCityArray.sort(function(a, b){
            if (parseInt($(a).attr("provinceId")) != parseInt($(b).attr("provinceId"))) {
                return parseInt($(a).attr("provinceId")) - parseInt($(b).attr("provinceId"));
            }
            if (parseInt($(a).attr("cityId")) != parseInt($(b).attr("cityId"))) {
                return parseInt($(a).attr("cityId")) - parseInt($(b).attr("cityId"));
            }
            return a.id - b.id;
        });
        var selectCountyLevelCityHtmlStr = "";
        $.each(selectCountyLevelCityArray, function(i, item) {
            selectCountyLevelCityHtmlStr += '<input type="checkbox" name="selectAllCountyLevelCity" id="'+item.id+'" provinceId="'+$(item).attr("provinceId")+'" cityId="'+$(item).attr("cityId")+'" value="'+item.value+'" checked>'+item.value+'县级市所有项目';
            selectCountyLevelCityHtmlStr += "<br/>";
        });
        $("#previewAllCountyLevelCityItem").show();
        $("#previewAllCountyLevelCityItem").html(selectCountyLevelCityHtmlStr);
    } else {
        $("#previewAllCountyLevelCityItem").html("");
        $("#previewAllCountyLevelCityItem").hide();
    }
}

$.setSelectCityDiv = function(provinceId) {
    var selectCityArray = new Array();
    var selectCityItems = $("#previewAllCityItem").find("input");
    for(var i = 0 ; i < selectCityItems.length ; i++) {
        if ($(selectCityItems[i]).attr("provinceId") != provinceId) {
            selectCityArray.push(selectCityItems[i])
        }
    }
    if (selectCityArray.length > 0) {
        selectCityArray.sort(function(a, b){
            if (parseInt($(a).attr("provinceId")) != parseInt($(b).attr("provinceId"))) {
                return parseInt($(a).attr("provinceId")) - parseInt($(b).attr("provinceId"));
            }
            return parseInt(a.id) - parseInt(b.id);
        });
        var selectCityHtmlStr = "";
        $.each(selectCityArray, function(i, item) {
            selectCityHtmlStr += '<input type="checkbox" name="selectAllCity" id="'+item.id+'" provinceId="'+$(item).attr("provinceId")+'" value="'+item.value+'" checked>'+item.value+'市所有项目';
            selectCityHtmlStr += "<br/>";
        });
        $("#previewAllCityItem").show();
        $("#previewAllCityItem").html(selectCityHtmlStr);
    } else {
        $("#previewAllCityItem").html("");
        $("#previewAllCityItem").hide();
    }

    // 处理县级市
    var selectCountyLevelCityArray = new Array();
    var selectCountyLevelCityItems = $("#previewAllCountyLevelCityItem").find("input");
    for(var i = 0 ; i < selectCountyLevelCityItems.length ; i++) {
        if ($(selectCountyLevelCityItems[i]).attr("provinceId") != provinceId) {
            selectCountyLevelCityArray.push(selectCountyLevelCityItems[i])
        }
    }
    if (selectCountyLevelCityArray.length > 0) {
        selectCountyLevelCityArray.sort(function(a, b){
            if (parseInt($(a).attr("provinceId")) != parseInt($(b).attr("provinceId"))) {
                return parseInt($(a).attr("provinceId")) - parseInt($(b).attr("provinceId"));
            }
            if (parseInt($(a).attr("cityId")) != parseInt($(b).attr("cityId"))) {
                return parseInt($(a).attr("cityId")) - parseInt($(b).attr("cityId"));
            }
            return a.id - b.id;
        });
        var selectCountyLevelCityHtmlStr = "";
        $.each(selectCountyLevelCityArray, function(i, item) {
            selectCountyLevelCityHtmlStr += '<input type="checkbox" name="selectAllCountyLevelCity" id="'+item.id+'" provinceId="'+$(item).attr("provinceId")+'" cityId="'+$(item).attr("cityId")+'" value="'+item.value+'" checked>'+item.value+'县级市所有项目';
            selectCountyLevelCityHtmlStr += "<br/>";
        });
        $("#previewAllCountyLevelCityItem").show();
        $("#previewAllCountyLevelCityItem").html(selectCountyLevelCityHtmlStr);
    } else {
        $("#previewAllCountyLevelCityItem").html("");
        $("#previewAllCountyLevelCityItem").hide();
    }
}

$.setEmptyUserProjectWin = function() {
    $("#projectItemInputDiv").html("");
    $("#provinceSearch").find('option[value="-1"]').attr("selected", true);
    $("#provinceSearch").prev().html("请选择省份");
    setEmpty("citySearch", "请选择城市");
    setEmpty("countyLevelCitySelectSearch", "请选择县级市");
    $("#countyLevelCitySelectSearchSpan").hide();
	var htmlStr = '<span><input type="checkbox" name="allCountry" value="allCountry" id="allCountry">全国所有项目</span>';
    $("#allCountryDiv").html(htmlStr);
    $("#areaInputDiv").html("");
    $("#previewAllCountryItem").html("");
    $("#previewAllProvinceItem").html("");
    $("#previewAllCityItem").html("");
    $("#previewProjectItem").html("");
}

// 在项目选择区域勾选与反勾选项目
$.checkProjectItem = function(obj) {
    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中
    var inputs = $("#previewProjectItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }

    // 这个选择区域的项目信息
    var projectItems = $("#projectItemInputDiv").find("input");
    for(var i = 0 ; i < projectItems.length ; i++) {
        if (obj != null && projectItems[i].id == obj.attr("id")) {
            projectItems[i].checked = false;
        }
        if(projectItems[i].checked == true) {
            // 不在Map中，放入Map中
            if (!inputMap.containsKey(projectItems[i].id)) {
                inputMap.put(projectItems[i].id, projectItems[i]);
            }

        } else {
            // 没有选中的，看看是否在数组中，在的话，从数组中拿出来
            if (inputMap.containsKey(projectItems[i].id)) {
                inputMap.remove(projectItems[i].id);
            }
        }
    }

    $.inputArrayToPreviewProjectItem(inputMap.values());
}

// 在项目选择区域勾选与反勾选项目
$.checkAllProvince = function(provinceId) {
    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中 排除掉属于当前所选省份的项目
    var inputs = $("#previewProjectItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true && $(inputs[i]).attr("provinceId") != provinceId) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }

    // 这个选择区域的项目信息
    var projectItems = $("#projectItemInputDiv").find("input");
    for(var i = 0 ; i < projectItems.length ; i++) {
        if(projectItems[i].checked == true && $(projectItems[i]).attr("provinceId") != provinceId) {
            // 不在Map中，放入Map中
            if (!inputMap.containsKey(projectItems[i].id)) {
                inputMap.put(projectItems[i].id, projectItems[i]);
            }

        } else {
            // 没有选中的，以及属于当前省份的，看看是否在Map中，在的话，从Map中拿出来
            if (inputMap.containsKey(projectItems[i].id)) {
                inputMap.remove(projectItems[i].id);
            }
        }
    }

    $.inputArrayToPreviewProjectItem(inputMap.values());
}

// 在项目选择区域勾选与反勾选项目
$.checkAllCity = function(cityId) {
    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中 排除掉属于当前所选城市的项目
    var inputs = $("#previewProjectItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true && $(inputs[i]).attr("cityId") != cityId) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }

    // 这个选择区域的项目信息
    var projectItems = $("#projectItemInputDiv").find("input");
    for(var i = 0 ; i < projectItems.length ; i++) {
        if(projectItems[i].checked == true && $(projectItems[i]).attr("cityId") != cityId) {
            // 不在Map中，放入Map中
            if (!inputMap.containsKey(projectItems[i].id)) {
                inputMap.put(projectItems[i].id, projectItems[i]);
            }

        } else {
            // 没有选中的，以及属于当前城市的，看看是否在Map中，在的话，从Map中拿出来
            if (inputMap.containsKey(projectItems[i].id)) {
                inputMap.remove(projectItems[i].id);
            }
        }
    }

    $.inputArrayToPreviewProjectItem(inputMap.values());
}

// 在项目选择区域勾选与反勾选项目
$.checkAllCountyLevelCity = function(countyLevelCityId) {
    var inputMap = new Map();
    // 先把放在 已选 框中的项目放到Map中 排除掉属于当前所选城市的项目
    var inputs = $("#previewProjectItem").find("input");
    for(var i = 0 ; i < inputs.length ; i++) {
        if(inputs[i].checked == true && $(inputs[i]).attr("countyLevelCityId") != countyLevelCityId) {
            inputMap.put(inputs[i].id, inputs[i]);
        }
    }

    // 这个选择区域的项目信息
    var projectItems = $("#projectItemInputDiv").find("input");
    for(var i = 0 ; i < projectItems.length ; i++) {
        if(projectItems[i].checked == true && $(projectItems[i]).attr("countyLevelCityId") != countyLevelCityId) {
            // 不在Map中，放入Map中
            if (!inputMap.containsKey(projectItems[i].id)) {
                inputMap.put(projectItems[i].id, projectItems[i]);
            }

        } else {
            // 没有选中的，以及属于当前城市的，看看是否在Map中，在的话，从Map中拿出来
            if (inputMap.containsKey(projectItems[i].id)) {
                inputMap.remove(projectItems[i].id);
            }
        }
    }

    $.inputArrayToPreviewProjectItem(inputMap.values());
}

$.inputArrayToPreviewProjectItem = function(inputArray) {
    inputArray.sort(function(a, b){
        var aPrId = $(a).attr("provinceId") == "" ? 100000 : $(a).attr("provinceId");
        var bPrId = $(b).attr("provinceId") == "" ? 100000 : $(b).attr("provinceId");
        if (parseInt(aPrId) - parseInt(bPrId) != 0) {
            return parseInt(aPrId) - parseInt(bPrId);
        }

        var aCId = $(a).attr("cityId") == "" ? 100000 : $(a).attr("cityId");
        var bCId = $(b).attr("cityId") == "" ? 100000 : $(b).attr("cityId");
        if (parseInt(aCId) - parseInt(bCId) != 0) {
            return parseInt(aCId) - parseInt(bCId);
        }

        var aClc = $(a).attr("countyLevelCity");
        var bClc = $(b).attr("countyLevelCity");
        if (aClc != bClc) {
            return aClc - bClc;
        }
        return parseInt(a.id) - parseInt(b.id);
    });

    var htmlStr = "";
    $.each(inputArray, function(i, item) {
        htmlStr += '<input type="checkbox" checked name="selectedProjectInput" id="' + item.id + '" projectName="' + $(item).attr("projectName") + '" provinceId="'+$(item).attr("provinceId")+'" cityId="'+$(item).attr("cityId")+'" countyLevelCityId="'+$(item).attr("countyLevelCityId")+'">' + $(item).attr("projectName");
        htmlStr += "<br/>";
    });
    $("#previewProjectItem").html(htmlStr);
}


//==========================================
//Purpose: 点击搜索模块的"查看全部"按钮后，消除所有的搜索条件置为空，用户名称与用户类型
//==========================================
function clearSearchInput() {
    $("#userNameSearch").val("");
    $("#userEmailSearch").val("");
}

//==========================================
//Purpose: 过滤检索输入框中的特殊字符
//==========================================
function replaceSpecialInput() {
    $("#userNameSearch").val(replaceSpecialStr(Trim($("#userNameSearch").val())));
    $("#userEmailSearch").val(replaceSpecialStr(Trim($("#userEmailSearch").val())));
}

//==========================================
//Purpose: 验证用户状态信息
//==========================================
function checkUserLock(obj) {
    var objId = $(obj).attr("id");
    if ($(obj).val() == -1) {
        seterror(objId, "请选择用户状态", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证用户状态信息
//==========================================
function checkUserRole(obj) {
    var objId = $(obj).attr("id");
    if ($(obj).val() == -1) {
        seterror(objId, "请选择用户角色", false);
        return false;
    } else {
        $('#'+objId+"_error").html("");
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证备注的输入
//==========================================
function checkUserNote(obj) {
    var objId = $(obj).attr("id");
    checkEmpty(obj);
    if ($(obj).val() != null && $(obj).val() != "" && $(obj).val().length > 120) {
        seterror(objId, "备注长度限制为120字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证密码的输入
//==========================================
function checkUserPassword(obj) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "请输入密码", false);
        return false;
    } else if (!$(obj).val().match(/^([a-zA-Z0-9]|[._]|[`~!@#$%^&*\(\)\-+\{\}\[\]\\|;:'",.\/<>?]){6,15}$/)) {
        seterror(objId, "<br/>密码为英文字母、数字和特殊字符(不含空格)组成的6-15个字符", false);
        return false;
    } else {
        seterror(objId, "", true);
        checkUserPassStrong(objId);
    }
    if (objId == "user_pass") {
        if ($(obj).val() == $("#confirm_user_pass").val()) {
            if ($("#confirm_user_pass_error").html() == "两次密码不一致，请确认") {
                seterror("confirm_user_pass", "", true);
            }
        }
    } else {
        if ($(obj).val() == $("#users_change_confirm_user_pass").val()) {
            if ($("#users_change_confirm_user_pass_error").html() == "两次密码不一致，请确认") {
                seterror("users_change_confirm_user_pass", "", true);
            }
        }
    }
}

//==========================================
//Purpose: 验证确认密码的输入
//==========================================
function checkConfirmUserPass(obj) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "请输入确认密码", false);
        return false;
    } else if($(obj).val() != $("#user_pass").val()) {
        seterror(objId, "两次密码不一致,请确认", false);
        return false;
    } else if (!$(obj).val().match(/^([a-zA-Z0-9]|[._]|[`~!@#$%^&*\(\)\-+\{\}\[\]\\|;:'",.\/<>?]){6,15}$/)) {
        seterror(objId, "<br/>密码为英文字母、数字和特殊字符(不含空格)组成的6-15个字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}

function checkChangeConfirmUserPass(obj) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "请输入确认密码", false);
        return false;
    } else if($(obj).val() != $("#users_change_user_pass").val()) {
        seterror(objId, "两次密码不一致,请确认", false);
        return false;
    } else if (!$(obj).val().match(/^([a-zA-Z0-9]|[._]|[`~!@#$%^&*\(\)\-+\{\}\[\]\\|;:'",.\/<>?]){6,15}$/)) {
        seterror(objId, "<br/>密码为英文字母、数字和特殊字符(不含空格)组成的6-15个字符", false);
        return false;
    } else {
        seterror(objId, "", true);
    }
}