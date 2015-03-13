$idposts= "";
$idcomment = "";
$title = "";
$contentposts = "";
$comment = "";
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
function showpostsofuser(){
        $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/getpostsofuser",
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
				alert("no a posts of user");
				window.location = "index.html";
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
				$('#header').html("All Posts Of User");
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
function showallpostsbyuserbyid(userid){
        $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/getpostsbyuser/"+userid,
        statusCode: {
			1001: function() {
				alert( "validate error" );
				return;
			},
			3006: function() {
				alert( "no result" );
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
				$('#header').html("All Posts Of User");
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

function getpostsbyid(postid){
    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/getposts/"+postid,
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
			3005: function() {
				alert( "invalid post id!" );
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
				$idposts = response['data']['id'];
				$title = response['data']['title'];
				$contentposts = response['data']['content'];
				$('#header').html(""+response['data']['title']);
				$('#content').html("\
				<p class='post-meta' style='text-align: center;'>Posted by <a href='#' onclick='userinfobyid("+response['data']['account']['id']+");'>"+response['data']['account']['username']+"</a> on "+response['data']['create_at']+"</p>\
				<a href='#' onclick='formupdateposts("+response['data']['id']+");' class='btn btn-default' style='margin-left: 270px;margin-right: 20px;'>Edit</a>\
				<a href='#' onclick='deleteposts("+response['data']['id']+");' class='btn btn-default'>Delete</a>\
				<div class='row'>\
					<div class='col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1'>\
						<p>"+response['data']['content']+"</p>\
						<hr>\
						<div class='comment' id='comment'>\
							<div name='addCommentForm' id='addCommentForm'>\
								<div class='row control-group'>\
									<div class='form-group col-xs-12 floating-label-form-group controls'>\
										<label>Comment</label>\
										<textarea rows='3' class='form-control' placeholder='Comment' id='commentofposts' ></textarea>\
										<p class='help-block text-danger'></p>\
									</div>\
								</div>\
								<br>\
								<div id='success'></div>\
								<div class='row'>\
									<div class='form-group col-xs-12'>\
										<button type='submit' onclick='addcomment("+response['data']['id']+");' class='btn btn-default'>Add</button>\
									</div>\
								</div>\
							</div>\
							<div class='comment-list'>\
								<div class='col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1'>\
									<div id='comments'><a href='#' onclick='listcomment("+response['data']['id']+");' class='btn btn-show' style='width: 400px;margin-right: -65px;margin-top: 0px;'>Show All Comment</a>\</div>\
									<hr>\
								</div>\
							</div>\
						</div>\
					</div>\
				</div>\
                ");
			}
		}
		});
	}

function formcreateposts(){
	$("#dialog").dialog({
        autoOpen: false,
        modal: true,
        title: "Create Posts",
        resizable: false,
        buttons: {
        	Save: function () {
                createposts($('#title').val(), $('#contentposts').val());
                $(this).dialog('close');
            },
            Cancel: function () {
                $(this).dialog('close');
            }
        }
    });
	$("#dialog").html("\
    		<form>\
    		<label>Title</label>\
    		<input type='text' id='title'  type='text' value=''/>\
    		<label>Comment</label>\
    		<textarea rows='2' id='contentposts'  type='text'></textarea>\
    		</form>\
     ");
    $("#dialog").dialog("open");
	}
function createposts(title,content){
	var data = "title="+title+"&content="+content;
        $.ajax({
		type: "POST",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/create",
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
			3001: function() {
				alert( "post create fail!" );
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
				alert("create posts success!");
				toppost();
			}
		}
		});
	}
function formupdateposts(id){
	$("#dialog").dialog({
        autoOpen: false,
        modal: true,
        title: "Edit Posts",
        resizable: false,
        buttons: {
        	Edit: function () {
                updateposts(id, $('#title').val(), $('#contentposts').val());
                $(this).dialog('close');
            },
            Cancel: function () {
                $(this).dialog('close');
            }
        }
    });
    
    $.ajax({
            type: "GET",
            beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
             
            },
            url: "http://localhost:8080/MiniBlog/getposts/"+id,
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
    			3005: function() {
    				alert( "invalid posts id!" );
    				return;
    			}
    		},
            success: function (response) {
                $("#dialog").html("\
                		<form>\
                		<label>Title</label>\
                		<input type='text' id='title'  type='text' value='"+response['data']['title']+"'/>\
                		<label>Comment</label>\
                		<textarea rows='2' id='contentposts'  type='text'>"+response['data']['content']+"</textarea>\
                		</form>\
                 ");
                $("#dialog").dialog("open");
            }
        });
	}
function updateposts(id,title,content){
	var data = "id="+id+"&title="+title+"&content="+content;
        $.ajax({
		type: "PUT",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/updateposts/"+id,
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
			3005: function() {
				alert( "invalid id posts" );
				return;
			},
			3008: function() {
				alert( "posts don't of you!");
				return;
			},
			3003: function() {
				alert( "update post fail!" );
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
				alert("update posts success!");
				getpostsbyid(id);
			}
		}
		});
	}
function deleteposts(id){
	var data = "id="+id;
        $.ajax({
		type: "DELETE",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/deleteposts/"+id,
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
			3005: function() {
				alert( "invalid id posts" );
				return;
			},
			3008: function() {
				alert( "posts don't of you!");
				window.location = 'index.html';
				return;
			},
			3004: function() {
				alert( "delete post fail!" );
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
				alert("delete posts success!");
				toppost();
			}
		}
		});
	}
