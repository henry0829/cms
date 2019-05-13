package cms.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cms.demo.bean.UserBean;
import cms.demo.dao.UserDao;

@Component
@Service
public class UserService {
	@Autowired
	private UserDao dao;
	
	public int insert(UserBean user) {
		return dao.insert(user);
	}
	
	public int update(UserBean user) {
		return dao.update(user);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public UserBean getUserByid(int id) {
		return dao.getUserById(id);
	}
	
	public List<UserBean>getUserList(){
		return dao.getUserList();
	}
	
	public UserBean getLoginInfo(String username, String password) {
		return dao.getLoginInfo(username, password);
	}
}
