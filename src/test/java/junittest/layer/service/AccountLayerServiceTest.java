package junittest.layer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.form.ChangePasswordForm;
import com.mulodo.miniblog.form.SignInform;
import com.mulodo.miniblog.form.SignUpForm;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class AccountLayerServiceTest {

	@Autowired
	private AccountService accountService;

	@Test
	public void AccountService_Register() {

		assertEquals(false, accountService.deleteUser("donghaetest"));
		SignUpForm form = new SignUpForm();
		form.username = "donghaetest";
		form.email = "test@mulodo.com";
		form.password = "123456";
		assertEquals(true, accountService.register(form.setData()));
		assertEquals(false, accountService.checkUser("donghaetest"));
		assertEquals(true, accountService.deleteUser("donghaetest"));
		assertEquals(true, accountService.checkUser("donghaetest"));

	}

	@Test
	public void AccountService_Login_Logout_Update_ChangepassUser() {

		assertEquals(false, accountService.deleteUser("donghaetest"));
		SignUpForm form = new SignUpForm();
		form.username = "donghaetest";
		form.email = "test@mulodo.com";
		form.password = "123456";
		assertEquals(true, accountService.register(form.setData()));

		SignInform userlogin = new SignInform();
		userlogin.username = "donghaetest";
		userlogin.password = "e10adc3949ba59abbe56e057f20f883e";

		Token token = accountService
				.login(userlogin.username, userlogin.password);
		Account acc = token.getAccount();
		acc.setFirstname("le van");
		acc.setLastname("teo");

		assertEquals(true, accountService.update(acc));

		Account account = accountService.getInfo(token.getAccount().getId());
		assertEquals(account.getFirstname(), "le van");
		assertEquals(account.getLastname(), "teo");

		userlogin.password = "testpassfalse";
		assertEquals(null,
				accountService.login(userlogin.username, userlogin.password));

		ChangePasswordForm userpass = new ChangePasswordForm();
		userpass.old_password = "testpassfalse";
		userpass.new_password = "testpassfalse";
		assertEquals(null, accountService.changePassword(-1,
				userpass.old_password, userpass.new_password));
		userpass.old_password = "e10adc3949ba59abbe56e057f20f883e";
		Token token11 = accountService.changePassword(token.getAccount().getId(),
				userpass.old_password, userpass.new_password);
		

		assertEquals(true, accountService.logout(token.getAccount().getId()));
		assertEquals(true, accountService.deleteUser("donghaetest"));
	}

	@Test
	public void AccountService_GetUser() {

		assertEquals(false, accountService.deleteUser("donghaetest"));
		SignUpForm form = new SignUpForm();
		form.username = "donghaetest";
		form.email = "test@mulodo.com";
		form.password = "123456";
		assertEquals(true, accountService.register(form.setData()));

		SignInform userlogin = new SignInform();
		userlogin.username = "donghaetest";
		userlogin.password = "e10adc3949ba59abbe56e057f20f883e";

		Token t = new Token();
		t.setAccess_token("123456789");

		Token token = accountService
				.login(userlogin.username, userlogin.password);

		Account test = accountService.findByUsername("donghaetest");
		assertEquals("test@mulodo.com", test.getEmail());
		assertTrue(accountService.searchByName("hae").size() > 1);

		assertEquals(true, accountService.logout(token.getAccount().getId()));
		assertEquals(true, accountService.deleteUser("donghaetest"));
	}
}
