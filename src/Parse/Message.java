package Parse;

public class Message {
	public String name;
	public String position;
	public String from;
	public String introduce;
	public String isGood;
	@Override
	public String toString() {
		return "Message [from=" + from + ", introduce=" + introduce
				+ ", isGood=" + isGood + ", name=" + name + ", position="
				+ position + "]";
	}
	public String getIsGood() {
		return isGood;
	}
	public void setIsGood(String isGood) {
		this.isGood = isGood;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
}
