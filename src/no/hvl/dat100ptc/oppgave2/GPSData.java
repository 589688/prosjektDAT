package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {
		
		antall = 0;
		
		gpspoints = new GPSPoint[n];

	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;
		
		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			inserted= true;
			antall++;
		}
		
		return inserted;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		
		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
	
		return insertGPS(gpspoint);
		
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		// TODO - START

		for (int i = 0; i < gpspoints.length; i++) {
			System.out.println(gpspoints[i].toString());
		}

		// TODO - SLUTT
		
		 System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
