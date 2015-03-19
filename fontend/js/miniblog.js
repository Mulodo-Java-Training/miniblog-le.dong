$firstname="";
$lastname="";
$username="";
$email="";
$create_at="";
$modified_at="";
function profile(){
	    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"profile",
        statusCode: {
			1001: function() {
				alert( "validate error" );
				return;
			},
			2008: function() {
				alert( "token is expired!" );
				window.location = 'signin.html';
				return;
			},
        	2009: function() {
				alert( "invalid user id" );
				var now = new Date();
				document.cookie = 'token=;expires='+now.toGMTString()+';';
				document.cookie = 'userid=;expires='+now.toGMTString()+';';
				window.location = 'signin.html';
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
				$firstname=response['data']['firstname'];
				$lastname=response['data']['lastname'];
				$username=response['data']['username'];
				$email=response['data']['email'];
				$create_at=response['data']['create_at'];
				$modified_at=response['data']['modified_at'];
				$('#header').html("Profile");
				$('#content').html("\
				<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Username : <span class='label label-info'>"+$username+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Email : <span class='label label-info'>"+$email+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Firstname : <span class='label label-info'>"+$firstname+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Lastname : <span class='label label-info'>"+$lastname+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Create Date : <span class='label label-info'>"+$create_at+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Modified Date : <span class='label label-info'>"+$modified_at+"</span>\
							</h4>\
						</div>\
					</div>\
					<br>\
					<div class='row'>\
						<div class='form-group col-xs-12'>\
							<a href='#' onclick='formupdate();'  class='btn btn-default'>Edit</a>\
							<a href='#' onclick='formchangepass();'  class='btn btn-default' style='width: 210px;'>Change Password</a>\
						</div>\
					</div>\
					");
                    
			}
		}
		});
	}
function userinfobyid(id){
	    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"getuser/"+id,
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
        	2006: function() {
				alert( "invalid user id" );
				var now = new Date();
				document.cookie = 'token=;expires='+now.toGMTString()+';';
				document.cookie = 'userid=;expires='+now.toGMTString()+';';
				window.location = 'index.html';
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
				$firstname=response['data']['firstname'];
				$lastname=response['data']['lastname'];
				$username=response['data']['username'];
				$email=response['data']['email'];
				$create_at=response['data']['create_at'];
				$modified_at=response['data']['modified_at'];
				$('#header').html("Infomartion User");
				$('#content').html("\
				<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Username : <span class='label label-info'>"+$username+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Email : <span class='label label-info'>"+$email+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Firstname : <span class='label label-info'>"+$firstname+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Lastname : <span class='label label-info'>"+$lastname+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Create Date : <span class='label label-info'>"+$create_at+"</span>\
							</h4>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<h4>\
								Modified Date : <span class='label label-info'>"+$modified_at+"</span>\
							</h4>\
						</div>\
					</div>\
					<br>\
					<div class='row'>\
						<div class='form-group col-xs-12'>\
							<a href='#' onclick='showallpostsbyuserbyid("+response['data']['id']+");' class='btn btn-default' style='width: 175px;'>Show All Posts</a>\
						</div>\
					</div>\
					");
                    
			}
		}
		});
	}
function formupdate(){
	$('#header').html("Update Info Users");
	$('#content').html("\
			<form onSubmit='update(); return false;'>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Firstname</label> <input type='text' class='form-control' placeholder='Firstname' id='firstname' value='"+$firstname+"'>\
							<p id='errorfirstname' style='color:red;'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Lastname</label> <input type='text' class='form-control' placeholder='Lastname' id='lastname' value='"+$lastname+"' >\
							<p id='errorlastname' style='color:red;'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Password</label> <input type='password' class='form-control' placeholder='Password' id='password''>\
							<p id='errorpassword' style='color:red;'></p>\
						</div>\
					</div>\
					<br>\
					<div id='success'></div>\
					<div class='row'>\
						<div class='form-group col-xs-12'>\
							<button  type='submit' class='btn btn-default'>Update</button>\
						</div>\
					</div>\
			</form>\
		");
	}
