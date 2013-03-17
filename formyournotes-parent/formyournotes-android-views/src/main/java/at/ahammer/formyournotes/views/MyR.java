package at.ahammer.formyournotes.views;

public class MyR {

	private Drawable drawable = new Drawable();

	public MyR() {
		// nothing
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public static final class Drawable {
		private int borderTopElement;

		public int getBorderTopElement() {
			return borderTopElement;
		}

		public void setBorderTopElement(int borderTopElement) {
			this.borderTopElement = borderTopElement;
		}

	}
}
