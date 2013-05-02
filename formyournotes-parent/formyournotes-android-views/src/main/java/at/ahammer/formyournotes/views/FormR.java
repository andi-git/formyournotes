package at.ahammer.formyournotes.views;

public class FormR {

	private Drawable drawable = new Drawable();

	private Style style = new Style();

	public FormR() {
		// nothing
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public Style getStyle() {
		return style;
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

	public static final class Style {
		private int widget_DeviceDefault_Light_Spinner = 16974227;

		public int getWidget_DeviceDefault_Light_Spinner() {
			return widget_DeviceDefault_Light_Spinner;
		}

		public void setWidget_DeviceDefault_Light_Spinner(
				int widget_DeviceDefault_Light_Spinner) {
			this.widget_DeviceDefault_Light_Spinner = widget_DeviceDefault_Light_Spinner;
		}
	}
}
