package junittest.resource;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.PostsService;

@Service
public class PostsControllerTest implements PostsService{

	@Override
	public int create(Posts p) {
		if(p.getTitle().equals("createposts200"))
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean update(Posts p) {
		if(p.getId()==1)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Posts p) {
		if(p.getId()==1)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean active(int id) {
		if(id==1)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean deactive(int id) {
		if(id==1)
		{
			return true;
		}
		return false;
	}

	@Override
	public Posts get(int id) {
		if(id==1)
		{
			Account a = new Account();
			a.setId(1);
			Posts p = new Posts();
			p.setId(1);
			p.setTitle("title");
			p.setContent("content");
			p.setAccount(a);
			return p;
		}
		if(id==2)
		{
			Account a = new Account();
			a.setId(1);
			Posts p = new Posts();
			p.setId(2);
			p.setTitle("title2");
			p.setContent("content2");
			p.setAccount(a);
			return p;
		}
		if(id==3)
		{
			Account a = new Account();
			a.setId(2);
			Posts p = new Posts();
			p.setId(3);
			p.setTitle("title3");
			p.setContent("content3");
			p.setAccount(a);
			return p;
		}
		return null;
	}

	@Override
	public List<Posts> getAllPostsActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Posts> getAllPostsDeactive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Posts> getAllPostsByUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Posts> getAllPostsByContent(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Posts> getAllPostsTop() {
		// TODO Auto-generated method stub
		return null;
	}

}
