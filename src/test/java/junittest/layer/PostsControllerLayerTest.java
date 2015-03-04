package junittest.layer;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.controller.PostsController;
import com.mulodo.miniblog.form.CreatePostsForm;
import com.mulodo.miniblog.form.UpdatePostsForm;
import com.mulodo.miniblog.util.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/java/TestControllerContext.xml")
public class PostsControllerLayerTest {
	@Autowired
	private PostsController postsController;

	@Test
	public void PostsController_CreatPosts_200() {
		CreatePostsForm form = new CreatePostsForm();
		form.title = "createposts200";
		form.content = "content";
		Response respone = postsController.create("token", form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_CreatPosts_1001() {
		CreatePostsForm form = new CreatePostsForm();
		Response respone = postsController.create("token", form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void PostsController_CreatPosts_2008() {
		CreatePostsForm form = new CreatePostsForm();
		form.title = "title";
		form.content = "content";
		Response respone = postsController.create("expired", form);
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void PostsController_CreatPosts_3001() {
		CreatePostsForm form = new CreatePostsForm();
		form.title = "title1111";
		form.content = "content1111";
		Response respone = postsController.create("token", form);
		assertEquals(Status.STATUS_3001, respone.getStatus());
	}

	@Test
	public void PostsController_ActivePosts_200() {
		Response respone = postsController.active(1, "token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_ActivePosts_1001() {
		Response respone = postsController.active(0, "token");
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void PostsController_ActivePosts_2008() {
		Response respone = postsController.active(1, "expired");
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void PostsController_ActivePosts_3002() {
		Response respone = postsController.active(2, "token");
		assertEquals(Status.STATUS_3002, respone.getStatus());
	}

	@Test
	public void PostsController_ActivePosts_3005() {
		Response respone = postsController.active(4, "token");
		assertEquals(Status.STATUS_3005, respone.getStatus());
	}

	@Test
	public void PostsController_ActivePosts_3008() {
		Response respone = postsController.active(3, "token");
		assertEquals(Status.STATUS_3008, respone.getStatus());
	}

	@Test
	public void PostsController_DeactivePosts_200() {
		Response respone = postsController.deactive(1, "token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_DeactivePosts_1001() {
		Response respone = postsController.deactive(0, "token");
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void PostsController_DeactivePosts_2008() {
		Response respone = postsController.deactive(1, "expired");
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void PostsController_DeactivePosts_3002() {
		Response respone = postsController.deactive(2, "token");
		assertEquals(Status.STATUS_3002, respone.getStatus());
	}

	@Test
	public void PostsController_DeactivePosts_3005() {
		Response respone = postsController.deactive(4, "token");
		assertEquals(Status.STATUS_3005, respone.getStatus());
	}

	@Test
	public void PostsController_DeactivePosts_3008() {
		Response respone = postsController.deactive(3, "token");
		assertEquals(Status.STATUS_3008, respone.getStatus());
	}

	@Test
	public void PostsController_UpdatePosts_200() {
		UpdatePostsForm form = new UpdatePostsForm();
		form.title = "title";
		form.content = "content";
		Response respone = postsController.update("token", 1, form);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_UpdatePosts_1001() {
		UpdatePostsForm form = new UpdatePostsForm();
		Response respone = postsController.update("token", 1, form);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void PostsController_UpdatePosts_2008() {
		UpdatePostsForm form = new UpdatePostsForm();
		form.title = "title";
		form.content = "content";
		Response respone = postsController.update("expired", 1, form);
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void PostsController_UpdatePosts_3003() {
		UpdatePostsForm form = new UpdatePostsForm();
		form.title = "title";
		form.content = "content";
		Response respone = postsController.update("token", 2, form);
		assertEquals(Status.STATUS_3003, respone.getStatus());
	}

	@Test
	public void PostsController_UpdatePosts_3005() {
		UpdatePostsForm form = new UpdatePostsForm();
		form.title = "title";
		form.content = "content";
		Response respone = postsController.update("token", 4, form);
		assertEquals(Status.STATUS_3005, respone.getStatus());
	}

	@Test
	public void PostsController_UpdatePosts_3008() {
		UpdatePostsForm form = new UpdatePostsForm();
		form.title = "title";
		form.content = "content";
		Response respone = postsController.update("token", 3, form);
		assertEquals(Status.STATUS_3008, respone.getStatus());
	}

	@Test
	public void PostsController_DeletePosts_200() {
		Response respone = postsController.delete("token", 1);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_DeletePosts_1001() {
		Response respone = postsController.delete("token", 0);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void PostsController_DeletePosts_2008() {
		Response respone = postsController.delete("expired", 1);
		assertEquals(Status.STATUS_2008, respone.getStatus());
	}

	@Test
	public void PostsController_DeletePosts_3004() {
		Response respone = postsController.delete("token", 2);
		assertEquals(Status.STATUS_3004, respone.getStatus());
	}

	@Test
	public void PostsController_DeletePosts_3005() {
		Response respone = postsController.delete("token", 4);
		assertEquals(Status.STATUS_3005, respone.getStatus());
	}

	@Test
	public void PostsController_DeletePosts_3008() {
		Response respone = postsController.delete("token", 3);
		assertEquals(Status.STATUS_3008, respone.getStatus());
	}

	@Test
	public void PostsController_GetPosts_200() {
		Response respone = postsController.get(1);
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_GetPosts_1001() {
		Response respone = postsController.get(0);
		assertEquals(Status.STATUS_1001, respone.getStatus());
	}

	@Test
	public void PostsController_GetPosts_3005() {
		Response respone = postsController.get(4);
		assertEquals(Status.STATUS_3005, respone.getStatus());
	}
	@Test
	public void PostsController_GetAllPosts_200() {
		Response respone = postsController.getAllActive();
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_GetAllPostsByUser_200() {
		Response respone = postsController.getPostsOfUser("token");
		assertEquals(Status.STATUS_200, respone.getStatus());
	}

	@Test
	public void PostsController_GetAllPostsByUser_3006() {
		Response respone = postsController.getPostsOfUser("errortoken");
		assertEquals(Status.STATUS_3006, respone.getStatus());
	}

}
