package session;

//Author:Victor Regalado
import mdl.Wrkt;
import android.content.Context;

//set up the session and process infomartion of Session
public class SessionCntr {

	private int numOfLifts = 0;

	private float currentSpeed = 0.0f;

	private float avgSpeed = 0.0f;

	private float[] Vk;
	private float tk1=0.0f;

	private float dist =0.0f;

	private int weight = 0;

	private final static float NANO_CONVERSION_FACTOR = 1000000000.0f;
	private final static float g = 10.0f;

	private float t0 = 0.0f;

	public SessionCntr() {
		clear();
	}

	private float currentTime;

	private float[] Ak1;

	private int k=0;

	private float s=0.0f;

	// X+=Y where X,Y are 3d vectors
	private void vctrAdd(float[] src, float[] out) {
		for (int k = 0; k < out.length; k++)
			out[k] += src[k];
	}

	// scalar multiplication
	private float[] sclrMult(float[] out, float s) {
		for (int k = 0; k < out.length; k++)
			out[k] *= s;
		return out;
	}

	public void update(float Ak[], long ttk, int mode) {
		float tk=(float)ttk;
		if (setInitTime(tk)) {
			currentTime = tk / NANO_CONVERSION_FACTOR - t0;
			float h = tk / NANO_CONVERSION_FACTOR - tk1;
			tk1 = tk / NANO_CONVERSION_FACTOR;

			if (modulus(Ak) - g > 0.0f) {
				if (mode == 0) {
					k++;Vk=new float[3];
					vctrAdd(sclrMult(Ak, h), Vk);
					float v=modulus(Vk)-2.0f;//-g*h;
					currentSpeed = v>0?v:0;
					s+=currentSpeed;
					avgSpeed = s/(k+1);
					dist=avgSpeed*currentTime;
				} else {
					if (Ak[0] - Ak1[0] <=0.0f & Ak[1] - Ak1[1] <=0.0f
							& Ak[2] - Ak1[2] <=0.0f) {
						numOfLifts++;
					}
					Ak1 = Ak;
				}
			}
		}
	}

	private float modulus(float[] v) {
		return (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
	}

	public int getNumOfLifts() {
		return numOfLifts;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public float getAvgSpeed() {
		return avgSpeed;
	}

	public void clear() {
		Vk = new float[3];
		Ak1 = new float[3];
		numOfLifts = 0;
		k=0;
		s=0;
		currentSpeed = 0.0f;
		avgSpeed = 0.0f;
		tk1 = 0.0f;
		for (int i = 0; i < 3; i++) {
			Ak1[i] = 0.0f;
			Vk[i] = 0.0f;
		}
		dist = 0.0f;
	}

	public void save(Wrkt w, Context ct) {
		w.saveMe(ct);
	}

	public float cals() {
		int weight = getWeight();
		return 0.00125f * (getTime()) * ((float) weight) * avgSpeed;
	}

	public void setWrk(Wrkt wrk) {
		wrk.setCal(this.cals());
		wrk.setDist(dist);
		wrk.setRun_tm(getTime());
		wrk.setNumOfLifts(getNumOfLifts());
		wrk.setAvg_speed(getAvgSpeed());
	}

	public float getDist() {
		return dist;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public float getTime() {
		return currentTime;
	}

	private boolean setInitTime(float time) {
		if (t0 == 0.0f) {
			t0 = time / NANO_CONVERSION_FACTOR;
			tk1 = t0;
			return false;
		}
		return true;
	}
}
