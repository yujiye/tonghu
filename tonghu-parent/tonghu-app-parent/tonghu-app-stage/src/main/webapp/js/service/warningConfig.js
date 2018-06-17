$(document).ready(function() {
	$("input[id*='beforeDays']").on('change', function() {
		var inputValue = Trim($(this).val());
		if(inputValue) {
			if (isPositiveInteger(inputValue)) {
				$(this).val(parseInt(inputValue));
			} else {
				$(this).val("");
			}
		}
	});
	
	//点击提交按钮，首先弹出提示框
	$('input[name="saveWarningConfig"]').click(function() {
		blockUIOpen('confirmSaveWin');
	});
	
	//点击确定提交按钮
	$('#sureSave').click(function() {
		setButtonStatus("sureSave", true); //设置按钮不可用
		clearAllErrors();//首先清除所有的错误信息
		jQuery.ajax({
			type : 'POST',
		    url : ctx + '/changeWarningConfig.do',
		    dataType : 'json',
		    data : {
		    	status : $('#status').val(),
		    	beforeDays : $('#beforeDays').val(),
		    	warningUserid : $('#fieldSelect').val()
		    },
		    success : function(data) {
		    	if (data) {
		    		if (data.status == "success") {
		    			$("#saveWarningConfig").attr('disabled', false);
		    			setButtonStatus("sureSave", false);
		    			$('#oper_result_label').html(data.data + "<br/>页面即将刷新！");
			    		blockUIOpen('operResultWin');
			    		location.reload();
					} else if (data.status == "error") {
						$.each(data.data, function(i, item) {   
		              		seterror(item.key, item.value, false);
		              		setButtonStatus("sureSave", false);
		            	});
						$('#oper_result_label').html("页面中存在错误，请查看红色提示文字！");
			    		blockUIOpen('operResultWin');
		            	$("#saveWarningConfig").attr('disabled', false); //设置按钮可用
		            	setButtonStatus("sureSave", false);
					} else {
						$('#oper_result_label').html(data.data);
			    		blockUIOpen('operResultWin');
		            	$("#saveWarningConfig").attr('disabled', false); //设置按钮可用
		            	setButtonStatus("sureSave", false);
					}
		    	}
		 	},
		    error : function(data) {
		    	$("#saveWarningConfig").attr('disabled', false); //设置按钮可用
		    	$('#oper_result_label').html("出现异常，请重试！");
			    blockUIOpen('operResultWin');
		    }
		});
	});
});


//==========================================
//Purpose: 验证提醒提前天数的输入
//==========================================
function checkBeforeDays(obj) {
	var objId = $(obj).attr("id");
	if (!checkEmpty(obj)) {
        seterror(objId, "请输入提醒提前天数", false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证提醒状态的选择
//==========================================
function checkStatus(obj) {
	var objId = $(obj).attr("id");
	if ($('#status').val() == -1) {
		setEmpty("status", "请选择提醒状态");
		return false;
	} else {
    	seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 当点击提交或者保存按钮时 清除所有的错误信息
//==========================================
function clearAllErrors() {
	seterror("status", "", true);
	seterror("beforeDays", "", true);
	seterror("fieldSelect", "", true);
}