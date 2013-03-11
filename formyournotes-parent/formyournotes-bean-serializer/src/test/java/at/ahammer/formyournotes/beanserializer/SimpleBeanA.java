package at.ahammer.formyournotes.beanserializer;

import java.util.ArrayList;
import java.util.List;

public class SimpleBeanA {

	private String string;

	private int i;

	private List<SimpleBeanB> list = new ArrayList<SimpleBeanB>();

	public SimpleBeanA(String string, int i) {
		super();
		this.string = string;
		this.i = i;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public List<SimpleBeanB> getList() {
		return list;
	}

	public void setList(List<SimpleBeanB> list) {
		this.list = list;
	}

}
