package at.ahammer.formyournotes.intent;

import android.content.Context;

public abstract class AbstractIntent {

	private final Context context;
	
	public AbstractIntent(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	public abstract void perform();
}
