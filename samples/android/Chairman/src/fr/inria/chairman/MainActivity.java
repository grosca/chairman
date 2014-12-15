package fr.inria.chairman;

import fr.inria.arles.yarta.resources.Group;
import fr.inria.arles.yarta.resources.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

	private Group group;
	private Person me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		app.initMSE(this);
	}

	@Override
	protected void onDestroy() {
		app.uninitMSE();
		super.onDestroy();
	}

	@Override
	protected void refreshUI() {
		findViewById(R.id.actions).setVisibility(View.VISIBLE);

		if (getSAM() != null) {
			try {
				if (group == null || me == null) {
					group = (Group) getSAM().getResourceByURI(
							Constants.getGroupId());
					me = getSAM().getMe();

					if (me != null) {
						me.addIsMemberOf(group);
					}
				}
			} catch (Exception ex) {
				// TODO: nothing can be done;
			}
		}
	}

	public void onClickSpeaker(View view) {
		startActivity(new Intent(this, CreateActivity.class));
	}

	public void onClickParticipant(View view) {
		startActivity(new Intent(this, BrowseActivity.class));
	}
}
