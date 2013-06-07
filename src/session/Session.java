package session;
//Author:Victor Regalado
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;

import mdl.Db;
import mdl.Wrkt;

//Author: Victor R

//contain the data of a workout session 
public class Session {
	private Wrkt wrk;
	private SessionCntr cntr=new SessionCntr();
	private boolean onSes=false;
	private int mode;
	private Context ct;
	
	public static int LIFTING_MODE=1;
	public static int RUNNING_MODE=0;
	
	public Session(Context context){
		wrk=new Wrkt();
		ct=context;
	}
	
    public Session(Context applicationContext, int weight) {
		this(applicationContext);
		setWeight(weight);
	}

	//Transition functions{
	//start the session
	public void start(int mode){
		wrk.setDate(DateFormat.format("MM-dd-yyyy hh:mm:ss",new Date()).toString());
		this.setMode(mode);
		onSes=true;
	}
	//stop taking data
	public void end(){
		onSes=false;
		cntr.setWrk(wrk);
	}
	//clear everything in workout.
	public void clear(){
		cntr.clear();
		wrk=new Wrkt();
	}
   //}
	
  //functions on the Operational_State{
	
	//Input data in this state
	public void Data(float[] values, long timestamp) {
		cntr.update(values, timestamp,this.getMode());
	}
	//Operational mode functions
	public int getMode() {
		return mode;
	}
	public void setMode(int mode){
		this.mode=mode;
	}
	
   //Running mode
	//get current speed
	public String speed(){
		return String.format("%.02f",cntr.getCurrentSpeed());
	}
	public String time(){
		return String.format("%.02f",cntr.getTime());
	}
	//get current average speed
	public String avgSpeed(){
		return String.format("%.02f",cntr.getAvgSpeed());
	}
   
   //Lifting mode	
	//get current number of lifts
	public int numOfLifts() { 
		return cntr.getNumOfLifts();
	}
	
  //}

  //Functions on the End_State{ 
	//save workout to db
	public void save(){
		cntr.save(wrk,ct);
	}
	//get number of calories burnt
	public String NumOfCal(){
		return String.format("%.02f",cntr.cals());
	}
	public String getTotalDistance(){
		return String.format("%.02f",cntr.getDist());
	}
  //}	
	
  //Functions in the clear state{
	public Wrkt[] searchBy(String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		Db db=new Db(ct);
		Wrkt[] out=null;
		try {
			Db data=db.open();
			out=data.searchBy(selection, selectionArgs, groupBy, having, orderBy);
			data.close();
		}
		catch(android.database.SQLException e){
			Log.d("ses_searchby_sql_err", e.getMessage());
		}
		return out;
	}
	public int getWeight() {
		return cntr.getWeight();
	}

	public void setWeight(int weight) {
		cntr.setWeight(weight);
	}

  //}
	//Indicates if the user is either running or lifting
	public boolean onOperation(){
		return onSes;
	}
	
}
