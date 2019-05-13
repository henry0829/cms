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

import cms.demo.bean.BannerBean;
import cms.demo.service.BannerService;
import cms.demo.util.DropdownUtil;
import cms.demo.util.SessionName;
import cms.demo.validator.BannerValidator;

@Controller
public class BannerController {
	@Autowired
	BannerService service;
	@Autowired
	BannerValidator validator;
	private String validInfo = "org.springframework.validation.BindingResult."+SessionName.BEAN;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	private final String URL_SAVE = "/admin/banner/save";
	private final String URL_LIST = "/admin/banners";
	private final String URL_ADD = "/admin/bannerAdd";
	private final String URL_EDIT = "/admin/bannerEdit/";//int id
	private final String URL_DELETE = "/admin/banner/delete";
	
	private final String PAGE_INDEX = "admin/banner/bannerIdx";
	private final String PAGE_FORM = "admin/banner/bannerForm";
	
	private void populateDefaultModel(Model m) {
		DropdownUtil d = new DropdownUtil();
		m.addAttribute("statusList", d.getStatus());
	}
	
	@RequestMapping(value=URL_SAVE, method=RequestMethod.POST)
	public String save(@ModelAttribute(SessionName.BANNER)@Validated BannerBean banner,
			BindingResult rs, Model m, final RedirectAttributes re){
		if(rs.hasErrors()) {
			re.addFlashAttribute(validInfo,rs);
			re.addFlashAttribute(SessionName.BEAN, banner);
			return "redirect:"+(banner.isNew()?URL_ADD:URL_EDIT+banner.getId());
		}else {
			banner.setCreatedby("Admin");
			re.addFlashAttribute("css","success");
			if(banner.isNew()) {
				service.insert(banner);
				re.addFlashAttribute(SessionName.MSG_INFO, "New banner added");
			}else {
				service.update(banner);
				re.addFlashAttribute(SessionName.MSG_INFO, "The banner updated");
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
			m.addAttribute(SessionName.BEAN, new BannerBean());
		}
		m.addAttribute(SessionName.ACTION_URL, URL_SAVE);
		populateDefaultModel(m);
		return PAGE_FORM;
	}
	
	@RequestMapping(value=URL_LIST)
	public String banners(Model m) {
		m.addAttribute(SessionName.BEANLIST, service.getBannerList());
		return PAGE_INDEX;
	}
	
	@RequestMapping(value=URL_EDIT+"{id}")
	public String bannerUpdate(@PathVariable("id")int id, Model m, RedirectAttributes r) {
		BannerBean banner = service.getBannerById(id);
		if(banner==null) {
			r.addAttribute(SessionName.MSG_INFO, "Banner not found");
			return "redirect:"+URL_LIST;
		}
		if(!m.containsAttribute(SessionName.BEAN)) {
			m.addAttribute(SessionName.BEAN, banner);
		}
		populateDefaultModel(m);
		m.addAttribute(SessionName.ACTION_URL, URL_SAVE);
		return PAGE_FORM;
	}
}
