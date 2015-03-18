package junittest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;

@Service
public class AccountControllerTest implements AccountService{

	@Override
	public boolean register(Account acc) {
		if(acc.getUsername().equals("register2009"))
			return false;
		return true;
	}

	@Override
	public Token login(String username, String password) {
		Account acc = new Account();
		Token token = new Token();
		if(username.equals("login200")) {
			acc.setUsername("login200");
			token.setAccess_token("login");
			token.setAccount(acc);
			return token;
		}
		return null;
	}

	@Override
	public Account getInfo(int id) {
		if(id == 1)
		{
			Account a = new Account();
			a.setId(1);
			a.setUsername("getinfo200");
			return a;
		}
		return null;
	}

	@Override
	public List<Account> searchByName(String name) {
		if(name.equals("hae"))
		{
			List<Account> accounts = new ArrayList<Account>();
			accounts.add(new Account(1, "le.dong", "e10adc3949ba59abbe56e057f20f883e", "dong", "hae", "le.dong@gmail.com",null , null, null, null, null));
			accounts.add(new Account(2, "le.dong2", "e10adc3949ba59abbe56e057f20f883e", "dong2", "hae", "le.dong2@gmail.com",null , null, null, null, null));
			return accounts;
		}
		return null;
	}

	@Override
	public boolean update(Account acc) {
		if(acc.getLastname().equals("update200")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkUser(String username) {
		if(username.equals("register2001")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean logout(int account_id) {
		if(account_id == 1)
			return true;
		return false;
	}

	@Override
	public Account findByUsername(String username) {
		Account a = new Account();
		if(username.equals("result")) {
			a.setUsername("result");
			return a;
		}
		return null;
	}

	@Override
	public Account checkToken(String accesstoken) {
		if(accesstoken.equals("token"))
		{
			Account a = new Account();
			a.setId(1);
			a.setPassword("e10adc3949ba59abbe56e057f20f883e");
			return a;
		}
		if(accesstoken.equals("errortoken"))
		{
			Account a = new Account();
			a.setId(0);
			return a;
		}
		return null;
	}

	@Override
	public Token changePassword(int id, String old_password,
			String new_password) {
	    Account acc = new Account();
	    acc.setId(id);
		if(new_password.equals("1234567"))
		{
		    Token token = new Token();
		    token.setAccount(acc);
			return token;
		}
		return null;
	}

	@Override
	public boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public boolean checkEmail(String email)
    {
        if(email.equals("register2001@gmail.com")) {
            return false;
        }
        return true;
    }

}
