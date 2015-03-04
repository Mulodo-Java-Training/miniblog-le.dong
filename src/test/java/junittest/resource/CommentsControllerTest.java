package junittest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.CommentsService;

@Service
public class CommentsControllerTest implements CommentsService{

	@Override
	public int create(Comments c) {
		if(c.getComment().equals("addcomments200"))
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean update(Comments c) {
		if(c.getId() == 1)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Comments c) {
		if(c.getId() == 1)
		{
			return true;
		}
		return false;
	}

	@Override
	public Comments get(int id) {
		if(id==1)
		{
			Account a = new Account();
			a.setId(1);
			Posts p = new Posts();
			p.setId(1);
			Comments c = new Comments();
			c.setId(1);
			c.setComment("content");
			c.setAccount(a);
			c.setPosts(p);
			return c;
		}
		if(id==2)
		{
			Account a = new Account();
			a.setId(1);
			Posts p = new Posts();
			p.setId(1);
			Comments c = new Comments();
			c.setId(2);
			c.setComment("content2");
			c.setAccount(a);
			c.setPosts(p);
			return c;
		}
		if(id==3)
		{
			Account a = new Account();
			a.setId(2);
			Posts p = new Posts();
			p.setId(2);
			Comments c = new Comments();
			c.setId(3);
			c.setComment("content3");
			c.setAccount(a);
			c.setPosts(p);
			return c;
		}
		return null;
	}

	@Override
	public List<Comments> getCommentsOfUser(int id_acc) {
		List<Comments> list = new ArrayList<Comments>();
		if(id_acc==1)
		{
			Comments c = new Comments();
			c.setComment("comment");
			list.add(c);
			return list;
		}
		return null;
	}

	@Override
	public List<Comments> getCommentsOfPosts(int id_posts) {
		List<Comments> list = new ArrayList<Comments>();
		if(id_posts==1)
		{
			Comments c = new Comments();
			c.setComment("comment");
			list.add(c);
			return list;
		}
		return null;
	}

}
