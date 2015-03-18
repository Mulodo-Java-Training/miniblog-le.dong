function signin(){
		var username = $('#username').val();
		var password = $('#password').val();
		
		if(!validateUsername(username)){
			$("#errorusername").text("username > 6 character and not containt special character!"); 
		}else
		{
			$("#errorusername").text("");
		}
		if(!validatePassword(password)){
			$("#errorpassword").text("password > 6 character and not containt special character!"); 
		}else
		{
			$("#errorpassword").text(""); 
		}
		
		if(validateUsername(username) && validatePassword(password))
		{
		var data = "username="+username+"&password="+password;
      	
      	$.ajax({
		type: "POST",
		data: data,
		url: "http://localhost:8080/MiniBlog/login",
        statusCode: {
			1001: function() {
				alert( "validate error" );
				window.location.reload();
				return;
			},
			2002: function() {
				alert( "invalid username or password" );
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
				alert( "login success!" );
				var now = new Date();
				var time = now.getTime();
				var expireTime = time + 1000*3600*12;
				now.setTime(expireTime);
				document.cookie = 'token='+response['data']['access_token']+';expires='+now.toGMTString()+';';
				document.cookie = 'userid='+response['data']['userid']+';expires='+now.toGMTString()+';';
				document.cookie = 'username='+response['data']['username']+';expires='+now.toGMTString()+';';
				window.onload = toppost();
				window.onload = getusername();
				window.location ="index.html";
			}
		}
		});
		}
      	
      	return false;
};

function toppost(){
    $.ajax({
	type: "GET",
    beforeSend: function (request)
        {
            request.setRequestHeader("token", getCookie('token'));
        },
	url: "http://localhost:8080/MiniBlog/top",
    statusCode: {
		1001: function() {
			alert( "validate error" );
			return;
		},
		2008: function() {
			alert( "token is expired!" );
			var now = new Date();
			document.cookie = 'token=;expires='+now.toGMTString()+';';
			document.cookie = 'userid=;expires='+now.toGMTString()+';';
			window.location = 'signin.html';
			return;
		},
		3006: function(){
			alert("null");
			formcreateposts();
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
			$('#header').html("Top Posts");
			$('#content').html("");
			$.each(response['data'], function(key,value) {
			$('#content').append("\
			<div class='post-preview'>\
				<a href='#' onclick='getpostsbyid("+value['id']+");'>\
					<h2 class='post-title'>"+value['title']+"</h2>\
				</a>\
				<p class='post-meta'>Posted by <a href='#' onclick='userinfobyid("+value['account']['id']+");'>"+value['account']['username']+"</a> on "+value['modified_at']+"</p>\
				<p class='post-subtitle'>"+value['content'].substring(0, 100)+"....</p>\
				<a href='#' onclick='getpostsbyid("+value['id']+");' class='btn btn-readmore'>Read more</a>\
			</div>\
			<hr>\
				");
			});
		}
	}
	});
}
function getusername(){
	return getCookie("username");
}
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0)==' ') c = c.substring(1);
	    if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
	}
	return "";
}

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

