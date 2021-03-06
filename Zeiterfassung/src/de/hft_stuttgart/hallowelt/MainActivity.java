package de.hft_stuttgart.hallowelt;

import java.util.concurrent.ExecutionException;

import de.mandi.hallowelt.R;
import android.R.color;
import android.R.integer;
import android.R.string;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private static Integer idOfWork;
	private static String project;
	private static String activity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		final TextView txvProject = (TextView) findViewById(R.id.txvProject);
		final TextView txvActivity = (TextView) findViewById(R.id.txvActivity);
		final Spinner spnProjects = (Spinner) findViewById(R.id.spinner);
		final Spinner spnActivities = (Spinner) findViewById(R.id.spinner1);
		
		final Button btnStart = (Button) findViewById(R.id.btnStart);
		final Button btnStop = (Button) findViewById(R.id.btnStop);
		final Button btnPause = (Button) findViewById(R.id.btnStartPause);
		final Button btnStopPause = (Button) findViewById(R.id.btnStopPause);
		btnStart.setEnabled(false);
//		btnStop.setEnabled(false);
		
		SoapArrayGetter aGetter;

		try {
			
			aGetter = new SoapArrayGetter();
			
			String[] sprojects = aGetter.execute("loadprojects").get();
			
			CustomArrayAdapter<String> adpProjects = new CustomArrayAdapter<String>(
					getApplicationContext(), sprojects);

			spnProjects.setAdapter(adpProjects);

			spnProjects.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					project = (String) spnProjects.getAdapter().getItem(arg2);
					checkButtons(btnStart);
					txvProject.setText("");
					txvProject.setText("Projectauswahl: "+project);
					Log.i("Project", project);

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			
			aGetter = new SoapArrayGetter();
			String[] activities = aGetter.execute("loadactivities").get(); 
			CustomArrayAdapter<String> adpActivities = new CustomArrayAdapter<String>(getApplicationContext(),activities);
			spnActivities.setAdapter(adpActivities);
			
			spnActivities.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					activity = (String) spnActivities.getAdapter().getItem(arg2);
					checkButtons(btnStart);
					txvActivity.setText("");
					txvActivity.setText("Aktivitšt: "+activity);
					Log.i("activity", activity);
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			btnStart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					WorkStarter starter = new WorkStarter();
					try {
						idOfWork = starter.execute(project, activity).get();
						Log.i("ID", idOfWork.toString());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			btnStop.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					WorkStoper stoper = new WorkStoper();
					try {
						Integer duration = stoper.execute(idOfWork).get();
						Log.i("Dauer", duration.toString());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			btnPause.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					WorkPausStarter pStarter = new WorkPausStarter();
					pStarter.execute(idOfWork);
					
				}
			});
			
			btnStopPause.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					WorkPausStoper pStoper = new WorkPausStoper();
					try {
						Integer pauseDuration = pStoper.execute(idOfWork).get();
						Log.i("PausenDauer", pauseDuration.toString());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
			});

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	private void checkButtons(Button b){
		if (project != null && activity != null) {
			b.setEnabled(true);
		}
		
	}

}
