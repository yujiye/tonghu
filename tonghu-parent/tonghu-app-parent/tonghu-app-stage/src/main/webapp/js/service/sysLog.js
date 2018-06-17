/**
 * 日志管理JS代码
 * author: liangyongjian
 * createDate: 2017-09-29 19:24:45
 */
$(document).ready(function() {});
	
	

//==========================================
//Purpose: 点击搜索模块的"查看全部"按钮后，消除所有的搜索条件置为空，经销商名称与经销商类型
//==========================================
function clearSearchInput() {
	$("#userSearch").val(-1);
	$("#operResultSearch").val(-1);
	$("#operateDateStartSearch").val("");
	$("#operateDateEndSearch").val("");
}

//==========================================
//Purpose: 过滤检索输入框中的特殊字符
//==========================================
function replaceSpecialInput() {
}

var checkin = $('#operateDateStartSearch').datepicker().on('changeDate', function(ev) {
	var newDate = new Date(ev.date)
    if (ev.date.valueOf() > checkout.date.valueOf()) {
		newDate.setDate(newDate.getDate());
		checkout.setValue(newDate);
    }
    checkin.hide();
    $('#operateDateEndSearch')[0].focus();
}).data('datepicker');

var checkout = $('#operateDateEndSearch').datepicker({
    onRender: function(date) {
    }
}).on('changeDate', function(ev) {
	if (ev.date.valueOf() < checkin.date.valueOf()) {
		checkout.setValue(checkin.date.valueOf());
	}
    checkout.hide();
}).data('datepicker');
