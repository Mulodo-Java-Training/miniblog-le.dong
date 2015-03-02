package com.mulodo.miniblog.util;

public class ReturnFormat {
	public meta meta = new meta();
	public Object data;

	public class meta {
		public int error_code;
		public String description;

		public void code(int code) {
			switch (code) {
			case 200:
				error_code = 200;
				break;
			case 1001:
				error_code = 1001;
				description = "Validate data error!";
				break;
			case 2001:
				error_code = 2001;
				description = "username aready existed!";
				break;
			case 2002:
				error_code = 2002;
				description = "incorrect username or password!";
				break;
			case 2003:
				error_code = 2003;
				description = "logout failled!";
				break;
			case 2004:
				error_code = 2004;
				description = "update user failled!";
				break;
			case 2005:
				error_code = 2005;
				description = "old password not match!";
				break;
			case 2006:
				error_code = 2006;
				description = "id user not existed!";
				break;
			case 2007:
				error_code = 2007;
				description = "no result keyword name!";
				break;
			case 2008:
				error_code = 2008;
				description = "invalid access_token!";
				break;
			case 2009:
				error_code = 2009;
				description = "register failled!";
				break;
			case 2010:
				error_code = 2010;
				description = "change password failled!";
				break;
			case 2011:
				error_code = 2011;
				description = "expired token!please login!";
				break;
			case 3001:
				error_code = 3001;
				description = "create posts failled!";
				break;
			case 3002:
				error_code = 3002;
				description = "active/deactive posts failled!";
				break;
			case 3003:
				error_code = 3003;
				description = "update posts failled!";
				break;
			case 3004:
				error_code = 3004;
				description = "delete posts failled!";
				break;
			case 3005:
				error_code = 3005;
				description = "id posts not existed!";
				break;
			case 3006:
				error_code = 3006;
				description = "account not existed!";
				break;
			case 3007:
				error_code = 3007;
				description = "no result with keyword content!";
				break;
			case 3008:
				error_code = 3008;
				description = "posts's not of account!";
				break;
			case 4001:
				error_code = 4001;
				description = "add comments failled!";
				break;
			case 4002:
				error_code = 4002;
				description = "edit comments failled!";
				break;
			case 4003:
				error_code = 4003;
				description = "delete comments failled!";
				break;
			case 4004:
				error_code = 4004;
				description = "id comments not existed!";
				break;
			case 4005:
				error_code = 4005;
				description = "posts not existed!";
				break;
			case 4006:
				error_code = 4006;
				description = "account not existed!";
				break;
			case 4007:
				error_code = 4007;
				description = "no found result!";
				break;
			case 4008:
				error_code = 4008;
				description = "comments's not of posts!";
				break;
			case 4009:
				error_code = 4009;
				description = "comments's not of account!";
				break;
			case 9001:
				error_code = 9001;
				description = "System error!";
				break;
			default:
				error_code = 9999;
				description = "Error code!";
				break;
			}
		}
	}

}
