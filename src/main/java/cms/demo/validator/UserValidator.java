package cms.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;


import cms.demo.bean.UserBean;

@Component
public class UserValidator implements Validator{
	
	public boolean supports(Class<?> clazz) {
		return UserBean.class.equals(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		String msgEmpty = "view.field.empty";
		String msgValid = "view.field.invalid";
		
		UserBean user = (UserBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", msgEmpty);

		
		if(user.getUsername()==null) {errors.rejectValue("username", msgValid);}
		if(user.getPassword()==null) {errors.rejectValue("password", msgValid);}
		if(user.getName()==null) {errors.rejectValue("name", msgValid);}
		if(user.getType()==0) {errors.rejectValue("type", msgValid);}
		if(user.getStatus()==0) {errors.rejectValue("status", msgValid);}

	}
}
