package cms.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cms.demo.bean.BannerBean;
import cms.demo.dao.BannerDao;

@Component
@Service
public class BannerService {
	@Autowired
	private BannerDao dao;
	
	public int insert(BannerBean banner) {
		return dao.insert(banner);
	}
	
	public int update(BannerBean banner) {
		return dao.update(banner);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public BannerBean getBannerById(int id) {
		return dao.getBannerById(id);
	}
	
	public List<BannerBean>getBannerList(){
		return dao.getBannerList();
	}
}
