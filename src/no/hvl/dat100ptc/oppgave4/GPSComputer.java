package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;
		
		for (int i=0; i < gpspoints.length-1; i++) {
			
			distance += GPSUtils.distance(gpspoints[i+1],gpspoints[i]);
		}

		return distance;
	}

	// beregn totale hÃ¸ydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i=0; i < gpspoints.length-1; i++) {
			
			if (gpspoints[i].getElevation() < gpspoints[i+1].getElevation()) {
				
				elevation += gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
			}
		}
		
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int tid = gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
		
		return tid;

	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] snittHast = new double[gpspoints.length-1];
		
		for (int i=0; i < snittHast.length; i++) {
			snittHast[i]= 3.6 * (GPSUtils.distance(gpspoints[i+1], gpspoints[i])) / (gpspoints[i+1].getTime() - gpspoints[i].getTime());
			
		}

		return snittHast;
	}
	
	public double maxSpeed() {
		
		double max = 0;
		
		for (int i=0; i<speeds().length; i++) {
			
			if (speeds()[i] > max) {
				max = speeds()[i];
			}
		}
		
		return max;
	}

	public double averageSpeed() {

		double average = totalDistance() / totalTime() * 3.6; 
		
		return average;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjÃ¸res med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4.0;}
		if (speedmph >10 && speedmph <= 12) {
			met = 6.0;}
		if (speedmph >12 && speedmph <= 14) {
			met = 8.0;}
		if (speedmph >14 && speedmph <= 16) {
			met = 10.0;}
		if (speedmph >16 && speedmph <= 20) {
			met = 12.0;}
		if (speedmph > 20) {
			met = 16.0;}

		kcal = met * weight * secs / 3600;
		
		
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = kcal(weight, totalTime(), averageSpeed());
		
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	private static String SEP = ":";
	public void displayStatistics() {

		System.out.println("==============================================");
		System.out.println("Total Time" + SEP + GPSUtils.formatTime(totalTime()));
		System.out.println("Total Distance" + SEP + GPSUtils.formatDouble(totalDistance() / 1000) + "km");
		System.out.println("Total Elevation" + SEP + GPSUtils.formatDouble(totalElevation()) + "m");
		System.out.println("Max speed" + SEP + GPSUtils.formatDouble(maxSpeed()) + "km/t");
		System.out.println("Average speed" + SEP + GPSUtils.formatDouble(averageSpeed()) + "km/t");
		System.out.println("Energy" + SEP + GPSUtils.formatDouble(totalKcal(WEIGHT)) + "kcal");
		System.out.println("==============================================");

	}
	
	public double[] climbs() {
		
		//stigningsprosent: antall meter oppover per meter bortover
		//en stigningsprosent på 15-30% forventes??
		//deler elevation forskjell fra i til i+1, på distance mellom i og i+1
		//da får vi høydemeter per lengdemeter mellom gpspunktene
		//hva skjer hvis elevation er negativ? får negativt svar, som ikke vil ha noen innvirkning på maxclimbs
		//hvordan få stigningsprosent? den skal beskrive høydeforskjellen i meter per 100 meters forflytning
		// 
		
		double[] climb = new double[gpspoints.length-1];
		
		for (int i=0; i < climb.length; i++) {
			climb[i]= (gpspoints[i +1].getElevation() - gpspoints[i].getElevation()) / (GPSUtils.distance(gpspoints[i+1], gpspoints[i]));
			
		}

		return climb;
		
		
		
	}
	
	public double maxClimb() {
		
		double max = 0;
		
		for (int i=0; i<climbs().length; i++) {
			if (climbs()[i] > max) {
				max = climbs()[i];
			}
			
		}
		
		return max;
	}

}
