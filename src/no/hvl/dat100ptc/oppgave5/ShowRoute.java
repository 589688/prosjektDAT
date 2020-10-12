package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);
		
		showStatistics();
		
		showRouteMap(MARGIN + MAPYSIZE);
		
		
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		
		return ystep;
	}

	public void showRouteMap(int ybase) {
		
		double xforst = gpspoints[0].getLongitude();
		double yminst = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		setColor(80,136,180);
		
		for (int i=0;i<gpspoints.length;i++) {
			
			
			int x1 = (int)((gpspoints[i].getLongitude() - xforst) * xstep() + MARGIN);
			
			//double ydiff = gpspoints[i].getLatitude() - yminst;
			//int ydiffpix = (int)((gpspoints[i].getLatitude() - yminst) * ystep());
			int y1 = ybase - MARGIN - ((int)((gpspoints[i].getLatitude() - yminst) * ystep()));
			

			fillCircle(x1,y1,2);
			
				if (i > 0) {
					
				int x0 = (int)((gpspoints[i-1].getLongitude() - xforst) * xstep() + MARGIN);	
				int y0 = ybase - MARGIN - ((int)((gpspoints[i-1].getLatitude() - yminst) * ystep()));
					drawLine(x1,y1,x0,y0);
			}
		}
		
		//tegne en prikk som beveger seg mellom punktene, med hastighet som tilsvarer den reelle hastigheten
		//bruke speed:(finne ut hvordan speed relaterer til hastighet som km/t eller m/s
		//bruke pause:(finne tid i sekunder mellom hvert gpspunkt og bruke pause mellom hver move
		setColor (250,50,0);
		int radius = 3;
		int dot = fillCircle((int)((gpspoints[0].getLongitude() - xforst) * xstep() + MARGIN), ybase - MARGIN - ((int)((gpspoints[0].getLatitude() - yminst) * ystep())),radius);
		
		for (int i=1; i<gpspoints.length; i++) {
			
			int x = (int)((gpspoints[i].getLongitude() - xforst) * xstep() + MARGIN);
			int y = ybase - MARGIN - ((int)((gpspoints[i].getLatitude() - yminst) * ystep()));
			
			moveCircle(dot, x, y);
			pause(200);
		}
		//moveCircle = (dot, neste x, neste y, radius);
		
	}

	public void showStatistics() {

		int TEXT = 8;
		int i = 1;
		String SEP = ":";
		int WEIGHT = 80;
		setColor(0,0,0);
		setFont("Courier",12);
		
		String time = "Total Time     " + SEP + GPSUtils.formatTime(gpscomputer.totalTime()) + "  ";
		drawString(time,TEXT,i*TEXT);
		i+=2;
		String distance = "Total Distance " + SEP + GPSUtils.formatDouble(gpscomputer.totalDistance() / 1000) + "km ";
		drawString(distance,TEXT,i*TEXT);
		i+=2;
		String elevation = "Total Elevation" + SEP + GPSUtils.formatDouble(gpscomputer.totalElevation()) + "m ";
		drawString(elevation,TEXT,i*TEXT);
		i+=2;
		String mspeed = "Max speed      " + SEP + GPSUtils.formatDouble(gpscomputer.maxSpeed()) + "km/t ";
		drawString(mspeed,TEXT,i*TEXT);
		i+=2;
		String aspeed = "Average speed  " + SEP + GPSUtils.formatDouble(gpscomputer.averageSpeed()) + "km/t ";
		drawString(aspeed,TEXT,i*TEXT);
		i+=2;
		String energy = "Energy         " + SEP + GPSUtils.formatDouble(gpscomputer.totalKcal(WEIGHT)) + "kcal ";
		drawString(energy,TEXT,i*TEXT);
		i+=2;
		String climb = "Max climb      " + SEP + GPSUtils.formatDouble(gpscomputer.maxClimb()) + "% ";
		drawString(climb,TEXT,i*TEXT);
	}

}
