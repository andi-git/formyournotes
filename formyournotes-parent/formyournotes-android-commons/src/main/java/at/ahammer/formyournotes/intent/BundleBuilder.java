package at.ahammer.formyournotes.intent;

import android.app.Fragment;
import android.os.Bundle;

public abstract class BundleBuilder {

	public abstract void fillBundle(Bundle bundle);

	public Bundle build() {
		Bundle bundle = new Bundle();
		fillBundle(bundle);
		return bundle;
	}

	public <T extends Fragment> T fillFragment(T fragment) {
		fragment.setArguments(build());
		return fragment;
	}
}
