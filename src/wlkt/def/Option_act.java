package wlkt.def;
/* @author Victor Regalado */
import sensorDat.SensorCntr;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Option_act extends SensorCntr {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        setup();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        
        final EditText ed=(EditText)findViewById(R.id.editText1);
        Button save = (Button) findViewById(R.id.saveOp);
 		ed.setText(Integer.toString(session.getWeight()));
        save.setOnClickListener(new OnClickListener(){
 				@Override
 				public void onClick(View v) {
 					setWeight(Integer.parseInt(ed.getText().toString()));
 					startActivity(new Intent(Option_act.this,main_act.class));
 				}
 	        });
        
 		Button cancel = (Button) findViewById(R.id.cancelOp);
 		cancel.setOnClickListener(new OnClickListener(){
 				@Override
 				public void onClick(View v) {
 					ed.setText("0");
 					startActivity(new Intent(Option_act.this,main_act.class));
 				}
 	        });
	}
}
