package at.ahammer.formyournotes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import at.ahammer.formyournotes.intent.IntentBuilder;

/**
 * This is a secondary activity, to show what the user has selected when the
 * screen is not large enough to show it all in one activity.
 */
public class FormActivityDetail extends FormActivity {

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
			FormFragmentDetail details = new FormFragmentDetail();
			details.setArguments(getIntent().getExtras());
			getFragmentManager().beginTransaction()
					.add(android.R.id.content, details).commit();
		}
	}

	public static class FormActivityDetailIntent extends IntentBuilder {

		public final static String INDEX = "index";

		public int index;

		public final static String NAME = "displayname";

		public String name;

		public FormActivityDetailIntent(Context context) {
			super(FormActivityDetail.class, context);
		}

		public FormActivityDetailIntent setIndex(int index) {
			this.index = index;
			return this;
		}

		public FormActivityDetailIntent setName(String name) {
			this.name = name;
			return this;
		}

		@Override
		public void fillIntent(Intent intent) {
			intent.putExtra(INDEX, index);
			intent.putExtra(NAME, name);
		}

		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}
	}
}