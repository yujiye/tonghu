/**
 * 系统页面头部功能JS代码 包含公用的JS代码
 * author: liangyongjian
 * createDate: 2013-10-17 19:24:45
 */
$(document).ready(function() {
	
	//页面加载成功后在页面的右上角给出提示
	$.growlUI('', '页面加载成功！');
	
//	$("#handleWating").ajaxStart(function() {
//		$.maskUI.open($(this));
//	});
//	$("#handleWating").ajaxStop(function() {
//		if($("#handleWating").length > 0 && !$("#handleWating").is(":hidden")) {
//			$.unmaskUI();
//		}
//	});
	
	//全局的ajax访问，处理ajax清求时sesion超时
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(xhr, textStatus) {
			//session timeout
			if (xhr.status == 911) {
				blockUIOpen('sessionTimeoutConfirm');
			}
		}
	});
	
	//点击查看当前用户信息
	$('#userInfo').click(function() {
		jQuery.ajax({
			type : 'POST',
		   	contentType : 'application/json',
		    url : ctx + '/currentUserInfo.do',
		    dataType : 'json',
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#label_user_name').html(data.data.userName);
		    		$('#label_user_email').html(data.data.userEmail);
		    		$('#label_true_name').html(data.data.trueName);
		    		$('#label_org_name').html(data.data.orgName);
		    		$('#label_user_mobile').html(data.data.userMobile);
		    		$('#label_user_phone').html(data.data.userPhone);
		    		$('#label_create_time').html(data.data.createTime);
		    		$('#label_note').html(data.data.note);
		    		blockUIOpen('currentUserInfoWin');
				}
		 	},
		    error : function(data) {}
		});
	});

    //搜索框：点击省份选择框后 城市选框会联动
    $('#provinceSearch').change(function() {
        if ($('#provinceSearch').val() == -1) {
            setEmpty("citySearch", "请选择城市");
            if ($("#countyLevelCitySelectSearch").length > 0) {
                setEmpty("countyLevelCitySelectSearch", "请选择县级市");
                $("#countyLevelCitySelectSearchSpan").hide("fast");
			}
            return;
        }
        jQuery.ajax({
            type : 'POST',
            url : ctx + '/provinceChangeCity.do',
            dataType : 'json',
            data : {
                provinceId : $('#provinceSearch').val()
            },
            success : function(data) {
                if (data) {
                    if ($("#countyLevelCitySelectSearch").length > 0) {
                        setEmpty("countyLevelCitySelectSearch", "请选择县级市");
                        $("#countyLevelCitySelectSearchSpan").hide("fast");
                    }
                    if (data.status == "success") {
                        //联动省份选择框
                        setEmpty("citySearch", "请选择城市");
                        $.each(data.data, function(i, item) {
                            $("#citySearch").append('<option value="' + item.id + '">' + item.city + '</option>');
                        });
                    } else {
                        setEmpty("citySearch", "请选择城市");
                    }
                }
            },
            error : function(data) {}
        });
    });
	
	//进入用户信息修改窗口
	$('#changeUserBaseInfo').live("click", function() {
		jQuery.ajax({
			type : 'POST',
		   	contentType : 'application/json',
		    url : ctx + '/currentUserInfo.do',
		    dataType : 'json',
		    success : function(data) {
		    	if (data && data.status == "success") {
		    		$('#change_user_name').val(data.data.userName);
		    		$('#change_user_email').val(data.data.userEmail);
		    		$('#change_true_name').val(data.data.trueName);
		    		$('#change_user_mobile').val(data.data.userMobile);
		    		$('#change_user_phone').val(data.data.userPhone);
		    		$('#change_note').val(data.data.note);
		    		$('#change_user_name').blur();
		    		$('#change_user_email').blur();
		    		$('#change_true_name').blur();
		    		$('#change_user_mobile').blur();
		    		blockUIOpen('changeUserBaseInfoWin');
				}
		 	},
		    error : function(data) {}
		});
	});
	
	//打开修改用户密码的窗口
	$('#changeUserPass').live("click", function() {
		//老密码校验
		$('#old_password').val(""); $('#old_password').attr("class", "width130"); 
	    $('#new_password').val(""); $('#new_password').attr("class", "width130");
	    $('#confirm_new_password').val(""); $('#confirm_new_password').attr("class", "width130");
	    $("#old_password_error").show(); $("#old_password_error").html("");
	    $("#new_password_error").show(); $("#new_password_error").html("");
	    $("#confirm_new_password_error").show(); $("#confirm_new_password_error").html("");
        blockUIOpen('changeUserPassWin');
	});
	
	//保存所修改的用户基本信息
	$('#saveChangeUserBaseInfo').live("click", function() {
		$('#change_user_name').blur();
		$('#change_user_email').blur();
		$('#change_true_name').blur();
		$('#change_user_mobile').blur();
		var is_error = false;
		var _error = ["change_user_name", "change_user_email", "change_true_name", "change_user_mobile", "change_user_phone", "change_note"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false
                }
        });
        if (is_error) return false;
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/setCurrentUserInfo.do',
		    dataType : 'json',
		    data : {
		    	userName : $('#change_user_name').val(),
		    	userEmail : $('#change_user_email').val(),
		    	trueName : $('#change_true_name').val(),
		    	userMobile : $('#change_user_mobile').val(),
		    	userPhone : $('#change_user_phone').val(),
		    	note : $('#change_note').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			$('#oper_result_label').html(data.data);
			    		blockUIOpen('operResultWin');
					} else {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		            	}); 
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//保存所修改的用户密码信息
	$('#saveChangeUserPassInfo').live("click", function() {
		$('#old_password').blur();
		$('#new_password').blur();
		$('#confirm_new_password').blur();
		var is_error = false;
		var _error = ["old_password", "new_password", "change_true_name", "confirm_new_password"];
		$.each(_error, function(key, val) {
        	var error_flag = $('#' + val).attr('class');
            if (error_flag != undefined)
            	if (error_flag.indexOf("wrong") >= 0) {
                	is_error = true;
                	return false
                }
        });
        if (is_error) return false;
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/setCurrentUserPassInfo.do',
		    dataType : 'json',
		    data : {
		    	password : $('#old_password').val(),
		    	newPassword : $('#new_password').val(),
		    	confirmNewPassword : $('#confirm_new_password').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status != "error") {
		    			$('#oper_result_label').html(data.data);
			    		blockUIOpen('operResultWin');
					} else {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		            	}); 
					}
		    	}
		 	},
		    error : function(data) {}
		});
	});
	
	//点击用户设置
	$('#userInfoSet').click(function() {
		blockUIOpen('userInfoSetWin');
	});
	
	//点击退出系统按钮
	$('#logout').click(function() { 
		blockUIOpen('logoutConfirm');
    });
    //退出系统
	$('#yesLogout').live("click", function() { 
        blockUIClose();
		location.href = ctx + '/logout'
	});
	//所有的 取消 都可以使用该事件 关闭当前窗口
	$('#cancel').live("click", function() {
        blockUIClose();
        return false;
	});
	
	//用户登录名校验
	$('#change_user_name').change(function() {
        checkUserName(this);
    });
    $('#change_user_name').blur(function() {
        checkUserName(this);
    });
    
    //用户真实姓名校验
	$('#change_true_name').change(function() {
        checkIsNull(this, "真实姓名");
    });
    $('#change_true_name').blur(function() {
        checkIsNull(this, "真实姓名");
    });
    
    //用户电子邮箱校验
	$('#change_user_email').change(function() {
        checkUserEmail(this, "电子邮箱");
    });
    $('#change_user_email').blur(function() {
        checkUserEmail(this, "电子邮箱");
    });
    
    //手机号码校验
	$('#change_user_mobile').change(function() {
        checkUserMobile(this);
    });
    $('#change_user_mobile').blur(function() {
        checkUserMobile(this);
    });
    
    //座机号码长度验证
    $('#change_user_phone').change(function() {
        checkUserPhone(this);
    });
    $('#change_user_phone').blur(function() {
        checkUserPhone(this);
    });
    
    //备注长度验证
    $('#change_note').change(function() {
        checkChangeNote(this);
    });
    $('#change_note').blur(function() {
        checkChangeNote(this);
    });
    
    //老密码校验
	$('#old_password').change(function() {
        checkOldPassword(this);
    });
    $('#old_password').blur(function() {
        checkOldPassword(this);
    });
    
    //新密码校验
	$('#new_password').change(function() {
        checkNewPassword(this);
    });
    $('#new_password').blur(function() {
        checkNewPassword(this);
    });
    
    //确认新校验
	$('#confirm_new_password').change(function() {
        checkConfirmNewPassword(this);
    });
    $('#confirm_new_password').blur(function() {
        checkConfirmNewPassword(this);
    });
    
    //分页所用的JS逻辑
    $("input[id*='pageTurn']").on('click', function() {
		var inputId = $(this).attr("id");
		var value = inputId.replace("pageTurn","");
		$("#pageNumInput").val(value);
		formSubmit();
	});
	
	//搜索按钮触发的事件
	$("#searchMatch").on('click', function() {
		$("#pageNumInput").val(1);
		replaceSpecialInput();
		formSubmit();
	});
	
	//点击搜索模块的"查看全部"按钮后，消除所有的搜索条件并将分页页码置为1
    $("#searchAll").on('click', function() {
		$("#pageNumInput").val(1);
		clearSearchInput();
		formSubmit();
	});
    
    //系统右下角返回页面顶部
    
    ;(function($) { 
        $.scrollBtn = function(options) { 
            var opts = $.extend({}, $.scrollBtn.defaults, options); 

            var $scrollBtn = $('<div></div>').css({ 
                                bottom: opts.bottom + 'px', 
                                right: opts.right + 'px' 
                            }).addClass('scroll-up') 
                            .attr('title', opts.title) 
                            .click(function() { 
                                $('html, body').animate({scrollTop: 0}, opts.duration); 
                            }).appendTo('body'); 
                                                                                 
            $(window).bind('scroll', function() { 
                var scrollTop = $(document).scrollTop(), 
                    viewHeight = $(window).height(); 

                if(scrollTop <= opts.showScale) { 
                    if($scrollBtn.is(':visible')) 
                        $scrollBtn.fadeOut(500); 
                } else { 
                    if($scrollBtn.is(':hidden'))  
                        $scrollBtn.fadeIn(500); 
                } 

                if(isIE6()) { 
                    var top = viewHeight + scrollTop - $scrollBtn.outerHeight() - opts.bottom; 
                    $scrollBtn.css('top', top + 'px'); 
                } 
            }); 

            function isIE6() { 
                if($.browser.msie) { 
                    if($.browser.version == '6.0') return true; 
                } 
            } 
        }; 

        /** 
         * -params  
         *  -showScale: scroll down how much to show the scrollup button 
         *  -right: to right of scrollable container  
         *  -bottom: to bottom of scrollable container  
         */ 
        $.scrollBtn.defaults = { 
            showScale: 100,   
            right:10, 
            bottom:10, 
            duration:200, 
            title:'返回到顶部' 
        } 
    })(jQuery); 

    $.scrollBtn({ 
        showScale: 200, 
        bottom:20, 
        right:20 
    });
});

