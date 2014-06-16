package spider;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class A {
	
	public static void main(String args[]) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
//		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,new HttpHost("cmnet-proxy.gmcc.net", 8081));
		// 创建GET方法的实例
		String urlString = "http://zhidao.baidu.com/prof/view/888258004";
		GetMethod mymethod = new GetMethod(urlString);
		
		// PostMethod method = new PostMethod(urlString);
		// 使用系统提供的默认的恢复策略
		mymethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		//httpClient.getHostConfiguration().setProxy("XXX.XXX.XXX.XXX", 8080);
		try {
			// 执行getMethod
				int statusCode = httpClient.executeMethod(mymethod);
				System.out.println("statusCode is "+statusCode);
				if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + mymethod.getStatusLine());
				}
				// 读取内容
				String responseBody = mymethod.getResponseBodyAsString();
				System.out.println("body is "+responseBody);
			} catch (HttpException e1) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e1.printStackTrace();
			} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			} finally {
			// 释放连接
			mymethod.releaseConnection();
			}
	}

}
