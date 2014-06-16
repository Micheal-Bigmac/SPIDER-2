package Parse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class GetFromUrl {
	public static void main(String[] args) throws SQLException {
		List<Message> messages = new ArrayList<Message>();
		try {
			Parser parser;
			String body = "";
			parser = new Parser("http://zhidao.baidu.com/prof/view/888257160");
			parser.setEncoding("GBK");
			HtmlPage htmlpage = new HtmlPage(parser);
			parser.visitAllNodesWith(htmlpage);
			body = htmlpage.getBody().toHtml();

			Parser nodesParser = new Parser(body);// 初始化Parser，这里要注意导入包为org.htmlparser。这里参数有很多。这个地方我写的是提前获取好的html文本。也可以传入URl对象
			nodesParser.setEncoding("utf-8");// 设置编码机
			AndFilter filter = new AndFilter(new TagNameFilter("div"),
					new HasAttributeFilter("id", "expertInfoView"));// 通过filter找到div且div的id为songListWrapper

			NodeList nodes = nodesParser.parse(filter);// 通过filter获取nodes

			Node node = nodes.elementAt(0);
			System.out.println(node.toHtml());

			NodeList nodesChild = node.getChildren();
			Node[] nodesArr = nodesChild.toNodeArray();
			Message mess = new Message();
			for (int j = 1; j < nodesArr.length; j++) {
				NodeList nextNode = nodesArr[j].getChildren();
				if (nextNode == null)
					continue;
				Node[] nodesArr2 = nextNode.toNodeArray();
				System.out.println(nodesArr2.length + " length ");
				for (int i = 0; i < nodesArr2.length; i++) {
					Node temp = (Node) nodesArr2[i];
					// System.out.println(temp.getText());
					String html = temp.toPlainTextString().trim();
					if (j == 1 & i == 1) {
						mess.setName(html);
					} else if (j == 1 && i == 3) {
						mess.setPosition(html);
					}
					String[] spit = html.split("：");
					if (spit.length == 2) {
						String param = spit[0];
						System.out.println(param + " param");
						if (param.equals("来自")) {
							System.out.println("来自");
							mess.setFrom(spit[1]);
						} else if (param.equals("专长")) {
							System.out.println("专长");
							mess.setIsGood(spit[1]);
						} else if (param.equals("简介")) {
							System.out.println("简介");
							mess.setIntroduce(spit[1]);
						}
					}
				}
			}
			messages.add(mess);
//			System.out.println(messages.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToDb(messages);
	}

	public static void saveToDb(List<Message> list) throws SQLException {
		DBHelper helper = new DBHelper();
		for (Message mess : list) {
			String name=mess.getName();
			String position=mess.getPosition();
			String from=mess.getFrom();
			String introduce=mess.getIntroduce();
			String isGood=mess.getIsGood();
			String sql="insert into message value(null,'" +name+"','"+ position+"' , '"+from+"' , '"+introduce+"' , '"+isGood+"')";
			System.out.println(sql);
			helper.executeNonQuery(sql);
		}
	}
}
