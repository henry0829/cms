package cms.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cms.demo.bean.CategoryBean;

@Component
public class CategoryValidator implements Validator{
	
	public boolean supports(Class<?> clazz) {
		return CategoryBean.class.equals(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		String msgEmpty = "view.field.empty";
		String msgValid = "view.field.invalid";
		
		CategoryBean category = (CategoryBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", msgEmpty);


		if(category.getName()==null){errors.rejectValue("name", msgValid);}
		if(category.getDescription()==null){errors.rejectValue("description", msgValid);}
		if(category.getColor()==null){errors.rejectValue("color", msgValid);}
		if(category.getStatus()==0){errors.rejectValue("status", msgValid);}

	}
}
