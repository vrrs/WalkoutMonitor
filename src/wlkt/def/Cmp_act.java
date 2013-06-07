package wlkt.def;
/* @author Victor Regalado */

import mdl.Wrkt;
import session.Session;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Cmp_act extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.compare);
            
            ListView lv=(ListView)findViewById(R.id.listView1);
            lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, getWorkouts()));
            lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                	
                }
              });
            
            Button  cancel= (Button) findViewById(R.id.cancelCmp);
     		cancel.setOnClickListener(new OnClickListener(){
     				@Override
     				public void onClick(View v) {
     					startActivity(new Intent(Cmp_act.this,main_act.class));
     				}
     	        });
	}
	
	private String[] getWorkouts(){
        Session session=new Session(this.getApplicationContext(),0);
        Wrkt[] wr=session.searchBy(null, null, null, null, null);
        String[] out=new String[wr.length];
        for(int i=0;i<wr.length;i++){
        	out[i]=wr[i].toString();
        }
		return out;
	}
}
