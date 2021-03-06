package at.ahammer.formyournotes.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public abstract class IntentBuilder {

	private final Class<? extends Activity> activity;
	
	private final Context context;
	
	public IntentBuilder(Class<? extends Activity>activity , Context context) {
		this.activity = activity;
		this.context = context;
	}
	
	public abstract void fillIntent(Intent intent);
	
	public Intent build() {
		Intent intent = new Intent(context, activity);
		fillIntent(intent);
		return intent;
	}
	
	public Class<? extends Activity> getActivity() {
		return this.activity;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public abstract IntentBuilder addValues(Intent intent);

	public abstract IntentBuilder addValues(Bundle bundle);
	
	public abstract Bundle asBundle();
	
	public abstract Bundle fillBundle(Bundle bundle);
}
