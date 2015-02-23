package junittest.resource;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;

@Service
public class AccountControllerTest implements AccountService{

	@Override
	public boolean register(Account acc) {
		switch (acc.getUsername()) {
		case "register2009":
			return false;
		}
		return true;
	}

	@Override
	public Account login(String username, String password) {
		Account a = new Account();
		Token t = new Token();
		switch (username) {
		case "login200":
			a.setUsername("login200");
			t.setAccess_token(null);
			break;

		default:
			a.setUsername(null);
			t.setAccess_token(null);
			break;
		}
		return a;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Account acc) {
		switch (acc.getUsername()) {
		case "update200":
			return true;
		}
		return false;
	}

	@Override
	public boolean createToken(Token t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkUser(String username) {
		switch (username) {
		case "register2001":
			return true;
		}
		return false;
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
		switch (username) {
		case "result":
			a.setUsername("result");
			return a;
		}
		return null;
	}

	@Override
	public Account checkToken(String accesstoken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkExpiredDate(String accesstoken) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date sumationExpiredDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteToken(String accesstoken) {
		// TODO Auto-generated method stub
		return false;
	}

}
