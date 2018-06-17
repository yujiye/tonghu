$("button[id*='button_status']").on('click', function() {
	var buttonObj = $("button[id*='button_status']");
	for(var i = 0; i < buttonObj.length; i++) {
		if (buttonObj[i] != this) {
			$(buttonObj[i]).html($(buttonObj[i]).text());
			$(buttonObj[i]).attr("class", "btn");
		} else {
			$(buttonObj[i]).html('<i class="icon-eye-open"></i>' + $(buttonObj[i]).text());
			$(buttonObj[i]).attr("class", "btn btn-fsjbut");
		}
		
	}
	var trObj = $("tr[id*='tr_status']");
	for(var i = 0; i < trObj.length; i++) {
		if($(this).attr("id") == "button_status_all") {
			$(trObj[i]).show();
		} else {
			if ($(this).attr("id").replace("button", "tr") != $(trObj[i]).attr("id")) {
				$(trObj[i]).hide();
			} else {
				$(trObj[i]).show();
			}
		}
	}
});