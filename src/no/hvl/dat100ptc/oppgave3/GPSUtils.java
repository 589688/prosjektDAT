package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

        min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] lats = new double[gpspoints.length];
		
		for (int i = 0; i < lats.length; i++) {
			lats[i] = (gpspoints[i]).getLatitude();
		}
		
		return lats;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longs = new double[gpspoints.length];
		
		for (int i = 0; i < longs.length; i++) {
			longs[i] = (gpspoints[i]).getLongitude();
		}
		
		return longs;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = gpspoint1.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		latitude2 = gpspoint2.getLatitude();
		longitude2 = gpspoint2.getLongitude();
		
		
		double o1 = toRadians(latitude1);
		double o2 = toRadians(latitude2);
		double oDiff = toRadians(latitude2 - latitude1);
		double oLam = toRadians(longitude2 - longitude1);
		
		double a = Math.pow((Math.sin(oDiff / 2)),2) + Math.cos(o1) * Math.cos(o2) * Math.pow((Math.sin(oLam / 2)),2);
		double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1 - a));
		
		d = R * c;
		
		return d;
		
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

		secs = gpspoint2.getTime() - gpspoint1.getTime();
		
		speed = distance(gpspoint1, gpspoint2) / secs * 3.6;
		
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int tim = secs / 3600;
		int min = secs % 360 / 60;
		int sek = secs % 360 % 60;
		
		String timS = String.format("%02d", tim);
		String minS = String.format("%02d", min);
		String sekS = String.format("%02d", sek);
		
		timestr = timS + TIMESEP + minS + TIMESEP + sekS;
		
		String nyTime = String.format("%10s", timestr);
		
		return nyTime;
		
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String toDes = String.format("%,.2f", d);
		
		String str = String.format("%10s", toDes);
		
		return str;
		
	}
}
