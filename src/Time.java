import java.math.BigDecimal;

import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerModel;

public class Time {

	private int sec;

	public Time() {
		sec = 0;
	}

	public Time(int sec) {
		this.sec = sec;
	}

	public Time(Time t) {
		this.sec = t.sec;
	}

	public void add(int sec) {
		this.sec += sec;
	}

	public void add(Time other) {
		this.sec += other.sec;
	}

	public void subtract(Time other) {
		if (isAfter(other)) {
			this.sec -= other.sec;
		} else {
			this.sec = 0;
		}
	}

	public boolean isAfter(Time other) {
		return this.sec > other.sec;
	}

	public int toSeconds() {
		return this.sec;
	}

	public long toMilliseconds() {
		return this.sec * 1000;
	}

	public float toMinutes() {
		BigDecimal bd = new BigDecimal(Float.toString(this.sec / 60f));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public void set(Time other) {
		this.sec = other.sec;
	}

	public void setSeconds(int sec) {
		this.sec = sec;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Time)) {
			return false;
		}
		
		Time t = (Time) o;
		return (this.sec == t.sec);
	}

	@Override
	public int hashCode() {
		return this.sec * 31;
	}

	@Override
	public String toString() {
	    return Time.timeFormat(this.toSeconds());
  }

  public static String timeFormat(int sec) { 
		  if (sec < 3600)
			    return String.format("%02d:%02d", sec/60, sec%60); 
		  return String.format("%d:%02d:%02d", sec/3600, (sec%3600)/60, sec%60);
  }

	public static Time parseTime(String str) throws NumberFormatException {
		int i = -1;
		try {
			i = Integer.parseInt(str);
		} catch (NumberFormatException e) {}
		if (i >= 0) return new Time(i * 60);

		double d = -1.0;
		try {
			d = Double.parseDouble(str.replace(',', '.'));
		} catch (NumberFormatException e) {}
		if (d >= 0) return new Time((int)(d * 60.0));

		String[] strs = str.split(":");
		int l = strs.length;
		int h, m, s;
		if (l == 3) {
			h = strs[0].isEmpty() ? 0 : Integer.parseInt(strs[0]);
			m = strs[1].isEmpty() ? 0 : Integer.parseInt(strs[1]);
			s = strs[2].isEmpty() ? 0 : Integer.parseInt(strs[2]);
		}
		else if (l == 2) {
			h = 0;
			m = strs[0].isEmpty() ? 0 : Integer.parseInt(strs[0]);
			s = strs[1].isEmpty() ? 0 : Integer.parseInt(strs[1]);
		} else {
			throw new NumberFormatException();
		}
		
		if (h >= 0 && m >= 0 && s >= 0) {	
			return new Time(3600 * h + 60 * m + s);
		} else {
			throw new NumberFormatException();
		}
	}
}
