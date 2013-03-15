package at.ahammer.formyournotes.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;

public class GroupView extends LinearLayout {

	private BeanViewMapper beanViewMapper = new BeanViewMapper();
	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;

	public GroupView(Context context, MyR r, FormBean formBean,
			GroupBean groupBean) {
		super(context);
		setOrientation(VERTICAL);
		setBackground(getResources().getDrawable(
				r.getDrawable().getMyCustomBackground()));
		viewName = viewHelper.newHeaderTextView(context);
		viewName.setText(groupBean.getName());
		addView(viewName, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		for (FormYourNotesBean<?> child : formBean.getAllChildren(groupBean)) {
			View view = beanViewMapper
					.getView(getContext(), r, formBean, child);
			addView(view, new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		// Paint paint = new Paint();
		// paint.setColor(Color.RED);
		// paint.setStrokeWidth(1.5f);
		// paint.setStyle(Style.STROKE);
		// canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

//		Paint paint = new Paint();
//		paint.setColor(Color.RED);
//		paint.setStrokeWidth(1.0f);
//		canvas.drawRect(0, 0, getWidth(), 1.0f, paint);
//		canvas.drawRect(0, 0, 1.0f, getHeight(), paint);
//		canvas.drawRect(0, getHeight() - 1.0f, getWidth(), getHeight(), paint);
//		canvas.drawRect(getWidth() - 1.0f, 0, getHeight(), getWidth(), paint);
	}

	public String getName() {
		return viewName.getText().toString();
	}
}