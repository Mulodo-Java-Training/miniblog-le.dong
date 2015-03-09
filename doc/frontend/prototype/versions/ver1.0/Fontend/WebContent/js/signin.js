function signin(){
		if($('#username').val()=="" || $('#password').val()==""){return;};
		var data = "username="+$('#username').val()+"&password="+$('#password').val();
      	
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
				document.cookie = 'token='+response['data']['access_token']+';';
//				document.cookie = 'userid='+response['data']['userId']+';';
				window.location = 'index.html';
			}
		}
		});
};

