Date.prototype.Format = function(fmt) { //author: meizz   
	var o = {
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	};   
	if(/(y+)/.test(fmt))   
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	for(var k in o)   
		if(new RegExp("("+ k +")").test(fmt))   
	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	return fmt;   
};
Date.prototype.diff = function(starDate, endDate){
  return (endDate.getTime() - starDate.getTime())/(24 * 60 * 60 * 1000);
}

//=============================================
//Purpose: Trim left spaces
//=============================================
function jsLTrim(str) {
	var rtnStr;
	rtnStr = "";
	for ( var i = 0; i < str.length; i++) {
		if (str.charAt(i) != " ") {
			rtnStr = str.substr(i);
			break;
		}
	}
	return rtnStr;
}

//==========================================
//Purpose: Trim right spaces
//==========================================
function jsRTrim(str) {
	var rtnStr;
	rtnStr = "";
	for ( var i = str.length - 1; i >= 0; i--) {
		if (str.charAt(i) != " ") {
			rtnStr = str.substring(0, i + 1);
			break;
		}
	}
	return rtnStr;
}

//==========================================
//Purpose: Trim both left and right spaces
//==========================================
function Trim(str) {
	return (jsLTrim(jsRTrim(str)));
}

//==========================================
//Purpose: 验证所控件的值是否为空，若为空则标红控件，若不为空则取消标红
//==========================================
function checkIsNull(obj, displayName) {
    var objId = $(obj).attr("id");
    if (!checkEmpty(obj)) {
        seterror(objId, "请输入" + displayName, false);
        return false;
    } else {
    	seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 验证所控件的值是否为空
//==========================================
function checkEmpty(obj) {
	var val = Trim($(obj).val());
	$(obj).val(val);
	if (val == null || val == "")
		return false;
	else 
		return true;
}

//==========================================
//Purpose: 过滤所给字符串中的特殊字符
//==========================================
function replaceSpecialStr(str) {
	if(str != null && str != "") 
	str = str.replace(/\%/g, "");
	str = str.replace(/\'/g, "");
	str = str.replace(/\_/g, "");
	return str;
}

//==========================================
//Purpose: 设置按钮的可用状态
//==========================================
function setButtonStatus(objId, status) {
	if ($("#" + objId)) {
		$("#" + objId).attr('disabled', status); //设置按钮可用状态
	}
}

//==========================================
//Purpose: 设置下拉选框为空，并添加默认选项
//==========================================
function setEmpty(objId, showStr) {
	$("#" + objId).empty();
	$("#" + objId).append('<option selected="selected" value="-1">' + showStr + '</option>');
	$("#" + objId).val(-1);
	$("#" + objId).prev().html(showStr);
}

//==========================================
//Purpose: 获取checkbox控件中被选中的值
//==========================================
function getCheckboxValue(objName){
  var checkValue = ''; 
  $('input[name="' + objName + '"]:checked').each(function(){ 
    checkValue += $(this).val() + ','; 
  });
  return checkValue;
}
//订单编号(QP+7位数字+字母或数字)
function isGoodsOrder(str) {
	var regu = /^QP\d{7}[0-9A-Za-z]$/;
	return regu.test(Trim(str));
}

//验证服务密码格式(16位以内数字字母)
function isVoucherCode(str) {
	var regu = /^[A-Za-z0-9]+$/;
	return regu.test(Trim(str));
}

//验证是否数字
function isInteger(str){
	var regu = /^\d+$/;
	return regu.test(Trim(str));
}

//验证是否正整数
function isPositiveInteger(str){
	var regu = /^\d*[0-9]\d*$/;
	return regu.test(Trim(str));
}


//==========================================
//Purpose: 检验整数 Long 型
//==========================================
function checkLongValue(obj) {
    var objId = $(obj).attr("id");
    var inputValue = Trim($(obj).val());
    if(inputValue) {
        if (!isPositiveInteger(inputValue)) {
            seterror(objId, "请输入数字且为正整数", false);
            return false;
        } else {
            $(this).val(parseInt(inputValue));
            seterror(objId, "", true);
        }
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 二级项目的顺序数字
//==========================================
function checkBranchOrder(obj, maxValue) {
    var objId = $(obj).attr("id");
    var inputValue = Trim($(obj).val());
    if(inputValue) {
        if (!isPositiveInteger(inputValue)) {
            seterror(objId, "请输入不大于"+maxValue+"的正整数", false);
            return false;
        } else if(parseInt(inputValue) > parseInt(maxValue)) {
            seterror(objId, "请输入不大于"+maxValue+"的正整数", false);
            return false;
        } else {
            var hasRepeat = false;
            $("#branchNameList").find("input[id*='branchOrder_']").each(function() {
                if (objId != $(this).attr("id")) {
                    var error_flag = $(this).attr('class');
                    if (error_flag != undefined && error_flag.indexOf("wrong") >= 0) {
                    } else {
                        if (parseInt(inputValue) == parseInt($(this).val())) {
                            seterror(objId, "输入的数字与其他输入框重复", false);
                            hasRepeat = true;
                            return;
                        }
                    }
                }
            });

            if (hasRepeat == false) {
                $(this).val(parseInt(inputValue));
                seterror(objId, "", true);
                return true;
            } else {
            	return false;
			}
		}
    } else {
        seterror(objId, "请输入不大于"+maxValue+"的正整数", false);
        return false;
    }
}

//==========================================
//Purpose: 校验 Decimal(20,8)的数字
//==========================================
function checkDecimalValue(obj) {
    var objId = $(obj).attr("id");
    var inputValue = Trim($(obj).val());
    if(inputValue) {
        if (!isFloatWith8pointNumber(inputValue)) {
            seterror(objId, "最多12位整数，8位小数", false);
            return false;
        } else {
            $(this).val(parseInt(inputValue));
            seterror(objId, "", true);
        }
    } else {
        seterror(objId, "", true);
    }
}

//==========================================
//Purpose: 校验 Decimal(20,8)的数字
//==========================================
function checkDecimalValueWithBr(obj) {
    var objId = $(obj).attr("id");
    var inputValue = Trim($(obj).val());
    if(inputValue) {
        if (!isFloatWith8pointNumber(inputValue)) {
            seterror(objId, "最多12位整数，8位小数", false);
            return false;
        } else {
            $(this).val(parseInt(inputValue));
            seterror(objId, "", true);
        }
    } else {
        seterror(objId, "", true);
    }
}

//验证是否浮点数
function isNumber(str){
	var regu = /^\d+(\.\d+)?$/;
	return regu.test(Trim(str));
}

//验证是否浮点数 2位小数
function isFloatWith2pointNumber(str){
    var regu = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;;
    return regu.test(Trim(str));
}

//验证是否浮点数 8位小数
function isFloatWith8pointNumber(str){
    var regu = /^((-)?0|[1-9]\d{0,11}|0)(\.\d{1,8})?$/;
    return regu.test(Trim(str));
}

//时间格式验证(yyyy-MM-dd)
function isDate(str){
	var regu = /^(\d{4})-((0([1-9]{1}))|(1[0-2]))-(([0-2]([0-9]{1}))|(3[01]))$/;
	return regu.test(Trim(str));
}

//email
function isEmail(str) {
	//var regu = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	var regu = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	return regu.test(Trim(str));
}

//固话
function isTel(str) {
	var regu = /^\d{10,12}$/;
	return regu.test(Trim(str));
}

//手机
function isMobile(str) {
	var regu = /^1[3458]\d{9}$/;
	return regu.test(Trim(str));
}

function openLoading() {
	$.blockUI( {
		message : $('#loading'),
		css : {
			backgroundColor : 'transparent'
		}
	});
}

//鼠标在行上over事件
function mouseover(ob) {
	//ob.style.cursor = "pointer";//鼠标变小手 兼容所有浏览器
	$(ob).addClass("td-blue");
}
//鼠标在行上out事件
function mouseout(ob) {
	$(ob).removeClass("td-blue");
}

//==========================================
//Purpose: 测试密码强度 开始
//==========================================
function checkStrong(sPW) {
    Modes=0;
    for (i=0;i<sPW.length;i++) {
     //测试每一个字符的类别并统计一共有多少种模式
     Modes|=CharMode(sPW.charCodeAt(i));
   }
   return bitTotal(Modes);
}
function CharMode(iN) {
   if (iN>=48 && iN <=57) return 1;//数字
   if (iN>=65 && iN <=90) return 2;//大写字母
   if (iN>=97 && iN <=122) return 4;//小写
   else return 8; //特殊字符
}
function bitTotal(num) {
   modes=0;
   for (i=0;i<4;i++) {
    if (num & 1) modes++;
     num>>>=1;
    }
   return modes;
}

function gopage(page) {
	if (!page) {
		return false;
	}

	if (page < 0) {
		totalpage = Math.abs(page);
		var topage = $("#topage").val();
		if (!isInteger(topage) || Number(topage <= 0) || Number(topage > totalpage)) {
			alert("只能输入1-" + totalpage + "的数字");
			$("#topage").val("");
			$("#topage").focus();
			return false;
		}
		page = topage;
	}

	$("#cpage").val(page);
	sub();
}

function error() {
	alert("查询出错");
	blockUIClose();
}

//本日
function today(begin, end) {
	var date = new Date();
	$("#" + begin).val(date.format("yyyy-MM-dd"));
	$("#" + end).val(date.format("yyyy-MM-dd"));
}

//近三天
function last3days(begin, end) {
	var date = new Date();
	var result = new Date();
	result.setDate(result.getDate() - 2);
	$("#" + begin).val(result.format("yyyy-MM-dd"));
	$("#" + end).val(date.format("yyyy-MM-dd"));
}

//近七天
function last7days(begin, end) {
	var date = new Date();
	var result = new Date();
	result.setDate(result.getDate() - 6);
	$("#" + begin).val(result.format("yyyy-MM-dd"));
	$("#" + end).val(date.format("yyyy-MM-dd"));
}

//近三十天
function last30days(begin, end) {
	var date = new Date();
	var result = new Date();
	result.setDate(result.getDate() - 29);
	$("#" + begin).val(result.format("yyyy-MM-dd"));
	$("#" + end).val(date.format("yyyy-MM-dd"));
}

//==========================================
//Purpose: 上传文件时，校验文件内容，对IE无效
//==========================================
function fileChange(target, id, maxFileSize, fileSuffix) {
	//判断文件后缀名
	var fileName = $("#" + id).val();
    var suffix = fileName.substr(fileName.indexOf("."));
    //id=activityFile 被固定占用作为模板文件的输入框
    if (fileSuffix != null && fileSuffix != "") {
    	if (fileSuffix.indexOf(suffix) == -1) {
            $('#oper_result_label').html("文件的后缀名不在允许范围之内！<br/>系统只支持“"+fileSuffix+"”后缀名文件");
            blockUIOpen('operResultWin');
            var file = document.getElementById(id);
            file.outerHTML = file.outerHTML;
            return;
		}
    }
	if (!(/msie/i.test(navigator.userAgent) && !window.opera)) {
		var fileSize = 0;
		fileSize = target.files[0].size;
	          
		var size = fileSize / (1024 * 1024);
		if(size > maxFileSize){
			$('#oper_result_label').html("文件大小不能大于" + maxFileSize + "M！");
			blockUIOpen('operResultWin');
			var file = document.getElementById(id);
			file.outerHTML = file.outerHTML;
			return;
		}
		if(size <= 0){
			$('#oper_result_label').html("文件不能为空！");
			blockUIOpen('operResultWin');
			var file = document.getElementById(id);
			file.outerHTML = file.outerHTML;
			return;
		}
		
		if($("#"+id+"_error")) {
			seterror(id, "", true);
		}

		// 触发文件上传按钮
        $("#"+id+"Upload").click();
	}
}

//==========================================
//Purpose: 上传导入文件时，校验文件内容，对IE无效
//==========================================
function importFileChange(target, id, maxFileSize, maxCompressFileSize) {
	//判断文件后缀名
	var fileName = $("#" + id).val();
    var suffix = fileName.substr(fileName.indexOf("."));
    //id=activityFile 被固定占用作为模板文件的输入框
    if (id == "activityFile" && (suffix != ".xls" && suffix != ".zip" && suffix != ".rar")) {
    	$('#oper_result_label').html("附件的后缀名不在允许范围之内！<br/>系统只支持“.xls”（压缩包为.zip.rar格式）类型模板文件");
		blockUIOpen('operResultWin');
		var file = document.getElementById(id);
		file.outerHTML = file.outerHTML;
		return;
    }
	if (!(/msie/i.test(navigator.userAgent) && !window.opera)) {
		var fileSize = 0;
		fileSize = target.files[0].size;
	          
		var size = fileSize / (1024 * 1024);
		if (suffix == ".xls") {
			if(size > maxFileSize){
				$('#oper_result_label').html("Excel附件大小不能大于" + maxFileSize + "M！");
				blockUIOpen('operResultWin');
				var file = document.getElementById(id);
				file.outerHTML = file.outerHTML;
				return;
			}
		} else if (suffix != ".zip" || suffix != ".rar") {
			if(size > maxCompressFileSize){
				$('#oper_result_label').html("压缩包附件大小不能大于" + maxFileSize + "M！");
				blockUIOpen('operResultWin');
				var file = document.getElementById(id);
				file.outerHTML = file.outerHTML;
				return;
			}
		}
		
		if(size <= 0){
			$('#oper_result_label').html("附件不能为空！");
			blockUIOpen('operResultWin');
			var file = document.getElementById(id);
			file.outerHTML = file.outerHTML;
			return;
		}
		
		if($("#"+id+"_error")) {
			seterror(id, "", true);
		}
	}
}
//==========================================
//Purpose: 下载导出的文件
//==========================================
function downLoadExportFile(fileName) {
	var form = $("<form>");   //定义一个form表单
	form.attr('style', 'display:none');   //在form表单中添加查询参数
	form.attr('target', '');
	form.attr('method', 'post');
	form.attr('action', ctx + "/exportFileDownload.do");
	var input = $('<input>'); 
	input.attr('type', 'hidden'); 
	input.attr('name', 'fileName'); 
	input.attr('value', fileName);
	$('body').append(form);  //将表单放置在web中
	form.append(input);   //将查询参数控件提交到表单上
	form.submit();   //表单提交
	form.remove();
}

//==========================================
//Purpose: 下载附件
//==========================================
function downLoadFile(fileId) {
	var form = $("<form>");   //定义一个form表单
	form.attr('style', 'display:none');   //在form表单中添加查询参数
	form.attr('target', '');
	form.attr('method', 'post');
	form.attr('action', ctx + "/fileDownload.do");
	var input = $('<input>'); 
	input.attr('type', 'hidden'); 
	input.attr('name', 'fileId'); 
	input.attr('value', fileId);
	$('body').append(form);  //将表单放置在web中
	form.append(input);   //将查询参数控件提交到表单上
	form.submit();   //表单提交
	form.remove();
}

//==========================================
//Purpose: 判断当前输入的时间是否合法，并且符合YYYY-MM-DD或者YYYY/MM/DD
//==========================================
function isValidDate(value) {
	var ereg = /^(\d{1,4})(-|\/)(\d{1,2})(-|\/)(\d{1,2})$/;
	var r = value.match(ereg);
	if (r == null) return false;
	var d = new Date(r[1], r[3] - 1, r[5]);
	var result = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[5]);
	return result;
}

//==========================================
//Purpose: 两个时间相减 返回减值 
//==========================================
function reductionTime(date1, date2) {
	var now = new Date();
	var startDate = new Date(date1.replace(/\-/g, "/") + " 00:00:00");
	var endDate = new Date(date2.replace(/\-/g, "/") + " 00:00:00");
	var diff = now.diff(startDate, endDate);
    return diff;
}

//==========================================
//Purpose: 获取当前时间
//==========================================
function getCurrentDate() {
	var now = new Date();
	var yy = now.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = now.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = now.getDate();
	if(dd<10) dd = '0' + dd;
	return(yy + "-" + MM + "-" + dd);
}

function adjustImgSizeWithImgId(img_id) {
    var imgMeasure = $('#' + img_id).attr('imgMeasure');
    adjustImgSize($('#' + img_id), $('#' + img_id).parent().width(),
		$('#' + img_id).parent().height(), imgMeasure.split("*")[0], imgMeasure.split("*")[1]);
}

var adjustImgSize = function(img, boxWidth, boxHeight, imgWidth, imgHeight) {
    //比较imgBox的长宽比与img的长宽比大小
    if((boxWidth/boxHeight)>=(imgWidth/imgHeight))  {
        //重新设置img的width和height
        img.width((boxHeight*imgWidth)/imgHeight);
        img.height(boxHeight);
        //让图片居中显示
        var margin=(boxWidth-img.width())/2;
        img.css("margin-left",margin);
    } else {
        //重新设置img的width和height
        img.width(boxWidth);
        img.height((boxWidth*imgHeight)/imgWidth);
        //让图片居中显示
        var margin=(boxHeight-img.height())/2;
        img.css("margin-top",margin);
    }

    if (boxWidth == imgWidth && boxHeight == imgHeight) {
        img.css("margin-left",0);
        img.css("margin-top",0);
    }
};