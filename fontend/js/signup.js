function signup(){
	if($('#username').val()=="" || $('#password').val()=="" || $('#email').val()==""){return;};
	var data = "username="+$('#username').val()+"&password="+$('#password').val()+"&email="+$('#email').val();
  	
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
};