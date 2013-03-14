package at.ahammer.formyournotes.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ViewHelper {

	public static final Typeface TEXT_TYPE = Typeface.SANS_SERIF;
	public static final int TEXT_COLOR = Color.WHITE;
	public static final int TEXT_SIZE = 20;

	public void setTextSize(TextView view) {
		view.setTextSize(TEXT_SIZE);
	}

	public void setTextType(TextView view) {
		view.setTypeface(TEXT_TYPE);
	}
	
	public void setTextColor(TextView view) {
		view.setTextSize(TEXT_SIZE);
	}
	
	public void setAllDefaults(TextView view) {
		setTextColor(view);
		setTextSize(view);
		setTextType(view);
	}
	
	public TextView newDefaultTextView(Context context) {
		TextView textView = new TextView(context);
		setAllDefaults(textView);
		return textView;
	}

	public EditText newDefaultEditText(Context context) {
		EditText editText = new EditText(context);
		setAllDefaults(editText);
		return editText;
	}
	
	public CheckBox newDefaultCheckBox(Context context) {
		CheckBox checkBox = new CheckBox(context);
		setAllDefaults(checkBox);
		return checkBox;
	}

	public Button newDefaultButton(Context context) {
		Button button = new Button(context);
		setAllDefaults(button);
		return button;
	}
}
