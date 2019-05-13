package cms.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cms.demo.bean.CategoryBean;
import cms.demo.util.StaticValueUtil;


@Repository
@Component
public class CategoryDao {
	@Autowired
	JdbcTemplate template;
	private String sql = "";
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int insert(CategoryBean category) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		sql = "insert into category (name,description,color,createddate,createdby,status)values"
			+ "('"+category.getName()+"','"+category.getDescription()+"','"+category.getColor()+"','"+timeStamp+"','"+category.getCreatedby()+"',"+StaticValueUtil.ACTIVE+")";
		return template.update(sql);
	}
	
	public int update(CategoryBean category) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		sql = "update category set "
			+ "name='"+category.getName()+"', "
			+ "description='"+category.getDescription()+"', "
			+ "color='"+category.getColor()+"', "
			+ "status='"+category.getStatus()+"', "
			+ "modifieddate='"+timeStamp+"', "	
			+ "modifiedby='"+category.getModifiedBy()+"' "			
			+ "where id="+category.getId();
		
		return template.update(sql);
	}
	
	public int delete(int id) {
		sql = "update category set status="+StaticValueUtil.DELETE+" where id="+id;
		return template.update(sql);
	}
	
	public CategoryBean getCategoryById(int id) {
		sql = "Select * from category where id=?";
		CategoryBean category;
		try {
			category = template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			category = null;
		}
		return category;
	}
	
	public List<CategoryBean>getCategoryList(){
		sql = "select * from category";
		return template.query(sql, new RowMapper<CategoryBean>() {
			public CategoryBean mapRow(ResultSet rs, int row)throws SQLException{
				CategoryBean category = new CategoryBean();
				category.setId(rs.getInt(1));
				category.setStatus(rs.getInt(2));
				category.setName(rs.getString(3));
				category.setDescription(rs.getString(4));
				category.setColor(rs.getString(5));
				category.setCreatedDate(rs.getTimestamp(6));
				category.setCreatedby(rs.getString(7));
				category.setModifiedDate(rs.getTimestamp(8));
				category.setModifiedBy(rs.getString(9));
				return category;
			}
		});
	}
}
