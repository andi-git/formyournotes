package at.ahammer.formyournotes.ui.activity;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.ui.activity.FormActivityDetail.FormActivityDetailIntent;
import at.ahammer.formyournotes.ui.activity.FormFragmentDetail.FormFragmentDetailBundleBuilder;
import at.ahammer.formyournotes.util.FormYourNotesController;

/**
 * This is the "top-level" fragment, showing a list of items that the user can
 * pick. Upon picking an item, it takes care of displaying the data to the user
 * as appropriate based on the current UI layout.
 */
public class FormFragmentList extends ListFragment {

	private int currentListPosition = 0;
	private String currentDisplayName = "";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Populate list with our static array of titles.
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				FormYourNotesController.INSTANCE
						.allDataNamesForCurrentForm(getActivity())));
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			currentListPosition = savedInstanceState.getInt(
					FormFragmentDetailBundleBuilder.ARG_INDEX, 0);
			currentDisplayName = savedInstanceState.getString(
					FormFragmentDetailBundleBuilder.ARG_DISPLAYNAME, "");
		}
		if (isInDualMode()) {
			// In dual-pane mode, the list view highlights the selected
			// item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(currentListPosition, currentDisplayName);
		}
	}

	private boolean isInDualMode() {
		View detailsFrame = getActivity().findViewById(R.id.fragment_details);
		return detailsFrame != null
				&& detailsFrame.getVisibility() == View.VISIBLE;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(FormFragmentDetailBundleBuilder.ARG_INDEX,
				currentListPosition);
		outState.putString(FormFragmentDetailBundleBuilder.ARG_DISPLAYNAME,
				currentDisplayName);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position, l.getItemAtPosition(position).toString());
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	private void showDetails(int index, String displayName) {
		Log.i(LogTag.FYN.getTag(), "show details for: " + index + ", "
				+ displayName);
		currentListPosition = index;
		currentDisplayName = displayName;
		if (isInDualMode()) {
			// We can display everything in-place with fragments, so update the
			// list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);
			// Check what fragment is currently shown, replace if needed.
			FormFragmentDetail details = (FormFragmentDetail) getFragmentManager()
					.findFragmentById(R.id.fragment_details);
			if (details == null || details.getShownIndex() != index) {
				// Make new fragment to show this selection.
				details = FormFragmentDetail.newInstance(index, displayName);
				// Execute a transaction, replacing any existing fragment with
				// this one inside the frame.
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.fragment_details, details);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			startActivity(new FormActivityDetailIntent(getActivity()).//
					setIndex(index).//
					setName(displayName).//
					build());
		}
	}
}