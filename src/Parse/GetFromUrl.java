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

			Parser nodesParser = new Parser(body);// ��ʼ��Parser������Ҫע�⵼���Ϊorg.htmlparser����������кܶࡣ����ط���д������ǰ��ȡ�õ�html�ı���Ҳ���Դ���URl����
			nodesParser.setEncoding("utf-8");// ���ñ����
			AndFilter filter = new AndFilter(new TagNameFilter("div"),
					new HasAttributeFilter("id", "expertInfoView"));// ͨ��filter�ҵ�div��div��idΪsongListWrapper

			NodeList nodes = nodesParser.parse(filter);// ͨ��filter��ȡnodes

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
					String[] spit = html.split("��");
					if (spit.length == 2) {
						String param = spit[0];
						System.out.println(param + " param");
						if (param.equals("����")) {
							System.out.println("����");
							mess.setFrom(spit[1]);
						} else if (param.equals("ר��")) {
							System.out.println("ר��");
							mess.setIsGood(spit[1]);
						} else if (param.equals("���")) {
							System.out.println("���");
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
