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

import com.mulodo.miniblog.form.AddCommentsForm;
import com.mulodo.miniblog.form.CreatePostsForm;
import com.mulodo.miniblog.form.SignInform;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.CommentsService;
import com.mulodo.miniblog.service.PostsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class CommentLayerServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PostsService postsService;

	@Autowired
	private CommentsService commentsService;

	private static Token token;

	private static Posts p;

	private static Comments c;

	@Before
	public void setUp() {
		SignInform form = new SignInform();
		form.username = "user";
		form.password = "e10adc3949ba59abbe56e057f20f883e";
		token = accountService.login(form.username, form.password);
		CreatePostsForm postsform = new CreatePostsForm();
		postsform.title = "testposttitle";
		postsform.content = "testpostcontent";
		int postsid = postsService.create(postsform.setData(token.getAccount()));
		p = postsService.get(postsid);

	}

	@After
	public void out() {
		postsService.delete(p);
		accountService.logout(token.getAccount().getId());
	}

	@Test
	public void CommentService_Create_Edit_Delete() {

		AddCommentsForm commentadd = new AddCommentsForm();
		commentadd.comment = "testcomment";

		int commentid = commentsService.create(commentadd.setData(token.getAccount(), p));
		c = commentsService.get(commentid);
		c.setComment("testcomment2");

		Comments cc = null;

		assertEquals(true, commentsService.update(c));
		assertEquals(false, commentsService.update(cc));

		assertEquals(true, commentsService.delete(c));
		assertEquals(false, commentsService.delete(cc));

	}

	@Test
	public void CommentService_GetComment() {

		AddCommentsForm commentadd = new AddCommentsForm();
		commentadd.comment = "testcomment";

		int commentid = commentsService.create(commentadd.setData(token.getAccount(), p));
		int commentlist = commentsService.getCommentsOfPosts(p.getId()).size();
		assertTrue(commentlist > 0);

		commentlist = commentsService.getCommentsOfUser(token.getAccount().getId()).size();
		assertTrue(commentlist > 0);

		Comments comment = commentsService.get(commentid);
		assertEquals("testcomment", comment.getComment());
		assertEquals(true, commentsService.delete(comment));

	}
}
