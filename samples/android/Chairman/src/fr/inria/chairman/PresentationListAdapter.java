package fr.inria.chairman;

import java.util.ArrayList;
import java.util.List;

import fr.inria.arles.yarta.resources.Agent;
import fr.inria.arles.yarta.resources.Content;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PresentationListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Content> callouts = new ArrayList<Content>();

	private static class ViewHolder {
		TextView author;
		TextView text;
		TextView description;
	}

	public PresentationListAdapter(Context context) {
		inflater = LayoutInflater.from(context);
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
			convertView = inflater.inflate(R.layout.item_presentation, parent,
					false);

			holder = new ViewHolder();
			holder.author = (TextView) convertView.findViewById(R.id.author);
			holder.text = (TextView) convertView.findViewById(R.id.title);
			holder.description = (TextView) convertView
					.findViewById(R.id.description);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Content content = (Content) getItem(position);

		for (Agent agent : content.getCreator_inverse()) {
			holder.author.setText(agent.getName());
		}

		holder.text.setText(content.getTitle());

		// TODO: etl
		String description = content.getContent().trim();
		holder.description.setText(description);

		return convertView;
	}

	public void setItems(List<Content> items) {
		this.callouts.clear();
		this.callouts.addAll(items);
		notifyDataSetChanged();
	}

}
