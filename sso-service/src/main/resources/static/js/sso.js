function login() {
	var data = {
	'loginName': $('#loginName').val(),
	'password': md5($("#password").val()),
	'validationCode': $('#validationCode').val(),
	'rememberMe': $('#rememberMe').val(),
	};
	var options = {
		url : "/sso/login",
		type : "post",
		data: data,
		success : function(res) {
			switch (res.code) {
			case -1:
				alert("System is busy, please try again later.")
				break;
			case 1:
				jump('/sso/jump');
				break;
			case -99:
				showError("Incorrect invalidation code!");
				break;
			case 0:
				showError("Incorrect login name or password!");
				break;
			case 99:
				showError("Account was locked, please contact with the administrator!");
				break;
			}
		},
		dataType : "json",
		timeout : 30000
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