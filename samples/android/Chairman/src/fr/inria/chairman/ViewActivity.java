package fr.inria.chairman;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fr.inria.arles.yarta.resources.Agent;
import fr.inria.arles.yarta.resources.Content;
import fr.inria.chairman.resources.Person;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ViewActivity extends BaseActivity {

	public static final String PresentationId = "PresentationId";

	private Content content;
	private ListView list;
	private QuestionListAdapter adapter;
	private boolean addedContent;

	private class AutoUpdateTask extends TimerTask {

		@Override
		public void run() {
			if (author != null && me != null) {
				if (!author.equals(me)) {
					sendUpdateRequest(author);
				}
			}
		}
	}

	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		String contentId = getIntent().getStringExtra(PresentationId);
		content = (Content) getSAM().getResourceByURI(contentId);

		adapter = new QuestionListAdapter(this);

		list = (ListView) findViewById(R.id.questions);
		list.setAdapter(adapter);
		list.setEmptyView(findViewById(R.id.emptyView));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Content c = (Content) adapter.getItem(position);
					me = (Person) getSAM().getMe();
					if (!me.getLikes().contains(c)) {
						me.addLikes(c);
					}
					sendNotify(author);

					refreshCommentsList();
				} catch (Exception ex) {
					Toast.makeText(ViewActivity.this, ex.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshUI();
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new AutoUpdateTask(), 0, 5000);
		}
	}

	protected void onPause() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.onPause();
	}

	Agent author = null;
	Person me = null;

	@Override
	protected void refreshUI() {
		setCtrlText(R.id.title, content.getTitle());
		for (Agent agent : content.getCreator_inverse()) {
			author = agent;
			setCtrlText(R.id.author, agent.getName());
		}
		setCtrlText(R.id.description, content.getContent());

		try {
			me = getSAM().getMe();
		} catch (Exception ex) {
		}

		refreshCommentsList();

		if (addedContent) {
			addedContent = false;
			sendNotify(author);
		}
	}

	private void refreshCommentsList() {
		List<Content> questions = new ArrayList<Content>();
		questions.addAll(content.getHasReply());

		// TODO: sort by time or votes
		try {
			for (int i = 0; i < questions.size() - 1; i++) {
				for (int j = i + 1; j < questions.size(); j++) {
					Content l = questions.get(i);
					Content r = questions.get(j);
					if (l.getTime() > r.getTime()) {
						questions.set(i, r);
						questions.set(j, l);
					}
				}
			}
		} catch (NumberFormatException ex) {
		}

		adapter.setItems(questions);

		list.post(new Runnable() {
			@Override
			public void run() {
				list.setSelection(list.getCount() - 1);
			}
		});
	}

	public void onClickAsk(View view) {
		String text = getCtrlText(R.id.questionText);
		if (text.length() > 0) {
			try {
				Content c = getSAM().createContent();
				getSAM().getMe().addCreator(c);
				c.setTitle(text);
				content.addHasReply(c);
				setCtrlText(R.id.questionText, "");

				hideSoftKeyboard(findViewById(R.id.questionText));

				addedContent = true;
			} catch (Exception ex) {
				Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void hideSoftKeyboard(View v) {
		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		// check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) {
			inputManager.hideSoftInputFromWindow(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
