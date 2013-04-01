package at.ahammer.formyournotes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.intent.IntentBuilder;
import at.ahammer.formyournotes.util.DefaultDataDeployer;
import at.ahammer.formyournotes.util.FormYourNotesController;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FormFragmentLayout extends FormActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DefaultDataDeployer(this);
		setContentView(R.layout.fragment_layout);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		// atm there is only 1 (static) form
		FormYourNotesController.INSTANCE.setFormId(1);
	}
	
	public static class FormFragmentLayoutIntentBuilder extends IntentBuilder {
		
		public final static String MESSAGE = "at.ahammer.formyournotes.MESSAGE";

		public String message;

		public FormFragmentLayoutIntentBuilder(Context context) {
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
	}
}
