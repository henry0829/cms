package cms.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cms.demo.bean.OrderBean;
@Component
public class OrderValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return OrderBean.class.equals(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		String msgEmpty = "view.field.empty";
		String msgValid = "view.field.invalid";
		
		OrderBean order = (OrderBean)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productid", msgEmpty);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userid", msgEmpty);

		if(order.getStatus()==0) {errors.rejectValue("status", msgValid);}
		if(order.getProductid()==0) {errors.rejectValue("productid", msgValid);}
		if(order.getUserid()==0) {errors.rejectValue("userid", msgValid);}

	}
}
