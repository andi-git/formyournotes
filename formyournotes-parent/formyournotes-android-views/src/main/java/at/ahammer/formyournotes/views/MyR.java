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
		private int myCustomBackground;

		public int getMyCustomBackground() {
			return myCustomBackground;
		}

		public void setMyCustomBackground(int myCustomBackground) {
			this.myCustomBackground = myCustomBackground;
		}

	}
}
