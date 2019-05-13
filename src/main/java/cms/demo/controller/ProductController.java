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

import cms.demo.bean.ProductBean;
import cms.demo.service.CategoryService;
import cms.demo.service.ProductService;
import cms.demo.util.DropdownUtil;
import cms.demo.validator.ProductValidator;
import cms.demo.util.SessionName;

@Controller
public class ProductController {
	@Autowired
	ProductService service;
	@Autowired
	DropdownUtil dropdown;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductValidator validator;
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	private String validInfo = "org.springframework.validation.BindingResult."+SessionName.BEAN;

	private final String actionUrl_save = "/admin/product/save";
	
	private void populateDefaultModel(Model m) {
		DropdownUtil d = new DropdownUtil();
		m.addAttribute("categoryDropdown", d.getCategoryList(categoryService.getCategoryList()));
		m.addAttribute("statusDropdown", d.getStatus());
	}
	//Remember the paramater "BindingResult rs" have to placed after the ModelAttribute
	@RequestMapping(value=actionUrl_save, method=RequestMethod.POST)
	public String save(@ModelAttribute(SessionName.PRODUCT)@Validated ProductBean product, 
			BindingResult rs, Model m, final RedirectAttributes re) {
		if(rs.hasErrors()) {
			re.addFlashAttribute(validInfo,rs);
			re.addFlashAttribute(SessionName.BEAN, product);
			return "redirect:/admin/"+(product.isNew()?"productAdd":"productEdit/"+product.getId());
		}else {
			re.addFlashAttribute("css","success");
			if(product.isNew()) {
				product.setCreatedby("Admin");
				service.insert(product);
				re.addFlashAttribute(SessionName.MSG_INFO, "New product added");
			}else {
				product.setModifiedBy("Admin");
				service.update(product);
				re.addFlashAttribute(SessionName.MSG_INFO, "Existing product updated");
			}
		}
		return "redirect:/admin/products";
	}
	
	@RequestMapping("/admin/product/delete")
	public String delete(int id) {
		service.delete(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping(value="/admin/productAdd")
	public String addProduct(Model m) {
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, new ProductBean());
		}
		m.addAttribute(SessionName.ACTION_URL, actionUrl_save);
		populateDefaultModel(m);
		return "admin/product/productForm";
	}
	
	@RequestMapping(value="/admin/products")
	public String products(Model m) {
		m.addAttribute(SessionName.BEANLIST, service.getProductList());
		return "admin/product/productIdx";
	}
	
	@GetMapping(value="/admin/productEdit/{id}")
	public String productUpdate(@PathVariable("id")int id, Model m, RedirectAttributes r) {
		ProductBean product = service.getProductById(id);
		if(product==null) {
			r.addAttribute(SessionName.MSG_INFO, "Product not found");
			return "redirect:/admin/products";
		}
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, product);
		}
		populateDefaultModel(m);
		m.addAttribute(SessionName.ACTION_URL, actionUrl_save);
		return "admin/product/productForm";
	}
	
	@RequestMapping(value="/shop/products")
	public String showProducts(Model m) {
		m.addAttribute(SessionName.BEANLIST, service.getProductList());
		return "shop/app/products";
	}
	
	@RequestMapping(value="/shop/product/{id}")
	public String displyProductDetail(@PathVariable("id")int id, Model m, RedirectAttributes r) {
		ProductBean product = service.getProductById(id);
		if(product==null) {
			r.addAttribute(SessionName.MSG_INFO, "Product not exist");
			return "redirect:/shop/products";
		}
		
		m.addAttribute(SessionName.PRODUCT, product);
		
		return "shop/app/productDetail";
	}
}
