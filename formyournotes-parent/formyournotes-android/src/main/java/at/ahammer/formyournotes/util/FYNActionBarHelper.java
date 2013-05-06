package at.ahammer.formyournotes.util;

import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.actionbar.ActionBarR;
import at.ahammer.formyournotes.actionbar.ActionBarR.Attribute;
import at.ahammer.formyournotes.actionbar.ActionBarR.Dimension;
import at.ahammer.formyournotes.actionbar.ActionBarR.Drawable;
import at.ahammer.formyournotes.actionbar.ActionBarR.Id;
import at.ahammer.formyournotes.actionbar.ActionBarR.Layout;
import at.ahammer.formyournotes.actionbar.ActionBarR.StrinG;

public enum FYNActionBarHelper {

	INSTANCE;

	final ActionBarR actionBarR;

	FYNActionBarHelper() {
		actionBarR = createActionBarR();
	}

	public ActionBarR getActionBarR() {
		return actionBarR;
	}

	public ActionBarR createActionBarR() {

		int actionbarLayoutCompat = R.layout.actionbar_compat;
		int actionbarLayoutIndeterminateProgress = R.layout.actionbar_indeterminate_progress;

		int icHome = R.drawable.actionbar_home;

		int actionbarCompatItemStyle = R.attr.actionbarCompatTitleStyle;
		int actionbarCompatItemHomeStyle = R.attr.actionbarCompatItemHomeStyle;
		int actionbarCompatTitleStyle = R.attr.actionbarCompatItemStyle;
		int actionbarCompatProgressIndicatorStyle = R.attr.actionbarCompatProgressIndicatorStyle;

		int actionbarCompatItemRefresh = R.id.actionbar_compat_item_refresh;
		int actionbarCompatTitle = R.id.actionbar_compat_title;
		int actionbarCompat = R.id.actionbar_compat;
		int menuRefresh = R.id.menu_refresh;
		int actionbarCompatItemRefreshProgress = R.id.actionbar_compat_item_refresh_progress;

		int actionbarCompatButtonHomeWidth = R.dimen.actionbar_compat_button_home_width;
		int actionbarCompatButtonWidth = R.dimen.actionbar_compat_button_width;
		int actionbarCompatHeight = R.dimen.actionbar_compat_height;
		int appName = R.string.app_name;

		Layout layout = new ActionBarR.Layout(actionbarLayoutCompat,
				actionbarLayoutIndeterminateProgress);

		Drawable drawable = new ActionBarR.Drawable(icHome);

		Attribute attribute = new ActionBarR.Attribute(
				actionbarCompatItemHomeStyle, actionbarCompatItemStyle,
				actionbarCompatProgressIndicatorStyle,
				actionbarCompatTitleStyle);

		Id id = new ActionBarR.Id(actionbarCompat, actionbarCompatItemRefresh,
				actionbarCompatTitle, actionbarCompatItemRefreshProgress,
				menuRefresh);

		Dimension dimension = new ActionBarR.Dimension(
				actionbarCompatButtonHomeWidth, actionbarCompatButtonWidth,
				actionbarCompatHeight);

		StrinG stirng = new ActionBarR.StrinG(appName);

		return new ActionBarR(layout, drawable, attribute, id, dimension,
				stirng);
	}
}
