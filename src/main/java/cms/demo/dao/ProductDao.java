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

import cms.demo.bean.ProductBean;
import cms.demo.util.StaticValueUtil;

@Repository
@Component
public class ProductDao {
	@Autowired
	JdbcTemplate template;
	private String sql="";
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int insert(ProductBean p) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        sql = "insert into product(name,description,category,price,createddate,createdby,status)values"
        	+ "('"+p.getName()+"','"+p.getDescription()+"','"+p.getCategory()+"','"+p.getPrice()+"','"+timeStamp+"','"+p.getCreatedby()+"','"+StaticValueUtil.ACTIVE+"')";
		return template.update(sql);
	}
	
	public int update(ProductBean p) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		sql = "update product set "
			+ "name='"+p.getName()+"', "
			+ "description='"+p.getDescription()+"', "
			+ "category='"+p.getCategory()+"', "
			+ "price='"+p.getPrice()+"', "
			+ "modifieddate='"+timeStamp+"', "
			+ "modifiedby='"+p.getModifiedBy()+"', "
			+ "status='"+p.getStatus()+"' "
			+ "where id="+p.getId();
		return template.update(sql);
	}
	
	public int delete(int id) {
		sql = "update product set status="+StaticValueUtil.DELETE+" where id="+id;
		return template.update(sql);
	}
	
	public ProductBean getProductById(int id) {
		sql = "select * from product where id=?";
		ProductBean product;
		try {
			product = template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			product = null;
		}
		return product;
	}
	
	public List<ProductBean>getProductList(){
		sql = "select * from product";
		return template.query(sql, new RowMapper<ProductBean>() {
			public ProductBean mapRow(ResultSet rs, int row)throws SQLException{
				ProductBean product = new ProductBean();
				product.setId(rs.getInt(1));
				product.setStatus(rs.getInt(2));
				product.setName(rs.getString(3));
				product.setDescription(rs.getString(4));
				product.setCategory(rs.getInt(5));
				product.setPrice(rs.getDouble(6));
				product.setCreatedDate(rs.getTimestamp(7));
				product.setCreatedby(rs.getString(8));
				product.setModifiedDate(rs.getTimestamp(9));
				product.setModifiedBy(rs.getString(10));
				return product;
			}
		});
	}
}
