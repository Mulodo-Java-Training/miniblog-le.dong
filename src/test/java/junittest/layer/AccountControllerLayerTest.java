package junittest.layer;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.AccountController;
import com.mulodo.miniblog.form.ChangePasswordForm;
import com.mulodo.miniblog.form.SignInform;
import com.mulodo.miniblog.form.SignUpForm;
import com.mulodo.miniblog.form.UpdateAccountForm;
import com.mulodo.miniblog.util.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/java/TestControllerContext.xml")
public class AccountControllerLayerTest {

	@Autowired
	private AccountController accountController;

	@Test
	public void AccountController_Register_1001() {
		SignUpForm form = new SignUpForm();
		Response respone = accountController.register(form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void AccountController_Register_2001() {
		SignUpForm form = new SignUpForm();
		form.username = "register2001";
		form.password = "";
		form.email = "";
		Response respone = accountController.register(form);
		assertEquals(Status.STATUS_2001, respone.getStatus());
	}

	@Test
	public void AccountController_Register_2009() {
		SignUpForm form = new SignUpForm();
		form.username = "register2009";
		form.password = "";
		form.email = "";
		Response respone = accountController.register(form);
		assertEquals(Status.STATUS_2009, respone.getStatus());
	}

	@Test
	public void AccountController_Register_200() {
		SignUpForm form = new SignUpForm();
		form.username = "register200";
		form.password = "register200";
		form.email = "register200@gmail.com";
		Response respone = accountController.register(form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void AccountController_Login_1001() {
		SignInform form = new SignInform();
		Response respone = accountController.login(form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void AccountController_Login_2002() {
		SignInform form = new SignInform();
		form.username = "login2002";
		form.password = "login2002";
		Response respone = accountController.login(form);
		assertEquals(Status.STATUS_2002, respone.getStatus());
	}

	@Test
	public void AccountController_Login_200() {
		SignInform form = new SignInform();
		form.username = "login200";
		form.password = "login200";
		Response respone = accountController.login(form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void AccountController_GetInfo_2006() {
		Response respone = accountController.getInfo(9999);
		assertEquals(Status.STATUS_2006, respone.getStatus());
	}

	@Test
	public void AccountController_GetInfo_200() {
		Response respone = accountController.getInfo(1);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void AccountController__Search_CheckExpiredToken_2008() {
		Response respone = accountController.search("expired", "hae");
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void AccountController__Search_1001() {
		Response respone = accountController.search("token", null);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void AccountController__Search_2007() {
		Response respone = accountController.search("token", "dong");
		assertEquals(Status.STATUS_2007, respone.getStatus());
	}

	@Test
	public void AccountController__Search_200() {
		Response respone = accountController.search("token", "hae");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void AccountController_Update_CheckExpiredToken_2008() {
		UpdateAccountForm form = new UpdateAccountForm();
		form.firstname = "";
		form.lastname = "";
		form.password = "";
		Response respone = accountController.update("expired", form);
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void AccountController_Update_1001() {
		UpdateAccountForm form = new UpdateAccountForm();
		Response respone = accountController.update("token", form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void AccountController_Update_2005() {
		UpdateAccountForm form = new UpdateAccountForm();
		form.lastname = "update2005";
		form.firstname = "update2005";
		form.password = "1234567";
		Response respone = accountController.update("token", form);
		assertEquals(Status.STATUS_2005, respone.getStatus());
	}

	@Test
	public void AccountController_Update_2004() {
		UpdateAccountForm form = new UpdateAccountForm();
		form.lastname = "update2004";
		form.firstname = "update2004";
		form.password = "123456";
		Response respone = accountController.update("token", form);
		assertEquals(Status.STATUS_2004, respone.getStatus());
	}

	@Test
	public void AccountController_Update_200() {
		UpdateAccountForm form = new UpdateAccountForm();
		form.lastname = "update200";
		form.firstname = "update200";
		form.password = "123456";
		Response respone = accountController.update("token", form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void AccountController_ChangePassword_1001() {
		ChangePasswordForm form = new ChangePasswordForm();
		Response respone = accountController.changePass("token", form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void AccountController_ChangePassword_CheckExpiredToken_2008() {
		ChangePasswordForm form = new ChangePasswordForm();
		form.old_password = "";
		form.new_password = "";
		Response respone = accountController.changePass("expired", form);
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void AccountController_ChangePassword_2005() {
		ChangePasswordForm form = new ChangePasswordForm();
		form.old_password = "1234567";
		form.new_password = "";
		Response respone = accountController.changePass("token", form);
		assertEquals(Status.STATUS_2005, respone.getStatus());
	}

	@Test
	public void AccountController_ChangePassword_2010() {
		ChangePasswordForm form = new ChangePasswordForm();
		form.old_password = "123456";
		form.new_password = "123456789";
		Response respone = accountController.changePass("token", form);
		assertEquals(Status.STATUS_2010, respone.getStatus());
	}

	@Test
	public void AccountController_ChangePassword_200() {
		ChangePasswordForm form = new ChangePasswordForm();
		form.old_password = "123456";
		form.new_password = "1234567";
		Response respone = accountController.changePass("token", form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void AccountController_Logout_CheckExpiredToken_2008() {
		Response respone = accountController.logout("expired");
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void AccountController_Logout__2003() {
		Response respone = accountController.logout("errortoken");
		assertEquals(Status.STATUS_2003, respone.getStatus());
	}

	@Test
	public void AccountController_Logout_200() {
		Response respone = accountController.logout("token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}
}
