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

import cms.demo.bean.BannerBean;
import cms.demo.util.StaticValueUtil;

@Repository
@Component
public class BannerDao {
	@Autowired
	JdbcTemplate template;
	private String sql="";
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int insert(BannerBean b) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        sql = "insert into banner(name,description,link,status,createddate,createdby)values("
        	+ "'"+b.getName()+"','"+b.getDescription()+"','"+b.getLink()+"','"+b.getStatus()+"','"+timeStamp+"','"+b.getCreatedby()+"'"
        	+ ")";
        return template.update(sql);
	}
	
	public int update(BannerBean b) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		sql = "update banner set "
			+ "status='"+b.getStatus()+"', "
			+ "name='"+b.getName()+"', "
			+ "description='"+b.getDescription()+"', "
			+ "link='"+b.getLink()+"', "
			+ "modifieddate='"+timeStamp+"', "
			+ "modifiedby='"+b.getModifiedBy()+"' "		
			+ "where id="+b.getId();
		return template.update(sql);
	}
	
	public int delete(int id) {
		sql = "update banner set status = "+StaticValueUtil.DELETE+" where id="+id;
		return template.update(sql);
	}
	
	public BannerBean getBannerById(int id) {
		sql = "select * from banner where id=?";
		BannerBean banner;
		try {
			banner = template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<BannerBean>(BannerBean.class));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			banner = null;
		}
		return banner;
	}
	
	public List<BannerBean>getBannerList(){
		sql = "select * from banner";
		return template.query(sql, new RowMapper<BannerBean>() {
			public BannerBean mapRow(ResultSet rs, int row)throws SQLException{
				BannerBean banner = new BannerBean();
				banner.setId(rs.getInt(1));
				banner.setStatus(rs.getInt(2));
				banner.setName(rs.getString(3));
				banner.setDescription(rs.getString(4));
				banner.setLink(rs.getString(5));
				banner.setCreatedDate(rs.getTimestamp(6));
				banner.setCreatedby(rs.getString(7));				
				banner.setModifiedDate(rs.getTimestamp(8));
				banner.setModifiedBy(rs.getString(9));
				return banner;
			}
		});		
		
	}
}
