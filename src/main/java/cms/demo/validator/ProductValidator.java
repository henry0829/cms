package cms.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cms.demo.bean.ProductBean;

@Component
public class ProductValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return ProductBean.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		String msgEmpty = "view.field.empty";
		String msgValid = "view.field.invalid";
		
		ProductBean product = (ProductBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", msgEmpty);
		
		if(product.getName()==null){errors.rejectValue("name", msgValid);}
		if(product.getDescription()==null){errors.rejectValue("description", msgValid);}
		if(product.getCategory()==0){errors.rejectValue("category", msgValid);}
		if(product.getPrice()==0){errors.rejectValue("price", msgValid);}
		if(product.getStatus()==0){errors.rejectValue("status", msgValid);}
		
		
	}
}
