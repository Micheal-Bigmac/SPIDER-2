package spider;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;


public class BB {
	// 构造HttpClient的实例
	public static void main(String args[]) {
	HttpClient httpClient = new HttpClient();

	String urlString = "http://open.baidu.com/phone/s?wd=13711112222&tn=phone&owd=13711112222";
	GetMethod mymethod = new GetMethod(urlString);
	int statusCode;
	try {
		statusCode = httpClient.executeMethod(mymethod);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + mymethod.getStatusLine());
		}
		// 读取内容
		String responseBody = mymethod.getResponseBodyAsString();
		System.out.println(responseBody);
	} catch (HttpException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//int statusCode = httpClient.executeMethod(mymethod);
	
	}
}
