package at.ahammer.formyournotes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.http.NetworkDetector;
import at.ahammer.formyournotes.http.NetworkDetector.ConnectionType;
import at.ahammer.formyournotes.intent.IntentBuilder;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.util.FYNController;
import at.ahammer.formyournotes.util.FYNDefaultDataDeployer;
import at.ahammer.formyournotes.util.FYNPreferences;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FormFragmentLayout extends FormActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new FYNDefaultDataDeployer(this);
		setContentView(R.layout.form_fragment);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		// atm there is only 1 (static) form
		FYNController.INSTANCE.setFormId(1);
		Log.i(LogTag.FYN.getTag(),
				"network any: "
						+ new NetworkDetector().hasNetworkConnection(this,
								ConnectionType.ANY));
		Log.i(LogTag.FYN.getTag(),
				"network mobile: "
						+ new NetworkDetector().hasNetworkConnection(this,
								ConnectionType.MOBILE));
		Log.i(LogTag.FYN.getTag(),
				"network wifi: "
						+ new NetworkDetector().hasNetworkConnection(this,
								ConnectionType.WIFI));
	}

	@Override
	public void onStart() {
		super.onStart();
		FYNPreferences.INSTANCE.setAccount(this, "andreas.ahammer@gmail.com", "bf1942");
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	public static class FormFragmentLayoutIntent extends IntentBuilder {

		public final static String MESSAGE = "message";

		public String message;

		public FormFragmentLayoutIntent(Context context) {
			super(FormFragmentLayout.class, context);
		}

		public IntentBuilder setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getMessage() {
			return message;
		}

		@Override
		public void fillIntent(Intent intent) {
			intent.putExtra(MESSAGE, message);
		}

		@Override
		public FormFragmentLayoutIntent addValues(Intent intent) {
			message = intent.getExtras().getString(MESSAGE, "");
			return this;
		}

		@Override
		public Bundle asBundle() {
			Bundle bundle = new Bundle();
			bundle.putString(MESSAGE, message);
			return bundle;
		}

		@Override
		public FormFragmentLayoutIntent addValues(Bundle bundle) {
			message = bundle.getString(MESSAGE, "");
			return this;
		}

		@Override
		public Bundle fillBundle(Bundle bundle) {
			bundle.putString(MESSAGE, message);
			return bundle;
		}
	}
}
