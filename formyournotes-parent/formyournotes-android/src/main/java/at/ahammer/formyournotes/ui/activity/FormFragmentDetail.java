package at.ahammer.formyournotes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.intent.BundleBuilder;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.util.FormYourNotesController;
import at.ahammer.formyournotes.views.FormR;
import at.ahammer.formyournotes.views.ViewHelper;

/**
 * This is the secondary fragment, displaying the details of a particular item.
 */
public class FormFragmentDetail extends Fragment {

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static FormFragmentDetail newInstance(int index, String displayName) {
		// FormFragmentDetail f = new FormFragmentDetail();
		// Bundle args = new Bundle();
		// args.putInt("index", index);
		// f.setArguments(args);
		// return f;
		return new FormFragmentDetailBundleBuilder().//
				setIndex(index).//
				setDisplayName(displayName).//
				fillFragment(new FormFragmentDetail());
	}

	public int getShownIndex() {
//		return getArguments().getInt(FormFragmentDetailBundleBuilder.ARG_INDEX,
//				0);
		return getArguments().getInt("index",
				0);
	}

	public String getShownDisplayName() {
//		return getArguments().getString(
//				FormFragmentDetailBundleBuilder.ARG_DISPLAYNAME, "");
		return getArguments().getString("displayName", "");
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

		Log.i(LogTag.FYN.getTag(), "selected index of list:  " + getShownIndex());
		Log.i(LogTag.FYN.getTag(), "name of data to display: " + getShownDisplayName());
		FormData formData = FormYourNotesController.INSTANCE.getCurrentDataByName(getActivity(), getShownDisplayName());
		FormYourNotesController.INSTANCE.setDataId(formData.getDataId());

		FormBean currentFormBean = FormYourNotesController.INSTANCE.getCurrentFormBeanWithCurrentData(getActivity());
		
		// TODO ugly!
//		DataDao dataDao = new DataDaoJSON(
//				FYNFileHelper.getExternalStorage(getActivity()));
//		List<FormData> formDataList = new ArrayList<FormData>();
//		List<String> sortedNames = new ArrayList<String>();
//		try {
//			formDataList.addAll(dataDao.allDataForForm(1));
//		} catch (DaoException e) {
//			throw new RuntimeException(e);
//		}
//		for (FormData formData : formDataList) {
//			sortedNames.add(formData.getName());
//		}
		ScrollView scroller = new ScrollView(getActivity());
//		if (!sortedNames.isEmpty()) {
//			Collections.sort(sortedNames);
//			String selectedName = sortedNames.get(getShownIndex());
//			FormBean currentFormBean = new FormBean();
//			for (FormData formData : formDataList) {
//				if (formData.getName().equals(selectedName)) {
//					FormDao formDao = new FormDaoJSON(
//							FYNFileHelper.getExternalStorage(getActivity()));
//					try {
//						currentFormBean = formDao.readWithData(
//								formData.getFormId(), formData.getDataId());
//						FormYourNotesController.INSTANCE.setDataId(formData
//								.getDataId());
//					} catch (DaoException e) {
//						throw new RuntimeException(e);
//					}
//					break;
//				}
//			}
			Log.i(LogTag.FYN.getTag(),
					"current formBean: " + currentFormBean.getId() + ", "
							+ currentFormBean.getName());

			ViewHelper viewHelper = new ViewHelper();

			scroller.setLayoutParams(viewHelper.getLinearLayoutParam());
			LinearLayout layout = new LinearLayout(getActivity());
			layout.setLayoutParams(viewHelper.getLinearLayoutParam());
			FormR formR = new FormR();
			formR.getDrawable().setBorderTopElement(
					R.drawable.border_top_element);
			formR.getDrawable().setButtonEdit(R.drawable.button_edit);
			formR.getDrawable().setButtonDown(R.drawable.button_down);

			FormYourNotesController.INSTANCE.getCurrentFormView(
					currentFormBean, getActivity(), formR).addToView(layout);
			Log.i(LogTag.FYN.getTag(), "layout: " + layout);
			scroller.addView(layout);
//		}
		return scroller;
	}

	public static class FormFragmentDetailBundleBuilder extends BundleBuilder {

		public static final String ARG_INDEX = "index";
		public static final String ARG_DISPLAYNAME = "displayName";
		public int index;
		public String displayName;

		public int getIndex() {
			return index;
		}

		public FormFragmentDetailBundleBuilder setIndex(int index) {
			this.index = index;
			return this;
		}

		public String getDisplayName() {
			return displayName;
		}

		public FormFragmentDetailBundleBuilder setDisplayName(String displayName) {
			this.displayName = displayName;
			return this;
		}

		@Override
		public void fillBundle(Bundle bundle) {
			bundle.putInt(ARG_INDEX, index);
			bundle.putString(ARG_DISPLAYNAME, displayName);
		}
	}
}