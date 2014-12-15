package fr.inria.chairman;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.inria.arles.yarta.resources.Content;
import fr.inria.arles.yarta.resources.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class BrowseActivity extends BaseActivity implements
		AdapterView.OnItemClickListener {

	private PresentationListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		adapter = new PresentationListAdapter(this);

		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		list.setEmptyView(findViewById(R.id.emptyView));

		refreshUI();
	}

	@Override
	protected void refreshUI() {
		// TODO: async task

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		String today = String.format("%02d/%02d/%02d", day, month, year);

		Group group = (Group) getSAM().getResourceByURI(Constants.getGroupId());

		if (group == null) {
			return;
		}

		List<Content> presentations = new ArrayList<Content>();

		// only today's presentations
		for (Content content : group.getHasContent()) {
			String description = content.getContent();
			if (description == null) {
				continue;
			}

			int comma = description.indexOf(',');
			if (comma == -1) {
				continue;
			}
			String date = description.substring(comma - 10, comma);

			if (today.equals(date)) {
				presentations.add(content);
			}
		}

		adapter.setItems(presentations);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Content content = (Content) adapter.getItem(position);
		Intent intent = new Intent(this, ViewActivity.class);
		intent.putExtra(ViewActivity.PresentationId, content.getUniqueId());
		startActivity(intent);
		finish();
	}
}
