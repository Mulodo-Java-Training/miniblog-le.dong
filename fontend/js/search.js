function searchuser(name){
	if(name==""){return;}
	    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/searchuser/"+name,
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
        	2007: function() {
				alert( "No result" );
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
				$('#header').html("Result");
				$('#content').html("");
				$.each(response['data'], function(key,value) {
				$('#content').append("\
				<div class='user-preview'>\
					<a href='#' onclick='userinfobyid("+value['id']+");'>\
						<h2 class='user-username'>"+value['username']+"</h2>\
						<a href='#' onclick='showallpostsbyuserbyid("+value['id']+");' class='btn btn-show'>Show All Posts</a>\
					</a>\
					<p class='user-meta'>Join Date: "+value['create_at']+"</p>\
					<p class='user-information'>\
						Full Name: "+value['lastname']+ value['firstname'] +" <br /> Email: "+value['email']+"<br />\
					</p>\
					<hr>\
				</div>\
					");
				});    
			}
		}
		});
	}
function searchposts(description){
    $.ajax({
	type: "GET",
    beforeSend: function (request)
        {
            request.setRequestHeader("token", getCookie('token'));
        },
	url: "http://localhost:8080/MiniBlog/searchposts/"+description,
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
			$('#header').html("Result Search keyword:"+description);
			$('#content').html("");
			$.each(response['data'], function(key,value) {
			$('#content').append("\
			<div class='post-preview'>\
				<a href='#' onclick='getpostsbyid("+value['id']+");'>\
					<h2 class='post-title'>"+value['title']+"</h2>\
				</a>\
				<p class='post-meta'>Posted by <a href='#' onclick='userinfobyid("+value['account']['id']+");'>"+value['account']['username']+"</a> on "+value['modified_at']+"</p>\
				<p class='post-subtitle'>"+value['content']+"....</p>\
				<a href='#' onclick='getpostsbyid("+value['id']+");' class='btn btn-readmore'>Read more</a>\
			</div>\
			<hr>\
				");
			});
		}
	}
	});
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


