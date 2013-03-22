package at.ahammer.formyournotes.views;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class AbstractTextWatcher implements TextWatcher {

	@Override
	public abstract void afterTextChanged(Editable editable);

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// nothing
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// nothing
	}

}