function addcomment(id_posts){
		var data = "id_posts="+id_posts+"&comment="+$('#commentofposts').val();
      	$.ajax({
		type: "POST",
		beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		data: data,
		url: "http://localhost:8080/MiniBlog/posts/"+id_posts+"/comments/add",
        statusCode: {
			1001: function() {
				alert( "validate error" );
				window.location.reload();
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
			3005: function() {
				alert( "invalid post id!" );
				return;
			},
			4006: function() {
				alert( "add comment fail" );
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
				alert( "comment added!" );
				listcomment(id_posts);
				return;
			}
		}
		});
	
	}
function listcomment(id_posts){
	    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: "http://localhost:8080/MiniBlog/posts/"+id_posts+"/comments/getcommentsofposts",
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
			4006: function() {
				alert( "invalid post id!" );
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
				$('#comments').html("");
				$.each(response['data'], function(key,value) {
				$('#comments').append("\
				<div class='comment-preview'>\
					<div class='avatar'>\
						<div class='user'>\
							<img src='//a.disquscdn.com/1424976780/images/noavatar92.png' class='user' alt='Avatar'>\
						</div>\
					</div>\
					<p class='comment-meta'>comment on "+value['create_at']+"  | author: <a href='#' onclick='userinfobyid("+value['account']['id']+");'>"+value['account']['username']+"</a>\
						<li class='dropdown' style='float: right; margin-top: -60px; list-style-type: none;margin-right: -120px;'><a class='dropdown-toggle' data-toggle='dropdown' role='button' aria-expanded='false'> <img src='img/edit_32.png' alt='Edit or Delete' width='20px;' height='20px;' /></a>\
							<ul class='dropdown-menu' role='menu'>\
								<li><a href='#' onclick='formedit("+value['id']+","+id_posts+");'>Edit</a></li>\
								<li><a href='#' onclick='deletecomment("+value['id']+","+id_posts+");'>Delete</a></li>\
							</ul>\
						</li>\
					</p>\
					<p class='comment-content'>"+value['comment']+"</p>\
				</div>\
					");
				});
			}
		}
		});
	}

function formedit(id,id_posts) {
    $("#dialog").dialog({
        autoOpen: false,
        modal: true,
        title: "Edit Comment",
        resizable: false,
        buttons: {
        	Edit: function () {
                editcomment(id_posts, id, $('#commentdialog').val());
                $(this).dialog('close');
            },
            Cancel: function () {
                $(this).dialog('close');
            }
        }
    });
    
    $.ajax({
            type: "GET",
            beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
             
            },
            url: "http://localhost:8080/MiniBlog/posts/comment/getcomment/"+id,
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
    			4004: function() {
    				alert( "invalid comment id!" );
    				return;
    			}
    		},
            success: function (response) {
                $("#dialog").html("\
                		<form>\
                		<label>Comment</label>\
                		<textarea rows='2' id='commentdialog'  type='text'>"+response['data']['comment']+"</textarea>\
                		</form>\
                 ");
                $("#dialog").dialog("open");
            }
        });
};
function editcomment(id_posts,id,comment){
		var data = "id_posts="+id_posts+"&id="+id+"&comment="+comment;
      	$.ajax({
		type: "PUT",
		beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		data: data,
		url: "http://localhost:8080/MiniBlog/posts/"+id_posts+"/comments/update/"+id,
        statusCode: {
			1001: function() {
				alert( "validate error" );
				window.location.reload();
				return;
			},
			2008: function() {
				alert( "token is expired!" );
				var now = new Date();
				document.cookie = 'token=;expires='+now.toGMTString()+';';
				document.cookie = 'userid=;expires='+now.toGMTString()+';';
				window.location = 'login.html';
				return;
			},
			4004: function() {
				alert( "invalid comment id" );
				return;
			},
			4009: function() {
				alert( "you don't author comment!" );
				return;
			},
			4002: function() {
				alert( "edit comment fail" );
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
				alert( "comment edited!" );
				listcomment(id_posts);
				return;
			}
		}
		});
	}

function deletecomment(id,id_posts){
	var data = "id_posts="+id_posts+"&id="+id;
  	$.ajax({
	type: "DELETE",
	beforeSend: function (request)
        {
            request.setRequestHeader("token", getCookie('token'));
        },
	data: data,
	url: "http://localhost:8080/MiniBlog/posts/"+id_posts+"/comments/delete/"+id,
    statusCode: {
		1001: function() {
			alert( "validate error" );
			window.location.reload();
			return;
		},
		2008: function() {
			alert( "token is expired!" );
			var now = new Date();
			document.cookie = 'token=;expires='+now.toGMTString()+';';
			document.cookie = 'userid=;expires='+now.toGMTString()+';';
			window.location = 'login.html';
			return;
		},
		4004: function() {
			alert( "invalid comment id" );
			return;
		},
		4009: function() {
			alert( "you don't author comment!" );
			return;
		},
		4003: function() {
			alert( "delete comment fail" );
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
			alert( "comment deleted!" );
			listcomment(id_posts);
			return;
		}
	}
	});
}
