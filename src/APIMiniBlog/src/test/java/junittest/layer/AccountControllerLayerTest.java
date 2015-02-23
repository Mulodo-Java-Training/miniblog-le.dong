package junittest.layer;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.AccountController;
import com.mulodo.miniblog.form.SignUpForm;
import com.mulodo.miniblog.util.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/java/TestControllerContext.xml")
public class AccountControllerLayerTest {
	
	@Autowired
	private AccountController accountController;
	@Test
	public void AccountController_Register_2001()
    {

        SignUpForm form = new SignUpForm();
        form.username = "register2001";
        Response respone = accountController.register(form);
        assertEquals(Status.STATUS_2001, respone.getStatus());
    }
}
