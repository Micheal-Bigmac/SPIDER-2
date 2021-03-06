package spider;

import java.util.Set;
public class Crawler {
	/* 使用种子 url 初始化 URL 队列*/
	private void initCrawlerWithSeeds(String[] seeds)
	{
		for(int i=0;i<seeds.length;i++)
			Link.addUnvisitedUrl(seeds[i]);
	}
	
	/* 爬取方法*/
	public void crawling(String[] seeds)
	{
		LinkFilter filter = new LinkFilter(){
			//提取以  UrlAddr.targetUrl 开头的链接
			public boolean accept(String url) {
				if(url.startsWith(UrlAddr.targetUrl))
					return true;
				else
					return false;
			}
		};
		//初始化 URL 队列
		initCrawlerWithSeeds(seeds);
		//循环条件：待抓取的链接不空且抓取的网页不多于 1000
		while(!Link.unVisitedUrlsEmpty()&&Link.getVisitedUrlNum()<=1000)
		{
			//队头 URL 出对
			String visitUrl=Link.unVisitedUrlDeQueue();
			if(visitUrl==null)
				continue;
			
			//该 url 放入到已访问的 URL 中
			Link.addVisitedUrl(visitUrl);
			
			
//			Set<String> links=HtmlParserTool.extracLinks(visitUrl,filter);
			Set<String> links=HtmlParserTool.extracLinks(visitUrl); 
			//新的未访问的 URL 入队
			for(String link:links)
			{
					Link.addUnvisitedUrl(link);
			}
		}
	}
	//main 方法入口
	public static void main(String[]args)
	{
		Crawler crawler = new Crawler();
		crawler.crawling(UrlAddr.parseUrl);
	}
}