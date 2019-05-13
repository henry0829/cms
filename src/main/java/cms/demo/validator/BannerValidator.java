package cms.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cms.demo.bean.BannerBean;
@Component
public class BannerValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return BannerBean.class.equals(clazz);
	}
		
	public void validate(Object target, Errors errors) {
		String msgEmpty = "view.field.empty";
		String msgValid = "view.field.invalid";
		
		BannerBean banner = (BannerBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", msgEmpty);
		
		if(banner.getName()==null) {errors.rejectValue("name", msgValid);}
		if(banner.getDescription()==null) {errors.rejectValue("description", msgValid);}
		if(banner.getStatus()==0) {errors.rejectValue("status", msgValid);}
	}
}
