package junittest.layer;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.CommentsController;
import com.mulodo.miniblog.form.AddCommentsForm;
import com.mulodo.miniblog.form.EditCommentsForm;
import com.mulodo.miniblog.util.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/java/TestControllerContext.xml")
public class CommentsControllerLayerTest {

	@Autowired
	private CommentsController commentsController;

	@Test
	public void CommentsController_AddComments_200() {
		AddCommentsForm form = new AddCommentsForm();
		form.comment = "addcomments200";
		Response respone = commentsController.add(1, "token", form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void CommentsController_AddComments_1001() {
		AddCommentsForm form = new AddCommentsForm();
		form.comment = null;
		Response respone = commentsController.add(0, "token", form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void CommentsController_AddComments_2008() {
		AddCommentsForm form = new AddCommentsForm();
		form.comment = "addcomments200";
		Response respone = commentsController.add(1, "expired", form);
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void CommentsController_AddComments_4006() {
		AddCommentsForm form = new AddCommentsForm();
		form.comment = "addcomments4006";
		Response respone = commentsController.add(1, "token", form);
		assertEquals(Status.STATUS_4006, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_200() {
		EditCommentsForm form = new EditCommentsForm();
		form.comment = "editcomments";
		Response respone = commentsController.update(1, 1, form, "token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_1001() {
		EditCommentsForm form = new EditCommentsForm();
		Response respone = commentsController.update(1, 1, form, "token");
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_2008() {
		EditCommentsForm form = new EditCommentsForm();
		form.comment = "editcomments";
		Response respone = commentsController.update(1, 1, form, "expired");
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_4002() {
		EditCommentsForm form = new EditCommentsForm();
		form.comment = "editcomments";
		Response respone = commentsController.update(2, 1, form, "token");
		assertEquals(Status.STATUS_4002, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_4004() {
		EditCommentsForm form = new EditCommentsForm();
		form.comment = "editcomments";
		Response respone = commentsController.update(4, 1, form, "token");
		assertEquals(Status.STATUS_4004, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_4008() {
		EditCommentsForm form = new EditCommentsForm();
		form.comment = "editcomments";
		Response respone = commentsController.update(3, 1, form, "token");
		assertEquals(Status.STATUS_4008, respone.getStatus());
	}

	@Test
	public void CommentsController_UpdateComments_4009() {
		EditCommentsForm form = new EditCommentsForm();
		form.comment = "editcomments";
		Response respone = commentsController.update(3, 2, form, "token");
		assertEquals(Status.STATUS_4009, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_200() {
		Response respone = commentsController.delete(1, 1, "token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_1001() {
		Response respone = commentsController.delete(0, 1, "token");
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_2008() {
		Response respone = commentsController.delete(1, 1, "expired");
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_4003() {
		Response respone = commentsController.delete(2, 1, "token");
		assertEquals(Status.STATUS_4003, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_4004() {
		Response respone = commentsController.delete(4, 1, "token");
		assertEquals(Status.STATUS_4004, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_4008() {
		Response respone = commentsController.delete(3, 1, "token");
		assertEquals(Status.STATUS_4008, respone.getStatus());
	}

	@Test
	public void CommentsController_DeleteComments_4009() {
		Response respone = commentsController.delete(3, 2, "token");
		assertEquals(Status.STATUS_4009, respone.getStatus());
	}
	@Test
	public void CommentsController_GetAllCommentsByUser_200() {
		Response respone = commentsController.getCommentsOfUser("token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void CommentsController_GetAllCommentsByUser_4005() {
		Response respone = commentsController.getCommentsOfUser("errortoken");
		assertEquals(Status.STATUS_4005, respone.getStatus());
	}
	@Test
	public void CommentsController_GetAllCommentsForPosts_200() {
		Response respone = commentsController.getCommentsOfPosts(1);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void CommentsController_GetAllCommentsForPosts_4006() {
		Response respone = commentsController.getCommentsOfPosts(99999);
		assertEquals(Status.STATUS_4006, respone.getStatus());
	}
}
