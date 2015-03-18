package returnform;

public class AccessToken {
	public String access_token;
	public int userid;
	public String username;

	public AccessToken(String access_token,int userid,String username) {
		super();
		this.access_token = access_token;
		this.userid = userid;
		this.username = username;
	}
	
}
