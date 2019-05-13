package cms.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cms.demo.bean.CategoryBean;
import cms.demo.dao.CategoryDao;

@Component
@Service
public class CategoryService {
	@Autowired
	private CategoryDao dao;
	
	public int insert(CategoryBean category) {
		return dao.insert(category);
	}
	
	public int update(CategoryBean category) {
		return dao.update(category);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public CategoryBean getCategoryByid(int id) {
		return dao.getCategoryById(id);
	}
	
	public List<CategoryBean>getCategoryList(){
		return dao.getCategoryList();
	}
}
