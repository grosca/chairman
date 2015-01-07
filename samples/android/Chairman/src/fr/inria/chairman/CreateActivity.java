package fr.inria.chairman;

import java.util.Calendar;
import java.util.Scanner;

import fr.inria.arles.yarta.resources.Content;
import fr.inria.arles.yarta.resources.Group;
import fr.inria.arles.yarta.resources.Person;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends BaseActivity {

	private static final int MENU_ACCEPT = 1;
	private Group group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		initTimeCtrls();
	}

	private void initTimeCtrls() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		int hour = c.get(Calendar.HOUR_OF_DAY);

		EditText date = (EditText) findViewById(R.id.date);
		date.setText(String.format("%02d/%02d/%02d", day, month + 1, year));

		date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onSetDate();
			}
		});

		EditText start = (EditText) findViewById(R.id.start);
		start.setText(String.format("%02d:%02d", hour, 0));
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onSetStart();
			}
		});

		EditText end = (EditText) findViewById(R.id.end);
		end.setText(String.format("%02d:%02d", hour + 1, 0));
		end.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSetEnd();
			}
		});
	}

	private void onSetDate() {
		Scanner scanner = new Scanner(getCtrlText(R.id.date));
		scanner.useDelimiter("/");
		int day = scanner.nextInt();
		int month = scanner.nextInt() - 1;
		int year = scanner.nextInt();
		scanner.close();

		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog dialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						setCtrlText(R.id.date, String.format("%02d/%02d/%04d",
								dayOfMonth, monthOfYear + 1, year));
					}
				}, year, month, day);
		dialog.show();
	}

	private void onSetStart() {
		Scanner scanner = new Scanner(getCtrlText(R.id.start));
		scanner.useDelimiter(":");
		int hour = scanner.nextInt();
		int minute = scanner.nextInt();
		scanner.close();

		// Create a new instance of TimePickerDialog and return it
		TimePickerDialog dialog = new TimePickerDialog(this,
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						setCtrlText(R.id.start,
								String.format("%02d:%02d", hourOfDay, minute));
					}
				}, hour, minute, DateFormat.is24HourFormat(this));
		dialog.show();
	}

	private void onSetEnd() {
		Scanner scanner = new Scanner(getCtrlText(R.id.end));
		scanner.useDelimiter(":");
		int hour = scanner.nextInt();
		int minute = scanner.nextInt();
		scanner.close();

		// Create a new instance of TimePickerDialog and return it
		TimePickerDialog dialog = new TimePickerDialog(this,
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						setCtrlText(R.id.end,
								String.format("%02d:%02d", hourOfDay, minute));
					}
				}, hour, minute, DateFormat.is24HourFormat(this));
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(0, MENU_ACCEPT, 0, R.string.create_save);
		item.setIcon(R.drawable.icon_accept);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ACCEPT:
			onClickSave(null);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onClickSave(View view) {
		String title = getCtrlText(R.id.title);
		String description = getCtrlText(R.id.description);
		String date = getCtrlText(R.id.date);
		String start = getCtrlText(R.id.start);
		String end = getCtrlText(R.id.end);

		if (title.length() == 0 || description.length() == 0) {
			Toast.makeText(this, R.string.create_empty_error,
					Toast.LENGTH_SHORT).show();
			return;
		}

		description = String.format(
				getString(R.string.create_description_format), date, start,
				end, description);

		group = (Group) getSAM().getResourceByURI(Constants.getGroupId());

		try {
			Person me = getSAM().getMe();

			Content c = getSAM().createContent();
			c.setTitle(title);
			c.setContent(description);

			me.addCreator(c);
			group.addHasContent(c);

			Intent intent = new Intent(this, ViewActivity.class);
			intent.putExtra(ViewActivity.PresentationId, c.getUniqueId());
			startActivity(intent);
			finish();
		} catch (Exception ex) {
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}
}
