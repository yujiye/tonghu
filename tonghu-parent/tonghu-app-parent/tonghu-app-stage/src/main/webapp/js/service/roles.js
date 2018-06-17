/**
 * 角色管理JS代码
 * author: liangyongjian
 * createDate: 2013-10-19 19:24:45
 */
$(document).ready(function() {
	
	//点击新增角色
	$('#addNewRole').click(function() {
		$('#role_name').val(""); $('#role_name').attr("class", "width130");
		$('#role_note').val(""); $('#role_note').attr("class", "width220"); 
		$("#role_name_error").show(); $("#role_name_error").html("");
		$("#role_note_error").show(); $("#role_pass_error").html("");
		blockUIOpen('addNewRoleWin');
		setButtonStatus("saveNewRole", false); //设置按钮可用
	});
	
	//保存新增加的角色信息
	$('#saveNewRole').live("click", function() {
		setButtonStatus("saveNewRole", true); //设置按钮不可用
		$('#role_name').blur();
		$('#role_note').blur(); 
		var is_error = false;
		var _error = ["role_name", "role_note"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false;
                }
        });
        if (is_error) {
        	setButtonStatus("saveNewRole", false); //设置按钮可用
        	return false;
        }
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/saveNewRole.do',
		    dataType : 'json',
		    data : {
		    	roleName : $('#role_name').val(),
		    	note : $('#role_note').val()
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
		            	setButtonStatus("saveNewRole", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//查看角色信息
    $("a[id*='showRole']").on('click', function() {
		var objId = $(this).attr("id");
		var roleId = objId.replace("showRole_", "");
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/showRole.do',
		    dataType : 'json',
		    data : {
		    	roleId : roleId
		    },
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#label_role_name').html(data.data.roleName);
		    		$('#label_role_create_time').html(data.data.createTime);
		    		$('#label_role_create_userName').html(data.data.createUserName);
		    		$('#label_role_update_time').html(data.data.updateTime);
		    		$('#label_role_update_userName').html(data.data.updateUserName);
		    		$('#label_role_note').html(data.data.note);
		    		blockUIOpen('roleInfoWin');
				} else {
					$('#oper_result_label').html(data.data);
			    	blockUIOpen('operResultWin');
				}
		    },
		    error : function(data) {}
		});
	});
	
	//修改角色信息 将角色信息铺回到页面
    $("a[id*='updateRole']").on('click', function() {
    	var objId = $(this).attr("id");
    	var roleId = objId.replace("updateRole_", "");
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/showRole.do',
		    dataType : 'json',
		    data : {
		    	roleId : roleId
		    },
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#change_role_id').val(roleId);
		    		$('#change_role_name').val(data.data.roleName);
		    		$('#change_role_note').val(data.data.note);
		    		$('#change_role_name').blur();
		    		$('#change_role_note').blur();
		    		blockUIOpen('changeRoleWin');
		    		setButtonStatus("saveChangeRole", false); //设置按钮可用
				} else {
					$('#oper_result_label').html(data.data);
			    	blockUIOpen('operResultWin');
				}
		    },
		    error : function(data) {}
		});
	});
	
	//点击删除角色的按钮
	$("a[id*='removeRole']").on('click', function() {
		var objId = $(this).attr("id");
		var roleId = objId.replace("removeRole_", "");
		$('#remove_alert_info').html("是否要删除角色“" + $("#show_role_" + roleId).html() + "”？");
		$('#remove_role_id').val(roleId);
		blockUIOpen('removeRoleConfirm');
		setButtonStatus("yesRemoveRole", false); //设置按钮可用
	});
	
	//删除角色
	$('#yesRemoveRole').live("click", function() {
		setButtonStatus("yesRemoveRole", true); //设置按钮不可用
		$.blockUIClose;
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/removeRole.do',
		    dataType : 'json',
		    data : {
		    	roleId : $('#remove_role_id').val()
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
		            	setButtonStatus("yesRemoveRole", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//保存所修改的角色信息
	$('#saveChangeRole').live("click", function() {
		setButtonStatus("saveChangeRole", true); //设置按钮不可用
		$('#change_role_name').blur();
		$('#change_role_note').blur();
		var is_error = false;
		var _error = ["change_role_name", "change_role_note"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false
                }
        });
        if (is_error) {
        	setButtonStatus("saveChangeRole", false); //设置按钮可用
        	return false;
        }
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/changeRole.do',
		    dataType : 'json',
		    data : {
		    	id : $('#change_role_id').val(),
		    	roleName : $('#change_role_name').val(),
		    	note : $('#change_role_note').val()
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
		            	setButtonStatus("saveChangeRole", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//修改角色权限信息 将角色权限信息铺回到页面
    $("a[id*='assignAuth']").on('click', function() {
    	var objId = $(this).attr("id");
    	var roleId = objId.replace("assignAuth_", "");
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/showRoleAuth.do',
		    dataType : 'json',
		    data : {
		    	roleId : roleId
		    },
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#change_auth_role_id').val(roleId);
		    		//删除表格第1行之外的所有行
		    		$("#changeRoleAuthTable tr:not(:first)").remove();
		    		var newRow = "";
		    		$.each(data.data, function(i, item) {   
		              	newRow = '<tr><td colspan="5" class="rightTd bold">' + item.moduleName + '</td></tr>';
		              	$("#changeRoleAuthTable tr:last").after(newRow);
		              	var subLength = item.resourceList.length;
		              	newRow = '<tr>';
		              	var checked = "";
		              	var tdNum = 0;
		              	$.each(item.resourceList, function(j, sub) {
		              		if (tdNum % 5 == 0 && tdNum != 0) {
		              			newRow += '<tr>';
		              		}
		              		tdNum = tdNum + 1;
		              		checked = "";
		              		if (sub.haveAuth == true) {
		              			checked = "checked";
		              		}
		              		newRow += '<td>';
		              		newRow += '<input type="checkbox" name="roleAuth" value="' + sub.id + '" ' + checked + ' />';
		              		newRow += sub.resourceName;
		              		newRow += '</td>';
		              		if (tdNum % 5 == 0 && tdNum != subLength) {
		              			newRow += '</tr>';
		              		}
		              	});
		              	newRow += '</tr>';
		              	$("#changeRoleAuthTable tr:last").after(newRow);
		            });
		    		blockUIOpen('changeRoleAuthWin');
		    		setButtonStatus("saveChangeRoleAuth", false); //设置按钮可用
				} else {
					$('#oper_result_label').html(data.data);
			    	blockUIOpen('operResultWin');
				}
		    },
		    error : function(data) {}
		});
	});
	
	//保存所修改的角色权限信息
	$('#saveChangeRoleAuth').live("click", function() {
		setButtonStatus("saveChangeRoleAuth", true); //设置按钮不可用
		var resourceIds = getCheckboxValue("roleAuth");
		var confirm = true;
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/assignRoleAuth.do',
		    dataType : 'json',
		    data : {
		    	roleId : $('#change_auth_role_id').val(),
		    	resourceIds : resourceIds
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
		            	setButtonStatus("saveChangeRoleAuth", false); //设置按钮可用
					}
		    	}
		 	},
		    error : function(data) {}
		});
		
	});
	
	
	
	//角色名校验
	$('#role_name').change(function() {
        checkRoleName(this);
    });
    $('#role_name').blur(function() {
        checkRoleName(this);
    });
    $('#change_role_name').change(function() {
        checkRoleName(this);
    });
    $('#change_role_name').blur(function() {
        checkRoleName(this);
    });
    
    //角色备注校验
	$('#role_note').change(function() {
        checkRoleNote(this);
    });
    $('#role_note').blur(function() {
        checkRoleNote(this);
    });
    $('#change_role_note').change(function() {
        checkRoleNote(this);
    });
    $('#change_role_note').blur(function() {
        checkRoleNote(this);
    });
    
});

//==========================================
//Purpose: 点击搜索模块的"查看全部"按钮后，消除所有的搜索条件置为空，角色名称与角色类型
//==========================================
function clearSearchInput() {
	$("#roleNameSearch").val("");
}

//==========================================
//Purpose: 过滤检索输入框中的特殊字符
//==========================================
function replaceSpecialInput() {
	$("#roleNameSearch").val(replaceSpecialStr(Trim($("#roleNameSearch").val())));
}
//==========================================
//Purpose: 验证角色名称
//==========================================
function checkRoleName(obj) {
	var objId = $(obj).attr("id");
	if (!checkEmpty(obj)) {
        seterror(objId, "请输入角色名称", false);
        return false;
    } else if ($(obj).val().length > 15) {
        seterror(objId, "角色名称不能超过15个字符", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证备注的输入
//==========================================
function checkRoleNote(obj) {
	var objId = $(obj).attr("id");
	checkEmpty(obj);
	if ($(obj).val() != null && $(obj).val() != "" && $(obj).val().length > 120) {
        seterror(objId, "备注长度限制为120字符", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}
