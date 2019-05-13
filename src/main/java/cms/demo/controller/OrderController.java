package cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cms.demo.bean.OrderBean;
import cms.demo.service.OrderService;
import cms.demo.util.DropdownUtil;
import cms.demo.util.SessionName;
import cms.demo.validator.OrderValidator;

@Controller
public class OrderController {
	@Autowired
	OrderService service;
	@Autowired
	OrderValidator validator;
	private String validInfo = "org.springframework.validation.BindingResult."+SessionName.BEAN;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	private final String URL_SAVE = "/admin/order/save";
	private final String URL_LIST = "/admin/orders";
	private final String URL_ADD = "/admin/orderAdd";
	private final String URL_EDIT = "/admin/orderEdit/";//int id
	private final String URL_DELETE = "/admin/order/delete";
	
	private final String PAGE_INDEX = "admin/order/orderIdx";
	private final String PAGE_FORM = "admin/order/orderForm";
	
	private void populateDefaultModel(Model m) {
		DropdownUtil d = new DropdownUtil();
		m.addAttribute("statusList", d.getStatus());
	}
	
	@RequestMapping(value=URL_SAVE, method=RequestMethod.POST)
	public String save(@ModelAttribute(SessionName.BANNER)@Validated OrderBean order,
			BindingResult rs, Model m, final RedirectAttributes re){
		if(rs.hasErrors()) {
			re.addFlashAttribute(validInfo,rs);
			re.addFlashAttribute(SessionName.BEAN, order);
			return "redirect:"+(order.isNew()?URL_ADD:URL_EDIT+order.getId());
		}else {
			order.setCreatedby("");
			re.addFlashAttribute("css","success");
			if(order.isNew()) {
				service.insert(order);
				re.addFlashAttribute(SessionName.MSG_INFO, "New order added");
			}else {
				service.update(order);
				re.addFlashAttribute(SessionName.MSG_INFO, "New order updated");
			}
		}
		return "redirect:"+URL_LIST;
	}
	
	@RequestMapping(value=URL_DELETE)
	public String delete(int id) {
		service.delete(id);
		return URL_DELETE;
	}
	
	@RequestMapping(value=URL_ADD)
	public String add(Model m) {
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, new OrderBean());
		}
		m.addAttribute(SessionName.ACTION_URL, URL_SAVE);
		populateDefaultModel(m);
		return PAGE_FORM;
	}
	
	@RequestMapping(value=URL_LIST)
	public String orders(Model m) {
		m.addAttribute(SessionName.BEANLIST, service.getOrderList());
		return PAGE_INDEX;
	}
	
	@RequestMapping(value=URL_EDIT+"{id}")
	public String orderUpdate(@PathVariable("id")int id, Model m, RedirectAttributes r) {
		OrderBean order = service.getOrderById(id);
		if(order==null) {
			r.addAttribute(SessionName.MSG_INFO, "Order not found");
			return "redirect:"+URL_LIST;
		}
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, order);
		}
		populateDefaultModel(m);
		m.addAttribute(SessionName.ACTION_URL, URL_SAVE);
		return PAGE_FORM;
	}
}
