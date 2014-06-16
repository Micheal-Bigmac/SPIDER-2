package spider;

public class test {
	public static void main(String[] args) {
		String[] test = { "http://zhidao.baidu.com/prof/view/888261962###",
				"http://zhidao.baidu.com/prof/view/#{uname}",
				"http://zhidao.baidu.com/feedback",
				"http://zhidao.baidu.com/yisheng" };
		for (String o : test) {
			if (o.matches(UrlAddr.targetUrl)) {
				System.out.println("==========----------" + o);
			}
		}
	}
}
