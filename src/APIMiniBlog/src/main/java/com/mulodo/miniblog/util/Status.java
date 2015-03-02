package com.mulodo.miniblog.util;

public class Status {
	
	//validation
	
	public final static String USERNAME_STRING_RANGE = "[a-z0-9]{6,32}";

    public final static String PASSWORD_STRING_RANGE = "[A-Za-z0-9]{6,72}";

    public final static String FIRSTNAME_STRING_RANGE = "[A-Za-z0-9]{1,32}";

    public final static String LASTNAME_STRING_RANGE = "[A-Za-z0-9]{1,32}";

    public final static String ACCESS_TOKEN_STRING_RANGE = "[a-f0-9]{64}";

    public final static String USERNAME_SEARCH_RANGE = "[a-z0-9]{1,32}";

    public final static String POST_TITLE_STRING_RANGE = "[\\p{Print}&&[^~,]]{10,100}";

    public final static String POST_TITLE_SEARCH_RANGE = "[\\p{Print}&&[^~,]]{1,100}";

    public final static String POST_CONTENT_STRING_RANGE = "[\\p{Print}&&[^~,]]{10,2048}";

    public final static String COMMENT_STRING_RANGE = "[\\p{Print}&&[^~,]]{1,254}";

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	//successfully
	public static final int STATUS_200 = 200;
	
	//error: validate input data
	public static final int STATUS_1001 = 1001;
	//error: DAO
	public static final int STATUS_9001 = 9001;
	
	//error table account
	//error:username is existed
	public static final int STATUS_2001 = 2001;
	//error: username or password wrong
	public static final int STATUS_2002 = 2002;
	//error: logout failled
	public static final int STATUS_2003 = 2003;
	//error: update account failled
	public static final int STATUS_2004 = 2004;
	//error: oldpass not match
	public static final int STATUS_2005 = 2005;
	//error: id not existed
	public static final int STATUS_2006 = 2006;
	//error: username , lastname, firstname not containt keyword 'name'
	public static final int STATUS_2007 = 2007;
	//error: invalid or expired token
	public static final int STATUS_2008 = 2008;
	//error: register failled
	public static final int STATUS_2009 = 2009;
	//error: change password failled
	public static final int STATUS_2010 = 2010;
	
	//error table posts
	//error: create posts failled
	public static final int STATUS_3001 = 3001;
	//error: change STATUS failled.
	public static final int STATUS_3002 = 3002;
	//error: update posts failled
	public static final int STATUS_3003 = 3003;
	//error: delete posts failled
	public static final int STATUS_3004 = 3004;
	//error: id not existed
	public static final int STATUS_3005 = 3005;
	//error: account_id not existed
	public static final int STATUS_3006 = 3006;
	//error: not found result.
	public static final int STATUS_3007 = 3007;
	//error: posts is not of account
	public static final int STATUS_3008 = 3008;

	
	//error table comments
	//error:add comments failled
	public static final int STATUS_4001 = 4001;
	//error:edit comments failled
	public static final int STATUS_4002 = 4002;
	//error: delete comments failled
	public static final int STATUS_4003 = 4003;
	//error:id not existed
	public static final int STATUS_4004 = 4004;
	//error: account_id not existed
	public static final int STATUS_4005 = 4005;
	//error: posts_id not existed
	public static final int STATUS_4006 = 4006;
	//error: not found result.
	public static final int STATUS_4007 = 4007;
	//error: comments not of posts
	public static final int STATUS_4008 = 4008;
	//error: comments not of account
	public static final int STATUS_4009 = 4009;
	
}
