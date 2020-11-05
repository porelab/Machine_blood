package errorcodes;

import java.util.HashMap;
import java.util.Map;

public class ErrorList {

	public static Map<String,ErrorInfo> list=new HashMap<String, ErrorInfo>();
	
	public static void setErrorList()
	{
		list.put("101", new ErrorInfo("101", "No Internet Connection", ""));
		list.put("102", new ErrorInfo("102", "Connection Error", ""));
		list.put("103", new ErrorInfo("103", "Connection Lost", ""));
		list.put("104", new ErrorInfo("104", "File Corrupted", ""));
		list.put("105", new ErrorInfo("105", "Max File Error", ""));
		list.put("106", new ErrorInfo("106", "No Internet connection", ""));
		list.put("107", new ErrorInfo("107", "No Internet connection", ""));
		list.put("108", new ErrorInfo("108", "No Internet connection", ""));
		
	}
	
	
}
