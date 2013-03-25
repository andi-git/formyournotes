package at.ahammer.formyournotes.email;

import android.content.Context;
import android.content.Intent;
import at.ahammer.formyournotes.intent.AbstractIntent;

public class EMailIntent extends AbstractIntent {

	private final String emailAddress;

	public EMailIntent(Context context, String emailAddress) {
		super(context);
		this.emailAddress = emailAddress;
	}

	@Override
	public void perform() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });
		intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
		intent.putExtra(Intent.EXTRA_TEXT, "text");
		getContext()
				.startActivity(Intent.createChooser(intent, "Send mail..."));
	}
}
