package cms.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cms.demo.bean.ProductBean;
import cms.demo.dao.ProductDao;

@Component
@Service
public class ProductService {
	@Autowired
	private ProductDao dao;
	
	public int insert(ProductBean product) {
		return dao.insert(product);
	}
	
	public int update(ProductBean product) {
		return dao.update(product);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public ProductBean getProductById(int id) {
		return dao.getProductById(id);
	}
	
	public List<ProductBean>getProductList(){
		return dao.getProductList();
	}
}