function update(){
	var firstname = $('#firstname').val();
	var lastname = $('#lastname').val();
	var password = $('#password').val();
	if(!validateFirstname(firstname)){
		$("#errorfirstname").text(" 1 character < firstname < 32 character and not containt special character"); 
	}else
	{
		$("#errorfirstname").text("");
	}
	if(!validateLastname(lastname)){
		$("#errorlastname").text(" 1 character < lastname < 32 character and not containt special character"); 
	}else
	{
		$("#errorlastname").text("");
	}
	if(!validatePassword(password)){
		$("#errorpassword").text(" 6 character < password < 72 character and not containt special character"); 
	}else
	{
		$("#errorpassword").text("");
	}
	if(validateFirstname(firstname) && validateLastname(lastname) && validatePassword(password))
	{
		var data = "lastname="+lastname+"&firstname="+firstname+"&password="+password;
		$.ajax({
		type: "PUT",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"updateuser",
        statusCode: {
			1001: function() {
				alert( "validate error" );
				return;
			},
			2005: function() {
				alert( "password not match!please enter retype password" );
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
        	2004: function() {
				alert( "update user failed" );
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
				profile();
			}
		}
		});
	}
	return false;
}

function formchangepass(){
	$('#header').html("Change Passsword");
	$('#content').html("\
			<form onSubmit='changepass(); return false;'>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Old Password</label> <input type='password' class='form-control' placeholder='Old Password' id='old_password'>\
							<p id='erroroldpassword' style='color:red;'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>New Password</label> <input type='password' class='form-control' placeholder='New Password' id='new_password' >\
							<p id='errornewpassword' style='color:red;'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Retype Password</label> <input type='password' class='form-control' placeholder='Retype Password' id='retype_password'>\
							<p id='errorretypepassword' style='color:red;'></p>\
						</div>\
					</div>\
					<br>\
					<div id='success'></div>\
					<div class='row'>\
						<div class='form-group col-xs-12'>\
							<button  type='submit' class='btn btn-default'>Submit</button>\
						</div>\
					</div>\
			</form>\
	");
}
function changepass(){
	var old_password = $('#old_password').val();
	var new_password = $('#new_password').val();
	var retype_password = $('#retype_password').val();
	
	if(!validatePassword(old_password)){
		$("#erroroldpassword").text(" 6 character < old_password < 72 character and not containt special character"); 
	}else
	{
		$("#erroroldpassword").text("");
	}
	if(!validatePassword(new_password)){
		$("#errornewpassword").text(" 6 character < new_password < 72 character and not containt special character"); 
	}else
	{
		$("#errornewpassword").text("");
	}
	if(!checkRetypePassword(new_password,retype_password)){
		$("#errorretypepassword").text(" retype_password not match new password"); 
	}else
	{
		$("#errorretypepassword").text("");
	}
	if(validatePassword(old_password) && validatePassword(new_password) && validatePassword(retype_password) && checkRetypePassword(new_password,retype_password))
	{
	
	var data = "old_password="+old_password+"&new_password="+new_password;
	$.ajax({
		type: "PUT",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"changepass",
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
			2005: function() {
				alert( "old password not match!" );
				return;
			},
        	2010: function() {
				alert( "change password failed" );
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
				alert( "change password of current user" );
				window.location = 'signin.html';
			}
		}
		});
	}
	return false;
}

function logout() {
  	$.ajax({
	type: "POST",
    beforeSend: function (request)
        {
            request.setRequestHeader("token", getCookie('token'));
         
        },
	url: $url+"logout",
    statusCode: {
		1001: function() {
			alert( "validate error" );
			window.location.reload();
			return;
		},
		2008: function() {
			alert( "token is expired!" );
			window.location = 'signin.html';
			return;
		},
    	2003: function() {
			alert( "logout error!" );
			window.location = 'signin.html';
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
			alert( "user logout success!" );
			window.location = 'signin.html';
		}
	}
	});
};
function getusername(){
	$('#getusername').html("Hi!"+getCookie("username")+"<span class='caret'>");
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

function validateFirstname(firstname) {
    var re = /[a-zA-Z0-9]{1,32}/;
    return re.test(firstname);
}

function validateLastname(lastname) {
    var re = /[a-zA-Z0-9]{1,32}/;
    return re.test(lastname);
}

function validatePassword(password) {
    var re = /[a-zA-Z0-9]{6,72}/;
    return re.test(password);
}

function checkRetypePassword(password,retype_password) {
    if(password == retype_password)
    {
    	return true;
    }
}


