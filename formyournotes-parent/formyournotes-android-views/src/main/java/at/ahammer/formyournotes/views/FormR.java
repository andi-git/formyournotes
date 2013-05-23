package at.ahammer.formyournotes.views;

public class FormR {

	private Drawable drawable = new Drawable();

	private Style style = new Style();

	private Layout layout = new Layout();

	public FormR() {
		// nothing
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public Style getStyle() {
		return style;
	}

	public Layout getLayout() {
		return layout;
	}

	public static final class Drawable {
		private int borderTopElement;
		private int borderLightGray;
		private int buttonContact;
		private int buttonChat;
		private int buttonCalendar;

		public int getBorderTopElement() {
			return borderTopElement;
		}

		public void setBorderTopElement(int borderTopElement) {
			this.borderTopElement = borderTopElement;
		}

		public int getBorderLightGray() {
			return borderLightGray;
		}

		public void setBorderLightGray(int borderLightGray) {
			this.borderLightGray = borderLightGray;
		}

		public int getButtonContact() {
			return buttonContact;
		}

		public void setButtonContact(int buttonContact) {
			this.buttonContact = buttonContact;
		}

		public int getButtonChat() {
			return buttonChat;
		}

		public void setButtonChat(int buttonChat) {
			this.buttonChat = buttonChat;
		}

		public int getButtonCalendar() {
			return buttonCalendar;
		}

		public void setButtonCalendar(int buttonCalendar) {
			this.buttonCalendar = buttonCalendar;
		}
	}

	public static final class Layout {
		private int simpleSpinnerDropdownItem = android.R.layout.simple_spinner_dropdown_item;

		public int getSimpleSpinnerDropdownItem() {
			return simpleSpinnerDropdownItem;
		}

		public void setSimpleSpinnerDropdownItem(int simpleSpinnerDropdownItem) {
			this.simpleSpinnerDropdownItem = simpleSpinnerDropdownItem;
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
