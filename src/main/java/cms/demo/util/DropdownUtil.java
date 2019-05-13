package cms.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cms.demo.bean.CategoryBean;

@Service
@Component
public class DropdownUtil {

//	public List<Integer> getStatus(){
//		List<Integer> statusList = new ArrayList<Integer>();
//		statusList.add(StaticValueUtil.ACTIVE);
//		statusList.add(StaticValueUtil.INACTIVE);
//		statusList.add(3);
//		return statusList;
//	}
	
	public Map<Integer, String> getStatus() {
		Map<Integer, String> statusDrop = new HashMap<Integer, String>();
		statusDrop.put(StaticValueUtil.ACTIVE, "Active");
		statusDrop.put(StaticValueUtil.INACTIVE, "Inactive");
		statusDrop.put(StaticValueUtil.DELETE, "Delete");
		return statusDrop;
	}
	
	public Map<Integer, String> getCategoryList(List<CategoryBean> categoryList){
		Map<Integer, String> categoryDrop = new HashMap<Integer, String>();
		List<CategoryBean> categories = categoryList;
		for(CategoryBean category:categories) {
			categoryDrop.put(category.getId(), category.getName());
		}
		return categoryDrop;
	}
}
