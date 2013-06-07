package mdl;

import android.content.Context;
import android.util.Log;

//Author:Victor Regalado
//this class abstract a workout session. Entity
public class Wrkt {

	private String date; // date when the workout was performed,including time
							// in this format:
	private float cal; // total number of calories burned during the workout
	private float run_tm; // Running time in this workout
	private int id; // id in the db
	private int numOfLifts;
	private float dist; // distance traveled while it was running
	private float avg_speed = 0;

	public static int FIELDS = 7; // fields including ID

	public Wrkt() {

	}

	public Wrkt(int id, String date, float run_tm,float dist, 
			float cal,float avg_speed, int numOfLifts) {
		this.setId(id);
		this.setDate(date);
		this.setCal(cal);
		this.setRun_tm(run_tm);
		this.setNumOfLifts(numOfLifts);
		this.setDist(dist);
		this.setAvg_speed(avg_speed);
	}

	public Wrkt( String date, float run_tm,float dist, 
			float cal,float avg_speed, int numOfLifts) {
		this(0, date, run_tm,dist,cal, avg_speed, numOfLifts);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getCal() {
		return cal;
	}

	public void setCal(float cal) {
		this.cal = cal;
	}

	public float getRun_tm() {
		return run_tm;
	}

	public void setRun_tm(float run_tm) {
		this.run_tm = run_tm;
	}

	public int getNumOfLifts() {
		return numOfLifts;
	}

	public void setNumOfLifts(int numOfLifts) {
		this.numOfLifts = numOfLifts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getDist() {
		return dist;
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public void st(int index, Object val) {
		switch (index) {
		case 0:
			this.setId((Integer) val);
			break;
		case 1:
			this.setDate((String) val);
			break;
		case 2:
			this.setRun_tm((Float) val);
			break;
		case 3:
			this.setDist((Float) val);
			break;
		case 4:
			this.setCal((Float) val);
			break;
		case 5:
			this.setAvg_speed((Float) val);
			break;
		default:
			this.setNumOfLifts((Integer) val);
			break;
		}
	}

	public Object gt(int index) {
		switch (index) {
		case 0:
			return this.getId();
		case 1:
			return this.getDate();
		case 2:
			return this.getRun_tm();
		case 3:
			return this.getDist();
		case 4:
			return this.getCal();
		case 5:
			return this.getAvg_speed();
		default:
			return this.getNumOfLifts();
		}
	}

	public void saveMe(Context ct) {
		Db db = new Db(ct);
		try {
			Db data = db.open();
			data.insertWrk(this);
			data.close();
		} catch (android.database.SQLException e) {
			Log.d("wrk_save_sql_err", e.getMessage());
		}
	}

	public float getAvg_speed() {
		return avg_speed;
	}

	public void setAvg_speed(float avg_speed) {
		this.avg_speed = avg_speed;
	}

	public String toString() {
		String sp = String.format("%.02f", this.avg_speed);
		String cl = String.format("%.02f", this.cal);
		String dst = String.format("%.02f", this.dist);
		String nl = String.format("%d", this.numOfLifts);
		String rt=String.format("%.02f", this.run_tm);
		String out = date + "  Run Time: " + rt + "\n"
				+ "Calories:" + cl + " Kcal" + "     Avg Speed: " + sp
				+ " m/s\nDistance: " + dst + " m        # of Lifts: "
				+ nl;
		return out;
	}
}
