package cms.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cms.demo.bean.OrderBean;
import cms.demo.dao.OrderDao;

@Component
@Service
public class OrderService {
	@Autowired
	private OrderDao dao;
	
	public int insert(OrderBean order) {
		return dao.insert(order);
	}
	
	public int update(OrderBean order) {
		return dao.update(order);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public OrderBean getOrderById(int id) {
		return dao.getOrderById(id);
	}
	
	public List<OrderBean>getOrderList(){
		return dao.getOrderList();
	}
}
