package at.ahammer.formyournotes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import at.ahammer.formyournotes.logging.LogTag;

public class HelloAndroidActivity extends Activity {

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LogTag.FYN.getTag(), "onCreate");
		setContentView(R.layout.main);

		Button button = new Button(this);
		button.setText("a button");
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i(LogTag.FYN.getTag(), "click button");
			}

		});

		Log.i(LogTag.FYN.getTag(), "my message");
		LinearLayout layout = (LinearLayout) findViewById(R.id.buttonlayout);
		layout.addView(button);
		
//		Form form = new Form();
//		form.setId(1);
//		form.setName("formName");
//		Group group = new Group();
//		group.setId(2);
//		group.setName("groupName");
//		form.getGroups().add(group);
		
//		try {
//			openFileInput("");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	}

}
