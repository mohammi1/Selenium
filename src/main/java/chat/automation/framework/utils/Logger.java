package chat.automation.framework.utils;

public class Logger {

	private static org.apache.logging.log4j.Logger Log = org.apache.logging.log4j.LogManager.getLogger(Logger.class.getName()); //org.apache.log4j.Logger.getLogger(Logger.class.getName());

	public static void info(String message) {
		Log.info(message);
	}

	public static void warn(String message) {
		Log.warn(message);
	}

	public static void error(String message) {
		Log.error(message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}
	
	public static void startMethod() {
		String classMethodName = "[" + getSenderName() + "]";
		info(classMethodName);
	}

	public static void endMethod() {
		String classMethodName = "[/" + getSenderName() + "]";
		info(classMethodName);
	}
	
	private static String getSenderName(){
		String className,classSimpleName,methodName,classMethodName;
		className = Thread.currentThread().getStackTrace()[3].getClassName();
		classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length-1];
		methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		classMethodName = classSimpleName + ":" + methodName;
		return classMethodName; 
	}
	
}