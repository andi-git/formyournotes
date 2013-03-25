package at.ahammer.formyournotes.maps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import at.ahammer.formyournotes.intent.AbstractIntent;

public class NaviIntent extends AbstractIntent {

	private final String address;

	public NaviIntent(Context context, String address) {
		super(context);
		this.address = address;
	}

	@Override
	public void perform() {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=" + address));
		getContext().startActivity(
				Intent.createChooser(intent, "Naviagte to..."));
	}
}
