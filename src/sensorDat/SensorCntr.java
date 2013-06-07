package sensorDat;

//Author:Victor Regalado

import session.Session;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class SensorCntr extends Activity implements
		SensorEventListener {

	// sensor fields
	protected SensorManager mSensorManager;
	protected Sensor mAccelerometer;
	protected Session session;

	private Runnable speed_gui;
	private Runnable lifting_gui;
	private Runnable tmr_tck;
	private Runnable avgsp;

	private static String WEIGHT = "Weight";
	private static String PREF_NAME = "mWeight";
	
	protected void setup(){
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		 mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		 session = new Session(this.getApplicationContext(),
					weight());
		 
	}

	protected void setSpeedGui(Runnable sg) {
		speed_gui = sg;
	}

	protected void setWeight(int w) {
		SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		Editor mEditor = prefs.edit();
		mEditor.putInt(WEIGHT, w);
		mEditor.commit();
		session.setWeight(w);
	}

	protected int weight() {
		SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		return prefs.getInt(WEIGHT, 0);
	}

	protected void setLiftingGui(Runnable sg) {
		lifting_gui = sg;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (session.onOperation()) {
			session.Data(event.values, event.timestamp);
			this.runOnUiThread(tmr_tck);
			if (session.getMode() == Session.LIFTING_MODE) {
				this.runOnUiThread(lifting_gui);
			} 
			else {
				this.runOnUiThread(speed_gui);
				this.runOnUiThread(avgsp);
			}

		}
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	
	protected void setTimeGui(Runnable tmr_tck) {
		this.tmr_tck = tmr_tck;
	}
	
	protected void setAvgSpeedGui(Runnable r){
		this.avgsp=r;
	}
	
}
