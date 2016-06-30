/**
 * Copyright (C) 2016-2017 Anand M Joseph.
 */
package conway.anand.myconway.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import conway.anand.myconway.R;
import conway.anand.myconway.app.AppConstants;
import conway.anand.myconway.coustomwidgets.AndroidLifeView;
import conway.anand.myconway.rules.RuleEditorActivity;

/**
 * This activity handels the main game activity.
 * 
 * Created by Anand M Joseph on 28/06/16.
 */
public class ConwayLifeActivity extends Activity {

	/**
	 * Variable to identify the View.
	 */
	private AndroidLifeView agolView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get device width and height BEFORE initializing anything else
		final Display display = getWindowManager().getDefaultDisplay();
		AppConstants.setViewportWidth(display.getWidth());
		AppConstants.setViewportHeight(display.getHeight());

		setContentView(R.layout.main);

		agolView = (AndroidLifeView) findViewById(R.id.androidGameOfLifeView1);
		agolView = new AndroidLifeView(this);
		agolView.setBackgroundColor(Color.BLACK);
		setContentView(agolView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item1:
				agolView.lock();
				agolView.runLoop();
				break;

			case R.id.item2:
				agolView.unlock();
				break;

			case R.id.item3:
				agolView.unlock();
				final Intent intent = new Intent(getApplicationContext(), RuleEditorActivity.class);

				startActivity(intent);
				break;
		}
		return true;
	}
}
