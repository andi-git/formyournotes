package at.ahammer.formyournotes.ui.activity;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.ui.activity.FormActivityDetail.FormActivityDetailIntent;
import at.ahammer.formyournotes.util.FYNController;

/**
 * This is the "top-level" fragment, showing a list of items that the user can
 * pick. Upon picking an item, it takes care of displaying the data to the user
 * as appropriate based on the current UI layout.
 */
public class FormFragmentList extends ListFragment {

	int currentPosition = 0;
	
	String currentName = "";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Populate list with our static array of titles.
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				FYNController.INSTANCE
						.allDataNamesForCurrentForm(getActivity())));

		if (savedInstanceState != null) {
			// Restore last state for checked position.
			FormActivityDetailIntent detailsActivityIntent = new FormActivityDetailIntent(
					getActivity()).addValues(savedInstanceState);
			currentPosition = detailsActivityIntent.getIndex();
			currentName = detailsActivityIntent.getName();
		}

		if (isInDualMode()) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(currentPosition, currentName);
		}
	}

	private boolean isInDualMode() {
		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.form_fragment_detail);
		return detailsFrame != null
				&& detailsFrame.getVisibility() == View.VISIBLE;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		new FormActivityDetailIntent(getActivity()).//
				setIndex(currentPosition).//
				setName(currentName).//
				fillBundle(outState);
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
	private void showDetails(int index, String name) {
		currentPosition = index;
		currentName = name;

		if (isInDualMode()) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);

			// Check what fragment is currently shown, replace if needed.
			FormFragmentDetail details = (FormFragmentDetail) getFragmentManager()
					.findFragmentById(R.id.form_layout_detail);
			if (details == null || details.getShownIndex() != index) {
				// Make new fragment to show this selection.
				details = FormFragmentDetail.newInstance(index, name,
						getActivity());
				// Execute a transaction, replacing any existing fragment with
				// this one inside the frame.
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.form_fragment_detail, details);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}

		} else {
			// Otherwise we need to launch a new activity to display the dialog
			// fragment with selected text.
			startActivity(new FormActivityDetailIntent(getActivity())
					.setIndex(index).setName(name).build());
		}
	}
}