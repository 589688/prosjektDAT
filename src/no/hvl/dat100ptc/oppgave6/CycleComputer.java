package no.hvl.dat100ptc.oppgave6;

import javax.swing.JOptionPane;

import easygraphics.*;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class CycleComputer extends EasyGraphics {

	private static int SPACE = 10;
	private static int MARGIN = 20;
	
	// FIXME: take into account number of measurements / gps points
	private static int ROUTEMAPXSIZE = 800; 
	private static int ROUTEMAPYSIZE = 400;
	private static int HEIGHTSIZE = 200;
	private static int TEXTWIDTH = 200;

	private GPSComputer gpscomp;
	private GPSPoint[] gpspoints;
	
	private int N = 0;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public CycleComputer() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");

		gpscomp = new GPSComputer(filename);
		gpspoints = gpscomp.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		N = gpspoints.length; // number of gps points

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = xstep();
		ystep = ystep();

		makeWindow("Cycle Computer", 
				2 * MARGIN + ROUTEMAPXSIZE,
				2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE);

		bikeRoute();

	}

	
	public void bikeRoute() {

		//tegne høydeprofil og rute med prikk, og vise hastighet og tid med samme for løkke
		//koden må tilpasses de nye målene for grafikkvindu,ellers er det copypasta
		//routemap er 800 * 400, finne ut hvor ruten skal begynne (x verdi??)
		//makeWindow gir ca 200 pixler ovenfor til å tegne høydeprofil
		//en skaleringsvariabel må leses inn, skaleringen skal brukes på begge visualiseringer
		//mål for 
		
		double xforst = gpspoints[0].getLongitude();
		double yminst = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		int ybaseRoute = 2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE;
		int ybaseProfile = HEIGHTSIZE + MARGIN;
		int radius = 2;
			setColor(250,50,50);
			int dot = fillCircle((int)((gpspoints[0].getLongitude() - xforst) * xstep() + MARGIN), ybaseRoute - MARGIN - ((int)((gpspoints[0].getLatitude() - yminst) * ystep())),radius * 2);
		int xbaseProfile = MARGIN;
		int time = 0;
		
		double scale = Double.parseDouble(JOptionPane.showInputDialog("velg skalering av tid (mellom 10 og 300 er anbefalt): "));
		
		for (int i=0;i<gpspoints.length;i++) {
			
            int x1 = (int)((gpspoints[i].getLongitude() - xforst) * xstep() + MARGIN);
			int y1 = ybaseRoute - MARGIN - ((int)((gpspoints[i].getLatitude() - yminst) * ystep()));
			//int xdot = (int)((gpspoints[i].getLongitude() - xforst) * xstep() + MARGIN);
			//int ydot = ybase - MARGIN - ((int)((gpspoints[i].getLatitude() - yminst) * ystep()));
           
			moveCircle(dot, x1, y1);
			
			setColor(80,136,180);
			fillCircle(x1,y1,radius);
			
				if (i > 0) {
					
				int x0 = (int)((gpspoints[i-1].getLongitude() - xforst) * xstep() + MARGIN);	
				int y0 = ybaseRoute - MARGIN - ((int)((gpspoints[i-1].getLatitude() - yminst) * ystep()));
					drawLine(x1,y1,x0,y0);
				}
				
				if (gpspoints[i].getElevation() >= 0) {
					
					drawLine(xbaseProfile+i,ybaseProfile,xbaseProfile+i,ybaseProfile-(int)gpspoints[i].getElevation());
			}
				
				else {
					drawLine(xbaseProfile+i,ybaseProfile,xbaseProfile+i,ybaseProfile);
				}
				
				if (i < gpspoints.length - 1) {
					time = gpspoints[i+1].getTime() - gpspoints[i].getTime();
					}
				
			//tegne hvit firkant over gammel string
			//drawString med km/t
				
			//setColor(255,255,255);
			//drawRectangle(0,0, MARGIN * 2,MARGIN * 2);
			
			//String speed = " ";
			
			//if (i > 0) {
			//speed = GPSUtils.formatDouble(GPSUtils.speed(gpspoints[i-1], gpspoints[i])) + "km/t";
			//}
			
			//setColor(0,0,0);
			//drawString(speed, 20, 20);
			pause((int)(time / scale * 1000));
			
		}
	}

	public double xstep() {

		double xstep = ROUTEMAPXSIZE / (Math.abs(maxlon - minlon));
		
		return xstep;
	}

	public double ystep() {

		double ystep = ROUTEMAPYSIZE / (Math.abs(maxlat - minlat));
		
		return ystep;
	}

}
