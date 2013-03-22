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
		private int buttonEdit;
		private int buttonDown;

		public int getBorderTopElement() {
			return borderTopElement;
		}

		public void setBorderTopElement(int borderTopElement) {
			this.borderTopElement = borderTopElement;
		}

		public int getButtonEdit() {
			return buttonEdit;
		}

		public void setButtonEdit(int buttonEdit) {
			this.buttonEdit = buttonEdit;
		}

		public int getButtonDown() {
			return buttonDown;
		}

		public void setButtonDown(int buttonDown) {
			this.buttonDown = buttonDown;
		}
	}
}
