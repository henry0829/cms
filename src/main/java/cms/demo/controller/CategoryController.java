package cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cms.demo.bean.CategoryBean;
import cms.demo.service.CategoryService;
import cms.demo.validator.CategoryValidator;
import cms.demo.util.DropdownUtil;
import cms.demo.util.SessionName;

@Controller
public class CategoryController {
	@Autowired
	CategoryService service;
	@Autowired
	DropdownUtil dropdown;
	
	@Autowired
	CategoryValidator validator;
	private String validInfo = "org.springframework.validation.BindingResult."+SessionName.BEAN;
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}	
	
	private final String actionUrl_save = "/admin/category/save";
	
	private void populateDefaultModel(Model m) {
		DropdownUtil d = new DropdownUtil();
		m.addAttribute("statusList", d.getStatus());
	}
	
	@RequestMapping(value=actionUrl_save, method=RequestMethod.POST)
	public String save(@ModelAttribute(SessionName.CATEGORY)@Validated CategoryBean category,
			BindingResult rs, Model m, final RedirectAttributes re) {
		if(rs.hasErrors()) {
			re.addFlashAttribute(validInfo,rs);
			re.addFlashAttribute(SessionName.BEAN, category);
			return "redirect:/admin/"+(category.isNew()?"categoryAdd":"categoryEdit/"+category.getId());
		}else {
			re.addFlashAttribute("css","success");
			if(category.isNew()) {
				category.setCreatedby("Admin");
				service.insert(category);
				re.addFlashAttribute(SessionName.MSG_INFO, "New category added");
			}else {
				category.setModifiedBy("Admin");
				service.update(category);
				re.addFlashAttribute(SessionName.MSG_INFO,"The category updated");
			}
		}
		return "redirect:/admin/categories";
	}
	
	@RequestMapping("admin/category/delete")
	public String delete(int id) {
		service.delete(id);
		return "redirect:/categories";
	}
	
	@GetMapping(value="/admin/categoryAdd")
	public String addCateogry(Model m) {
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, new CategoryBean());
		}
		m.addAttribute(SessionName.ACTION_URL, actionUrl_save);
		populateDefaultModel(m);
		return "admin/category/categoryForm";
	}
	
	@RequestMapping(value="/admin/categories")
	public String categories(Model m) {
		m.addAttribute(SessionName.BEANLIST, service.getCategoryList());
		return "admin/category/categoryIdx";
	}
	
	@GetMapping(value="/admin/categoryEdit/{id}")
	public String categoryUpdate(@PathVariable("id")int id, Model m, RedirectAttributes r) {
		CategoryBean category = service.getCategoryByid(id);
		if(category==null) {
			r.addAttribute(SessionName.MSG_INFO, "Category not found");
			return "redirect:/admin/categories";
		}
		
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, category);
		}
		populateDefaultModel(m);
		m.addAttribute(SessionName.ACTION_URL, actionUrl_save);
		return "admin/category/categoryForm";
	}
}
