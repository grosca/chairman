package fr.inria.chairman;

import java.util.ArrayList;
import java.util.List;

import fr.inria.arles.yarta.resources.Agent;
import fr.inria.chairman.resources.Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuestionListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Content> callouts = new ArrayList<Content>();
	private String authorFmt;

	private static class ViewHolder {
		TextView author;
		TextView text;
	}

	public QuestionListAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		authorFmt = context.getString(R.string.view_author_fmt);
	}

	@Override
	public int getCount() {
		return callouts.size();
	}

	@Override
	public Object getItem(int position) {
		return callouts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_question, parent,
					false);

			holder = new ViewHolder();
			holder.author = (TextView) convertView.findViewById(R.id.author);
			holder.text = (TextView) convertView.findViewById(R.id.title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Content content = (Content) getItem(position);

		for (Agent agent : content.getCreator_inverse()) {
			String text = String.format(authorFmt, agent.getName(), content
					.getLikes_inverse().size());
			holder.author.setText(text);
		}

		holder.text.setText("\"" + content.getTitle() + "\"");

		return convertView;
	}

	public void setItems(List<fr.inria.arles.yarta.resources.Content> items) {
		this.callouts.clear();
		for (int i = 0; i < items.size(); i++) {
			this.callouts.add((Content) items.get(i));
		}
		notifyDataSetChanged();
	}
}
