var checkin = $('#startDate').datepicker().on('changeDate', function(ev) {
	var newDate = new Date(ev.date)
    if (ev.date.valueOf() > checkout.date.valueOf()) {
		newDate.setDate(newDate.getDate());
		checkout.setValue(newDate);
    }
    checkin.hide();
    $('#endDate')[0].focus();
}).data('datepicker');

var checkout = $('#endDate').datepicker({
    onRender: function(date) {
		// return date.valueOf() < checkin.date.valueOf() ? 'disabled' : '';
    }
}).on('changeDate', function(ev) {
	if (ev.date.valueOf() < checkin.date.valueOf()) {
		checkout.setValue(checkin.date.valueOf());
	}
    checkout.hide();
}).data('datepicker');