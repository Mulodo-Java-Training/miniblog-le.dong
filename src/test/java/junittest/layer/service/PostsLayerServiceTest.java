package junittest.layer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mulodo.miniblog.form.CreatePostsForm;
import com.mulodo.miniblog.form.SignInform;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.PostsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class PostsLayerServiceTest {
	@Autowired
	private PostsService postsService;

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
	public void PostsService_Create_Edit_Delete_Active_Deactive_Post() {

		CreatePostsForm form = new CreatePostsForm();
		form.title = "testposttitle";
		form.content = "testpostcontent";
		int postsid = postsService.create(form.setData(token.getAccount()));
		assertTrue(postsid > 0);

		Posts p = postsService.get(postsid);
		p.setTitle("testposttitle2");
		p.setContent("testpostcontent2");

		Posts pp = null;

		assertEquals(true, postsService.update(p));
		assertEquals(false, postsService.update(pp));
		assertEquals(true, postsService.deactive(postsid));
		assertEquals(false, postsService.deactive(-1));
		assertEquals(true, postsService.active(postsid));
		assertEquals(false, postsService.active(-1));
		assertEquals(true, postsService.delete(p));
		assertEquals(false, postsService.delete(pp));

	}

	@Test
	public void PostsService_GetPosts() {

		CreatePostsForm form = new CreatePostsForm();
		form.title = "testposttitle";
		form.content = "testpostcontent";
		int postsid = postsService.create(form.setData(token.getAccount()));
		assertTrue(postsid > 0);

		int postlist = postsService.getAllPostsActive().size();
		assertTrue(postlist > 0);
		postlist = postsService.getAllPostsDeactive().size();
		assertTrue(postlist > 0);
		postlist = postsService.getAllPostsByUser(token.getAccount().getId()).size();
		assertTrue(postlist > 0);
		postlist = postsService.getAllPostsTop().size();
		assertTrue(postlist > 0);
		postlist = postsService.search("t").size();
		assertTrue(postlist > 0);
		Posts posts = postsService.get(postsid);
		assertEquals("testposttitle", posts.getTitle());
		assertEquals(true, postsService.delete(posts));

	}

}
