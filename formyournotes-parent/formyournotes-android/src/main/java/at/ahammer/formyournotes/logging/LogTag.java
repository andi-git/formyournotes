package at.ahammer.formyournotes.logging;

public enum LogTag {

	FYN("FYN");
	
	final String tag;
	
	LogTag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return this.tag;
	}
}
