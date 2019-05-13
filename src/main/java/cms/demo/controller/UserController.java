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

import cms.demo.bean.UserBean;
import cms.demo.service.UserService;
import cms.demo.util.DropdownUtil;
import cms.demo.util.SessionName;
import cms.demo.validator.UserValidator;

@Controller
public class UserController {
	@Autowired
	UserService service;
	@Autowired
	UserValidator validator;
	private String validInfo = "org.springframework.validation.BindingResult."+SessionName.BEAN;
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	private final String actionUrl_save = "/admin/user/save";
	private final String actionUrl_list = "/admin/users";
	private final String actionUrl_add = "/admin/userAdd";
	private final String actionUrl_edit = "/admin/userEdit/";//int id
	private final String actionUrl_delete = "/admin/user/delete";
	
	private final String admin_login = "/admin/";
	private final String shop_login = "/shop/";
	private final String PAGE_ADMIN_LOGIN = "admin/login";
	private final String PAGE_SHOP_LOGIN = "shop/login";	
	
	
	@RequestMapping("/admin")
	public String adminLogin(@ModelAttribute(SessionName.LOGINUSER) UserBean user,Model m, RedirectAttributes re) {
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		UserBean validUser = service.getLoginInfo(user.getUsername(), user.getPassword());
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		if(validUser==null) {
			m.addAttribute(SessionName.LOGINUSER, user);
			re.addAttribute(SessionName.MSG_INFO, "Invalid Login");
			m.addAttribute("css", "danger");
			return PAGE_ADMIN_LOGIN;
		}
		
		re.addFlashAttribute(SessionName.LOGINUSER, validUser);
		return "redirect:"+actionUrl_list;
	}
	private void populateDefaultModel(Model m) {
		DropdownUtil d = new DropdownUtil();
		m.addAttribute("statusList", d.getStatus());
	}
	
	@RequestMapping(value=actionUrl_save, method=RequestMethod.POST)
	public String save(@ModelAttribute(SessionName.USER)@Validated UserBean user,
			BindingResult rs, Model m, final RedirectAttributes re) {
		if(rs.hasErrors()) {
			re.addFlashAttribute(validInfo,rs);
			re.addFlashAttribute(SessionName.BEAN, user);
			return "redirect:"+(user.isNew()?actionUrl_add:actionUrl_edit+user.getId());
		}else {
			re.addFlashAttribute("css","success");
			if(user.isNew()) {
				service.insert(user);
				re.addFlashAttribute(SessionName.MSG_INFO, "New user added");
			}else {
				service.update(user);
				re.addFlashAttribute(SessionName.MSG_INFO, "The user updated");
			}
		}		
		return "redirect:"+actionUrl_list;
	}
	
	@RequestMapping("admin/user/delete")
	public String delete(int id) {
		service.delete(id);
		return actionUrl_delete;
	}
	
	@RequestMapping(value=actionUrl_add)
	public String addUser(Model m) {
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, new UserBean());
		}
		m.addAttribute(SessionName.ACTION_URL, actionUrl_save);
		populateDefaultModel(m);
		return "admin/user/userForm";
	}
	
	@RequestMapping(value=actionUrl_list)
	public String users(Model m, RedirectAttributes r) {
		
		m.addAttribute(SessionName.BEANLIST, service.getUserList());
		return "admin/user/userIdx";
	}
	
	@GetMapping(value=actionUrl_edit+"{id}")
	public String userUpdate(@PathVariable("id")int id, Model m, RedirectAttributes r) {
		UserBean user = service.getUserByid(id);
		if(user==null) {
			r.addAttribute(SessionName.MSG_INFO, "User not found");
			return "redirect:"+actionUrl_list;
		}
		
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, user);
		}
		
		populateDefaultModel(m);
		m.addAttribute(SessionName.ACTION_URL, actionUrl_save);
		return "admin/user/userForm";
	}
	
}
