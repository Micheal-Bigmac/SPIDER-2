package spider;

import java.util.HashSet;
import java.util.Set;

/**
 * 用来保存已经访问过 Url 和待访问的 Url 的类
 */
public class Link {

	//已访问的 url 集合
	private static Set<String> visitedUrl = new HashSet<String>();
	//待访问的 url 集合
	private static Queue<String> unVisitedUrl = new Queue<String>();
	private static Set<String> download=new HashSet<String>();
	public static void enDownload(String url){
		download.add(url);
	}

	
	public static Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}

	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	public static String unVisitedUrlDeQueue() {
		return unVisitedUrl.deQueue();
	}

	// 保证每个 url 只被访问一次
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contians(url))
			unVisitedUrl.enQueue(url);
	}

	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}

	public static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.empty();
	}
	public static boolean isContain(String url){
		for(String temp : visitedUrl){
			System.out.println(temp);
		}
		if (url != null && !url.trim().equals("") && download.contains(url)){
			return true;
		}
		return false;
		
	}
}