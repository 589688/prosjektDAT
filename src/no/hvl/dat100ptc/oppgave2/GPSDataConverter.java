package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr
	private static int TIME_SLUTTINDEX = 13;
	private static int MIN_STARTINDEX = 14;
	private static int MIN_SLUTTINDEX = 16;
	private static int SEK_STARTINDEX = 17;
	private static int SEK_SLUTTINDEX = 19;
	
	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;
		
		hr = Integer.parseInt(timestr.substring(TIME_STARTINDEX, TIME_SLUTTINDEX));
		
		min = Integer.parseInt(timestr.substring(MIN_STARTINDEX, MIN_SLUTTINDEX));
		
		sec = Integer.parseInt(timestr.substring(SEK_STARTINDEX, SEK_SLUTTINDEX));
		
		secs = hr * 60 * 60 + min * 60 + sec;

		return secs;
		// OPPGAVE - SLUTT
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		

		// TODO - START ;
		
		int nyTime = toSeconds(timeStr);
		double nyLatitude = Double.parseDouble(latitudeStr);
		double nyLongitude = Double.parseDouble(longitudeStr);
		double nyElevation = Double.parseDouble(elevationStr);
		
		GPSPoint gpspoint = new GPSPoint(nyTime, nyLatitude, nyLongitude, nyElevation);
			
		
		return gpspoint;
		
		

		// OPPGAVE - SLUTT ;
	    
	}
	
}
