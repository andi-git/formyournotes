package at.ahammer.formyournotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.FormDao;
import at.ahammer.formyournotes.dao.json.DataDaoJSON;
import at.ahammer.formyournotes.dao.json.FormDaoJSON;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.views.BeanViewMapper;
import at.ahammer.formyournotes.views.MyR;
import at.ahammer.formyournotes.views.ViewHelper;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FragmentLayout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DefaultDataDeployer(this);
		setContentView(R.layout.fragment_layout);
	}

	/**
	 * This is a secondary activity, to show what the user has selected when the
	 * screen is not large enough to show it all in one activity.
	 */

	public static class DetailsActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				// If the screen is now in landscape mode, we can show the
				// dialog in-line with the list so we don't need this
				// activity.
				finish();
				return;
			}

			if (savedInstanceState == null) {
				// During initial setup, plug in the details fragment.
				DetailsFragment details = new DetailsFragment();
				details.setArguments(getIntent().getExtras());
				getFragmentManager().beginTransaction()
						.add(android.R.id.content, details).commit();
			}
		}
	}

	/**
	 * This is the "top-level" fragment, showing a list of items that the user
	 * can pick. Upon picking an item, it takes care of displaying the data to
	 * the user as appropriate based on the currrent UI layout.
	 */

	public static class TitlesFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			// TODO ugly!
			DataDao dataDao = new DataDaoJSON(getActivity().getFilesDir());
			List<FormData> formDataList = new ArrayList<FormData>();
			List<String> sortedNames = new ArrayList<String>();
			try {
				formDataList.addAll(dataDao.allDataForForm(1));
			} catch (DaoException e) {
				throw new RuntimeException(e);
			}
			for (FormData formData : formDataList) {
				sortedNames.add(formData.getName());
			}
			Collections.sort(sortedNames);
			
			
			// Populate list with our static array of titles.
			// setListAdapter(new ArrayAdapter<String>(getActivity(),
			// android.R.layout.simple_list_item_activated_1,
			// Shakespeare.TITLES));
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_activated_1,
					sortedNames));

			// Check to see if we have a frame in which to embed the details
			// fragment directly in the containing UI.
			View detailsFrame = getActivity().findViewById(R.id.details);
			mDualPane = detailsFrame != null
					&& detailsFrame.getVisibility() == View.VISIBLE;

			if (savedInstanceState != null) {
				// Restore last state for checked position.
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			}

			if (mDualPane) {
				// In dual-pane mode, the list view highlights the selected
				// item.
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				// Make sure our UI is in the correct state.
				showDetails(mCurCheckPosition);
			}
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("curChoice", mCurCheckPosition);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			showDetails(position);
		}

		/**
		 * Helper function to show the details of a selected item, either by
		 * displaying a fragment in-place in the current UI, or starting a whole
		 * new activity in which it is displayed.
		 */
		void showDetails(int index) {
			mCurCheckPosition = index;

			if (mDualPane) {
				// We can display everything in-place with fragments, so
				// update
				// the list to highlight the selected item and show the
				// data.
				getListView().setItemChecked(index, true);

				// Check what fragment is currently shown, replace if
				// needed.
				DetailsFragment details = (DetailsFragment) getFragmentManager()
						.findFragmentById(R.id.details);
				if (details == null || details.getShownIndex() != index) {
					// Make new fragment to show this selection.
					details = DetailsFragment.newInstance(index);

					// Execute a transaction, replacing any existing
					// fragment
					// with this one inside the frame.
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					// if (index == 0) {
					ft.replace(R.id.details, details);
					// } else {
					// ft.replace(R.id.a_item, details);
					// }
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}

			} else {
				// Otherwise we need to launch a new activity to display
				// the dialog fragment with selected text.
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailsActivity.class);
				intent.putExtra("index", index);
				startActivity(intent);
			}
		}
	}

	/**
	 * This is the secondary fragment, displaying the details of a particular
	 * item.
	 */

	public static class DetailsFragment extends Fragment {
		/**
		 * Create a new instance of DetailsFragment, initialized to show the
		 * text at 'index'.
		 */
		public static DetailsFragment newInstance(int index) {
			DetailsFragment f = new DetailsFragment();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
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

			// TODO ugly!
			DataDao dataDao = new DataDaoJSON(getActivity().getFilesDir());
			List<FormData> formDataList = new ArrayList<FormData>();
			List<String> sortedNames = new ArrayList<String>();
			try {
				formDataList.addAll(dataDao.allDataForForm(1));
			} catch (DaoException e) {
				throw new RuntimeException(e);
			}
			for (FormData formData : formDataList) {
				sortedNames.add(formData.getName());
			}
			Collections.sort(sortedNames);
			String selectedName = sortedNames.get(getShownIndex());
			FormBean currentFormBean = new FormBean();
			for (FormData formData : formDataList) {
				if (formData.getName().equals(selectedName)) {
					FormDao formDao = new FormDaoJSON(getActivity().getFilesDir());
					try {
						currentFormBean = formDao.readWithData(formData.getFormId(), formData.getDataId());
					} catch (DaoException e) {
						throw new RuntimeException(e);
					}
					break;
				}
			}
			Log.i(LogTag.FYN.getTag(), "current formBean: " + currentFormBean.getId() + ", " + currentFormBean.getName());
			
			ViewHelper viewHelper = new ViewHelper();
			ScrollView scroller = new ScrollView(getActivity());
			
			scroller.setLayoutParams(viewHelper.getLinearLayoutParam());
			LinearLayout layout = new LinearLayout(getActivity());
			layout.setLayoutParams(viewHelper.getLinearLayoutParam());
			MyR myR = new MyR();
			myR.getDrawable().setBorderTopElement(R.drawable.border_top_element);
			new BeanViewMapper().add(getActivity(), layout, myR, currentFormBean);
			Log.i(LogTag.FYN.getTag(), "layout: " + layout);
			scroller.addView(layout);
			
			return scroller;
		}
	}
}
