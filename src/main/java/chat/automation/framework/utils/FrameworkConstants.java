package chat.automation.framework.utils;
import java.io.File;
public interface FrameworkConstants {
		
		public static final String PROJECT_PATH = System.getProperty("user.dir");
		public static final String OrgName = "CLH";
		public static final int maxTimeOut = Integer.parseInt(Environment.getProperty("maxTimeOut"));
		public static final int minTimeOut = Integer.parseInt(Environment.getProperty("minTimeOut"));
		public static final String screenShotsPath = FrameworkConstants.PROJECT_PATH + File.separator + "Reports"+File.separator;
	
		
		

}
