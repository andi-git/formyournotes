package at.ahammer.formyournotes.actionbar;

public class ActionBarR {

	private final Layout layout;

	private final Drawable drawable;

	private final Attribute attribute;

	private final Id id;

	private final Dimension dimension;

	private final StrinG stirng;

	public ActionBarR(Layout layout, Drawable drawable, Attribute attribute,
			Id id, Dimension dimension, StrinG stirng) {
		super();
		this.layout = layout;
		this.drawable = drawable;
		this.attribute = attribute;
		this.id = id;
		this.dimension = dimension;
		this.stirng = stirng;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public Layout getLayout() {
		return layout;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public Id getId() {
		return id;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public StrinG getStirng() {
		return stirng;
	}

	public static final class Layout {

		private final int actionbarCompat;
		private final int actionbarIndeterminateProgress;

		public Layout(int actionbarCompat, int actionbarIndeterminateProgress) {
			super();
			this.actionbarCompat = actionbarCompat;
			this.actionbarIndeterminateProgress = actionbarIndeterminateProgress;
		}

		public int getActionbarCompat() {
			return actionbarCompat;
		}

		public int getActionbarIndeterminateProgress() {
			return actionbarIndeterminateProgress;
		}

	}

	public static final class Drawable {

		private final int icHome;

		public Drawable(int icHome) {
			super();
			this.icHome = icHome;
		}

		public int getIcHome() {
			return icHome;
		}

	}

	public static final class Attribute {

		private final int actionbarCompatItemHomeStyle;
		private final int actionbarCompatItemStyle;
		private final int actionbarCompatProgressIndicatorStyle;
		private final int actionbarCompatTitleStyle;

		public Attribute(int actionbarCompatItemHomeStyle,
				int actionbarCompatItemStyle,
				int actionbarCompatProgressIndicatorStyle,
				int actionbarCompatTitleStyle) {
			super();
			this.actionbarCompatItemHomeStyle = actionbarCompatItemHomeStyle;
			this.actionbarCompatItemStyle = actionbarCompatItemStyle;
			this.actionbarCompatProgressIndicatorStyle = actionbarCompatProgressIndicatorStyle;
			this.actionbarCompatTitleStyle = actionbarCompatTitleStyle;
		}

		public int getActionbarCompatItemHomeStyle() {
			return actionbarCompatItemHomeStyle;
		}

		public int getActionbarCompatItemStyle() {
			return actionbarCompatItemStyle;
		}

		public int getActionbarCompatProgressIndicatorStyle() {
			return actionbarCompatProgressIndicatorStyle;
		}

		public int getActionbarCompatTitleStyle() {
			return actionbarCompatTitleStyle;
		}
	}

	public static final class Id {

		private final int actionbarCompat;
		private final int actionbarCompatItemRefresh;
		private final int actionbarCompatTitle;
		private final int actionbarCompatItemRefreshProgress;
		private final int menuRefresh;

		public Id(int actionbarCompat, int actionbarCompatItemRefresh,
				int actionbarCompatTitle,
				int actionbarCompatItemRefreshProgress, int menuRefresh) {
			super();
			this.actionbarCompat = actionbarCompat;
			this.actionbarCompatItemRefresh = actionbarCompatItemRefresh;
			this.actionbarCompatTitle = actionbarCompatTitle;
			this.actionbarCompatItemRefreshProgress = actionbarCompatItemRefreshProgress;
			this.menuRefresh = menuRefresh;
		}

		public int getActionbarCompat() {
			return actionbarCompat;
		}

		public int getActionbarCompatItemRefresh() {
			return actionbarCompatItemRefresh;
		}

		public int getActionbarCompatTitle() {
			return actionbarCompatTitle;
		}

		public int getActionbarCompatItemRefreshProgress() {
			return actionbarCompatItemRefreshProgress;
		}

		public int getMenuRefresh() {
			return menuRefresh;
		}
	}

	public static final class Dimension {

		private final int actionbarCompatButtonHomeWidth;
		private final int actionbarCompatButtonWidth;
		private final int actionbarCompatHeight;

		public Dimension(int actionbarCompatButtonHomeWidth,
				int actionbarCompatButtonWidth, int actionbarCompatHeight) {
			super();
			this.actionbarCompatButtonHomeWidth = actionbarCompatButtonHomeWidth;
			this.actionbarCompatButtonWidth = actionbarCompatButtonWidth;
			this.actionbarCompatHeight = actionbarCompatHeight;
		}

		public int getActionbarCompatButtonHomeWidth() {
			return actionbarCompatButtonHomeWidth;
		}

		public int getActionbarCompatButtonWidth() {
			return actionbarCompatButtonWidth;
		}

		public int getActionbarCompatHeight() {
			return actionbarCompatHeight;
		}
	}

	public static final class StrinG {

		private final int appName;

		public StrinG(int appName) {
			super();
			this.appName = appName;
		}

		public int getAppName() {
			return appName;
		}
	}
}
