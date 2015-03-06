package junittest.resource;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.TokenService;

@Service
public class TokenControllerTest implements TokenService{

	@Override
	public boolean create(Token t) {
		return false;
	}

	@Override
	public boolean delete(int id_acc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteToken(String accesstoken) {
	    if(accesstoken.equals("delete"))
        {
            return true;
        }
        return false;
	}

	@Override
	public List<Token> getAllTokenOfUser(int iduser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getTokenByAccesstoken(String accesstoken) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public int checkExpiredDate(String accesstoken)
    {
        if(accesstoken.equals("expired"))
        {
            return 1;
        }
        return 0;
    }

    @Override
    public Date sumationExpiredDate()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
