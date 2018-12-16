var app ={
	init : function(){
		app.initializeToolTip();
	}
}
app.initializeToolTip = function(){
	$('[data-toggle="tooltip"]').tooltip();
}
$(document).ready(function(){
	app.init();
});

