package at.ahammer.formyournotes.beanserializer;

public class SimpleBeanB {

	private String bstring;

	private boolean b;

	public SimpleBeanB(String bstring, boolean b) {
		super();
		this.bstring = bstring;
		this.b = b;
	}

	public String getBstring() {
		return bstring;
	}

	public void setBstring(String bstring) {
		this.bstring = bstring;
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

}
