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

import cms.demo.bean.OrderBean;
import cms.demo.util.StaticValueUtil;

@Repository
@Component
public class OrderDao {
	@Autowired
	JdbcTemplate template;
	private String sql="";
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int insert(OrderBean o) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        sql = "insert into orderinfo(userid,productid,status,receiver,address,createddate,createdby)values("
        	+ "'"+o.getUserid()+"','"+o.getProductid()+"','"+o.getStatus()+"','"+o.getReceiver()+"','"+o.getAddress()+"','"+timeStamp+"','"+o.getCreatedby()+"'"
        	+ ")";
        return template.update(sql);
	}
	
	public int update(OrderBean o) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		sql = "update orderinfo set "
			+ "status='"+o.getStatus()+"', "
			+ "userid='"+o.getUserid()+"', "
			+ "productid='"+o.getProductid()+"', "
			+ "receiver='"+o.getReceiver()+"', "
			+ "address='"+o.getAddress()+"', "
			+ "modifieddate='"+timeStamp+"', "
			+ "modifiedby='"+o.getModifiedBy()+"' "		
			+ "where id="+o.getId();
		return template.update(sql);
	}
	
	public int delete(int id) {
		sql = "update orderinfo set status = "+StaticValueUtil.DELETE+" where id="+id;
		return template.update(sql);
	}
	
	public OrderBean getOrderById(int id) {
		sql = "select * from orderinfo where id=? and status !="+StaticValueUtil.DELETE;
		OrderBean order;
		try {
			order = template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<OrderBean>(OrderBean.class));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			order = null;
		}
		return order;
	}
	
	public List<OrderBean>getOrderList(){
		sql = "select * from orderinfo";
		return template.query(sql, new RowMapper<OrderBean>() {
			public OrderBean mapRow(ResultSet rs, int row)throws SQLException{
				OrderBean order = new OrderBean();
				order.setId(rs.getInt(1));
				order.setUserid(rs.getInt(2));
				order.setProductid(rs.getInt(3));
				order.setStatus(rs.getInt(4));
				order.setReceiver(rs.getString(5));
				order.setAddress(rs.getString(6));
				order.setCreatedDate(rs.getTimestamp(7));
				order.setCreatedby(rs.getString(8));				
				order.setModifiedDate(rs.getTimestamp(9));
				order.setModifiedBy(rs.getString(10));
				return order;
			}
		});		
		
	}
}
