package chat.automation.framework.exceptions;

import chat.automation.framework.utils.Logger;

public class FrameworkExceptions extends Exception {
		private static final long serialVersionUID = 1L;
		
		public FrameworkExceptions() {

		}

		public FrameworkExceptions(String message) {
			Logger.error(getClassMethodName() + message);
		}

		public FrameworkExceptions(Throwable cause) {
			super(cause);
			Logger.error(getClassMethodName() + cause);
		}

		public FrameworkExceptions(String message, Throwable cause) {
			super(message, cause);
			Logger.error(getClassMethodName() + message + ":" + cause);
		}
		
		public FrameworkExceptions(Exception exception) {
			//Reporter.reportEvent(LogStatus.ERROR,getClassMethodName(),exception.toString());
			/*if (!ApplicationConstants.isContinue) {
				if (exception instanceof InvocationTargetException) {
					Thread.currentThread().stop();
				}else if (exception instanceof NullPointerException) {
						Thread.currentThread().stop();	
				}else{
					Reporter.reportEvent(LogStatus.ERROR,getClassMethodName(),exception.toString());
					Thread.currentThread().stop();
				}
			}else{
				Reporter.reportEvent(LogStatus.ERROR,getClassMethodName(),exception.toString());
			}*/
		}
		
		private static String getClassMethodName(){
			String className,classSimpleName,methodName,classMethodName;
			className = Thread.currentThread().getStackTrace()[3].getClassName();
			classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length-1];
			methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
			classMethodName = "[" +  classSimpleName + ":" + methodName + "]";
			return classMethodName; 
		}

	}

