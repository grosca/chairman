package fr.inria.chairman;

import fr.inria.arles.yarta.middleware.communication.CommunicationManager;
import fr.inria.arles.yarta.middleware.msemanagement.MSEManager;
import fr.inria.arles.yarta.resources.Agent;
import fr.inria.chairman.ChairmanApp.Observer;
import fr.inria.chairman.msemanagement.StorageAccessManagerEx;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BaseActivity extends ActionBarActivity implements Observer {

	protected ChairmanApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ChairmanApp) getApplication();
	}

	@Override
	protected void onResume() {
		super.onResume();
		app.addObserver(this);
	}

	@Override
	protected void onPause() {
		app.removeObserver(this);
		super.onPause();
	}

	protected StorageAccessManagerEx getSAM() {
		return app.getSam();
	}

	protected CommunicationManager getCOMM() {
		return app.getComm();
	}

	protected MSEManager getMSE() {
		return app.getMse();
	}

	protected void sendNotify(Agent agent) {
		if (agent != null) {
			final String uniqueId = agent.getUniqueId();
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (uniqueId.indexOf('_') != -1) {
						getCOMM().sendNotify(
								uniqueId.substring(uniqueId.indexOf('_') + 1));
					}
				}
			}).start();
		}
	}

	protected void sendUpdateRequest(Agent agent) {
		if (agent != null) {
			final String uniqueId = agent.getUniqueId();
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (uniqueId.indexOf('_') != -1) {
						try {
							getCOMM()
									.sendUpdateRequest(
											uniqueId.substring(uniqueId
													.indexOf('_') + 1));
						} catch (Exception ex) {
						}
					}
				}
			}).start();
		}
	}

	@Override
	public void onRefreshUI() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				refreshUI();
			}
		});
	}

	@Override
	public void onLogout() {
		finish();
	}

	protected void refreshUI() {
	}

	protected void setCtrlText(int id, String text) {
		TextView txt = (TextView) findViewById(id);
		txt.setText(text);
	}

	protected String getCtrlText(int id) {
		TextView txt = (TextView) findViewById(id);
		return txt.getText().toString();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
