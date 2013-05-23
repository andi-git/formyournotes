package at.ahammer.formyournotes.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.SelectBean;

public class SelectView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final TextView viewColon;
	private final Spinner spinner;

	public SelectView(Context context, FormR r, FormBean formBean,
			SelectBean selectBean) {
		super(context);
		setId(selectBean.getId());
		setOrientation(HORIZONTAL);
		viewName = viewHelper.newDefaultTextView(context);
		viewName.setText(selectBean.getDiscription());
		spinner = viewHelper.newDefaultSpinner(context);
		spinner.setAdapter(new ArrayAdapter<String>(context, r.getLayout()
				.getSimpleSpinnerDropdownItem(), selectBean.getValuesArray()));
		spinner.setSelection(selectBean.getSelectedPosision());
		spinner.setOnItemSelectedListener(new SelectWatcher(spinner,
				selectBean, formBean));
		viewColon = viewHelper.newDefaultTextView(context);
		viewColon.setText(": ");
		addView(viewName, viewHelper.getLinearLayoutParamWrap());
		addView(viewColon, viewHelper.getLinearLayoutParamWrap());
		addView(spinner, viewHelper.getLinearLayoutParamWrap());
	}

	public String getName() {
		return viewName.getText().toString();
	}

	public void setName(String name) {
		viewName.setText(name);
	}

	public static class SelectWatcher implements OnItemSelectedListener {

		private final Spinner spinner;

		private final SelectBean selectBean;

		private final FormBean formBean;

		public SelectWatcher(Spinner spinner, SelectBean selectBean,
				FormBean formBean) {
			this.spinner = spinner;
			this.selectBean = selectBean;
			this.formBean = formBean;
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String savedData = selectBean.getData().getSelected();
			String item = (String) spinner.getSelectedItem();
			if (savedData == null || !savedData.equals(item)) {
				Log.i("FormYourNotes",
						"set value of " + selectBean.getDiscription() + " to '"
								+ item + "'");
				selectBean.getData().setItemId(selectBean);
				// TODO why do i have to set that hard-coded?
				formBean.setDataChange(true);
				selectBean.getData().setSelected(item);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing
		}
	}
}