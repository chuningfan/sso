function login() {
	var callback = window.location.search.replace("?CALLBACK_URL=", "");
	var data = {
	'loginName': $('#loginName').val(),
	'password': md5($("#password").val()),
	'validationCode': $('#validationCode').val(),
	'rememberMe': $('#rememberMe').val(),
	'callback': callback
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