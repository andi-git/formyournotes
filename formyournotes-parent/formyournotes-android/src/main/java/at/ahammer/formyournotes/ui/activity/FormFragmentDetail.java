package at.ahammer.formyournotes.ui.activity;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.ui.activity.FormActivityDetail.FormActivityDetailIntent;
import at.ahammer.formyournotes.util.FYNController;
import at.ahammer.formyournotes.util.FYNFormHelper;
import at.ahammer.formyournotes.views.ViewHelper;

/**
 * This is the secondary fragment, displaying the details of a particular item.
 */
public class FormFragmentDetail extends Fragment {

	private FormActivityDetailIntent formActivityDetailIntent;

	private ViewHelper viewHelper = new ViewHelper();

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 * 
	 * @param context
	 */
	public static FormFragmentDetail newInstance(int index, String name,
			Context context) {
		FormFragmentDetail f = new FormFragmentDetail();

		// Supply index input as an argument.
		Log.i(LogTag.FYN.getTag(),
				"newInstance of DetailsFragment create bundle with index: "
						+ index + " and name: " + name);
		f.setArguments(new FormActivityDetailIntent(context).setIndex(index)
				.setName(name).asBundle());
		return f;
	}

	public int getShownIndex() {
		if (formActivityDetailIntent == null) {
			return 0;
		}
		return formActivityDetailIntent.getIndex();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}

		formActivityDetailIntent = new FormActivityDetailIntent(getActivity())
				.addValues(getArguments());

		Log.i(LogTag.FYN.getTag(),
				"create view of DetailsFragment with index: "
						+ formActivityDetailIntent.getIndex() + " and name "
						+ formActivityDetailIntent.getName());

		ScrollView scroller = new ScrollView(getActivity());

		String displayName = formActivityDetailIntent.getName();
		// ISSUE 2: if there is no display-name (e.g. on startup) choose name
		// via index
		if (displayName == null || "".equals(displayName)) {
			List<String> allNamesSorted = FYNController.INSTANCE
					.allDataNamesForCurrentForm(getActivity());
			if (!allNamesSorted.isEmpty()) {
				displayName = allNamesSorted.get(getShownIndex());
			}
		}

		if (displayName != null && !"".equals(displayName)) {
			FormData formData = FYNController.INSTANCE.getCurrentDataByName(
					getActivity(), displayName);
			FYNController.INSTANCE.setDataId(formData.getDataId());

			FormBean currentFormBean = FYNController.INSTANCE
					.getCurrentFormBeanWithCurrentData(getActivity());

			Log.i(LogTag.FYN.getTag(),
					"current formBean: " + currentFormBean.getId() + ", "
							+ currentFormBean.getName());

			scroller.setLayoutParams(viewHelper.getLinearLayoutParamMatch());
			LinearLayout layout = new LinearLayout(getActivity());
			layout.setLayoutParams(viewHelper.getLinearLayoutParamMatch());

			FYNController.INSTANCE.getCurrentFormView(currentFormBean,
					getActivity(), FYNFormHelper.INSTANCE.getFormR())
					.addToView(layout);
			scroller.addView(layout, viewHelper.getLinearLayoutParamMatch());
		}
		return scroller;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Toast.makeText(getActivity(), "Save Form Data", Toast.LENGTH_SHORT).show();
		FYNController.INSTANCE.updateFormData(getActivity());
	}
}