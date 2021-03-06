package spider;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserTool {
	static	Set<String> links = new HashSet<String>();
	
	// 获取一个网站上的链接,filter 用来过滤链接
	public static Set<String> extracLinks(String url) {
		
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			// 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};
			// OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			// 得到所有经过过滤的标签
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> 标签
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// url
//					System.out.println(" HtmlParserTool  "+linkUrl);
					modify(linkUrl);
					links.add(linkUrl);
					/*if (filter.accept(linkUrl)){
						System.out.println(linkUrl);
						links.add(linkUrl);
					}*/
				} else// <frame> 标签
				{
					// 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
					String frame = tag.getText();
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if (end == -1)
						end = frame.indexOf(">");
					String frameUrl = frame.substring(5, end - 1);
					modify(frameUrl);
					/*if(frameUrl.matches(UrlAddr.targetUrl)){
						System.out.println("==============");
						links.add(frameUrl);
					}*/
					/*if (filter.accept(frameUrl))
						links.add(frameUrl);*/
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
	//提取出下载网页中的 URL
	private static void modify(String linkUrl) {
		if(linkUrl.matches(UrlAddr.targetUrl)&& !Link.isContain(linkUrl)){
			Link.enDownload(linkUrl);
			FileDownLoader downLoader=new FileDownLoader();
			//下载网页
//						System.out.println(" Crawler "+visitUrl);
			downLoader.downloadFile(linkUrl);
			System.out.println("==============");
		}
	}

	public Set<String> getLinks() {
		return links;
	}


	// 测试的 main 方法
	/*public static void main(String[] args) {
		Set<String> links = HtmlParserTool.extracLinks("http://zhidao.baidu.com/yisheng",
				new LinkFilter() {
					// 提取以 http://www.twt.edu.cn 开头的链接
					public boolean accept(String url) {
						if (url.startsWith("http://zhidao.baidu.com/"))
							return true;
						else
							return false;
					}

				});
		for (String link : links)
			System.out.println(link);
	}*/
}