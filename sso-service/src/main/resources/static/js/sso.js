function login() {
	var data = {
	'loginName': $('#loginName').val(),
	'password': md5($("#password").val()),
	'validationCode': $('#validationCode').val(),
	'rememberMe': $('#rememberMe').val(),
	'callback': getParam('callback'),
	'service_id': getParam('service_id')
	};
	var options = {
		url : "/sso/login",
		type : "post",
		data: data,
		success : function(res) {
			window.location.href=res.url;
		},
		dataType : "json"
	};
	$.ajax(options);
}

function getParam(p_name) {
	var query = window.location.search.replace('?', '');
	var array = query.split('&');
	for (var i = 0; i < array.length; i++) {
		var str = array[i];
		var map = str.split('=');
		if (map[0] === p_name) {
			return map[1];
		}
	}
	return null;
}

function jump(url) {
	var url = document.location.origin + url;
	document.location.href = url;
}

function showError(msg) {
	$("#msg").html(msg);
}

function getVerify(obj) {
	obj.src = document.location.origin + "/validation/code?" + Math.random();
}

function showCover() {
	$("#coverer").show();
}

function stopCover() {
	$("#coverer").hide();
}