//==========================================
//Purpose: 在form表单提交前 添加遮罩层 并延时一定时间后再提交
//==========================================
function formSubmit() {
	blockUIOpen('handleWating');
	setTimeout(function() { 
    	$.unblockUI({ 
        	onUnblock: function(){ $('#searchForm').submit(); } 
        }); 
    }, 500);
}

//==========================================
//Purpose: 验证用户名输入控件
//==========================================
function checkUserName(obj) {
	var objId = $(obj).attr("id");
	if (!checkEmpty(obj)) {
        seterror(objId, "请输入用户名", false);
        return false;
    } else if (!$(obj).val().match(/^[a-zA-Z]{1}([a-zA-Z0-9_@.]){4,29}$/)) {
    	//只处验证不能为空并且只能为英文或者数字或者下划线组成的5-30个字符
        seterror(objId, "<br/>用户名为字母、数字和下划线组成的5-30个字符,且以字母开头", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证用户原密码输入控件
//==========================================
function checkOldPassword(obj) {
	var objId = $(obj).attr("id");
	if (!checkEmpty(obj)) {
        seterror(objId, "请输入原密码", false);
        return false;
    } else if (!$(obj).val().match(/^([a-zA-Z0-9]|[._]|[`~!@#$%^&*\(\)\-+\{\}\[\]\\|;:'",.\/<>?]){6,15}$/)) {
        seterror(objId, "<br/>密码为英文字母、数字和特殊字符(不含空格)组成的6-15个字符", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}
//==========================================
//Purpose: 验证用户新密码输入控件
//==========================================
function checkNewPassword(obj) {
	var objId = $(obj).attr("id");
	if (!checkEmpty(obj)) {
        seterror(objId, "请输入新密码", false);
        return false;
    } else if (!$(obj).val().match(/^([a-zA-Z0-9]|[._]|[`~!@#$%^&*\(\)\-+\{\}\[\]\\|;:'",.\/<>?]){6,15}$/)) {
        seterror(objId, "<br/>密码为英文字母、数字和特殊字符(不含空格)组成的6-15个字符", false);
        return false;
    } else {
    	seterror(objId, "", true);
    	checkUserPassStrong(objId);
    }
    if ($(obj).val() == $("#confirm_new_password").val()) {
    	if ($("#confirm_new_password_error").html() == "两次密码不一致，请确认") {
    		seterror("confirm_new_password", "", true);
    	}
    }
}
//==========================================
//Purpose: 验证用户确认新密码输入控件
//==========================================
function checkConfirmNewPassword(obj) {
	var objId = $(obj).attr("id");
	if (!checkEmpty(obj)) {
        seterror(objId, "请输入确认密码", false);
        return false;
    } else if($(obj).val() != $("#new_password").val()) {
    	seterror(objId, "两次密码不一致,请确认", false);
        return false;
    } else if (!$(obj).val().match(/^([a-zA-Z0-9]|[._]|[`~!@#$%^&*\(\)\-+\{\}\[\]\\|;:'",.\/<>?]){6,15}$/)) {
        seterror(objId, "<br/>密码为英文字母、数字和特殊字符(不含空格)组成的6-15个字符", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}
//==========================================
//Purpose: 验证用户电子邮箱输入控件
//==========================================
function checkUserEmail(obj) {
	var objId = $(obj).attr("id");
	if ($(obj).val() != null && $(obj).val() != "") {
        if (!checkEmpty(obj) && !isEmail($(obj).val())) {
            seterror(objId, "电子邮箱格式有误", false);
            return false;
        }
    } else {
    	seterror(objId, "", true);
    }
}
//==========================================
//Purpose: 验证用户手机号码输入控件
//==========================================
function checkUserMobile(obj) {
	var objId = $(obj).attr("id");
	checkEmpty(obj);
	if ($(obj).val() != null && $(obj).val() != "" && !isMobile($(obj).val())) {
        seterror(objId, "手机号码格式有误", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证备注的输入
//==========================================
function checkChangeNote(obj) {
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
//Purpose: 验证座机号码的输入
//==========================================
function checkUserPhone(obj) {
	var objId = $(obj).attr("id");
	checkEmpty(obj);
	if ($(obj).val() != null && $(obj).val() != "" && $(obj).val().length > 15) {
        seterror(objId, "座机号码长度限制为15字符", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}

function reloadCurrentPage(pageNum) {
	if (pageNum) {
		setTimeout(function() { 
			$.unblockUI({ 
        		onUnblock: function(){ $("#pageTurn" + pageNum).click(); } 
       		}); 
    	}, 1500);
	} else {
		setTimeout(function() { 
			$.unblockUI({ 
        		onUnblock: function(){ $('#searchForm').submit(); } 
       		}); 
    	}, 1500);
	}
}

function reloadPage(url) {
	setTimeout(function() { 
		$.unblockUI({ 
        	onUnblock: function(){ window.location.href=""+url+""; } 
       	}); 
    }, 1500);
}

function reloadPageWithDelay(id, name, url, target) {
	setTimeout(function() {
		$.unblockUI({ 
        	onUnblock: function(){
        		var form = $("<form>");   //定义一个form表单
				form.attr('style', 'display:none');   //在form表单中添加查询参数
				form.attr('target', target);
				form.attr('method', 'post');
				form.attr('action', ctx + url);
				var input = $('<input>'); 
				input.attr('type', 'hidden'); 
				input.attr('name', name);
				input.attr('value', id);
				$('body').append(form);  //将表单放置在web中
				form.append(input);   //将查询参数控件提交到表单上
				form.submit();   //表单提交
				form.remove();
        	} 
       	}); 
    }, 1500);
}

function reloadPageNotDelay(id, name, url, target) {
    var form = $("<form>");   //定义一个form表单
    form.attr('style', 'display:none');   //在form表单中添加查询参数
    form.attr('target', target);
    form.attr('method', 'post');
    form.attr('action', ctx + url);
    var input = $('<input>');
    input.attr('type', 'hidden');
    input.attr('name', name);
    input.attr('value', id);
    $('body').append(form);  //将表单放置在web中
    form.append(input);   //将查询参数控件提交到表单上
    form.submit();   //表单提交
    form.remove();
}

function checkUserPassStrong(objId) {
	//校验密码强度
    var strong = checkStrong($("#" + objId).val());
    $("#" + objId + "_error").show();
    if (strong == "1") {
    	$("#" + objId + "_error").html("<font color='black'>(密码强度</font><font color='red'> 弱 </font><font color='black'>,仅提示,不影响提交)");
    } else if (strong == "2") {
    	$("#" + objId + "_error").html("<font color='black'>(密码强度</font><font color='blue'> 中 </font><font color='black'>,仅提示,不影响提交)</font>");
    } else {
    	$("#" + objId + "_error").html("<font color='black'>(密码强度</font><font color='green'> 强 </font><font color='black'>,仅提示,不影响提交)</font>");
    }
}

function showSubMenu(menuId) {
	var currentShowMenu = $("#menuUl").attr("showMenu");
	if (menuId == currentShowMenu) {
        $("li[menuId]").hide("fast");
        $("#menuUl").attr("showMenu", "-1");
	} else {
        $("li[menuId="+currentShowMenu+"]").hide("fast");
        $("li[menuId="+menuId+"]").show("fast");
        $("#menuUl").attr("showMenu", menuId);
	}
}

// 获取当前时间 YYYY-MM-DD
function getNowFormatDate() {
    var date = new Date();
    var dot = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    return (year + dot + month + dot + strDate);
}