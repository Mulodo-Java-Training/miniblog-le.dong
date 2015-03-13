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
		url: "http://localhost:8080/MiniBlog/profile",
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
		url: "http://localhost:8080/MiniBlog/getuser/"+id,
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
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Firstname</label> <input type='text' class='form-control' placeholder='Firstname' id='firstname' value='"+$firstname+"'>\
							<p class='help-block text-danger'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Lastname</label> <input type='text' class='form-control' placeholder='Lastname' id='lastname' value='"+$lastname+"' >\
							<p class='help-block text-danger'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Password</label> <input type='password' class='form-control' placeholder='Password' id='password' required data-validation-required-message='Please enter a password !'>\
							<p class='help-block text-danger'></p>\
						</div>\
					</div>\
					<br>\
					<div id='success'></div>\
					<div class='row'>\
						<div class='form-group col-xs-12'>\
							<button  type='submit' onclick='update();' class='btn btn-default'>Update</button>\
						</div>\
					</div>\
				");
	}
function update(){
	if($('#firstname')=="" || $('#lastname') =="" || $('#password').val()==""){alert("info and password may not be null");return;};
		var data = "lastname="+$('#lastname').val()+"&firstname="+$('#firstname').val()+"&password="+$('#password').val();
		$.ajax({
		type: "PUT",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/updateuser",
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

function formchangepass(){
	$('#header').html("Change Passsword");
	$('#content').html("\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Old Password</label> <input type='password' class='form-control' placeholder='Old Password' id='old_password'  required data-validation-required-message='Please enter old password !'>\
							<p class='help-block text-danger'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>New Password</label> <input type='password' class='form-control' placeholder='New Password' id='new_password' required data-validation-required-message='Please enter new password !'>\
							<p class='help-block text-danger'></p>\
						</div>\
					</div>\
					<div class='row control-group'>\
						<div class='form-group col-xs-12 floating-label-form-group controls'>\
							<label>Retype Password</label> <input type='password' class='form-control' placeholder='Retype Password' id='retype_password' required data-validation-required-message='Please enter retype password !'>\
							<p class='help-block text-danger'></p>\
						</div>\
					</div>\
					<br>\
					<div id='success'></div>\
					<div class='row'>\
						<div class='form-group col-xs-12'>\
							<button  type='submit' onclick='changepass();' class='btn btn-default'>Submit</button>\
						</div>\
					</div>\
				");
	}
function changepass(){
	if($('#old_password').val()=="" || $('#new_password').val()=="" || $('#retype_password').val()==""){alert("password must not be null");return;}
	if($('#new_password').val()!=$('#retype_password').val()){alert("retype password must match");return;}
	var data = "old_password="+$('#old_password').val()+"&new_password="+$('#new_password').val();
	$.ajax({
		type: "PUT",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/changepass",
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

function logout() {
  	$.ajax({
	type: "POST",
    beforeSend: function (request)
        {
            request.setRequestHeader("token", getCookie('token'));
         
        },
	url: "http://localhost:8080/MiniBlog/logout",
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


