package cms.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cms.demo.bean.UserBean;
import cms.demo.util.StaticValueUtil;

@Repository
@Component
public class UserDao {
	@Autowired
	JdbcTemplate template;
	private String sql = "";
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int insert(UserBean u) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        sql = "insert into user(type, username, password, name, address, createddate, createdby, status)values("
        	+ "'"+u.getType()+"','"+u.getUsername()+"','"+u.getPassword()+"','"+u.getName()+"','"+u.getAddress()+"','"+timeStamp+"','"+u.getCreatedby()+"','"+StaticValueUtil.DELETE+"'"
        	+ ")";
		return template.update(sql);
	}
	
	public int update(UserBean u) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		sql = "update user set "
			+ "status='"+u.getStatus()+"', "
			+ "type='"+u.getType()+"', "
			+ "username='"+u.getUsername()+"', "
			+ "password='"+u.getPassword()+"', "
			+ "name='"+u.getName()+"', "
			+ "address='"+u.getAddress()+"', "
			+ "modifieddate='"+timeStamp+"', "
			+ "modifiedby='"+u.getModifiedBy()+"' "
			+ "where id="+u.getId();
		return template.update(sql);
	}
	
	public int delete(int id) {
		sql = "update user set status = "+StaticValueUtil.DELETE+" where id="+id;
		return template.update(sql);
	}
	
	public UserBean getUserById(int id) {
		sql = "select * from user where id=?";
		UserBean user;
		try {
			user = template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<UserBean>(UserBean.class));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	
	public List<UserBean>getUserList(){
		sql = "select * from user";
		return template.query(sql, new RowMapper<UserBean>() {
			public UserBean mapRow(ResultSet rs, int row)throws SQLException{
				UserBean user = new UserBean();
				user.setId(rs.getInt(1));
				user.setStatus(rs.getInt(2));
				user.setType(rs.getInt(3));
				user.setUsername(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setName(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCreatedDate(rs.getTimestamp(8));
				user.setCreatedby(rs.getString(9));
				user.setModifiedDate(rs.getTimestamp(10));
				user.setModifiedBy(rs.getString(11));
				return user;
			}
		});
	}
	
	public List<UserBean>getUserListByRole(String sqlStatement){
		sql = "select * from user "+sqlStatement;
		return template.query(sql, new RowMapper<UserBean>() {
			public UserBean mapRow(ResultSet rs, int row)throws SQLException{
				UserBean user = new UserBean();
				user.setId(rs.getInt(1));
				user.setStatus(rs.getInt(2));
				user.setType(rs.getInt(3));
				user.setUsername(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setName(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCreatedDate(rs.getTimestamp(8));
				user.setCreatedby(rs.getString(9));
				user.setModifiedDate(rs.getTimestamp(10));
				user.setModifiedBy(rs.getString(11));
				return user;
			}
		});
	}
	
	public UserBean getLoginInfo(String username, String password) {
		sql = "select * from user where username=? and password=?";
		UserBean user;
		try {
			user = template.queryForObject(sql, new Object[] {username, password}, new BeanPropertyRowMapper<UserBean>(UserBean.class));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	
}
