package junittest.layer.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.form.SignInform;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class TokenLayerServiceTest {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AccountService accountService;
	
	private static Token token;
	
	@Before
	public void setUp() {
		SignInform form = new SignInform();
		form.username = "user";
		form.password = "e10adc3949ba59abbe56e057f20f883e";
		token = accountService.login(form.username, form.password);
	}

	@After
	public void out() {
		accountService.logout(token.getAccount().getId());
	}
	
	@Test
	public void TokenService_Create_Delete()
	{
		Token t = new Token();
		t.setAccess_token(Util.randomString());
		t.setAccount(token.getAccount());
		assertEquals(true, tokenService.create(t));
		assertEquals(true, tokenService.delete(token.getAccount().getId()));
		assertEquals(true, tokenService.delete(-1));
	}
	@Test
	public void TokenService_GetToken()
	{
		String accesstoken = "VHYCH13a4cInQGzx0zdy6RBfOFOd1BS4NIuuviJf";
		Token t = tokenService.getTokenByAccesstoken(accesstoken);
		assertEquals(3,t.getAccount().getId());
	}
}
