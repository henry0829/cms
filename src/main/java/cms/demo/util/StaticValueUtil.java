package cms.demo.util;

import org.springframework.stereotype.Component;

@Component
public class StaticValueUtil {
	//Status Option
	public static final int ACTIVE = 1;
	public static final int INACTIVE = -1;
	public static final int DELETE = -3;
	
	//User Type
	public static final int ADMIN = 1;
	public static final int USER = -1;
	public static final int MASTER =-3;
}
