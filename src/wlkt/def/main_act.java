package wlkt.def;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main_act extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button startW = (Button) findViewById(R.id.startW);
		startW.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					startActivity(new Intent(main_act.this,Op_act.class));
				}
	        });
		
		Button opt = (Button) findViewById(R.id.opt);
		opt.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					startActivity(new Intent(main_act.this,Option_act.class));
				}
	        });
		
		Button cmp = (Button) findViewById(R.id.cmp);
		cmp.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					startActivity(new Intent(main_act.this,Cmp_act.class));
				}
	        });
	}

}