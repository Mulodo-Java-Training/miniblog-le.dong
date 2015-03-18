function signup(){
	var username = $('#username').val();
	var password = $('#password').val();
	var email = $('#email').val();

	if(!validateUsername(username)){
		$("#errorusername").text("username > 6 character and not containt special character!"); 
	}
	else
	{
		$("#errorusername").text("");
	}
	if(!validateEmail(email)){
		$("#erroremail").text("invalid format email!eg:email@gmail.com"); 
	}
	else
	{
		$("#erroremail").text("");
	}
	if(!validatePassword(password)){
		$("#errorpassword").text("password > 6 character and not containt special character!"); 
	}
	else
	{
		$("#errorpassword").text("");
	}
	
	if(validateUsername(username) && validatePassword(password) && validatePassword(email))
	{
	var data = "username="+username+"&password="+password+"&email="+email;
	
  	$.ajax({
	type: "POST",
	data: data,
	url: "http://localhost:8080/MiniBlog/register",
    statusCode: {
		1001: function() {
			alert( "validate error" );
			window.location.reload();
			return;
		},
		2001: function() {
			alert( "username is existed!" );
			window.location.reload();
			return;
		},
		2009: function() {
			alert( "register is vailid!" );
			window.location.reload();
			return;
		},
		2011: function() {
			alert( "email is existed!" );
			window.location.reload();
			return;
		}
	},
	success: function (response, status, xhr) {
		if(status!="success"){
			alert( "unknown error" );
			window.location.reload();
			return;
		}
		var ct = xhr.getResponseHeader("content-type") || "";
		if (ct.indexOf('json') > -1) {
			alert( "register success!" );
			window.location = 'signin.html';
		}
	}
	});
	}
	return false;
};

function validateUsername(username) {
    var re = /[a-zA-Z0-9]{6,32}/;
    return re.test(username);
}

function validatePassword(password) {
    var re = /[a-zA-Z0-9]{6,32}/;
    return re.test(password);
}

function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}