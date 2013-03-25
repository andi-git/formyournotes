package at.ahammer.formyournotes.phone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import at.ahammer.formyournotes.intent.AbstractIntent;

public class PhoneIntent extends AbstractIntent {

	private final String number;

	private final Type type;

	public static enum Type {
		TELEPHONE("tel:"), SMS("sms:");

		private final String uriPrefix;

		Type(String uriPrefix) {
			this.uriPrefix = uriPrefix;
		}

		public String getUriPrefix() {
			return uriPrefix;
		}
	}

	public PhoneIntent(Context context, String number, Type type) {
		super(context);
		this.number = number;
		this.type = type;
	}

	@Override
	public void perform() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(type.getUriPrefix() + number));
		getContext().startActivity(
				Intent.createChooser(intent, "Start phone action..."));
	}
}
