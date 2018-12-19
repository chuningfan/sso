$(document).ready(function() {
		var url = "/validate.do"
		var query = window.location.search;
		window.location.href = window.location.protocol + "//" + window.location.host + url + query;
});