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
		url: $url+"top",
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
function showpostsofuser(){
        $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"getpostsofuser",
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
function showallpostsbyuserbyid(userid){
        $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"getpostsbyuser/"+userid,
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

function getpostsbyid(postid){
    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"getposts/"+postid,
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
							<form name='addCommentForm' id='addCommentForm' onSubmit='addcomment("+response['data']['id']+"); return false;'>\
								<div class='row control-group'>\
									<div class='form-group col-xs-12 floating-label-form-group controls'>\
										<label>Comment</label>\
										<textarea rows='3' class='form-control' placeholder='Comment' id='commentofposts' ></textarea>\
										<p class='help-block text-danger'></p>\
									</div>\
								</div>\
								<br>\
								<p id='error' style='color:red;'></p>\
								<div class='row'>\
									<div class='form-group col-xs-12'>\
										<button type='submit' class='btn btn-default'>Add</button>\
									</div>\
								</div>\
							</form>\
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

$(function () {
	$("#dialogcreateposts").dialog({
        width:500,
		autoOpen: false,
        modal: true,
        closeOnEscape: false,
        title: "Create Posts",
        resizable: false,
        buttons: {
        	Save: function () {
        		if ($('#formcreateposts').valid()) {
                createposts($('#titleposts').val(), $('#contentposts').val());
                $(this).dialog('close');
        		}
            },
            Cancel: function () {
                $(this).dialog('close');
            }
        }
    });
	  $('#btncreatepost').on('click', function() {
		  	$('#formcreateposts').show();
		  	$('#titleposts').val("");
		  	$('#contentposts').val("");
		    $('#dialogcreateposts').dialog('open');
		  });
	$('#formcreateposts').validate({
	    rules: {
	      titleposts: {
	        required: true,
	        regex:"^[a-zA-Z0-9_-_ ]{10,100}$"
	      },
	      contentposts: {
		    required: true,
		    regex:"^[a-zA-Z0-9_-_\^\$\.\|\?\*\+\(\)\\~`\!@#\-_+={}'>:;, ]{10,2048}$",
		  }
	    }
	  });
	});
function createposts(title,content){
	var data = "title="+title+"&content="+content;
        $.ajax({
		type: "POST",
		data: data,
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url +"create",
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
	$("#dialogupdateposts").dialog({
		width:500,
    	top:100,
        autoOpen: false,
        modal: true,
        title: "Edit Posts",
        resizable: false,
        buttons: {
        	Edit: function () {
        		if ($('#formupdateposts').valid()) {
                updateposts(id, $('#titleupdateposts').val(), $('#contentupdateposts').val());
                $(this).dialog('close');
        		}
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
            url: $url+"getposts/"+id,
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
    		  	$('#formupdateposts').show();
    		  	$('#titleupdateposts').val(response['data']['title']);
    		  	$('#contentupdateposts').val(response['data']['content']);
                $("#dialogupdateposts").dialog("open");
            }
        });
    $('#formupdateposts').validate({
	    rules: {
	      titleupdateposts: {
	        required: true,
	        regex:"^[a-zA-Z0-9_-_\^\$\.\|\?\*\+\(\)\\~`\!@#\-_+={}'>:;, ]{10,100}$"
	      },
	      contentupdateposts: {
	    	required: true,
		    regex:"^[a-zA-Z0-9_-_\^\$\.\|\?\*\+\(\)\\~`\!@#\-_+={}'>:;, ]{10,2048}$",
		  }
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
		url: $url+"updateposts/"+id,
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
		url: $url+"deleteposts/"+id,
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
		var comment = $('#commentofposts').val();
		if(!validateComment(comment)){
			$("#error").text(" 10 character < commment < 254 character and not containt '%&<'"); 
		}else
		{
			$("#error").text("");
		}
		if(validateComment(comment))
		{
		var data = "id_posts="+id_posts+"&comment="+comment;
      	$.ajax({
		type: "POST",
		beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		data: data,
		url: $url+"posts/"+id_posts+"/comments/add",
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
				$('#commentofposts').val("");
				listcomment(id_posts);
				return;
			}
		}
		});
		}
		return false;
	
	}
function listcomment(id_posts){
	    $.ajax({
		type: "GET",
        beforeSend: function (request)
            {
                request.setRequestHeader("token", getCookie('token'));
            },
		url: $url+"posts/"+id_posts+"/comments/getcommentsofposts",
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
    $("#dialogeditcomment").dialog({
    	width:500,
    	top:100,
        autoOpen: false,
        modal: true,
        title: "Edit Comment",
        resizable: false,
        buttons: {
        	Edit: function () {
        		if ($('#formeditcomment').valid()) {
                editcomment(id_posts, id, $('#commentforposts').val());
                $(this).dialog('close');
        		}
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
            url: $url+"posts/comment/getcomment/"+id,
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
    		  	$('#formeditcomment').show();
    		  	$('#commentforposts').val(response['data']['comment']);
                $("#dialogeditcomment").dialog("open");
            }
        });
    $('#formeditcomment').validate({
	    rules: {
	    	commentforposts: {
	    	required: true,
		    regex:"^[a-zA-Z0-9_-_\^\$\.\|\?\*\+\(\)\\~`\!@#\-_+={}'>:;, ]{10,254}$",
		  }
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
		url: $url+"posts/"+id_posts+"/comments/update/"+id,
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
	url: $url+"posts/"+id_posts+"/comments/delete/"+id,
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

$.validator.addMethod(
        "regex",
        function(value, element, regexp) {
            var re = new RegExp(regexp);
            return this.optional(element) || re.test(value);
        },
        "Invalid regex exception!!!"
);

function validateComment(comment) {
    var re = /^[a-zA-Z0-9_-_\^\$\.\|\?\*\+\(\)\\~`\!@#\-_+={}'>:;, ]{10,254}$/;
    return re.test(comment);
}

