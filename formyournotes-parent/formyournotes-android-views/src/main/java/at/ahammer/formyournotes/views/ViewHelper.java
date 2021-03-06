package at.ahammer.formyournotes.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewHelper {

	public static final Typeface TEXT_TYPE = Typeface.SANS_SERIF;
	public static final int TEXT_COLOR = Color.WHITE;
	public static final int TEXT_SIZE = 16;
	public static final Typeface HEADER_TYPE = TEXT_TYPE;
	public static final int HEADER_COLOR = TEXT_COLOR;
	public static final int HEADER_SIZE = 20;

	public void setTextSize(TextView view) {
		view.setTextSize(TEXT_SIZE);
	}

	public void setTextType(TextView view) {
		view.setTypeface(TEXT_TYPE);
	}

	public void setTextColor(TextView view) {
		view.setTextSize(TEXT_SIZE);
	}

	public void setHeaderSize(TextView view) {
		view.setTextSize(HEADER_SIZE);
	}

	public void setHeaderType(TextView view) {
		view.setTypeface(HEADER_TYPE);
	}

	public void setHeaderColor(TextView view) {
		view.setTextSize(HEADER_SIZE);
	}

	public void setAllDefaults(TextView view) {
		setTextColor(view);
		setTextSize(view);
		setTextType(view);
	}

	public void setAllHeaders(TextView view) {
		setHeaderColor(view);
		setHeaderSize(view);
		setHeaderType(view);
		view.setTypeface(HEADER_TYPE, Typeface.BOLD_ITALIC);
	}

	public TextView newDefaultTextView(Context context) {
		TextView textView = new TextView(context);
		setAllDefaults(textView);
		return textView;
	}

	public TextView newDefaultTextViewBlank(Context context) {
		TextView textView = new TextView(context);
		textView.setText(" ");
		setAllDefaults(textView);
		return textView;
	}

	public TextView newHeaderTextView(Context context) {
		TextView textView = new TextView(context);
		setAllHeaders(textView);
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

	public Spinner newDefaultSpinner(Context context) {
		Spinner spinner = new Spinner(context);
		// TODO set defaults?
		return spinner;
	}

	public LinearLayout.LayoutParams getLinearLayoutParamFirstInRow() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(20, 0, 0, 0);
		return layoutParams;
	}

	public LinearLayout.LayoutParams getLinearLayoutGroupHeader() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(20, 5, 0, 5);
		return layoutParams;
	}

	public LinearLayout.LayoutParams getLinearLayoutParamWrap() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 0, 0, 0);
		return layoutParams;
	}

	public LinearLayout.LayoutParams getLinearLayoutCalendarParamWrap() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 10, 0, 5);
		return layoutParams;
	}

	public LinearLayout.LayoutParams getLinearLayoutParamMatch() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(0, 0, 0, 0);
		return layoutParams;
	}

	public LinearLayout.LayoutParams getLinearLayoutTopElements() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(5, 5, 5, 5);
		return layoutParams;
	}
}
