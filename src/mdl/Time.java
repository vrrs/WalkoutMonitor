package mdl;
//Author:Victor Regalado
public class Time {
	private int hr=0;
	private int min=0;
	private int sec=0;
	private float tim;
	
	//time in milisecond
	public Time(float time){
		this.tim=time;
		convert(time);
	}
	
	public float getTime(){
		return this.tim;
	}
	
	public Time(){
		
	}
	
	//operation methods
	
	//convert from miliseconds to hr,min,sec
	private void convert(float time){
		sec = (int) (time / 1000) % 60 ;
		min= (int) ((time / (1000*60)) % 60);
		hr= (int) ((time / (1000*60*60)) % 24);
	}
	
	//getters and setters
	public int getHr() {
		return hr;
	}
	
	public void setHr(int hr) {
		this.hr = hr;
	}
	
	public int getMin() {
		return min;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int getSec() {
		return sec;
	}
	
	public void setSec(int sec) {
		this.sec = sec;
	}
	
	//format time output
	public String toString(){
		return Integer.toString(hr)+":"+Integer.toString(min)+":"+Integer.toString(sec);
	}
	
}
