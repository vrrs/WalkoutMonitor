package wlkt.def;
/* @author Victor Regalado */

import sensorDat.SensorCntr;
import session.Session;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class Op_act extends SensorCntr {
	 
	@Override
     public void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.op);
             
             setup();
             mSensorManager.registerListener(this, mAccelerometer,
                             SensorManager.SENSOR_DELAY_NORMAL);
           
            final TextView rnt=(TextView)findViewById(R.id.run_tm);
      		final TextView avgsp=(TextView)findViewById(R.id.avg_sp);
      		final TextView cursp=(TextView)findViewById(R.id.current_sp);
      		final TextView lfn=(TextView)findViewById(R.id.numlift);
      		final TextView dists=(TextView)findViewById(R.id.dist);
      		final TextView cals=(TextView)findViewById(R.id.calr);
      		
      		rnt.setText("0"); 
			avgsp.setText("0 m/s"); 
			cursp.setText("0 m/s"); 
			lfn.setText("0");
			dists.setText("0 m");
			cals.setText("0 kcal");
      		
      		final RadioButton rn=(RadioButton) findViewById(R.id.modeR);
      		final RadioButton lf=(RadioButton) findViewById(R.id.modeL);
      		
      		rn.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(isChecked){
						session.setMode(Session.RUNNING_MODE);
					}
					
				}
      		});
      		
      		lf.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(isChecked){
						session.setMode(Session.LIFTING_MODE);
					}
					
				}
      		});
             
            Button restart = (Button) findViewById(R.id.restart);
     		restart.setOnClickListener(new OnClickListener(){
     				@Override
     				public void onClick(View v) {
     					session.start(session.getMode());
     				}
     	        });
     		
     		Button  stop= (Button) findViewById(R.id.stop);
     		stop.setOnClickListener(new OnClickListener(){
     				@Override
     				public void onClick(View v) {
     					session.end();
     					cals.setText(session.NumOfCal()+" kcal");
     					dists.setText(session.getTotalDistance()+" m");
     				}
     	        });
     		
     		stop.setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View arg0) {
					session.end();
					session.clear();
					rnt.setText("0"); 
					avgsp.setText("0 m/s"); 
					cursp.setText("0 m/s"); 
					lfn.setText("0");
					dists.setText("0 m");
					cals.setText("0 kcal");
					return true;
				}
     			
     		});
     		
     		Button save = (Button) findViewById(R.id.save);
     		save.setOnClickListener(new OnClickListener(){
     				@Override
     				public void onClick(View v) {
     					session.save();
     					session.clear();
     					startActivity(new Intent(Op_act.this,main_act.class));
     				}
     	        });
     		Button cancel = (Button) findViewById(R.id.cancel);
     		cancel.setOnClickListener(new OnClickListener(){
     				@Override
     				public void onClick(View v) {
     					session.clear();
     					startActivity(new Intent(Op_act.this,main_act.class));
     				}
     	        });
             
             //the following Runnables are optional, they are used to modify the gui when the sensors fire events
             setSpeedGui(new Runnable() {
                     public void run() {
                             cursp.setText(session.speed()+" m/s");
                     }
             });

             setLiftingGui(new Runnable() {
                     public void run() {
                             int nLifts = session.numOfLifts();
                             lfn.setText(Integer.toString(nLifts));
                     }
             });

             setTimeGui(new Runnable() {
                     public void run() {
                             String s = session.time();
                            rnt.setText(s+" s");
                     }
             });
             
             setAvgSpeedGui(new Runnable(){
            	public void run(){
            		avgsp.setText(session.avgSpeed()+" m/s");
            	}
             });

     }
}
