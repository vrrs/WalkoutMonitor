package mdl;

//Author: Victor Regalado

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//db adapter class
public class Db {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "wrkt_db";

	// table definition {
	private static final String WORKOUT_TABLE = "workout";
	// table fields{
	public static final String ID = "id";
	public static final String DATE = "date";
	public static final String RUNNING_TIME = "runtm";
	public static final String DISTANCE = "dist";
	public static final String CALORIES = "cal";
	public static final String AVERAGE_SPEED = "avgsp";
	public static final String NUM_OF_LIFTS = "lifts";
	public static final String[] FIELDS={ID,DATE,RUNNING_TIME,DISTANCE,CALORIES,AVERAGE_SPEED,NUM_OF_LIFTS};
	// }
	// }

	private static final String DATABASE_CREATE = "create table "
			+ WORKOUT_TABLE + " (" + ID
			+ " integer primary key autoincrement, " + DATE
			+ " text not null, " + RUNNING_TIME + " float, " + DISTANCE
			+ " float, " + CALORIES + " float, "
			+ AVERAGE_SPEED + " float, " + NUM_OF_LIFTS + " integer);";

	private final Context mContext;
	private DbHlp mDbHelper; 
	private SQLiteDatabase mDb;
	
	private static class DbHlp extends SQLiteOpenHelper{

		public DbHlp(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
		
	}
	
	public Db(Context ct) {
		this.mContext=ct;
	}

	public Db open() throws android.database.SQLException {
		mDbHelper= new DbHlp(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	public long insertWrk(Wrkt w){
		ContentValues initVals=new ContentValues();
		initVals.put(DATE, w.getDate());
		for(int i=2;i<FIELDS.length-1;i++){
			initVals.put(FIELDS[i],(Float)w.gt(i));
		}
		initVals.put(NUM_OF_LIFTS, w.getNumOfLifts());
		return mDb.insert(WORKOUT_TABLE, null, initVals);
	}
	
	public Wrkt[] searchBy(String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		Cursor dat=fetchBy(selection,selectionArgs,groupBy,having,orderBy);
		int size=dat.getCount();
		Wrkt[] out=new Wrkt[size];
		for(int i=0;i<size;i++){
			dat.moveToNext();
			out[i]=new Wrkt(dat.getInt(0),dat.getString(1),dat.getFloat(2),dat.getFloat(3),dat.getFloat(4),dat.getFloat(5),dat.getInt(6));
		}
		dat.close();
		return out;
	}
	
	private Cursor fetchBy(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {  
		return mDb.query(WORKOUT_TABLE, FIELDS,selection,selectionArgs,groupBy,having,orderBy);
	}
}
